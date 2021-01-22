package com.vulp.druidcraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.HellkilnIgniterContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class HellkilnIgniterScreen extends ContainerScreen<HellkilnIgniterContainer> {

    private static final ResourceLocation HELLKILN_IGNITER_GUI_TEXTURES = new ResourceLocation(Druidcraft.MODID, "textures/gui/container/hellkiln_igniter.png");

    public HellkilnIgniterScreen(HellkilnIgniterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    protected void init() {
        super.init();
        this.titleX = (this.xSize - this.font.getStringPropertyWidth(this.title)) / 2;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(HELLKILN_IGNITER_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        double fuelPercentage = ((double)this.container.getFuelLevel() / 100.0) * 20.0;
        if (fuelPercentage > 0) {
            this.blit(matrixStack, i + 78, j + 52, 176, 0, (int) Math.round(fuelPercentage), 4);
        }

    }



}
