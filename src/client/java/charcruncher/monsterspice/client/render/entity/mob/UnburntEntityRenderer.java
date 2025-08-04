package charcruncher.monsterspice.client.render.entity.mob;

import charcruncher.monsterspice.MonsterSpice;
import charcruncher.monsterspice.entity.mob.UnburntEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.AbstractSkeletonEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.client.render.entity.state.SkeletonEntityRenderState;
import net.minecraft.util.Identifier;

public class UnburntEntityRenderer extends AbstractSkeletonEntityRenderer<UnburntEntity, SkeletonEntityRenderState> {
    private static final Identifier TEXTURE = Identifier.of(MonsterSpice.MOD_ID, "textures/entity/mob/unburnt.png");
    private static final Identifier OVERLAY_TEXTURE = Identifier.of(MonsterSpice.MOD_ID, "textures/entity/mob/unburnt_overlay.png");

    public UnburntEntityRenderer(EntityRendererFactory.Context context) {
        super(context, EntityModelLayers.SKELETON, EntityModelLayers.SKELETON_INNER_ARMOR, EntityModelLayers.SKELETON_OUTER_ARMOR);
        this.addFeature(new EyesFeatureRenderer<>(this) {
            @Override
            public RenderLayer getEyesTexture() {
                return RenderLayer.getEyes(OVERLAY_TEXTURE);
            }
        });
    }

    @Override
    public SkeletonEntityRenderState createRenderState() {
        return new SkeletonEntityRenderState();
    }

    @Override
    public Identifier getTexture(SkeletonEntityRenderState skeletonEntityRenderState) {
        return TEXTURE;
    }
}
