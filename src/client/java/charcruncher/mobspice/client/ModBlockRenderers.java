package charcruncher.mobspice.client;

import charcruncher.mobspice.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.BlockRenderType;
import net.minecraft.client.render.RenderLayer;

public class ModBlockRenderers {
    public static void registerBlockRenderers() {
        BlockRenderLayerMap.INSTANCE.putBlock(
                ModBlocks.GLOWING_EMBERS,
                RenderLayer.getCutout()
        );
    }
}
