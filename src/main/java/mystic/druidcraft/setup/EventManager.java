package mystic.druidcraft.setup;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.event.RegisterItemFactoriesEvent;
import mystic.druidcraft.Druidcraft;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Druidcraft.MODID)
public class EventManager {

    @SubscribeEvent
    public static void onFactoryRegister(RegisterItemFactoriesEvent event){
        int i = 0;
    }

}
