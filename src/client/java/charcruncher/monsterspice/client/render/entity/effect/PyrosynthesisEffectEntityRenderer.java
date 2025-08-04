package charcruncher.monsterspice.client.render.entity.effect;

import charcruncher.monsterspice.MonsterSpice;
import charcruncher.monsterspice.entity.effect.PyrosynthesisEffectEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class PyrosynthesisEffectEntityRenderer extends EntityRenderer<PyrosynthesisEffectEntity, EntityRenderState> {
    private static final Identifier TEXTURE = MonsterSpice.id("textures/entity/effect/pyrosynthesis_effect.png");
    private static final int ANIMATION_FRAMES = 13;
    private static final int TICKS_PER_FRAME = 2;

    public PyrosynthesisEffectEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void render(EntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        // 计算动画帧
        int frame = ((int) state.age / TICKS_PER_FRAME) % ANIMATION_FRAMES;
        float u1 = frame * (1.0f / ANIMATION_FRAMES);
        float u2 = (frame + 1) * (1.0f / ANIMATION_FRAMES);

        // 将特效稍微抬高一点，避免z-fighting
        matrices.translate(0, 0.01, 0);

        // 获取顶点消费者
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(TEXTURE));
        org.joml.Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
        org.joml.Matrix3f normalMatrix = matrices.peek().getNormalMatrix();

        // 渲染一个水平的方形，使用世界坐标系
        float halfSize = 1.5F / 2.0F;

        // 渲染四个顶点，确保法线朝上
        vertexConsumer
                .vertex(positionMatrix, -halfSize, 0, -halfSize)
                .color(255, 255, 255, 255)
                .texture(u1, 1.0F)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(matrices.peek(), 0.0F, 1.0F, 0.0F);

        vertexConsumer
                .vertex(positionMatrix, -halfSize, 0, halfSize)
                .color(255, 255, 255, 255)
                .texture(u1, 0.0F)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(matrices.peek(), 0.0F, 1.0F, 0.0F);

        vertexConsumer
                .vertex(positionMatrix, halfSize, 0, halfSize)
                .color(255, 255, 255, 255)
                .texture(u2, 0.0F)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(matrices.peek(), 0.0F, 1.0F, 0.0F);
        vertexConsumer
                .vertex(positionMatrix, halfSize, 0, -halfSize)
                .color(255, 255, 255, 255)
                .texture(u2, 1.0F)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(matrices.peek(), 0.0F, 1.0F, 0.0F);

        matrices.pop();
    }

}
