package com.vulp.druidcraft;

import com.vulp.druidcraft.config.Configuration;
import com.vulp.druidcraft.registry.ArmorMaterialRegistry;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import com.vulp.druidcraft.registry.ToolMaterialRegistry;
import com.vulp.druidcraft.world.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("druidcraft")
public class Druidcraft
{
    public static Druidcraft INSTANCE;
    public static final String MODID = "druidcraft";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final ItemGroup DRUIDCRAFT = new DruidcraftItemGroup();

    public Druidcraft()
    {
        INSTANCE = this;

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Configuration.server_config);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Configuration.client_config);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        Configuration.loadConfig(Configuration.server_config, FMLPaths.CONFIGDIR.get().resolve("druidcraft-server.toml").toString());
        Configuration.loadConfig(Configuration.client_config, FMLPaths.CONFIGDIR.get().resolve("druidcraft-client.toml").toString());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        OreGeneration.setupOreGeneration();
        LOGGER.info("Setup method registered.");
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        LOGGER.info("Client registry method registered.", event.getMinecraftSupplier().get().gameSettings);
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {

        // ITEM REGISTRATION
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
        {
            itemRegistryEvent.getRegistry().registerAll
                    (
                            // True items:
                            ItemRegistry.hemp = new Item(new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("hemp")),
                            ItemRegistry.amber = new Item(new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("amber")),

                            //Tools & Armour:
                            ItemRegistry.bone_sword = new SwordItem(ToolMaterialRegistry.bone, 3, -2.4f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_sword")),
                            ItemRegistry.bone_shovel = new ShovelItem(ToolMaterialRegistry.bone, 1.5f, -3.0f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_shovel")),
                            ItemRegistry.bone_pickaxe = new PickaxeItem(ToolMaterialRegistry.bone, 1, -2.8f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_pickaxe")),
                            ItemRegistry.bone_axe = new AxeItem(ToolMaterialRegistry.bone, 7.0f, -3.2f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_axe")),
                            ItemRegistry.bone_hoe = new HoeItem(ToolMaterialRegistry.bone, -2.0f, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_hoe")),

                            ItemRegistry.bone_helmet = new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlotType.HEAD, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_helmet")),
                            ItemRegistry.bone_chestplate = new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlotType.CHEST, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_chestplate")),
                            ItemRegistry.bone_leggings = new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlotType.LEGS, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_leggings")),
                            ItemRegistry.bone_boots = new ArmorItem(ArmorMaterialRegistry.bone, EquipmentSlotType.FEET, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(location("bone_boots")),

                            // Item-blocks:
                            ItemRegistry.amber_ore = new BlockItem(BlockRegistry.amber_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.amber_ore.getRegistryName()),
                            ItemRegistry.moonstone_ore = new BlockItem(BlockRegistry.moonstone_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.moonstone_ore.getRegistryName()),
                            ItemRegistry.fiery_glass_ore = new BlockItem(BlockRegistry.fiery_glass_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.fiery_glass_ore.getRegistryName()),
                            ItemRegistry.rockroot_ore = new BlockItem(BlockRegistry.rockroot_ore, new Item.Properties().group(DRUIDCRAFT)).setRegistryName(BlockRegistry.rockroot_ore.getRegistryName())
                    );
            LOGGER.info("Items registered.");
        }

        // BLOCK REGISTRATION
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> BlockRegistryEvent)
            {
                BlockRegistryEvent.getRegistry().registerAll
                        (
                                BlockRegistry.amber_ore = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f).harvestTool(ToolType.PICKAXE).harvestLevel(1)).setRegistryName(location("amber_ore")),
                                BlockRegistry.moonstone_ore = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2)).setRegistryName(location("moonstone_ore")),
                                BlockRegistry.fiery_glass_ore = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2)).setRegistryName(location("fiery_glass_ore")),
                                BlockRegistry.rockroot_ore = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f).harvestTool(ToolType.PICKAXE).harvestLevel(0)).setRegistryName(location("rockroot_ore"))

                        );
                {

                }
                LOGGER.info("Blocks registered.");
            }

        private static ResourceLocation location(String name)
        {
            return new ResourceLocation(MODID, name);
        }
    }
}