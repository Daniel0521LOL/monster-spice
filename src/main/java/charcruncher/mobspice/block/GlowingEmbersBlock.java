package charcruncher.mobspice.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.LichenGrower;
import net.minecraft.block.MultifaceGrowthBlock;

public class GlowingEmbersBlock extends MultifaceGrowthBlock {
    public static final MapCodec<GlowingEmbersBlock> CODEC = createCodec(GlowingEmbersBlock::new);
    private final LichenGrower grower = new LichenGrower(this);

    public GlowingEmbersBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends MultifaceGrowthBlock> getCodec() {
        return CODEC;
    }

    @Override
    public LichenGrower getGrower() {
        return this.grower;
    }
}
