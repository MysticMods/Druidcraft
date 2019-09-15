package com.vulp.druidcraft.client.renders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.client.models.DreadfishEntityModel;
import com.vulp.druidcraft.entities.DreadfishEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class DreadfishEntityRender extends MobRenderer<DreadfishEntity, DreadfishEntityModel>
{
    public DreadfishEntityRender(EntityRendererManager manager)
    {
        super(manager, new DreadfishEntityModel(), 1f);
    }

    @Override
    protected ResourceLocation getEntityTexture(DreadfishEntity entity)
    {
        return new ResourceLocation(DruidcraftRegistry.MODID + ":textures/entity/dreadfish/dreadfish.png");
    }

    public static class RenderFactory implements IRenderFactory<DreadfishEntity>
    {
        @Override
        public EntityRenderer<? super DreadfishEntity> createRenderFor(EntityRendererManager manager)
        {
            return new DreadfishEntityRender(manager);
        }
    }

    @Override
    protected void applyRotations(DreadfishEntity entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        float f = 1.0F;
        float f1 = 1.0F;
        float f2 = f * 4.3F * MathHelper.sin(f1 * 0.6F * ageInTicks);
        GlStateManager.rotatef(f2, 0.0F, 1.0F, 0.0F);
        GlStateManager.translatef(0.0F, 0.0F, -0.4F);
    }
}