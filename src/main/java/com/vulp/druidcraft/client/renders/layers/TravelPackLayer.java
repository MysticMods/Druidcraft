package com.vulp.druidcraft.client.renders.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.Map;
import java.util.function.Consumer;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.items.TravelPackItem;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TravelPackLayer<T extends PlayerEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends LayerRenderer<T, M> {
    protected final A normal_pack;
    protected final A bedroll_pack;
    private static final ResourceLocation REGULAR_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/regular.png");
    private static final ResourceLocation WHITE_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_white.png");
    private static final ResourceLocation ORANGE_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_orange.png");
    private static final ResourceLocation MAGENTA_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_magenta.png");
    private static final ResourceLocation LIGHT_BLUE_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_light_blue.png");
    private static final ResourceLocation YELLOW_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_yellow.png");
    private static final ResourceLocation LIME_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_lime.png");
    private static final ResourceLocation PINK_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_pink.png");
    private static final ResourceLocation GRAY_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_gray.png");
    private static final ResourceLocation LIGHT_GRAY_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_light_gray.png");
    private static final ResourceLocation CYAN_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_cyan.png");
    private static final ResourceLocation PURPLE_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_purple.png");
    private static final ResourceLocation BLUE_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_blue.png");
    private static final ResourceLocation BROWN_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_brown.png");
    private static final ResourceLocation GREEN_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_green.png");
    private static final ResourceLocation RED_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_red.png");
    private static final ResourceLocation BLACK_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/bedrolled_black.png");

    public TravelPackLayer(IEntityRenderer<T, M> entityRenderer, A normalModel, A bedrolledModel) {
        super(entityRenderer);
        this.normal_pack = normalModel;
        this.bedroll_pack = bedrolledModel;
    }

    public void render(T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        renderPack(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
    }

    // For some reason the pack refuses to render unless empty. I believe it has something to do with TravelPackContainer&detectAndSendChanges.
    // Also, oddly the textures are refusing to apply to the bedrolled pack. Might have something to do with the switch?
    public void renderPack(T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entityLivingBaseIn.getEntity() instanceof PlayerEntity) {
            int slotNum = entityLivingBaseIn.inventory.getSlotFor(new ItemStack(ItemRegistry.travel_pack));
            if (slotNum != -1) {
                ItemStack travelPackItem = entityLivingBaseIn.inventory.getStackInSlot(slotNum);
                DyeColor bedrollColor = travelPackItem.getTag() != null ? TravelPackItem.getColor(travelPackItem.getTag()) : null;
                A a = bedrollColor == null ? this.normal_pack : this.bedroll_pack;
                this.getEntityModel().func_217148_a(a);
                a.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
                Druidcraft.LOGGER.debug(bedrollColor);
                if (bedrollColor == null) {
                    this.bindTexture(REGULAR_PACK_TEX);
                } else {
                    switch (bedrollColor) {
                        case WHITE:
                            this.bindTexture(WHITE_PACK_TEX);
                        case ORANGE:
                            this.bindTexture(ORANGE_PACK_TEX);
                        case MAGENTA:
                            this.bindTexture(MAGENTA_PACK_TEX);
                        case LIGHT_BLUE:
                            this.bindTexture(LIGHT_BLUE_PACK_TEX);
                        case YELLOW:
                            this.bindTexture(YELLOW_PACK_TEX);
                        case LIME:
                            this.bindTexture(LIME_PACK_TEX);
                        case PINK:
                            this.bindTexture(PINK_PACK_TEX);
                        case GRAY:
                            this.bindTexture(GRAY_PACK_TEX);
                        case LIGHT_GRAY:
                            this.bindTexture(LIGHT_GRAY_PACK_TEX);
                        case CYAN:
                            this.bindTexture(CYAN_PACK_TEX);
                        case PURPLE:
                            this.bindTexture(PURPLE_PACK_TEX);
                        case BLUE:
                            this.bindTexture(BLUE_PACK_TEX);
                        case BROWN:
                            this.bindTexture(BROWN_PACK_TEX);
                        case GREEN:
                            this.bindTexture(GREEN_PACK_TEX);
                        case RED:
                            this.bindTexture(RED_PACK_TEX);
                        case BLACK:
                            this.bindTexture(BLACK_PACK_TEX);
                    }
                }
                a.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    public static <T extends Entity> void func_215338_a(Consumer<ResourceLocation> p_215338_0_, T p_215338_1_, EntityModel<T> p_215338_2_, float p_215338_3_, float p_215338_4_, float p_215338_5_, float p_215338_6_, float p_215338_7_, float p_215338_8_, float p_215338_9_) {
        float f = (float) p_215338_1_.ticksExisted + p_215338_5_;
        GameRenderer gamerenderer = Minecraft.getInstance().gameRenderer;
        gamerenderer.setupFogColor(true);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);
        float f1 = 0.5F;
        GlStateManager.color4f(0.5F, 0.5F, 0.5F, 1.0F);

        for (int i = 0; i < 2; ++i) {
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
            float f2 = 0.76F;
            GlStateManager.color4f(0.38F, 0.19F, 0.608F, 1.0F);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f3 = 0.33333334F;
            GlStateManager.scalef(0.33333334F, 0.33333334F, 0.33333334F);
            GlStateManager.rotatef(30.0F - (float) i * 60.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translatef(0.0F, f * (0.001F + (float) i * 0.003F) * 20.0F, 0.0F);
            GlStateManager.matrixMode(5888);
            p_215338_2_.render(p_215338_1_, p_215338_3_, p_215338_4_, p_215338_6_, p_215338_7_, p_215338_8_, p_215338_9_);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }

        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GlStateManager.disableBlend();
        gamerenderer.setupFogColor(false);
    }
}