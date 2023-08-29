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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class Util
{
    public static final String MOD_ID = "lightestlamp";

    public static TagKey<Item> getCentrifugablesTag() {
        var tagManager = ForgeRegistries.ITEMS.tags();
        return tagManager.createTagKey(new ResourceLocation("lightestlamp:centrifugable_for_glowing_dust"));
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
                    if (ent instanceof Arrow && ((Arrow) ent).onGround())
                    {
                        continue;
                    }
                    Vec3 p = new Vec3(x, y, z);
                    Vec3 t = new Vec3(ent.getX(), ent.getY(), ent.getZ());
                    double distance = p.distanceTo(t) + 0.1D;

                    Vec3 r = new Vec3(t.x - p.x, t.y - p.y, t.z - p.z);

                    ent.setDeltaMovement(direction*(r.x / 1.5D / distance+ent.getDeltaMovement().x),direction*(r.y / 1.5D / distance+ent.getDeltaMovement().y),direction*(r.z / 1.5D / distance+ent.getDeltaMovement().z));
                }
            }
        }
    }
}