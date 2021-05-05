package com.vulp.druidcraft.mixin;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingRenderer.class)
public abstract class LivingRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Shadow @Final protected List<LayerRenderer<T, M>> layerRenderers;

    @Inject(at = @At("RETURN"), method = "<init>")
    public void LivingRenderer(EntityRendererManager rendererManager, M entityModelIn, float shadowSizeIn, CallbackInfo ci) {
        this.layerRenderers.add(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
    }


}
