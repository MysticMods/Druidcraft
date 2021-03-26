package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.network.PacketHandler;
import com.vulp.druidcraft.network.message.BlockProtectionVisualMessage;
import com.vulp.druidcraft.network.message.FlareTorchParticleMessage;
import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class FlareTorchTileEntity extends TileEntity implements ITickableTileEntity {

    private double particlePosX;
    private double particlePosY;
    private double particlePosZ;
    private boolean particleSpawned = false;

    public FlareTorchTileEntity() {
        this(TileEntityRegistry.flare_torch, null);
    }

    public FlareTorchTileEntity(@Nullable Direction direction) {
        this(TileEntityRegistry.flare_torch, direction);
    }

    public FlareTorchTileEntity(TileEntityType<?> tileEntityType, @Nullable Direction direction) {
        super(tileEntityType);
        if (direction != null) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + 0.65D;
            double d2 = (double) pos.getZ() + 0.5D;
            double d3 = 0.22D;
            double d4 = 0.27D;
            Direction direction1 = direction.getOpposite();
            this.particlePosX = d0 + d4 * (double) direction1.getXOffset();
            this.particlePosY = d1 + d3 - 0.1D;
            this.particlePosZ = d2 + d4 * (double) direction1.getZOffset();
        } else {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + 0.7D;
            double d2 = (double) pos.getZ() + 0.5D;
            this.particlePosX = d0;
            this.particlePosY = d1 - 0.1D;
            this.particlePosZ = d2;
        }
        if (!this.particleSpawned) {
            if (this.world != null) {
                sendParticle(this.particlePosX, this.particlePosY, this.particlePosZ);
                this.particleSpawned = true;
            }
        }
    }

    private void sendParticle(double x, double y, double z) {
        if (this.world != null) {
            Supplier<Chunk> chunkSupplier = () -> (Chunk) this.world.getChunk(this.pos);
            PacketHandler.instance.send(PacketDistributor.TRACKING_CHUNK.with(chunkSupplier), new FlareTorchParticleMessage(this.particlePosX, this.particlePosY, this.particlePosZ));
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.particlePosX = nbt.getDouble("PosX");
        this.particlePosY = nbt.getDouble("PosY");
        this.particlePosZ = nbt.getDouble("PosZ");
        if (!this.particleSpawned) {
            if (this.world != null) {
                sendParticle(this.particlePosX, this.particlePosY, this.particlePosZ);
                this.particleSpawned = true;
            }
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        nbt.putDouble("PosX", this.particlePosX);
        nbt.putDouble("PosY", this.particlePosY);
        nbt.putDouble("PosZ", this.particlePosZ);
        return nbt;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state, tag);
    }

    @Override
    public void tick() {
        Druidcraft.LOGGER.debug("TICKING!");
        /*if (!this.particleSpawned) {
            if (this.world != null) {
                sendParticle(this.particlePosX, this.particlePosY, this.particlePosZ);
                this.particleSpawned = true;
            }
        }*/
    }
}