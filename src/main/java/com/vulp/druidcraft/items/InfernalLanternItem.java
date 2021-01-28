package com.vulp.druidcraft.items;

import com.vulp.druidcraft.util.MathUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class InfernalLanternItem extends BlockItem {

    private final Block block;

    public InfernalLanternItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
        this.block = blockIn;
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();
        ItemStack itemstack = player.getHeldItem(hand);
        CompoundNBT nbt = itemstack.getOrCreateTag();
        int fuel = nbt.getInt("fuel");
        if (fuel > 0) {
            ActionResultType actionresulttype = this.tryPlace(new BlockItemUseContext(context));
            return !actionresulttype.isSuccessOrConsume() ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype;
        }
        player.sendStatusMessage(new TranslationTextComponent("item.druidcraft.infernal_lantern.fail"), true);
        return ActionResultType.FAIL;
    }

    public ActionResultType tryPlace(BlockItemUseContext context) {
        if (!context.canPlace()) {
            return ActionResultType.FAIL;
        } else {
            BlockItemUseContext blockitemusecontext = this.getBlockItemUseContext(context);
            if (blockitemusecontext == null) {
                return ActionResultType.FAIL;
            } else {
                BlockState blockstate = this.getStateForPlacement(blockitemusecontext);
                if (blockstate == null) {
                    return ActionResultType.FAIL;
                } else if (!this.placeBlock(blockitemusecontext, blockstate)) {
                    return ActionResultType.FAIL;
                } else {
                    BlockPos blockpos = blockitemusecontext.getPos();
                    World world = blockitemusecontext.getWorld();
                    PlayerEntity playerentity = blockitemusecontext.getPlayer();
                    ItemStack itemstack = blockitemusecontext.getItem();
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    Block block = blockstate1.getBlock();
                    if (block == blockstate.getBlock()) {
                        blockstate1 = this.func_219985_a(blockpos, world, itemstack, blockstate1);
                        this.onBlockPlaced(blockpos, world, playerentity, itemstack, blockstate1);
                        block.onBlockPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
                        if (playerentity instanceof ServerPlayerEntity) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos, itemstack);
                        }
                    }
                    SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
                    world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, context.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if (playerentity == null || !playerentity.abilities.isCreativeMode) {
                        CompoundNBT nbt = itemstack.getOrCreateTag();
                        int newFuel = nbt.getInt("fuel") - 1;
                        nbt.putInt("fuel", newFuel);
                        String formattedFuel = MathUtil.formatInteger(newFuel);
                        if (!formattedFuel.equals(MathUtil.formatInteger(nbt.getInt("fuel_old")))) {
                            playerentity.sendStatusMessage(ITextComponent.getTextComponentOrEmpty(new StringBuilder().append(new TranslationTextComponent("item.druidcraft.infernal_lantern.remainder")).append(formattedFuel).toString()), true);
                            nbt.putInt("fuel_old", newFuel);
                        }
                    }
                    return ActionResultType.func_233537_a_(world.isRemote);
                }
            }
        }
    }

    private BlockState func_219985_a(BlockPos p_219985_1_, World p_219985_2_, ItemStack p_219985_3_, BlockState p_219985_4_) {
        BlockState blockstate = p_219985_4_;
        CompoundNBT compoundnbt = p_219985_3_.getTag();
        if (compoundnbt != null) {
            CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
            StateContainer<Block, BlockState> statecontainer = p_219985_4_.getBlock().getStateContainer();

            for(String s : compoundnbt1.keySet()) {
                Property<?> property = statecontainer.getProperty(s);
                if (property != null) {
                    String s1 = compoundnbt1.get(s).getString();
                    blockstate = func_219988_a(blockstate, property, s1);
                }
            }
        }

        if (blockstate != p_219985_4_) {
            p_219985_2_.setBlockState(p_219985_1_, blockstate, 2);
        }

        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState func_219988_a(BlockState p_219988_0_, Property<T> p_219988_1_, String p_219988_2_) {
        return p_219988_1_.parseValue(p_219988_2_).map((p_219986_2_) -> {
            return p_219988_0_.with(p_219988_1_, p_219986_2_);
        }).orElse(p_219988_0_);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (worldIn == null) return;
        if (!Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("item.druidcraft.hold_shift").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
            tooltip.add(new TranslationTextComponent("item.druidcraft.infernal_lantern.description2").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
        } else {
            tooltip.add(new TranslationTextComponent("item.druidcraft.infernal_lantern.description1").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
            tooltip.add(new TranslationTextComponent("item.druidcraft.infernal_lantern.description2").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
        }
    }

}
