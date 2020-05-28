package com.vulp.druidcraft.client.gui.screen.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.inventory.TravelPackInventory;
import com.vulp.druidcraft.inventory.container.CrateContainer;
import com.vulp.druidcraft.inventory.container.TravelPackContainer;
import com.vulp.druidcraft.items.TravelPackItem;
import com.vulp.druidcraft.network.PacketHandler;
import com.vulp.druidcraft.network.message.DeployBedrollMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.BeaconScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.BeaconContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraft.network.play.client.CUpdateBeaconPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TravelPackScreen extends ContainerScreen<TravelPackContainer> implements IHasContainer<TravelPackContainer> {
    private static final ResourceLocation TRAVEL_PACK_GUI_TEXTURE = new ResourceLocation(Druidcraft.MODID, "textures/gui/container/travel_pack.png");
    private ClickableButton bedrollButton;
    private ClickableButton button2;
    private ClickableButton button3;
    private ClickableButton button4;

    public TravelPackScreen(TravelPackContainer container, PlayerInventory playerInv, ITextComponent iTextComponent) {
        super(container, playerInv, iTextComponent);
        this.xSize = 176;
        this.ySize = 151;
        this.passEvents = false;
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.bedrollButton = this.addButton(new BedrollButton(i + 18, j + 20, "item." + DruidcraftRegistry.MODID + ".travel_pack.button1"));
        this.button2 = this.addButton(new ClickableButton(i + 152, j + 20, "item." + DruidcraftRegistry.MODID + ".travel_pack.button2"));
        this.button3 = this.addButton(new ClickableButton(i + 18, j + 38, "item." + DruidcraftRegistry.MODID + ".travel_pack.button3"));
        this.button4 = this.addButton(new ClickableButton(i + 152, j + 38, "item." + DruidcraftRegistry.MODID + ".travel_pack.button4"));
        this.bedrollButton.active = false;
        this.button2.active = false;
        this.button3.active = false;
        this.button4.active = false;
    }

    @Override
    public void tick() {
        super.tick();
        this.bedrollButton.active = this.container.hasBedroll();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, 57.0F, 4210752);
        for(Widget widget : this.buttons) {
            if (widget.isHovered()) {
                widget.renderToolTip(mouseX - this.guiLeft, mouseY - this.guiTop);
                break;
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TRAVEL_PACK_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
        for(int a = 0; a < 2; a++) {
            for(int b = 0; b < 2; b++) {
                this.blit(i + 18 + 134 * b, j + 20 + 18 * a, 12, 151, 6, 16);
            }
        }
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }

    @OnlyIn(Dist.CLIENT)
    class BedrollButton extends ClickableButton {

        public BedrollButton(int p_i50828_2_, int p_i50828_3_, String resourceLocation) {
            super(p_i50828_2_, p_i50828_3_, resourceLocation);
        }

        @Override
        public void onPress() {
            Minecraft minecraft = TravelPackScreen.this.minecraft;
            if (minecraft != null) {
                Container travelPackContainer = minecraft.player.openContainer;
                minecraft.player.connection.sendPacket(new CCloseWindowPacket(travelPackContainer.windowId));
                minecraft.displayGuiScreen(null);
                PacketHandler.instance.sendToServer(new DeployBedrollMessage(minecraft.player.getEntityId()));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    class ClickableButton extends Button {

        private String resourceLocation;

        public ClickableButton(int p_i50828_2_, int p_i50828_3_, String resourceLocation) {
            super(p_i50828_2_, p_i50828_3_);
            this.resourceLocation = resourceLocation;
        }

        public void onPress() {
        }

        public void renderToolTip(int p_renderToolTip_1_, int p_renderToolTip_2_) {
            TravelPackScreen.this.renderTooltip(I18n.format(resourceLocation), p_renderToolTip_1_, p_renderToolTip_2_);
        }
    }

    @OnlyIn(Dist.CLIENT)
    abstract static class Button extends AbstractButton {
        private boolean selected;

        protected Button(int xIn, int yIn) {
            super(xIn, yIn, 6, 16, "");
        }

        public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
            Minecraft.getInstance().getTextureManager().bindTexture(TRAVEL_PACK_GUI_TEXTURE);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int lvt_5_1_ = 0;
            if (!this.active) {
                lvt_5_1_ += this.width * 2;
            } else if (this.selected) {
                lvt_5_1_ += this.width;
            } else if (this.isHovered()) {
                lvt_5_1_ += this.width * 3;
            }

            this.blit(this.x, this.y, lvt_5_1_, 151, this.width, this.height);
        }

        public boolean isSelected() {
            return this.selected;
        }

        public void setSelected(boolean p_146140_1_) {
            this.selected = p_146140_1_;
        }
    }

}