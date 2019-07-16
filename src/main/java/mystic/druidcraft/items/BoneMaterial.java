package mystic.druidcraft.items;

import epicsquid.mysticallib.block.OreBlockProperties;
import epicsquid.mysticallib.material.BaseArmorMaterial;
import epicsquid.mysticallib.material.BaseItemTier;
import epicsquid.mysticallib.material.IMaterial;
import mystic.druidcraft.Druidcraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvents;

public class BoneMaterial implements IMaterial {

    private static final String SWORD = "SWORD";
    private static final String KNIFE = "KNIFE";
    private static final String PICKAXE = "PICKAXE";
    private static final String AXE = "AXE";
    private static final String SHOVEL = "SHOVEL";
    private static final String HOE = "HOE";
    private static final String SPEAR = "SPEAR";

    @Override
    public IItemTier getTier() {
        return new BaseItemTier(170, 6.0F, 2.0F, 2, 14, () -> Ingredient.fromItems(Items.BONE));
    }

    @Override
    public IArmorMaterial getArmor() {
        return new BaseArmorMaterial("druidcraft:bone", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, () -> Ingredient.fromItems(Items.BONE));
    }

    @Override
    public Item.Properties getItemProps() {
        return new Item.Properties().group(Druidcraft.setup.tab);
    }

    @Override
    public Block.Properties getBlockProps() {
        return Block.Properties.create(Material.IRON);
    }

    @Override
    public OreBlockProperties getBlockOreProps() {
        return null;
    }

    @Override
    public float getAttackSpeed(String name) {
        switch(name){
            case SWORD:
                return -2.4F;
            case KNIFE:
                return -1.0F;
            case PICKAXE:
                return -2.8F;
            case AXE:
                return -3.1F;
            case SHOVEL:
                return -3.0F;
            case HOE:
                return -1.0F;
            case SPEAR:
                return -1.5F;
        }
        return 0;
    }

    @Override
    public float getAttackDamage(String name) {
        return ItemTier.IRON.getAttackDamage();
    }

    @Override
    public String getName() {
        return "bone";
    }

    @Override
    public boolean isMetal() {
        return false;
    }
}
