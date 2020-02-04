package tk.dczippl.lightestlamp.init;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.fluid.BromineFluidBlock;

import java.awt.*;

public class ModFluids
{
    public static final ResourceLocation FLUID_STILL = new ResourceLocation(Reference.MOD_ID,"fluid/bromine_still");
    public static final ResourceLocation FLUID_FLOWING = new ResourceLocation(Reference.MOD_ID,"fluid/bromine_flow");

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Reference.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Reference.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, Reference.MOD_ID);

    public static RegistryObject<FlowingFluid> BROMINE_FLUID = FLUIDS.register("bromine_fluid", () ->
            new ForgeFlowingFluid.Source(ModFluids.test_fluid_properties)
    );
    public static RegistryObject<FlowingFluid> BROMINE_FLUID_FLOWING = FLUIDS.register("bromine_fluid_flowing", () ->
            new ForgeFlowingFluid.Flowing(ModFluids.test_fluid_properties)
    );

    public static RegistryObject<FlowingFluidBlock> BROMINE_FLUID_BLOCK = BLOCKS.register("bromine_fluid_block", () ->
            new BromineFluidBlock(BROMINE_FLUID, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops())
    );
    public static RegistryObject<Item> BROMINE_FLUID_BUCKET = ITEMS.register("bromine_fluid_bucket", () ->
            new BucketItem(BROMINE_FLUID, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC))
    );

    public static final ForgeFlowingFluid.Properties test_fluid_properties =
            new ForgeFlowingFluid.Properties(BROMINE_FLUID, BROMINE_FLUID_FLOWING, FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING).color(new Color(102,16,0).getRGB()).density(31028).temperature(316).viscosity(600))
                    .bucket(BROMINE_FLUID_BUCKET).block(BROMINE_FLUID_BLOCK);
}