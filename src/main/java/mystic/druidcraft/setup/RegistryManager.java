package mystic.druidcraft.setup;

import mystic.druidcraft.Druidcraft;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Druidcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryManager {

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new Item(new Item.Properties().group(Druidcraft.setup.tab)).setRegistryName("amber"));

	}
}
