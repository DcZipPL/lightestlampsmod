package tk.dczippl.lightestlamp.machine.craftingtest;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

public class WorkbenchBlock extends ContainerBlock {
	public WorkbenchBlock(Properties properties) {
		super(properties);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new WorkbenchTile();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_)
	{
		if (p_225533_2_.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			this.interactWith(p_225533_2_, p_225533_3_, p_225533_4_);
			return ActionResultType.SUCCESS;
		}
	}

	/**
	 * Interface for handling interaction with blocks that impliment AbstractFurnaceBlock. Called in onBlockActivated
	 * inside AbstractFurnaceBlock.
	 */
	protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof WorkbenchTile) {
			NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileentity, buf -> buf.writeBlockPos(pos));
			player.addStat(Stats.INTERACT_WITH_FURNACE);
		}
	}
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof WorkbenchTile) {
				((WorkbenchTile)tileentity).setCustomName(stack.getDisplayName());
			}
		}

	}
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof WorkbenchTile) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (WorkbenchTile)tileentity);
			}
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
