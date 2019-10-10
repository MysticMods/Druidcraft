package com.vulp.druidcraft.entities;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public class PlayerOverride extends PlayerEntity {

    public PlayerOverride(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    public void openBeetleInventory(BeetleEntity beetle, IInventory inventoryIn) {
    }
}
