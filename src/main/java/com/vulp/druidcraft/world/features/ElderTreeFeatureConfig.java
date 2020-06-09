package com.vulp.druidcraft.world.features;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraftforge.common.IPlantable;

import java.util.List;

public class ElderTreeFeatureConfig extends BaseTreeFeatureConfig {
    public final BlockStateProvider baseProvider;
    public final BlockStateProvider trunkProvider;
    public final BlockStateProvider leavesProvider;
    public final List<TreeDecorator> decorators;
    public final int baseHeight;
    public transient boolean forcePlacement;
    protected IPlantable sapling = (IPlantable) BlockRegistry.elder_sapling;

    protected ElderTreeFeatureConfig(BlockStateProvider baseProviderIn, BlockStateProvider trunkProviderIn, BlockStateProvider leavesProviderIn, List<TreeDecorator> decoratorsIn, int baseHeightIn) {
        super(baseProviderIn, trunkProviderIn, decoratorsIn, baseHeightIn);
        this.baseProvider = baseProviderIn;
        this.trunkProvider = trunkProviderIn;
        this.leavesProvider = leavesProviderIn;
        this.decorators = decoratorsIn;
        this.baseHeight = baseHeightIn;
    }

    public void forcePlacement() {
        this.forcePlacement = true;
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        ImmutableMap.Builder<T, T> builder = ImmutableMap.builder();
        builder.put(ops.createString("base_provider"), this.baseProvider.serialize(ops)).put(ops.createString("trunk_provider"), this.trunkProvider.serialize(ops)).put(ops.createString("leaves_provider"), this.leavesProvider.serialize(ops)).put(ops.createString("decorators"), ops.createList(this.decorators.stream().map((p_227375_1_) -> {
            return p_227375_1_.serialize(ops);
        }))).put(ops.createString("base_height"), ops.createInt(this.baseHeight));
        return new Dynamic<>(ops, ops.createMap(builder.build()));
    }

    protected ElderTreeFeatureConfig setSapling(net.minecraftforge.common.IPlantable value) {
        this.sapling = value;
        return this;
    }

    public net.minecraftforge.common.IPlantable getSapling() {
        return this.sapling;
    }

    public static <T> ElderTreeFeatureConfig deserialize(Dynamic<T> data) {
        BlockStateProviderType<?> blockstateprovidertype = Registry.BLOCK_STATE_PROVIDER_TYPE.getOrDefault(new ResourceLocation(data.get("base_provider").get("type").asString().orElseThrow(RuntimeException::new)));
        BlockStateProviderType<?> blockstateprovidertype1 = Registry.BLOCK_STATE_PROVIDER_TYPE.getOrDefault(new ResourceLocation(data.get("trunk_provider").get("type").asString().orElseThrow(RuntimeException::new)));
        BlockStateProviderType<?> blockstateprovidertype2 = Registry.BLOCK_STATE_PROVIDER_TYPE.getOrDefault(new ResourceLocation(data.get("leaves_provider").get("type").asString().orElseThrow(RuntimeException::new)));
        return new ElderTreeFeatureConfig(blockstateprovidertype.func_227399_a_(data.get("base_provider").orElseEmptyMap()), blockstateprovidertype1.func_227399_a_(data.get("trunk_provider").orElseEmptyMap()), blockstateprovidertype2.func_227399_a_(data.get("leaves_provider").orElseEmptyMap()), data.get("decorators").asList((p_227374_0_) -> {
            return Registry.TREE_DECORATOR_TYPE.getOrDefault(new ResourceLocation(p_227374_0_.get("type").asString().orElseThrow(RuntimeException::new))).func_227431_a_(p_227374_0_);
        }), data.get("base_height").asInt(0));
    }

    public static class Builder {
        public final BlockStateProvider baseProvider;
        public final BlockStateProvider trunkProvider;
        public final BlockStateProvider leavesProvider;
        private List<TreeDecorator> decorators = Lists.newArrayList();
        private int baseHeight = 0;
        protected net.minecraftforge.common.IPlantable sapling = (net.minecraftforge.common.IPlantable)net.minecraft.block.Blocks.OAK_SAPLING;

        public Builder(BlockStateProvider baseProviderIn, BlockStateProvider trunkProviderIn, BlockStateProvider leavesProviderIn) {
            this.baseProvider = baseProviderIn;
            this.trunkProvider = trunkProviderIn;
            this.leavesProvider = leavesProviderIn;
        }

        public ElderTreeFeatureConfig.Builder baseHeight(int baseHeightIn) {
            this.baseHeight = baseHeightIn;
            return this;
        }

        public ElderTreeFeatureConfig.Builder setSapling(net.minecraftforge.common.IPlantable value) {
            this.sapling = value;
            return this;
        }

        public ElderTreeFeatureConfig build() {
            return new ElderTreeFeatureConfig(this.baseProvider, this.trunkProvider, this.leavesProvider, this.decorators, this.baseHeight).setSapling(sapling);
        }
    }
}
