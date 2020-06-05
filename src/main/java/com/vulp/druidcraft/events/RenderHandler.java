package com.vulp.druidcraft.events;

import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderHandler {

    private static final BlockColors blockColors = Minecraft.getInstance().getBlockColors();
    private static final ItemColors itemColors = Minecraft.getInstance().getItemColors();

    public static void registerColors() {

        registerBlockColor(10276442, BlockRegistry.fruit_leaves);

        registerItemColor(10276442, ItemRegistry.fruit_leaves);


    }

    public static void registerBlockColor(int tint, Block... block) {
        blockColors.register((state, world, pos, tintIndex) ->
                tint, block);
    }

    public static void registerItemColor(int tint, IItemProvider... item) {
        itemColors.register((itemStack, i) ->
                tint, item);
    }

}
