/*
package com.vulp.druidcraft.mixin;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.vulp.druidcraft.registry.BiomeRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Mixin(NetherBiomeProvider.class)
public abstract class NetherBiomeProviderMixin extends BiomeProvider {

    @Shadow
    @Final
    private Optional<Pair<Registry<Biome>, NetherBiomeProvider.Preset>> netherProviderPreset;
    @Shadow
    @Final
    private List<Pair<Biome.Attributes, Supplier<Biome>>> biomeAttributes;

    protected NetherBiomeProviderMixin(Stream<Supplier<Biome>> biomes) {
        super(biomes);
    }

    @Inject(at = @At("RETURN"), method = "<init>(JLjava/util/List;Ljava/util/Optional;)V")
    private void providerInit(long seed, List<Pair<Biome.Attributes, Supplier<Biome>>> biomeAttributes, Optional<Pair<Registry<Biome>, NetherBiomeProvider.Preset>> netherProviderPreset, CallbackInfo ci) {
        {
            netherProviderPreset.ifPresent(registryPresetPair -> this.biomes = getBiomes(registryPresetPair.getFirst()));
        }
    }

    */
/*@Inject(at = @At("RETURN"), method = "<init>(JLjava/util/List;Lnet/minecraft/world/biome/provider/NetherBiomeProvider$Noise;Lnet/minecraft/world/biome/provider/NetherBiomeProvider$Noise;Lnet/minecraft/world/biome/provider/NetherBiomeProvider$Noise;Lnet/minecraft/world/biome/provider/NetherBiomeProvider$Noise;)V")
    private void providerInitB(long seed, List<Pair<Biome.Attributes, Supplier<Biome>>> biomeAttributes, NetherBiomeProvider.Noise temperatureNoise, NetherBiomeProvider.Noise humidityNoise, NetherBiomeProvider.Noise altitudeNoise, NetherBiomeProvider.Noise weirdnessNoise, CallbackInfo ci) {

    }*//*


    private List<Biome> getBiomes(Registry<Biome> biomeRegistry)
    {
        List<Biome> list = Lists.newArrayList();
        biomeRegistry.forEach((biome) -> {
			if (biome.getCategory() == Biome.Category.NETHER)
			{
				list.add(biome);
			}
        });
        return list;
    }

    @Inject(method = "getBiomeProvider", at = @At("HEAD"), cancellable = true)
    private void be_getBiomeProvider(long seed, CallbackInfoReturnable<BiomeProvider> info)
    {
        info.setReturnValue(new NetherBiomeProvider(seed, this.biomeAttributes, this.netherProviderPreset));
        info.cancel();
    }

}
*/
