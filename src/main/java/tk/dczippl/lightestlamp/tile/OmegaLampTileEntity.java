package tk.dczippl.lightestlamp.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModBlockEntities;

import java.util.Random;

public class OmegaLampTileEntity extends BlockEntity implements BlockEntityTicker<OmegaLampTileEntity>
{
	private int cooldown = 0;

	public OmegaLampTileEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
		super(pType, pWorldPosition, pBlockState);
	}

	public OmegaLampTileEntity(BlockPos pWorldPosition, BlockState pBlockState) {this(ModBlockEntities.OMEGA_BE, pWorldPosition, pBlockState);}

	private boolean isAir(BlockPos pPos)
	{
		return level.getBlockState(pPos).getBlock() == Blocks.AIR || level.getBlockState(pPos).getBlock() == Blocks.CAVE_AIR;
	}

	@Override
	public void tick(Level level, BlockPos pPos, BlockState pState, OmegaLampTileEntity pBlockEntity) {
		if (level.isClientSide) return;

		cooldown++;

		Random random = new Random();

		if (cooldown == 20)
		{
			BlockPos.betweenClosed(pPos.relative(Direction.UP, 15).relative(Direction.NORTH, 15).relative(Direction.WEST, 15),
					pPos.relative(Direction.UP, 1).relative(Direction.NORTH, 0).relative(Direction.WEST, 1)).forEach((pPos2) ->
			{
				if (isAir(pPos2)&&level.getBlockState(pPos2.above()).getBlock() != Blocks.VINE)
				{
					if(random.nextInt(20)==5)
						level.setBlockAndUpdate(pPos2, ModBlocks.LIGHT_AIR.get().defaultBlockState());
				}
			});
		}
		if (cooldown == 30)
		{
			BlockPos.betweenClosed(pPos.relative(Direction.UP, 15).relative(Direction.SOUTH, 15).relative(Direction.WEST, 15),
					pPos.relative(Direction.UP, 1).relative(Direction.SOUTH, 1).relative(Direction.WEST, 0)).forEach((pPos2) ->
			{
				if (isAir(pPos2)&&level.getBlockState(pPos2.above()).getBlock() != Blocks.VINE)
				{
					if(random.nextInt(20)==5)
						level.setBlockAndUpdate(pPos2, ModBlocks.LIGHT_AIR.get().defaultBlockState());
				}
			});
		}
		if (cooldown == 40)
		{
			BlockPos.betweenClosed(pPos.relative(Direction.UP, 15).relative(Direction.NORTH, 15).relative(Direction.EAST, 15),
					pPos.relative(Direction.UP, 1).relative(Direction.NORTH, 1).relative(Direction.EAST, 0)).forEach((pPos2) ->
			{
				if (isAir(pPos2)&&level.getBlockState(pPos2.above()).getBlock() != Blocks.VINE)
				{
					if(random.nextInt(20)==5)
						level.setBlockAndUpdate(pPos2, ModBlocks.LIGHT_AIR.get().defaultBlockState());
				}
			});
		}
		if (cooldown == 50)
		{
			BlockPos.betweenClosed(pPos.relative(Direction.UP, 15).relative(Direction.SOUTH, 15).relative(Direction.EAST, 15),
					pPos.relative(Direction.UP, 1).relative(Direction.SOUTH, 0).relative(Direction.EAST, 1)).forEach((pPos2) ->
			{
				if (isAir(pPos2)&&level.getBlockState(pPos2.above()).getBlock() != Blocks.VINE)
				{
					if(random.nextInt(20)==5)
						level.setBlockAndUpdate(pPos2, ModBlocks.LIGHT_AIR.get().defaultBlockState());
				}
			});
		}

		//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//

		if (cooldown == 60)
		{
			BlockPos.betweenClosed(pPos.relative(Direction.DOWN, 15).relative(Direction.NORTH, 15).relative(Direction.WEST, 15),
					pPos.relative(Direction.NORTH, 1).relative(Direction.WEST, 0)).forEach((pPos2) ->
			{
				if (isAir(pPos2)&&level.getBlockState(pPos2.above()).getBlock() != Blocks.VINE)
				{
					if(random.nextInt(20)==5)
						level.setBlockAndUpdate(pPos2, ModBlocks.LIGHT_AIR.get().defaultBlockState());
				}
			});
		}
		if (cooldown == 70)
		{
			BlockPos.betweenClosed(pPos.relative(Direction.DOWN, 15).relative(Direction.SOUTH, 15).relative(Direction.WEST, 15),
					pPos.relative(Direction.SOUTH, 0).relative(Direction.WEST, 1)).forEach((pPos2) ->
			{
				if (isAir(pPos2)&&level.getBlockState(pPos2.above()).getBlock() != Blocks.VINE)
				{
					if(random.nextInt(20)==5)
						level.setBlockAndUpdate(pPos2, ModBlocks.LIGHT_AIR.get().defaultBlockState());
				}
			});
		}
		if (cooldown == 80)
		{
			BlockPos.betweenClosed(pPos.relative(Direction.DOWN, 15).relative(Direction.NORTH, 15).relative(Direction.EAST, 15),
					pPos.relative(Direction.NORTH, 0).relative(Direction.EAST, 1)).forEach((pPos2) ->
			{
				if (isAir(pPos2)&&level.getBlockState(pPos2.above()).getBlock() != Blocks.VINE)
				{
					if(random.nextInt(20)==5)
						level.setBlockAndUpdate(pPos2, ModBlocks.LIGHT_AIR.get().defaultBlockState());
				}
			});
		}
		if (cooldown == 90)
		{
			BlockPos.betweenClosed(pPos.relative(Direction.DOWN, 15).relative(Direction.SOUTH, 15).relative(Direction.EAST, 15),
					pPos.relative(Direction.SOUTH, 1).relative(Direction.EAST, 0)).forEach((pPos2) ->
			{
				if (isAir(pPos2)&&level.getBlockState(pPos2.above()).getBlock() != Blocks.VINE)
				{
					if(random.nextInt(20)==5)
						level.setBlockAndUpdate(pPos2, ModBlocks.LIGHT_AIR.get().defaultBlockState());
				}
			});
		}
		if (cooldown == 100)
		{
			BlockPos.betweenClosed(pPos.relative(Direction.DOWN, 15),
					pPos.relative(Direction.DOWN, 1)).forEach((pPos2) ->
			{
				if (isAir(pPos2)&&level.getBlockState(pPos2.above()).getBlock() != Blocks.VINE)
				{
					if(random.nextInt(20)==5)
						level.setBlockAndUpdate(pPos2, ModBlocks.LIGHT_AIR.get().defaultBlockState());
				}
			});
			BlockPos.betweenClosed(pPos.relative(Direction.UP, 15),
					pPos.relative(Direction.UP, 1)).forEach((pPos2) ->
			{
				if (isAir(pPos2)&&level.getBlockState(pPos2.above()).getBlock() != Blocks.VINE)
				{
					if(random.nextInt(20)==5)
						level.setBlockAndUpdate(pPos2, ModBlocks.LIGHT_AIR.get().defaultBlockState());
				}
			});

			cooldown = 0;
		}
	}
}