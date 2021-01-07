package com.vulp.druidcraft.world.config;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

import java.util.List;

public class ElderTreeFeatureConfig implements IFeatureConfig {

    public static final Codec<ElderTreeFeatureConfig> CODEC = RecordCodecBuilder.create((p_236683_0_) -> {
        return p_236683_0_.group(BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter((p_236693_0_) -> {
            return p_236693_0_.trunkProvider;
        }), BlockStateProvider.CODEC.fieldOf("root_provider").forGetter((p_236692_0_) -> {
            return p_236692_0_.rootProvider;
        }), BlockStateProvider.CODEC.fieldOf("leaves_provider").forGetter((p_236692_0_) -> {
            return p_236692_0_.leavesProvider;
        }), BlockStateProvider.CODEC.fieldOf("sapling_provider").forGetter((p_236692_0_) -> {
            return p_236692_0_.saplingProvider;
        }), TreeDecorator.field_236874_c_.listOf().fieldOf("decorators").forGetter((p_236688_0_) -> {
            return p_236688_0_.decorators;
        }), Heightmap.Type.CODEC.fieldOf("heightmap").forGetter((p_236684_0_) -> {
            return p_236684_0_.heightmap;
        })).apply(p_236683_0_, ElderTreeFeatureConfig::new);
    });

    public final BlockStateProvider trunkProvider;
    public final BlockStateProvider rootProvider;
    public final BlockStateProvider leavesProvider;
    public final BlockStateProvider saplingProvider;
    public final List<TreeDecorator> decorators;
    public transient boolean forcePlacement;
    public final Heightmap.Type heightmap;

    protected ElderTreeFeatureConfig(BlockStateProvider trunkProvider, BlockStateProvider rootProvider, BlockStateProvider leavesProvider, BlockStateProvider saplingProvider, List<TreeDecorator> decorators, Heightmap.Type p_i232020_9_) {
        this.trunkProvider = trunkProvider;
        this.rootProvider = rootProvider;
        this.leavesProvider = leavesProvider;
        this.saplingProvider = saplingProvider;
        this.decorators = decorators;
        this.heightmap = p_i232020_9_;
    }

    public void forcePlacement() {
        this.forcePlacement = true;
    }

    public ElderTreeFeatureConfig configureDecorators(List<TreeDecorator> p_236685_1_) {
        return new ElderTreeFeatureConfig(this.trunkProvider, this.rootProvider, this.leavesProvider, this.saplingProvider, p_236685_1_, this.heightmap);
    }

    public static class Builder {
        public final BlockStateProvider trunkProvider;
        public final BlockStateProvider rootProvider;
        public final BlockStateProvider leavesProvider;
        public final BlockStateProvider saplingProvider;

        private List<TreeDecorator> decorators = ImmutableList.of();
        private Heightmap.Type heightmap = Heightmap.Type.OCEAN_FLOOR;

        public Builder(BlockStateProvider trunkProvider, BlockStateProvider rootProvider, BlockStateProvider leavesProvider, BlockStateProvider saplingProvider) {
            this.trunkProvider = trunkProvider;
            this.rootProvider = rootProvider;
            this.leavesProvider = leavesProvider;
            this.saplingProvider = saplingProvider;
        }

        public ElderTreeFeatureConfig.Builder func_236703_a_(List<TreeDecorator> p_236703_1_) {
            this.decorators = p_236703_1_;
            return this;
        }

        public ElderTreeFeatureConfig.Builder setHeightmap(Heightmap.Type heightmap) {
            this.heightmap = heightmap;
            return this;
        }

        public ElderTreeFeatureConfig build() {
            return new ElderTreeFeatureConfig(this.trunkProvider, this.rootProvider, this.leavesProvider, this.saplingProvider, this.decorators, this.heightmap);
        }
    }
}
