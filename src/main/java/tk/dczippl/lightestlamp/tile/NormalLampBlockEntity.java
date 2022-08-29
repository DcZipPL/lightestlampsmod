package tk.dczippl.lightestlamp.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import tk.dczippl.lightestlamp.blocks.LightestLampBlock;
import tk.dczippl.lightestlamp.init.ModBlockEntities;
import tk.dczippl.lightestlamp.init.ModBlocks;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.POWERED;

public class NormalLampBlockEntity extends BlockEntity implements BlockEntityTicker<NormalLampBlockEntity> {
    private final LightestLampBlock.Tier _tier;
    private final Block lightBlockType;
    private int cooldown = 0;

    public NormalLampBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
        _tier = ((LightestLampBlock) pBlockState.getBlock()).getTier();
        lightBlockType = ((LightestLampBlock) pBlockState.getBlock()).getType() == LightestLampBlock.Type.NORMAL ? ModBlocks.LIGHT_AIR.get() : ModBlocks.WATERLOGGABLE_LIGHT_AIR.get();
    }
    public NormalLampBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {this(ModBlockEntities.NORMAL_LAMP_BE,pWorldPosition,pBlockState);}

    private static boolean isAir(Level level, BlockPos pos)
    {
        return level.getBlockState(pos).getBlock() == Blocks.AIR || level.getBlockState(pos).getBlock() == Blocks.CAVE_AIR;
    }

    @Override
    public void tick(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull NormalLampBlockEntity pBlockEntity) {
        if (level.isClientSide) return;
        cooldown++;

        if (cooldown == 15) {

            if (_tier.power != 0) {
                if (level.getDirectSignalTo(pPos) > 0) // Redstone
                    cleanLight(pLevel, pPos, pState, _tier.power);
                else {if (pState.getValue(POWERED)) // Set block state to off if no redstone
                    level.setBlockAndUpdate(pPos, pState.setValue(POWERED,false));
                }
            }

            if (_tier == LightestLampBlock.Tier.ALPHA) tickAlphaLamp(pLevel, pPos, pState, lightBlockType);
            else if (_tier == LightestLampBlock.Tier.BETA) {tickBetaLamp(pLevel, pPos, pState, lightBlockType); tickLamp(pLevel, pPos, pState, 1);}
            else if (_tier == LightestLampBlock.Tier.GAMMA) tickLamp(pLevel, pPos, pState, lightBlockType, 2);
            else if (_tier == LightestLampBlock.Tier.DELTA) tickLamp(pLevel, pPos, pState, lightBlockType, 4);
            else if (_tier == LightestLampBlock.Tier.EPSILON) tickLamp(pLevel, pPos, pState, lightBlockType, 5);
            else if (_tier == LightestLampBlock.Tier.ZETA) tickLamp(pLevel, pPos, pState, lightBlockType, 9);
            else if (_tier == LightestLampBlock.Tier.ETA) tickLamp(pLevel, pPos, pState, lightBlockType, 11);
            cooldown = 0;
        }
    }

    private void cleanLight(Level level, BlockPos pos, BlockState state, int power) {
            if (!state.getValue(POWERED))
            {
                level.setBlockAndUpdate(pos, state.setValue(POWERED, true));

                BlockPos.betweenClosed(
                                pos.above(power).north(power).west(power),
                                pos.below(power).south(power).east(power))
                        .forEach((pos1) ->
                        {
                            if (level.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR.get())
                            {
                                level.setBlockAndUpdate(pos1, Blocks.AIR.defaultBlockState());
                            }
                        });
            }
    }

    private static void tickLamp(Level level, BlockPos pos, BlockState state, Block blockToReplace, int power) {
        if (!state.getValue(POWERED))
        {
            BlockPos.betweenClosed(
                            pos.above(power).north(power).west(power),
                            pos.below(power).south(power).east(power))
                    .forEach((pos2) ->
                    {
                        if (isAir(level, pos2))
                        {
                            level.setBlockAndUpdate(pos2, blockToReplace.defaultBlockState());
                        }
                    });
        }
    }

    private static void tickBetaLamp(Level level, BlockPos pos, BlockState state, Block blockToReplace) {
            if (!state.getValue(POWERED))
            {
                BlockPos pos1 = pos.above(2);
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.below(2);
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.north(2);
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.south(2);
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.west(2);
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.east(2);
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }
            }
    }

    private static void tickAlphaLamp(Level level, BlockPos pos, BlockState state, Block blockToReplace) {
            if (!state.getValue(POWERED))
            {
                BlockPos pos1 = pos.above();
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.below();
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.north();
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.south();
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.west();
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.east();
                if (isAir(level, pos1))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }
            }
    }
}
