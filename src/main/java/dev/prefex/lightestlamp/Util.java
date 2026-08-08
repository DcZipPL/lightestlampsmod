package dev.prefex.lightestlamp;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.registries.Registries;

public class Util
{
    public static final String MOD_ID = "lightestlamp";

    public static TagKey<Item> getCentrifugablesTag() {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, "centrifugable_for_glowing_dust"));
    }

    public static void repelEntitiesInAABBFromPoint(Level world, AABB effectBounds, double x, double y, double z, boolean ignore)
    {
        moveEntitiesInAABFromPoint(world, effectBounds, x, y, z, ignore, 1D);
    }

    public static void attractEntitiesInAABBFromPoint(Level world, AABB effectBounds, double x, double y, double z, boolean ignore)
    {
        moveEntitiesInAABFromPoint(world, effectBounds, x, y, z, ignore, -1D);
    }

    private static void moveEntitiesInAABFromPoint(Level world, AABB effectBounds, double x, double y, double z, boolean ignore, double direction){
        if (ignore) {
            world.getEntitiesOfClass(LivingEntity.class, effectBounds).forEach(entity -> moveEntity(entity, x, y, z, direction));
        } else {
            world.getEntitiesOfClass(Mob.class, effectBounds).forEach(entity -> moveEntity(entity, x, y, z, direction));
        }
        world.getEntitiesOfClass(Projectile.class, effectBounds).forEach(entity -> moveEntity(entity, x, y, z, direction));
    }

    private static void moveEntity(Entity entity, double x, double y, double z, double direction) {
        if (entity instanceof Arrow arrow && arrow.onGround()) {
            return;
        }
        double dx = entity.getX() - x;
        double dy = entity.getY() - y;
        double dz = entity.getZ() - z;
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz) + 0.1D;
        var velocity = entity.getDeltaMovement();
        entity.setDeltaMovement(
                direction * (dx / 1.5D / distance + velocity.x),
                direction * (dy / 1.5D / distance + velocity.y),
                direction * (dz / 1.5D / distance + velocity.z));
    }
}
