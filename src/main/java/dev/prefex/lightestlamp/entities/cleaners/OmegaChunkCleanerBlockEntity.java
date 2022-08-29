package dev.prefex.lightestlamp.entities.cleaners;

import dev.prefex.lightestlamp.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import dev.prefex.lightestlamp.init.ModBlockEntities;

public class OmegaChunkCleanerBlockEntity extends BlockEntity
{
    private int cooldown = 0;

    public OmegaChunkCleanerBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }
    public OmegaChunkCleanerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        this(ModBlockEntities.OCC_BE, pWorldPosition, pBlockState);
    }

    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState, BlockEntity pBlockEntity) {
        if (pLevel.isClientSide) return;

        OmegaChunkCleanerBlockEntity blockEntity = (OmegaChunkCleanerBlockEntity) pBlockEntity;

        if (blockEntity.cooldown == 1)
        {
            BlockPos.betweenClosed(pPos.above(15).north( 15).west(15),
                    pPos.above(1).north( 0).west(1)).forEach((pPos2) ->
            {
                if (isAir(pLevel, pPos2))
                {
                    pLevel.setBlockAndUpdate(pPos2, Blocks.AIR.defaultBlockState());
                }
            });
        }
        if (blockEntity.cooldown == 2)
        {
            BlockPos.betweenClosed(pPos.above(15).south(15).west(15),
                    pPos.above(1).south(1).west(0)).forEach((pPos2) ->
            {
                if (isAir(pLevel, pPos2))
                {
                    pLevel.setBlockAndUpdate(pPos2, Blocks.AIR.defaultBlockState());
                }
            });
        }
        if (blockEntity.cooldown == 3)
        {
            BlockPos.betweenClosed(pPos.above(15).north( 15).east(15),
                    pPos.above(1).north( 1).east(0)).forEach((pPos2) ->
            {
                if (isAir(pLevel, pPos2))
                {
                    pLevel.setBlockAndUpdate(pPos2, Blocks.AIR.defaultBlockState());
                }
            });
        }
        if (blockEntity.cooldown == 4)
        {
            BlockPos.betweenClosed(pPos.above(15).south(15).east(15),
                    pPos.above(1).south(0).east(1)).forEach((pPos2) ->
            {
                if (isAir(pLevel, pPos2))
                {
                    pLevel.setBlockAndUpdate(pPos2, Blocks.AIR.defaultBlockState());
                }
            });
        }

        //---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//

        if (blockEntity.cooldown == 5)
        {
            BlockPos.betweenClosed(pPos.below( 15).north( 15).west(15),
                    pPos.north( 1).west(0)).forEach((pPos2) ->
            {
                if (isAir(pLevel, pPos2))
                {
                    pLevel.setBlockAndUpdate(pPos2, Blocks.AIR.defaultBlockState());
                }
            });
        }
        if (blockEntity.cooldown == 6)
        {
            BlockPos.betweenClosed(pPos.below( 15).south(15).west(15),
                    pPos.south(0).west(1)).forEach((pPos2) ->
            {
                if (isAir(pLevel, pPos2))
                {
                    pLevel.setBlockAndUpdate(pPos2, Blocks.AIR.defaultBlockState());
                }
            });
        }
        if (blockEntity.cooldown == 7)
        {
            BlockPos.betweenClosed(pPos.below( 15).north( 15).east(15),
                    pPos.north( 0).east(1)).forEach((pPos2) ->
            {
                if (isAir(pLevel, pPos2))
                {
                    pLevel.setBlockAndUpdate(pPos2, Blocks.AIR.defaultBlockState());
                }
            });
        }
        if (blockEntity.cooldown == 8)
        {
            BlockPos.betweenClosed(pPos.below( 15).south(15).east(15),
                    pPos.south(1).east(0)).forEach((pPos2) ->
            {
                if (isAir(pLevel, pPos2))
                {
                    pLevel.setBlockAndUpdate(pPos2, Blocks.AIR.defaultBlockState());
                }
            });
        }
        if (blockEntity.cooldown == 9)
        {
            BlockPos.betweenClosed(pPos.below( 15),
                    pPos.below( 1)).forEach((pPos2) ->
            {
                if (isAir(pLevel, pPos2))
                {
                    pLevel.setBlockAndUpdate(pPos2, Blocks.AIR.defaultBlockState());
                }
            });
            BlockPos.betweenClosed(pPos.above(15),
                    pPos.above(1)).forEach((pPos2) ->
            {
                if (isAir(pLevel, pPos2))
                {
                    pLevel.setBlockAndUpdate(pPos2, Blocks.AIR.defaultBlockState());
                }
            });
        }
        if (blockEntity.cooldown >= 20)
        {
            pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
            //Recalc Light in nearby chunks

            blockEntity.cooldown = 0;
        }
        blockEntity.cooldown++;
    }

    private static boolean isAir(Level pLevel,BlockPos pPos)
    {
        return pLevel.getBlockState(pPos).getBlock() == Blocks.AIR || pLevel.getBlockState(pPos).getBlock() == Blocks.CAVE_AIR || pLevel.getBlockState(pPos).getBlock() == ModBlocks.LIGHT_AIR.get();
    }
}