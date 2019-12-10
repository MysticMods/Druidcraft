package com.vulp.druidcraft;

import com.vulp.druidcraft.blocks.*;
import com.vulp.druidcraft.blocks.DoorBlock;
import com.vulp.druidcraft.blocks.OreBlock;
import com.vulp.druidcraft.blocks.PressurePlateBlock;
import com.vulp.druidcraft.blocks.SaplingBlock;
import com.vulp.druidcraft.blocks.StairsBlock;
import com.vulp.druidcraft.blocks.FieryTorchBlock;
import com.vulp.druidcraft.blocks.TrapDoorBlock;
import com.vulp.druidcraft.blocks.WoodButtonBlock;
import com.vulp.druidcraft.blocks.trees.DarkwoodTree;
import com.vulp.druidcraft.blocks.trees.ElderTree;
import com.vulp.druidcraft.items.*;
import com.vulp.druidcraft.registry.*;
import com.vulp.druidcraft.world.biomes.DarkwoodForest;
import com.vulp.druidcraft.world.features.BerryBushFeature;
import com.vulp.druidcraft.world.features.ElderTreeFeature;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ScatteredPlantFeature;
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
                        // True items:
                        ItemRegistry.hemp = new Item(new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("hemp")),
                        ItemRegistry.hemp_seeds = new PlantableItem(new Item.Properties().group(DRUIDCRAFT), PlantType.Crop, BlockRegistry.hemp_crop).setRegistryName(location("hemp_seeds")),
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

                        ItemRegistry.chitin_helmet = new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlotType.HEAD, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("chitin_helmet")),
                        ItemRegistry.chitin_chestplate = new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlotType.CHEST, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("chitin_chestplate")),
                        ItemRegistry.chitin_leggings = new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlotType.LEGS, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("chitin_leggings")),
                        ItemRegistry.chitin_boots = new ArmorItem(ArmorMaterialRegistry.chitin, EquipmentSlotType.FEET, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("chitin_boots")),

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

                        ItemRegistry.wooden_sickle = new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ItemTier.WOOD).radius(1).setGroup(ItemGroup.TOOLS)).setRegistryName(location("wooden_sickle")),
                        ItemRegistry.stone_sickle = new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ItemTier.STONE).radius(2).setGroup(ItemGroup.TOOLS)).setRegistryName(location("stone_sickle")),
                        ItemRegistry.iron_sickle = new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ItemTier.IRON).radius(3).setGroup(ItemGroup.TOOLS)).setRegistryName(location("iron_sickle")),
                        ItemRegistry.gold_sickle = new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ItemTier.GOLD).radius(1).setGroup(ItemGroup.TOOLS)).setRegistryName(location("gold_sickle")),
                        ItemRegistry.diamond_sickle = new SickleItem(new ItemProperties().attackDamage(0).attackSpeed(-1.5f).tier(ItemTier.DIAMOND).radius(4).setGroup(ItemGroup.TOOLS)).setRegistryName(location("diamond_sickle")),

                        // Item-blocks:
                        ItemRegistry.amber_ore = new BlockItem(BlockRegistry.amber_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.amber_ore.getRegistryName()),
                        ItemRegistry.moonstone_ore = new BlockItem(BlockRegistry.moonstone_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.moonstone_ore.getRegistryName()),
                        ItemRegistry.fiery_glass_ore = new BlockItem(BlockRegistry.fiery_glass_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.fiery_glass_ore.getRegistryName()),
                        ItemRegistry.rockroot_ore = new BlockItem(BlockRegistry.rockroot_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.rockroot_ore.getRegistryName()),
                        ItemRegistry.amber_block = new BlockItem(BlockRegistry.amber_block, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.amber_block.getRegistryName()),
                        ItemRegistry.moonstone_block = new BlockItem(BlockRegistry.moonstone_block, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.moonstone_block.getRegistryName()),
                        ItemRegistry.fiery_glass_block = new BlockItem(BlockRegistry.fiery_glass_block, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.fiery_glass_block.getRegistryName()),
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
                        ItemRegistry.elder_leaves = new BlockItem(BlockRegistry.elder_leaves, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_leaves.getRegistryName()),
                        ItemRegistry.elder_sapling = new BlockItem(BlockRegistry.elder_sapling, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_sapling.getRegistryName()),
                        ItemRegistry.elder_wood = new BlockItem(BlockRegistry.elder_wood, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.elder_wood.getRegistryName()),


                        ItemRegistry.oak_beam = new BlockItem(BlockRegistry.oak_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.oak_beam.getRegistryName()),
                        ItemRegistry.spruce_beam = new BlockItem(BlockRegistry.spruce_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.spruce_beam.getRegistryName()),
                        ItemRegistry.birch_beam = new BlockItem(BlockRegistry.birch_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.birch_beam.getRegistryName()),
                        ItemRegistry.jungle_beam = new BlockItem(BlockRegistry.jungle_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.jungle_beam.getRegistryName()),
                        ItemRegistry.acacia_beam = new BlockItem(BlockRegistry.acacia_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.acacia_beam.getRegistryName()),
                        ItemRegistry.dark_oak_beam = new BlockItem(BlockRegistry.dark_oak_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.dark_oak_beam.getRegistryName()),
                        ItemRegistry.darkwood_beam = new BlockItem(BlockRegistry.darkwood_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_beam.getRegistryName()),
                        ItemRegistry.oak_small_beam = new BlockItem(BlockRegistry.oak_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.oak_small_beam.getRegistryName()),
                        ItemRegistry.spruce_small_beam = new BlockItem(BlockRegistry.spruce_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.spruce_small_beam.getRegistryName()),
                        ItemRegistry.birch_small_beam = new BlockItem(BlockRegistry.birch_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.birch_small_beam.getRegistryName()),
                        ItemRegistry.jungle_small_beam = new BlockItem(BlockRegistry.jungle_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.jungle_small_beam.getRegistryName()),
                        ItemRegistry.acacia_small_beam = new BlockItem(BlockRegistry.acacia_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.acacia_small_beam.getRegistryName()),
                        ItemRegistry.dark_oak_small_beam = new BlockItem(BlockRegistry.dark_oak_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.dark_oak_small_beam.getRegistryName()),
                        ItemRegistry.darkwood_small_beam = new BlockItem(BlockRegistry.darkwood_small_beam, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_small_beam.getRegistryName()),
                        ItemRegistry.oak_panels = new BlockItem(BlockRegistry.oak_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.oak_panels.getRegistryName()),
                        ItemRegistry.spruce_panels = new BlockItem(BlockRegistry.spruce_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.spruce_panels.getRegistryName()),
                        ItemRegistry.birch_panels = new BlockItem(BlockRegistry.birch_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.birch_panels.getRegistryName()),
                        ItemRegistry.jungle_panels = new BlockItem(BlockRegistry.jungle_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.jungle_panels.getRegistryName()),
                        ItemRegistry.acacia_panels = new BlockItem(BlockRegistry.acacia_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.acacia_panels.getRegistryName()),
                        ItemRegistry.dark_oak_panels = new BlockItem(BlockRegistry.dark_oak_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.dark_oak_panels.getRegistryName()),
                        ItemRegistry.darkwood_panels = new BlockItem(BlockRegistry.darkwood_panels, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.darkwood_panels.getRegistryName()),
                        ItemRegistry.wet_mud_bricks = new BlockItem(BlockRegistry.wet_mud_bricks, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.wet_mud_bricks.getRegistryName()),
                        ItemRegistry.dry_mud_bricks = new BlockItem(BlockRegistry.dry_mud_bricks, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.dry_mud_bricks.getRegistryName()),
                        ItemRegistry.fiery_torch = new WallOrFloorItem(BlockRegistry.fiery_torch, BlockRegistry.wall_fiery_torch, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.fiery_torch.getRegistryName()),
                        ItemRegistry.rope = new BlockItem(BlockRegistry.rope, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.rope.getRegistryName()),
                        ItemRegistry.blueberries = new BlockNamedItem(BlockRegistry.blueberry_bush, new Item.Properties().group(DRUIDCRAFT).food(FoodRegistry.blueberries)).setRegistryName(location("blueberries")),
                        ItemRegistry.elderberries = new Item(new Item.Properties().group(DRUIDCRAFT).food(FoodRegistry.elderberries)).setRegistryName(location("elderberries")),
                        ItemRegistry.elderflower = new Item(new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("elderflower")),
                        ItemRegistry.blueberry_muffin = new Item(new Item.Properties().group(DRUIDCRAFT).food(FoodRegistry.blueberry_muffin)).setRegistryName(location("blueberry_muffin")),
                        ItemRegistry.apple_elderberry_crumble = new Item(new Item.Properties().group(DRUIDCRAFT).food(FoodRegistry.apple_elderberry_crumble)).setRegistryName(location("apple_elderberry_crumble")),
                        ItemRegistry.elderflower_cordial = new DrinkableItem(new DrinkableItem.Properties().group(DRUIDCRAFT).food(FoodRegistry.elderflower_cordial)).setRegistryName(location("elderflower_cordial")),




                        ItemRegistry.black_soulfire = new BlockItem(BlockRegistry.black_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.black_soulfire.getRegistryName()),
                        ItemRegistry.red_soulfire = new BlockItem(BlockRegistry.red_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.red_soulfire.getRegistryName()),
                        ItemRegistry.green_soulfire = new BlockItem(BlockRegistry.green_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.green_soulfire.getRegistryName()),
                        ItemRegistry.brown_soulfire = new BlockItem(BlockRegistry.brown_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.brown_soulfire.getRegistryName()),
                        ItemRegistry.blue_soulfire = new BlockItem(BlockRegistry.blue_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.blue_soulfire.getRegistryName()),
                        ItemRegistry.purple_soulfire = new BlockItem(BlockRegistry.purple_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.purple_soulfire.getRegistryName()),
                        ItemRegistry.cyan_soulfire = new BlockItem(BlockRegistry.cyan_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.cyan_soulfire.getRegistryName()),
                        ItemRegistry.light_gray_soulfire = new BlockItem(BlockRegistry.light_gray_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.light_gray_soulfire.getRegistryName()),
                        ItemRegistry.gray_soulfire = new BlockItem(BlockRegistry.gray_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.gray_soulfire.getRegistryName()),
                        ItemRegistry.pink_soulfire = new BlockItem(BlockRegistry.pink_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.pink_soulfire.getRegistryName()),
                        ItemRegistry.lime_soulfire = new BlockItem(BlockRegistry.lime_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.lime_soulfire.getRegistryName()),
                        ItemRegistry.yellow_soulfire = new BlockItem(BlockRegistry.yellow_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.yellow_soulfire.getRegistryName()),
                        ItemRegistry.light_blue_soulfire = new BlockItem(BlockRegistry.light_blue_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.light_blue_soulfire.getRegistryName()),
                        ItemRegistry.magenta_soulfire = new BlockItem(BlockRegistry.magenta_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.magenta_soulfire.getRegistryName()),
                        ItemRegistry.orange_soulfire = new BlockItem(BlockRegistry.orange_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.orange_soulfire.getRegistryName()),
                        ItemRegistry.white_soulfire = new BlockItem(BlockRegistry.white_soulfire, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.white_soulfire.getRegistryName())
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
                        BlockRegistry.hemp_crop = new HempBlock(HempBlock.Properties.create(Material.PLANTS).sound(SoundType.CROP).hardnessAndResistance(0.0f).doesNotBlockMovement().tickRandomly()).setRegistryName(location("hemp_crop")),
                        BlockRegistry.amber_ore = new OreBlock(OreBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(1), 6, 12).setRegistryName(location("amber_ore")),
                        BlockRegistry.moonstone_ore = new OreBlock(OreBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3), 6, 14).setRegistryName(location("moonstone_ore")),
                        BlockRegistry.fiery_glass_ore = new OreBlock(OreBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2), 4, 10).setRegistryName(location("fiery_glass_ore")),
                        BlockRegistry.rockroot_ore = new OreBlock(OreBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0), 2, 10).setRegistryName(location("rockroot_ore")),
                        BlockRegistry.amber_block = new BeaconBaseBlock(BeaconBaseBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)).setRegistryName(location("amber_block")),
                        BlockRegistry.moonstone_block = new BeaconBaseBlock(BeaconBaseBlock.Properties.create(Material.ROCK).hardnessAndResistance(6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)).setRegistryName(location("moonstone_block")),
                        BlockRegistry.fiery_glass_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)).setRegistryName(location("fiery_glass_block")),
                        BlockRegistry.rockroot_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)).setRegistryName(location("rockroot_block")),
                        BlockRegistry.darkwood_log = new LogBlock(MaterialColor.WOOD, LogBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_log")),
                        BlockRegistry.stripped_darkwood_log = new LogBlock(MaterialColor.WOOD, LogBlock.Properties.from(BlockRegistry.darkwood_log)).setRegistryName(location("stripped_darkwood_log")),
                        BlockRegistry.darkwood_leaves = new LeavesBlock(LeavesBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f).tickRandomly().sound(SoundType.PLANT)).setRegistryName(location("darkwood_leaves")),
                        BlockRegistry.darkwood_sapling = new SaplingBlock(new DarkwoodTree(), SaplingBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).doesNotBlockMovement().sound(SoundType.PLANT)).setRegistryName(location("darkwood_sapling")),
                        BlockRegistry.potted_darkwood_sapling = new FlowerPotBlock(BlockRegistry.darkwood_sapling, FlowerPotBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).sound(SoundType.STONE)).setRegistryName(location("potted_darkwood_sapling")),
                        BlockRegistry.darkwood_planks = new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_planks")),
                        BlockRegistry.stripped_darkwood_wood = new RotatedPillarBlock(RotatedPillarBlock.Properties.from(BlockRegistry.darkwood_log)).setRegistryName(location("stripped_darkwood_wood")),
                        BlockRegistry.darkwood_wood = new RotatedPillarBlock(RotatedPillarBlock.Properties.from(BlockRegistry.darkwood_log)).setRegistryName(location("darkwood_wood")),
                        BlockRegistry.darkwood_slab = new SlabBlock(SlabBlock.Properties.from(BlockRegistry.darkwood_planks)).setRegistryName(location("darkwood_slab")),
                        BlockRegistry.darkwood_stairs = new StairsBlock(BlockRegistry.darkwood_planks.getDefaultState(),StairsBlock.Properties.from(BlockRegistry.darkwood_planks)).setRegistryName(location("darkwood_stairs")),
                        BlockRegistry.darkwood_fence = new FenceBlock(FenceBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_fence")),
                        BlockRegistry.darkwood_fence_gate = new FenceGateBlock(FenceGateBlock.Properties.from(BlockRegistry.darkwood_fence)).setRegistryName(location("darkwood_fence_gate")),
                        BlockRegistry.darkwood_pressure_plate = new PressurePlateBlock(net.minecraft.block.PressurePlateBlock.Sensitivity.EVERYTHING, PressurePlateBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_pressure_plate")),
                        BlockRegistry.darkwood_button = new WoodButtonBlock(WoodButtonBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_button")),
                        BlockRegistry.darkwood_trapdoor = new TrapDoorBlock(TrapDoorBlock.Properties.create(Material.WOOD).hardnessAndResistance(3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_trapdoor")),
                        BlockRegistry.darkwood_door = new DoorBlock(DoorBlock.Properties.create(Material.WOOD).hardnessAndResistance(3.0f, 5.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("darkwood_door")),

                        BlockRegistry.elder_sapling = new SaplingBlock(new ElderTree(), SaplingBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).doesNotBlockMovement().sound(SoundType.PLANT)).setRegistryName(location("elder_sapling")),
                        BlockRegistry.elder_fruit = new ElderFruitBlock(ElderFruitBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).doesNotBlockMovement().sound(SoundType.PLANT).tickRandomly()).setRegistryName(location("elder_fruit")),
                        BlockRegistry.elder_log = new LogBlock(MaterialColor.WOOD, LogBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName(location("elder_log")),
                        BlockRegistry.elder_wood = new RotatedPillarBlock(RotatedPillarBlock.Properties.from(BlockRegistry.elder_log)).setRegistryName(location("elder_wood")),
                        BlockRegistry.elder_leaves = new ElderLeavesBlock(ElderLeavesBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f).tickRandomly().sound(SoundType.PLANT)).setRegistryName(location("elder_leaves")),

                        BlockRegistry.oak_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("oak_beam")),
                        BlockRegistry.spruce_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("spruce_beam")),
                        BlockRegistry.birch_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("birch_beam")),
                        BlockRegistry.jungle_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("jungle_beam")),
                        BlockRegistry.acacia_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("acacia_beam")),
                        BlockRegistry.dark_oak_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("dark_oak_beam")),
                        BlockRegistry.darkwood_beam = new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("darkwood_beam")),
                        BlockRegistry.oak_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("oak_small_beam")),
                        BlockRegistry.spruce_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("spruce_small_beam")),
                        BlockRegistry.birch_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("birch_small_beam")),
                        BlockRegistry.jungle_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("jungle_small_beam")),
                        BlockRegistry.acacia_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("acacia_small_beam")),
                        BlockRegistry.dark_oak_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("dark_oak_small_beam")),
                        BlockRegistry.darkwood_small_beam = new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)).setRegistryName(location("darkwood_small_beam")),
                        BlockRegistry.oak_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("oak_panels")),
                        BlockRegistry.spruce_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("spruce_panels")),
                        BlockRegistry.birch_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("birch_panels")),
                        BlockRegistry.jungle_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("jungle_panels")),
                        BlockRegistry.acacia_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("acacia_panels")),
                        BlockRegistry.dark_oak_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("dark_oak_panels")),
                        BlockRegistry.darkwood_panels = new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)).setRegistryName(location("darkwood_panels")),
                        BlockRegistry.dry_mud_bricks = new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0F, 4.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)).setRegistryName(location("dry_mud_bricks")),
                        BlockRegistry.wet_mud_bricks = new WetMudBlock(BlockRegistry.dry_mud_bricks, WetMudBlock.Properties.create(Material.EARTH).sound(SoundType.SLIME).hardnessAndResistance(0.8f).harvestTool(ToolType.SHOVEL).tickRandomly()).setRegistryName(location("wet_mud_bricks")),
                        BlockRegistry.fiery_torch = new FieryTorchBlock(FieryTorchBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0f).lightValue(15).sound(SoundType.BAMBOO)).setRegistryName((location("fiery_torch"))),
                        BlockRegistry.wall_fiery_torch = new WallFieryTorchBlock(WallFieryTorchBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0f).lightValue(15).sound(SoundType.BAMBOO).lootFrom(BlockRegistry.fiery_torch)).setRegistryName(location("wall_fiery_torch")),
                        BlockRegistry.rope = new RopeBlock(RopeBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.0f)).setRegistryName(location("rope")),
                        BlockRegistry.rope_lantern = new RopeLanternBlock(RopeLanternBlock.Properties.create(Material.IRON).hardnessAndResistance(3.5F).sound(SoundType.LANTERN).lightValue(15).lootFrom(Blocks.LANTERN)).setRegistryName(location("rope_lantern")),
                        BlockRegistry.blueberry_bush = new BerryBushBlock(ItemRegistry.blueberries, false, BerryBushBlock.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH)).setRegistryName(location("blueberry_bush")),

                        BlockRegistry.black_soulfire = new SoulfireBlock(DyeColor.BLACK, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("black_soulfire")),
                        BlockRegistry.red_soulfire = new SoulfireBlock(DyeColor.RED, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("red_soulfire")),
                        BlockRegistry.green_soulfire = new SoulfireBlock(DyeColor.GREEN, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("green_soulfire")),
                        BlockRegistry.brown_soulfire = new SoulfireBlock(DyeColor.BROWN, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("brown_soulfire")),
                        BlockRegistry.blue_soulfire = new SoulfireBlock(DyeColor.BLUE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("blue_soulfire")),
                        BlockRegistry.purple_soulfire = new SoulfireBlock(DyeColor.PURPLE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("purple_soulfire")),
                        BlockRegistry.cyan_soulfire = new SoulfireBlock(DyeColor.CYAN, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("cyan_soulfire")),
                        BlockRegistry.light_gray_soulfire = new SoulfireBlock(DyeColor.LIGHT_GRAY, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("light_gray_soulfire")),
                        BlockRegistry.gray_soulfire = new SoulfireBlock(DyeColor.GRAY, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("gray_soulfire")),
                        BlockRegistry.pink_soulfire = new SoulfireBlock(DyeColor.PINK, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("pink_soulfire")),
                        BlockRegistry.lime_soulfire = new SoulfireBlock(DyeColor.LIME, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("lime_soulfire")),
                        BlockRegistry.yellow_soulfire = new SoulfireBlock(DyeColor.YELLOW, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("yellow_soulfire")),
                        BlockRegistry.light_blue_soulfire = new SoulfireBlock(DyeColor.LIGHT_BLUE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("light_blue_soulfire")),
                        BlockRegistry.magenta_soulfire = new SoulfireBlock(DyeColor.MAGENTA, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("magenta_soulfire")),
                        BlockRegistry.orange_soulfire = new SoulfireBlock(DyeColor.ORANGE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("orange_soulfire")),
                        BlockRegistry.white_soulfire = new SoulfireBlock(DyeColor.WHITE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().lightValue(13)).setRegistryName(location("white_soulfire"))
                );

        LOGGER.info("Blocks registered.");
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

        LOGGER.info("Recipes registered.");
    }

    // GUI REGISTRATION
    @SubscribeEvent
    public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> ContainerRegistryEvent)
    {
        ContainerRegistryEvent.getRegistry().registerAll
                (
                        GUIRegistry.beetle_inv
                );

        LOGGER.info("GUI registered.");
    }

    // FEATURE REGISTRATION
    @SubscribeEvent
    public static void onFeatureRegistry(final RegistryEvent.Register<Feature<?>> FeatureRegistryEvent)
    {
        IForgeRegistry<Feature<?>> registry = FeatureRegistryEvent.getRegistry();

        FeatureRegistry.elder_tree = FeatureRegistry.register(registry, new ElderTreeFeature(NoFeatureConfig::deserialize, true), "elder_tree");
        FeatureRegistry.blueberry_bush = FeatureRegistry.register(registry, new BerryBushFeature(NoFeatureConfig::deserialize, BlockRegistry.blueberry_bush.getDefaultState().with(BerryBushBlock.AGE, 3)), "blueberry_bush");

        FeatureRegistry.spawnFeatures();
        LOGGER.info("Features registered.");
    }

    // BIOME REGISTRATION
    @SubscribeEvent
    public static void onBiomeRegistry(final RegistryEvent.Register<Biome> BiomeRegistryEvent)
    {
        IForgeRegistry<Biome> registry = BiomeRegistryEvent.getRegistry();

        BiomeRegistry.darkwood_forest = BiomeRegistry.registerBiome(registry, new DarkwoodForest(), "darkwood_forest", 6, false, BiomeManager.BiomeType.COOL, BiomeDictionary.Type.CONIFEROUS);

        LOGGER.info("Biomes registered.");
    }

    public static ResourceLocation location(String name)
    {
        return new ResourceLocation(MODID, name);
    }
}
