package com.vulp.druidcraft.entities.tileentity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.Function;

public class SignTileEntity extends TileEntity {
    public final ITextComponent[] signText = new ITextComponent[]{new StringTextComponent(""), new StringTextComponent(""), new StringTextComponent(""), new StringTextComponent("")};
    @OnlyIn(Dist.CLIENT)
    private boolean field_214070_b;
    private int lineBeingEdited = -1;
    private int field_214071_g = -1;
    private int field_214072_h = -1;
    private boolean isEditable = true;
    private PlayerEntity player;
    private final String[] renderText = new String[4];
    private DyeColor textColor;

    public SignTileEntity() {
        super(TileEntityRegistry.sign_te);
        this.textColor = DyeColor.BLACK;
    }

    public CompoundNBT write(CompoundNBT p_189515_1_) {
        super.write(p_189515_1_);

        for(int lvt_2_1_ = 0; lvt_2_1_ < 4; ++lvt_2_1_) {
            String lvt_3_1_ = ITextComponent.Serializer.toJson(this.signText[lvt_2_1_]);
            p_189515_1_.putString("Text" + (lvt_2_1_ + 1), lvt_3_1_);
        }

        p_189515_1_.putString("Color", this.textColor.getTranslationKey());
        return p_189515_1_;
    }

    public void read(CompoundNBT p_145839_1_) {
        this.isEditable = false;
        super.read(p_145839_1_);
        this.textColor = DyeColor.byTranslationKey(p_145839_1_.getString("Color"), DyeColor.BLACK);

        for(int lvt_2_1_ = 0; lvt_2_1_ < 4; ++lvt_2_1_) {
            String lvt_3_1_ = p_145839_1_.getString("Text" + (lvt_2_1_ + 1));
            ITextComponent lvt_4_1_ = ITextComponent.Serializer.fromJson(lvt_3_1_.isEmpty() ? "\"\"" : lvt_3_1_);
            if (this.world instanceof ServerWorld) {
                try {
                    this.signText[lvt_2_1_] = TextComponentUtils.updateForEntity(this.getCommandSource((ServerPlayerEntity)null), lvt_4_1_, (Entity)null, 0);
                } catch (CommandSyntaxException var6) {
                    this.signText[lvt_2_1_] = lvt_4_1_;
                }
            } else {
                this.signText[lvt_2_1_] = lvt_4_1_;
            }

            this.renderText[lvt_2_1_] = null;
        }

    }

    @OnlyIn(Dist.CLIENT)
    public ITextComponent getText(int p_212366_1_) {
        return this.signText[p_212366_1_];
    }

    public void setText(int p_212365_1_, ITextComponent p_212365_2_) {
        this.signText[p_212365_1_] = p_212365_2_;
        this.renderText[p_212365_1_] = null;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public String getRenderText(int p_212364_1_, Function<ITextComponent, String> p_212364_2_) {
        if (this.renderText[p_212364_1_] == null && this.signText[p_212364_1_] != null) {
            this.renderText[p_212364_1_] = (String)p_212364_2_.apply(this.signText[p_212364_1_]);
        }

        return this.renderText[p_212364_1_];
    }

    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 9, this.getUpdateTag());
    }

    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public boolean onlyOpsCanSetNbt() {
        return true;
    }

    public boolean getIsEditable() {
        return this.isEditable;
    }

    @OnlyIn(Dist.CLIENT)
    public void setEditable(boolean p_145913_1_) {
        this.isEditable = p_145913_1_;
        if (!p_145913_1_) {
            this.player = null;
        }

    }

    public void setPlayer(PlayerEntity p_145912_1_) {
        this.player = p_145912_1_;
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }

    public boolean executeCommand(PlayerEntity p_174882_1_) {
        ITextComponent[] var2 = this.signText;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            ITextComponent lvt_5_1_ = var2[var4];
            Style lvt_6_1_ = lvt_5_1_ == null ? null : lvt_5_1_.getStyle();
            if (lvt_6_1_ != null && lvt_6_1_.getClickEvent() != null) {
                ClickEvent lvt_7_1_ = lvt_6_1_.getClickEvent();
                if (lvt_7_1_.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    p_174882_1_.getServer().getCommandManager().handleCommand(this.getCommandSource((ServerPlayerEntity)p_174882_1_), lvt_7_1_.getValue());
                }
            }
        }

        return true;
    }

    public CommandSource getCommandSource(@Nullable ServerPlayerEntity p_195539_1_) {
        String lvt_2_1_ = p_195539_1_ == null ? "Sign" : p_195539_1_.getName().getString();
        ITextComponent lvt_3_1_ = p_195539_1_ == null ? new StringTextComponent("Sign") : p_195539_1_.getDisplayName();
        return new CommandSource(ICommandSource.field_213139_a_, new Vec3d((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D), Vec2f.ZERO, (ServerWorld)this.world, 2, lvt_2_1_, (ITextComponent)lvt_3_1_, this.world.getServer(), p_195539_1_);
    }

    public DyeColor getTextColor() {
        return this.textColor;
    }

    public boolean setTextColor(DyeColor p_214068_1_) {
        if (p_214068_1_ != this.getTextColor()) {
            this.textColor = p_214068_1_;
            this.markDirty();
            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
            return true;
        } else {
            return false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void func_214062_a(int p_214062_1_, int p_214062_2_, int p_214062_3_, boolean p_214062_4_) {
        this.lineBeingEdited = p_214062_1_;
        this.field_214071_g = p_214062_2_;
        this.field_214072_h = p_214062_3_;
        this.field_214070_b = p_214062_4_;
    }

    @OnlyIn(Dist.CLIENT)
    public void func_214063_g() {
        this.lineBeingEdited = -1;
        this.field_214071_g = -1;
        this.field_214072_h = -1;
        this.field_214070_b = false;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean func_214069_r() {
        return this.field_214070_b;
    }

    @OnlyIn(Dist.CLIENT)
    public int getLineBeingEdited() {
        return this.lineBeingEdited;
    }

    @OnlyIn(Dist.CLIENT)
    public int func_214065_t() {
        return this.field_214071_g;
    }

    @OnlyIn(Dist.CLIENT)
    public int func_214067_u() {
        return this.field_214072_h;
    }
}