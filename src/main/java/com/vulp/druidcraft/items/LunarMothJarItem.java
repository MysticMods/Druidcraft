package com.vulp.druidcraft.items;

import com.vulp.druidcraft.entities.LunarMothColors;
import com.vulp.druidcraft.entities.LunarMothEntity;
import com.vulp.druidcraft.registry.EntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.spawner.AbstractSpawner;

import java.util.Objects;

public class LunarMothJarItem extends Item {

    public final LunarMothColors MOTH_COLOR;

    public LunarMothJarItem(Properties properties, LunarMothColors color) {
        super(properties);
        this.MOTH_COLOR = color;
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemstack = context.getItem();
            BlockPos blockpos = context.getPos();
            Direction direction = context.getFace();
            BlockState blockstate = world.getBlockState(blockpos);
            BlockPos blockpos1;

            if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.offset(direction);
            }

            if (EntityRegistry.lunar_moth_entity.spawn(world, itemstack, context.getPlayer(), blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                mothJarToBottle(itemstack, context.getPlayer(), itemstack);
            }

            return ActionResultType.SUCCESS;
        }
    }

    protected ItemStack mothJarToBottle(ItemStack itemstack, PlayerEntity player, ItemStack stack) {
        itemstack.shrink(1);
        if (itemstack.isEmpty()) {
            return stack;
        } else {
            if (!player.inventory.addItemStackToInventory(stack)) {
                player.dropItem(stack, false);
            }

            return itemstack;
        }
    }

}
