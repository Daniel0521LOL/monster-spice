package charcruncher.monsterspice.item;

import charcruncher.monsterspice.MonsterSpice;
import charcruncher.monsterspice.entity.effect.PyrosynthesisEffectEntity;
import charcruncher.monsterspice.logic.WarmthManager;
import com.github.crimsondawn45.fabricshieldlib.lib.event.ShieldBlockCallback;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.EquippableDispenserBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.RepairableComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class EmberbloomShieldItem extends Item implements FabricShield {


    private int coolDownTicks;
    private int gainWarmthCounter = 0;

    // Configuration values; Gradual gains are counted per second, while costs are per use.
    private static final int PYROSYNTHESIS_WARMTH_COST = 120;
    private static final int SPOREBUSTION_WARMTH_COST = 200;
    private static final int SPOREBUSTION_DELAY = 500;
    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(
            false,
            true,
            Optional.of(1.3F),
            Optional.empty()
    );
    private static final float EXPLOSION_POWER = 1.3F;
    private static final int PASSIVE_WARMTH_GAIN = 20;
    private static final int BLOCK_WARMTH_GAIN = 10;
    private static final int HEAT_SOURCE_WARMTH_GAIN = 2;
    private static final int NETHER_WARMTH_GAIN = 1;




    public EmberbloomShieldItem(net.minecraft.item.Item.Settings settings, int coolDownTicks, int enchantability, Item... repairItems) {
        this(settings, coolDownTicks, enchantability, RegistryEntryList.of(Arrays.stream(repairItems).map(Item::getRegistryEntry).collect(Collectors.toList())));
    }

    public EmberbloomShieldItem(net.minecraft.item.Item.Settings settings, int coolDownTicks, int enchantability, @Nullable RegistryEntryList<Item> repairItems) {
        super((repairItems == null ? settings : settings.component(DataComponentTypes.REPAIRABLE, new RepairableComponent(repairItems))).enchantable(enchantability).equippableUnswappable(EquipmentSlot.OFFHAND));

        //Register dispenser equip behavior
        // TODO: This is no longer necessary if this item has DataComponentTypes.EQUIPPABLE
        DispenserBlock.registerBehavior(this, EquippableDispenserBehavior.INSTANCE);

        //Register that item has a blocking model
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            this.RegisterModelPredicate();
        }

        this.coolDownTicks = coolDownTicks;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient() && entity.isPlayer()) {
            gainWarmthCounter += 1;
            if (gainWarmthCounter >= 20) {
                gainWarmthCounter = 0;
                int gain = PASSIVE_WARMTH_GAIN + (world.getDimension().ultrawarm() ? NETHER_WARMTH_GAIN : 0);
                WarmthManager.addWarmth((PlayerEntity) entity, gain);
            }
        }
    }

    public static void registerShieldBlockCallback() {
        ShieldBlockCallback.EVENT.register((defender, source, amount, hand, shield) -> {
            if (shield.getItem() instanceof EmberbloomShieldItem) {
                return ((EmberbloomShieldItem) shield.getItem()).gainWarmthOnBlock(defender, source, amount, hand, shield);
            }
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        });
    }

    // Regen warmth when the shield is used to block.
    public ActionResult gainWarmthOnBlock(LivingEntity defender, DamageSource source, float amount, Hand hand, ItemStack shield) {
        MonsterSpice.LOGGER.info("Blocked damage with Emberbloom Shield.");

        if (defender.isPlayer() && !defender.getWorld().isClient()) {
            PlayerEntity player = (PlayerEntity) defender;
            WarmthManager.addWarmth(player, BLOCK_WARMTH_GAIN);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }

    // PYROSYNTHESIS: Cleanse the user of negative effects and heal them when they release the shield.
    public void doPyrosynthesis(ItemStack stack, World world, LivingEntity user) {
        int warmth = WarmthManager.getWarmth((PlayerEntity) user);

        if (warmth >= PYROSYNTHESIS_WARMTH_COST) {
            // Remove negative effects
            for (var effect : user.getStatusEffects()) {
                if (!effect.getEffectType().value().isBeneficial()) {
                    user.removeStatusEffect(effect.getEffectType());
                }
            }
            // Heal the user
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 2));
            // Reduce warmth level
            WarmthManager.removeWarmth((PlayerEntity) user, PYROSYNTHESIS_WARMTH_COST);
            // Spawn Pyrosynthesis effect entity
            if (!world.isClient()) {
                PyrosynthesisEffectEntity effectEntity = new PyrosynthesisEffectEntity(world);
                effectEntity.setPosition(user.getPos());
                effectEntity.setOwner(user);
                world.spawnEntity(effectEntity);
            }
        }
    }

    // SPOREBUSTION: Create spore explosion that damage nearby entities and apply weakness.
    public void doSporebustion(ItemStack stack, World world, LivingEntity user) {

        int warmth = WarmthManager.getWarmth((PlayerEntity) user);

        if (warmth >= SPOREBUSTION_WARMTH_COST) {
            // Spawns a particle cloud of spores around the user
            ((ServerWorld) world).spawnParticles(
                    ParticleTypes.SPORE_BLOSSOM_AIR,
                    user.getX(),
                    user.getY() + 1.0D,
                    user.getZ(),
                    50, // Number of particles
                    1.0D, 0.5D, 1.0D, // Offset
                    0.05D // Speed
            );

            for (Entity entity : world.getEntitiesByClass(Entity.class, user.getBoundingBox().expand(5.0D), e -> e != user)) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 120, 2));
                }
            }

            try {
                // Wait for 1 second
                sleep(SPOREBUSTION_DELAY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            ((ServerWorld) world).spawnParticles(
                    ParticleTypes.SMALL_FLAME,
                    user.getX(),
                    user.getY() + 1.0D,
                    user.getZ(),
                    30, // Number of particles
                    1.0D, 0.5D, 1.0D, // Offset
                    0.2D // Speed
            );

            // Create spore explosion effect
            world.createExplosion(
                    user,
                    this.getDamageSource(user),
                    EXPLOSION_BEHAVIOR,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    EXPLOSION_POWER,
                    false,
                    World.ExplosionSourceType.MOB,
                    ParticleTypes.SMALL_FLAME,
                    ParticleTypes.SPORE_BLOSSOM_AIR,
                    SoundEvents.ENTITY_GENERIC_EXPLODE
            );

            // Apply weakness to nearby entities and set them on fire
            for (Entity entity : world.getEntitiesByClass(Entity.class, user.getBoundingBox().expand(5.0D), e -> e != user)) {
                if (entity instanceof LivingEntity livingEntity) {
                    entity.setOnFireFor(5);
                }
            }

            WarmthManager.removeWarmth((PlayerEntity) user, SPOREBUSTION_WARMTH_COST);
        }
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (world.isClient()) { return false; }
        if (!user.isPlayer()) { return false; }
        this.doPyrosynthesis(stack, world, user);
        return false;
    }



    private void RegisterModelPredicate() {
//        ModelPredicateProviderRegistry.register(Identifier.of("blocking"), (itemStack, clientWorld, livingEntity, i) -> {
//            return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
//        });
    }

    @Override
    public int getCoolDownTicks(@Nullable ItemStack itemStack) {
        return this.coolDownTicks;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return ActionResult.CONSUME;
    }

    public void setCoolDownTicks(int coolDownTicks) {
        this.coolDownTicks = coolDownTicks;
    }


}
