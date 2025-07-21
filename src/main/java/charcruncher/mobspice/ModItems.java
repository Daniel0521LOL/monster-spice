package charcruncher.mobspice;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import charcruncher.mobspice.item.EmberArrowItem;

import java.util.function.Function;

public class ModItems {
    public static final Item EMBER = register("ember", Item::new, new Item.Settings().maxCount(64));
    public static final Item EMBER_ARROW = register("ember_arrow", EmberArrowItem::new, new Item.Settings().maxCount(64));

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MonsterSpice.MOD_ID, name));
        Item item = itemFactory.apply(settings);
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> itemGroup.add(ModItems.EMBER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> itemGroup.add(ModItems.EMBER_ARROW));
        FuelRegistry.INSTANCE.add(ModItems.EMBER, 10 * 10 * 20); // 10 items * 10 seconds per item * 20 ticks per second
    }
}
