package charcruncher.monsterspice.entity.mob;

import charcruncher.monsterspice.entity.projectile.EmberArrowEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class UnburntEntity extends StrayEntity {
    public UnburntEntity(EntityType<? extends UnburntEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createUnburntAttributes() {
        return AbstractSkeletonEntity.createAbstractSkeletonAttributes().add(EntityAttributes.MAX_HEALTH, 35.0);
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }


    @Override
    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        // Override to create a custom arrow projectile if needed
        return new EmberArrowEntity(this.getWorld(), this, arrow, shotFrom);
    }
}
