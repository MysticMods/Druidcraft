package com.vulp.druidcraft.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.util.IParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.TexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class MagicRisingSparkParticle extends TexturedParticle implements IParticleRenderType {
    private final float scale;
    private final int MAX_FRAME_ID = 3;
    private int currentFrame = 0;
    private boolean directionRight = true;
    private int lastTick = 0;

    public MagicRisingSparkParticle(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        this.motionX = this.motionX * 0.10d;
        this.motionY = this.motionY * 0.01d;
        this.motionZ = this.motionZ * 0.01d;
        this.scale = this.particleScale = 0.5f;
        this.particleRed = (float) motionX;
        this.particleGreen = (float) motionY;
        this.particleBlue = (float) motionZ;
        this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, ActiveRenderInfo entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        TextureManager textureManager = Minecraft.getInstance().textureManager;
        beginRender(buffer, textureManager);
        onPreRender(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        finishRender(Tessellator.getInstance());
    }

    @Override
    public void move(double x, double y, double z) {
        super.move(x, y, z);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getScale(float scale) {
        float f = ((float)this.age + scale) / (float)this.maxAge;
        return this.particleScale * (1.0F - f * f * 0.5F);
    }

    @Override
    protected float getMinU() {
        return 0;
    }

    @Override
    protected float getMaxU() {
        return 0;
    }

    @Override
    protected float getMinV() {
        return 0;
    }

    @Override
    protected float getMaxV() {
        return 0;
    }

    public void onPreRender(BufferBuilder buffer, ActiveRenderInfo activeInfo, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        Entity entity = activeInfo.getRenderViewEntity();
        if (entity.ticksExisted >= this.lastTick + 5) {
            if (this.currentFrame == MAX_FRAME_ID) {
                this.directionRight = false;
            } else if (currentFrame == 0) {
                this.directionRight = true;
            }
            this.currentFrame = this.currentFrame + (directionRight ? 1 : -1);
            this.lastTick = entity.ticksExisted;
        }
        float f = ((float) this.age + partialTicks) / (float) this.maxAge;
        this.particleScale = this.scale;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ < this.maxAge) {
            this.motionX += this.rand.nextFloat() / 5000.0F * (float)(this.rand.nextBoolean() ? 1 : -1);
            this.motionZ += this.rand.nextFloat() / 5000.0F * (float)(this.rand.nextBoolean() ? 1 : -1);
            this.motionY -= this.rand.nextFloat() / 5000.0F * (float)(this.rand.nextBoolean() ? 1 : -1);;
            this.move(this.motionX, this.motionY, this.motionZ);
        } else {
            this.setExpired();
        }

    }

    @Override
    public void beginRender(BufferBuilder buffer, TextureManager textureManager) {
        RenderHelper.disableStandardItemLighting();
        GlStateManager.depthMask(true);
        textureManager.bindTexture(ParticleTexture.MAGIC_RISING_SPARK[currentFrame]);
        buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
    }

    @Override
    public void finishRender(Tessellator tess) {
        tess.draw();
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        float f = ((float)this.age + partialTick) / (float)this.maxAge;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        int i = super.getBrightnessForRender(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticle {

        @Override
        public net.minecraft.client.particle.Particle makeParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... params) {
            return new MagicRisingSparkParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}