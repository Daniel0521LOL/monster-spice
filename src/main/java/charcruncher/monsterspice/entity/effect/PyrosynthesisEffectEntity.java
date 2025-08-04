package charcruncher.monsterspice.entity.effect;

import charcruncher.monsterspice.MonsterSpice;
import charcruncher.monsterspice.entity.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class PyrosynthesisEffectEntity extends Entity {
    private final int MAX_LIFETIME = 26; // Maximum lifetime of the effect in ticks
    private int lifetime = 0;
    private Entity owner;

    public PyrosynthesisEffectEntity(EntityType<PyrosynthesisEffectEntity> type, World world) {
        super(type, world);
        this.noClip = true; // Prevents the entity from being affected by gravity or collisions
        this.setNoGravity(true); // Ensures the entity does not fall
    }

    public PyrosynthesisEffectEntity(World world) {
        this(ModEntities.PYROSYNTHESIS_EFFECT_ENTITY, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.owner != null && !this.owner.isRemoved()) {
            this.setPosition(this.owner.getPos());
        }

        lifetime++;
        if (lifetime >= MAX_LIFETIME) {
            this.discard(); // Remove the entity after its lifetime expires
        }
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (owner != null) {
            nbt.putUuid("OwnerId", owner.getUuid());
        }
        nbt.putInt("Lifetime", lifetime);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.containsUuid("OwnerId") && this.getWorld() instanceof ServerWorld) {
            UUID ownerId = nbt.getUuid("OwnerId");
            Entity entity = ((ServerWorld)this.getWorld()).getEntity(ownerId);
            if (entity != null) {
                this.owner = entity;
            }
        }
        this.lifetime = nbt.getInt("Lifetime");
    }
}
