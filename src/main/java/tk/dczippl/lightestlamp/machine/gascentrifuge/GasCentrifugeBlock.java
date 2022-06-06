package tk.dczippl.lightestlamp.machine.gascentrifuge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkHooks;
import tk.dczippl.lightestlamp.init.ModFluids;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class GasCentrifugeBlock extends ContainerBlock
{
    public GasCentrifugeBlock(Block.Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity createNewBlockEntity(IBlockReader worldIn) {
        return new GasCentrifugeTile();
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
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof GasCentrifugeTile) {
            if (player.getHeldItemMainhand().getItem() == Items.BUCKET)
            {
                ItemStack bucket = player.getHeldItemMainhand();
                if (player.getHeldItemMainhand().getCount() == 1)
                    if (((GasCentrifugeTile) tileentity).getTank().getFluidAmount() >= 1000)
                    {
                        ((GasCentrifugeTile) tileentity).getTank().drain(1000, IFluidHandler.FluidAction.EXECUTE);
                        player.inventory.deleteStack(bucket);
                        player.inventory.addItemStackToInventory(new ItemStack(ModFluids.BROMINE_FLUID_BUCKET.get()));
                    }
            }
            else
            {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileentity, buf -> buf.writeBlockPos(pos));
                player.addStat(Stats.INTERACT_WITH_FURNACE);
            }
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
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof GasCentrifugeTile) {
                ((GasCentrifugeTile)tileentity).setCustomName(stack.getDisplayName());
            }
        }

    }
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof GasCentrifugeTile) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (GasCentrifugeTile)tileentity);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}