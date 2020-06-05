package com.vulp.druidcraft.client.renders.layers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.Map;
import java.util.function.Consumer;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.items.TravelPackItem;
import com.vulp.druidcraft.registry.ItemRegistry;
import javafx.util.Builder;
import net.minecraft.block.Blocks;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

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
    private static final Map<DyeColor, ResourceLocation> TEX_MAP = (new ImmutableMap.Builder<DyeColor, ResourceLocation>()).put(DyeColor.WHITE, WHITE_PACK_TEX).put(DyeColor.ORANGE, ORANGE_PACK_TEX)
            .put(DyeColor.MAGENTA, MAGENTA_PACK_TEX).put(DyeColor.LIGHT_BLUE, LIGHT_BLUE_PACK_TEX).put(DyeColor.YELLOW, YELLOW_PACK_TEX).put(DyeColor.LIME, LIME_PACK_TEX).put(DyeColor.PINK, PINK_PACK_TEX)
            .put(DyeColor.GRAY, GRAY_PACK_TEX).put(DyeColor.LIGHT_GRAY, LIGHT_GRAY_PACK_TEX).put(DyeColor.CYAN, CYAN_PACK_TEX).put(DyeColor.PURPLE, PURPLE_PACK_TEX).put(DyeColor.BLUE, BLUE_PACK_TEX)
            .put(DyeColor.BROWN, BROWN_PACK_TEX).put(DyeColor.GREEN, GREEN_PACK_TEX).put(DyeColor.RED, RED_PACK_TEX).put(DyeColor.BLACK, BLACK_PACK_TEX).build();

    public TravelPackLayer(IEntityRenderer<T, M> entityRenderer, A normalModel, A bedrolledModel) {
        super(entityRenderer);
        this.normal_pack = normalModel;
        this.bedroll_pack = bedrolledModel;
    }

    public void render(T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        renderPack(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public void renderPack(T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entityLivingBaseIn.getEntity() instanceof PlayerEntity) {
            IItemHandler handler = entityLivingBaseIn.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).orElse(null);
            int slotNum = -1;
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack inSlot = handler.getStackInSlot(i);
                if (inSlot.getItem() == ItemRegistry.travel_pack && entityLivingBaseIn.inventory.currentItem != i) {
                    slotNum = i;
                    break;
                }
            }
            if (slotNum != -1) {
                ItemStack travelPackItem = entityLivingBaseIn.inventory.getStackInSlot(slotNum);
                CompoundNBT nbt = travelPackItem.getOrCreateTag();
                DyeColor bedrollColor = nbt.contains("Color") || nbt.getInt("Color") == -1 ? TravelPackItem.getColor(nbt) : null;
                A a = bedrollColor == null ? this.normal_pack : this.bedroll_pack;
                this.getEntityModel().func_217148_a(a);
                a.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
                if (bedrollColor == null) {
                    this.bindTexture(REGULAR_PACK_TEX);
                } else {
                    this.bindTexture(TEX_MAP.get(bedrollColor));
                }
                a.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }

}