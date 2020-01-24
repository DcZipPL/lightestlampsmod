package tk.dczippl.lightestlamp.util;

import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;

public class TheoreticalFluid
{
    public TheoreticalFluid(GasCentrifugeRecipe.TheoreticalFluidTypes type, int amount)
    {
        this.type = type;
        this.amount = amount;
    }
    public GasCentrifugeRecipe.TheoreticalFluidTypes type;
    public int amount;
}