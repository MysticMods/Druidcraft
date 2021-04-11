package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FieryGlassGlowEntity extends BlockInhabitingEntity {

    public FieryGlassGlowEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
        toggleBlockLight(this.world, getPosition(), true);
    }

    @Override
    public void onKillCommand() {
        toggleBlockLight(this.world, getPosition(), false);
        super.onKillCommand();
    }

    @Override
    public void remove() {
        toggleBlockLight(this.world, getPosition(), false);
        super.remove();
    }

    public void toggleBlockLight(World world, BlockPos pos, boolean flag) {
        /*BlockState state = world.getBlockState(pos);
        if (flag) {
            state.lightLevel = 15;
            AbstractBlock.Properties.from(state.getBlock()).setLightLevel((light) -> 15);
            world.getLightManager().checkBlock(pos);
            // world.getPendingBlockTicks().scheduleTick(pos, state.getBlock(), 1);
        } else {
            state.lightLevel = Block.Properties.from(state.getBlock()).lightLevel.applyAsInt(state);
            world.getLightManager().checkBlock(pos);
            // world.getPendingBlockTicks().scheduleTick(pos, state.getBlock(), 1);
        }
        Druidcraft.LOGGER.debug("LIGHT UPDATED!");*/
        world.getPendingBlockTicks().scheduleTick(pos, world.getBlockState(pos).getBlock(), 1);
    }

}
