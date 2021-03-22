package com.vulp.druidcraft.items;

import com.vulp.druidcraft.entities.DuragemProtectionEntity;
import com.vulp.druidcraft.registry.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;

import java.util.List;
import java.util.Objects;

public class CrushedDuragemItem extends Item {

    public CrushedDuragemItem(Properties properties) {
        super(properties);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        if (!(world instanceof ServerWorld)) {
            if (world.getEntitiesWithinAABB(DuragemProtectionEntity.class, new AxisAlignedBB(blockpos.add(1.0D, 1.0D, 1.0D), blockpos)).size() == 0) {
                return ActionResultType.SUCCESS;
            } else return super.onItemUse(context);
        } else {
            ItemStack itemstack = context.getItem();
            if (world.getEntitiesWithinAABB(DuragemProtectionEntity.class, new AxisAlignedBB(blockpos.add(1.0D, 1.0D, 1.0D), blockpos)).size() == 0 && EntityRegistry.duragem_protection_entity.spawn((ServerWorld) world, itemstack, context.getPlayer(), blockpos.add(0.5D, 0.0D, 0.5D), SpawnReason.SPAWN_EGG, false, true) != null) {
                itemstack.shrink(1);
                return ActionResultType.CONSUME;
            }
            return super.onItemUse(context);
        }
    }

}
