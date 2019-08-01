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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoneMaterial implements IMaterial {

    private static final String SWORD = "SWORD";
    private static final String KNIFE = "KNIFE";
    private static final String PICKAXE = "PICKAXE";
    private static final String AXE = "AXE";
    private static final String SHOVEL = "SHOVEL";
    private static final String HOE = "HOE";
    private static final String SPEAR = "SPEAR";

    private int enchantability = 14;

    private Map<String, Float> damage = new HashMap<>();
    private Map<String, Float> speed = new HashMap<>();

    public BoneMaterial() {
        damage.put(SWORD, 3.0f);
        damage.put(SHOVEL, 1.5f);
        damage.put(PICKAXE, 1.0f);
        damage.put(AXE, 5.0f);
        damage.put(KNIFE, 2.5f);

        speed.put(SWORD, -2.4f);
        speed.put(SHOVEL, -3.0f);
        speed.put(PICKAXE, -2.8f);
        speed.put(AXE, -3.1f);
        speed.put(KNIFE, -1.0f);
        speed.put(HOE, 3.0f);
    }

    @Override
    public IItemTier getTier() {
        return new BaseItemTier(170, 6.0F, 2.0F, 2, enchantability, () -> Ingredient.fromItems(Items.BONE));
    }

    @Override
    public IArmorMaterial getArmor() {
        return new BaseArmorMaterial(Druidcraft.MODID + ":" + getName(), 15, new int[]{2, 5, 6, 2}, enchantability, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F, () -> Ingredient.fromItems(Items.BONE));
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
        return speed.getOrDefault(name, 1.0f);
    }

    @Override
    public float getAttackDamage(String name) {
        return damage.getOrDefault(name, 1.0f);
    }

    @Override
    public String getName() {
        return "bone";
    }

    @Override
    public List<String> getWhitelist() {
        return Arrays.asList("ingot", "nugget", "ore", "block", "dust", "");
    }

    @Override
    public boolean isBlacklist() {
        return true;
    }

    @Override
    public int getDurability() {
        return 0;
    }
}
