package com.vulp.druidcraft.config;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class TreeConfig extends FeatureConfig<TreeConfig> {
    private final double chance;
    private List<RegistryKey<World>> dimensions;
    private ForgeConfigSpec.DoubleValue configChance;
    private ForgeConfigSpec.ConfigValue<List<? extends String>> configDimensions;
    private String treeName;

    public TreeConfig(String treeName, double chance, List<BiomeDictionary.Type> biomeTypes, List<BiomeDictionary.Type> biomeRestrictions, List<RegistryKey<World>> dimensions) {
        super(biomeTypes, biomeRestrictions);
        this.chance = chance;
        this.dimensions = dimensions;
        this.treeName = treeName;
    }

    public double getChance() {
        return configChance.get();
    }

    private Set<RegistryKey<World>> storedDimension = null;

    public Set<RegistryKey<World>> getDimensions() {
        if (storedDimension == null) {
            storedDimension = configDimensions.get().stream().map(o -> RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(o))).collect(Collectors.toSet());
        }

        return storedDimension;
    }

    @Override
    public boolean shouldSpawn() {
        return getChance() != 0;
    }

    @Override
    public GenerationStage.Decoration getStage() {
        return GenerationStage.Decoration.VEGETAL_DECORATION;
    }

    @Override
    public void apply(ForgeConfigSpec.Builder builder) {
        builder.comment(treeName + " Generation").push("charred_tree");
        configChance = builder.comment("Number of trees per chunk (set to 0 to disable).").defineInRange("chance", chance, 0, 256);
        StringJoiner sb = new StringJoiner(",");
        biomes.forEach(o -> sb.add(o.getName()));
        configBiomes = builder.comment("List of biome types to spawn (default [" + sb.toString() + "], pass empty list for every biome").define("biomes", sb.toString());
        StringJoiner sb2 = new StringJoiner(",");
        biomeRestrictions.forEach(biome -> sb2.add(biome.getName()));
        configBiomeRestrictions = builder.comment("Which biome types this tree shouldn't spawn in (default END, NETHER)").define("biomeRestrictions", sb2.toString());
        configDimensions = builder.comment("The dimensions that this feature should spawn in as a list (default [\"minecraft:overworld\"])").defineList("dimensions", dimensions.stream().map(RegistryKey::getLocation).map(ResourceLocation::toString).collect(Collectors.toList()), (o) -> o instanceof String);
        builder.pop();
    }

    @Override
    public void reset() {
        super.reset();
        storedDimension = null;
    }
}
