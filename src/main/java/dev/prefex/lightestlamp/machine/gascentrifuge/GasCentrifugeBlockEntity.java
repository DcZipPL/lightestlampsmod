package dev.prefex.lightestlamp.machine.gascentrifuge;

import com.google.common.collect.Maps;
import dev.prefex.lightestlamp.Config;
import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlockEntities;
import dev.prefex.lightestlamp.init.ModItems;
import dev.prefex.lightestlamp.init.ModMiscs;
import dev.prefex.lightestlamp.items.FilterItem;
import dev.prefex.lightestlamp.util.CustomEnergyStorage;
import io.netty.buffer.Unpooled;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

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

	@Override
	protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
		FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer(8,8));
		buffer.writeBlockPos(worldPosition);
		return new GasCentrifugeMenu(ModMiscs.GAS_CENTRIFUGE.get(),pContainerId, pInventory, this, this.furnaceData, buffer);
	}

	public static final int MAX_IN = 164;
	public static final int OC_MAX_IN = 286;
	public static final int magic = 12;
	private static final int[] SLOTS_UP = new int[]{0,1};
	private static final int[] SLOTS_DOWN = new int[]{2, 3, 4, 5};
	//private static final int[] SLOTS_HORIZONTAL = new int[]{1};
	protected NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
	private int ticksBeforeDumping;
	private int burnTime;
	private int cookTime;
	private int cookTimeTotal;
	private int redstoneMode;
	private int liquidMode;
	public LazyOptional<CustomEnergyStorage> energyStorage = LazyOptional.of(()->new CustomEnergyStorage(1600 * magic, MAX_IN));
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
				case 6 -> GasCentrifugeBlockEntity.this.ticksBeforeDumping;
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
				case 6 -> GasCentrifugeBlockEntity.this.ticksBeforeDumping = value;
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
		return this.energyStorage.orElse(new CustomEnergyStorage(1600,0));
	}

	public static Map<Item, Integer> addedCentrifugables = Maps.newLinkedHashMap();
	public static Map<Item, Integer> getBurnTimes() {
		Map<Item, Integer> map = addedCentrifugables; //= Maps.newLinkedHashMap();

		int multiplier = Config.GLOWSTONE_FUEL_MULTIPLIER.get() >= 2 ? Config.GLOWSTONE_FUEL_MULTIPLIER.get() : 2;
		//Mekanism compatibility
        /*ITag<Item> refined_glowstones = ItemTags.getCollection().get(new ResourceLocation("forge:ingots/refined_glowstone"));
        if (refined_glowstones!=null)
            add(map, refined_glowstones,60*multiplier);
        ITag<Item> refined_glowstones_block = ItemTags.getCollection().get(new ResourceLocation("forge:storage_blocks/refined_glowstone"));
        if (refined_glowstones_block!=null)
            add(map, refined_glowstones_block,520*multiplier);
        ITag<Item> refined_glowstones_nugget = ItemTags.getCollection().get(new ResourceLocation("forge:nuggets/refined_glowstone"));
        if (refined_glowstones_nugget!=null)
            add(map, refined_glowstones_nugget,5*multiplier);
        ITag<Item> glowstone_blocks = ItemTags.getCollection().get(new ResourceLocation("forge:storage_blocks/glowstone"));
        if (glowstone_blocks!=null)
            add(map, glowstone_blocks,360*multiplier);*/ // TODO: Create tag for this stuff

		ForgeRegistries.ITEMS.tags().getTag(Util.getCentrifugablesTag()).forEach(item -> {
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
		return map;
	}

	private static void add(Map<Item, Integer> pMap, TagKey<Item> pItemTag, int pBurnTime) {
		for(Item item : ForgeRegistries.ITEMS.tags().getTag(pItemTag)) {
			pMap.put(item, pBurnTime);
		}

	}

	private static void add(Map<Item, Integer> pMap, ItemLike pItem, int pBurnTime) {
		pMap.put(pItem.asItem(), pBurnTime);
	}

	private boolean isBurning() {
		return this.burnTime > 0;
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag pTag) {
		super.saveAdditional(pTag);
		saveInternal(pTag);
	}

	private void saveInternal(CompoundTag pTag){
		pTag.putInt("BurnTime", this.burnTime);
		pTag.putInt("CookTime", this.cookTime);
		pTag.putInt("CookTimeTotal", this.cookTimeTotal);
		pTag.putInt("RedstoneMode", this.redstoneMode);
		pTag.putInt("LiquidMode", this.liquidMode);
		pTag.putInt("Power",this.getEnergyStorage().getEnergyStored());
		ContainerHelper.saveAllItems(pTag, this.items);
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = super.serializeNBT();
		saveInternal(nbt);
		return nbt;
	}

	@Override
	public void load(@NotNull CompoundTag pTag) {
		super.load(pTag);
		loadInternal(pTag);
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		super.deserializeNBT(nbt);
		loadInternal(nbt);
	}

	private void loadInternal(CompoundTag nbt) {
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(nbt, this.items);
		this.burnTime = nbt.getInt("BurnTime");
		this.cookTime = nbt.getInt("CookTime");
		this.cookTimeTotal = nbt.getInt("CookTimeTotal");
		this.getEnergyStorage().setEnergy(nbt.getInt("Power"));
		this.redstoneMode = nbt.getInt("RedstoneMode");
		this.liquidMode = nbt.getInt("LiquidMode");
	}

	public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
		GasCentrifugeBlockEntity blockEntity = (GasCentrifugeBlockEntity) pBlockEntity;
		boolean flag = blockEntity.isBurning();
		boolean flag1 = false;
		GasCentrifugeRecipe recipe = pLevel.getRecipeManager().getRecipeFor((RecipeType<GasCentrifugeRecipe>)ModMiscs.GLOWSTONE_CENTRIFUGE_RECIPE_TYPE.get(), blockEntity, pLevel).orElse(null);

		// Decrease burn time
		if (blockEntity.isBurning() && blockEntity.canSmelt(recipe)) {
			--blockEntity.burnTime;
			if (blockEntity.getEnergyStorage().getEnergyStored() > 0&&blockEntity.furnaceData.get(4)!=0){
				blockEntity.getEnergyStorage().extractEnergy((int) (80 * (blockEntity.furnaceData.get(4)==2 ? 1.6f : 1f)),false);
			}
		}

		if (!pLevel.isClientSide) {
			ItemStack itemstack = blockEntity.items.get(1);
			if (blockEntity.isBurning()
					|| !itemstack.isEmpty() && !blockEntity.items.get(0).isEmpty()) {
				if (!blockEntity.isBurning() && blockEntity.canSmelt(recipe)
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
				if (blockEntity.isBurning() && blockEntity.canSmelt(recipe)) {
					++blockEntity.cookTime;
					// If done produce items
					if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
						blockEntity.cookTime = 0;
						blockEntity.cookTimeTotal = blockEntity.getCookTimeTotal();
						blockEntity.placeItemsInRightSlot(recipe);
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

	protected boolean canSmelt(@Nullable Recipe<?> pRecipe) {
		if (!this.items.get(0).isEmpty()) {
			if ((GasCentrifugeRecipe) pRecipe == null) return false;
			ItemStack[] itemstacks = ((GasCentrifugeRecipe) pRecipe).assemble();
			if (itemstacks[0].isEmpty()&&itemstacks[1].isEmpty()&&itemstacks[2].isEmpty()&&itemstacks[3].isEmpty())
			{
				return false;
			} else if (redstoneMode==1&&level.getDirectSignalTo(worldPosition)>0||redstoneMode==2&&level.getDirectSignalTo(worldPosition)<1) // Redstone
			{
				return false;
			} else {
				ItemStack[] itemstacks1 = new ItemStack[] {this.items.get(2),this.items.get(3),this.items.get(4),this.items.get(5)};
				boolean[] output0 = new boolean[] {true,true,true,true};
				for (int i = 0; i < itemstacks.length-1; i++)
				{
					ItemStack itemstack = itemstacks[i];
					ItemStack itemstack1 = itemstacks1[i];
					if (itemstack1.isEmpty()) {
						output0[i] =  true;
					} else if (!ItemStack.isSameItem(itemstack1, itemstack)) {
						output0[i] =  false;
					} else if (itemstack1.getCount() + itemstack.getCount() <= getMaxStackSize() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) {
						// Forge fix: make furnace respect stack sizes in furnace recipes
						output0[i] =  true;
					} else {
						output0[i] =  itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
						// Forge fix: make furnace respect stack sizes in furnace recipes
					}
				}
				return output0[0]&&output0[1]&&output0[2]&&output0[3];
			}
		} else {
			return false;
		}
	}

	private void placeItemsInRightSlot(@Nullable GasCentrifugeRecipe pRecipe)
	{
		if (this.canSmelt(pRecipe)) {
			ItemStack itemstack = this.items.get(0);
			ItemStack[] itemstacks = pRecipe.assemble();
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

			int unbreaking_lvl = 0;
			for (Tag enchantment : itemstack.getEnchantmentTags())
			{
				if (((CompoundTag) enchantment).getString("id").equals("minecraft:unbreaking"))
					unbreaking_lvl = ((CompoundTag)enchantment).getShort("lvl");
			}

			itemstack.setDamageValue(itemstack.getDamageValue()+
					(
							unbreaking_lvl==0
									?
									1
									:
									(new Random().nextInt(unbreaking_lvl+1)==1?1:0)
					));

			if (itemstack.getDamageValue() >= itemstack.getMaxDamage())
				this.items.set(0, ItemStack.EMPTY);
		}
	}

	protected int getBurnTime(ItemStack stack) {
		if (stack.isEmpty()) {
			return 0;
		} else {
			Item item = stack.getItem();
			return getBurnTimes().getOrDefault(item, 0);
		}
	}

	public boolean isFuel(ItemStack stack) {
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
	public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @org.jetbrains.annotations.Nullable Direction pDirection) {
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
		boolean flag = !pStack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, pStack);
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

	net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
			net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	/*@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
		if (!this.remove && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == Direction.UP)
				return handlers[0].cast();
			else if (facing == Direction.DOWN)
				return handlers[1].cast();
			else
				return handlers[2].cast();
		}
		if (!this.remove && facing != null && capability == CapabilityEnergy.ENERGY) {
			return energyStorage.cast();
		}
		return super.getCapability(capability, facing);
	}*/ // TODO: Re-Implement capabilities

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