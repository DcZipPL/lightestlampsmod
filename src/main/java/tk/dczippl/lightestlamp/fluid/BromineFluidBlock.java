package tk.dczippl.lightestlamp.fluid;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import tk.dczippl.lightestlamp.init.ModEffect;

import java.util.Random;
import java.util.function.Supplier;

public class BromineFluidBlock extends FlowingFluidBlock
{
    protected BromineFluidBlock(FlowingFluid fluidIn, Properties builder)
    {
        super(fluidIn, builder);
    }

    /**
     * @param supplier    A fluid supplier such as {@link RegistryObject <Fluid>}
     * @param properties
     */
    public BromineFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties)
    {
        super(supplier, properties);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
    {
        worldIn.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.offset(Direction.NORTH, 4).offset(Direction.WEST, 4).offset(Direction.UP, 4),
                pos.offset(Direction.SOUTH, 4).offset(Direction.EAST, 4).offset(Direction.DOWN, 4))).forEach(entity ->
        {
            if (entity instanceof LivingEntity)
                ((LivingEntity) entity).addPotionEffect(new EffectInstance(ModEffect.BROMINE_POISON, 80, 0));
        });
        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public boolean ticksRandomly(BlockState state)
    {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Vec3d getFogColor(BlockState state, IWorldReader world, BlockPos pos, Entity entity, Vec3d originalColor, float partialTicks)
    {
        return new Vec3d(0.6F, 0.1F, 0.0F);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
    {
        if (entityIn instanceof LivingEntity)
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(ModEffect.BROMINE_POISON, 80, 2));
    }
}