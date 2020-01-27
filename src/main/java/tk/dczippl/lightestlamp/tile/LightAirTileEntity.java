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
            BlockPos.getAllInBox(pos.offset(Direction.UP, 13).offset(Direction.NORTH,13).offset(Direction.WEST,13), pos.offset(Direction.DOWN,13).offset(Direction.SOUTH,13).offset(Direction.EAST,13)).forEach((pos1) -> {
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
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.setTileEntity(pos, null);
            //Recalc Light in nearby chunks
        }
        cooldown++;
    }
}