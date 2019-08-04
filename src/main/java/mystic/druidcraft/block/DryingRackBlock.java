package mystic.druidcraft.block;

import mystic.druidcraft.Druidcraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class DryingRackBlock extends Block {

    public DryingRackBlock() {
        super(Block.Properties.create(Material.WOOD));
        setRegistryName(new ResourceLocation(Druidcraft.MODID, "drying_rack"));
    }
}
