package com.vulp.druidcraft.client.models;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelBox;

public class ChitinShieldModel extends Model {
    private final RendererModel bb_main;
    private final RendererModel chitin_bot_left;
    private final RendererModel chitin_bot_right;
    private final RendererModel chitin_top_left;
    private final RendererModel chitin_top_right;

    public ChitinShieldModel() {
        textureWidth = 64;
        textureHeight = 64;

        bb_main = new RendererModel(this);
        bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
        bb_main.cubeList.add(new ModelBox(bb_main, 32, 17, -1.0F, -27.0F, -1.0F, 2, 6, 6, 0.0F, false));
        bb_main.cubeList.add(new ModelBox(bb_main, 32, 0, -6.0F, -32.0F, -2.0F, 12, 16, 1, 0.0F, false));

        chitin_bot_left = new RendererModel(this);
        chitin_bot_left.setRotationPoint(-0.5F, 21.5F, -2.0F);
        setRotationAngle(chitin_bot_left, 0.0873F, 0.0873F, 0.0F);
        chitin_bot_left.cubeList.add(new ModelBox(chitin_bot_left, 0, 10, -6.9826F, -20.9374F, 0.6326F, 7, 9, 1, 0.0F, false));

        chitin_bot_right = new RendererModel(this);
        chitin_bot_right.setRotationPoint(0.5F, 21.5F, -2.0F);
        setRotationAngle(chitin_bot_right, 0.0873F, -0.0873F, 0.0F);
        chitin_bot_right.cubeList.add(new ModelBox(chitin_bot_right, 16, 10, -0.0174F, -20.9374F, 0.6326F, 7, 9, 1, 0.0F, false));

        chitin_top_left = new RendererModel(this);
        chitin_top_left.setRotationPoint(-0.5F, 26.5F, -2.0F);
        setRotationAngle(chitin_top_left, -0.0873F, 0.0873F, 0.0F);
        chitin_top_left.cubeList.add(new ModelBox(chitin_top_left, 0, 0, -6.9826F, -35.8798F, -3.5525F, 7, 9, 1, 0.0F, false));

        chitin_top_right = new RendererModel(this);
        chitin_top_right.setRotationPoint(0.5F, 26.5F, -2.0F);
        setRotationAngle(chitin_top_right, -0.0873F, -0.0873F, 0.0F);
        chitin_top_right.cubeList.add(new ModelBox(chitin_top_right, 16, 0, -0.0174F, -35.8798F, -3.5525F, 7, 9, 1, 0.0F, false));
    }

    public void render() {
        bb_main.render(0.0625F);
        chitin_bot_left.render(0.0625F);
        chitin_bot_right.render(0.0625F);
        chitin_top_left.render(0.0625F);
        chitin_top_right.render(0.0625F);
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}