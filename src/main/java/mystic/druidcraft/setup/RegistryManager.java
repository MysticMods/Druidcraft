package mystic.druidcraft.setup;

import java.util.ArrayList;
import java.util.List;

import epicsquid.mysticallib.material.MaterialGenerator;
import mystic.druidcraft.Druidcraft;
import mystic.druidcraft.block.DryingRackBlock;
import mystic.druidcraft.entity.DreadFishEntity;
import mystic.druidcraft.entity.render.DreadFishRenderer;
import mystic.druidcraft.items.BoneMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Druidcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryManager {

	private static List<Block> blocks = new ArrayList<>();

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		blocks.add(new DryingRackBlock());

		generateWoodVariants("acacia");
		generateWoodVariants("birch");
		generateWoodVariants("dark_oak");
		generateWoodVariants("jungle");
		generateWoodVariants("oak");
		generateWoodVariants("spruce");

		for (Block b : blocks) {
			event.getRegistry().register(b);
		}
	}

	private static void generateWoodVariants(String name) {
		blocks.add(new LogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F)).setRegistryName(Druidcraft.MODID, name + "_log_beams"));
		blocks.add(new Block(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(Druidcraft.MODID, name + "_ornate_planks"));
		blocks.add(new Block(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F, 3.0F)).setRegistryName(Druidcraft.MODID, name + "_panels"));
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("amber"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("ash"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("druid_book"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("fiery_glass"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("frosted_heart"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("ghostly_halo"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("ghostly_halo_empowered"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("growth_lantern_idle"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("growth_lantern_active"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("mistletoe"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("moonstone"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("radiant_javelin_idle"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("radiant_javelin_active"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("rockroot"));
		//		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName("soulfire"));

		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName(Druidcraft.MODID, "hemp"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName(Druidcraft.MODID, "hemp_seeds"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.GROUP)).setRegistryName(Druidcraft.MODID, "rope"));

		MaterialGenerator.getInstance().generateItems(new BoneMaterial(), event.getRegistry(), Druidcraft.MODID);

		blocks.forEach(block -> event.getRegistry().register(new BlockItem(block, new Item.Properties().group(Druidcraft.GROUP)).setRegistryName(block.getRegistryName())));

	}

	@SubscribeEvent
	public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(EntityType.Builder.create(DreadFishEntity::new, EntityClassification.WATER_CREATURE).size(0.5F, 0.5F).build("dreadfish").setRegistryName(new ResourceLocation(Druidcraft.MODID, "dreadfish")));
	}
}
