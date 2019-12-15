package com.vulp.druidcraft.items;

import com.google.common.collect.Maps;
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
import net.minecraft.item.Items;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Objects;

public class LunarMothJarItem extends Item {
    private static Map<LunarMothColors, LunarMothJarItem> map = Maps.newEnumMap(LunarMothColors.class);

    public LunarMothJarItem(Properties properties, LunarMothColors color) {
        super(properties);
        map.put(color, this);
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

            PlayerEntity player = context.getPlayer();

            if (EntityRegistry.lunar_moth_entity.spawn(world, itemstack, player, blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                itemstack.shrink(1);
                ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
                if (!player.inventory.addItemStackToInventory(bottle)) {
                    player.dropItem(bottle, false);
                }
            }

            return ActionResultType.SUCCESS;
        }
    }

    public static LunarMothJarItem getItemByColor (LunarMothColors color) {
        return map.get(color);
    }

    public static ItemStack getItemStackByColor (LunarMothColors color) {
        return new ItemStack(getItemByColor(color));
    }
}
