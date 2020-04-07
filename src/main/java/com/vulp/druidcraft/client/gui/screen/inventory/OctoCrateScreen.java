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
    private static final ResourceLocation CRATE_GUI_TEXTURE = new ResourceLocation(Druidcraft.MODID, "textures/gui/container/generic_216.png");

    public OctoCrateScreen(CrateContainer container, PlayerInventory playerInv, ITextComponent iTextComponent) {
        super(container, playerInv, iTextComponent);
        this.passEvents = false;
        this.xSize = 338;
        this.ySize = 330;
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 89.0F, 236.0F, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(CRATE_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
    }
}