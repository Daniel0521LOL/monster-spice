package charcruncher.monsterspice.client.render.block;

import charcruncher.monsterspice.block.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ModBlockRenderers {
    public static void registerBlockRenderers() {
        BlockRenderLayerMap.INSTANCE.putBlock(
                ModBlocks.GLOWING_EMBERS,
                RenderLayer.getCutout()
        );
    }
}
