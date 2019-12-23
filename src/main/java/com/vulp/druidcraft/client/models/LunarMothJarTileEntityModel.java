package com.vulp.druidcraft.client.models;

import com.mojang.blaze3d.platform.GLX;
import com.vulp.druidcraft.blocks.tileentities.LunarMothJarTileEntity;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LunarMothJarTileEntityModel extends Model {
    private final RendererModel main;
    private final RendererModel left_wing;
    private final RendererModel right_wing;

    public LunarMothJarTileEntityModel() {
        this.textureWidth = 8;
        this.textureHeight = 8;

        this.main = new RendererModel(this);
        this.main.setRotationPoint(0.0F, 22.0F, 7.5F);

        this.left_wing = new RendererModel(this, 0, 0);
        this.left_wing.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.main.addChild(this.left_wing);
        this.left_wing.addBox(-2.0F, 0.0F, -2.5F, 2, 0, 5, 0.0F, false);

        this.right_wing = new RendererModel(this, 0, 0);
        this.right_wing.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.main.addChild(this.right_wing);
        this.right_wing.addBox(0.0F, 0.0F, -2.5F, 2, 0, 5, 0.0F, true);
    }

    public void render(LunarMothJarTileEntity entity, float scale) {
        setRotationAngle(entity.ageInTicks);
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 240.0F, 240.0F);
        this.main.render(scale);
    }

    public void RotationAngles(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setRotationAngle(float ageInTicks) {
        RotationAngles(this.main, -0.6109F, 0.0F, 0.0F);
        RotationAngles(this.left_wing, 0.0F, 0.0F, MathHelper.cos(ageInTicks * 1.3F) * 3.1415927F * 0.25F);
        RotationAngles(this.right_wing, 0.0F, 0.0F, -(MathHelper.cos(ageInTicks * 1.3F) * 3.1415927F * 0.25F));
    }
}