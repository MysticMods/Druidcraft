package com.vulp.druidcraft.items;

import com.vulp.druidcraft.api.IKnifeable;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class KnifeItem extends Item {

    public KnifeItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResultType.PASS;

        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof IKnifeable) {
            ActionResultType result = ((IKnifeable) state.getBlock()).onKnife(context);
            if (result != ActionResultType.PASS) {
                return result;
            }
        }

        if (player.isSneaking() && state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            BlockState state1 = cycleProperty(state, BlockStateProperties.HORIZONTAL_FACING);
            world.setBlockState(pos, state1, 18);
            return ActionResultType.SUCCESS;
        }

        return super.onItemUse(context);
    }

    private static <T extends Comparable<T>> BlockState cycleProperty(BlockState state, Property<T> propertyIn) {
        return state.with(propertyIn, getAdjacentValue(propertyIn.getAllowedValues(), state.get(propertyIn)));
    }

    private static <T> T getAdjacentValue(Iterable<T> p_195959_0_, @Nullable T p_195959_1_) {
        return Util.getElementAfter(p_195959_0_, p_195959_1_);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (worldIn == null) return;
        if (!Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("item.druidcraft.hold_shift").setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true)));
        } else {
            tooltip.add(new TranslationTextComponent("item.druidcraft.knife.description1").setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true)));
        }
    }
}
