package com.vulp.druidcraft.items;

import com.google.common.collect.Maps;
import com.vulp.druidcraft.blocks.LunarMothJarBlock;
import com.vulp.druidcraft.entities.LunarMothColors;
import com.vulp.druidcraft.entities.LunarMothEntity;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.EntityRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Objects;

public class LunarMothJarItem extends BlockItem {
    private static Map<LunarMothColors, LunarMothJarItem> map = Maps.newEnumMap(LunarMothColors.class);
    private final LunarMothColors color;

    public LunarMothJarItem(Block block, LunarMothColors color, Properties properties) {
        super(block, properties);
        map.put(color, this);
        this.color = color;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack stack = new ItemStack(this);
            CompoundNBT tag = stack.getOrCreateTag();
            CompoundNBT entityData = new CompoundNBT();
            entityData.putInt("Color", LunarMothColors.colorToInt(color));
            tag.put("EntityData", entityData);
            items.add(stack);
        }
    }

    public LunarMothColors getColor() {
        return this.color;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer().isSneaking()) {
            ActionResultType actionresulttype1 = this.tryPlace(new BlockItemUseContext(context));
            return actionresulttype1 != ActionResultType.SUCCESS ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype1;
        } else {
            ActionResultType actionresulttype2 = this.tryRelease(new BlockItemUseContext(context));
            return actionresulttype2 != ActionResultType.SUCCESS ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype2;
        }
    }

    public ActionResultType tryRelease(ItemUseContext context) {
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

            LunarMothEntity moth = (LunarMothEntity) EntityRegistry.lunar_moth_entity.spawn(world, itemstack, player, blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
            if (moth != null) {
                if (itemstack.getTag() != null) {
                    CompoundNBT tag = itemstack.getTag();
                    if (tag.contains("EntityData")) {
                        moth.readAdditional(tag.getCompound("EntityData"));
                    }
                }
                itemstack.shrink(1);
                ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
                if (player != null) {
                    if (!player.inventory.addItemStackToInventory(bottle)) {
                        player.dropItem(bottle, false);
                    }
                } else {
                    InventoryHelper.spawnItemStack(world, blockpos1.getX() + 0.5, blockpos1.getY() + 0.5, blockpos1.getZ() + 0.5, bottle);
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
