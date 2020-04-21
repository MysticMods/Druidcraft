package com.vulp.druidcraft.client.renders;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.models.BoneShieldModel;
import com.vulp.druidcraft.client.models.ChitinShieldModel;
import com.vulp.druidcraft.client.models.MoonstoneShieldModel;
import com.vulp.druidcraft.items.BasicShieldItem;
import com.vulp.druidcraft.registry.ItemRegistry;
import javafx.util.Pair;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class ItemTileEntityRenderer extends ItemStackTileEntityRenderer {

    private final BoneShieldModel bone_shield = new BoneShieldModel();
    private final ChitinShieldModel chitin_shield = new ChitinShieldModel();
    private final MoonstoneShieldModel moonstone_shield = new MoonstoneShieldModel();

    public void renderByItem(ItemStack itemStackIn) {
        Item item = itemStackIn.getItem();
        if (item instanceof BasicShieldItem) {
            if (item == ItemRegistry.bone_shield) {
                Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(Druidcraft.MODID, "textures/entity/shields/bone.png"));
                GlStateManager.pushMatrix();
                GlStateManager.scalef(1.0F, -1.0F, -1.0F);
                this.bone_shield.render();
                if (itemStackIn.hasEffect()) {
                    this.renderEffect(this.bone_shield::render);
                }
                GlStateManager.popMatrix();
            }
            if (item == ItemRegistry.chitin_shield) {
                Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(Druidcraft.MODID, "textures/entity/shields/chitin.png"));
                GlStateManager.pushMatrix();
                GlStateManager.scalef(1.0F, -1.0F, -1.0F);
                this.chitin_shield.render();
                if (itemStackIn.hasEffect()) {
                    this.renderEffect(this.chitin_shield::render);
                }
                GlStateManager.popMatrix();
            }
            if (item == ItemRegistry.moonstone_shield) {
                Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(Druidcraft.MODID, "textures/entity/shields/moonstone.png"));
                GlStateManager.pushMatrix();
                GlStateManager.scalef(1.0F, -1.0F, -1.0F);
                this.moonstone_shield.render();
                if (itemStackIn.hasEffect()) {
                    this.renderEffect(this.moonstone_shield::render);
                }
                GlStateManager.popMatrix();
            }
        }
    }

    private void renderEffect(Runnable renderModelFunction) {
        GlStateManager.color3f(0.5019608F, 0.2509804F, 0.8F);
        Minecraft.getInstance().getTextureManager().bindTexture(ItemRenderer.RES_ITEM_GLINT);
        ItemRenderer.renderEffect(Minecraft.getInstance().getTextureManager(), renderModelFunction, 1);
    }

}
