package mystic.druidcraft;

import mystic.druidcraft.setup.ModSetup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("druidcraft")
public class Druidcraft {
	public static final String MODID = "druidcraft";

	public static ModSetup setup = new ModSetup();

	public Druidcraft() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}

	private void setup(FMLCommonSetupEvent event) {
		setup.init(event);
	}
}
