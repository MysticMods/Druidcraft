package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.FluidCraftingTableContainer;
import com.vulp.druidcraft.network.PacketHandler;
import com.vulp.druidcraft.network.message.FluidCraftingIDMessage;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

public class FluidCraftingTableTileEntity extends TileEntity implements INamedContainerProvider {

    private final IntReferenceHolder fluidID = new IntReferenceHolder() {

        private int fluid;

        @Override
        public int get() {
            return this.fluid;
        }

        @Override
        public void set(int i) {
            onUpdate(i);
            this.fluid = i;
        }

        private void onUpdate(int i) {
            updateClient(i);
        }

    };

    public FluidCraftingTableTileEntity() {
        this(TileEntityRegistry.fluid_crafting_table);
    }

    public FluidCraftingTableTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
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
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.fluidID.set(nbt.getInt("FluidID"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("FluidID", this.fluidID.get());
        return compound;
    }

    public void setFluid(@Nullable Fluid fluid) {
        this.fluidID.set(Registry.FLUID.getId(fluid));
    }

    public void setFluidID(int fluidID) {
        this.fluidID.set(fluidID);
    }

    public Fluid getFluid() {
        return Registry.FLUID.getByValue(this.fluidID.get());
    }

    private void updateClient(int i) {
        if (this.world != null) {
            if (!this.world.isRemote()) {
                PacketHandler.instance.send(PacketDistributor.TRACKING_CHUNK.with(() -> (Chunk) this.world.getChunk(this.pos)), new FluidCraftingIDMessage(i, this.pos));
            }
            this.world.getLightManager().checkBlock(this.pos);
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container." + Druidcraft.MODID + ".fluid_crafting_table");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FluidCraftingTableContainer(id, playerInventory, this.fluidID, IWorldPosCallable.of(this.world, this.pos));
    }
}
