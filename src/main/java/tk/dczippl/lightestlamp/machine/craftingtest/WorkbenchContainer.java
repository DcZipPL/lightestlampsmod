package tk.dczippl.lightestlamp.machine.craftingtest;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tk.dczippl.lightestlamp.Config;
import tk.dczippl.lightestlamp.init.ModContainers;

public class WorkbenchContainer extends Container
{
	private final IInventory furnaceInventory;
	private final BlockPos pos;
	public final IIntArray field_217064_e;
	protected final World world;

	protected WorkbenchContainer(ContainerType<?> containerTypeIn, int id, PlayerInventory playerInventoryIn, PacketBuffer buf) {
		this(containerTypeIn, id, playerInventoryIn, new Inventory(19), new IntArray(2),buf);
	}

	protected WorkbenchContainer(ContainerType<?> containerTypeIn, int id, PlayerInventory playerInventoryIn, IInventory furnaceInventoryIn, IIntArray p_i50104_6_, PacketBuffer buf) {
		super(containerTypeIn, id);
		assertInventorySize(furnaceInventoryIn, 19);
		assertIntArraySize(p_i50104_6_, 2);
		this.furnaceInventory = furnaceInventoryIn;
		this.field_217064_e = p_i50104_6_;
		this.world = playerInventoryIn.player.world;
		this.pos = buf.readBlockPos();
		for (int i = 0; i < 9; i++) {
			this.addSlot(new Slot(furnaceInventoryIn, i, 8+(18*i), 79));
		}

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				this.addSlot(new Slot(furnaceInventoryIn, j + i * 3 + 9, 8 + j * 18, 17 + i * 18));
			}
		}

		this.addSlot(new FurnaceResultSlot(playerInventoryIn.player, furnaceInventoryIn, 18, 99, 51));

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 16));
			}
		}

		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142 + 16));
		}

		this.trackIntArray(p_i50104_6_);
	}

	public WorkbenchContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer)
	{
		this(ModContainers.WORKBENCH, i, playerInventory, new Inventory(19), new IntArray(2),packetBuffer);
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

	public void clear() {
		this.furnaceInventory.clear();
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.furnaceInventory.isUsableByPlayer(playerIn);
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 1 && index != 0) {
				if (index >= 3 && index < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean func_217061_l() {
		return this.field_217064_e.get(0) > 0;
	}
}