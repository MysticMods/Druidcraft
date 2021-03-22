package com.vulp.druidcraft.items;

import com.vulp.druidcraft.entities.GaseousBombEntity;
import com.vulp.druidcraft.inventory.TravelPackInventory;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class GaseousBombItem extends Item {

    public GaseousBombItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        world.playSound((PlayerEntity)null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            GaseousBombEntity thrownEntity = new GaseousBombEntity(world, playerEntity);
            thrownEntity.setItem(new ItemStack(ItemRegistry.gaseous_bomb));
            thrownEntity.func_234612_a_(playerEntity, playerEntity.rotationPitch, playerEntity.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.addEntity(thrownEntity);
        }

        playerEntity.addStat(Stats.ITEM_USED.get(this));
        if (!playerEntity.abilities.isCreativeMode) {
            itemStack.shrink(1);
        }

        return ActionResult.func_233538_a_(itemStack, world.isRemote());
    }

}
