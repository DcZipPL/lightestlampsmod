package dev.prefex.lightestlamp.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class StickAndBowlItem extends Item
{
    public StickAndBowlItem(Properties properties)
    {
        super(properties.durability(10));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack stack = itemStack.copy();
        if (stack.getDamageValue() != stack.getMaxDamage())
            stack.setDamageValue(stack.getDamageValue()+1);
        else stack = new ItemStack(Items.BOWL);
        return stack;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }
}
