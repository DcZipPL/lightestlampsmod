package dev.prefex.lightestlamp.machine.gascentrifuge;

import dev.prefex.lightestlamp.Config;
import dev.prefex.lightestlamp.init.ModMiscs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GasCentrifugeMenu extends AbstractContainerMenu
{
    private final Container furnaceInventory;
    private final BlockPos pos;
    public final ContainerData data;
    protected final Level level;

    public static final Logger LOGGER = LoggerFactory.getLogger(GasCentrifugeMenu.class);

    protected GasCentrifugeMenu(MenuType<?> containerTypeIn, int id, Inventory playerInventoryIn, Container furnaceInventoryIn, ContainerData data, @NotNull FriendlyByteBuf buf) {
        super(containerTypeIn, id);
        checkContainerSize(furnaceInventoryIn, 6);
        checkContainerDataCount(data, 7);
        this.furnaceInventory = furnaceInventoryIn;
        this.data = data;
        this.level = playerInventoryIn.player.getCommandSenderWorld();
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

        this.addDataSlots(data);
    }

    public GasCentrifugeMenu(int i, Inventory playerInventory, @NotNull FriendlyByteBuf packetBuffer)
    {
        this(ModMiscs.GAS_CENTRIFUGE.get(), i, playerInventory, new SimpleContainer(6), new SimpleContainerData(7),packetBuffer);
    }

    public BlockPos getBlockPos()
    {
        return pos;
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
    public @NotNull ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack copiedStack = ItemStack.EMPTY;
        Slot sourceSlot = this.slots.get(slotIndex);
        if (sourceSlot != null && sourceSlot.hasItem()) {
            ItemStack sourceStack = sourceSlot.getItem();
            copiedStack = sourceStack.copy();
            if (slotIndex >= 2 && slotIndex < 6) {
                if (!this.moveItemStackTo(sourceStack, 6, 42, true)) {
                    return ItemStack.EMPTY;
                }

                sourceSlot.onQuickCraft(sourceStack, copiedStack);
            } else if (slotIndex != 1 && slotIndex != 0) {
                if (this.isFilter(sourceStack)) {
                    if (!this.moveItemStackTo(sourceStack, 0, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 6 && slotIndex < 33) {
                    if (!this.moveItemStackTo(sourceStack, 33, 42, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 33 && slotIndex < 42 && !this.moveItemStackTo(sourceStack, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(sourceStack, 6, 42, false)) {
                return ItemStack.EMPTY;
            }

            if (sourceStack.isEmpty()) {
                sourceSlot.set(ItemStack.EMPTY);
            } else {
                sourceSlot.setChanged();
            }

            if (sourceStack.getCount() == copiedStack.getCount()) {
                return ItemStack.EMPTY;
            }

            sourceSlot.onTake(player, sourceStack);
        }

        return copiedStack;
    }

    protected boolean isFilter(ItemStack itemStack) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled() {
        int i = this.data.get(2);
        int j = this.data.get(3);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled()
    {
        int multiplier = Config.GLOWSTONE_FUEL_MULTIPLIER.get() >= 2 ? Config.GLOWSTONE_FUEL_MULTIPLIER.get() : 2;
        return this.data.get(0) * 13 / 180 / multiplier;
    }

    @OnlyIn(Dist.CLIENT)
    public int getLiquidScaled()
    {
        return (this.data.get(5) * 50 / (1600*GasCentrifugeBlockEntity.magic)) - 1;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean func_217061_l() {
        return this.data.get(0) > 0;
    }
}