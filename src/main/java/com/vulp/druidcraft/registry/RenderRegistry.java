package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.blocks.tileentities.SmallBeamTileEntity;
import com.vulp.druidcraft.client.gui.screen.inventory.*;
import com.vulp.druidcraft.client.models.BedrollTravelPackModel;
import com.vulp.druidcraft.client.models.TravelPackModel;
import com.vulp.druidcraft.client.renders.BeetleEntityRender;
import com.vulp.druidcraft.client.renders.DreadfishEntityRender;
import com.vulp.druidcraft.client.renders.LunarMothEntityRender;
import com.vulp.druidcraft.client.renders.SmallBeamTileEntityRenderer;
import com.vulp.druidcraft.client.renders.layers.TravelPackLayer;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.entities.DreadfishEntity;
import com.vulp.druidcraft.entities.LunarMothEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.inventory.StonecutterScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry
{
    public static void registryRenders(FMLClientSetupEvent event) {
        // ENTITIES
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.dreadfish_entity, new DreadfishEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.beetle_entity, new BeetleEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.lunar_moth_entity, new LunarMothEntityRender.RenderFactory());

        // BLOCK TILE ENTITIES
        ClientRegistry.bindTileEntityRenderer(TileEntityRegistry.small_beam, SmallBeamTileEntityRenderer::new);

        event.enqueueWork(() -> {
            // ITEM TILE ENTITIES
            ItemModelsProperties.registerProperty(ItemRegistry.chitin_shield, new ResourceLocation("blocking"), (stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);
            ItemModelsProperties.registerProperty(ItemRegistry.bone_shield, new ResourceLocation("blocking"), (stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);
            ItemModelsProperties.registerProperty(ItemRegistry.moonstone_shield, new ResourceLocation("blocking"), (stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);

            ItemModelsProperties.registerProperty(ItemRegistry.travel_pack, new ResourceLocation(Druidcraft.MODID, "color"), (itemStack, world, livingEntity) -> {
                CompoundNBT nbt = itemStack.getOrCreateTag();
                if (nbt.contains("Color")) {
                    return nbt.getInt("Color");
                } else return -1;
            });

            // BLOCKS
            //    RenderTypeLookup.setRenderLayer(BlockRegistry.aloe_vera_crop, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.amber_block, RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(BlockRegistry.blueberry_bush, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.ceramic_lantern, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.darkwood_door, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.darkwood_leaves, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.darkwood_sapling, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.darkwood_trapdoor, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.elder_door, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.elder_leaves, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.elder_sapling, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.elder_fruit, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.elder_leaf_layer, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.elder_trapdoor, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.fiery_torch, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.wall_fiery_torch, RenderType.getCutout());
            //    RenderTypeLookup.setRenderLayer(BlockRegistry.fruit_door, RenderType.getCutout());
            //    RenderTypeLookup.setRenderLayer(BlockRegistry.fruit_leaves, RenderType.getCutout());
            //    RenderTypeLookup.setRenderLayer(BlockRegistry.fruit_sapling, RenderType.getCutout());
            //    RenderTypeLookup.setRenderLayer(BlockRegistry.fruit_trapdoor, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.growth_lamp, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.hemp_crop, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.lime_lunar_moth_jar, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.orange_lunar_moth_jar, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.pink_lunar_moth_jar, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.turquoise_lunar_moth_jar, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.white_lunar_moth_jar, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.yellow_lunar_moth_jar, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.potted_darkwood_sapling, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.potted_elder_sapling, RenderType.getCutout());
            //    RenderTypeLookup.setRenderLayer(BlockRegistry.potted_fruit_sapling, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.potted_lavender, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.rope_lantern, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.rope, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.lavender, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.woodcutter, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.acacia_small_beam, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.birch_small_beam, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.dark_oak_small_beam, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.darkwood_small_beam, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.elder_small_beam, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.jungle_small_beam, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.oak_small_beam, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistry.spruce_small_beam, RenderType.getCutout());
            //    RenderTypeLookup.setRenderLayer(BlockRegistry.fruit_small_beam, RenderType.getCutout());

            // SCREENS
            ScreenManager.registerFactory(GUIRegistry.beetle_inv, BeetleInventoryScreen::new);
            ScreenManager.registerFactory(GUIRegistry.generic_9X3, SingleCrateScreen::new);
            ScreenManager.registerFactory(GUIRegistry.generic_9X6, DoubleCrateScreen::new);
            ScreenManager.registerFactory(GUIRegistry.generic_9X12, QuadCrateScreen::new);
            ScreenManager.registerFactory(GUIRegistry.generic_9X24, OctoCrateScreen::new);
            ScreenManager.registerFactory(GUIRegistry.woodcutter, WoodcutterScreen::new);
            ScreenManager.registerFactory(GUIRegistry.travel_pack, TravelPackScreen::new);
            ScreenManager.registerFactory(GUIRegistry.hellkiln_igniter, HellKilnIgniterScreen::new);

            // PLAYER MODEL HOOK
            Map<String, PlayerRenderer> playerSkinMap = Minecraft.getInstance().getRenderManager().getSkinMap();

            List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> defaultSkinMap = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, playerSkinMap.get("default"), "field_177097_h");
            List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> slimSkinMap = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, playerSkinMap.get("slim"), "field_177097_h");

            defaultSkinMap.add(new TravelPackLayer<>(playerSkinMap.get("default"), new TravelPackModel<>(), new BedrollTravelPackModel<>()));
            slimSkinMap.add(new TravelPackLayer<>(playerSkinMap.get("default"), new TravelPackModel<>(), new BedrollTravelPackModel<>()));
        });
    }
}
