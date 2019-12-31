package tk.dczippl.lightestlamp.machine.gasextractor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class GasExtractorBlock extends Block
{
    //public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public GasExtractorBlock(Properties properties)
    {
        super(properties.lightValue(4));
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
        return new GasExtractorTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult brtr) {
        if (!world.isRemote() && player instanceof ServerPlayerEntity) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof GasExtractorTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player,(GasExtractorTileEntity) tileEntity, packetBuffer -> packetBuffer.writeBlockPos(pos));
            }
        }
        return true;
    }

    @Nullable
    @Override
    public Direction[] getValidRotations(BlockState state, IBlockReader world, BlockPos pos)
    {
        return new Direction[0];
    }

    /**
     * Change block based on compacting status.
     *
     * @param parIsCompacting
     *            the par is compacting
     * @param parWorld
     *            the par world
     * @param parBlockPos
     *            the par block pos
     */
    public static void changeBlockBasedOnCompactingStatus(boolean parIsCompacting, World parWorld, BlockPos parBlockPos)
    {
    }

    @Override
    public void addInformation(ItemStack p_190948_1_, @Nullable IBlockReader p_190948_2_, List<ITextComponent> text, ITooltipFlag p_190948_4_)
    {
        text.add(new TranslationTextComponent("tooltip.rods_only").applyTextStyle(TextFormatting.GRAY));
    }
}