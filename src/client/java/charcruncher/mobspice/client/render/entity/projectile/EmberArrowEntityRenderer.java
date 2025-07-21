package charcruncher.mobspice.client.render.entity.projectile;

import charcruncher.mobspice.MonsterSpice;
import charcruncher.mobspice.entity.projectile.EmberArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class EmberArrowEntityRenderer extends ProjectileEntityRenderer<EmberArrowEntity> {
    public static final Identifier TEXTURE = Identifier.of(MonsterSpice.MOD_ID, "textures/entity/projectiles/ember_arrow.png");


    public EmberArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(EmberArrowEntity emberArrowEntity) {
        return TEXTURE;
    }

}
