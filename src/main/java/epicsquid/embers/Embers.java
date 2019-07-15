package epicsquid.embers;

import epicsquid.embers.setup.ClientSetup;
import epicsquid.embers.setup.ModSetup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("embers")
public class Embers {
	public static final String MODID = "embers";

	public static ModSetup setup = new ModSetup();

	public Embers() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}

	private void setup(FMLCommonSetupEvent event) {
		setup.init(event);
	}
}
