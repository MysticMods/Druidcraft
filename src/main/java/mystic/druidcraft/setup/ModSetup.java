package mystic.druidcraft.setup;

import mystic.druidcraft.Druidcraft;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

	public ItemGroup tab = new ItemGroup(Druidcraft.MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.PUMPKIN);
		}
	};

	public void init(FMLCommonSetupEvent event) {

	}
}
