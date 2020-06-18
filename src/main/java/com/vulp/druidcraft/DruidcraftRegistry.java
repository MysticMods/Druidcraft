package com.vulp.druidcraft;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.vulp.druidcraft.blocks.*;
import com.vulp.druidcraft.blocks.DoorBlock;
import com.vulp.druidcraft.blocks.OreBlock;
import com.vulp.druidcraft.blocks.PressurePlateBlock;
import com.vulp.druidcraft.blocks.SaplingBlock;
import com.vulp.druidcraft.blocks.StairsBlock;
import com.vulp.druidcraft.blocks.FieryTorchBlock;
import com.vulp.druidcraft.blocks.TrapDoorBlock;
import com.vulp.druidcraft.blocks.WoodButtonBlock;
import com.vulp.druidcraft.blocks.tileentities.CrateTileEntity;
import com.vulp.druidcraft.blocks.tileentities.GrowthLampTileEntity;
import com.vulp.druidcraft.blocks.tileentities.SmallBeamTileEntity;
import com.vulp.druidcraft.blocks.trees.DarkwoodTree;
import com.vulp.druidcraft.blocks.trees.ElderTree;
import com.vulp.druidcraft.client.renders.ItemTileEntityRenderer;
import com.vulp.druidcraft.client.renders.SmallBeamTileEntityRenderer;
import com.vulp.druidcraft.entities.LunarMothColors;
import com.vulp.druidcraft.items.*;
import com.vulp.druidcraft.recipes.RecipeSerializers;
import com.vulp.druidcraft.registry.*;
import com.vulp.druidcraft.world.biomes.DarkwoodForest;
import com.vulp.druidcraft.world.features.ElderTreeFeature;
import com.vulp.druidcraft.world.features.ElderTreeFeatureConfig;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.SpruceTree;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class DruidcraftRegistry {

    public static final String MODID = "druidcraft";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final ItemGroup DRUIDCRAFT = new DruidcraftItemGroup();

    // ITEM REGISTRATION
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
    {
        itemRegistryEvent.getRegistry().registerAll
                (
                        ItemRegistry.debug_block = new BlockItem(BlockRegistry.debug_block, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.debug_block.getRegistryName()),

                        // True items:
                        ItemRegistry.hemp = new Item(new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("hemp")),
                        ItemRegistry.hemp_seeds = new PlantableItem(new Item.Properties().group(DRUIDCRAFT), PlantType.Crop, (CropBlock)BlockRegistry.hemp_crop, true).setRegistryName(location("hemp_seeds")),
                        ItemRegistry.amber = new Item(new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("amber")),
                        ItemRegistry.moonstone = new Item(new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("moonstone")),
                        ItemRegistry.fiery_glass = new SmeltableItem(new Item.Properties().group(DRUIDCRAFT), 2400).setRegistryName(location("fiery_glass")),
                        ItemRegistry.rockroot = new Item(new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("rockroot")),
                        ItemRegistry.chitin = new Item(new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("chitin")),
                        ItemRegistry.knife = new KnifeItem(new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(location("knife")),

                        //Tools & Armour:
                        ItemRegistry.bone_sword = new SwordItem(ToolMaterialRegistry.bone, 3, -2.4f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_sword")),
                        ItemRegistry.bone_shovel = new ShovelItem(ToolMaterialRegistry.bone, 1.5f, -3.0f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_shovel")),
                        ItemRegistry.bone_pickaxe = new PickaxeItem(ToolMaterialRegistry.bone, 1, -2.8f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_pickaxe")),
                        ItemRegistry.bone_axe = new AxeItem(ToolMaterialRegistry.bone, 7.0f, -3.2f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_axe")),
                        ItemRegistry.bone_hoe = new HoeItem(ToolMaterialRegistry.bone, -2.0f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_hoe")),
                        ItemRegistry.bone_sickle = new SickleItem(new ItemProperties().attackDamage(-1).attackSpeed(-1.5f).tier(ToolMaterialRegistry.bone).radius(2).setGroup(DRUIDCRAFT)).setRegistryName(location("bone_sickle")),
                        ItemRegistry.bone_helmet = new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlotType.HEAD, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_helmet")),
                        ItemRegistry.bone_chestplate = new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlotType.CHEST, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_chestplate")),
                        ItemRegistry.bone_leggings = new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlotType.LEGS, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_leggings")),
                        ItemRegistry.bone_boots = new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlotType.FEET, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_boots")),
                        ItemRegistry.bone_shield = new BasicShieldItem(new Item.Properties().group(DRUIDCRAFT).setISTER(() -> ItemTileEntityRenderer::new), 72000, Items.BONE).setRegistryName(location("bone_shield")),

                        ItemRegistry.chitin_helmet = new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlotType.HEAD, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("chitin_helmet")),
                        ItemRegistry.chitin_chestplate = new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlotType.CHEST, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("chitin_chestplate")),
                        ItemRegistry.chitin_leggings = new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlotType.LEGS, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("chitin_leggings")),
                        ItemRegistry.chitin_boots = new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlotType.FEET, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("chitin_boots")),
                        ItemRegistry.chitin_shield = new BasicShieldItem(new Item.Properties().group(DRUIDCRAFT).setISTER(() -> ItemTileEntityRenderer::new), 360000, ItemRegistry.chitin).setRegistryName(location("chitin_shield")),

                        ItemRegistry.moonstone_sword = new SwordItem(ToolMaterialRegistry.moonstone, 3, -2.4f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("moonstone_sword")),
                        ItemRegistry.moonstone_shovel = new ShovelItem(ToolMaterialRegistry.moonstone, 1.5f, -3.0f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("moonstone_shovel")),
                        ItemRegistry.moonstone_pickaxe = new PickaxeItem(ToolMaterialRegistry.moonstone, 1, -2.8f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("moonstone_pickaxe")),
                        ItemRegistry.moonstone_axe = new AxeItem(ToolMaterialRegistry.moonstone, 7.0f, -3.2f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("moonstone_axe")),
                        ItemRegistry.moonstone_hoe = new HoeItem(ToolMaterialRegistry.moonstone, -2.0f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("moonstone_hoe")),
                        ItemRegistry.moonstone_sickle = new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ToolMaterialRegistry.moonstone).radius(4).setGroup(DRUIDCRAFT)).setRegistryName(location("moonstone_sickle")),
                        ItemRegistry.moonstone_helmet = new ArmorItem(ArmorMaterialRegistry.moonstone, EquipmentSlotType.HEAD, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("moonstone_helmet")),
                        ItemRegistry.moonstone_chestplate = new ArmorItem(ArmorMaterialRegistry.moonstone, EquipmentSlotType.CHEST, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("moonstone_chestplate")),
                        ItemRegistry.moonstone_leggings = new ArmorItem(ArmorMaterialRegistry.moonstone, EquipmentSlotType.LEGS, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("moonstone_leggings")),
                        ItemRegistry.moonstone_boots = new ArmorItem(ArmorMaterialRegistry.moonstone, EquipmentSlotType.FEET, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("moonstone_boots")),
                        ItemRegistry.moonstone_shield = new BasicShieldItem(new Item.Properties().group(DRUIDCRAFT).setISTER(() -> ItemTileEntityRenderer::new), 1080000, ItemRegistry.moonstone).setRegistryName(location("moonstone_shield")),

                        ItemRegistry.wooden_sickle = new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ItemTier.WOOD).radius(1).setGroup(ItemGroup.TOOLS)).setRegistryName(location("wooden_sickle")),
                        ItemRegistry.stone_sickle = new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ItemTier.STONE).radius(2).setGroup(ItemGroup.TOOLS)).setRegistryName(location("stone_sickle")),
                        ItemRegistry.iron_sickle = new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ItemTier.IRON).radius(3).setGroup(ItemGroup.TOOLS)).setRegistryName(location("iron_sickle")),
                        ItemRegistry.gold_sickle = new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ItemTier.GOLD).radius(1).setGroup(ItemGroup.TOOLS)).setRegistryName(location("gold_sickle")),
                        ItemRegistry.diamond_sickle = new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ItemTier.DIAMOND).radius(4).setGroup(ItemGroup.TOOLS)).setRegistryName(location("diamond_sickle")),

                        ItemRegistry.lunar_moth_jar_turquoise = new LunarMothJarItem(BlockRegistry.turquoise_lunar_moth_jar, LunarMothColors.TURQUOISE, new LunarMothJarItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("turquoise_lunar_moth_jar")),
                        ItemRegistry.lunar_moth_jar_white = new LunarMothJarItem(BlockRegistry.white_lunar_moth_jar, LunarMothColors.WHITE, new LunarMothJarItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("white_lunar_moth_jar")),
                        ItemRegistry.lunar_moth_jar_lime = new LunarMothJarItem(BlockRegistry.lime_lunar_moth_jar, LunarMothColors.LIME, new LunarMothJarItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("lime_lunar_moth_jar")),
                        ItemRegistry.lunar_moth_jar_yellow = new LunarMothJarItem(BlockRegistry.yellow_lunar_moth_jar, LunarMothColors.YELLOW, new LunarMothJarItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("yellow_lunar_moth_jar")),
                        ItemRegistry.lunar_moth_jar_orange = new LunarMothJarItem(BlockRegistry.orange_lunar_moth_jar, LunarMothColors.ORANGE, new LunarMothJarItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("orange_lunar_moth_jar")),
                        ItemRegistry.lunar_moth_jar_pink = new LunarMothJarItem(BlockRegistry.pink_lunar_moth_jar, LunarMothColors.PINK, new LunarMothJarItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("pink_lunar_moth_jar")),

                        ItemRegistry.lunar_moth_egg_turquoise = new LunarMothEggItem(LunarMothColors.TURQUOISE, new LunarMothEggItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("turquoise_lunar_moth_egg")),
                        ItemRegistry.lunar_moth_egg_white = new LunarMothEggItem(LunarMothColors.WHITE, new LunarMothEggItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("white_lunar_moth_egg")),
                        ItemRegistry.lunar_moth_egg_lime = new LunarMothEggItem(LunarMothColors.LIME, new LunarMothEggItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("lime_lunar_moth_egg")),
                        ItemRegistry.lunar_moth_egg_yellow = new LunarMothEggItem(LunarMothColors.YELLOW, new LunarMothEggItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("yellow_lunar_moth_egg")),
                        ItemRegistry.lunar_moth_egg_orange = new LunarMothEggItem(LunarMothColors.ORANGE, new LunarMothEggItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("orange_lunar_moth_egg")),
                        ItemRegistry.lunar_moth_egg_pink = new LunarMothEggItem(LunarMothColors.PINK, new LunarMothEggItem.Properties().group(DRUIDCRAFT)).setRegistryName(location("pink_lunar_moth_egg")),

                        ItemRegistry.blueberries = new BlockNamedItem(BlockRegistry.blueberry_bush, new Item.Properties().group(DRUIDCRAFT).food(FoodRegistry.blueberries)).setRegistryName(location("blueberries")),
                        ItemRegistry.elderberries = new Item(new Item.Properties().group(DRUIDCRAFT).food(FoodRegistry.elderberries)).setRegistryName(location("elderberries")),
                        ItemRegistry.elderflower = new Item(new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("elderflower")),
                        ItemRegistry.blueberry_muffin = new Item(new Item.Properties().group(DRUIDCRAFT).food(FoodRegistry.blueberry_muffin)).setRegistryName(location("blueberry_muffin")),
                        ItemRegistry.apple_elderberry_crumble = new Item(new Item.Properties().group(DRUIDCRAFT).food(FoodRegistry.apple_elderberry_crumble)).setRegistryName(location("apple_elderberry_crumble")),
                        ItemRegistry.elderflower_cordial = new DrinkableItem(new DrinkableItem.Properties().group(DRUIDCRAFT).food(FoodRegistry.elderflower_cordial)).setRegistryName(location("elderflower_cordial")),

                        ItemRegistry.travel_pack = new TravelPackItem(new TravelPackItem.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(location("travel_pack")),

                        // Item-blocks:
                        ItemRegistry.amber_ore = new BlockItem(BlockRegistry.amber_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.amber_ore.getRegistryName()),
                        ItemRegistry.moonstone_ore = new BlockItem(BlockRegistry.moonstone_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.moonstone_ore.getRegistryName()),
                        ItemRegistry.fiery_glass_ore = new BlockItem(BlockRegistry.fiery_glass_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.fiery_glass_ore.getRegistryName()),
                        ItemRegistry.rockroot_ore = new BlockItem(BlockRegistry.rockroot_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.rockroot_ore.getRegistryName()),
                        ItemRegistry.amber_block = new BlockItem(BlockRegistry.amber_block, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.amber_block.getRegistryName()),
                        ItemRegistry.moonstone_block = new BlockItem(BlockRegistry.moonstone_block, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.moonstone_block.getRegistryName()),
                        ItemRegistry.fiery_glass_block = new SmeltableBlockItem(BlockRegistry.fiery_glass_block, 24000, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.fiery_glass_block.getRegistryName()),
                        ItemRegistry.rockroot_block = new BlockItem(BlockRegistry.rockroot_block, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.rockroot_block.getRegistryName()),
                        ItemRegistry.darkwood_log = new BlockItem(BlockRegistry.darkwood_log, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_log.getRegistryName()),
                        ItemRegistry.stripped_darkwood_log = new BlockItem(BlockRegistry.stripped_darkwood_log, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.stripped_darkwood_log.getRegistryName()),
                        ItemRegistry.darkwood_leaves = new BlockItem(BlockRegistry.darkwood_leaves, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_leaves.getRegistryName()),
                        ItemRegistry.darkwood_sapling = new BlockItem(BlockRegistry.darkwood_sapling, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_sapling.getRegistryName()),
                        ItemRegistry.darkwood_planks = new BlockItem(BlockRegistry.darkwood_planks, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_planks.getRegistryName()),
                        ItemRegistry.stripped_darkwood_wood = new BlockItem(BlockRegistry.stripped_darkwood_wood, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.stripped_darkwood_wood.getRegistryName()),
                        ItemRegistry.darkwood_wood = new BlockItem(BlockRegistry.darkwood_wood, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_wood.getRegistryName()),
                        ItemRegistry.darkwood_slab = new BlockItem(BlockRegistry.darkwood_slab, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_slab.getRegistryName()),
                        ItemRegistry.darkwood_stairs = new BlockItem(BlockRegistry.darkwood_stairs, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_stairs.getRegistryName()),
                        ItemRegistry.darkwood_fence = new BlockItem(BlockRegistry.darkwood_fence, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_fence.getRegistryName()),
                        ItemRegistry.darkwood_fence_gate = new BlockItem(BlockRegistry.darkwood_fence_gate, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_fence_gate.getRegistryName()),
                        ItemRegistry.darkwood_pressure_plate = new BlockItem(BlockRegistry.darkwood_pressure_plate, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_pressure_plate.getRegistryName()),
                        ItemRegistry.darkwood_button = new BlockItem(BlockRegistry.darkwood_button, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_button.getRegistryName()),
                        ItemRegistry.darkwood_trapdoor = new BlockItem(BlockRegistry.darkwood_trapdoor, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_trapdoor.getRegistryName()),
                        ItemRegistry.darkwood_door = new BlockItem(BlockRegistry.darkwood_door, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_door.getRegistryName()),

                        ItemRegistry.elder_log = new BlockItem(BlockRegistry.elder_log, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_log.getRegistryName()),
                        ItemRegistry.stripped_elder_log = new BlockItem(BlockRegistry.stripped_elder_log, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.stripped_elder_log.getRegistryName()),
                        ItemRegistry.elder_leaves = new BlockItem(BlockRegistry.elder_leaves, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_leaves.getRegistryName()),
                        ItemRegistry.elder_sapling = new BlockItem(BlockRegistry.elder_sapling, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_sapling.getRegistryName()),
                        ItemRegistry.elder_planks = new BlockItem(BlockRegistry.elder_planks, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_planks.getRegistryName()),
                        ItemRegistry.stripped_elder_wood = new BlockItem(BlockRegistry.stripped_elder_wood, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.stripped_elder_wood.getRegistryName()),
                        ItemRegistry.elder_wood = new BlockItem(BlockRegistry.elder_wood, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_wood.getRegistryName()),
                        ItemRegistry.elder_slab = new BlockItem(BlockRegistry.elder_slab, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_slab.getRegistryName()),
                        ItemRegistry.elder_stairs = new BlockItem(BlockRegistry.elder_stairs, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_stairs.getRegistryName()),
                        ItemRegistry.elder_fence = new BlockItem(BlockRegistry.elder_fence, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_fence.getRegistryName()),
                        ItemRegistry.elder_fence_gate = new BlockItem(BlockRegistry.elder_fence_gate, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_fence_gate.getRegistryName()),
                        ItemRegistry.elder_pressure_plate = new BlockItem(BlockRegistry.elder_pressure_plate, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_pressure_plate.getRegistryName()),
                        ItemRegistry.elder_button = new BlockItem(BlockRegistry.elder_button, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_button.getRegistryName()),
                        ItemRegistry.elder_trapdoor = new BlockItem(BlockRegistry.elder_trapdoor, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_trapdoor.getRegistryName()),
                        ItemRegistry.elder_door = new BlockItem(BlockRegistry.elder_door, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_door.getRegistryName()),

                        ItemRegistry.oak_beam = new BlockItem(BlockRegistry.oak_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.oak_beam.getRegistryName()),
                        ItemRegistry.spruce_beam = new BlockItem(BlockRegistry.spruce_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.spruce_beam.getRegistryName()),
                        ItemRegistry.birch_beam = new BlockItem(BlockRegistry.birch_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.birch_beam.getRegistryName()),
                        ItemRegistry.jungle_beam = new BlockItem(BlockRegistry.jungle_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.jungle_beam.getRegistryName()),
                        ItemRegistry.acacia_beam = new BlockItem(BlockRegistry.acacia_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.acacia_beam.getRegistryName()),
                        ItemRegistry.dark_oak_beam = new BlockItem(BlockRegistry.dark_oak_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.dark_oak_beam.getRegistryName()),
                        ItemRegistry.darkwood_beam = new BlockItem(BlockRegistry.darkwood_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_beam.getRegistryName()),
                        ItemRegistry.elder_beam = new BlockItem(BlockRegistry.elder_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_beam.getRegistryName()),
                        ItemRegistry.oak_small_beam = new BlockItem(BlockRegistry.oak_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.oak_small_beam.getRegistryName()),
                        ItemRegistry.spruce_small_beam = new BlockItem(BlockRegistry.spruce_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.spruce_small_beam.getRegistryName()),
                        ItemRegistry.birch_small_beam = new BlockItem(BlockRegistry.birch_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.birch_small_beam.getRegistryName()),
                        ItemRegistry.jungle_small_beam = new BlockItem(BlockRegistry.jungle_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.jungle_small_beam.getRegistryName()),
                        ItemRegistry.acacia_small_beam = new BlockItem(BlockRegistry.acacia_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.acacia_small_beam.getRegistryName()),
                        ItemRegistry.dark_oak_small_beam = new BlockItem(BlockRegistry.dark_oak_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.dark_oak_small_beam.getRegistryName()),
                        ItemRegistry.darkwood_small_beam = new BlockItem(BlockRegistry.darkwood_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_small_beam.getRegistryName()),
                        ItemRegistry.elder_small_beam = new BlockItem(BlockRegistry.elder_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_small_beam.getRegistryName()),
                        ItemRegistry.oak_panels = new BlockItem(BlockRegistry.oak_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.oak_panels.getRegistryName()),
                        ItemRegistry.spruce_panels = new BlockItem(BlockRegistry.spruce_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.spruce_panels.getRegistryName()),
                        ItemRegistry.birch_panels = new BlockItem(BlockRegistry.birch_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.birch_panels.getRegistryName()),
                        ItemRegistry.jungle_panels = new BlockItem(BlockRegistry.jungle_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.jungle_panels.getRegistryName()),
                        ItemRegistry.acacia_panels = new BlockItem(BlockRegistry.acacia_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.acacia_panels.getRegistryName()),
                        ItemRegistry.dark_oak_panels = new BlockItem(BlockRegistry.dark_oak_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.dark_oak_panels.getRegistryName()),
                        ItemRegistry.darkwood_panels = new BlockItem(BlockRegistry.darkwood_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_panels.getRegistryName()),
                        ItemRegistry.elder_panels = new BlockItem(BlockRegistry.elder_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_panels.getRegistryName()),
                        ItemRegistry.wet_mud_bricks = new BlockItem(BlockRegistry.wet_mud_bricks, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.wet_mud_bricks.getRegistryName()),
                        ItemRegistry.dry_mud_bricks = new BlockItem(BlockRegistry.dry_mud_bricks, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.dry_mud_bricks.getRegistryName()),
                        ItemRegistry.dry_mud_brick_slab = new BlockItem(BlockRegistry.dry_mud_brick_slab, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.dry_mud_brick_slab.getRegistryName()),
                        ItemRegistry.dry_mud_brick_stairs = new BlockItem(BlockRegistry.dry_mud_brick_stairs, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.dry_mud_brick_stairs.getRegistryName()),
                        ItemRegistry.dry_mud_brick_wall = new BlockItem(BlockRegistry.dry_mud_brick_wall, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.dry_mud_brick_wall.getRegistryName()),
                        ItemRegistry.fiery_torch = new WallOrFloorItem(BlockRegistry.fiery_torch, BlockRegistry.wall_fiery_torch, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.fiery_torch.getRegistryName()),
                        ItemRegistry.rope = new BlockItem(BlockRegistry.rope, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.rope.getRegistryName()),
                        ItemRegistry.crate = new BlockItem(BlockRegistry.crate, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.crate.getRegistryName()),
                        ItemRegistry.ceramic_lantern = new BlockItem(BlockRegistry.ceramic_lantern, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.ceramic_lantern.getRegistryName()),
                        ItemRegistry.lavender = new BlockItem(BlockRegistry.lavender, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.lavender.getRegistryName()),
                        ItemRegistry.growth_lamp = new BlockItem(BlockRegistry.growth_lamp, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.growth_lamp.getRegistryName()),

                        ItemRegistry.white_bedroll = new BedrollItem(DyeColor.WHITE, BlockRegistry.white_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.white_bedroll.getRegistryName()),
                        ItemRegistry.orange_bedroll = new BedrollItem(DyeColor.ORANGE, BlockRegistry.orange_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.orange_bedroll.getRegistryName()),
                        ItemRegistry.magenta_bedroll = new BedrollItem(DyeColor.MAGENTA, BlockRegistry.magenta_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.magenta_bedroll.getRegistryName()),
                        ItemRegistry.light_blue_bedroll = new BedrollItem(DyeColor.LIGHT_BLUE, BlockRegistry.light_blue_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.light_blue_bedroll.getRegistryName()),
                        ItemRegistry.yellow_bedroll = new BedrollItem(DyeColor.YELLOW, BlockRegistry.yellow_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.yellow_bedroll.getRegistryName()),
                        ItemRegistry.lime_bedroll = new BedrollItem(DyeColor.LIME, BlockRegistry.lime_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.lime_bedroll.getRegistryName()),
                        ItemRegistry.pink_bedroll = new BedrollItem(DyeColor.PINK, BlockRegistry.pink_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.pink_bedroll.getRegistryName()),
                        ItemRegistry.gray_bedroll = new BedrollItem(DyeColor.GRAY, BlockRegistry.gray_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.gray_bedroll.getRegistryName()),
                        ItemRegistry.light_gray_bedroll = new BedrollItem(DyeColor.LIGHT_GRAY, BlockRegistry.light_gray_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.light_gray_bedroll.getRegistryName()),
                        ItemRegistry.cyan_bedroll = new BedrollItem(DyeColor.CYAN, BlockRegistry.cyan_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.cyan_bedroll.getRegistryName()),
                        ItemRegistry.purple_bedroll = new BedrollItem(DyeColor.PURPLE, BlockRegistry.purple_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.purple_bedroll.getRegistryName()),
                        ItemRegistry.blue_bedroll = new BedrollItem(DyeColor.BLUE, BlockRegistry.blue_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.blue_bedroll.getRegistryName()),
                        ItemRegistry.brown_bedroll = new BedrollItem(DyeColor.BROWN, BlockRegistry.brown_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.brown_bedroll.getRegistryName()),
                        ItemRegistry.green_bedroll = new BedrollItem(DyeColor.GREEN, BlockRegistry.green_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.green_bedroll.getRegistryName()),
                        ItemRegistry.red_bedroll = new BedrollItem(DyeColor.RED, BlockRegistry.red_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.red_bedroll.getRegistryName()),
                        ItemRegistry.black_bedroll = new BedrollItem(DyeColor.BLACK, BlockRegistry.black_bedroll, new Item.Properties().group(DRUIDCRAFT).maxStackSize(1)).setRegistryName(BlockRegistry.black_bedroll.getRegistryName()),

                        ItemRegistry.white_soulfire = new BlockItem(BlockRegistry.white_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.white_soulfire.getRegistryName()),
                        ItemRegistry.orange_soulfire = new BlockItem(BlockRegistry.orange_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.orange_soulfire.getRegistryName()),
                        ItemRegistry.magenta_soulfire = new BlockItem(BlockRegistry.magenta_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.magenta_soulfire.getRegistryName()),
                        ItemRegistry.light_blue_soulfire = new BlockItem(BlockRegistry.light_blue_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.light_blue_soulfire.getRegistryName()),
                        ItemRegistry.yellow_soulfire = new BlockItem(BlockRegistry.yellow_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.yellow_soulfire.getRegistryName()),
                        ItemRegistry.lime_soulfire = new BlockItem(BlockRegistry.lime_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.lime_soulfire.getRegistryName()),
                        ItemRegistry.pink_soulfire = new BlockItem(BlockRegistry.pink_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.pink_soulfire.getRegistryName()),
                        ItemRegistry.gray_soulfire = new BlockItem(BlockRegistry.gray_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.gray_soulfire.getRegistryName()),
                        ItemRegistry.light_gray_soulfire = new BlockItem(BlockRegistry.light_gray_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.light_gray_soulfire.getRegistryName()),
                        ItemRegistry.cyan_soulfire = new BlockItem(BlockRegistry.cyan_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.cyan_soulfire.getRegistryName()),
                        ItemRegistry.purple_soulfire = new BlockItem(BlockRegistry.purple_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.purple_soulfire.getRegistryName()),
                        ItemRegistry.blue_soulfire = new BlockItem(BlockRegistry.blue_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.blue_soulfire.getRegistryName()),
                        ItemRegistry.brown_soulfire = new BlockItem(BlockRegistry.brown_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.brown_soulfire.getRegistryName()),
                        ItemRegistry.green_soulfire = new BlockItem(BlockRegistry.green_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.green_soulfire.getRegistryName()),
                        ItemRegistry.red_soulfire = new BlockItem(BlockRegistry.red_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.red_soulfire.getRegistryName()),
                        ItemRegistry.black_soulfire = new BlockItem(BlockRegistry.black_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.black_soulfire.getRegistryName()),

                        ItemRegistry.woodcutter = new BlockItem(BlockRegistry.woodcutter, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.woodcutter.getRegistryName())

                );

        EntityRegistry.registerEntitySpawnEggs(itemRegistryEvent);
        LOGGER.info("Items registered.");
    }

    // BLOCK REGISTRATION
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> BlockRegistryEvent)
    {
        BlockRegistryEvent.getRegistry().registerAll
                (
                        BlockRegistry.debug_block = new DebugBlock(DebugBlock.Properties.create(Material.CLAY)).setRegistryName(location("debug")),

                        BlockRegistry.hemp_crop = new HempBlock(HempBlock.Properties.create(Material.PLANTS).sound(SoundType.CROP).hardnessAndResistance(0.0f).doesNotBlockMovement().tickRandomly()).setRegistryName(location("hemp_crop")),
                        BlockRegistry.amber_ore = new OreBlock(OreBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(1), 6, 12).setRegistryName(location("amber_ore")),
                        BlockRegistry.moonstone_ore = new OreBlock(OreBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3), 6, 14).setRegistryName(location("moonstone_ore")),
                        BlockRegistry.fiery_glass_ore = new OreBlock(OreBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2), 4, 10).setRegistryName(location("fiery_glass_ore")),
                        BlockRegistry.rockroot_ore = new OreBlock(OreBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0), 2, 10).setRegistryName(location("rockroot_ore")),
                        BlockRegistry.amber_block = new BeaconBaseBlock(BeaconBaseBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)).setRegistryName(location("amber_block")),
                        BlockRegistry.moonstone_block = new BeaconBaseBlock(BeaconBaseBlock.Properties.create(Material.ROCK).hardnessAndResistance(6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)).setRegistryName(location("moonstone_block")),
                        BlockRegistry.fiery_glass_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).lightValue(15).sound(SoundType.STONE)).setRegistryName(location("fiery_glass_block")),
                        BlockRegistry.rockroot_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)).setRegistryName(location("rockroot_block")),
                        BlockRegistry.darkwood_log = new LogBlock(MaterialColor.WOOD, LogBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_log")),
                        BlockRegistry.stripped_darkwood_log = new LogBlock(MaterialColor.WOOD, LogBlock.Properties.from(BlockRegistry.darkwood_log)).setRegistryName(location("stripped_darkwood_log")),
                        BlockRegistry.darkwood_leaves = new LeavesBlock(LeavesBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f).tickRandomly().sound(SoundType.PLANT).notSolid()).setRegistryName(location("darkwood_leaves")),
                        BlockRegistry.darkwood_sapling = new SaplingBlock(() -> new DarkwoodTree(), SaplingBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).tickRandomly().doesNotBlockMovement().sound(SoundType.PLANT)).setRegistryName(location("darkwood_sapling")),
                        BlockRegistry.potted_darkwood_sapling = new FlowerPotBlock(BlockRegistry.darkwood_sapling, FlowerPotBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).sound(SoundType.STONE)).setRegistryName(location("potted_darkwood_sapling")),
                        BlockRegistry.darkwood_planks = new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_planks")),
                        BlockRegistry.stripped_darkwood_wood = new RotatedPillarBlock(RotatedPillarBlock.Properties.from(BlockRegistry.darkwood_log)).setRegistryName(location("stripped_darkwood_wood")),
                        BlockRegistry.darkwood_wood = new WoodBlock(() -> ItemRegistry.darkwood_log, WoodBlock.Properties.from(BlockRegistry.darkwood_log)).setRegistryName(location("darkwood_wood")),
                        BlockRegistry.darkwood_slab = new SlabBlock(SlabBlock.Properties.from(BlockRegistry.darkwood_planks)).setRegistryName(location("darkwood_slab")),
                        BlockRegistry.darkwood_stairs = new StairsBlock(BlockRegistry.darkwood_planks.getDefaultState(), StairsBlock.Properties.from(BlockRegistry.darkwood_planks)).setRegistryName(location("darkwood_stairs")),
                        BlockRegistry.darkwood_fence = new FenceBlock(FenceBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_fence")),
                        BlockRegistry.darkwood_fence_gate = new FenceGateBlock(FenceGateBlock.Properties.from(BlockRegistry.darkwood_fence)).setRegistryName(location("darkwood_fence_gate")),
                        BlockRegistry.darkwood_pressure_plate = new PressurePlateBlock(net.minecraft.block.PressurePlateBlock.Sensitivity.EVERYTHING, PressurePlateBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_pressure_plate")),
                        BlockRegistry.darkwood_button = new WoodButtonBlock(WoodButtonBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_button")),
                        BlockRegistry.darkwood_trapdoor = new TrapDoorBlock(TrapDoorBlock.Properties.create(Material.WOOD).hardnessAndResistance(3.0f).harvestTool(ToolType.AXE).notSolid().sound(SoundType.WOOD)).setRegistryName(location("darkwood_trapdoor")),
                        BlockRegistry.darkwood_door = new DoorBlock(DoorBlock.Properties.create(Material.WOOD).hardnessAndResistance(3.0f, 5.0f).harvestTool(ToolType.AXE).notSolid().sound(SoundType.WOOD)).setRegistryName(location("darkwood_door")),

                        BlockRegistry.elder_log = new LogBlock(MaterialColor.WOOD, LogBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("elder_log")),
                        BlockRegistry.stripped_elder_log = new LogBlock(MaterialColor.WOOD, LogBlock.Properties.from(BlockRegistry.elder_log)).setRegistryName(location("stripped_elder_log")),
                        BlockRegistry.elder_leaves = new ElderLeavesBlock(ElderLeavesBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f).tickRandomly().sound(SoundType.PLANT).notSolid()).setRegistryName(location("elder_leaves")),
                        BlockRegistry.elder_sapling = new SaplingBlock(() -> new ElderTree(), SaplingBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).tickRandomly().doesNotBlockMovement().sound(SoundType.PLANT)).setRegistryName(location("elder_sapling")),
                        BlockRegistry.potted_elder_sapling = new FlowerPotBlock(BlockRegistry.elder_sapling, FlowerPotBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).sound(SoundType.STONE)).setRegistryName(location("potted_elder_sapling")),
                        BlockRegistry.elder_planks = new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("elder_planks")),
                        BlockRegistry.stripped_elder_wood = new RotatedPillarBlock(RotatedPillarBlock.Properties.from(BlockRegistry.elder_log)).setRegistryName(location("stripped_elder_wood")),
                        BlockRegistry.elder_wood = new WoodBlock(() -> ItemRegistry.elder_log, WoodBlock.Properties.from(BlockRegistry.elder_log)).setRegistryName(location("elder_wood")),
                        BlockRegistry.elder_slab = new SlabBlock(SlabBlock.Properties.from(BlockRegistry.elder_planks)).setRegistryName(location("elder_slab")),
                        BlockRegistry.elder_stairs = new StairsBlock(BlockRegistry.elder_planks.getDefaultState(), StairsBlock.Properties.from(BlockRegistry.elder_planks)).setRegistryName(location("elder_stairs")),
                        BlockRegistry.elder_fence = new FenceBlock(FenceBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("elder_fence")),
                        BlockRegistry.elder_fence_gate = new FenceGateBlock(FenceGateBlock.Properties.from(BlockRegistry.elder_fence)).setRegistryName(location("elder_fence_gate")),
                        BlockRegistry.elder_pressure_plate = new PressurePlateBlock(net.minecraft.block.PressurePlateBlock.Sensitivity.EVERYTHING, PressurePlateBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("elder_pressure_plate")),
                        BlockRegistry.elder_button = new WoodButtonBlock(WoodButtonBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("elder_button")),
                        BlockRegistry.elder_trapdoor = new TrapDoorBlock(TrapDoorBlock.Properties.create(Material.WOOD).hardnessAndResistance(3.0f).harvestTool(ToolType.AXE).notSolid().sound(SoundType.WOOD)).setRegistryName(location("elder_trapdoor")),
                        BlockRegistry.elder_door = new DoorBlock(DoorBlock.Properties.create(Material.WOOD).hardnessAndResistance(3.0f, 5.0f).harvestTool(ToolType.AXE).notSolid().sound(SoundType.WOOD)).setRegistryName(location("elder_door")),
                        BlockRegistry.elder_fruit = new ElderFruitBlock(ElderFruitBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).doesNotBlockMovement().sound(SoundType.CROP).tickRandomly()).setRegistryName(location("elder_fruit")),
                        BlockRegistry.lavender = new FlowerBlock(Effects.HASTE, 8, FlowerBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0f).sound(SoundType.PLANT)).setRegistryName(location("lavender")),
                        BlockRegistry.potted_lavender = new FlowerPotBlock(BlockRegistry.lavender, FlowerPotBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).sound(SoundType.STONE)).setRegistryName(location("potted_lavender")),

                        BlockRegistry.oak_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("oak_beam")),
                        BlockRegistry.spruce_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("spruce_beam")),
                        BlockRegistry.birch_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("birch_beam")),
                        BlockRegistry.jungle_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("jungle_beam")),
                        BlockRegistry.acacia_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("acacia_beam")),
                        BlockRegistry.dark_oak_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("dark_oak_beam")),
                        BlockRegistry.darkwood_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("darkwood_beam")),
                        BlockRegistry.elder_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("elder_beam")),
                        BlockRegistry.oak_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("oak_small_beam")),
                        BlockRegistry.spruce_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("spruce_small_beam")),
                        BlockRegistry.birch_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("birch_small_beam")),
                        BlockRegistry.jungle_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("jungle_small_beam")),
                        BlockRegistry.acacia_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("acacia_small_beam")),
                        BlockRegistry.dark_oak_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("dark_oak_small_beam")),
                        BlockRegistry.darkwood_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("darkwood_small_beam")),
                        BlockRegistry.elder_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("elder_small_beam")),
                        BlockRegistry.oak_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("oak_panels")),
                        BlockRegistry.spruce_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("spruce_panels")),
                        BlockRegistry.birch_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("birch_panels")),
                        BlockRegistry.jungle_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("jungle_panels")),
                        BlockRegistry.acacia_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("acacia_panels")),
                        BlockRegistry.dark_oak_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("dark_oak_panels")),
                        BlockRegistry.darkwood_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("darkwood_panels")),
                        BlockRegistry.elder_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("elder_panels")),
                        BlockRegistry.dry_mud_bricks = new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F, 4.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)).setRegistryName(location("dry_mud_bricks")),
                        BlockRegistry.dry_mud_brick_slab = new SlabBlock(SlabBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F, 4.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)).setRegistryName(location("dry_mud_brick_slab")),
                        BlockRegistry.dry_mud_brick_stairs = new StairsBlock(BlockRegistry.dry_mud_bricks.getDefaultState(), StairsBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F, 4.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)).setRegistryName(location("dry_mud_brick_stairs")),
                        BlockRegistry.dry_mud_brick_wall = new WallBlock(WallBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F, 4.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)).setRegistryName(location("dry_mud_brick_wall")),
                        BlockRegistry.wet_mud_bricks = new WetMudBlock(BlockRegistry.dry_mud_bricks, WetMudBlock.Properties.create(Material.EARTH).sound(SoundType.SLIME).hardnessAndResistance(0.8f).harvestTool(ToolType.SHOVEL).tickRandomly()).setRegistryName(location("wet_mud_bricks")),
                        BlockRegistry.fiery_torch = new FieryTorchBlock(FieryTorchBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0f).lightValue(15).sound(SoundType.BAMBOO)).setRegistryName((location("fiery_torch"))),
                        BlockRegistry.wall_fiery_torch = new WallFieryTorchBlock(WallFieryTorchBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0f).lightValue(15).sound(SoundType.BAMBOO).lootFrom(BlockRegistry.fiery_torch)).setRegistryName(location("wall_fiery_torch")),
                        BlockRegistry.rope = new RopeBlock(RopeBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.0f)).setRegistryName(location("rope")),
                        BlockRegistry.rope_lantern = new RopeLanternBlock(RopeLanternBlock.Properties.create(Material.IRON).hardnessAndResistance(3.5F).sound(SoundType.LANTERN).lightValue(15).lootFrom(Blocks.LANTERN)).setRegistryName(location("rope_lantern")),
                        BlockRegistry.blueberry_bush = new BerryBushBlock(() -> ItemRegistry.blueberries, false, BerryBushBlock.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH)).setRegistryName(location("blueberry_bush")),
                        BlockRegistry.ceramic_lantern = new CeramicLanternBlock(CeramicLanternBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5f).lightValue(13)).setRegistryName(location("ceramic_lantern")),
                        BlockRegistry.turquoise_lunar_moth_jar = new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).lightValue(10), 1).setRegistryName(location("turquoise_lunar_moth_lantern")),
                        BlockRegistry.white_lunar_moth_jar = new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).lightValue(10), 2).setRegistryName(location("white_lunar_moth_lantern")),
                        BlockRegistry.lime_lunar_moth_jar = new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).lightValue(10), 3).setRegistryName(location("lime_lunar_moth_lantern")),
                        BlockRegistry.yellow_lunar_moth_jar = new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).lightValue(10), 4).setRegistryName(location("yellow_lunar_moth_lantern")),
                        BlockRegistry.orange_lunar_moth_jar = new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).lightValue(10), 5).setRegistryName(location("orange_lunar_moth_lantern")),
                        BlockRegistry.pink_lunar_moth_jar = new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).lightValue(10), 6).setRegistryName(location("pink_lunar_moth_lantern")),
                        BlockRegistry.growth_lamp = new GrowthLampBlock(GrowthLampBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).tickRandomly().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.35f).lightValue(14)).setRegistryName(location("growth_lamp")),
                        BlockRegistry.crate = new CrateBlock(CrateBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("crate")),

                        BlockRegistry.white_bedroll = new BedrollBlock(DyeColor.WHITE, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("white_bedroll")),
                        BlockRegistry.orange_bedroll = new BedrollBlock(DyeColor.ORANGE, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("orange_bedroll")),
                        BlockRegistry.magenta_bedroll = new BedrollBlock(DyeColor.MAGENTA, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("magenta_bedroll")),
                        BlockRegistry.light_blue_bedroll = new BedrollBlock(DyeColor.LIGHT_BLUE, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("light_blue_bedroll")),
                        BlockRegistry.yellow_bedroll = new BedrollBlock(DyeColor.YELLOW, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("yellow_bedroll")),
                        BlockRegistry.lime_bedroll = new BedrollBlock(DyeColor.LIME, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("lime_bedroll")),
                        BlockRegistry.pink_bedroll = new BedrollBlock(DyeColor.PINK, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("pink_bedroll")),
                        BlockRegistry.gray_bedroll = new BedrollBlock(DyeColor.GRAY, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("gray_bedroll")),
                        BlockRegistry.light_gray_bedroll = new BedrollBlock(DyeColor.LIGHT_GRAY, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("light_gray_bedroll")),
                        BlockRegistry.cyan_bedroll = new BedrollBlock(DyeColor.CYAN, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("cyan_bedroll")),
                        BlockRegistry.purple_bedroll = new BedrollBlock(DyeColor.PURPLE, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("purple_bedroll")),
                        BlockRegistry.blue_bedroll = new BedrollBlock(DyeColor.BLUE, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("blue_bedroll")),
                        BlockRegistry.brown_bedroll = new BedrollBlock(DyeColor.BROWN, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("brown_bedroll")),
                        BlockRegistry.green_bedroll = new BedrollBlock(DyeColor.GREEN, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("green_bedroll")),
                        BlockRegistry.red_bedroll = new BedrollBlock(DyeColor.RED, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("red_bedroll")),
                        BlockRegistry.black_bedroll = new BedrollBlock(DyeColor.BLACK, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)).setRegistryName(location("black_bedroll")),

                        BlockRegistry.white_soulfire = new SoulfireBlock(DyeColor.WHITE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("white_soulfire")),
                        BlockRegistry.orange_soulfire = new SoulfireBlock(DyeColor.ORANGE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("orange_soulfire")),
                        BlockRegistry.magenta_soulfire = new SoulfireBlock(DyeColor.MAGENTA, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("magenta_soulfire")),
                        BlockRegistry.light_blue_soulfire = new SoulfireBlock(DyeColor.LIGHT_BLUE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("light_blue_soulfire")),
                        BlockRegistry.yellow_soulfire = new SoulfireBlock(DyeColor.YELLOW, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("yellow_soulfire")),
                        BlockRegistry.lime_soulfire = new SoulfireBlock(DyeColor.LIME, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("lime_soulfire")),
                        BlockRegistry.pink_soulfire = new SoulfireBlock(DyeColor.PINK, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("pink_soulfire")),
                        BlockRegistry.gray_soulfire = new SoulfireBlock(DyeColor.GRAY, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("gray_soulfire")),
                        BlockRegistry.light_gray_soulfire = new SoulfireBlock(DyeColor.LIGHT_GRAY, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("light_gray_soulfire")),
                        BlockRegistry.cyan_soulfire = new SoulfireBlock(DyeColor.CYAN, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("cyan_soulfire")),
                        BlockRegistry.purple_soulfire = new SoulfireBlock(DyeColor.PURPLE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("purple_soulfire")),
                        BlockRegistry.blue_soulfire = new SoulfireBlock(DyeColor.BLUE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("blue_soulfire")),
                        BlockRegistry.brown_soulfire = new SoulfireBlock(DyeColor.BROWN, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("brown_soulfire")),
                        BlockRegistry.green_soulfire = new SoulfireBlock(DyeColor.GREEN, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("green_soulfire")),
                        BlockRegistry.red_soulfire = new SoulfireBlock(DyeColor.RED, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("red_soulfire")),
                        BlockRegistry.black_soulfire = new SoulfireBlock(DyeColor.BLACK, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("black_soulfire")),

                        BlockRegistry.woodcutter = new WoodcutterBlock(WoodcutterBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(1.75f)).setRegistryName(location("woodcutter"))
                );

        LOGGER.info("Blocks registered.");
    }

    // SOUND REGISTRATION
    @SubscribeEvent
    public static void onSoundRegistry(final RegistryEvent.Register<SoundEvent> SoundRegistryEvent)
    {
        SoundRegistryEvent.getRegistry().registerAll
                (
                        SoundEventRegistry.fill_bottle,
                        SoundEventRegistry.open_crate,
                        SoundEventRegistry.close_crate,
                        SoundEventRegistry.wood_cutter_take
                );

        LOGGER.info("Sound events registered.");
    }

    // ENTITY REGISTRATION
    @SubscribeEvent
    public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> EntityRegistryEvent)
    {
        EntityRegistryEvent.getRegistry().registerAll
                (
                        EntityRegistry.dreadfish_entity,
                        EntityRegistry.beetle_entity,
                        EntityRegistry.lunar_moth_entity
                );

        EntityRegistry.registerEntityWorldSpawns();
        LOGGER.info("Entities registered.");
    }

    // PARTICLE REGISTRATION
    @SubscribeEvent
    public static void onParticleRegistry(final RegistryEvent.Register<ParticleType<?>> ParticleRegistryEvent)
    {
        ParticleRegistryEvent.getRegistry().registerAll
                (
                        ParticleRegistry.magic_smoke.setRegistryName("magic_smoke"),
                        ParticleRegistry.fiery_glow.setRegistryName("fiery_glow"),
                        ParticleRegistry.fiery_spark.setRegistryName("fiery_spark"),
                        ParticleRegistry.magic_mist.setRegistryName("magic_mist"),
                        ParticleRegistry.magic_glitter.setRegistryName("magic_glitter")
                );

        LOGGER.info("Particles registered.");
    }

    // RECIPE REGISTRATION
    @SubscribeEvent
    public static void onRecipeRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> RecipeRegistryEvent)
    {

        RecipeRegistry.register(RecipeRegistryEvent);
        RecipeRegistryEvent.getRegistry().register(RecipeSerializers.woodcutting.setRegistryName(location("woodcutting")));

        LOGGER.info("Recipes registered.");
    }

    // GUI REGISTRATION
    @SubscribeEvent
    public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> ContainerRegistryEvent)
    {
        ContainerRegistryEvent.getRegistry().registerAll
                (
                        GUIRegistry.beetle_inv,
                        GUIRegistry.generic_9X12,
                        GUIRegistry.generic_9X24,
                        GUIRegistry.woodcutter,
                        GUIRegistry.travel_pack
                );

        LOGGER.info("GUI registered.");
    }

    // TILE ENTITY REGISTRATION
    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> TileEntityRegistryEvent)
    {
        TileEntityRegistryEvent.getRegistry().registerAll
                (
                        TileEntityRegistry.crate = TileEntityRegistry.register("crate", TileEntityType.Builder.create(CrateTileEntity::new, BlockRegistry.crate)),
                        TileEntityRegistry.growth_lamp = TileEntityRegistry.register("growth_lamp", TileEntityType.Builder.create(GrowthLampTileEntity::new, BlockRegistry.growth_lamp)),
                        TileEntityRegistry.small_beam = TileEntityRegistry.register("small_beam", TileEntityType.Builder.create(SmallBeamTileEntity::new, BlockRegistry.acacia_small_beam, BlockRegistry.birch_small_beam, BlockRegistry.dark_oak_small_beam, BlockRegistry.darkwood_small_beam, BlockRegistry.elder_small_beam, BlockRegistry.jungle_small_beam, BlockRegistry.oak_small_beam, BlockRegistry.spruce_small_beam))
                );

        LOGGER.info("Tile Entities registered.");
    }

    // BIOME REGISTRATION
    @SubscribeEvent
    public static void onBiomeRegistry(final RegistryEvent.Register<Biome> BiomeRegistryEvent)
    {
        IForgeRegistry<Biome> registry = BiomeRegistryEvent.getRegistry();

        BiomeRegistry.darkwood_forest = BiomeRegistry.registerBiome(registry, new DarkwoodForest(), "darkwood_forest", 6, false, BiomeManager.BiomeType.COOL, BiomeDictionary.Type.CONIFEROUS);

        LOGGER.info("Biomes registered.");
    }

    // FEATURE REGISTRATION
    @SubscribeEvent
    public static void onFeatureRegistry(final RegistryEvent.Register<Feature<?>> FeatureRegistryEvent)
    {
        IForgeRegistry<Feature<?>> registry = FeatureRegistryEvent.getRegistry();

        FeatureRegistry.elder_tree = FeatureRegistry.register(registry, new ElderTreeFeature(ElderTreeFeatureConfig::deserialize), "elder_tree");

        FeatureRegistry.elder_tree_feature = new ElderTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.elder_wood.getDefaultState().with(WoodBlock.dropSelf, false)), new SimpleBlockStateProvider(BlockRegistry.elder_log.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.elder_leaves.getDefaultState())).baseHeight(6).setSapling((IPlantable)BlockRegistry.elder_sapling).build();
        FeatureRegistry.blueberry_bush_feature = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.blueberry_bush.getDefaultState().with(BerryBushBlock.AGE, 3)), new SimpleBlockPlacer()).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK.getBlock())).func_227317_b_().build();
        FeatureRegistry.lavender_feature = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.lavender.getDefaultState()), new SimpleBlockPlacer()).tries(64).build();

        FeatureRegistry.spawnFeatures();
        LOGGER.info("Features registered.");
    }

    // TEXTURE STITCHING
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        if (event.getMap().getTextureLocation() == AtlasTexture.LOCATION_BLOCKS_TEXTURE) {
            event.addSprite(ItemTileEntityRenderer.bone_shield_tex);
            event.addSprite(ItemTileEntityRenderer.chitin_shield_tex);
            event.addSprite(ItemTileEntityRenderer.moonstone_shield_tex);
            event.addSprite(SmallBeamTileEntityRenderer.texture);
        }
        LOGGER.info("Textures stitched.");
    }

    public static ResourceLocation location(String name)
    {
        return new ResourceLocation(MODID, name);
    }
}
