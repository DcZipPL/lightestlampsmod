package tk.dczippl.lightestlamp.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.ObjectHolder;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeMenu;

public class ModContainers
{
    @SuppressWarnings("unchecked") // dangerous!
    @ObjectHolder(Reference.MOD_ID+":"+"gas_centrifuge_container")
    public static MenuType<GasCentrifugeMenu> GAS_CENTRIFUGE = (MenuType<GasCentrifugeMenu>) IForgeMenuType.create(GasCentrifugeMenu::new).setRegistryName("gas_centrifuge_container");
}