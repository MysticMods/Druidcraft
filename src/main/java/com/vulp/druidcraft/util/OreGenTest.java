package com.vulp.druidcraft.util;

import com.mojang.serialization.Codec;
import com.vulp.druidcraft.registry.RuleTestRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class OreGenTest extends RuleTest {
    public static final OreGenTest INSTANCE = new OreGenTest();
    public static final Codec<OreGenTest> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public boolean test(BlockState q, Random rand) {
        if (OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD.test(q, rand) || OreFeatureConfig.FillerBlockType.BASE_STONE_NETHER.test(q, rand)) {
            return true;
        }

        //noinspection ConstantConditions
        if (q != null) {
            return q.isIn(Tags.Blocks.END_STONES);
        }

        return false;
    }

    @Override
    protected IRuleTestType<?> getType() {
        return RuleTestRegistry.ORE_GEN;
    }
}
