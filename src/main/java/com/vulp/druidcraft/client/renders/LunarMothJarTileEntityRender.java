package com.vulp.druidcraft.client.renders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.tileentities.LunarMothJarTileEntity;
import com.vulp.druidcraft.client.models.LunarMothJarTileEntityModel;
import com.vulp.druidcraft.entities.LunarMothEntity;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.EnchantingTableTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LunarMothJarTileEntityRender extends TileEntityRenderer<LunarMothJarTileEntity> {
    private static final ResourceLocation MOTH_TURQUOISE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_turquoise.png");
    private static final ResourceLocation MOTH_WHITE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_white.png");
    private static final ResourceLocation MOTH_LIME = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_lime.png");
    private static final ResourceLocation MOTH_ORANGE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_orange.png");
    private static final ResourceLocation MOTH_PINK = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_pink.png");
    private static final ResourceLocation MOTH_YELLOW = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_mini_yellow.png");
    private final LunarMothJarTileEntityModel modelMoth = new LunarMothJarTileEntityModel();
    private static boolean rotationDir = true;

    public LunarMothJarTileEntityRender() {
    }

    public void render(LunarMothJarTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)x + tileEntityIn.positionX, (float)y + tileEntityIn.positionY, (float)z + tileEntityIn.positionZ);
        if (getWorld().rand.nextInt(30) == 0) {
            rotationDir = !rotationDir;
        }
        float angleModifier = rotationDir ? 1.0f : -1.0f;
        GlStateManager.rotatef(angleModifier, 0.0F, 0.0F, 1.0F);

        if (tileEntityIn.color == 1) {
            this.bindTexture(MOTH_TURQUOISE);
        }
        else if (tileEntityIn.color == 3) {
            this.bindTexture(MOTH_LIME);
        }
        else if (tileEntityIn.color == 4) {
            this.bindTexture(MOTH_YELLOW);
        }
        else if (tileEntityIn.color == 5) {
            this.bindTexture(MOTH_ORANGE);
        }
        else if (tileEntityIn.color == 6) {
            this.bindTexture(MOTH_PINK);
        }
        else {
            this.bindTexture(MOTH_WHITE);
        }

        GlStateManager.enableCull();
        this.modelMoth.render(tileEntityIn, 1.0f);
        GlStateManager.popMatrix();
    }
}