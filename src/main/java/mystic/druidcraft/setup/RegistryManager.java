package mystic.druidcraft.setup;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.material.IMaterial;
import mystic.druidcraft.Druidcraft;
import mystic.druidcraft.items.BoneMaterial;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
		LibRegistry.registerMetalSetItems(new BoneMaterial(), event.getRegistry(), Druidcraft.MODID);
	}
}
