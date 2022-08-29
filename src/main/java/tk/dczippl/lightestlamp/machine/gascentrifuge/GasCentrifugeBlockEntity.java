package tk.dczippl.lightestlamp.machine.gascentrifuge;

import com.google.common.collect.Maps;
import io.netty.buffer.Unpooled;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;
import tk.dczippl.lightestlamp.Config;
import tk.dczippl.lightestlamp.init.ModContainers;
import tk.dczippl.lightestlamp.init.ModBlockEntities;
import tk.dczippl.lightestlamp.items.FilterItem;
import tk.dczippl.lightestlamp.util.FluidHandlerWrapper;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class GasCentrifugeBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible, BlockEntityTicker<GasCentrifugeBlockEntity>
{

    public GasCentrifugeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.lightestlamp.gascentrifuge");
    }

    public GasCentrifugeBlockEntity(BlockPos pos, BlockState state) {this(ModBlockEntities.CENTRIFUGE_BE, pos, state);}

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer(8,8));
        buffer.writeBlockPos(worldPosition);
        return new GasCentrifugeMenu(ModContainers.GAS_CENTRIFUGE,pContainerId, pInventory, this, this.furnaceData, buffer);
    }

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
    public final ContainerData furnaceData = new ContainerData() {
        @Override
        public int get(int index) {
            switch(index) {
                case 0:
                    return GasCentrifugeBlockEntity.this.burnTime;
                case 1:
                    return GasCentrifugeBlockEntity.this.redstoneMode;
                case 2:
                    return GasCentrifugeBlockEntity.this.cookTime;
                case 3:
                    return GasCentrifugeBlockEntity.this.cookTimeTotal;
                case 4:
                    return GasCentrifugeBlockEntity.this.liquidMode;
                case 5:
                    return GasCentrifugeBlockEntity.this.tank.getFluidAmount();
                case 6:
                    return GasCentrifugeBlockEntity.this.ticksBeforeDumping;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch(index) {
                case 0:
                    GasCentrifugeBlockEntity.this.burnTime = value;
                    break;
                case 1:
                    GasCentrifugeBlockEntity.this.redstoneMode = value;
                    break;
                case 2:
                    GasCentrifugeBlockEntity.this.cookTime = value;
                    break;
                case 3:
                    GasCentrifugeBlockEntity.this.cookTimeTotal = value;
                    break;
                case 4:
                    GasCentrifugeBlockEntity.this.liquidMode = value;
                    break;
                case 5:
                    break;
                case 6:
                    GasCentrifugeBlockEntity.this.ticksBeforeDumping = value;
                    break;
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

    public void startTicksBeforeDumping()
    {
        furnaceData.set(6,60);
    }

    public static Map<Item, Integer> getBurnTimes() {
        Map<Item, Integer> map = Maps.newLinkedHashMap();

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

        add(map, Tags.Items.DUSTS_GLOWSTONE,40*multiplier);
        add(map, Blocks.GLOWSTONE, 160*multiplier);
        add(map, Blocks.SHROOMLIGHT, 240*multiplier);
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
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.items);
        this.burnTime = nbt.getInt("BurnTime");
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        //this.recipesUsed = this.getBurnTime(this.items.get(1));
        this.redstoneMode = nbt.getInt("RedstoneMode");
        this.liquidMode = nbt.getInt("LiquidMode");
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = super.serializeNBT();
        nbt.putInt("BurnTime", this.burnTime);
        nbt.putInt("CookTime", this.cookTime);
        nbt.putInt("CookTimeTotal", this.cookTimeTotal);
        nbt.putInt("RedstoneMode", this.redstoneMode);
        nbt.putInt("LiquidMode", this.liquidMode);
        ContainerHelper.saveAllItems(nbt, this.items);
        return nbt;
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, GasCentrifugeBlockEntity pBlockEntity) {
        boolean flag = pBlockEntity.isBurning();
        boolean flag1 = false;
        if (pBlockEntity.isBurning()) {
            --pBlockEntity.burnTime;
        }

        if (!pLevel.isClientSide) {
            ItemStack itemstack = pBlockEntity.items.get(1);
            if (pBlockEntity.isBurning() || !itemstack.isEmpty() && !pBlockEntity.items.get(0).isEmpty()) {
                if (!pBlockEntity.isBurning() && pBlockEntity.canSmelt()) {
                    pBlockEntity.burnTime = pBlockEntity.getBurnTime(itemstack);
                    //pBlockEntity.recipesUsed = pBlockEntity.burnTime;
                    if (pBlockEntity.isBurning()) {
                        flag1 = true;
                        if (itemstack.hasContainerItem())
                            pBlockEntity.items.set(1, itemstack.getContainerItem());
                        else
                        if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                pBlockEntity.items.set(1, itemstack.getContainerItem());
                            }
                        }
                    }
                }

                if (pBlockEntity.isBurning() && pBlockEntity.canSmelt()) {
                    ++pBlockEntity.cookTime;
                    if (pBlockEntity.cookTime == pBlockEntity.cookTimeTotal) {
                        pBlockEntity.cookTime = 0;
                        pBlockEntity.cookTimeTotal = getCookTimeTotal();
                        pBlockEntity.placeItemsInRightSlot();
                        flag1 = true;
                    }
                } else {
                    pBlockEntity.cookTime = 0;
                }
            } else if (!pBlockEntity.isBurning() && pBlockEntity.cookTime > 0) {
                pBlockEntity.cookTime = Mth.clamp(pBlockEntity.cookTime - 2, 0, pBlockEntity.cookTimeTotal);
            }

            if (flag != pBlockEntity.isBurning()) {
                flag1 = true;
                //pBlockEntity.world.setBlockState(pBlockEntity.pos, pBlockEntity.world.getBlockState(pBlockEntity.pos).with(AbstractFurnaceBlock.LIT, Boolean.valueOf(pBlockEntity.isBurning())), 3);
            }
        }

        if (flag1) {
            setChanged(pLevel, pPos, pState);
        }
    }

    private int getCookTimeTotal()
    {
        return 100;
    }

    protected boolean canSmelt() {
        if (!this.items.get(0).isEmpty()) {
            ItemStack[] itemstacks = GasCentrifugeRecipe.getRecipeOutputs(items.get(0));
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
                    } else if (!itemstack1.sameItem(itemstack)) {
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

    private void placeItemsInRightSlot(/*Here was recipe*/)
    {
        if (this.canSmelt()) {
            ItemStack itemstack = this.items.get(0);
            ItemStack[] itemstacks = GasCentrifugeRecipe.getRecipeOutputs(items.get(0));
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
            for (INBT enchantment : itemstack.getEnchantmentTagList())
            {
                if (((CompoundNBT) enchantment).getString("id").equals("minecraft:unbreaking"))
                    unbreaking_lvl = ((CompoundNBT)enchantment).getShort("lvl");
            }

            itemstack.setDamage(itemstack.getDamage()+
                    (
                            unbreaking_lvl==0
                                    ?
                                    1
                                    :
                                    (new Random().nextInt(unbreaking_lvl+1)==1?1:0)
                    ));
            //unbreaking_lvl==0 ? 1 : (new Random().nextInt(unbreaking_lvl+1)==1?1:0)

            if (itemstack.getDamage() >= itemstack.getMaxDamage())
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

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction)
    {
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
    public ItemStack getItem(int pIndex) {
        return this.items.get(pIndex);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(this.items, pIndex, pCount);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(this.items, pIndex);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int pIndex, ItemStack pStack) {
        ItemStack itemstack = this.items.get(pIndex);
        boolean flag = !pStack.isEmpty() && pStack.sameItem(itemstack) && ItemStack.tagMatches(pStack, itemstack);
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
    public boolean stillValid(Player pPlayer) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return pPlayer.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public boolean isFilter(ItemStack stack){
        return stack.getItem() instanceof FilterItem;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    @Override
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

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.orEmpty(capability, LazyOptional.of(() -> new FluidHandlerWrapper(this, facing)));
        }
        return super.getCapability(capability, facing);
    }

    /**
     * invalidates a tile entity
     */
    @Override
    public void remove() {
        super.remove();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }
}