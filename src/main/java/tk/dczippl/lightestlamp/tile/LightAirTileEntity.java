package tk.dczippl.lightestlamp.tile;

import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import tk.dczippl.lightestlamp.Main;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class LightAirTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public LightAirTileEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    public LightAirTileEntity()
    {
        super(ModTileEntities.AIR_TE);
    }

    @Override
    public void tick()
    {
        if (world.isRemote) return;
        if (cooldown >= 1)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 18).offset(Direction.NORTH,18).offset(Direction.WEST,18), pos.offset(Direction.DOWN,18).offset(Direction.SOUTH,18).offset(Direction.EAST,18)).forEach((pos1) -> {
                if (world.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR)
                {
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
                }
                else if (world.getBlockState(pos1).getBlock() == ModBlocks.WATERLOGGABLE_LIGHT_AIR && !world.getBlockState(pos1).get(BlockStateProperties.WATERLOGGED))
                {
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
                }
                else if (world.getBlockState(pos1).getBlock() == ModBlocks.WATERLOGGABLE_LIGHT_AIR && world.getBlockState(pos1).get(BlockStateProperties.WATERLOGGED))
                {
                    world.setBlockState(pos1, Blocks.WATER.getDefaultState());
                }
            });
            BlockPos.getAllInBox(pos.offset(Direction.UP, 19).offset(Direction.NORTH,19).offset(Direction.WEST,19), pos.offset(Direction.DOWN,19).offset(Direction.SOUTH,19).offset(Direction.EAST,19)).forEach((pos1) -> {
                if (isAir(pos1))
                    world.notifyBlockUpdate(pos1,world.getBlockState(pos1),world.getBlockState(pos1),3);
            });
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.setTileEntity(pos, null);
            //Recalc Light in nearby chunks
        }
        cooldown++;
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR;
    }
}