package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.WoodcutterContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.StonecutterContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class WoodcutterBlock extends StonecutterBlock {

    private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container." + Druidcraft.MODID + ".woodcutter");

    public WoodcutterBlock(Properties propertiesIn) {
        super(propertiesIn);
    }

    @Override
    @Nullable
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        return new SimpleNamedContainerProvider((p_220283_2_, p_220283_3_, p_220283_4_) -> {
            return new WoodcutterContainer(p_220283_2_, p_220283_3_, IWorldPosCallable.of(worldIn, pos));
        }, CONTAINER_NAME);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            player.openContainer(state.getContainer(worldIn, pos));
            // player.addStat(Stats.interact_with_woodcutter);
            return ActionResultType.SUCCESS;
        }
    }

}
