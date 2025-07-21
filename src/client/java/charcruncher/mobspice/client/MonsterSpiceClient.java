package charcruncher.mobspice.client;

import charcruncher.mobspice.MonsterSpice;
import net.fabricmc.api.ClientModInitializer;

public class MonsterSpiceClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Initialize client-side components here
        ModEntityRenderers.registerEntityRenderers();
        ModBlockRenderers.registerBlockRenderers();
        MonsterSpice.LOGGER.info("MonsterSpice Client Initialized");

    }
}
