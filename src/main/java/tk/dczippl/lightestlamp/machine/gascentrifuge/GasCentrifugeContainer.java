package tk.dczippl.lightestlamp.machine.gascentrifuge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.antlr.runtime.misc.IntArray;
import tk.dczippl.lightestlamp.Config;
import tk.dczippl.lightestlamp.init.ModContainers;

public class GasCentrifugeContainer extends AbstractContainerMenu
{
    private final Container furnaceInventory;
    private final BlockPos pos;
    public final ContainerData field_217064_e;
    protected final Level level;

    protected GasCentrifugeContainer(MenuType<?> containerTypeIn, int id, Inventory playerInventoryIn, PacketBuffer buf) {
        this(containerTypeIn, id, playerInventoryIn, new SimpleContainer(6), new SimpleContainerData(7),buf);
    }

    protected GasCentrifugeContainer(MenuType<?> containerTypeIn, int id, Inventory playerInventoryIn, Container furnaceInventoryIn, ContainerData p_i50104_6_, PacketBuffer buf) {
        super(containerTypeIn, id);
        checkContainerSize(furnaceInventoryIn, 6);
        checkContainerDataCount(p_i50104_6_, 7);
        this.furnaceInventory = furnaceInventoryIn;
        this.field_217064_e = p_i50104_6_;
        this.level = playerInventoryIn.player.level;
        this.pos = buf.readBlockPos();
        this.addSlot(new Slot(furnaceInventoryIn, 0, 16, 35));
        this.addSlot(new Slot(furnaceInventoryIn, 1, 41, 35));
        this.addSlot(new FurnaceResultSlot(playerInventoryIn.player, furnaceInventoryIn, 2, 99, 19));
        this.addSlot(new FurnaceResultSlot(playerInventoryIn.player, furnaceInventoryIn, 3, 127, 19));
        this.addSlot(new FurnaceResultSlot(playerInventoryIn.player, furnaceInventoryIn, 4, 99, 51));
        this.addSlot(new FurnaceResultSlot(playerInventoryIn.player, furnaceInventoryIn, 5, 127, 51));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142));
        }

        this.addDataSlots(p_i50104_6_);
    }

    public GasCentrifugeContainer(int i, Inventory playerInventory, PacketBuffer packetBuffer)
    {
        this(ModContainers.GAS_CENTRIFUGE, i, playerInventory, new SimpleContainer(6), new SimpleContainerData(7),packetBuffer);
    }

    public BlockPos getBlockPos()
    {
        return pos;
    }

    public void func_201771_a(RecipeItemHelper p_201771_1_) {
        if (this.furnaceInventory instanceof IRecipeHelperPopulator) {
            ((IRecipeHelperPopulator)this.furnaceInventory).fillStackedContents(p_201771_1_);
        }

    }

    public void clearContent() {
        this.furnaceInventory.clearContent();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return this.furnaceInventory.stillValid(pPlayer);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex != 1 && pIndex != 0) {
                if (this.canSmelt(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= 3 && pIndex < 30) {
                    if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= 30 && pIndex < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }

        return itemstack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled() {
        int i = this.field_217064_e.get(2);
        int j = this.field_217064_e.get(3);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled()
    {
        int multiplier = Config.GLOWSTONE_FUEL_MULTIPLIER.get() >= 2 ? Config.GLOWSTONE_FUEL_MULTIPLIER.get() : 2;
        return this.field_217064_e.get(0) * 13 / 180 / multiplier;
    }

    @OnlyIn(Dist.CLIENT)
    public int getLiquidScaled()
    {
        return this.field_217064_e.get(5) / 50;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean func_217061_l() {
        return this.field_217064_e.get(0) > 0;
    }
}