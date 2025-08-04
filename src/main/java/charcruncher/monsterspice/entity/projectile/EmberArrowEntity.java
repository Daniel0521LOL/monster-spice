package charcruncher.monsterspice.entity.projectile;

/*
import charcruncher.mobspice.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.EmberArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class EmberArrowEntity extends EmberArrowEntity {

    public EmberArrowEntity(EntityType<? extends EmberArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public EmberArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(world, x, y, z, stack, shotFrom);
    }

    public EmberArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(ModEntities.EMBER_ARROW_ENTITY, world);
        this.setOwner(owner);
        this.setStack(stack.copyWithCount(1)); // Ensure the arrow has a single item stack
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED; // Prevent pickup by default
    }



    @Override
    public void tick() {
        super.tick();
        // Example: Add fire particles along the arrow's path
        if (this.getWorld().isClient && !this.inGround) {
            this.getWorld().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    // This method is called when the arrow hits an entity
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult); // Applies normal arrow damage and physics

        // Get the entity that was hit
        var entity = entityHitResult.getEntity();

        // Set the entity on fire for 5 seconds (100 ticks)
        entity.setOnFireFor(5);

        // Apply 5 extra points of fire damage
        entity.damage(this.getDamageSources().inFire(), 2.0F);
    }

    // This defines what item is dropped when the arrow is picked up
    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(Items.ARROW);
    }
}
*/

import charcruncher.monsterspice.block.ModBlocks;
import charcruncher.monsterspice.entity.ModEntities;
import charcruncher.monsterspice.item.ModItems;
import charcruncher.monsterspice.block.GlowingEmbersBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static net.minecraft.block.MultifaceGrowthBlock.getProperty;

public class EmberArrowEntity extends PersistentProjectileEntity {

    public EmberArrowEntity(EntityType<? extends EmberArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public EmberArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(ModEntities.EMBER_ARROW_ENTITY, x, y, z, world, stack, shotFrom);
    }

    public EmberArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(ModEntities.EMBER_ARROW_ENTITY, owner, world, stack, shotFrom);
    }

    @Override
    protected void setStack(ItemStack stack) {
        super.setStack(stack);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            this.getWorld().addParticle(ParticleTypes.ASH, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void onHit(LivingEntity entity) {
        super.onHit(entity);
        entity.setOnFireFor(5);
        // Apply 2 extra points of fire damage
        entity.damage((ServerWorld) this.getWorld(), this.getDamageSources().inFire(), 2.0F);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

        World world = this.getWorld();
        BlockPos pos = blockHitResult.getBlockPos();
        Direction face = blockHitResult.getSide();

        BlockPos placePos = pos.offset(face);

        if (!world.isClient) {
            ((ServerWorld) world).spawnParticles(
                    ParticleTypes.LAVA,
                    placePos.getX() + 0.5, placePos.getY() + 0.5, placePos.getZ() + 0.5,
                    4, 0.3, 0.3, 0.3, 0.2
            );
        }

        if (Objects.requireNonNull(this.getOwner()).isPlayer()) {
            if (world.getBlockState(placePos).isAir()) {
                // If the gainWarmthOnBlock is air, we can place glowing embers
                BlockState glowing_embers = ModBlocks.GLOWING_EMBERS.getDefaultState()
                        .with(getProperty(face.getOpposite()), true);
                world.setBlockState(placePos, glowing_embers);
            } else if (world.getBlockState(placePos).getBlock() instanceof GlowingEmbersBlock) {
                // If the gainWarmthOnBlock is already glowing embers, we can just update it
                world.setBlockState(placePos, world.getBlockState(placePos).with(getProperty(face.getOpposite()), true));
            }
        }

        this.discard();
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.EMBER_ARROW);
    }

}
