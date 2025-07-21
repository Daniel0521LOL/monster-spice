package charcruncher.mobspice.client;

import charcruncher.mobspice.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import charcruncher.mobspice.client.render.entity.projectile.EmberArrowEntityRenderer;

@Environment(value = EnvType.CLIENT)
public class ModEntityRenderers {
    public static void registerEntityRenderers() {
        // Register entity renderers here
        EntityRendererRegistry.register(ModEntities.EMBER_ARROW_ENTITY, EmberArrowEntityRenderer::new);
    }
}
