package tk.dczippl.lightestlamp.machine.gasextractor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IInteractionObject;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModItems;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings({"WeakerAccess", "NullableProblems"})
public class GasExtractorTileEntity extends LockableTileEntity implements ITickableTileEntity, ISidedInventory, IInteractionObject
{
    public GasExtractorTileEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    public GasExtractorTileEntity()
    {
        super(Reference.EXTRACTOR_TE);
    }

    @Override
    public int[] getSlotsForFace(Direction enumFacing)
    {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemStack, @Nullable Direction enumFacing)
    {
        return true;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemStack, Direction enumFacing)
    {
        return true;
    }

    // enumerate the slots
    public enum slotEnum
    {
        INPUT_SLOT, CATALYST_SLOT, OUTPUT_SLOT, EXTRA_OUTPUT_SLOT
    }

    // enumerate the redstone state
    public enum redstoneEnum
    {
        IGNORE, REDSTONE_OFF, REDSTONE_ON
    }

    protected static final int[] slotsTop = new int[]{slotEnum.INPUT_SLOT.ordinal()};
    protected static final int[] slotsBottom = new int[]{slotEnum.CATALYST_SLOT.ordinal()};
    protected static final int[] slotsSides = new int[]{slotEnum.OUTPUT_SLOT.ordinal()};

    /**
     * The ItemStacks that hold the items currently being used in the labtable
     */
    protected NonNullList<ItemStack> extractorItemStacks = NonNullList.<ItemStack>
            withSize(5, ItemStack.EMPTY);

    protected boolean hasBeenCompacting = false;

    /**
     * The number of ticks that the labtable will keep compacting
     */
    protected int timeCanCompact;
    protected int currentItemCompactTime; // not used currently but holdover from fuel-based tile entity
    protected int ticksCompactingItemSoFar;
    protected int ticksPerItem;
    protected int argon;
    protected int krypton;
    protected int redstone;

    protected String extractorCustomName;

    //protected IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    //protected IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    //protected IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

    /**
     * Returns the number of slots in the inventory.
     *
     * @return the size inventory
     */
    @Override
    public int getSizeInventory()
    {
        return extractorItemStacks.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.inventory.IInventory#isEmpty()
     */
    @Override
    public boolean isEmpty()
    {
        {
            for (ItemStack itemstack : extractorItemStacks)
            {
                if (!itemstack.isEmpty())
                {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Returns the stack in slot i.
     *
     * @param index the index
     * @return the stack in slot
     */
    @Override
    public ItemStack getStackInSlot(int index)
    {
        return extractorItemStacks.get(index);
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack.
     *
     * @param index the index
     * @param count the count
     * @return the item stack
     */
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.extractorItemStacks, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     *
     * @param index the index
     * @return the item stack
     */
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.extractorItemStacks, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param index the index
     * @param stack the stack
     */
    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        // DEBUG
        System.out.println("TileEntityLabTable setInventorySlotContents()");

        boolean isSameItemStackAlreadyInSlot = !stack.isEmpty() && stack.isItemEqual(extractorItemStacks.get(index))
                && ItemStack.areItemStackTagsEqual(stack, extractorItemStacks.get(index));
        extractorItemStacks.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit())
        {
            stack.setCount(getInventoryStackLimit());
        }

        // if input slot, reset the compacting timers
        if (index == slotEnum.INPUT_SLOT.ordinal() && !isSameItemStackAlreadyInSlot) //TODO: CHECK THIS SECTION!
        {
            startCompacting();
        }

        markDirty();
    }

    /**
     * Start compacting.
     */
    protected void startCompacting()
    {
        ticksCompactingItemSoFar = 0;
        ticksPerItem = timeToCompactOneItem(extractorItemStacks.get(slotEnum.INPUT_SLOT.ordinal())); //TODO: CHECK THIS SECTION!
    }

    @Override
    public ITextComponent getName()
    {
        return new StringTextComponent(hasCustomName() ? extractorCustomName : "container.analyzer");
    }

    /**
     * Returns true if this thing is named.
     *
     * @return true, if successful
     */
    @Override
    public boolean hasCustomName()
    {
        return extractorCustomName != null && extractorCustomName.length() > 0;
    }

    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon").
     *
     * @return the name
     */
    @Nullable
    @Override
    public ITextComponent getCustomName()
    {
        return new StringTextComponent(hasCustomName() ? extractorCustomName : "container.analyzer");
    }

    /**
     * Sets the custom inventory name.
     *
     * @param parCustomName the new custom inventory name
     */
    public void setCustomInventoryName(String parCustomName)
    {
        extractorCustomName = parCustomName;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.tileentity.TileEntityLockable#readFromNBT(net.minecraft.nbt.NBTTagCompound)
     */
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        extractorItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, extractorItemStacks);

        timeCanCompact = compound.getShort("CompactTime");
        ticksCompactingItemSoFar = compound.getShort("CookTime");
        ticksPerItem = compound.getShort("CookTimeTotal");
        argon = compound.getShort("Argon");

        if (compound.hasUniqueId("CustomName"))
        {
            extractorCustomName = compound.getString("CustomName");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.tileentity.TileEntityLockable#writeToNBT(net.minecraft.nbt.NBTTagCompound)
     */
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.putShort("CompactTime", (short) timeCanCompact);
        compound.putShort("CookTime", (short) ticksCompactingItemSoFar);
        compound.putShort("CookTimeTotal", (short) ticksPerItem);
        compound.putShort("Argon", (short) argon);
        ItemStackHelper.saveAllItems(compound, extractorItemStacks);

        if (hasCustomName())
        {
            compound.putString("CustomName", extractorCustomName);
        }

        return compound;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this more of a set than a get?*
     *
     * @return the inventory stack limit
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Compacting state changed.
     *
     * @param parHasBeenCompacting the par has been compacting
     */
    protected void compactingStateChanged(boolean parHasBeenCompacting)
    {
        hasBeenCompacting = true;
        GasExtractorBlock.changeBlockBasedOnCompactingStatus(canCompact(), world, pos);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.util.ITickable#update()
     */
    @Override
    public void tick()
    {
        // // DEBUG
        // System.out.println("update() in TileEntityLabTable");

        if (!world.isRemote)
        {
            // if something in input slot
            if (!extractorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()).isEmpty()) //TODO: CHECK THIS SECTION!
            {
                // check if input is compactable
                if (canCompact())
                {
                    // check if just started compacting
                    if (!hasBeenCompacting)
                    {
                        // DEBUG
                        //System.out.println("TileEntityLabTable update() started compacting");

                        compactingStateChanged(true);
                        startCompacting();
                    } else // already compacting
                    {
                        // DEBUG
                        //System.out.println("TileEntityLabTable update() continuing compacting");

                        ++ticksCompactingItemSoFar;

                        // check if completed compacting an item
                        if (ticksCompactingItemSoFar >= ticksPerItem)
                        {
                            // DEBUG
                            //System.out.println("Compacting completed another output cycle");

                            startCompacting();
                            compactItem();
                        }
                    }
                } else // item in input slot is not compactable
                {
                    if (hasBeenCompacting)
                    {
                        compactingStateChanged(false);
                    }

                    ticksCompactingItemSoFar = 0;
                }
            } else // nothing in input slot
            {
                if (hasBeenCompacting)
                {
                    compactingStateChanged(false);
                }

                ticksCompactingItemSoFar = 0;
            }
        }
    }

    /**
     * Time to compact one item.
     *
     * @param parItemStack the par item stack
     * @return the int
     */
    public int timeToCompactOneItem(ItemStack parItemStack)
    {
        return 40;
    }

    /**
     * Returns true if the labtable can compact an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canCompact()
    {
        if (getField(5) == 1 && world.getRedstonePower(pos,Direction.UP) > 0) return false;
        else if (getField(5) == 2 && world.getRedstonePower(pos,Direction.UP) < 1) return false;

            ItemStack stackInOutputSlot = extractorItemStacks.get(slotEnum.OUTPUT_SLOT.ordinal());
            ItemStack stackInInputSlot = extractorItemStacks.get(slotEnum.INPUT_SLOT.ordinal());
            ItemStack stackInCatalystSlot = extractorItemStacks.get(slotEnum.CATALYST_SLOT.ordinal());

            // if nothing in input slot
            if (stackInInputSlot.isEmpty())
            {
                return false;
            } else // check if it has a compacting recipe
            {
                // DEBUG
                //System.out.println("Checking if it has a valid compacting recipe");
                ItemStack itemStackToOutput = GasExtractorRecipes.instance().getResult(stackInInputSlot, stackInCatalystSlot);
                if (itemStackToOutput.isEmpty()) // no valid recipe for compacting this item
                {
                    // DEBUG
                    //System.out.println("Does not have a valid compacting recipe");
                    return false;
                }
                if (stackInOutputSlot.isEmpty()) // output slot is empty
                {
                    // check if enough of the input item (to allow recipes that consume multiple amounts) }
                    if (stackInInputSlot.getCount() >= GasExtractorRecipes.instance().getInputsAmount(stackInInputSlot, stackInCatalystSlot)[0])
                    {
                        if (stackInInputSlot.getCount() >= GasExtractorRecipes.instance().getInputsAmount(stackInInputSlot, stackInCatalystSlot)[1])
                        {
                            // // DEBUG
                            // System.out.println("There is "+stackInInputSlot.stackSize+" in input slot and "+LabTableRecipes.instance().getInputAmount(stackInInputSlot)+" is needed");
                            return true;
                        } else
                        {
                            return false;
                        }
                    } else // not enough in input stack
                    {
                        // // DEBUG
                        // System.out.println("TileEntityLabTable canCompact() right item but not enough in input slot");
                        return false;
                    }
                }
                if (!stackInOutputSlot.isItemEqual(itemStackToOutput)) // output slot has different item occupying it
                {
                    return false;
                }
                // check if output slot is full
                int result = stackInOutputSlot.getCount() + itemStackToOutput.getCount();
                if (result <= getInventoryStackLimit() && result <= stackInOutputSlot.getMaxStackSize())
                {
                    // check if enough of the input item (to allow recipes that consume multiple amounts) }
                    if (stackInInputSlot.getCount() >= GasExtractorRecipes.instance().getInputsAmount(stackInInputSlot, stackInCatalystSlot)[0])
                    {
                        if (stackInInputSlot.getCount() >= GasExtractorRecipes.instance().getInputsAmount(stackInInputSlot, stackInCatalystSlot)[1])
                        {
                            // // DEBUG
                            // System.out.println("There is "+stackInInputSlot.stackSize+" in input slot and "+LabTableRecipes.instance().getInputAmount(stackInInputSlot)+" is needed");
                            return true;
                        } else
                        {
                            return false;
                        }
                    } else // not enough in input stack
                    {
                        // // DEBUG
                        // System.out.println("TileEntityLabTable canCompact() right item but not enough in input slot");
                        return false;
                    }
                } else // no room to output
                {
                    return false;
                }
            }
    }

    /**
     * Turn one item from the labtable source stack into the appropriate compacted item in the labtable result stack.
     */
    public void compactItem()
    {
        if (canCompact())
        {
            Random rand = new Random();
            ItemStack itemstack = GasExtractorRecipes.instance().getResult(extractorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()), extractorItemStacks.get(slotEnum.CATALYST_SLOT.ordinal()));
            ItemStack extra_itemstack = GasExtractorRecipes.instance().getExtraResult(extractorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()), extractorItemStacks.get(slotEnum.CATALYST_SLOT.ordinal()));
            argon++;
            krypton++;

            // check if output slot is empty
            if (extractorItemStacks.get(slotEnum.OUTPUT_SLOT.ordinal()) == ItemStack.EMPTY)
            {
                extractorItemStacks.set(slotEnum.OUTPUT_SLOT.ordinal(), itemstack.copy());
            }
            else if (extractorItemStacks.get(slotEnum.OUTPUT_SLOT.ordinal()).getItem() == itemstack.getItem())
            {
                extractorItemStacks.get(slotEnum.OUTPUT_SLOT.ordinal())
                        .setCount(extractorItemStacks.get(slotEnum.OUTPUT_SLOT.ordinal()).getCount() + itemstack.getCount()); // Forge BugFix: Results may have multiple items
            }

            if (argon >= 20)
            {
                if (extractorItemStacks.get(slotEnum.EXTRA_OUTPUT_SLOT.ordinal()) == ItemStack.EMPTY)
                {
                    extractorItemStacks.set(slotEnum.EXTRA_OUTPUT_SLOT.ordinal(), extra_itemstack.copy());
                    argon = 0;
                }
                else if (extractorItemStacks.get(slotEnum.EXTRA_OUTPUT_SLOT.ordinal()).getItem() == extra_itemstack.getItem())
                {
                    extractorItemStacks.get(slotEnum.EXTRA_OUTPUT_SLOT.ordinal())
                            .setCount(extractorItemStacks.get(slotEnum.EXTRA_OUTPUT_SLOT.ordinal()).getCount() + extra_itemstack.getCount()); // Forge BugFix: Results may have multiple items
                    argon = 0;
                }
            }

            if (krypton >= 120)
                krypton = 100;

            ItemStack tempItemStack = extractorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()).copy();

            // consume the number of input items based on recipe
            try
            {
                if (rand.nextInt(2) == 1)
                    extractorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()).setCount(
                            extractorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()).getCount()
                                    - GasExtractorRecipes.instance().getInputsAmount(extractorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()), extractorItemStacks.get(slotEnum.CATALYST_SLOT.ordinal()))[0]);

                extractorItemStacks.get(slotEnum.CATALYST_SLOT.ordinal()).setCount(
                        extractorItemStacks.get(slotEnum.CATALYST_SLOT.ordinal()).getCount()
                                - GasExtractorRecipes.instance().getInputsAmount(extractorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()), extractorItemStacks.get(slotEnum.CATALYST_SLOT.ordinal()))[0]);
            }
            catch (NullPointerException ignore){}

            if (extractorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()).getCount() <= 0)
            {
                extractorItemStacks.set(slotEnum.INPUT_SLOT.ordinal(), ItemStack.EMPTY);
            }
            if (extractorItemStacks.get(slotEnum.CATALYST_SLOT.ordinal()).getCount() <= 0)
            {
                extractorItemStacks.set(slotEnum.CATALYST_SLOT.ordinal(), ItemStack.EMPTY);
            }
        }
    }
    
    @Override
    public void openInventory(PlayerEntity playerIn)
    {
    }
    
    @Override
    public void closeInventory(PlayerEntity playerIn)
    {
    }
    
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return index == slotEnum.INPUT_SLOT.ordinal(); // can always put things in input (may not compact though) and can't put anything in output
    }
    
    @Override
    public String getGuiID()
    {
        return Reference.MOD_ID + ":analyzer";
    }

    @Override
    public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn)
    {
        // DEBUG
        System.out.println("TileEntityLabTable createContainer()");
        return new GasExtractorContainer(playerInventory, this);
    }
    
    @Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return timeCanCompact;
            case 1:
                return currentItemCompactTime;
            case 2:
                return ticksCompactingItemSoFar;
            case 3:
                return ticksPerItem;
            case 4:
                return argon;
            case 5:
                return redstone;
            case 6:
                return krypton;
            default:
                return 10;
        }
    }

    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                timeCanCompact = value;
                break;
            case 1:
                currentItemCompactTime = value;
                break;
            case 2:
                ticksCompactingItemSoFar = value;
                break;
            case 3:
                ticksPerItem = value;
                break;
            case 4:
                argon = value;
                break;
            case 5:
                redstone = value;
                break;
            case 6:
                krypton = value;
                break;
            default:
                break;
        }
    }

    @Override
    public int getFieldCount()
    {
        return 7;
    }

    @Override
    public void clear()
    {
        extractorItemStacks.clear();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean isUsableByPlayer(PlayerEntity player)
    {
        return true;
    }
}
