package charcruncher.monsterspice.item;

import charcruncher.monsterspice.MonsterSpice;
import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static final Item EMBER = register("ember", Item::new, new Item.Settings().maxCount(64));
    public static final Item EMBER_ARROW = register("ember_arrow", EmberArrowItem::new, new Item.Settings().maxCount(64));
    public static final Item EMBERBLOOM_SHIELD = register("emberbloom_shield", settings -> new EmberbloomShieldItem(settings,20, 13, ModItems.EMBER), new FabricShieldItem.Settings()
            .maxDamage(600)
            .fireproof()
    );

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MonsterSpice.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> itemGroup.add(ModItems.EMBER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> itemGroup.add(ModItems.EMBER_ARROW));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> itemGroup.add(ModItems.EMBERBLOOM_SHIELD));
        FuelRegistryEvents.BUILD.register((builder, context) -> builder.add(ModItems.EMBER, 10 * 10 * 20)); // 10 items * 10 seconds per item * 20 ticks per second
        EmberbloomShieldItem.registerShieldBlockCallback();
    }
}
