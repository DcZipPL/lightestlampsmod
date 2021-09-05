package tk.dczippl.lightestlamp.machine.craftingtest;

import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import tk.dczippl.lightestlamp.init.ModContainers;
import tk.dczippl.lightestlamp.init.ModTileEntities;
import tk.dczippl.lightestlamp.items.FilterItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WorkbenchTile extends LockableTileEntity implements ISidedInventory, ITickableTileEntity {
	public WorkbenchTile(TileEntityType type)
	{
		super(type);
	}
	public WorkbenchTile()
	{
		super(ModTileEntities.WORKBENCH_TE);
	}

	protected ITextComponent getDefaultName()
	{
		return new TranslationTextComponent("container.workbench");
	}

	protected Container createMenu(int id, PlayerInventory player)
	{
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer(8,8));
		buffer.writeBlockPos(pos);
		return new WorkbenchContainer(ModContainers.WORKBENCH,id, player, this, this.furnaceData, buffer);
	}

	private static final int[] SLOTS_UP = new int[]{0,1};
	private static final int[] SLOTS_DOWN = new int[]{2, 3, 4, 5};
	//private static final int[] SLOTS_HORIZONTAL = new int[]{1};
	protected NonNullList<ItemStack> items = NonNullList.withSize(19, ItemStack.EMPTY);
	private int redstoneMode;
	private int liquidMode;
	public final IIntArray furnaceData = new IIntArray() {
		@Override
		public int get(int index) {
			switch(index) {
				case 0:
					return WorkbenchTile.this.redstoneMode;
				case 1:
					return WorkbenchTile.this.liquidMode;
				default:
					return 0;
			}
		}

		@Override
		public void set(int index, int value) {
			switch(index) {
				case 0:
					WorkbenchTile.this.redstoneMode = value;
					break;
				case 1:
					WorkbenchTile.this.liquidMode = value;
					break;
			}

		}

		public int size() {
			return 2;
		}
	};

	public void setRedstoneMode(int redstoneMode)
	{
		furnaceData.set(0,redstoneMode);
	}

	public int getRedstoneMode()
	{
		return furnaceData.get(0);
	}

	public void setLiquidMode(int liquidMode)
	{
		furnaceData.set(1,liquidMode);
	}

	public int getLiquidMode()
	{
		return furnaceData.get(4);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.items);
		this.redstoneMode = compound.getInt("RedstoneMode");
		this.liquidMode = compound.getInt("LiquidMode");
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("RedstoneMode", this.redstoneMode);
		compound.putInt("LiquidMode", this.liquidMode);
		ItemStackHelper.saveAllItems(compound, this.items);

		return compound;
	}

	@Override
	public void tick()
	{
		if (redstoneMode==1&&world.getRedstonePowerFromNeighbors(pos)>0||redstoneMode==2&&world.getRedstonePowerFromNeighbors(pos)<1)
		{

		}
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
	public int getSizeInventory() {
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
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.items.get(index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.items, index, count);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.items, index);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.items.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.items.set(index, stack);

		if (index == 0 && !flag) {
			this.markDirty();
		}

	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
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
		return true;
	}

	@Override
	public void clear() {
		this.items.clear();
	}

	public void onCrafting(PlayerEntity player) {
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
