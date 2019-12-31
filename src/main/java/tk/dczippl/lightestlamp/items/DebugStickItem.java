package tk.dczippl.lightestlamp.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SimpleFoiledItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.dczippl.lightestlamp.init.ModBlocks;

public class DebugStickItem extends SimpleFoiledItem
{
    public DebugStickItem(Properties properties)
    {
        super(properties.maxStackSize(1));
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        BlockPos.getAllInBox(playerIn.getPosition().offset(Direction.NORTH,8).offset(Direction.WEST,8).offset(Direction.UP,16),
                playerIn.getPosition().offset(Direction.SOUTH,8).offset(Direction.EAST,8).offset(Direction.DOWN,16)).forEach((pos1)->{
                    if (worldIn.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR)
                    {
                        worldIn.setBlockState(pos1, Blocks.AIR.getDefaultState());
                    }
        });
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}