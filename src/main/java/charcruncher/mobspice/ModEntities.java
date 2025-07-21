package charcruncher.mobspice;

import charcruncher.mobspice.entity.projectile.EmberArrowEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<EmberArrowEntity> EMBER_ARROW_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MonsterSpice.MOD_ID, "ember_arrow_entity"),
            EntityType.Builder.<EmberArrowEntity>create(EmberArrowEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5F, 0.5F)
                    //.maxTrackingRange(4)
                    //.trackingTickInterval(20)
                    .build("ember_arrow_entity")
    );
}
