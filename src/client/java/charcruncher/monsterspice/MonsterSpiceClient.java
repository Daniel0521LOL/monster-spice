package charcruncher.monsterspice;

import charcruncher.monsterspice.client.networking.ModS2CPackets;
import charcruncher.monsterspice.client.render.block.ModBlockRenderers;
import charcruncher.monsterspice.client.render.entity.ModEntityRenderers;
import charcruncher.monsterspice.client.ModKeyBindings;
import charcruncher.monsterspice.client.render.gui.WarmthMeterRenderer;
import net.fabricmc.api.ClientModInitializer;

public class MonsterSpiceClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Initialize client-side components here
        ModEntityRenderers.registerEntityRenderers();
        ModBlockRenderers.registerBlockRenderers();
        ModKeyBindings.registerKeyBindings();
        ModS2CPackets.registerS2CPacketReceivers();
        WarmthMeterRenderer.registerWarmthMeterRenderer();


        MonsterSpice.LOGGER.info("Monster Spice Client Initialized");

    }
}
