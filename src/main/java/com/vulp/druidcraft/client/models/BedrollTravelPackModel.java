package com.vulp.druidcraft.client.models;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class BedrollTravelPackModel<T extends LivingEntity> extends BipedModel<T> {
    private final RendererModel pack;
    private final RendererModel straps;
    private final RendererModel link3;
    private final RendererModel link2;
    private final RendererModel link1;

    public BedrollTravelPackModel() {
        textureWidth = 64;
        textureHeight = 64;

        pack = new RendererModel(this);
        pack.setRotationPoint(0.0F, 24.0F, 0.0F);
        pack.setTextureOffset(0, 16).addBox(-4.0F, -23.0F, 2.5F, 8, 10, 4, 0.0F, false);
        pack.setTextureOffset(24, 16).addBox(-3.5F, -18.0F, 6.5F, 7, 4, 2, 0.0F, false);
        pack.setTextureOffset(12, 30).addBox(-6.0F, -18.0F, 2.5F, 2, 4, 4, 0.0F, true);
        pack.setTextureOffset(0, 30).addBox(4.0F, -18.0F, 2.5F, 2, 4, 4, 0.0F, false);
        pack.setTextureOffset(24, 22).addBox(-4.0F, -23.0F, 6.5F, 8, 4, 1, 0.0F, false);
        pack.setTextureOffset(24, 0).addBox(-5.5F, -27.0F, 2.5F, 11, 4, 4, 0.0F, false);

        straps = new RendererModel(this);
        straps.setRotationPoint(0.0F, 0.0F, 0.0F);
        pack.addChild(straps);
        straps.setTextureOffset(0, 0).addBox(-4.0F, -24.0F, -2.0F, 8, 12, 4, 0.5F, false);

        link3 = new RendererModel(this);
        link3.setRotationPoint(0.0F, -23.0F, 7.0F);
        straps.addChild(link3);
        setRotationAngle(link3, 0.2443F, 0.0F, 0.0F);
        link3.setTextureOffset(38, 30).addBox(-3.5F, -3.979F, 0.4851F, 7, 4, 0, 0.0F, false);

        link2 = new RendererModel(this);
        link2.setRotationPoint(0.0F, -18.0F, 8.0F);
        straps.addChild(link2);
        setRotationAngle(link2, 0.192F, 0.0F, 0.0F);
        link2.setTextureOffset(24, 30).addBox(-3.5F, -5.0046F, 0.4908F, 7, 5, 0, 0.0F, false);

        link1 = new RendererModel(this);
        link1.setRotationPoint(0.0F, -14.0F, 8.0F);
        straps.addChild(link1);
        setRotationAngle(link1, -1.0996F, 0.0F, 0.0F);
        link1.setTextureOffset(0, 38).addBox(-3.5F, -0.4455F, 0.227F, 7, 2, 0, 0.0F, false);
    }


    @Override
    public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.pack.render(scale);
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
