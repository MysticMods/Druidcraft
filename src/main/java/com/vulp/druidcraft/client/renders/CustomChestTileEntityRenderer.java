/*
package com.vulp.druidcraft.client.renders;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.api.ChestToString;
import com.vulp.druidcraft.blocks.CustomChestBlock;
import com.vulp.druidcraft.blocks.CustomTrappedChestBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;

import java.util.HashMap;
import java.util.Map;

public class CustomChestTileEntityRenderer<T extends TileEntity & IChestLid> extends ChestTileEntityRenderer<T> {

    private static final String path = "entity/chests/";
    private static Map<Block, ChestResources> RESOURCEMAP = new HashMap<>();
    protected boolean trapped = false;

    public CustomChestTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    public static void stitchChests(TextureStitchEvent.Pre event, Block chest) {
        ResourceLocation atlas = event.getMap().getTextureLocation();
        if (chest instanceof CustomChestBlock) {
            if (chest instanceof CustomTrappedChestBlock) {
                resourcePacker(event, atlas, chest, "trap", "trap_left", "trap_right");
            } else {
                resourcePacker(event, atlas, chest, "normal", "left", "right");
            }
        }
    }

    private static void resourcePacker(TextureStitchEvent.Pre event, ResourceLocation atlas, Block chest, String middleLocation, String leftLocation, String rightLocation) {
        String deepPath;
        try {
            deepPath = path + ChestToString.getEnumFromChest(chest).getString();
        } catch (Exception e) {
            Druidcraft.LOGGER.debug("Chest without EnumValue: " + chest);
            return;
        }
        ResourceLocation normal = new ResourceLocation(Druidcraft.MODID, deepPath + "/" + middleLocation);
        ResourceLocation left = new ResourceLocation(Druidcraft.MODID, deepPath + "/" + leftLocation);
        ResourceLocation right = new ResourceLocation(Druidcraft.MODID, deepPath + "/" + rightLocation);
        RESOURCEMAP.put(chest, new ChestResources(new RenderMaterial(atlas, normal), new RenderMaterial(atlas, left), new RenderMaterial(atlas, right)));
        addSprites(event, normal, left, right);
    }

    private static void addSprites(TextureStitchEvent.Pre event, ResourceLocation normal, ResourceLocation left, ResourceLocation right) {
        event.addSprite(normal);
        event.addSprite(left);
        event.addSprite(right);
    }

    @Override
    protected RenderMaterial getMaterial(T tileEntity, ChestType chestType) {
        ChestResources resources = RESOURCEMAP.get(trapped ? BlockRegistry.trapped_darkwood_chest : BlockRegistry.darkwood_chest);
        if (resources == null) {
            return null;
        }
        switch (chestType) {
            case SINGLE:
            default:
                return resources.getMain();
            case LEFT:
                return resources.getLeft();
            case RIGHT:
                return resources.getRight();
        }
    }

    private static class ChestResources {
        private final RenderMaterial main;
        private final RenderMaterial left;
        private final RenderMaterial right;

        public ChestResources(RenderMaterial main, RenderMaterial left, RenderMaterial right) {
            this.main = main;
            this.left = left;
            this.right = right;
        }

        public RenderMaterial getLeft() {
            return left;
        }

        public RenderMaterial getMain() {
            return main;
        }

        public RenderMaterial getRight() {
            return right;
        }
    }

}
*/
