package epicsquid.embers.setup;

import epicsquid.embers.Embers;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

	public ItemGroup tab = new ItemGroup(Embers.MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.PUMPKIN);
		}
	};

	public void init(FMLCommonSetupEvent event) {

	}
}
