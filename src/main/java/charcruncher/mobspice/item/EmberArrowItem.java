package charcruncher.mobspice.item;

import charcruncher.mobspice.ModEntities;
import charcruncher.mobspice.entity.projectile.EmberArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EmberArrowItem extends ArrowItem {
    public EmberArrowItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        // Instantiate your custom EmberArrowEntity here.
        // You'll need to pass your custom EntityType and the shooter/world.
        // Make sure YourModEntities.EMBER_ARROW_ENTITY_TYPE is properly registered.
        // EmberArrowEntity emberArrowEntity = new EmberArrowEntity(ModEntities.EMBER_ARROW_ENTITY, world);

        // Set the shooter for the arrow entity.
        // This is important for damage attribution and other projectile behaviors.
        // emberArrowEntity.setOwner(shooter);

        // You might want to set properties based on the stack or shooter if needed,
        // though PersistentProjectileEntity handles most defaults (like crit, damage multiplier).
        // For example, if you wanted to change base damage:
        // emberArrowEntity.setDamage(2.5D);

        // return emberArrowEntity;
        return new EmberArrowEntity(world, shooter, stack.copyWithCount(1), shotFrom);
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        EmberArrowEntity emberArrowEntity = new EmberArrowEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1), null);
        emberArrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        return emberArrowEntity;
    }
}