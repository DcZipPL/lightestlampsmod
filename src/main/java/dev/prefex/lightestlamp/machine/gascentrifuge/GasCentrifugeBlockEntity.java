package dev.prefex.lightestlamp.machine.gascentrifuge;

import com.google.common.collect.Maps;
import dev.prefex.lightestlamp.Config;
import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlockEntities;
import dev.prefex.lightestlamp.init.ModItems;
import dev.prefex.lightestlamp.init.ModMiscs;
import dev.prefex.lightestlamp.items.FilterItem;
import dev.prefex.lightestlamp.util.CustomEnergyStorage;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GasCentrifugeBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible
{

	public GasCentrifugeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container.lightestlamp.glowstone_centrifuge");
	}

	public GasCentrifugeBlockEntity(BlockPos pos, BlockState state) {this(ModBlockEntities.CENTRIFUGE_BE.get(), pos, state);}
	@Nullable
	private Component customName;

	public void setCustomName(Component customName) {
		this.customName = customName;
	}

	@Override
	public Component getName() {
		return customName != null ? customName : getDefaultName();
	}

	@Override
	@Nullable
	public Component getCustomName() {
		return customName;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items = items;
	}

	@Override
	protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
		return new GasCentrifugeMenu(ModMiscs.GAS_CENTRIFUGE.get(), pContainerId, pInventory, this, this.furnaceData, worldPosition);
	}

	public static final int MAX_IN = 164;
	public static final int OC_MAX_IN = 286;
	public static final int magic = 12;
	private static final int[] SLOTS_UP = new int[]{0,1};
	private static final int[] SLOTS_DOWN = new int[]{2, 3, 4, 5};
	//private static final int[] SLOTS_HORIZONTAL = new int[]{1};
	protected NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
	private final RecipeManager.CachedCheck<GasCentrifugeRecipe.Input, GasCentrifugeRecipe> recipeCheck =
			RecipeManager.createCheck(ModMiscs.GLOWSTONE_CENTRIFUGE_RECIPE_TYPE.get());
	private int machineStatus;
	private int burnTime;
	private int cookTime;
	private int cookTimeTotal;
	private int redstoneMode;
	private int liquidMode;
	private final CustomEnergyStorage energyStorage = new CustomEnergyStorage(1600 * magic, MAX_IN);
	private final SidedInvWrapper inputHandler = new SidedInvWrapper(this, Direction.UP);
	private final SidedInvWrapper outputHandler = new SidedInvWrapper(this, Direction.DOWN);
	private final SidedInvWrapper sideHandler = new SidedInvWrapper(this, Direction.NORTH);
	public final ContainerData furnaceData = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> GasCentrifugeBlockEntity.this.burnTime;
				case 1 -> GasCentrifugeBlockEntity.this.redstoneMode;
				case 2 -> GasCentrifugeBlockEntity.this.cookTime;
				case 3 -> GasCentrifugeBlockEntity.this.cookTimeTotal;
				case 4 -> GasCentrifugeBlockEntity.this.liquidMode;
				case 5 -> GasCentrifugeBlockEntity.this.getEnergyStorage().getEnergyStored();
				case 6 -> GasCentrifugeBlockEntity.this.machineStatus;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0 -> GasCentrifugeBlockEntity.this.burnTime = value;
				case 1 -> GasCentrifugeBlockEntity.this.redstoneMode = value;
				case 2 -> GasCentrifugeBlockEntity.this.cookTime = value;
				case 3 -> GasCentrifugeBlockEntity.this.cookTimeTotal = value;
				case 4 -> GasCentrifugeBlockEntity.this.liquidMode = value;
				case 5 -> GasCentrifugeBlockEntity.this.getEnergyStorage().setEnergy(value);
				case 6 -> GasCentrifugeBlockEntity.this.machineStatus = value;
			}
		}

		@Override
		public int getCount() {
			return 7;
		}
	};

	public void setRedstoneMode(int redstoneMode)
	{
		furnaceData.set(1,redstoneMode);
	}

	public int getRedstoneMode()
	{
		return furnaceData.get(1);
	}

	public void setLiquidMode(int liquidMode)
	{
		furnaceData.set(4,liquidMode);
	}

	public int getLiquidMode()
	{
		return furnaceData.get(4);
	}

	public CustomEnergyStorage getEnergyStorage(){
		return this.energyStorage;
	}

	public IItemHandler getItemHandler(@Nullable Direction side) {
		if (side == Direction.UP) {
			return inputHandler;
		}
		if (side == Direction.DOWN) {
			return outputHandler;
		}
		return sideHandler;
	}

	private static volatile Map<Item, Integer> cachedBurnTimes;
	public static Map<Item, Integer> getBurnTimes() {
		Map<Item, Integer> cached = cachedBurnTimes;
		if (cached != null) {
			return cached;
		}
		Map<Item, Integer> map = Maps.newLinkedHashMap();

		int multiplier = Config.GLOWSTONE_FUEL_MULTIPLIER.get() >= 2 ? Config.GLOWSTONE_FUEL_MULTIPLIER.get() : 2;
		// TODO: Add mod compat

		BuiltInRegistries.ITEM.getTagOrEmpty(Util.getCentrifugablesTag()).forEach(holder -> {
			Item item = holder.value();
			if (item == Blocks.GLOWSTONE.asItem())
				add(map, Blocks.GLOWSTONE, 160*multiplier);
			else if (item == Blocks.SHROOMLIGHT.asItem())
				add(map, Blocks.SHROOMLIGHT, 240*multiplier);
			else if (item == ModItems.GLOW_LICHEN_FIBER.get())
				add(map, ModItems.GLOW_LICHEN_FIBER.get(), 10*multiplier); // TODO: Apply this to Fabric/Quilt version
			else if (item == Blocks.GLOW_LICHEN.asItem())
				add(map, Blocks.GLOW_LICHEN, 5*multiplier);
			else if (item == Items.GLOW_BERRIES)
				add(map, Items.GLOW_BERRIES, 60*multiplier);
		});

		add(map, Tags.Items.DUSTS_GLOWSTONE,40*multiplier);
		cachedBurnTimes = Map.copyOf(map);
		return cachedBurnTimes;
	}

	public static void invalidateBurnTimes() {
		cachedBurnTimes = null;
	}

	private static void add(Map<Item, Integer> pMap, TagKey<Item> pItemTag, int pBurnTime) {
		for (var holder : BuiltInRegistries.ITEM.getTagOrEmpty(pItemTag)) {
			pMap.put(holder.value(), pBurnTime);
		}

	}

	private static void add(Map<Item, Integer> pMap, ItemLike pItem, int pBurnTime) {
		pMap.put(pItem.asItem(), pBurnTime);
	}

	private boolean isBurning() {
		return this.burnTime > 0;
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag pTag, HolderLookup.Provider registries) {
		super.saveAdditional(pTag, registries);
		saveInternal(pTag, registries);
	}

	private void saveInternal(CompoundTag pTag, HolderLookup.Provider registries){
		if (customName != null) {
			pTag.putString("CustomName", Component.Serializer.toJson(customName, registries));
		}
		pTag.putInt("BurnTime", this.burnTime);
		pTag.putInt("CookTime", this.cookTime);
		pTag.putInt("CookTimeTotal", this.cookTimeTotal);
		pTag.putInt("RedstoneMode", this.redstoneMode);
		pTag.putInt("LiquidMode", this.liquidMode);
		pTag.putInt("Power",this.getEnergyStorage().getEnergyStored());
		ContainerHelper.saveAllItems(pTag, this.items, registries);
	}

	@Override
	protected void loadAdditional(@NotNull CompoundTag pTag, HolderLookup.Provider registries) {
		super.loadAdditional(pTag, registries);
		loadInternal(pTag, registries);
	}

	private void loadInternal(CompoundTag nbt, HolderLookup.Provider registries) {
		customName = nbt.contains("CustomName", Tag.TAG_STRING)
				? BlockEntity.parseCustomNameSafe(nbt.getString("CustomName"), registries)
				: null;
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(nbt, this.items, registries);
		this.burnTime = nbt.getInt("BurnTime");
		this.cookTime = nbt.getInt("CookTime");
		this.cookTimeTotal = nbt.getInt("CookTimeTotal");
		this.getEnergyStorage().setEnergy(nbt.getInt("Power"));
		this.redstoneMode = nbt.getInt("RedstoneMode");
		this.liquidMode = nbt.getInt("LiquidMode");
	}

	public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
		if (pLevel.isClientSide || !(pBlockEntity instanceof GasCentrifugeBlockEntity blockEntity)) {
			return;
		}
		boolean flag = blockEntity.isBurning();
		boolean flag1 = false;
		ItemStack filter = blockEntity.items.get(0);
		ItemStack fuel = blockEntity.items.get(1);
		GasCentrifugeRecipe recipe = filter.isEmpty() || fuel.isEmpty() ? null : blockEntity.recipeCheck
				.getRecipeFor(new GasCentrifugeRecipe.Input(filter, fuel), pLevel)
				.map(RecipeHolder::value)
				.orElse(null);
		ItemStack[] outputs = recipe == null ? null : recipe.assemble();
		blockEntity.machineStatus = blockEntity.determineStatus(recipe, outputs);
		boolean canSmelt = blockEntity.machineStatus == 0 || blockEntity.machineStatus == 1;
		if (!blockEntity.isBurning() && fuel.isEmpty() && blockEntity.cookTime == 0) {
			return;
		}

		// Decrease burn time
		if (blockEntity.isBurning() && canSmelt) {
			--blockEntity.burnTime;
			if (blockEntity.getEnergyStorage().getEnergyStored() > 0&&blockEntity.furnaceData.get(4)!=0){
				blockEntity.getEnergyStorage().extractEnergy((int) (80 * (blockEntity.furnaceData.get(4)==2 ? 1.6f : 1f)),false);
			}
		}

		{
			ItemStack itemstack = fuel;
			if (blockEntity.isBurning()
					|| !itemstack.isEmpty() && !blockEntity.items.get(0).isEmpty()) {
				if (!blockEntity.isBurning() && canSmelt
						&& (blockEntity.getEnergyStorage().getEnergyStored() > 0||blockEntity.furnaceData.get(4)==0)) {
					blockEntity.burnTime = blockEntity.getBurnTime(itemstack);
					// Use items
					if (blockEntity.isBurning()) {
						flag1 = true;
						if (itemstack.hasCraftingRemainingItem())
							blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
						else
						if (!itemstack.isEmpty()) {
							itemstack.shrink(1);
							if (itemstack.isEmpty()) {
								blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
							}
						}
					}
				}

				// Increase burn time
				if (blockEntity.isBurning() && canSmelt) {
					++blockEntity.cookTime;
					// If done produce items
					if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
						blockEntity.cookTime = 0;
						blockEntity.cookTimeTotal = blockEntity.getCookTimeTotal();
						blockEntity.placeItemsInRightSlot(outputs);
						flag1 = true;
					}
				} else {
					blockEntity.cookTime = 0;
				}
			} else if (!blockEntity.isBurning() && blockEntity.cookTime > 0) {
				blockEntity.cookTime = Mth.clamp(blockEntity.cookTime - 2, 0, blockEntity.cookTimeTotal);
			}

			if (flag != blockEntity.isBurning()) {
				flag1 = true;
			}
		}

		if (flag1) {
			setChanged(pLevel, pPos, pState);
		}
	}

	private int getCookTimeTotal()
	{
		return (int)(
				(80 * (liquidMode != 2 ?
			((liquidMode == 0 && (Config.ENERGY_MODE.get() != Config.EnergyModes.passive_only))
			? 1.8f : 1.0f) : 0.5f)));
	}

	private int determineStatus(@Nullable GasCentrifugeRecipe recipe, @Nullable ItemStack[] outputs) {
		if ((redstoneMode == 1 && level.getDirectSignalTo(worldPosition) > 0)
				|| (redstoneMode == 2 && level.getDirectSignalTo(worldPosition) < 1)) {
			return 3;
		}
		if (items.get(0).isEmpty() || items.get(1).isEmpty()) {
			return 4;
		}
		if (recipe == null || outputs == null || allOutputsEmpty(outputs)) {
			return 5;
		}
		if (!outputsFit(outputs)) {
			return 6;
		}
		if (liquidMode != 0 && energyStorage.getEnergyStored() <= 0) {
			return 2;
		}
		return liquidMode == 0 ? 1 : 0;
	}

	private static boolean allOutputsEmpty(ItemStack[] outputs) {
		for (ItemStack output : outputs) {
			if (!output.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	private boolean outputsFit(ItemStack[] outputs) {
		for (int i = 0; i < outputs.length; i++) {
			ItemStack output = outputs[i];
			ItemStack current = items.get(i + 2);
			if (output.isEmpty() || current.isEmpty()) {
				continue;
			}
			if (!ItemStack.isSameItemSameComponents(current, output)
					|| current.getCount() + output.getCount() > Math.min(getMaxStackSize(), current.getMaxStackSize())) {
				return false;
			}
		}
		return true;
	}

	private void placeItemsInRightSlot(@Nullable ItemStack[] itemstacks)
	{
		if (itemstacks != null) {
			ItemStack itemstack = this.items.get(0);
			ItemStack itemstack2 = this.items.get(2);
			ItemStack itemstack3 = this.items.get(3);
			ItemStack itemstack4 = this.items.get(4);
			ItemStack itemstack5 = this.items.get(5);
			if (itemstack2.isEmpty()) {
				this.items.set(2, itemstacks[0].copy());
			} else if (itemstack2.getItem() == itemstacks[0].getItem()) {
				itemstack2.grow(itemstacks[0].getCount());
			}
			if (itemstack3.isEmpty()) {
				this.items.set(3, itemstacks[1].copy());
			} else if (itemstack3.getItem() == itemstacks[1].getItem()) {
				itemstack3.grow(itemstacks[1].getCount());
			}
			if (itemstack4.isEmpty()) {
				this.items.set(4, itemstacks[2].copy());
			} else if (itemstack4.getItem() == itemstacks[2].getItem()) {
				itemstack4.grow(itemstacks[2].getCount());
			}
			if (itemstack5.isEmpty()) {
				this.items.set(5, itemstacks[3].copy());
			} else if (itemstack5.getItem() == itemstacks[3].getItem()) {
				itemstack5.grow(itemstacks[3].getCount());
			}

			if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
				int damage = net.minecraft.world.item.enchantment.EnchantmentHelper.processDurabilityChange(serverLevel, itemstack, 1);
				itemstack.setDamageValue(itemstack.getDamageValue() + damage);
			}

			if (itemstack.getDamageValue() >= itemstack.getMaxDamage())
				this.items.set(0, ItemStack.EMPTY);
		}
	}

	protected static int getBurnTime(ItemStack stack) {
		if (stack.isEmpty()) {
			return 0;
		} else {
			Item item = stack.getItem();
			return getBurnTimes().getOrDefault(item, 0);
		}
	}

	public static boolean isFuel(ItemStack stack) {
		return getBurnTime(stack) > 0;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN) {
			return SLOTS_DOWN;
		} else {
			return SLOTS_UP;
		}
	}

	@Override
	public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
		return this.isItemValidForSlot(pIndex, pItemStack);
	}

	@Override
	public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
		return true;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getContainerSize() {
		return this.items.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the stack in the given slot.
	 */
	public @NotNull ItemStack getItem(int pIndex) {
		return this.items.get(pIndex);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	public @NotNull ItemStack removeItem(int pIndex, int pCount) {
		return ContainerHelper.removeItem(this.items, pIndex, pCount);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	public @NotNull ItemStack removeItemNoUpdate(int pIndex) {
		return ContainerHelper.takeItem(this.items, pIndex);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	public void setItem(int pIndex, ItemStack pStack) {
		ItemStack itemstack = this.items.get(pIndex);
		boolean flag = !pStack.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, pStack);
		this.items.set(pIndex, pStack);
		if (pStack.getCount() > this.getMaxStackSize()) {
			pStack.setCount(this.getMaxStackSize());
		}

		if (pIndex == 0 && !flag) {
			this.cookTimeTotal = getCookTimeTotal();//this.level, this.recipeType, this
			this.cookTime = 0;
			this.setChanged();
		}

	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	public boolean stillValid(@NotNull Player pPlayer) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return pPlayer.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}

	public boolean isFilter(ItemStack stack){
		return stack.getItem() instanceof FilterItem;
	}

	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) {
			return isFilter(stack);
		} else if (index == 1) {
			return isFuel(stack);
		} else {
			return false;
		}
	}

	@Override
	public void clearContent() {
		this.items.clear();
	}

	@Override
	public void fillStackedContents(StackedContents pHelper) {
		for(ItemStack itemstack : this.items) {
			pHelper.accountStack(itemstack);
		}
	}
}
