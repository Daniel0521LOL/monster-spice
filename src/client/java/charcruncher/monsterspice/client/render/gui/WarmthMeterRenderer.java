package charcruncher.monsterspice.client.render.gui;

import charcruncher.monsterspice.MonsterSpice;
import charcruncher.monsterspice.item.ModItems;
import charcruncher.monsterspice.logic.IEntityDataReadWrite;
import charcruncher.monsterspice.logic.WarmthManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class WarmthMeterRenderer {
    private static final Identifier WARMTH_METER_TEXTURE = MonsterSpice.id("textures/gui/warmth_meter.png");
    private static final Identifier WARMTH_METER_LAYER = MonsterSpice.id("warmth_meter_layer");

    public static void registerWarmthMeterRenderer() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> {
            layeredDrawer.attachLayerBefore(IdentifiedLayer.HOTBAR_AND_BARS, WARMTH_METER_LAYER, WarmthMeterRenderer::renderWarmthMeter);
        });
    }

    public static void renderWarmthMeter(DrawContext drawContext, RenderTickCounter tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null) return;

        PlayerEntity player = client.player;
        if (player == null) return;

        // Only render the HUD if the player is holding the shield
        if (!player.isHolding(ModItems.EMBERBLOOM_SHIELD)) {
            return;
        }

        // Position of the meter on screen (top-left corner of the texture)
        int x = drawContext.getScaledWindowWidth() / 2 - 120;
        int y = drawContext.getScaledWindowHeight() - 40;

        int warmth = WarmthManager.getWarmth(player);
//        MonsterSpice.LOGGER.info("Current warmth level: {}", warmth);
        int maxWarmth = WarmthManager.MAX_WARMTH;

        int warmthStage = (int) ((float) warmth / maxWarmth * 5); // Calculate the stage based on warmth level

        drawContext.drawTexture(
                RenderLayer::getGuiTextured,
                WARMTH_METER_TEXTURE,
                x, y, warmthStage * 16, 0, 16, 16,
                16 * 6, 16
        );
    }
}
