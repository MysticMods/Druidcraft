package com.vulp.druidcraft.pathfinding;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ImprovedFlyingPathNavigator extends FlyingPathNavigator {

    public ImprovedFlyingPathNavigator(MobEntity entityIn, World worldIn) {
        super(entityIn, worldIn);
    }

    @Override
    public boolean canEntityStandOnPos(@Nullable BlockPos pos) {
        if (pos == null) {
            return false;
        }
        return super.canEntityStandOnPos(pos);
    }
}
