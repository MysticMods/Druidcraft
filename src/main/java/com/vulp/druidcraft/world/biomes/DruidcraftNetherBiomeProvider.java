package com.vulp.druidcraft.world.biomes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.gen.MaxMinNoiseMixer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class DruidcraftNetherBiomeProvider extends BiomeProvider {

    private static final DruidcraftNetherBiomeProvider.Noise DEFAULT_NOISE = new DruidcraftNetherBiomeProvider.Noise(-7, ImmutableList.of(1.0D, 1.0D));
    public static final MapCodec<DruidcraftNetherBiomeProvider> PACKET_CODEC = RecordCodecBuilder.mapCodec((builder) -> {
        return builder.group(Codec.LONG.fieldOf("seed").forGetter((netherProvider) -> {
            return netherProvider.seed;
        }), RecordCodecBuilder.<Pair<Biome.Attributes, Supplier<Biome>>>create((biomeAttributes) -> {
            return biomeAttributes.group(Biome.Attributes.CODEC.fieldOf("parameters").forGetter(Pair::getFirst), Biome.BIOME_CODEC.fieldOf("biome").forGetter(Pair::getSecond)).apply(biomeAttributes, Pair::of);
        }).listOf().fieldOf("biomes").forGetter((netherProvider) -> {
            return netherProvider.biomeAttributes;
        }), DruidcraftNetherBiomeProvider.Noise.CODEC.fieldOf("temperature_noise").forGetter((netherProvider) -> {
            return netherProvider.temperatureNoise;
        }), DruidcraftNetherBiomeProvider.Noise.CODEC.fieldOf("humidity_noise").forGetter((netherProvider) -> {
            return netherProvider.humidityNoise;
        }), DruidcraftNetherBiomeProvider.Noise.CODEC.fieldOf("altitude_noise").forGetter((netherProvider) -> {
            return netherProvider.altitudeNoise;
        }), DruidcraftNetherBiomeProvider.Noise.CODEC.fieldOf("weirdness_noise").forGetter((netherProvider) -> {
            return netherProvider.weirdnessNoise;
        })).apply(builder, DruidcraftNetherBiomeProvider::new);
    });
    public static final Codec<DruidcraftNetherBiomeProvider> CODEC = Codec.mapEither(DruidcraftNetherBiomeProvider.DefaultBuilder.CODEC, PACKET_CODEC).xmap((either) -> {
        return either.map(DruidcraftNetherBiomeProvider.DefaultBuilder::build, Function.identity());
    }, (netherProvider) -> {
        return netherProvider.getDefaultBuilder().map(Either::<DruidcraftNetherBiomeProvider.DefaultBuilder, DruidcraftNetherBiomeProvider>left).orElseGet(() -> {
            return Either.right(netherProvider);
        });
    }).codec();
    private final DruidcraftNetherBiomeProvider.Noise temperatureNoise;
    private final DruidcraftNetherBiomeProvider.Noise humidityNoise;
    private final DruidcraftNetherBiomeProvider.Noise altitudeNoise;
    private final DruidcraftNetherBiomeProvider.Noise weirdnessNoise;
    private final MaxMinNoiseMixer temperatureNoiseMixer;
    private final MaxMinNoiseMixer humidityNoiseMixer;
    private final MaxMinNoiseMixer altitudeNoiseMixer;
    private final MaxMinNoiseMixer weirdnessNoiseMixer;
    private final List<Pair<Biome.Attributes, Supplier<Biome>>> biomeAttributes;
    private final boolean useHeightForNoise;
    private final long seed;
    private final Optional<Pair<Registry<Biome>, DruidcraftNetherBiomeProvider.Preset>> netherProviderPreset;

    private DruidcraftNetherBiomeProvider(long seed, List<Pair<Biome.Attributes, Supplier<Biome>>> biomeAttributes, Optional<Pair<Registry<Biome>, DruidcraftNetherBiomeProvider.Preset>> netherProviderPreset) {
        this(seed, biomeAttributes, DEFAULT_NOISE, DEFAULT_NOISE, DEFAULT_NOISE, DEFAULT_NOISE, netherProviderPreset);
    }

    private DruidcraftNetherBiomeProvider(long seed, List<Pair<Biome.Attributes, Supplier<Biome>>> biomeAttributes, DruidcraftNetherBiomeProvider.Noise temperatureNoise, DruidcraftNetherBiomeProvider.Noise humidityNoise, DruidcraftNetherBiomeProvider.Noise altitudeNoise, DruidcraftNetherBiomeProvider.Noise weirdnessNoise) {
        this(seed, biomeAttributes, temperatureNoise, humidityNoise, altitudeNoise, weirdnessNoise, Optional.empty());
    }

    private DruidcraftNetherBiomeProvider(long seed, List<Pair<Biome.Attributes, Supplier<Biome>>> biomeAttributes, DruidcraftNetherBiomeProvider.Noise temperatureNoise, DruidcraftNetherBiomeProvider.Noise humidityNoise, DruidcraftNetherBiomeProvider.Noise altitudeNoise, DruidcraftNetherBiomeProvider.Noise weirdnessNoise, Optional<Pair<Registry<Biome>, DruidcraftNetherBiomeProvider.Preset>> netherProviderPreset) {
        super(biomeAttributes.stream().map(Pair::getSecond));
        this.seed = seed;
        this.netherProviderPreset = netherProviderPreset;
        this.temperatureNoise = temperatureNoise;
        this.humidityNoise = humidityNoise;
        this.altitudeNoise = altitudeNoise;
        this.weirdnessNoise = weirdnessNoise;
        this.temperatureNoiseMixer = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(seed), temperatureNoise.getNumberOfOctaves(), temperatureNoise.getAmplitudes());
        this.humidityNoiseMixer = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(seed + 1L), humidityNoise.getNumberOfOctaves(), humidityNoise.getAmplitudes());
        this.altitudeNoiseMixer = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(seed + 2L), altitudeNoise.getNumberOfOctaves(), altitudeNoise.getAmplitudes());
        this.weirdnessNoiseMixer = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(seed + 3L), weirdnessNoise.getNumberOfOctaves(), weirdnessNoise.getAmplitudes());
        this.biomeAttributes = biomeAttributes;
        this.useHeightForNoise = false;
    }

    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @OnlyIn(Dist.CLIENT)
    public BiomeProvider getBiomeProvider(long seed) {
        return new DruidcraftNetherBiomeProvider(seed, this.biomeAttributes, this.temperatureNoise, this.humidityNoise, this.altitudeNoise, this.weirdnessNoise, this.netherProviderPreset);
    }

    private Optional<DruidcraftNetherBiomeProvider.DefaultBuilder> getDefaultBuilder() {
        return this.netherProviderPreset.map((registryPresetPair) -> {
            return new DruidcraftNetherBiomeProvider.DefaultBuilder(registryPresetPair.getSecond(), registryPresetPair.getFirst(), this.seed);
        });
    }

    public Biome getNoiseBiome(int x, int y, int z) {
        int i = this.useHeightForNoise ? y : 0;
        Biome.Attributes biome$attributes = new Biome.Attributes((float)this.temperatureNoiseMixer.func_237211_a_((double)x, (double)i, (double)z), (float)this.humidityNoiseMixer.func_237211_a_((double)x, (double)i, (double)z), (float)this.altitudeNoiseMixer.func_237211_a_((double)x, (double)i, (double)z), (float)this.weirdnessNoiseMixer.func_237211_a_((double)x, (double)i, (double)z), 0.0F);
        return this.biomeAttributes.stream().min(Comparator.comparing((attributeBiomePair) -> {
            return attributeBiomePair.getFirst().getAttributeDifference(biome$attributes);
        })).map(Pair::getSecond).map(Supplier::get).orElse(BiomeRegistry.THE_VOID);
    }

    public boolean isDefaultPreset(long seed) {
        return this.seed == seed && this.netherProviderPreset.isPresent() && Objects.equals(this.netherProviderPreset.get().getSecond(), DruidcraftNetherBiomeProvider.Preset.DEFAULT_NETHER_PROVIDER_PRESET);
    }

    static final class DefaultBuilder {
        public static final MapCodec<DruidcraftNetherBiomeProvider.DefaultBuilder> CODEC = RecordCodecBuilder.mapCodec((builder) -> {
            return builder.group(ResourceLocation.CODEC.flatXmap((id) -> {
                return Optional.ofNullable(DruidcraftNetherBiomeProvider.Preset.PRESETS.get(id)).map(DataResult::success).orElseGet(() -> {
                    return DataResult.error("Unknown preset: " + id);
                });
            }, (preset) -> {
                return DataResult.success(preset.id);
            }).fieldOf("preset").stable().forGetter(DruidcraftNetherBiomeProvider.DefaultBuilder::getPreset), RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter(DruidcraftNetherBiomeProvider.DefaultBuilder::getLookupRegistry), Codec.LONG.fieldOf("seed").stable().forGetter(DruidcraftNetherBiomeProvider.DefaultBuilder::getSeed)).apply(builder, builder.stable(DruidcraftNetherBiomeProvider.DefaultBuilder::new));
        });
        private final DruidcraftNetherBiomeProvider.Preset preset;
        private final Registry<Biome> lookupRegistry;
        private final long seed;

        private DefaultBuilder(DruidcraftNetherBiomeProvider.Preset preset, Registry<Biome> lookupRegistry, long seed) {
            this.preset = preset;
            this.lookupRegistry = lookupRegistry;
            this.seed = seed;
        }

        public DruidcraftNetherBiomeProvider.Preset getPreset() {
            return this.preset;
        }

        public Registry<Biome> getLookupRegistry() {
            return this.lookupRegistry;
        }

        public long getSeed() {
            return this.seed;
        }

        public DruidcraftNetherBiomeProvider build() {
            return this.preset.build(this.lookupRegistry, this.seed);
        }
    }

    static class Noise {
        private final int numOctaves;
        private final DoubleList amplitudes;
        public static final Codec<DruidcraftNetherBiomeProvider.Noise> CODEC = RecordCodecBuilder.create((builder) -> {
            return builder.group(Codec.INT.fieldOf("firstOctave").forGetter(DruidcraftNetherBiomeProvider.Noise::getNumberOfOctaves), Codec.DOUBLE.listOf().fieldOf("amplitudes").forGetter(DruidcraftNetherBiomeProvider.Noise::getAmplitudes)).apply(builder, DruidcraftNetherBiomeProvider.Noise::new);
        });

        public Noise(int numOctaves, List<Double> amplitudes) {
            this.numOctaves = numOctaves;
            this.amplitudes = new DoubleArrayList(amplitudes);
        }

        public int getNumberOfOctaves() {
            return this.numOctaves;
        }

        public DoubleList getAmplitudes() {
            return this.amplitudes;
        }
    }

    public static class Preset {
        private static final Map<ResourceLocation, DruidcraftNetherBiomeProvider.Preset> PRESETS = Maps.newHashMap();
        public static final DruidcraftNetherBiomeProvider.Preset DEFAULT_NETHER_PROVIDER_PRESET = new DruidcraftNetherBiomeProvider.Preset(new ResourceLocation("druidcraftnether"), (preset, lookupRegistry, seed) -> {
            return new DruidcraftNetherBiomeProvider(seed, ImmutableList.of(Pair.of(new Biome.Attributes(0.4F, 0.5F, 0.0F, 0.0F, 0.0F), () -> {
                return lookupRegistry.getOrThrow(com.vulp.druidcraft.registry.BiomeRegistry.BiomeKeys.fervid_jungle);
            })), Optional.of(Pair.of(lookupRegistry, preset)));
        });
        private final ResourceLocation id;
        private final Function3<DruidcraftNetherBiomeProvider.Preset, Registry<Biome>, Long, DruidcraftNetherBiomeProvider> netherProviderFunction;

        public Preset(ResourceLocation id, Function3<DruidcraftNetherBiomeProvider.Preset, Registry<Biome>, Long, DruidcraftNetherBiomeProvider> netherProviderFunction) {
            this.id = id;
            this.netherProviderFunction = netherProviderFunction;
            PRESETS.put(id, this);
        }

        public DruidcraftNetherBiomeProvider build(Registry<Biome> lookupRegistry, long seed) {
            return this.netherProviderFunction.apply(this, lookupRegistry, seed);
        }
    }

}
