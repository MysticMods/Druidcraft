package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.EntityRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CustomBoatEntity extends BoatEntity {

    private static final DataParameter<Integer> CUSTOM_BOAT_TYPE = EntityDataManager.createKey(CustomBoatEntity.class, DataSerializers.VARINT);

    public CustomBoatEntity(EntityType<? extends CustomBoatEntity> type, World world) {
        super(type, world);
    }

    public CustomBoatEntity(World worldIn, double x, double y, double z) {
        this(EntityRegistry.boat_entity, worldIn);
        this.setPosition(x, y, z);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(CUSTOM_BOAT_TYPE, CustomType.DARKWOOD.ordinal());
    }

    protected void readAdditional(CompoundNBT compound) {
        if (compound.contains("Type", 8)) {
            this.setBoatType(CustomBoatEntity.CustomType.getTypeFromString(compound.getString("Type")));
        }

    }

    public void setBoatType(CustomBoatEntity.CustomType boatType) {
        this.dataManager.set(CUSTOM_BOAT_TYPE, boatType.ordinal());
    }

    public Item getItemBoat() {
        switch(this.getCustomBoatType()) {
            case DARKWOOD:
            default:
                return ItemRegistry.darkwood_boat;
            case ELDER:
                return ItemRegistry.elder_boat;
        }
    }

    protected void writeAdditional(CompoundNBT compound) {
        compound.putString("Type", this.getCustomBoatType().getName());
    }

    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        this.lastYd = this.getMotion().y;
        if (!this.isPassenger()) {
            if (onGroundIn) {
                if (this.fallDistance > 3.0F) {
                    if (this.status != CustomBoatEntity.Status.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.onLivingFall(this.fallDistance, 1.0F);
                    if (!this.world.isRemote && !this.removed) {
                        this.remove();
                        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            for(int i = 0; i < 3; ++i) {
                                this.entityDropItem(this.getCustomBoatType().asPlank());
                            }

                            for(int j = 0; j < 2; ++j) {
                                this.entityDropItem(Items.STICK);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            } else if (!this.world.getFluidState(this.getPosition().down()).isTagged(FluidTags.WATER) && y < 0.0D) {
                this.fallDistance = (float)((double)this.fallDistance - y);
            }

        }
    }

    public CustomBoatEntity.CustomType getCustomBoatType() {
        return CustomBoatEntity.CustomType.byId(this.dataManager.get(CUSTOM_BOAT_TYPE));
    }

    public static enum CustomType {
        DARKWOOD(BlockRegistry.darkwood_planks, "darkwood"),
        ELDER(BlockRegistry.elder_planks, "elder");

        private final String name;
        private final Block block;

        private CustomType(Block block, String name) {
            this.name = name;
            this.block = block;
        }

        public String getName() {
            return this.name;
        }

        public Block asPlank() {
            return this.block;
        }

        public String toString() {
            return this.name;
        }

        /**
         * Get a boat type by it's enum ordinal
         */
        public static CustomBoatEntity.CustomType byId(int id) {
            CustomBoatEntity.CustomType[] aboatentity$type = values();
            if (id < 0 || id >= aboatentity$type.length) {
                id = 0;
            }

            return aboatentity$type[id];
        }

        public static CustomBoatEntity.CustomType getTypeFromString(String nameIn) {
            CustomBoatEntity.CustomType[] aboatentity$type = values();

            for(int i = 0; i < aboatentity$type.length; ++i) {
                if (aboatentity$type[i].getName().equals(nameIn)) {
                    return aboatentity$type[i];
                }
            }

            return aboatentity$type[0];
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
