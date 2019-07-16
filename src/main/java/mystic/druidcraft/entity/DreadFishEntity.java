package mystic.druidcraft.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.world.World;

public class DreadFishEntity extends WaterMobEntity {

    public DreadFishEntity(EntityType<? extends WaterMobEntity> type, World world) {
        super(type, world);
    }
}
