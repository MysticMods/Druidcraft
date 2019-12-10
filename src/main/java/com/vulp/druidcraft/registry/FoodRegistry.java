package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.items.DrinkableItem;
import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class FoodRegistry {

    public static final Food blueberries = (new Food.Builder()).hunger(2).saturation(0.1F).build();
    public static final Food elderberries = (new Food.Builder()).hunger(2).saturation(0.1F).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.5F).build();
    public static final Food apple_elderberry_crumble = (new Food.Builder()).hunger(8).saturation(0.9F).build();
    public static final Food blueberry_muffin = (new Food.Builder()).hunger(6).saturation(0.4F).build();
    public static final Food elderflower_cordial = (new Food.Builder()).hunger(3).saturation(0.6F).build();

}
