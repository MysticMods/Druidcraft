package com.vulp.druidcraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.SmithingWorkbenchContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.SmithingTableScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractRepairContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmithingWorkbenchScreen extends ContainerScreen<SmithingWorkbenchContainer> implements IContainerListener {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Druidcraft.MODID, "textures/gui/container/smithing_workbench.png");

    public SmithingWorkbenchScreen(SmithingWorkbenchContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.titleX = 10;
        this.titleY = 6;
        this.ySize = 176;
        this.playerInventoryTitleY += 10;
    }

    protected void initFields() {
    }

    protected void init() {
        super.init();
        this.initFields();
        (this.container).addListener(this);
    }

    public void onClose() {
        super.onClose();
        (this.container).removeListener(this);
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.disableBlend();
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        RenderSystem.disableBlend();
        super.drawGuiContainerForegroundLayer(matrixStack, x, y);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        this.blit(matrixStack, i + 50, j + 16, this.xSize, 21 + (this.container.getSlot(1).getHasStack() ? 0 : 18), 18, 18);
        this.blit(matrixStack, i + 50, j + 62, this.xSize, 21 + (this.container.getSlot(2).getHasStack() ? 0 : 18), 18, 18);
        this.blit(matrixStack, i + 27, j + 27, this.xSize, 21 + (this.container.getSlot(3).getHasStack() ? 0 : 18), 18, 18);
        this.blit(matrixStack, i + 27, j + 51, this.xSize, 21 + (this.container.getSlot(4).getHasStack() ? 0 : 18), 18, 18);
        this.blit(matrixStack, i + 73, j + 27, this.xSize, 21 + (this.container.getSlot(5).getHasStack() ? 0 : 18), 18, 18);
        this.blit(matrixStack, i + 73, j + 51, this.xSize, 21 + (this.container.getSlot(6).getHasStack() ? 0 : 18), 18, 18);
        if (!this.container.isEmpty() && !this.container.getSlot(7).getHasStack()) {
            this.blit(matrixStack, i + 99, j + 38, this.xSize, 0, 28, 21);
        }
    }

    public void sendAllContents(Container p_71110_1_, NonNullList<ItemStack> p_71110_2_) {
        this.sendSlotContents(p_71110_1_, 0, p_71110_1_.getSlot(0).getStack());
    }

    public void sendWindowProperty(Container p_71112_1_, int p_71112_2_, int p_71112_3_) {
    }

    public void sendSlotContents(Container p_71111_1_, int p_71111_2_, ItemStack p_71111_3_) {
    }

}
