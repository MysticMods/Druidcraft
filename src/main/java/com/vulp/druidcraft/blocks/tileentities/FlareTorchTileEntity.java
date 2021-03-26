package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.FlareTorchBlock;
import com.vulp.druidcraft.blocks.WallFlareTorchBlock;
import com.vulp.druidcraft.network.PacketHandler;
import com.vulp.druidcraft.network.message.BlockProtectionVisualMessage;
import com.vulp.druidcraft.network.message.FlareTorchParticleMessage;
import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class FlareTorchTileEntity extends TileEntity implements ITickableTileEntity {

    private boolean particleSpawned = false;

    public FlareTorchTileEntity() {
        this(TileEntityRegistry.flare_torch);
    }

    public FlareTorchTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.particleSpawned = false;
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        return super.write(nbt);
    }

    @OnlyIn(Dist.CLIENT)
    public void doParticles(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof WallFlareTorchBlock) {
            WallFlareTorchBlock.spawnParticles(world, pos);
        } else if (block instanceof FlareTorchBlock) {
            FlareTorchBlock.spawnParticles(world, pos);
        }
    }

    @Override
    public void tick() {
        if (!this.particleSpawned) {
            World world = this.getWorld();
            if (world != null) {
                doParticles(world, this.getPos());
                this.particleSpawned = true;
            }
        }
    }

}