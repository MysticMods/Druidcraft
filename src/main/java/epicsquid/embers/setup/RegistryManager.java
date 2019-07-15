package epicsquid.embers.setup;

import epicsquid.embers.Embers;
import epicsquid.mysticallib.factories.ItemGenerator;
import epicsquid.mysticallib.factories.ToolFactories;
import epicsquid.mysticallib.material.BaseItemTier;
import epicsquid.mysticallib.material.MaterialProperties;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Embers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryManager {

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ItemGenerator generator = new ItemGenerator();
		generator.addAllFactories(ToolFactories.getFactories());
		generator.execute(new MaterialProperties()
										.setItemProps(() -> new Item.Properties().maxDamage(256).maxStackSize(1).group(Embers.setup.tab))
										.setName("dawnstone")
										.setDamage("SWORD", 7)
										.setAttackSpeed("SWORD", 1.6f)
										.setTier(() -> new BaseItemTier(256, 5.0f, 4.0f, 4, 4,
														() -> Ingredient.fromItems(Items.IRON_INGOT)))
						, event);
	}
}
