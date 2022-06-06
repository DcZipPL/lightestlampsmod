package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import tk.dczippl.lightestlamp.init.ModBlocks;

import javax.annotation.Nullable;

public class ZetaLampBlock extends Block
{
    public ZetaLampBlock()
    {
        super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.3f,1));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }

    @Override
    public boolean hasBlockEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockState state, IBlockReader world)
    {
        return new ZetaLampBlockEntity();
    }

    @Override
    public int getHarvestLevel(BlockState p_getHarvestLevel_1_)
    {
        return 0;
    }

    @Override
    public void onPlayerDestroy(IWorld iworld, BlockPos pos, BlockState state)
    {
        World world = (World) iworld;
        world.setBlockState(pos, ModBlocks.CHUNK_CLEANER.get().getDefaultState());
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        BlockPos.getAllInBox(pos.offset(Direction.UP, 5).offset(Direction.NORTH, 5).offset(Direction.WEST, 5),
                pos.offset(Direction.DOWN, 5).offset(Direction.SOUTH, 5).offset(Direction.EAST, 5)).forEach((pos2) ->
        {
            if (isWAir(pos2,world))
            {
                world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
            }
        });
        /*BlockPos.getAllInBox(pos.offset(Direction.UP, 12).offset(Direction.NORTH,12).offset(Direction.WEST,12), pos.offset(Direction.DOWN,12).offset(Direction.SOUTH,12).offset(Direction.EAST,12)).forEach((pos1) -> {
            if (isWAir(pos1,world))
            {
                world.notifyBlockUpdate(pos1, world.getBlockState(pos1), world.getBlockState(pos1), 3);
            }
        });*/
    }

    private boolean isWAir(BlockPos pos, World world)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR.get();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag flag)
    {
        text.add(new TranslationTextComponent("tooltip.lightestlamp.type.zeta").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.penetration").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.always_active").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
    }
}