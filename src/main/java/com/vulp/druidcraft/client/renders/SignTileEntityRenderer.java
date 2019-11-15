package com.vulp.druidcraft.client.renders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.client.renderer.tileentity.model.SignModel;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.ResourceLocation;

public class SignTileEntityRenderer extends net.minecraft.client.renderer.tileentity.SignTileEntityRenderer {

    private static final ResourceLocation DARKWOOD_TEXTURE = new ResourceLocation(Druidcraft.MODID + "textures/entity/signs/darkwood.png");
    private final SignModel model = new SignModel();

    @Override
    public void render(SignTileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
        BlockState blockstate = tileEntityIn.getBlockState();
        GlStateManager.pushMatrix();
        float f = 0.6666667F;
        if (blockstate.getBlock() instanceof StandingSignBlock) {
            GlStateManager.translatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            GlStateManager.rotatef(-((float) (blockstate.get(StandingSignBlock.ROTATION) * 360) / 16.0F), 0.0F, 1.0F, 0.0F);
            this.model.getSignStick().showModel = true;
        } else {
            GlStateManager.translatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            GlStateManager.rotatef(-blockstate.get(WallSignBlock.FACING).getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
            GlStateManager.translatef(0.0F, -0.3125F, -0.4375F);
            this.model.getSignStick().showModel = false;
        }

        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(4.0F, 2.0F, 1.0F);
            GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            this.bindTexture(this.getCustomTextureForSign(blockstate.getBlock()));
        }
    }

    private ResourceLocation getCustomTextureForSign(Block sign) {
        if (sign != BlockRegistry.darkwood_sign && sign != BlockRegistry.darkwood_wall_sign) {
            return sign != BlockRegistry.darkwood_sign && sign != BlockRegistry.darkwood_wall_sign ? DARKWOOD_TEXTURE : DARKWOOD_TEXTURE;
        } else {
            return DARKWOOD_TEXTURE;
        }
    }
}
