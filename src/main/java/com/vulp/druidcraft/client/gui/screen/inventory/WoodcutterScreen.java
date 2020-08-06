package com.vulp.druidcraft.client.gui.screen.inventory;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.vulp.druidcraft.inventory.container.WoodcutterContainer;
import com.vulp.druidcraft.recipes.WoodcuttingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WoodcutterScreen extends ContainerScreen<WoodcutterContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/container/stonecutter.png");
    private float sliderProgress;
    private boolean clickedOnSroll;
    private int recipeIndexOffset;
    private boolean hasItemsInInputSlot;

    public WoodcutterScreen(WoodcutterContainer p_i51076_1_, PlayerInventory p_i51076_2_, ITextComponent p_i51076_3_) {
        super(p_i51076_1_, p_i51076_2_, p_i51076_3_);
        p_i51076_1_.setInventoryUpdateListener(this::onInventoryUpdate);
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 4.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int left = this.guiLeft;
        int top = this.guiTop;
        this.blit(left, top, 0, 0, this.xSize, this.ySize);
        int progress = (int)(41.0F * this.sliderProgress);
        this.blit(left + 119, top + 15 + progress, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
        int offsetX = this.guiLeft + 52;
        int offsetY = this.guiTop + 14;
        int recipeOffset = this.recipeIndexOffset + 12;
        this.drawRecipesBackground(p_146976_2_, p_146976_3_, offsetX, offsetY, recipeOffset);
        this.drawRecipesItems(offsetX, offsetY, recipeOffset);
    }

    private void drawRecipesBackground(int p_214141_1_, int p_214141_2_, int p_214141_3_, int p_214141_4_, int p_214141_5_) {
        for(int lvt_6_1_ = this.recipeIndexOffset; lvt_6_1_ < p_214141_5_ && lvt_6_1_ < (this.container).getRecipeListSize(); ++lvt_6_1_) {
            int lvt_7_1_ = lvt_6_1_ - this.recipeIndexOffset;
            int lvt_8_1_ = p_214141_3_ + lvt_7_1_ % 4 * 16;
            int lvt_9_1_ = lvt_7_1_ / 4;
            int lvt_10_1_ = p_214141_4_ + lvt_9_1_ * 18 + 2;
            int lvt_11_1_ = this.ySize;
            if (lvt_6_1_ == (this.container).getSelectedRecipe()) {
                lvt_11_1_ += 18;
            } else if (p_214141_1_ >= lvt_8_1_ && p_214141_2_ >= lvt_10_1_ && p_214141_1_ < lvt_8_1_ + 16 && p_214141_2_ < lvt_10_1_ + 18) {
                lvt_11_1_ += 36;
            }

            this.blit(lvt_8_1_, lvt_10_1_ - 1, 0, lvt_11_1_, 16, 18);
        }

    }

    private void drawRecipesItems(int p_214142_1_, int p_214142_2_, int p_214142_3_) {
        RenderHelper.enableStandardItemLighting();
        List<WoodcuttingRecipe> lvt_4_1_ = ((this.container).getRecipeList());

        for(int lvt_5_1_ = this.recipeIndexOffset; lvt_5_1_ < p_214142_3_ && lvt_5_1_ < (this.container).getRecipeListSize(); ++lvt_5_1_) {
            int lvt_6_1_ = lvt_5_1_ - this.recipeIndexOffset;
            int lvt_7_1_ = p_214142_1_ + lvt_6_1_ % 4 * 16;
            int lvt_8_1_ = lvt_6_1_ / 4;
            int lvt_9_1_ = p_214142_2_ + lvt_8_1_ * 18 + 2;
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI((lvt_4_1_.get(lvt_5_1_)).getRecipeOutput(), lvt_7_1_, lvt_9_1_);
        }

        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        this.clickedOnSroll = false;
        if (this.hasItemsInInputSlot) {
            int lvt_6_1_ = this.guiLeft + 52;
            int lvt_7_1_ = this.guiTop + 14;
            int lvt_8_1_ = this.recipeIndexOffset + 12;

            for(int lvt_9_1_ = this.recipeIndexOffset; lvt_9_1_ < lvt_8_1_; ++lvt_9_1_) {
                int lvt_10_1_ = lvt_9_1_ - this.recipeIndexOffset;
                double lvt_11_1_ = p_mouseClicked_1_ - (double)(lvt_6_1_ + lvt_10_1_ % 4 * 16);
                double lvt_13_1_ = p_mouseClicked_3_ - (double)(lvt_7_1_ + lvt_10_1_ / 4 * 18);
                if (lvt_11_1_ >= 0.0D && lvt_13_1_ >= 0.0D && lvt_11_1_ < 16.0D && lvt_13_1_ < 18.0D && (this.container).enchantItem(this.minecraft.player, lvt_9_1_)) {
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.minecraft.playerController.sendEnchantPacket((this.container).windowId, lvt_9_1_);
                    return true;
                }
            }

            lvt_6_1_ = this.guiLeft + 119;
            lvt_7_1_ = this.guiTop + 9;
            if (p_mouseClicked_1_ >= (double)lvt_6_1_ && p_mouseClicked_1_ < (double)(lvt_6_1_ + 12) && p_mouseClicked_3_ >= (double)lvt_7_1_ && p_mouseClicked_3_ < (double)(lvt_7_1_ + 54)) {
                this.clickedOnSroll = true;
            }
        }

        return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
    }

    @Override
    public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
        if (this.clickedOnSroll && this.canScroll()) {
            int lvt_10_1_ = this.guiTop + 14;
            int lvt_11_1_ = lvt_10_1_ + 54;
            this.sliderProgress = ((float)p_mouseDragged_3_ - (float)lvt_10_1_ - 7.5F) / ((float)(lvt_11_1_ - lvt_10_1_) - 15.0F);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)this.getHiddenRows()) + 0.5D) * 4;
            return true;
        } else {
            return super.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_);
        }
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
        if (this.canScroll()) {
            int lvt_7_1_ = this.getHiddenRows();
            this.sliderProgress = (float)((double)this.sliderProgress - p_mouseScrolled_5_ / (double)lvt_7_1_);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)lvt_7_1_) + 0.5D) * 4;
        }

        return true;
    }

    private boolean canScroll() {
        return this.hasItemsInInputSlot && (this.container).getRecipeListSize() > 12;
    }

    protected int getHiddenRows() {
        return ((this.container).getRecipeListSize() + 4 - 1) / 4 - 3;
    }

    private void onInventoryUpdate() {
        this.hasItemsInInputSlot = (this.container).hasItemsinInputSlot();
        if (!this.hasItemsInInputSlot) {
            this.sliderProgress = 0.0F;
            this.recipeIndexOffset = 0;
        }

    }
}