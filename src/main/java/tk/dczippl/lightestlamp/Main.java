package tk.dczippl.lightestlamp;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
import net.minecraftforge.registries.IForgeRegistry;
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
import java.util.Map;
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
            ItemStack stack = new ItemStack(ModBlocks.OMEGA_LAMP.get());
            return stack;
        }
    };
    
    public Main()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.init(modEventBus);

        ModFluids.ITEMS.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);

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

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("lightestlamp-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("lightestlamp-common.toml"));

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.debug(ModBlocks.ANTI_LAMP.get().toString());
        // some preinit code
        WorldGenerator.setupWorldGeneraton();
        Networking.registerMessages();
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        // do something that can only be done on the client
        ScreenManager.registerFactory(ModContainers.GAS_CENTRIFUGE, GasCentrifugeScreen::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.JUNGLE_LANTERN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GLOWING_GLASS_BLOCK.get(), RenderType.getCutout());
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
    public void onEntityLivingDeath(LivingDeathEvent event)
    {
        if (event.getSource().getDamageType().equals("player"))
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
            BlockPos pos = new BlockPos(entity.getPositionVec());
            Block b = entity.getEntityWorld().getBlockState(pos.offset(Direction.UP)).getBlock();
            Block b1 = entity.getEntityWorld().getBlockState(pos).getBlock();
            if (b.equals(ModFluids.BROMINE_FLUID_BLOCK.get()))
            {
                entity.setMotion(new Vector3d(entity.getMotion().x, 0.100000011620D, entity.getMotion().z));
            } else if (b1.equals(ModFluids.BROMINE_FLUID_BLOCK.get()))
            {
                entity.setMotion(new Vector3d(entity.getMotion().x, 0.100000011620D, entity.getMotion().z));
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

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void setFogDensity(EntityViewRenderEvent.FogDensity fog)
    {
        World w = fog.getInfo().getRenderViewEntity().getEntityWorld();
        BlockPos pos = fog.getInfo().getBlockPos();
        BlockState bs = w.getBlockState(pos);
        Block b = bs.getBlock();
        if(b.equals(ModFluids.BROMINE_FLUID_BLOCK.get()))
        {
            fog.setDensity(1f);
        }
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {

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
        }

        @SubscribeEvent
        public static void registerTE(RegistryEvent.Register<TileEntityType<?>> evt)
        {
            evt.getRegistry().register(TileEntityType.Builder.create(LightAirTileEntity::new,ModBlocks.LIGHT_AIR.get(),ModBlocks.CHUNK_CLEANER.get()).build(null).setRegistryName("light_air_te"));
            evt.getRegistry().register(TileEntityType.Builder.create(AntiLampTileEntity::new,ModBlocks.ANTI_LAMP.get()).build(null).setRegistryName("antilamp_te"));

            TileEntityType<ClearLampTileEntity> clear_te = TileEntityType.Builder.create(ClearLampTileEntity::new,ModBlocks.CLEAR_LAMP.get()).build(null);
            clear_te.setRegistryName(Reference.MOD_ID, "clear_te");
            ModTileEntities.CLEAR_TE = clear_te;
            evt.getRegistry().register(ModTileEntities.CLEAR_TE);

            TileEntityType<AlfaLampTileEntity> type0 = TileEntityType.Builder.create(AlfaLampTileEntity::new,ModBlocks.ALPHA_LAMP.get()).build(null);
            type0.setRegistryName(Reference.MOD_ID, "alfa_te");

            TileEntityType<BetaLampTileEntity> type1 = TileEntityType.Builder.create(BetaLampTileEntity::new,ModBlocks.BETA_LAMP.get()).build(null);
            type1.setRegistryName(Reference.MOD_ID, "beta_te");

            TileEntityType<GammaLampTileEntity> type2 = TileEntityType.Builder.create(GammaLampTileEntity::new,ModBlocks.GAMMA_LAMP.get()).build(null);
            type2.setRegistryName(Reference.MOD_ID, "gamma_te");

            TileEntityType<DeltaLampTileEntity> type3 = TileEntityType.Builder.create(DeltaLampTileEntity::new,ModBlocks.DELTA_LAMP.get()).build(null);
            type3.setRegistryName(Reference.MOD_ID, "delta_te");

            TileEntityType<EpsilonLampTileEntity> type4 = TileEntityType.Builder.create(EpsilonLampTileEntity::new,ModBlocks.EPSILON_LAMP.get()).build(null);
            type4.setRegistryName(Reference.MOD_ID, "epsilon_te");

            TileEntityType<ZetaLampTileEntity> type5 = TileEntityType.Builder.create(ZetaLampTileEntity::new,ModBlocks.ZETA_LAMP.get()).build(null);
            type5.setRegistryName(Reference.MOD_ID, "zeta_te");

            TileEntityType<OmegaLampTileEntity> type6 = TileEntityType.Builder.create(OmegaLampTileEntity::new,ModBlocks.OMEGA_LAMP.get()).build(null);
            type6.setRegistryName(Reference.MOD_ID, "omega_te");

            TileEntityType<DeepSeaLanternTileEntity> type7 = TileEntityType.Builder.create(DeepSeaLanternTileEntity::new,ModBlocks.DEEP_SEA_LANTERN.get()).build(null);
            type7.setRegistryName(Reference.MOD_ID, "deep_sea_lantern_te");

            TileEntityType<OceanLanternTileEntity> type8 = TileEntityType.Builder.create(OceanLanternTileEntity::new,ModBlocks.OCEAN_LANTERN.get()).build(null);
            type8.setRegistryName(Reference.MOD_ID, "ocean_lantern_te");

            TileEntityType<ClearSeaLanternTileEntity> type9 = TileEntityType.Builder.create(ClearSeaLanternTileEntity::new,ModBlocks.CLEAR_SEA_LANTERN.get()).build(null);
            type9.setRegistryName(Reference.MOD_ID, "clear_sea_lantern_te");

            TileEntityType<AlchemicalLampTileEntity> type10 = TileEntityType.Builder.create(AlchemicalLampTileEntity::new,ModBlocks.ALCHEMICAL_LAMP.get()).build(null);
            type10.setRegistryName(Reference.MOD_ID, "alchemical_lamp_te");

            TileEntityType<GasCentrifugeTile> centrifuge_te = TileEntityType.Builder.create(GasCentrifugeTile::new,ModBlocks.GAS_EXTRACTOR.get()).build(null);
            centrifuge_te.setRegistryName(Reference.MOD_ID, "centrifuge_te");

            TileEntityType<EtaLampTileEntity> eta_te = TileEntityType.Builder.create(EtaLampTileEntity::new,ModBlocks.ETA_LAMP.get()).build(null);
            eta_te.setRegistryName(Reference.MOD_ID, "eta_te");
            ModTileEntities.ETA_TE = eta_te;
            evt.getRegistry().register(ModTileEntities.ETA_TE);

            TileEntityType<DeepOceanLanternTileEntity> deep_ocean_lantern_te = TileEntityType.Builder.create(DeepOceanLanternTileEntity::new,ModBlocks.DEEP_OCEAN_LANTERN.get()).build(null);
            deep_ocean_lantern_te.setRegistryName(Reference.MOD_ID, "deep_ocean_lantern_te");
            ModTileEntities.DEEPOCEANLANTERN_TE = deep_ocean_lantern_te;
            evt.getRegistry().register(ModTileEntities.DEEPOCEANLANTERN_TE);

            TileEntityType<AbyssalLanternTileEntity> abyssal_lantern_te = TileEntityType.Builder.create(AbyssalLanternTileEntity::new,ModBlocks.ABYSSAL_LANTERN.get()).build(null);
            abyssal_lantern_te.setRegistryName(Reference.MOD_ID, "abyssal_lantern_te");
            ModTileEntities.ABYSSALLANTERN_TE = abyssal_lantern_te;
            evt.getRegistry().register(ModTileEntities.ABYSSALLANTERN_TE);

            TileEntityType<OmegaChunkCleanerTileEntity> occ_te = TileEntityType.Builder.create(OmegaChunkCleanerTileEntity::new,ModBlocks.OCC.get()).build(null);
            occ_te.setRegistryName(Reference.MOD_ID, "occ_te");
            ModTileEntities.OCC_TE = occ_te;
            evt.getRegistry().register(ModTileEntities.OCC_TE);

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
            IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
            ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
                Item.Properties properties = new Item.Properties();
                if (block!=ModBlocks.LIGHT_AIR.get()&&block!=ModBlocks.WATERLOGGABLE_LIGHT_AIR.get()&&block!=ModFluids.BROMINE_FLUID_BLOCK.get())
                    properties = properties.group(main_group);
                BlockItem blockItem = new BlockItem(block, properties);
                blockItem.setRegistryName(block.getRegistryName());
                registry.register(blockItem);
            });

            // register a new item here
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
            itemRegistryEvent.getRegistry().register(ModItems.LANTHANUM_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.ALCHEMICAL_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.NEON_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.ARGON_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.KRYPTON_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.XENON_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.RADON_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.LANTHANUM_PILE);
            itemRegistryEvent.getRegistry().register(ModItems.LANTHANUM_INGOT);
            itemRegistryEvent.getRegistry().register(ModItems.LANTHANUM_NUGGET);
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
            itemRegistryEvent.getRegistry().register(ModItems.NETHERITE_MESH);
            itemRegistryEvent.getRegistry().register(ModItems.DEBUG_STICK);
            itemRegistryEvent.getRegistry().register(ModItems.GLOWING_DUST_AGGLOMERATIO);
        }
    }

    public static void repelEntitiesInAABBFromPoint(World world, AxisAlignedBB effectBounds, double x, double y, double z, boolean ignore)
    {
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, effectBounds);

        for (Entity ent : list)
        {
            if ((ent instanceof LivingEntity) || (ent instanceof ProjectileEntity))
            {
                if (!ignore && !(ent instanceof IMob || ent instanceof ProjectileEntity))
                {
                    continue;
                }
                else
                {
                    //TODO: In Ground (Pull request to forge)
                    /*if (ent instanceof ArrowEntity && ((ArrowEntity) ent).inGound)
                    {
                        continue;
                    }*/
                    Vector3d p = new Vector3d(x, y, z);
                    Vector3d t = ent.getPositionVec();
                    double distance = p.distanceTo(t) + 0.1D;

                    Vector3d r = new Vector3d(t.x - p.x, t.y - p.y, t.z - p.z);

                    ent.setMotion((r.x / 1.5D / distance+ent.getMotion().x),(r.y / 1.5D / distance+ent.getMotion().y),(r.z / 1.5D / distance+ent.getMotion().z));
                }
            }
        }
    }
}