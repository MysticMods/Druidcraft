package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.client.gui.screen.inventory.*;
import com.vulp.druidcraft.client.models.BedrollTravelPackModel;
import com.vulp.druidcraft.client.models.TravelPackModel;
import com.vulp.druidcraft.client.renders.BeetleEntityRender;
import com.vulp.druidcraft.client.renders.DreadfishEntityRender;
import com.vulp.druidcraft.client.renders.LunarMothEntityRender;
import com.vulp.druidcraft.client.renders.layers.TravelPackLayer;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.entities.DreadfishEntity;
import com.vulp.druidcraft.entities.LunarMothEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.inventory.StonecutterScreen;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry
{
    public static void registryRenders() {
        // ENTITIES
        RenderingRegistry.registerEntityRenderingHandler(DreadfishEntity.class, new DreadfishEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(BeetleEntity.class, new BeetleEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(LunarMothEntity.class, new LunarMothEntityRender.RenderFactory());


        // SCREENS
        ScreenManager.registerFactory(GUIRegistry.beetle_inv, BeetleInventoryScreen::new);
        ScreenManager.registerFactory(GUIRegistry.generic_9X12, QuadCrateScreen::new);
        ScreenManager.registerFactory(GUIRegistry.generic_9X24, OctoCrateScreen::new);
        ScreenManager.registerFactory(GUIRegistry.woodcutter, WoodcutterScreen::new);
        ScreenManager.registerFactory(GUIRegistry.travel_pack, TravelPackScreen::new);

        // PLAYER MODEL HOOK
        Map<String, PlayerRenderer> playerSkinMap = Minecraft.getInstance().getRenderManager().getSkinMap();
        // REMEMBER TO CHANGE THESE TO OBFUSCATED!
        List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> defaultSkinMap = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, playerSkinMap.get("default"), "layerRenderers");
        List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> slimSkinMap = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, playerSkinMap.get("slim"), "layerRenderers");

        defaultSkinMap.add(new TravelPackLayer<>(playerSkinMap.get("default"), new TravelPackModel<>(), new BedrollTravelPackModel<>()));
        slimSkinMap.add(new TravelPackLayer<>(playerSkinMap.get("default"), new TravelPackModel<>(), new BedrollTravelPackModel<>()));
    }
}
