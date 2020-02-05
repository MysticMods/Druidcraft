package com.vulp.druidcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WoodBlock extends RotatedPillarBlock {

    public static BooleanProperty dropSelf = BooleanProperty.create("drop_self");
    private final Supplier<Item> item;

    public WoodBlock(Supplier<Item> logItem, Properties properties) {
        super(properties);
        this.item = logItem;
        this.setDefaultState(this.getDefaultState().with(dropSelf, true).with(AXIS, Direction.Axis.Y));
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        player.addStat(Stats.BLOCK_MINED.get(this));
        player.addExhaustion(0.005F);
        if (!state.get(dropSelf)) {
            spawnAsEntity(worldIn, pos, new ItemStack(this.item.get()));
        }
        else spawnDrops(state, worldIn, pos, te, player, stack);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS, dropSelf);
    }
}
