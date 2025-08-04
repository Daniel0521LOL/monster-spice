package charcruncher.monsterspice.block;

import charcruncher.monsterspice.MonsterSpice;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {

    public static final Block GLOWING_EMBERS = register("glowing_embers", GlowingEmbersBlock::new,
            AbstractBlock.Settings.create()
                    .luminance(state -> 13)
                    .strength(0.5F, 0.5F)
                    .noCollision()
                    .pistonBehavior(PistonBehavior.DESTROY)
    );

    public static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings) {
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MonsterSpice.MOD_ID, name));
        Block block = blockFactory.apply(settings.registryKey(blockKey));
        Registry.register(Registries.BLOCK, blockKey, block);
        return block;
    }

    public static void registerModBlocks() {
        MonsterSpice.LOGGER.info("Registering ModBlocks for " + MonsterSpice.MOD_ID);
    }
}
