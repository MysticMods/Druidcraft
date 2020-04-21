package com.vulp.druidcraft.client.models;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;

public class BoneShieldModel extends Model {
    private final RendererModel cage_right;
    private final RendererModel bone2;
    private final RendererModel main;
    private final RendererModel bone;
    private final RendererModel cage_left;
    private final RendererModel bone3;

    public BoneShieldModel() {
        textureWidth = 64;
        textureHeight = 64;

        cage_right = new RendererModel(this);
        cage_right.setRotationPoint(0.0F, 24.0F, -2.0F);
        setRotationAngle(cage_right, 0.0F, -0.1745F, 0.0F);
        cage_right.cubeList.add(new ModelBox(cage_right, 0, 0, 0.0F, -31.0F, 0.0F, 6, 10, 1, 0.0F, false));

        bone2 = new RendererModel(this);
        bone2.setRotationPoint(0.0F, 2.0F, 0.0F);
        setRotationAngle(bone2, 0.1745F, 0.0F, 0.0F);
        cage_right.addChild(bone2);
        bone2.cubeList.add(new ModelBox(bone2, 0, 11, 0.0F, -22.6506F, 3.9939F, 4, 4, 1, 0.0F, false));

        main = new RendererModel(this);
        main.setRotationPoint(0.0F, 24.0F, 0.0F);
        main.cubeList.add(new ModelBox(main, 20, 0, -1.0F, -27.0F, -1.0F, 2, 6, 6, 0.0F, false));
        main.cubeList.add(new ModelBox(main, 14, 0, -1.0F, -31.0F, -2.7F, 2, 10, 1, 0.0F, false));

        bone = new RendererModel(this);
        bone.setRotationPoint(0.0F, 2.0F, -2.7F);
        setRotationAngle(bone, 0.1745F, 0.0F, 0.0F);
        main.addChild(bone);
        bone.cubeList.add(new ModelBox(bone, 14, 10, -1.0F, -22.6506F, 3.9939F, 2, 6, 1, 0.0F, false));

        cage_left = new RendererModel(this);
        cage_left.setRotationPoint(0.0F, 24.0F, -2.0F);
        setRotationAngle(cage_left, 0.0F, 0.1745F, 0.0F);
        cage_left.cubeList.add(new ModelBox(cage_left, 0, 16, -6.0F, -31.0F, 0.0F, 6, 10, 1, 0.0F, true));

        bone3 = new RendererModel(this);
        bone3.setRotationPoint(0.0F, 2.0F, 0.0F);
        setRotationAngle(bone3, 0.1745F, 0.0F, 0.0F);
        cage_left.addChild(bone3);
        bone3.cubeList.add(new ModelBox(bone3, 0, 11, -4.0F, -22.6506F, 3.9939F, 4, 4, 1, 0.0F, true));
    }

    public void render() {
        cage_right.render(0.0625F);
        main.render(0.0625F);
        cage_left.render(0.0625F);
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}