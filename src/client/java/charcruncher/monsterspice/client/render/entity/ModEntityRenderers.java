package charcruncher.monsterspice.client.render.entity;

import charcruncher.monsterspice.client.render.entity.effect.PyrosynthesisEffectEntityRenderer;
import charcruncher.monsterspice.entity.ModEntities;
import charcruncher.monsterspice.client.render.entity.mob.UnburntEntityRenderer;
import charcruncher.monsterspice.entity.effect.PyrosynthesisEffectEntity;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import charcruncher.monsterspice.client.render.entity.projectile.EmberArrowEntityRenderer;

@Environment(value = EnvType.CLIENT)
public class ModEntityRenderers {
    public static void registerEntityRenderers() {
        // Register entity renderers here
        EntityRendererRegistry.register(ModEntities.EMBER_ARROW_ENTITY, EmberArrowEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.UNBURNT_ENTITY, UnburntEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.PYROSYNTHESIS_EFFECT_ENTITY, PyrosynthesisEffectEntityRenderer::new);
    }
}
