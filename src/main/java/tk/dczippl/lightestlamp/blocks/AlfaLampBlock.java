package tk.dczippl.lightestlamp.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.tile.AlfaLampTileEntity;

import javax.annotation.Nullable;
import java.util.List;

public class AlfaLampBlock extends LeavesBlock
{
    public AlfaLampBlock()
    {
        super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).lightValue(15).hardnessAndResistance(0.3f,1));
    }



    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new AlfaLampTileEntity();
    }

    @Override
    public int getHarvestLevel(BlockState p_getHarvestLevel_1_)
    {
        return 0;
    }

    @Override
    public void onPlayerDestroy(IWorld iworld, BlockPos pos, BlockState state)
    {
        World world = iworld.getWorld();
        BlockPos.getAllInBox(pos.offset(Direction.UP, 1).offset(Direction.NORTH,1).offset(Direction.WEST,1), pos.offset(Direction.DOWN, 1).offset(Direction.SOUTH,1).offset(Direction.EAST,1)).forEach((pos1) -> {
            if (world.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR)
            {
                world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }
        });
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag flag)
    {
        text.add(new TranslationTextComponent("tooltip.type.alpha").applyTextStyle(TextFormatting.GRAY));
        text.add(new TranslationTextComponent("tooltip.inverted").applyTextStyle(TextFormatting.GRAY));
    }
}