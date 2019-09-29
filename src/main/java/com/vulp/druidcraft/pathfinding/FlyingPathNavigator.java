package com.vulp.druidcraft.pathfinding;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlyingNodeProcessor;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlyingPathNavigator extends SwimmerPathNavigator {

    public FlyingPathNavigator(MobEntity entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    @Override
    protected PathFinder getPathFinder(int p_179679_1_) {
        this.nodeProcessor = new FlyingNodeProcessor();
        return new PathFinder(this.nodeProcessor, p_179679_1_);
    }

    @Override
    protected boolean canNavigate() {
        return true;
    }


    @Override
    protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
        Vec3d vec3d = new Vec3d(posVec32.x, posVec32.y + (double)this.entity.getHeight() * 0.5D, posVec32.z);
        return this.world.rayTraceBlocks(new RayTraceContext(posVec31, vec3d, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, this.entity)).getType() == RayTraceResult.Type.MISS;
    }

    public void setCanSwim(boolean canSwim) {
    }
}
