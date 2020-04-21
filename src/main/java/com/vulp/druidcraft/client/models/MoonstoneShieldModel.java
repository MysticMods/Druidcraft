package com.vulp.druidcraft.client.models;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelBox;

public class MoonstoneShieldModel extends Model {

    private final RendererModel bb_main;
    private final RendererModel plate_left;
    private final RendererModel plate_right;

    public MoonstoneShieldModel() {
        textureWidth = 64;
        textureHeight = 64;

        bb_main = new RendererModel(this);
        bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
        bb_main.cubeList.add(new ModelBox(bb_main, 30, 6, -1.0F, -27.0F, -1.0F, 2, 6, 6, 0.0F, false));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -3.0F, -35.0F, -2.0F, 6, 22, 1, 0.0F, false));

        plate_left = new RendererModel(this);
        plate_left.setRotationPoint(-3.0F, 24.0F, -2.0F);
        setRotationAngle(plate_left, 0.0F, 0.3491F, 0.0F);
        plate_left.cubeList.add(new ModelBox(plate_left, 14, 0, -3.0F, -35.0F, 0.0F, 3, 22, 1, 0.0F, false));
        plate_left.cubeList.add(new ModelBox(plate_left, 30, 3, -4.0F, -15.0F, 0.0F, 1, 2, 1, 0.0F, false));
        plate_left.cubeList.add(new ModelBox(plate_left, 30, 0, -4.0F, -35.0F, 0.0F, 1, 2, 1, 0.0F, false));

        plate_right = new RendererModel(this);
        plate_right.setRotationPoint(3.0F, 24.0F, -2.0F);
        setRotationAngle(plate_right, 0.0F, -0.3491F, 0.0F);
        plate_right.cubeList.add(new ModelBox(plate_right, 22, 0, 0.0F, -35.0F, 0.0F, 3, 22, 1, 0.0F, false));
        plate_right.cubeList.add(new ModelBox(plate_right, 34, 3, 3.0F, -15.0F, 0.0F, 1, 2, 1, 0.0F, false));
        plate_right.cubeList.add(new ModelBox(plate_right, 34, 0, 3.0F, -35.0F, 0.0F, 1, 2, 1, 0.0F, false));
    }

    public void render() {
        bb_main.render(0.0625F);
        plate_left.render(0.0625F);
        plate_right.render(0.0625F);
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}