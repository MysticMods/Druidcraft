package mystic.druidcraft.setup;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.material.IMaterial;
import mystic.druidcraft.Druidcraft;
import mystic.druidcraft.entity.DreadFishEntity;
import mystic.druidcraft.entity.render.DreadFishRenderer;
import mystic.druidcraft.items.BoneMaterial;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Druidcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryManager {

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("amber"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("ash"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("druid_book"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("fiery_glass"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("frosted_heart"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("ghostly_halo"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("ghostly_halo_empowered"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("growth_lantern_idle"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("growth_lantern_active"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("hemp"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("hemp_seeds"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("mistletoe"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("moonstone"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("radiant_javelin_idle"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("radiant_javelin_active"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("rockroot"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("rope"));
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("soulfire"));
		LibRegistry.registerMetalSetItems(new BoneMaterial(), event.getRegistry(), Druidcraft.MODID);
	}

	@SubscribeEvent
	public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event)
	{
		event.getRegistry().register(EntityType.Builder.create(DreadFishEntity::new, EntityClassification.WATER_CREATURE).size(0.5F, 0.5F).build("dreadfish")
				.setRegistryName(new ResourceLocation(Druidcraft.MODID, "dreadfish")));

		RenderingRegistry.registerEntityRenderingHandler(DreadFishEntity.class, DreadFishRenderer::new);
	}
}
