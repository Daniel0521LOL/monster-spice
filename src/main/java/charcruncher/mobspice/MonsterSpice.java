package charcruncher.mobspice;

import net.fabricmc.api.ModInitializer;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class MonsterSpice implements ModInitializer {
    public static final String MOD_ID = "monster-spice";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
    }
}
