package mystic.druidcraft;

import mystic.druidcraft.registry.ModItems;
import mystic.druidcraft.setup.ModSetup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("druidcraft")
public class Druidcraft {
	public static final String MODID = "druidcraft";

	public static final ItemGroup GROUP = new ItemGroup(Druidcraft.MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.APPLE);
		}
	};

	public static ModSetup setup = new ModSetup();

	public Druidcraft() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}

	private void setup(FMLCommonSetupEvent event) {
		setup.init(event);
	}
}
