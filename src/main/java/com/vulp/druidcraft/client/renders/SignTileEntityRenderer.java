package com.vulp.druidcraft.client.renders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.StandingSignBlock;
import com.vulp.druidcraft.blocks.WallSignBlock;
import com.vulp.druidcraft.entities.tileentity.SignTileEntity;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.model.SignModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import java.util.List;

public class SignTileEntityRenderer extends TileEntityRenderer<SignTileEntity> {

    private static final ResourceLocation DARKWOOD_TEXTURE = new ResourceLocation(Druidcraft.MODID, "textures/entity/signs/darkwood.png");
    private final SignModel model = new SignModel();

    @Override
    public void render(SignTileEntity p_199341_1_, double p_199341_2_, double p_199341_4_, double p_199341_6_, float p_199341_8_, int p_199341_9_) {
        BlockState lvt_10_1_ = p_199341_1_.getBlockState();
        GlStateManager.pushMatrix();
        float lvt_11_1_ = 0.6666667F;
        if (lvt_10_1_.getBlock() instanceof StandingSignBlock) {
            GlStateManager.translatef((float)p_199341_2_ + 0.5F, (float)p_199341_4_ + 0.5F, (float)p_199341_6_ + 0.5F);
            GlStateManager.rotatef(-((float)((Integer)lvt_10_1_.get(StandingSignBlock.ROTATION) * 360) / 16.0F), 0.0F, 1.0F, 0.0F);
            this.model.getSignStick().showModel = true;
        } else {
            GlStateManager.translatef((float)p_199341_2_ + 0.5F, (float)p_199341_4_ + 0.5F, (float)p_199341_6_ + 0.5F);
            GlStateManager.rotatef(-((Direction)lvt_10_1_.get(WallSignBlock.FACING)).getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
            GlStateManager.translatef(0.0F, -0.3125F, -0.4375F);
            this.model.getSignStick().showModel = false;
        }

        if (p_199341_9_ >= 0) {
            this.bindTexture(DESTROY_STAGES[p_199341_9_]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(4.0F, 2.0F, 1.0F);
            GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            this.bindTexture(this.getCustomTextureForSign(lvt_10_1_.getBlock()));
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.scalef(0.6666667F, -0.6666667F, -0.6666667F);
        this.model.renderSign();
        GlStateManager.popMatrix();
        FontRenderer lvt_12_1_ = this.getFontRenderer();
        float lvt_13_1_ = 0.010416667F;
        GlStateManager.translatef(0.0F, 0.33333334F, 0.046666667F);
        GlStateManager.scalef(0.010416667F, -0.010416667F, 0.010416667F);
        GlStateManager.normal3f(0.0F, 0.0F, -0.010416667F);
        GlStateManager.depthMask(false);
        int lvt_14_1_ = p_199341_1_.getTextColor().getTextColor();
        if (p_199341_9_ < 0) {
            for(int lvt_15_1_ = 0; lvt_15_1_ < 4; ++lvt_15_1_) {
                String lvt_16_1_ = p_199341_1_.getRenderText(lvt_15_1_, (p_212491_1_) -> {
                    List<ITextComponent> lvt_2_1_ = RenderComponentsUtil.splitText(p_212491_1_, 90, lvt_12_1_, false, true);
                    return lvt_2_1_.isEmpty() ? "" : ((ITextComponent)lvt_2_1_.get(0)).getFormattedText();
                });
                if (lvt_16_1_ != null) {
                    lvt_12_1_.drawString(lvt_16_1_, (float)(-lvt_12_1_.getStringWidth(lvt_16_1_) / 2), (float)(lvt_15_1_ * 10 - p_199341_1_.signText.length * 5), lvt_14_1_);
                    if (lvt_15_1_ == p_199341_1_.getLineBeingEdited() && p_199341_1_.func_214065_t() >= 0) {
                        int lvt_17_1_ = lvt_12_1_.getStringWidth(lvt_16_1_.substring(0, Math.max(Math.min(p_199341_1_.func_214065_t(), lvt_16_1_.length()), 0)));
                        int lvt_18_1_ = lvt_12_1_.getBidiFlag() ? -1 : 1;
                        int lvt_19_1_ = (lvt_17_1_ - lvt_12_1_.getStringWidth(lvt_16_1_) / 2) * lvt_18_1_;
                        int lvt_20_1_ = lvt_15_1_ * 10 - p_199341_1_.signText.length * 5;
                        int var10001;
                        if (p_199341_1_.func_214069_r()) {
                            if (p_199341_1_.func_214065_t() < lvt_16_1_.length()) {
                                var10001 = lvt_20_1_ - 1;
                                int var10002 = lvt_19_1_ + 1;
                                lvt_12_1_.getClass();
                                AbstractGui.fill(lvt_19_1_, var10001, var10002, lvt_20_1_ + 9, -16777216 | lvt_14_1_);
                            } else {
                                lvt_12_1_.drawString("_", (float)lvt_19_1_, (float)lvt_20_1_, lvt_14_1_);
                            }
                        }

                        if (p_199341_1_.func_214067_u() != p_199341_1_.func_214065_t()) {
                            int lvt_21_1_ = Math.min(p_199341_1_.func_214065_t(), p_199341_1_.func_214067_u());
                            int lvt_22_1_ = Math.max(p_199341_1_.func_214065_t(), p_199341_1_.func_214067_u());
                            int lvt_23_1_ = (lvt_12_1_.getStringWidth(lvt_16_1_.substring(0, lvt_21_1_)) - lvt_12_1_.getStringWidth(lvt_16_1_) / 2) * lvt_18_1_;
                            int lvt_24_1_ = (lvt_12_1_.getStringWidth(lvt_16_1_.substring(0, lvt_22_1_)) - lvt_12_1_.getStringWidth(lvt_16_1_) / 2) * lvt_18_1_;
                            var10001 = Math.min(lvt_23_1_, lvt_24_1_);
                            int var10003 = Math.max(lvt_23_1_, lvt_24_1_);
                            lvt_12_1_.getClass();
                            this.func_217657_a(var10001, lvt_20_1_, var10003, lvt_20_1_ + 9);
                        }
                    }
                }
            }
        }

        GlStateManager.depthMask(true);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
        if (p_199341_9_ >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }

    }

    private ResourceLocation getCustomTextureForSign(Block sign) {
        if (sign != BlockRegistry.darkwood_sign && sign != BlockRegistry.darkwood_wall_sign) {
            return sign != BlockRegistry.darkwood_sign && sign != BlockRegistry.darkwood_wall_sign ? DARKWOOD_TEXTURE : DARKWOOD_TEXTURE;
        } else {
            return DARKWOOD_TEXTURE;
        }
    }

    private void func_217657_a(int p_217657_1_, int p_217657_2_, int p_217657_3_, int p_217657_4_) {
        Tessellator lvt_5_1_ = Tessellator.getInstance();
        BufferBuilder lvt_6_1_ = lvt_5_1_.getBuffer();
        GlStateManager.color4f(0.0F, 0.0F, 255.0F, 255.0F);
        GlStateManager.disableTexture();
        GlStateManager.enableColorLogicOp();
        GlStateManager.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        lvt_6_1_.begin(7, DefaultVertexFormats.POSITION);
        lvt_6_1_.pos((double) p_217657_1_, (double) p_217657_4_, 0.0D).endVertex();
        lvt_6_1_.pos((double) p_217657_3_, (double) p_217657_4_, 0.0D).endVertex();
        lvt_6_1_.pos((double) p_217657_3_, (double) p_217657_2_, 0.0D).endVertex();
        lvt_6_1_.pos((double) p_217657_1_, (double) p_217657_2_, 0.0D).endVertex();
        lvt_5_1_.draw();
        GlStateManager.disableColorLogicOp();
        GlStateManager.enableTexture();
    }
}
