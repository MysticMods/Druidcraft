package com.vulp.druidcraft.client.models;

import com.vulp.druidcraft.entities.BeetleEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeetleEntityModel extends EntityModel<BeetleEntity> {
    private final RendererModel body_1;
    private final RendererModel head;
    private final RendererModel antennae_L;
    private final RendererModel antennae_R;
    private final RendererModel mandibles;
    private final RendererModel body_2;
    private final RendererModel leg_L_front;
    private final RendererModel leg_L_middle;
    private final RendererModel leg_L_back;
    private final RendererModel leg_R_front;
    private final RendererModel leg_R_middle;
    private final RendererModel leg_R_back;
    private final RendererModel harness;
    private final RendererModel chest_L_front;
    private final RendererModel chest_L_back;
    private final RendererModel chest_R_front;
    private final RendererModel chest_R_back;
    private final RendererModel saddle_main;
    private final RendererModel saddle_front;
    private final RendererModel saddle_back;

    public BeetleEntityModel() {
        this.textureWidth = 112;
        this.textureHeight = 112;

        this.body_1 = new RendererModel(this, 0, 0);
        this.body_1.setRotationPoint(0.0F, 16.5F, -1.0F);
        this.body_1.addBox(-11.0F, -16.5F, -9.0F, 22, 18, 24, 0.0F, false);

        this.head = new RendererModel(this, 68, 42);
        this.head.setRotationPoint(0.0F, 7.5F, -3.0F);
        this.body_1.addChild(this.head);
        this.head.addBox(-5.0F, -18.0F, -14.0F, 10, 10, 8, 0.0F, false);

        this.antennae_L = new RendererModel(this, 0, 0);
        this.antennae_L.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addChild(this.antennae_L);
        this.antennae_L.addBox(-2.0F, -19.0F, -16.0F, 0, 3, 2, 0.0F, false);

        this.antennae_R = new RendererModel(this, 0, 3);
        this.antennae_R.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addChild(this.antennae_R);
        this.antennae_R.addBox(2.0F, -19.0F, -16.0F, 0, 3, 2, 0.0F, false);

        this.mandibles = new RendererModel(this, 65, 60);
        this.mandibles.setRotationPoint(0.0F, -9.0F, -14.0F);
        RotationPoint(this.mandibles, 0.4363F, 0.0F, 0.0F);
        this.head.addChild(this.mandibles);
        this.mandibles.addBox(-5.0F, 0.0F, -3.0F, 10, 0, 3, 0.0F, false);

        this.body_2 = new RendererModel(this, 68, 0);
        this.body_2.setRotationPoint(0.0F, -6.5F, 15.0F);
        this.body_1.addChild(body_2);
        this.body_2.addBox(-8.0F, -8.0F, 0.0F, 16, 16, 6, 0.0F, false);

        this.leg_L_front = new RendererModel(this, 0, 15);
        this.leg_L_front.setRotationPoint(-6.0F, 6.0F, -21.0F);
        RotationPoint(this.leg_L_front, 0.0F, -0.1745F, -0.6109F);
        this.body_2.addChild(this.leg_L_front);
        this.leg_L_front.addBox(-8.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.leg_L_middle = new RendererModel(this, 0, 15);
        this.leg_L_middle.setRotationPoint(-6.0F, 6.0F, -12.0F);
        RotationPoint(this.leg_L_middle, 0.0F, 0.0F, -0.6109F);
        this.body_2.addChild(this.leg_L_middle);
        this.leg_L_middle.addBox(-8.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.leg_L_back = new RendererModel(this, 0, 15);
        this.leg_L_back.setRotationPoint(-6.0F, 6.0F, -3.0F);
        RotationPoint(this.leg_L_back, 0.0F, 0.1745F, -0.6109F);
        this.body_2.addChild(this.leg_L_back);
        this.leg_L_back.addBox(-8.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.leg_R_front = new RendererModel(this, 0, 15);
        this.leg_R_front.setRotationPoint(6.0F, 6.0F, -21.0F);
        RotationPoint(this.leg_R_front, 0.0F, 0.1745F, 0.6109F);
        this.body_2.addChild(this.leg_R_front);
        this.leg_R_front.addBox(0.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.leg_R_middle = new RendererModel(this, 0, 15);
        this.leg_R_middle.setRotationPoint(6.0F, 6.0F, -12.0F);
        RotationPoint(this.leg_R_middle, 0.0F, 0.0F, 0.6109F);
        this.body_2.addChild(this.leg_R_middle);
        this.leg_R_middle.addBox(0.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.leg_R_back = new RendererModel(this, 0, 15);
        this.leg_R_back.setRotationPoint(6.0F, 6.0F, -3.0F);
        RotationPoint(this.leg_R_back, 0.0F, -0.1745F, 0.6109F);
        this.body_2.addChild(this.leg_R_back);
        this.leg_R_back.addBox(0.0F, 0.0F, -2.0F, 8, 4, 4, 0.0F, false);

        this.harness = new RendererModel(this, 0, 42);
        this.harness.setRotationPoint(0.0F, 5.625F, 1.25F);
        this.body_1.addChild(this.harness);
        this.harness.addBox(-11.0F, -22.125F, -10.25F, 22, 18, 24, 0.3F, false);

        this.chest_L_front = new RendererModel(this, 0, 0);
        this.chest_L_front.setRotationPoint(-12.7F, -10.625F, -3.25F);
        this.harness.addChild(this.chest_L_front);
        this.chest_L_front.addBox(-1.5F, -3.5F, -4.0F, 3, 7, 8, 0.0F, false);

        this.chest_L_back = new RendererModel(this, 0, 0);
        this.chest_L_back.setRotationPoint(-12.7F, -10.625F, 6.75F);
        this.harness.addChild(this.chest_L_back);
        this.chest_L_back.addBox(-1.5F, -3.5F, -4.0F, 3, 7, 8, 0.0F, false);

        this.chest_R_front = new RendererModel(this, 0, 0);
        this.chest_R_front.setRotationPoint(12.7F, -10.625F, -3.25F);
        this.harness.addChild(this.chest_R_front);
        this.chest_R_front.addBox(-1.5F, -3.5F, -4.0F, 3, 7, 8, 0.0F, false);

        this.chest_R_back = new RendererModel(this, 0, 0);
        this.chest_R_back.setRotationPoint(12.7F, -10.625F, 6.75F);
        this.harness.addChild(this.chest_R_back);
        this.chest_R_back.addBox(-1.5F, -3.5F, -4.0F, 3, 7, 8, 0.0F, false);

        this.saddle_main = new RendererModel(this, 44, 84);
        this.saddle_main.setRotationPoint(0.0F, -22.625F, 1.75F);
        this.harness.addChild(this.saddle_main);
        this.saddle_main.addBox(-7.0F, -0.5F, -10.0F, 14, 1, 20, 0.0F, false);

        this.saddle_front = new RendererModel(this, 78, 105);
        this.saddle_front.setRotationPoint(0.0F, -23.125F, -6.75F);
        RotationPoint(this.saddle_front, 0.2618F, 0.0F, 0.0F);
        this.harness.addChild(this.saddle_front);
        this.saddle_front.addBox(-7.0F, -1.0F, -1.5F, 14, 2, 3, 0.0F, false);

        this.saddle_back = new RendererModel(this, 44, 105);
        this.saddle_back.setRotationPoint(0.0F, -23.125F, 10.25F);
        RotationPoint(this.saddle_back, -0.2618F, 0.0F, 0.0F);
        this.harness.addChild(this.saddle_back);
        this.saddle_back.addBox(-7.0F, -1.0F, -1.5F, 14, 2, 3, 0.0F, false);
        }

    @Override
    public void render(BeetleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.body_1.render(scale);

        if (entity.isBeetleSaddled()) {
            this.harness.showModel = true;
            this.saddle_main.showModel = true;
            this.saddle_front.showModel = true;
            this.saddle_back.showModel = true;
        } else {
            this.harness.showModel = false;
            this.saddle_main.showModel = false;
            this.saddle_front.showModel = false;
            this.saddle_back.showModel = false;

            if (entity.hasChest()) {
                this.chest_L_front.showModel = true;
                this.chest_L_back.showModel = true;
                this.chest_R_front.showModel = true;
                this.chest_R_back.showModel = true;
            } else {
                this.chest_L_front.showModel = false;
                this.chest_L_back.showModel = false;
                this.chest_R_front.showModel = false;
                this.chest_R_back.showModel = false;
            }
        }
    }

    private void RotationPoint(RendererModel rendererModel, float x, float y, float z) {
        rendererModel.rotateAngleX = x;
        rendererModel.rotateAngleY = y;
        rendererModel.rotateAngleZ = z;
    }
}