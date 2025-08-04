package charcruncher.monsterspice.entity;

import charcruncher.monsterspice.MonsterSpice;
import charcruncher.monsterspice.entity.effect.PyrosynthesisEffectEntity;
import charcruncher.monsterspice.entity.mob.UnburntEntity;
import charcruncher.monsterspice.entity.projectile.EmberArrowEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<EmberArrowEntity> EMBER_ARROW_ENTITY = register(
            "ember_arrow_entity",
            EntityType.Builder.<EmberArrowEntity>create(EmberArrowEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5F, 0.5F)
    );

    public static final EntityType<UnburntEntity> UNBURNT_ENTITY = register(
            "unburnt",
            EntityType.Builder.<UnburntEntity>create(UnburntEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 1.99f)
                    .maxTrackingRange(8)
                    .trackingTickInterval(3)
    );

    public static final EntityType<PyrosynthesisEffectEntity> PYROSYNTHESIS_EFFECT_ENTITY = register(
            "pyrosynthesis_effect_entity",
            EntityType.Builder.<PyrosynthesisEffectEntity>create(PyrosynthesisEffectEntity::new, SpawnGroup.MISC)
                    .dimensions(1.5F, 0.01F)
    );

    public static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        Identifier identifier = MonsterSpice.id(name);
        return Registry.register(Registries.ENTITY_TYPE, identifier, builder.build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, identifier)));
    }

    public static void registerModEntities() {
        MonsterSpice.LOGGER.info("Registering ModEntities for " + MonsterSpice.MOD_ID);
        FabricDefaultAttributeRegistry.register(UNBURNT_ENTITY, UnburntEntity.createAbstractSkeletonAttributes());
    }
}
