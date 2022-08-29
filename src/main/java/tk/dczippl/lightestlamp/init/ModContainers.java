package tk.dczippl.lightestlamp.init;

import net.minecraftforge.registries.ObjectHolder;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeContainer;

public class ModContainers
{
    @SuppressWarnings("unchecked") // dangerous!
    @ObjectHolder(Reference.MOD_ID+":"+"gas_centrifuge_container")
    public static ContainerType<GasCentrifugeContainer> GAS_CENTRIFUGE = (ContainerType<GasCentrifugeContainer>) IForgeContainerType.create(GasCentrifugeContainer::new).setRegistryName("gas_centrifuge_container");
}