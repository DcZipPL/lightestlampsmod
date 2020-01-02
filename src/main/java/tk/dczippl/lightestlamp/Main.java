package tk.dczippl.lightestlamp;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.GenericEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.dczippl.lightestlamp.blocks.AntiLampBlock;
import tk.dczippl.lightestlamp.blocks.LightAirBlock;
import tk.dczippl.lightestlamp.blocks.OmegaLampBlock;
import tk.dczippl.lightestlamp.datagen.DataGenerators;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModItems;
import tk.dczippl.lightestlamp.init.ModTileEntities;
import tk.dczippl.lightestlamp.tile.*;
import tk.dczippl.lightestlamp.util.WorldGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

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


        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("lightestlamp-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("lightestlamp-common.toml"));

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        WorldGenerator.setupWorldGeneraton();
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        // do something that can only be done on the client
        RenderTypeLookup.setRenderLayer(ModBlocks.JUNGLE_LANTERN, RenderType.func_228643_e_());
        RenderTypeLookup.setRenderLayer(ModBlocks.GLOWING_GLASS_BLOCK, RenderType.func_228643_e_());
        RenderTypeLookup.setRenderLayer(ModBlocks.OMEGA_LAMP, RenderType.func_228657_l_());
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
        LOGGER.info("HELLO from server starting");
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

            blockRegistryEvent.getRegistry().register(ModBlocks.LIGHT_AIR);
            blockRegistryEvent.getRegistry().register(ModBlocks.CLEAR_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.ALFA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.BETA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.GAMMA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.DELTA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.EPSILON_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.ZETA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.OMEGA_LAMP);
            blockRegistryEvent.getRegistry().register(ModBlocks.GLOWING_GLASS_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.DARK_AIR);
            blockRegistryEvent.getRegistry().register(ModBlocks.JUNGLE_LANTERN);
            //blockRegistryEvent.getRegistry().register(ModBlocks.GAS_EXTRACTOR);
            blockRegistryEvent.getRegistry().register(ModBlocks.NEON_ROD_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.ARGON_ROD_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.KRYPTON_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.NEON_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.ARGON_BLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.CHUNK_CLEANER);
            blockRegistryEvent.getRegistry().register(ModBlocks.VANTA_BLACK);

            //AUTO-GENERATE CODE |GC-00|
            //END OF AUTO-GENERATE CODE |GC-01|

            LOGGER.info("HELLO from Register Block");
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

            ModTileEntities.ALFA_TE = type0;
            ModTileEntities.BETA_TE = type1;
            ModTileEntities.GAMMA_TE = type2;
            ModTileEntities.DELTA_TE = type3;
            ModTileEntities.EPSILON_TE = type4;
            ModTileEntities.ZETA_TE = type5;
            ModTileEntities.OMEGA_TE = type6;
            evt.getRegistry().register(ModTileEntities.ALFA_TE);
            evt.getRegistry().register(ModTileEntities.BETA_TE);
            evt.getRegistry().register(ModTileEntities.GAMMA_TE);
            evt.getRegistry().register(ModTileEntities.DELTA_TE);
            evt.getRegistry().register(ModTileEntities.EPSILON_TE);
            evt.getRegistry().register(ModTileEntities.ZETA_TE);
            evt.getRegistry().register(ModTileEntities.OMEGA_TE);
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
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.OMEGA_LAMP, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.OMEGA_LAMP.getRegistryName()));

            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.JUNGLE_LANTERN, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.JUNGLE_LANTERN.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.NEON_ROD_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.NEON_ROD_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.ARGON_ROD_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.ARGON_ROD_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.NEON_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.NEON_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.ARGON_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.ARGON_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.KRYPTON_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.KRYPTON_BLOCK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.CHUNK_CLEANER, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.CHUNK_CLEANER.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.VANTA_BLACK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.VANTA_BLACK.getRegistryName()));
            itemRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.GLOWING_GLASS_BLOCK, new Item.Properties().group(Main.main_group)).setRegistryName(ModBlocks.GLOWING_GLASS_BLOCK.getRegistryName()));

            // register a new item here

            //ib = new BlockItem(ModBlocks.GAS_EXTRACTOR, new Item.Properties().group(Main.main_group));
            //ib.setRegistryName(ModBlocks.GAS_EXTRACTOR.getRegistryName());
            //itemRegistryEvent.getRegistry().register(ib);

            //AUTO-GENERATE CODE |GC-02|
            //END OF AUTO-GENERATE CODE |GC-03|

            itemRegistryEvent.getRegistry().register(ModItems.EMPTY_ROD);
            itemRegistryEvent.getRegistry().register(ModItems.ARGON_ROD);
            itemRegistryEvent.getRegistry().register(ModItems.KRYPTON_ROD);
            itemRegistryEvent.getRegistry().register(ModItems.NEON_ROD);
            itemRegistryEvent.getRegistry().register(ModItems.ARGON_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.KRYPTON_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.KRYPTON_SMALL_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.NEON_DUST);
            itemRegistryEvent.getRegistry().register(ModItems.CARBON_NANOTUBE);
            //itemRegistryEvent.getRegistry().register(ModItems.WRITTEN_BOOK);
            itemRegistryEvent.getRegistry().register(ModItems.DEBUG_STICK);
            LOGGER.info("HELLO from Register Item");
        }
    }
}