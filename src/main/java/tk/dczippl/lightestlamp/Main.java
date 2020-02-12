package tk.dczippl.lightestlamp;

import mekanism.api.MekanismAPI;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasAttributes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.dczippl.lightestlamp.init.*;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeScreen;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeTile;
import tk.dczippl.lightestlamp.tile.*;
import tk.dczippl.lightestlamp.util.WorldGenerator;
import tk.dczippl.lightestlamp.util.network.Networking;

import java.awt.*;
import java.util.List;
import java.util.Random;

@SuppressWarnings("NullableProblems")
@Mod(Reference.MOD_ID)
public class Main
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup main_group = new ItemGroup("lamps") {

        @Override
        public ItemStack createIcon()
        {
            ItemStack stack = new ItemStack(ModItems.NEON_ROD);
            stack.setCount(8);
            return stack;
        }
    };
    
    public Main()
    {
        //Register GUI Handler
        //ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> GuiHandler::getClientGuiElement);

        //ModPacketHandler.registerMessages();
        //NetworkRegistry.newSimpleChannel(new ResourceLocation(Reference.MOD_ID, "lampschannel"), () -> Integer.toString(1), s -> true, s -> true);

        //CapabilityManager.INSTANCE.register(IArgon.class, new ArgonStorage(), ArgonFactory.class);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModFluids.BLOCKS.register(modEventBus);
        ModFluids.ITEMS.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("lightestlamp-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("lightestlamp-common.toml"));

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        // Mekanism
        //if (ModList.get().isLoaded("mekanism"))
        //{
            //DeferredRegister<Gas> GASES = new DeferredRegister<>(MekanismAPI.GAS_REGISTRY, Reference.MOD_ID);
            //RegistryObject<Gas> BROMINE_VAPOUR = GASES.register("bromine_vapour",() -> new Gas(GasAttributes.builder().color(new Color(102,16,0).getRGB())));
            //GASES.register(modEventBus);
            //LOGGER.info("Lightest Lamps: Mekanism is loaded.");
        //}
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        WorldGenerator.setupWorldGeneraton();
        Networking.registerMessages();
        //new RecipeManager().getRecipes().removeIf(p->p.getRecipeOutput().getItem()==ModItems.BORON_INGOT);
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        // do something that can only be done on the client
        ScreenManager.registerFactory(ModContainers.GAS_CENTRIFUGE, GasCentrifugeScreen::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.JUNGLE_LANTERN, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GLOWING_GLASS_BLOCK, RenderType.cutout());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
        // do something when the server starts
    }

    @SubscribeEvent
    public void Drops(LivingDropsEvent event)
    {
        if(event.getEntityLiving() instanceof IMob)
        {
            if (event.getEntityLiving().getEntityWorld().getCurrentMoonPhaseFactor() == 1.0F)
            {
                Random rnd = new Random();
                if (rnd.nextInt(4) == 2)//25% chance
                    event.getEntityLiving().entityDropItem(new ItemStack(ModItems.MOON_SHARD));
            }
        }
    }

    @SubscribeEvent
    public void EntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        LivingEntity entity = event.getEntityLiving();
        boolean disabled = false;
        if (entity instanceof PlayerEntity)
        {
            if (((PlayerEntity)entity).isSpectator())
                disabled = true;
        }
        if (!disabled)
        {
            BlockPos pos = entity.getPosition();
            Block b = entity.getEntityWorld().getBlockState(pos.offset(Direction.UP)).getBlock();
            Block b1 = entity.getEntityWorld().getBlockState(pos).getBlock();
            if (b.equals(ModFluids.BROMINE_FLUID_BLOCK.get()))
            {
                entity.setMotion(new Vec3d(entity.getMotion().x, 0.100000011620D, entity.getMotion().z));
            } else if (b1.equals(ModFluids.BROMINE_FLUID_BLOCK.get()))
            {
                entity.setMotion(new Vec3d(entity.getMotion().x, 0.100000011620D, entity.getMotion().z));
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void setFog(EntityViewRenderEvent.FogColors fog)
    {
        World w = fog.getInfo().getRenderViewEntity().getEntityWorld();
        BlockPos pos = fog.getInfo().getBlockPos();
        BlockState bs = w.getBlockState(pos);
        Block b = bs.getBlock();
        if(b.equals(ModFluids.BROMINE_FLUID_BLOCK.get()))
        {
            float red = 0.2F, green = 0.05F, blue = 0.05F;
            fog.setRed(red); fog.setGreen(green); fog.setBlue(blue);
        }
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // register a new block here
            blockRegistryEvent.getRegistry().register(ModBlocks.ANTI_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.ALCHEMICAL_LAMP);

            blockRegistryEvent.getRegistry().register(ModBlocks.LIGHT_AIR);
            blockRegistryEvent.getRegistry().register(ModBlocks.WATERLOGGABLE_LIGHT_AIR);
            blockRegistryEvent.getRegistry().register(ModBlocks.CLEAR_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.ALFA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.BETA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.GAMMA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.DELTA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.EPSILON_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.ZETA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.ETA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.OMEGA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.CLEAR_SEA_LANTERN);
            blockRegistryEvent.getRegistry().register(ModBlocks.DEEP_SEA_LANTERN);
            blockRegistryEvent.getRegistry().register(ModBlocks.OCEAN_LANTERN);
            blockRegistryEvent.getRegistry().register(ModBlocks.DEEP_OCEAN_LANTERN);
            blockRegistryEvent.getRegistry().register(ModBlocks.ABYSSAL_LANTERN);
            blockRegistryEvent.getRegistry().register(ModBlocks.GLOWING_GLASS_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.DARK_AIR);
            blockRegistryEvent.getRegistry().register(ModBlocks.JUNGLE_LANTERN);
            blockRegistryEvent.getRegistry().register(ModBlocks.GAS_EXTRACTOR);
            blockRegistryEvent.getRegistry().register(ModBlocks.NEON_ROD_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.ARGON_ROD_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.KRYPTON_ROD_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.XENON_ROD_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.RADON_ROD_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.NEON_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.ARGON_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.KRYPTON_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.XENON_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.RADON_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.CHUNK_CLEANER);
            blockRegistryEvent.getRegistry().register(ModBlocks.VANTA_BLACK);
            blockRegistryEvent.getRegistry().register(ModBlocks.BORON_ORE);
            //LOGGER.info("Lightest Lamps: block init");
        }

        @SubscribeEvent
        public static void registerEffects(final RegistryEvent.Register<Effect> event) {
            event.getRegistry().registerAll(
                    ModEffect.BROMINE_POISON
            );
        }

        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> containerTypeRegistryEvent)
        {
            // register a new container here
            containerTypeRegistryEvent.getRegistry().register(ModContainers.GAS_CENTRIFUGE);
            //LOGGER.info("Lightest Lamps: container init");
        }

        @SubscribeEvent
        public static void registerTE(RegistryEvent.Register<TileEntityType<?>> evt)
        {
            //Reference.EXTRACTOR_TE = TileEntityType.register("extractor_te", TileEntityType.Builder.create(GasExtractorTileEntity::new));

            evt.getRegistry().register(TileEntityType.Builder.create(LightAirTileEntity::new,ModBlocks.LIGHT_AIR,ModBlocks.CHUNK_CLEANER).build(null).setRegistryName("light_air_te"));
            evt.getRegistry().register(TileEntityType.Builder.create(AntiLampTileEntity::new,ModBlocks.ANTI_LAMP).build(null).setRegistryName("antilamp_te"));

            TileEntityType<AlfaLampTileEntity> type0 = TileEntityType.Builder.create(AlfaLampTileEntity::new,ModBlocks.ALFA_LAMP).build(null);
            type0.setRegistryName(Reference.MOD_ID, "alfa_te");

            TileEntityType<BetaLampTileEntity> type1 = TileEntityType.Builder.create(BetaLampTileEntity::new,ModBlocks.BETA_LAMP).build(null);
            type1.setRegistryName(Reference.MOD_ID, "beta_te");

            TileEntityType<GammaLampTileEntity> type2 = TileEntityType.Builder.create(GammaLampTileEntity::new,ModBlocks.GAMMA_LAMP).build(null);
            type2.setRegistryName(Reference.MOD_ID, "gamma_te");

            TileEntityType<DeltaLampTileEntity> type3 = TileEntityType.Builder.create(DeltaLampTileEntity::new,ModBlocks.DELTA_LAMP).build(null);
            type3.setRegistryName(Reference.MOD_ID, "delta_te");

            TileEntityType<EpsilonLampTileEntity> type4 = TileEntityType.Builder.create(EpsilonLampTileEntity::new,ModBlocks.EPSILON_LAMP).build(null);
            type4.setRegistryName(Reference.MOD_ID, "epsilon_te");

            TileEntityType<ZetaLampTileEntity> type5 = TileEntityType.Builder.create(ZetaLampTileEntity::new,ModBlocks.ZETA_LAMP).build(null);
            type5.setRegistryName(Reference.MOD_ID, "zeta_te");

            TileEntityType<OmegaLampTileEntity> type6 = TileEntityType.Builder.create(OmegaLampTileEntity::new,ModBlocks.OMEGA_LAMP).build(null);
            type6.setRegistryName(Reference.MOD_ID, "omega_te");

            TileEntityType<DeepSeaLanternTileEntity> type7 = TileEntityType.Builder.create(DeepSeaLanternTileEntity::new,ModBlocks.DEEP_SEA_LANTERN).build(null);
            type7.setRegistryName(Reference.MOD_ID, "deep_sea_lantern_te");

            TileEntityType<OceanLanternTileEntity> type8 = TileEntityType.Builder.create(OceanLanternTileEntity::new,ModBlocks.OCEAN_LANTERN).build(null);
            type8.setRegistryName(Reference.MOD_ID, "ocean_lantern_te");

            TileEntityType<ClearSeaLanternTileEntity> type9 = TileEntityType.Builder.create(ClearSeaLanternTileEntity::new,ModBlocks.CLEAR_SEA_LANTERN).build(null);
            type9.setRegistryName(Reference.MOD_ID, "clear_sea_lantern_te");

            TileEntityType<AlchemicalLampTileEntity> type10 = TileEntityType.Builder.create(AlchemicalLampTileEntity::new,ModBlocks.ALCHEMICAL_LAMP).build(null);
            type10.setRegistryName(Reference.MOD_ID, "alchemical_lamp_te");

            TileEntityType<GasCentrifugeTile> centrifuge_te = TileEntityType.Builder.create(GasCentrifugeTile::new,ModBlocks.GAS_EXTRACTOR).build(null);
            centrifuge_te.setRegistryName(Reference.MOD_ID, "centrifuge_te");

            TileEntityType<EtaLampTileEntity> eta_te = TileEntityType.Builder.create(EtaLampTileEntity::new,ModBlocks.ETA_LAMP).build(null);
            eta_te.setRegistryName(Reference.MOD_ID, "eta_te");
            ModTileEntities.ETA_TE = eta_te;
            evt.getRegistry().register(ModTileEntities.ETA_TE);

            TileEntityType<DeepOceanLanternTileEntity> deep_ocean_lantern_te = TileEntityType.Builder.create(DeepOceanLanternTileEntity::new,ModBlocks.DEEP_OCEAN_LANTERN).build(null);
            deep_ocean_lantern_te.setRegistryName(Reference.MOD_ID, "deep_ocean_lantern_te");
            ModTileEntities.DEEPOCEANLANTERN_TE = deep_ocean_lantern_te;
            evt.getRegistry().register(ModTileEntities.DEEPOCEANLANTERN_TE);

            TileEntityType<AbyssalLanternTileEntity> abyssal_lantern_te = TileEntityType.Builder.create(AbyssalLanternTileEntity::new,ModBlocks.ABYSSAL_LANTERN).build(null);
            abyssal_lantern_te.setRegistryName(Reference.MOD_ID, "abyssal_lantern_te");
            ModTileEntities.ABYSSALLANTERN_TE = abyssal_lantern_te;
            evt.getRegistry().register(ModTileEntities.ABYSSALLANTERN_TE);

            ModTileEntities.ALFA_TE = type0;
            ModTileEntities.BETA_TE = type1;
            ModTileEntities.GAMMA_TE = type2;
            ModTileEntities.DELTA_TE = type3;
            ModTileEntities.EPSILON_TE = type4;
            ModTileEntities.ZETA_TE = type5;
            ModTileEntities.OMEGA_TE = type6;
            ModTileEntities.DEEPSEALANTERN_TE = type7;
            ModTileEntities.OCEANLANTERN_TE = type8;
            ModTileEntities.CLEARSEALANTERN_TE = type9;
            ModTileEntities.ALCHEMICALLAMP_TE = type10;
            ModTileEntities.CENTRIFUGE_TE = centrifuge_te;
            evt.getRegistry().register(ModTileEntities.ALFA_TE);
            evt.getRegistry().register(ModTileEntities.BETA_TE);
            evt.getRegistry().register(ModTileEntities.GAMMA_TE);
            evt.getRegistry().register(ModTileEntities.DELTA_TE);
            evt.getRegistry().register(ModTileEntities.EPSILON_TE);
            evt.getRegistry().register(ModTileEntities.ZETA_TE);
            evt.getRegistry().register(ModTileEntities.OMEGA_TE);
            evt.getRegistry().register(ModTileEntities.DEEPSEALANTERN_TE);
            evt.getRegistry().register(ModTileEntities.OCEANLANTERN_TE);
            evt.getRegistry().register(ModTileEntities.CLEARSEALANTERN_TE);
            evt.getRegistry().register(ModTileEntities.ALCHEMICALLAMP_TE);
            evt.getRegistry().register(ModTileEntities.CENTRIFUGE_TE);
        }

        @SuppressWarnings("ConstantConditions")
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
        {
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.ANTI_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.ANTI_LAMP.getRegistryName()));

            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CLEAR_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.CLEAR_LAMP.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.ALFA_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.ALFA_LAMP.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.BETA_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.BETA_LAMP.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.GAMMA_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.GAMMA_LAMP.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.DELTA_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.DELTA_LAMP.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.EPSILON_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.EPSILON_LAMP.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.ZETA_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.ZETA_LAMP.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.ETA_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.ETA_LAMP.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.OMEGA_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.OMEGA_LAMP.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CLEAR_SEA_LANTERN, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.CLEAR_SEA_LANTERN.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.DEEP_SEA_LANTERN, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.DEEP_SEA_LANTERN.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.OCEAN_LANTERN, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.OCEAN_LANTERN.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.DEEP_OCEAN_LANTERN, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.DEEP_OCEAN_LANTERN.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.ALCHEMICAL_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.ALCHEMICAL_LAMP.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.ABYSSAL_LANTERN, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.ABYSSAL_LANTERN.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.JUNGLE_LANTERN, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.JUNGLE_LANTERN.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.NEON_ROD_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.NEON_ROD_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.ARGON_ROD_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.ARGON_ROD_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.KRYPTON_ROD_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.KRYPTON_ROD_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.XENON_ROD_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.XENON_ROD_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.RADON_ROD_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.RADON_ROD_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.NEON_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.NEON_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.ARGON_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.ARGON_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.KRYPTON_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.KRYPTON_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.XENON_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.XENON_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.RADON_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.RADON_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.GLOWING_GLASS_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.GLOWING_GLASS_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.VANTA_BLACK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.VANTA_BLACK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.GAS_EXTRACTOR, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.GAS_EXTRACTOR.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.BORON_ORE, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.BORON_ORE.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHUNK_CLEANER, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.CHUNK_CLEANER.getRegistryName()));

            // register a new item here

            //ib = new BlockItem(ModBlocks.GAS_EXTRACTOR, new Item.Properties().group(Main.main_group));
            //ib.setRegistryName(ModBlocks.GAS_EXTRACTOR.getRegistryName());
            //itemRegistryEvent.getRegistry().register(ib);

            itemRegistryEvent.getRegistry().register(ModItems.EMPTY_ROD);
            itemRegistryEvent.getRegistry().register(ModItems.NEON_ROD);
            itemRegistryEvent.getRegistry().register(ModItems.ARGON_ROD);
            itemRegistryEvent.getRegistry().register(ModItems.KRYPTON_ROD);
            itemRegistryEvent.getRegistry().register(ModItems.XENON_ROD);
            itemRegistryEvent.getRegistry().register(ModItems.RADON_ROD);
            itemRegistryEvent.getRegistry().register(ModItems.NEON_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.ARGON_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.KRYPTON_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.XENON_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.RADON_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.BORON_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.ALCHEMICAL_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.NEON_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.ARGON_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.KRYPTON_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.XENON_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.RADON_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.BORON_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.BORON_INGOT);
            itemRegistryEvent.getRegistry().register(ModItems.BORON_NUGGET);
            itemRegistryEvent.getRegistry().register(ModItems.CARBON_NANOTUBE);
            itemRegistryEvent.getRegistry().register(ModItems.MOON_SHARD);
            itemRegistryEvent.getRegistry().register(ModItems.CHORUS_FIBER);
            itemRegistryEvent.getRegistry().register(ModItems.BROMINE_CRYSTAL);
            itemRegistryEvent.getRegistry().register(ModItems.STICKANDBOWL);
            itemRegistryEvent.getRegistry().register(ModItems.BASIC_FILTER);
            itemRegistryEvent.getRegistry().register(ModItems.NEON_FILTER);
            itemRegistryEvent.getRegistry().register(ModItems.ARGON_FILTER);
            itemRegistryEvent.getRegistry().register(ModItems.KRYPTON_FILTER);
            itemRegistryEvent.getRegistry().register(ModItems.XENON_FILTER);
            itemRegistryEvent.getRegistry().register(ModItems.RADON_FILTER);
            itemRegistryEvent.getRegistry().register(ModItems.BROMINE_FILTER);
            itemRegistryEvent.getRegistry().register(ModItems.BORON_MESH);
            itemRegistryEvent.getRegistry().register(ModItems.ALCHEMICAL_MESH);
            itemRegistryEvent.getRegistry().register(ModItems.DEBUG_STICK);
            //itemRegistryEvent.getRegistry().register(ModItems.WRITTEN_BOOK);
            //LOGGER.info("Lightest Lamps: item init");
        }
    }

    public static void repelEntitiesInAABBFromPoint(World world, AxisAlignedBB effectBounds, double x, double y, double z, boolean ignore)
    {
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, effectBounds);

        for (Entity ent : list)
        {
            if ((ent instanceof LivingEntity) || (ent instanceof IProjectile))
            {
                if (!ignore && !(ent instanceof IMob || ent instanceof IProjectile))
                {
                    continue;
                }
                else
                {
                    if (ent instanceof ArrowEntity && ((ArrowEntity) ent).onGround)
                    {
                        continue;
                    }
                    Vec3d p = new Vec3d(x, y, z);
                    Vec3d t = ent.getPositionVec();
                    double distance = p.distanceTo(t) + 0.1D;

                    Vec3d r = new Vec3d(t.x - p.x, t.y - p.y, t.z - p.z);

                    ent.setMotion((r.x / 1.5D / distance+ent.getMotion().x),(r.y / 1.5D / distance+ent.getMotion().y),(r.z / 1.5D / distance+ent.getMotion().z));
                }
            }
        }
    }
}