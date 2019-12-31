package tk.dczippl.lightestlamp.machine.gasextractor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tk.dczippl.lightestlamp.util.slot.ExtractorOutputSlot;

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class GasExtractorContainer extends Container
{
    private final GasExtractorTileEntity tileGasExtractor;
    private final int sizeInventory;
    private int ticksCompactingItemSoFar;
    private int ticksPerItem;
    private int timeCanCompact;
    private int argon;
    private int krypton;
    private int redstone;
    
    public GasExtractorContainer(PlayerInventory parInventoryPlayer, GasExtractorTileEntity te)
    {
        // DEBUG
        System.out.println("LabTableContainer constructor()");

        tileGasExtractor = te;
        sizeInventory = tileGasExtractor.getSizeInventory();
        // DEBUG
        System.out.println("LabTable inventory size = " + sizeInventory);

        this.addSlot(new Slot(tileGasExtractor, GasExtractorTileEntity.slotEnum.CATALYST_SLOT.ordinal(), 26, 35));
        this.addSlot(new Slot(tileGasExtractor, GasExtractorTileEntity.slotEnum.INPUT_SLOT.ordinal(), 51, 35));
        addSlot(new ExtractorOutputSlot(parInventoryPlayer.player, tileGasExtractor, GasExtractorTileEntity.slotEnum.OUTPUT_SLOT.ordinal(), 111, 19));
        addSlot(new ExtractorOutputSlot(parInventoryPlayer.player, tileGasExtractor, GasExtractorTileEntity.slotEnum.EXTRA_OUTPUT_SLOT.ordinal(), 111, 51));

        // add player inventory slots
        // note that the slot numbers are within the player inventory so can be same as the tile entity inventory
        // DEBUG
        System.out.println("Adding player inventory slots to LabTable container");
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(parInventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // add hotbar slots
        // DEBUG
        System.out.println("Adding hotbar slots to LabTable container");
        for (i = 0; i < 9; ++i)
        {
            this.addSlot(new Slot(parInventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    /**
     * Add the given Listener to the list of Listeners. Method name is for legacy.
     *
     * @param listener
     *            the listener
     */
    @Override
    public void addListener(IContainerListener listener)
    {
        // DEBUG
        System.out.println("Adding listener to LabTable container = " + listener);
        super.addListener(listener);
        // DEBUG
        System.out.println("Sending all window properties listener");
        listener.sendAllWindowProperties(this, tileGasExtractor);
        // DEBUG
        System.out.println("Finished adding listener to LabTable container");
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener iContainerListener = this.listeners.get(i);

            if (ticksCompactingItemSoFar != tileGasExtractor.getField(2))
            {
                iContainerListener.sendWindowProperty(this, 2, this.tileGasExtractor.getField(2));
            }

            if (timeCanCompact != tileGasExtractor.getField(0))
            {
                iContainerListener.sendWindowProperty(this, 2, this.tileGasExtractor.getField(0));
            }

            if (ticksPerItem != tileGasExtractor.getField(3))
            {
                iContainerListener.sendWindowProperty(this, 2, this.tileGasExtractor.getField(3));
            }
            if (argon != tileGasExtractor.getField(4))
            {
                iContainerListener.sendWindowProperty(this, 4, this.tileGasExtractor.getField(4));
            }
            if (redstone != tileGasExtractor.getField(5))
            {
                iContainerListener.sendWindowProperty(this, 5, this.tileGasExtractor.getField(5));
            }
            if (krypton != tileGasExtractor.getField(6))
            {
                iContainerListener.sendWindowProperty(this, 6, this.tileGasExtractor.getField(6));
            }
        }

        ticksCompactingItemSoFar = tileGasExtractor.getField(2); // tick compacting item so far
        timeCanCompact = tileGasExtractor.getField(0); // time can compact
        ticksPerItem = tileGasExtractor.getField(3); // ticks per item
        argon = tileGasExtractor.getField(4);// argon amount
        redstone = tileGasExtractor.getField(5);// redstone value
        krypton = tileGasExtractor.getField(6);// redstone value
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        tileGasExtractor.setField(id, data);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return tileGasExtractor.isUsableByPlayer(playerIn);
    }

    /**
     * Take a stack from the specified inventory slot.
     *
     * @param playerIn
     *            the player in
     * @param slotIndex
     *            the slot index
     * @return the item stack
     */
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int slotIndex)
    {
        ItemStack itemStack1 = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack2 = slot.getStack();
            itemStack1 = itemStack2.copy();

            if (slotIndex == GasExtractorTileEntity.slotEnum.OUTPUT_SLOT.ordinal())
            {
                /*if (!mergeItemStack(itemStack2, sizeInventory, sizeInventory + 36, true))
                {
                    return ItemStack.EMPTY;
                }*/

                slot.onSlotChange(itemStack2, itemStack1);
            }
            else if (slotIndex != GasExtractorTileEntity.slotEnum.INPUT_SLOT.ordinal())
            {
                // check if there is a compacting recipe for the stack
                /*if (LabTableRecipes.instance().getResult(itemStack2) != ItemStack.EMPTY) //TODO CHEK THIS SECTION!
                {
                    if (!mergeItemStack(itemStack2, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (slotIndex >= sizeInventory && slotIndex < sizeInventory + 27) // player inventory slots
                {
                    if (!mergeItemStack(itemStack2, sizeInventory + 27, sizeInventory + 35, false)) //FIXED
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (slotIndex >= sizeInventory + 27 && slotIndex < sizeInventory + 36
                        && !mergeItemStack(itemStack2, sizeInventory + 1, sizeInventory + 28, false)) // hotbar slots
                {
                    return ItemStack.EMPTY;
                }*/
            }
            else if (!mergeItemStack(itemStack2, sizeInventory, sizeInventory + 36, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemStack2.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemStack2.getCount() == itemStack1.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemStack2);
        }

        return itemStack1;
    }
}
