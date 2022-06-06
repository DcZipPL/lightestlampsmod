package tk.dczippl.lightestlamp.fluid;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import tk.dczippl.lightestlamp.init.ModEffect;

import java.util.Random;

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