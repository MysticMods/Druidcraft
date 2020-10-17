package com.vulp.druidcraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.vulp.druidcraft.inventory.container.CrateContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DoubleCrateScreen extends ContainerScreen<CrateContainer> implements IHasContainer<CrateContainer> {
    private static final ResourceLocation CRATE_GUI_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");

    public DoubleCrateScreen(CrateContainer container, PlayerInventory playerInv, ITextComponent iTextComponent) {
        super(container, playerInv, iTextComponent);
        this.passEvents = false;
        //this.xSize = 230;
        this.ySize = 114 + 6 * 19;
    }

    @Override
    public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredTooltip(matrixStack, p_render_1_, p_render_2_);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.drawString(matrixStack, this.title.getString(), 8.0F, 6.0F, 4210752);
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 35.0F, 182.0F, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(CRATE_GUI_TEXTURE);
      int lvt_4_1_ = (this.width - this.xSize) / 2;
      int lvt_5_1_ = (this.height - this.ySize) / 2;
      this.blit(matrixStack, lvt_4_1_, lvt_5_1_, 0, 0, this.xSize, 6 * 18 + 17);
      this.blit(matrixStack, lvt_4_1_, lvt_5_1_ + 6 * 18 + 17, 0, 126, this.xSize, 96);
    }
}