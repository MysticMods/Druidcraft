package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.world.config.BlockStateRadiusFeatureConfig;
import com.vulp.druidcraft.world.config.DummyTreeFeatureConfig;
import com.vulp.druidcraft.world.features.ElderTreeFeature;

import com.vulp.druidcraft.world.features.RadiusBlockBlobFeature;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistry {
    private static Set<Feature<?>> FEATURES = new HashSet<>();

    public static Feature<DummyTreeFeatureConfig> elder_tree = register("elder_tree", new ElderTreeFeature(DummyTreeFeatureConfig.CODEC));
/*    public static Feature<DummyTreeFeatureConfig> giant_heartburn_fungus = register("giant_heartburn_fungus", new GiantHeartburnFungusFeature(DummyTreeFeatureConfig.CODEC));*/
    public static Feature<BlockStateRadiusFeatureConfig> taiga_rock = register("taiga_rock", new RadiusBlockBlobFeature(BlockStateRadiusFeatureConfig.CODEC));
/*    public static Feature<NoFeatureConfig> gaseous_growth = register("gaseous_growth", new GaseousGrowthFeature(NoFeatureConfig.field_236558_a_));*/

    public static <V extends IFeatureConfig> Feature<V> register(String name, Feature<V> feature) {
        ResourceLocation id = new ResourceLocation(Druidcraft.MODID, name);
        feature.setRegistryName(id);
        FEATURES.add(feature);
        return feature;
    }

    @SubscribeEvent
    public static void register (RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(FEATURES.toArray(new Feature[0]));
    }
}
