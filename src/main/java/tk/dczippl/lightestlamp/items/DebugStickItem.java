package tk.dczippl.lightestlamp.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SimpleFoiledItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
        /*BlockPos pos = new BlockPos(playerIn.getPositionVec());
        BlockPos.getAllInBox(pos.offset(Direction.NORTH,8).offset(Direction.WEST,8).offset(Direction.UP,16),
                pos.offset(Direction.SOUTH,8).offset(Direction.EAST,8).offset(Direction.DOWN,16)).forEach((pos1)->{
                    if (worldIn.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR.get())
                    {
                        worldIn.setBlockState(pos1, Blocks.AIR.getDefaultState());
                    }
        });*/
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        return super.onItemUseFirst(stack, context);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }
}