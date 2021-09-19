/*
package com.vulp.druidcraft.client.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.api.ButtonStateIndex;
import com.vulp.druidcraft.inventory.container.MortarAndPestleContainer;
import com.vulp.druidcraft.inventory.container.SmithingWorkbenchContainer;
import com.vulp.druidcraft.network.PacketHandler;
import com.vulp.druidcraft.network.message.DeployBedrollMessage;
import com.vulp.druidcraft.network.message.MortarGrindMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MortarAndPestleScreen extends ContainerScreen<MortarAndPestleContainer> implements IContainerListener {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Druidcraft.MODID, "textures/gui/container/mortar_and_pestle.png");
    private GrindButton grindButton;
    private int grindTimer = 0;

    public MortarAndPestleScreen(MortarAndPestleContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.titleX = 10;
        this.titleY = 6;
    }

    protected void initFields() {
    }

    protected void init() {
        super.init();
        this.initFields();
        (this.container).addListener(this);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.grindButton = this.addButton(new GrindButton(i + 77, j + 58, "block." + DruidcraftRegistry.MODID + ".mortar_and_pestle"));
        this.grindButton.disabled = true;
        this.grindButton.auto = false;
    }

    @Override
    public void tick() {
        super.tick();
        this.grindButton.disabled = !this.container.canGrind();
        this.grindButton.auto = this.container.isAuto();
        if (this.grindButton.keyPressed && !this.grindButton.disabled && !this.grindButton.auto) {
            Minecraft minecraft = MortarAndPestleScreen.this.minecraft;
            if (this.grindTimer > 1 && minecraft != null) {
                PacketHandler.instance.sendToServer(new MortarGrindMessage(MortarAndPestleScreen.this.container.windowId));
                this.grindTimer = 0;
            } else {
                this.grindTimer++;
            }
        }
    }

    public void onClose() {
        this.grindButton.keyPressed = false;
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
        for(Widget widget : this.buttons) {
            if (widget.isHovered()) {
                widget.renderToolTip(matrixStack, x - this.guiLeft, y - this.guiTop);
                break;
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        if (this.container.canGrind()) {
            this.blit(matrixStack, i + 76, j + 34, this.xSize, 0, this.container.getGrindValue(), 17);
        } else {
            this.blit(matrixStack, i + 76, j + 34, this.xSize + 25, 0, 25, 17);
        }
    }

    public void sendAllContents(Container p_71110_1_, NonNullList<ItemStack> p_71110_2_) {
        this.sendSlotContents(p_71110_1_, 0, p_71110_1_.getSlot(0).getStack());
    }

    public void sendWindowProperty(Container p_71112_1_, int p_71112_2_, int p_71112_3_) {
    }

    public void sendSlotContents(Container p_71111_1_, int p_71111_2_, ItemStack p_71111_3_) {
    }

    @Override
    public boolean mouseReleased(double p_231048_1_, double p_231048_3_, int p_231048_5_) {
        this.grindButton.keyPressed = false;
        return super.mouseReleased(p_231048_1_, p_231048_3_, p_231048_5_);
    }

    @OnlyIn(Dist.CLIENT)
    class GrindButton extends AbstractButton {

        private String resourceLocation;
        private ButtonStateIndex index;
        private boolean disabled;
        private boolean auto;
        private boolean keyPressed;

        public GrindButton(int xIn, int yIn, String resourceLocation) {
            super(xIn, yIn, 22, 16, ITextComponent.getTextComponentOrEmpty(""));
            this.resourceLocation = resourceLocation;
        }

        @Override
        public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            Minecraft.getInstance().getTextureManager().bindTexture(GUI_TEXTURE);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (!this.disabled) {
                if (this.auto) {
                    this.index = ButtonStateIndex.AUTO;
                } else if (this.keyPressed) {
                    this.index = ButtonStateIndex.PRESSED;
                } else if (this.isHovered) {
                    this.index = ButtonStateIndex.HOVERED;
                } else {
                    this.index = ButtonStateIndex.DEFAULT;
                }
            } else {
                this.index = ButtonStateIndex.DISABLED;
            }
            this.blit(matrixStack, this.x, this.y, 176, texHeightFromIndex(this.index), this.width, this.height);
        }

        @Override
        public void onPress() {
            this.keyPressed = true;
        }

        @Override
        // TODO: Change sound!
        public void playDownSound(SoundHandler handler) {
            handler.play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }

        public int texHeightFromIndex(ButtonStateIndex index) {
            if (auto) {
                return 81;
            } else switch (index) {
                case DISABLED:
                    return 17;
                case HOVERED:
                    return 49;
                case PRESSED:
                    return 65;
                case AUTO:
                    return 81;
                case DEFAULT:
                default:
                    return 33;
            }
        }

        @Override
        public void renderToolTip(MatrixStack matrixStack, int p_renderToolTip_1_, int p_renderToolTip_2_) {
            MortarAndPestleScreen.this.renderTooltip(matrixStack, getResourceLocation(this.index), p_renderToolTip_1_, p_renderToolTip_2_);
        }

        public ITextComponent getResourceLocation(ButtonStateIndex index) {
            String string;
            if (index == ButtonStateIndex.PRESSED) {
                string = ".button_pressed";
            } else if (index == ButtonStateIndex.AUTO) {
                string = ".button_auto";
            } else {
                string = index == ButtonStateIndex.DISABLED ? ".button_disabled" : ".button_enabled";
            }
            return ITextComponent.getTextComponentOrEmpty(I18n.format(this.resourceLocation + string));

        }

    }

}
*/
