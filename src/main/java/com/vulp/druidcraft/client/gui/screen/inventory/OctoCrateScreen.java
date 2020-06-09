package com.vulp.druidcraft.client.gui.screen.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.CrateContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OctoCrateScreen extends ContainerScreen<CrateContainer> implements IHasContainer<CrateContainer> {
    private static final ResourceLocation CRATE_GUI_TEXTURE = new ResourceLocation(Druidcraft.MODID, "textures/gui/container/generic_108_216.png");

    public OctoCrateScreen(CrateContainer container, PlayerInventory playerInv, ITextComponent iTextComponent) {
        super(container, playerInv, iTextComponent);
        this.passEvents = false;
        this.xSize = 338;
        this.ySize = 330;
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 89.0F, 236.0F, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(CRATE_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        int k = (this.width / 256) / 2;
        int l = (this.height / 256) / 2;
        // Crate inventory slots.
        for (int a = 0; a < 18; a++) {
            for (int b = 0; b < 12; b++) {
                this.blit(i + 7 + (a * 18), j - l + 17 + (b * 18), 7, 4, 18, 18);
            }
        }
        // Player inventory.
        this.blit(i + 81, j - l + 243, 0, 0, 176, 87);
        // Corners.
        this.blit(i, j - l, 10, 87, 7, 7);
        this.blit(i + 331, j - l, 17, 87, 7, 7);
        this.blit(i, j - l + 233, 10, 94, 7, 7);
        this.blit(i + 331, j - l + 233, 17, 94, 7, 7);
        this.blit(i + 78, j - l + 233, 0, 87, 10, 10);
        this.blit(i + 250, j - l + 233, 0, 97, 10, 10);
        // Edges.
        for (int a = 0; a < 113; a++) {
            this.blit(i, j - l + 7 + (a * 2), 24, 87, 7, 2);
            this.blit(i + 331, j - l + 7 + (a * 2), 24, 89, 7, 2);
        }
        for (int a = 0; a < 162; a++) {
            this.blit(i + 7 + (a * 2), j - l, 24, 91, 2, 7);
        }
        for (int a = 0; a < 36; a++) {
            this.blit(i + 7 + (a * 2), j - l + 233, 26, 91, 2, 7);
            this.blit(i + 260 + (a * 2), j - l + 233, 26, 91, 2, 7);
        }
        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 162; b++) {
                this.blit(i + 7 + (b * 2), j - l + 7 + (a * 2), 28, 91, 2, 2);
            }
            for (int b = 0; b < 81; b++) {
                this.blit(i + 88 + (b * 2), j - l + 233 + (a * 2), 28, 91, 2, 2);
            }
        }
    }
}