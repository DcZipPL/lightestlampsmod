package dev.prefex.lightestlamp.entities;

import dev.prefex.lightestlamp.blocks.LightestLampBlock;
import dev.prefex.lightestlamp.init.ModBlockEntities;
import dev.prefex.lightestlamp.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.POWERED;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class NormalLampBlockEntity extends BlockEntity {
    private final LightestLampBlock.Tier _tier;
    private final LightestLampBlock.Type _type;
    private final Block lightBlockType;
    private final Random rng = new Random();
    private final int rand;
    private int cooldown = 0;

    public NormalLampBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
        _tier = ((LightestLampBlock) pBlockState.getBlock()).getTier();
        _type = ((LightestLampBlock) pBlockState.getBlock()).getType();
        lightBlockType = ((LightestLampBlock) pBlockState.getBlock()).getType() == LightestLampBlock.Type.NORMAL ? ModBlocks.LIGHT_AIR.get() : ModBlocks.WATERLOGGABLE_LIGHT_AIR.get();
        rand = rng.nextInt(300,900);
    }
    public NormalLampBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {this(ModBlockEntities.NORMAL_LAMP_BE.get(),pWorldPosition,pBlockState);}

    private static boolean isAir(Level level, BlockPos pos, LightestLampBlock.Type type)
    {
        if (type == LightestLampBlock.Type.UNDERWATER)
            return level.getBlockState(pos).getBlock() == Blocks.WATER;
        return level.getBlockState(pos).getBlock() == Blocks.AIR || level.getBlockState(pos).getBlock() == Blocks.CAVE_AIR;
    }

    public static <T extends BlockEntity> void tick(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull T pBlockEntity) {
        NormalLampBlockEntity blockEntity = (NormalLampBlockEntity) pBlockEntity;
        if (pLevel.isClientSide) return;
        blockEntity.cooldown++;

        if (blockEntity.cooldown == blockEntity.rand) {

            if (!blockEntity._tier.always_active) {
                if (pLevel.getDirectSignalTo(pPos) > 0) // Redstone
                    cleanLight(pLevel, pPos, pState, blockEntity._tier.power, false);
                else {if (pState.getValue(POWERED)) // Set block state to off if no redstone
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(POWERED,false));
                }
            }

            if (blockEntity._tier == LightestLampBlock.Tier.ALPHA) tickAlphaLamp(pLevel, pPos, pState, blockEntity._type, blockEntity.lightBlockType);
            else if (blockEntity._tier == LightestLampBlock.Tier.BETA) {tickBetaLamp(pLevel, pPos, pState, blockEntity._type, blockEntity.lightBlockType); tickLamp(pLevel, pPos, pState, blockEntity.lightBlockType, blockEntity._type, blockEntity._tier.power);}
            else if (blockEntity._tier.ordinal() > 1) tickLamp(pLevel, pPos, pState, blockEntity.lightBlockType, blockEntity._type, blockEntity._tier.power); // TODO: Implement underwater lamps
            blockEntity.cooldown = 0;
        }
    }

    public static void cleanLight(Level level, BlockPos pos, BlockState state, int power, boolean remove) {
            if (!state.getValue(POWERED))
            {
                if (!remove)
                    level.setBlockAndUpdate(pos, state.setValue(POWERED, true));

                BlockPos.betweenClosed(
                                pos.above(power).north(power).west(power),
                                pos.below(power).south(power).east(power))
                        .forEach((pos1) ->
                        {
                            if (level.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR.get())
                            {
                                level.setBlockAndUpdate(pos1, Blocks.AIR.defaultBlockState());
                            } else if (level.getBlockState(pos1).getBlock() == ModBlocks.WATERLOGGABLE_LIGHT_AIR.get()) {
                                if (level.getBlockState(pos1).getValue(WATERLOGGED)) {
                                    if (level.getBlockState(pos1).getFluidState().isSource())
                                        level.setBlockAndUpdate(pos1, Blocks.WATER.defaultBlockState());
                                } else {
                                    level.setBlockAndUpdate(pos1, Blocks.AIR.defaultBlockState());
                                }
                            }
                        });
            }
    }

    public static void tickLamp(Level level, BlockPos pos, BlockState state, Block blockToReplace, LightestLampBlock.Type type, int power) {
        if (!state.getValue(POWERED))
        {
            BlockPos.betweenClosed(
                            pos.above(power).north(power).west(power),
                            pos.below(power).south(power).east(power))
                    .forEach((pos2) ->
                    {
                        if (isAir(level, pos2, type))
                        {
                            level.setBlockAndUpdate(pos2, blockToReplace.defaultBlockState());
                        }
                    });
        }
    }

    public static void tickBetaLamp(Level level, BlockPos pos, BlockState state, LightestLampBlock.Type type, Block blockToReplace) {
            if (!state.getValue(POWERED))
            {
                BlockPos pos1 = pos.above(2);
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.below(2);
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.north(2);
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.south(2);
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.west(2);
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.east(2);
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }
            }
    }

    public static void tickAlphaLamp(Level level, BlockPos pos, BlockState state, LightestLampBlock.Type type, Block blockToReplace) {
            if (!state.getValue(POWERED))
            {
                BlockPos pos1 = pos.above();
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.below();
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.north();
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.south();
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.west();
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }

                pos1 = pos.east();
                if (isAir(level, pos1, type))
                {
                    level.setBlockAndUpdate(pos1, blockToReplace.defaultBlockState());
                }
            }
    }
}
