package com.vulp.druidcraft.client.gui.screen.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeetleInventoryScreen extends ContainerScreen<BeetleInventoryContainer> {
    private static final ResourceLocation BEETLE_GUI_TEXTURES = new ResourceLocation(Druidcraft.MODID, "textures/gui/container/beetle.png");
    private final BeetleEntity beetleEntity;
    private float mousePosx;
    private float mousePosY;

    public BeetleInventoryScreen(BeetleInventoryContainer container, PlayerInventory inventory, ITextComponent text) {
        super(container, inventory, container.getBeetle().getDisplayName());
        this.xSize = 257;
        this.ySize = 238;
        this.beetleEntity = container.getBeetle();
        this.passEvents = false;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 18.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 49.0F, 144.0F, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BEETLE_GUI_TEXTURES);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
        if (this.beetleEntity instanceof BeetleEntity) {
            BeetleEntity beetleEntity = this.beetleEntity;
            if (beetleEntity.hasChest()) {
                for (int k = 0; k < 7; k++) {
                    this.blit(i + 84, j + 17 + (k * 18), 0, this.ySize, 162, 18);
                }
            }
        }
        InventoryScreen.drawEntityOnScreen(i + 43, j + 62, 17, (float)(i + 43) - this.mousePosx, (float)(j + 48) - this.mousePosY, this.beetleEntity);
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.mousePosx = (float)p_render_1_;
        this.mousePosY = (float)p_render_2_;
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }
}
