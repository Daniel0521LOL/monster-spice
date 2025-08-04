package charcruncher.monsterspice.client.render.entity.projectile;

import charcruncher.monsterspice.MonsterSpice;
import charcruncher.monsterspice.entity.projectile.EmberArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class EmberArrowEntityRenderer extends ProjectileEntityRenderer<EmberArrowEntity, ProjectileEntityRenderState> {
    public static final Identifier TEXTURE = Identifier.of(MonsterSpice.MOD_ID, "textures/entity/projectiles/ember_arrow.png");


    public EmberArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public ProjectileEntityRenderState createRenderState() {
        return new ProjectileEntityRenderState();
    }

    @Override
    protected Identifier getTexture(ProjectileEntityRenderState state) {
        return TEXTURE;
    }

}
