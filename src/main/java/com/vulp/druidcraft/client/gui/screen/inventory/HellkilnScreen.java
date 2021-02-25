package com.vulp.druidcraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.HellkilnContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class HellkilnScreen extends ContainerScreen<HellkilnContainer> {

    private static final ResourceLocation HELLKILN_GUI_TEXTURES = new ResourceLocation(Druidcraft.MODID, "textures/gui/container/hellkiln.png");

    public HellkilnScreen(HellkilnContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    protected void init() {
        super.init();
        this.titleX = 7;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(HELLKILN_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        int[] fuel = this.container.getIgniterFuel();
        double fuelPercentage1 = fuel[0] != -1 ? ((double)fuel[0] / 100.0) * 20.0 : 0;
        double fuelPercentage2 = fuel[1] != -1 ? ((double)fuel[1] / 100.0) * 20.0 : 0;
        double fuelPercentage3 = fuel[2] != -1 ? ((double)fuel[2] / 100.0) * 20.0 : 0;
        double cookTime = ((double)this.container.getCookTime() / (double)this.container.getTotalCookTime()) * 43.0;
        if (fuelPercentage1 > 0) {
            int remainingPercentage1 = (int) Math.round(20 - fuelPercentage1);
            this.blit(matrixStack, i + 8, j + 28 + remainingPercentage1, 194, 4 + remainingPercentage1, 4, (int) Math.round(fuelPercentage1));
        }
        if (fuelPercentage2 > 0) {
            this.blit(matrixStack, i + 78, j + 8, 194, 0, (int) Math.round(fuelPercentage2), 4);
        }
        if (fuelPercentage3 > 0) {
            int remainingPercentage3 = (int) Math.round(20 - fuelPercentage3);
            this.blit(matrixStack, i + 164, j + 28 + remainingPercentage3, 194, 4 + remainingPercentage3, 4, (int) Math.round(fuelPercentage3));
        }
        if (cookTime > 0) {
            int remainingCookTime = (int) Math.round(43 - cookTime);
            this.blit(matrixStack, i + 30, j + 25 + remainingCookTime, 176, remainingCookTime, 18, (int) Math.round(cookTime));
        }

    }



}
