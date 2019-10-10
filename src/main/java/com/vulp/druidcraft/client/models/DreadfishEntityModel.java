package com.vulp.druidcraft.client.models;

import com.vulp.druidcraft.entities.DreadfishEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DreadfishEntityModel extends EntityModel<DreadfishEntity> {
    private final RendererModel spine1;
    private final RendererModel spine2;
    private final RendererModel head1;
    private final RendererModel head2;
    private final RendererModel tail;
    private final RendererModel topFin;
    private final RendererModel sidefinR;
    private final RendererModel sidefinL;

    public DreadfishEntityModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.spine1 = new RendererModel(this, 0, 0);
        this.spine1.addBox(-1.5F, -2.5F, -3.0F, 3, 5, 6, 0.0F, false);
        this.spine1.setRotationPoint(0.0F, 21.0F, 6.0F);

        this.spine2 = new RendererModel(this, 18, 0);
        this.spine2.addBox(-1.0F, -2.5F, 0.0F, 2, 4, 4, 0.0F, false);
        this.spine2.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.spine1.addChild(this.spine2);

        this.head1 = new RendererModel(this, 8, 11);
        this.head1.addBox(-1.0F, -2.0F, -3.0F, 2, 3, 3, 0.0F, false);
        this.head1.setRotationPoint(0.0F, 0.0F, -3.0F);
        this.spine1.addChild(this.head1);

        this.head2 = new RendererModel(this, 0, 20);
        this.head2.addBox(-1.0F, 0.0F, -3.0F, 2, 1, 3, 0.0F, false);
        this.head2.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.head1.addChild(this.head2);

        this.tail = new RendererModel(this, 0, 11);
        this.tail.addBox(0.0F, -2.5F, 0.0F, 0, 5, 4, 0.0F, false);
        this.tail.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.spine2.addChild(this.tail);

        this.sidefinR = new RendererModel(this, 16, 20);
        this.sidefinR.addBox(-2.0F, 0.0F, -1.0F, 2, 0, 2, 0.0F, false);
        this.sidefinR.setRotationPoint(-1.5F, 1.5F, -2.0F);
        this.spine1.addChild(this.sidefinR);
        this.sidefinR.rotateAngleZ = -0.7853982F;

        this.sidefinL = new RendererModel(this, 0, 25);
        this.sidefinL.addBox(0.0F, 0.0F, -1.0F, 2, 0, 2, 0.0F, false);
        this.sidefinL.setRotationPoint(1.5F, 1.5F, -2.0F);
        this.spine1.addChild(this.sidefinL);
        this.sidefinL.rotateAngleZ = 0.7853982F;

        this.topFin = new RendererModel(this, 10, 20);
        this.topFin.addBox(0.0F, -7.5F, 6.0F, 0, 2, 3, 0.0F, false);
        this.topFin.setRotationPoint(0.0F, 3.0F, -6.0F);
        this.spine1.addChild(this.topFin);
    }

    @Override
    public void render(DreadfishEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.spine1.render(scale);
    }

    @Override
    public void setRotationAngles(DreadfishEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        float f = 1.5F;
        float f1 = 1.5F;
        this.spine2.rotateAngleY = -f * 0.25F * MathHelper.sin(f1 * 0.6F * ageInTicks);
        }
}