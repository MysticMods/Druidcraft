package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.blocks.*;
import com.vulp.druidcraft.blocks.FungusBlock;
import com.vulp.druidcraft.blocks.trees.DarkwoodTree;
import com.vulp.druidcraft.blocks.trees.ElderTree;
import com.vulp.druidcraft.blocks.trees.GiantHeartburnFungus;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DyeColor;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.function.ToIntFunction;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {
  private static Set<Block> BLOCKS = new HashSet<>();

  public static Block debug_block = register("debug", new DebugBlock(DebugBlock.Properties.create(Material.CLAY)));
  public static Block hemp_crop = register("hemp_crop", new HempBlock(HempBlock.Properties.create(Material.PLANTS).sound(SoundType.CROP).hardnessAndResistance(0.0f).doesNotBlockMovement().tickRandomly()));
  public static Block amber_ore = register("amber_ore", new OreXPBlock(OreBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(1), 3, 6));
  public static Block moonstone_ore = register("moonstone_ore", new OreXPBlock(OreBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3), 3, 7));
  public static Block fiery_glass_ore = register("fiery_glass_ore", new OreXPBlock(OreBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2), 2, 5));
  public static Block rockroot_ore = register("rockroot_ore", new OreXPBlock(OreBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0), 1, 5));
  public static Block amber_block = register("amber_block", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).setRequiresTool().notSolid().harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)));
  public static Block moonstone_block = register("moonstone_block", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(6.0f).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE)));
  public static Block fiery_glass_block = register("fiery_glass_block", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.0f).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).setLightLevel((light) -> 15).sound(SoundType.STONE)));
  public static Block rockroot_block = register("rockroot_block", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).sound(SoundType.STONE)));
  public static Block darkwood_log = register("darkwood_log", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).harvestLevel(0).sound(SoundType.WOOD)));
  public static Block stripped_darkwood_log = register("stripped_darkwood_log", new RotatedPillarBlock(RotatedPillarBlock.Properties.from(darkwood_log)));
  public static Block darkwood_leaves = register("darkwood_leaves", new LeavesBlock(LeavesBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f).tickRandomly().sound(SoundType.PLANT).notSolid()));
  public static Block potted_darkwood_sapling = register("potted_darkwood_sapling", new FlowerPotBlock(null, () -> BlockRegistry.darkwood_sapling, FlowerPotBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).sound(SoundType.STONE)));
  public static Block darkwood_planks = register("darkwood_planks", new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
  public static Block stripped_darkwood_wood = register("stripped_darkwood_wood", new RotatedPillarBlock(RotatedPillarBlock.Properties.from(darkwood_log)));
  public static Block darkwood_wood = register("darkwood_wood", new WoodBlock(() -> ItemRegistry.darkwood_log, WoodBlock.Properties.from(darkwood_log)));
  public static Block darkwood_slab = register("darkwood_slab", new SlabBlock(SlabBlock.Properties.from(darkwood_planks)));
  public static Block darkwood_stairs = register("darkwood_stairs", new StairsBlock(() -> darkwood_planks.getDefaultState(), StairsBlock.Properties.from(darkwood_planks)));
  public static Block darkwood_fence = register("darkwood_fence", new FenceBlock(FenceBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
  public static Block darkwood_fence_gate = register("darkwood_fence_gate", new FenceGateBlock(FenceGateBlock.Properties.from(darkwood_fence)));
  public static Block darkwood_pressure_plate = register("darkwood_pressure_plate", new PressurePlateBlock(net.minecraft.block.PressurePlateBlock.Sensitivity.EVERYTHING, PressurePlateBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
  public static Block darkwood_button = register("darkwood_button", new WoodButtonBlock(WoodButtonBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
  public static Block darkwood_trapdoor = register("darkwood_trapdoor", new TrapDoorBlock(TrapDoorBlock.Properties.create(Material.WOOD).hardnessAndResistance(3.0f).harvestTool(ToolType.AXE).notSolid().sound(SoundType.WOOD)));
  public static Block darkwood_door = register("darkwood_door", new DoorBlock(DoorBlock.Properties.create(Material.WOOD).hardnessAndResistance(3.0f, 5.0f).harvestTool(ToolType.AXE).notSolid().sound(SoundType.WOOD)));
  public static Block darkwood_sapling = register("darkwood_sapling", new SaplingBlock(new DarkwoodTree(), SaplingBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).tickRandomly().doesNotBlockMovement().sound(SoundType.PLANT)));
  public static Block darkwood_sign = register("darkwood_sign", new CustomStandingSignBlock(CustomStandingSignBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), WoodTypeRegistry.DARKWOOD));
  public static Block darkwood_wall_sign = register("darkwood_wall_sign", new CustomWallSignBlock(CustomWallSignBlock.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), WoodTypeRegistry.DARKWOOD));

  public static Block elder_log = register("elder_log", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
  public static Block stripped_elder_log = register("stripped_elder_log", new RotatedPillarBlock(RotatedPillarBlock.Properties.from(elder_log)));
  public static Block elder_leaves = register("elder_leaves", new ElderLeavesBlock(ElderLeavesBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2f).tickRandomly().sound(SoundType.PLANT).notSolid()));
  public static Block potted_elder_sapling = register("potted_elder_sapling", new FlowerPotBlock(null, () -> BlockRegistry.elder_sapling, FlowerPotBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).sound(SoundType.STONE)));
  public static Block elder_planks = register("elder_planks", new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
  public static Block stripped_elder_wood = register("stripped_elder_wood", new RotatedPillarBlock(RotatedPillarBlock.Properties.from(elder_log)));
  public static Block elder_wood = register("elder_wood", new WoodBlock(() -> ItemRegistry.elder_log, WoodBlock.Properties.from(elder_log)));
  public static Block elder_slab = register("elder_slab", new SlabBlock(SlabBlock.Properties.from(elder_planks)));
  public static Block elder_stairs = register("elder_stairs", new StairsBlock(() -> elder_planks.getDefaultState(), StairsBlock.Properties.from(elder_planks)));
  public static Block elder_fence = register("elder_fence", new FenceBlock(FenceBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
  public static Block elder_fence_gate = register("elder_fence_gate", new FenceGateBlock(FenceGateBlock.Properties.from(elder_fence)));
  public static Block elder_pressure_plate = register("elder_pressure_plate", new PressurePlateBlock(net.minecraft.block.PressurePlateBlock.Sensitivity.EVERYTHING, PressurePlateBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
  public static Block elder_button = register("elder_button", new WoodButtonBlock(WoodButtonBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.5f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
  public static Block elder_trapdoor = register("elder_trapdoor", new TrapDoorBlock(TrapDoorBlock.Properties.create(Material.WOOD).hardnessAndResistance(3.0f).harvestTool(ToolType.AXE).notSolid().sound(SoundType.WOOD)));
  public static Block elder_door = register("elder_door", new DoorBlock(DoorBlock.Properties.create(Material.WOOD).hardnessAndResistance(3.0f, 5.0f).harvestTool(ToolType.AXE).notSolid().sound(SoundType.WOOD)));
  public static Block elder_sapling = register("elder_sapling", new SaplingBlock(new ElderTree(), SaplingBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).tickRandomly().doesNotBlockMovement().sound(SoundType.PLANT)));
  public static Block elder_fruit = register("elder_fruit", new ElderFruitBlock(ElderFruitBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).doesNotBlockMovement().sound(SoundType.CROP).tickRandomly()));
  public static Block elder_leaf_layer = register("elder_leaf_layer", new ElderLeafLayerBlock(ElderLeafLayerBlock.Properties.create(Material.PLANTS).hardnessAndResistance(0.2f).doesNotBlockMovement().sound(SoundType.CROP).tickRandomly().notSolid()));
  public static Block lavender = register("lavender", new FlowerBlock(Effects.HASTE, 8, FlowerBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0f).sound(SoundType.PLANT)));
  public static Block potted_lavender = register("potted_lavender", new FlowerPotBlock(null, () -> lavender, FlowerPotBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0f).sound(SoundType.STONE)));

  public static Block oak_beam = register("oak_beam", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block spruce_beam = register("spruce_beam", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block birch_beam = register("birch_beam", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block jungle_beam = register("jungle_beam", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block acacia_beam = register("acacia_beam", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block dark_oak_beam = register("dark_oak_beam", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block darkwood_beam = register("darkwood_beam", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block elder_beam = register("elder_beam", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block oak_small_beam = register("oak_small_beam", new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block spruce_small_beam = register("spruce_small_beam", new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block birch_small_beam = register("birch_small_beam", new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block jungle_small_beam = register("jungle_small_beam", new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block acacia_small_beam = register("acacia_small_beam", new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block dark_oak_small_beam = register("dark_oak_small_beam", new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block darkwood_small_beam = register("darkwood_small_beam", new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block elder_small_beam = register("elder_small_beam", new SmallBeamBlock(SmallBeamBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));
  public static Block oak_panels = register("oak_panels", new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
  public static Block spruce_panels = register("spruce_panels", new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
  public static Block birch_panels = register("birch_panels", new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
  public static Block jungle_panels = register("jungle_panels", new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
  public static Block acacia_panels = register("acacia_panels", new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
  public static Block dark_oak_panels = register("dark_oak_panels", new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
  public static Block darkwood_panels = register("darkwood_panels", new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
  public static Block elder_panels = register("elder_panels", new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f, 3.0f).harvestTool(ToolType.AXE)));
  public static Block dry_mud_bricks = register("dry_mud_bricks", new Block(Block.Properties.create(Material.ROCK).setRequiresTool().sound(SoundType.STONE).hardnessAndResistance(2.0F, 4.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
  public static Block dry_mud_brick_slab = register("dry_mud_brick_slab", new SlabBlock(SlabBlock.Properties.create(Material.ROCK).setRequiresTool().sound(SoundType.STONE).hardnessAndResistance(2.0F, 4.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
  public static Block dry_mud_brick_stairs = register("dry_mud_brick_stairs", new StairsBlock(() -> dry_mud_bricks.getDefaultState(), StairsBlock.Properties.create(Material.ROCK).setRequiresTool().sound(SoundType.STONE).hardnessAndResistance(2.0F, 4.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
  public static Block dry_mud_brick_wall = register("dry_mud_brick_wall", new WallBlock(WallBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(2.0F, 4.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
  public static Block wet_mud_bricks = register("wet_mud_bricks", new WetMudBlock(dry_mud_bricks, WetMudBlock.Properties.create(Material.EARTH).sound(SoundType.SLIME).hardnessAndResistance(0.8f).harvestTool(ToolType.SHOVEL).tickRandomly()));
  public static Block fiery_torch = register("fiery_torch", new FieryTorchBlock(FieryTorchBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0f).setLightLevel((light) -> (15)).sound(SoundType.BAMBOO)));
  public static Block wall_fiery_torch = register("wall_fiery_torch", new WallFieryTorchBlock(WallFieryTorchBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0f).setLightLevel((light) -> (15)).sound(SoundType.BAMBOO).lootFrom(fiery_torch)));
  public static Block rope = register("rope", new RopeBlock(RopeBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.0f)));
  public static Block rope_lantern = register("rope_lantern", new RopeLanternBlock(RopeLanternBlock.Properties.create(Material.IRON).hardnessAndResistance(3.5F).sound(SoundType.LANTERN).setLightLevel((light) -> (15)).lootFrom(Blocks.LANTERN)));
  public static Block blueberry_bush = register("blueberry_bush", new BerryBushBlock(() -> ItemRegistry.blueberries, false, BerryBushBlock.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH)));
  public static Block ceramic_lantern = register("ceramic_lantern", new CeramicLanternBlock(CeramicLanternBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5f).setLightLevel((light) -> (13))));
  public static Block turquoise_lunar_moth_jar = register("turquoise_lunar_moth_lantern", new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).setLightLevel((light) -> (10)), 1));
  public static Block white_lunar_moth_jar = register("white_lunar_moth_lantern", new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).setLightLevel((light) -> (10)), 2));
  public static Block lime_lunar_moth_jar = register("lime_lunar_moth_lantern", new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).setLightLevel((light) -> (10)).tickRandomly(), 3));
  public static Block yellow_lunar_moth_jar = register("yellow_lunar_moth_lantern", new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).setLightLevel((light) -> (10)), 4));
  public static Block orange_lunar_moth_jar = register("orange_lunar_moth_lantern", new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).setLightLevel((light) -> (10)), 5));
  public static Block pink_lunar_moth_jar = register("pink_lunar_moth_lantern", new LunarMothJarBlock(LunarMothJarBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(1.0f).setLightLevel((light) -> (10)), 6));
  public static Block growth_lamp = register("growth_lamp", new GrowthLampBlock(GrowthLampBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).tickRandomly().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.35f).setLightLevel((light) -> (14))));
  public static Block crate = register("crate", new CrateBlock(CrateBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE)));

  public static Block white_bedroll = register("white_bedroll", new BedrollBlock(DyeColor.WHITE, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block orange_bedroll = register("orange_bedroll", new BedrollBlock(DyeColor.ORANGE, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block magenta_bedroll = register("magenta_bedroll", new BedrollBlock(DyeColor.MAGENTA, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block light_blue_bedroll = register("light_blue_bedroll", new BedrollBlock(DyeColor.LIGHT_BLUE, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block yellow_bedroll = register("yellow_bedroll", new BedrollBlock(DyeColor.YELLOW, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block lime_bedroll = register("lime_bedroll", new BedrollBlock(DyeColor.LIME, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block pink_bedroll = register("pink_bedroll", new BedrollBlock(DyeColor.PINK, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block gray_bedroll = register("gray_bedroll", new BedrollBlock(DyeColor.GRAY, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block light_gray_bedroll = register("light_gray_bedroll", new BedrollBlock(DyeColor.LIGHT_GRAY, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block cyan_bedroll = register("cyan_bedroll", new BedrollBlock(DyeColor.CYAN, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block purple_bedroll = register("purple_bedroll", new BedrollBlock(DyeColor.PURPLE, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block blue_bedroll = register("blue_bedroll", new BedrollBlock(DyeColor.BLUE, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block brown_bedroll = register("brown_bedroll", new BedrollBlock(DyeColor.BROWN, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block green_bedroll = register("green_bedroll", new BedrollBlock(DyeColor.GREEN, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block red_bedroll = register("red_bedroll", new BedrollBlock(DyeColor.RED, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));
  public static Block black_bedroll = register("black_bedroll", new BedrollBlock(DyeColor.BLACK, BedrollBlock.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(0.3f)));

  public static Block white_soulfire = register("white_soulfire", new SoulfireBlock(DyeColor.WHITE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block orange_soulfire = register("orange_soulfire", new SoulfireBlock(DyeColor.ORANGE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block magenta_soulfire = register("magenta_soulfire", new SoulfireBlock(DyeColor.MAGENTA, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block light_blue_soulfire = register("light_blue_soulfire", new SoulfireBlock(DyeColor.LIGHT_BLUE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block yellow_soulfire = register("yellow_soulfire", new SoulfireBlock(DyeColor.YELLOW, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block lime_soulfire = register("lime_soulfire", new SoulfireBlock(DyeColor.LIME, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block pink_soulfire = register("pink_soulfire", new SoulfireBlock(DyeColor.PINK, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block gray_soulfire = register("gray_soulfire", new SoulfireBlock(DyeColor.GRAY, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block light_gray_soulfire = register("light_gray_soulfire", new SoulfireBlock(DyeColor.LIGHT_GRAY, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block cyan_soulfire = register("cyan_soulfire", new SoulfireBlock(DyeColor.CYAN, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block purple_soulfire = register("purple_soulfire", new SoulfireBlock(DyeColor.PURPLE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block blue_soulfire = register("blue_soulfire", new SoulfireBlock(DyeColor.BLUE, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block brown_soulfire = register("brown_soulfire", new SoulfireBlock(DyeColor.BROWN, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block green_soulfire = register("green_soulfire", new SoulfireBlock(DyeColor.GREEN, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block red_soulfire = register("red_soulfire", new SoulfireBlock(DyeColor.RED, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block black_soulfire = register("black_soulfire", new SoulfireBlock(DyeColor.BLACK, SoulfireBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (13))));
  public static Block woodcutter = register("woodcutter", new WoodcutterBlock(WoodcutterBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(1.75f)));

  public static Block nether_fiery_glass_ore = register("nether_fiery_glass_ore", new OreXPBlock(OreBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f).harvestTool(ToolType.PICKAXE).harvestLevel(1), 2, 5));
  public static Block brightstone_ore = register("brightstone_ore", new OreXPBlock(OreBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f).setLightLevel((light) -> (8)).harvestTool(ToolType.PICKAXE).harvestLevel(3), 3, 8));
  public static Block hellkiln = register("hellkiln", new HellkilnBlock(HellkilnBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f).setLightLevel(getLightValueLit(15)).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
  public static Block hellkiln_igniter = register("hellkiln_igniter", new HellkilnIgniterBlock(HellkilnIgniterBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0f).setLightLevel(getLightValueLit(13)).harvestTool(ToolType.PICKAXE).harvestLevel(0)));
  public static Block infernal_flare = register("infernal_flare", new InfernalFlareBlock(InfernalFlareBlock.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f).doesNotBlockMovement().setLightLevel((light) -> (15))));
  public static Block overgrown_nylium = register("overgrown_nylium", new NyliumBlock(NyliumBlock.Properties.create(Material.ROCK).sound(SoundType.NYLIUM).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4f)));
  public static Block heartburn_fungus = register("heartburn_fungus", new FungusBlock(FungusBlock.Properties.create(Material.PLANTS).sound(SoundType.FUNGUS).hardnessAndResistance(0.0f).doesNotBlockMovement(), () -> ConfiguredFeatureRegistry.giant_heartburn_fungus, Block.makeCuboidShape(4.0F, 0.0F, 4.0F, 12.0F, 9.0F, 12.0F)));
  public static Block heartburn_stem = register("heartburn_fungus_stem", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.NETHER_WOOD).sound(SoundType.HYPHAE).hardnessAndResistance(2.0f)));
  public static Block heartburn_hyphae = register("heartburn_fungus_hyphae", new WoodBlock(() -> ItemRegistry.heartburn_hyphae, RotatedPillarBlock.Properties.create(Material.NETHER_WOOD).sound(SoundType.HYPHAE).hardnessAndResistance(2.0f)));
  public static Block stripped_heartburn_stem = register("stripped_heartburn_fungus_stem", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.NETHER_WOOD).sound(SoundType.HYPHAE).hardnessAndResistance(2.0f)));
  public static Block stripped_heartburn_hyphae = register("stripped_heartburn_fungus_hyphae", new RotatedPillarBlock(RotatedPillarBlock.Properties.create(Material.NETHER_WOOD).sound(SoundType.HYPHAE).hardnessAndResistance(2.0f)));
  public static Block heartburn_cap = register("heartburn_fungus_block", new HugeMushroomBlock(HugeMushroomBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.4f)));
  public static Block gaseous_growth = register("gaseous_growth", new GaseousGrowthBlock(GaseousGrowthBlock.Properties.create(Material.NETHER_PLANTS).sound(SoundType.NETHER_VINE_LOWER_PITCH).doesNotBlockMovement().hardnessAndResistance(0.5f).setLightLevel(GaseousGrowthBlock.getLightValue(11))));
  public static Block scorching_curtain = register("scorching_curtain", new ScorchingCurtainBlock(ScorchingCurtainBlock.Properties.create(Material.NETHER_PLANTS).sound(SoundType.NETHER_VINE).doesNotBlockMovement().hardnessAndResistance(0.3f).setLightLevel((light) -> (11))));
  public static Block fireblanket_moss = register("fireblanket_moss", new Block(Block.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f)));
  public static Block brambleroot = register("brambleroot", new BramblerootBlock(1.0F, BramblerootBlock.Properties.create(Material.NETHER_WOOD).sound(SoundType.WOOD).hardnessAndResistance(1.0f).tickRandomly()));
  public static Block overgrown_roots = register("overgrown_roots", new Block(Block.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f)));
  public static Block tall_overgrown_roots = register("tall_overgrown_roots", new Block(Block.Properties.create(Material.FIRE).sound(SoundType.SNOW).hardnessAndResistance(0.0f)));
  public static Block glow_air = register("glow_air", new AirBlock(Block.Properties.create(Material.AIR).doesNotBlockMovement().noDrops().setAir().setLightLevel((light) -> (12))));

  public static Block duragem_ore = register("duragem_ore", new OreXPBlock(OreBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(5.0f).harvestTool(ToolType.PICKAXE).harvestLevel(4), 2, 6));
  public static Block duragem_block = register("duragem_block", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(4).sound(SoundType.STONE)));

  public static Block snow_bricks = register("snow_bricks", new Block(Block.Properties.create(Material.SNOW).hardnessAndResistance(0.4f).sound(SoundType.SNOW)));
  public static Block snow_tiles = register("snow_tiles", new Block(Block.Properties.create(Material.SNOW).hardnessAndResistance(0.4f).sound(SoundType.SNOW)));
  public static Block ice_bricks = register("ice_bricks", new Block(Block.Properties.create(Material.ICE).hardnessAndResistance(0.6f).slipperiness(0.98F).notSolid().sound(SoundType.GLASS)));
  public static Block worked_ice = register("worked_ice", new Block(Block.Properties.create(Material.ICE).hardnessAndResistance(0.6f).slipperiness(0.98F).notSolid().sound(SoundType.GLASS)));
  public static Block packed_ice_bricks = register("packed_ice_bricks", new Block(Block.Properties.create(Material.ICE).hardnessAndResistance(0.6f).slipperiness(0.98F).sound(SoundType.GLASS)));
  public static Block worked_packed_ice = register("worked_packed_ice", new Block(Block.Properties.create(Material.ICE).hardnessAndResistance(0.6f).slipperiness(0.98F).sound(SoundType.GLASS)));

  public static Block flare_torch = register("flare_torch", new FlareTorchBlock(FlareTorchBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0f).setLightLevel((light) -> (10)).sound(SoundType.WOOD)));
  public static Block wall_flare_torch = register("wall_flare_torch", new WallFlareTorchBlock(WallFlareTorchBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0f).setLightLevel((light) -> (10)).sound(SoundType.WOOD).lootFrom(flare_torch)));
  public static Block liquid_rainbow = register("liquid_rainbow", new FlowingFluidBlock(() -> (FlowingFluid) FluidRegistry.liquid_rainbow, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
  public static Block cloud_block = register("cloud_block", new Block(Block.Properties.create(Material.WOOL).hardnessAndResistance(0.5f).sound(SoundType.SNOW)));
  public static Block thundercloud_block = register("thundercloud_block", new Block(Block.Properties.create(Material.WOOL).hardnessAndResistance(0.5f).sound(SoundType.SNOW)));
  public static Block crystalized_thundercloud_block = register("crystalized_thundercloud_block", new Block(Block.Properties.create(Material.WOOL).hardnessAndResistance(0.5f).sound(SoundType.SNOW)));
  public static Block sulfur_cloud_block = register("sulfur_cloud_block", new Block(Block.Properties.create(Material.WOOL).hardnessAndResistance(0.5f).sound(SoundType.SNOW)));
  public static Block skyberry_bush = register("skyberry_bush", new Block(Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).sound(SoundType.PLANT)));
  public static Block live_skyberry_bush = register("live_skyberry_bush", new Block(Block.Properties.create(Material.PLANTS).hardnessAndResistance(0.0f).sound(SoundType.PLANT)));

  public static Block register (String name, Block block) {
    block.setRegistryName(DruidcraftRegistry.location(name));
    BLOCKS.add(block);
    return block;
  }

  @SubscribeEvent
  public static void register (RegistryEvent.Register<Block> event) {
    event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
  }

  private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
    return (state) -> state.get(BlockStateProperties.LIT) ? lightValue : 0;
  }

}
