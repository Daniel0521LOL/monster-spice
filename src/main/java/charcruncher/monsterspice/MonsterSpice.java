package charcruncher.monsterspice;

import charcruncher.monsterspice.block.ModBlocks;
import charcruncher.monsterspice.entity.ModEntities;
import charcruncher.monsterspice.item.ModItems;
import charcruncher.monsterspice.networking.ModPackets;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import org.slf4j.Logger; // Changed from java.util.logging.Logger to org.slf4j.Logger (Fabric's preferred logging)
import org.slf4j.LoggerFactory;

public class MonsterSpice implements ModInitializer {
    public static final String MOD_ID = "monster-spice";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ModItems.registerModItems();

        ModBlocks.registerModBlocks();

        ModEntities.registerModEntities();

        ModPackets.registerModPayloadTypes();
        ModPackets.registerC2SPacketReceivers();

        ModComponents.registerComponents();

    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
