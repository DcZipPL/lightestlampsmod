package dev.prefex.lightestlamp;

import dev.prefex.lightestlamp.init.ModBlocks;
import dev.prefex.lightestlamp.init.ModContainers;
import dev.prefex.lightestlamp.init.ModItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.dczippl.lightestlamp.init.*;
import dev.prefex.lightestlamp.util.network.Networking;

import java.util.List;

import static dev.prefex.lightestlamp.Reference.MOD_ID;

@SuppressWarnings("NullableProblems")
@Mod(MOD_ID)
public class Main
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final CreativeModeTab main_tab = new CreativeModeTab("lamps") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.OMEGA_LAMP.get());
        }
    };
    
    public Main()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.init(modEventBus);
        ModItems.init(modEventBus);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
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
        Networking.registerMessages();
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        // do something that can only be done on the client
        //ScreenManager.registerFactory(ModContainers.GAS_CENTRIFUGE, GasCentrifugeScreen::new); // TODO: Register screen
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.JUNGLE_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GLOWING_GLASS_BLOCK.get(), RenderType.cutout());
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<MenuType<?>> containerTypeRegistryEvent)
        {
            // register a new container here
            containerTypeRegistryEvent.getRegistry().register(ModContainers.GAS_CENTRIFUGE);
        }

        @SuppressWarnings("ConstantConditions")
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
        {
            // Register Block items
            IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
            ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
                Item.Properties properties = new Item.Properties();
                if (block!=ModBlocks.LIGHT_AIR.get()&&block!=ModBlocks.WATERLOGGABLE_LIGHT_AIR.get())
                    properties = properties.tab(main_tab);
                BlockItem blockItem = new BlockItem(block, properties);
                blockItem.setRegistryName(block.getRegistryName());
                registry.register(blockItem);
            });
        }
    }

    public static void repelEntitiesInAABBFromPoint(Level world, AABB effectBounds, double x, double y, double z, boolean ignore)
    {
        List<Entity> list = world.getEntitiesOfClass(Entity.class, effectBounds);

        for (Entity ent : list)
        {
            if ((ent instanceof LivingEntity) || (ent instanceof Projectile))
            {
                if (!ignore && !(ent instanceof Mob || ent instanceof Projectile))
                {
                    continue;
                }
                else
                {
                    /*if (ent instanceof ArrowEntity && ((ArrowEntity) ent).inGound)
                    {
                        continue;
                    }*/
                    Vec3 p = new Vec3(x, y, z);
                    Vec3 t = ent.getPosition(0);
                    double distance = p.distanceTo(t) + 0.1D;

                    Vec3 r = new Vec3(t.x - p.x, t.y - p.y, t.z - p.z);

                    ent.moveTo((r.x / 1.5D / distance+ent.getDeltaMovement().x),(r.y / 1.5D / distance+ent.getDeltaMovement().y),(r.z / 1.5D / distance+ent.getDeltaMovement().z));
                }
            }
        }
    }
}