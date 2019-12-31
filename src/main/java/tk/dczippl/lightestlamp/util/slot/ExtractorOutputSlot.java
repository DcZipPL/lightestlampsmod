package tk.dczippl.lightestlamp.util.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ExtractorOutputSlot extends OutputSlot
{
    public ExtractorOutputSlot(PlayerEntity parPlayer, IInventory parIInventory, int parSlotIndex, int parXDisplayPosition, int parYDisplayPosition)
    {
        super(parPlayer, parIInventory, parSlotIndex, parXDisplayPosition, parYDisplayPosition);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     *
     * @param parItemStack
     *            the par item stack
     */
    @Override
    protected void onCrafting(ItemStack parItemStack)
    {
        if (!thePlayer.world.isRemote)
        {
            int expEarned = getNumOutput();
            float expFactor = 0.0F;

            if (expFactor == 0.0F)
            {
                expEarned = 0;
            }
            else if (expFactor < 1.0F)
            {
                int possibleExpEarned = MathHelper.floor(expEarned * expFactor);

                if (possibleExpEarned < MathHelper.ceil(expEarned * expFactor) && Math.random() < expEarned * expFactor - possibleExpEarned)
                {
                    ++possibleExpEarned;
                }

                expEarned = possibleExpEarned;
            }

            // create experience orbs
            int expInOrb;
            while (expEarned > 0)
            {
                //expInOrb = EntityXPOrb.getXPSplit(expEarned);
                //expEarned -= expInOrb;
                //thePlayer.world.spawnEntity(new EntityXPOrb(thePlayer.world, thePlayer.posX, thePlayer.posY + 0.5D, thePlayer.posZ + 0.5D, expInOrb));
            }
        }

        setNumOutput(0);
    }
}