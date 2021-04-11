package com.vulp.druidcraft.items;

import com.vulp.druidcraft.blocks.tileentities.BlocklessTileEntityType;
import com.vulp.druidcraft.entities.BlockInhabitingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BlockEntityPlacer extends SmeltableItem {

    private Class<? extends BlockInhabitingEntity> entity;
    private EntityType<? extends BlockInhabitingEntity> type;
    private TileEntity tile;

    public BlockEntityPlacer(Class<? extends BlockInhabitingEntity> entity, EntityType<? extends BlockInhabitingEntity> type, Properties properties) {
        super(properties);
        this.entity = entity;
        this.type = type;
    }

    public BlockEntityPlacer(Class<? extends BlockInhabitingEntity> entity, EntityType<? extends BlockInhabitingEntity> type, TileEntity tile, Properties properties) {
        super(properties);
        this.entity = entity;
        this.tile = tile;
        this.type = type;
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        if (!(world instanceof ServerWorld)) {
            if (world.getEntitiesWithinAABB(this.entity, new AxisAlignedBB(blockpos.add(1.0D, 1.0D, 1.0D), blockpos)).size() == 0) {
                return ActionResultType.SUCCESS;
            } else return super.onItemUse(context);
        } else {
            ItemStack itemstack = context.getItem();
            if (world.getEntitiesWithinAABB(this.entity, new AxisAlignedBB(blockpos.add(1.0D, 1.0D, 1.0D), blockpos)).size() == 0 && this.type.spawn((ServerWorld) world, itemstack, context.getPlayer(), blockpos.add(0.5D, 0.0D, 0.5D), SpawnReason.SPAWN_EGG, false, true) != null) {
                if (tile != null) {
                    world.setTileEntity(blockpos, tile);
                }
                itemstack.shrink(1);
                return ActionResultType.CONSUME;
            }
            return super.onItemUse(context);
        }
    }

}
