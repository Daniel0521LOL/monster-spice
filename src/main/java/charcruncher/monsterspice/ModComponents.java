package charcruncher.monsterspice;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModComponents {

    public static final ComponentType<Integer> WARMTH_LEVEL = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            MonsterSpice.id("warmth_level"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> WARMTH = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            MonsterSpice.id("player_warmth_level"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static void registerComponents() {
        // Register components here
        // Example: ComponentRegistry.register("example_component", ExampleComponent::new);
    }
}
