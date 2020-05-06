package com.vulp.druidcraft.client.renders.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.TravelPackInventory;
import com.vulp.druidcraft.items.TravelPackItem;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class TravelPackLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends LayerRenderer<T, M> {
    protected final A normal_pack;
    protected final A bedroll_pack;
    private static final ResourceLocation TRAVEL_PACK_TEX = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/pack.png");
    private DyeColor bedroll;
    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();

    protected TravelPackLayer(IEntityRenderer<T, M> p_i50951_1_, A p_i50951_2_, A p_i50951_3_) {
        super(p_i50951_1_);
        this.normal_pack = p_i50951_2_;
        this.bedroll_pack = p_i50951_3_;
    }

    public void render(T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        renderPack(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public void renderPack(T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entityLivingBaseIn.getEntity() instanceof PlayerEntity) {
            PlayerEntity entity = (PlayerEntity) entityLivingBaseIn;
            int slotNum = entity.inventory.getSlotFor(new ItemStack(ItemRegistry.travel_pack));
            if (slotNum != -1) {
                TravelPackItem item = (TravelPackItem) entity.inventory.getStackInSlot(slotNum).getItem();
                A a = TravelPackItem.getInventory(item).bedrollColor() != null ? this.bedroll_pack : this.normal_pack;
                ((BipedModel) this.getEntityModel()).func_217148_a(a);
                a.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
                this.bindTexture(TRAVEL_PACK_TEX);
                a.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    public void renderBedroll(T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entityLivingBaseIn.getEntity() instanceof PlayerEntity) {
            PlayerEntity entity = (PlayerEntity) entityLivingBaseIn;
            int slotNum = entity.inventory.getSlotFor(new ItemStack(ItemRegistry.travel_pack));
            if (slotNum != -1) {
                TravelPackItem item = (TravelPackItem) entity.inventory.getStackInSlot(slotNum).getItem();
                A a = TravelPackItem.getInventory(item).bedrollColor() != null ? this.bedroll_pack : this.normal_pack;
                ((BipedModel) this.getEntityModel()).func_217148_a(a);
                a.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
                this.bindTexture(TRAVEL_PACK_TEX);
                a.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    private boolean isLegSlot(EquipmentSlotType slotIn) {
        return slotIn == EquipmentSlotType.LEGS;
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

    public ResourceLocation getBedrollResource(ItemStack stack) {
        TravelPackItem item = (TravelPackItem) stack.getItem();
        TravelPackInventory inventory = TravelPackItem.getInventory(item);
        DyeColor color = inventory.bedrollColor();
        if (color != null) {
            String dyeColor = color.getTranslationKey();
            ResourceLocation resourcelocation = new ResourceLocation(Druidcraft.MODID, "textures/entity/travel_pack/" + dyeColor + "_bedroll");
            return resourcelocation;
        }
        return null;
    }

}