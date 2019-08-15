package mystic.druidcraft.setup;

import mystic.druidcraft.Druidcraft;
import mystic.druidcraft.entity.DreadFishEntity;
import mystic.druidcraft.entity.render.DreadFishRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Druidcraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

	@SubscribeEvent
	public static void init(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(DreadFishEntity.class, DreadFishRenderer::new);
	}
}
