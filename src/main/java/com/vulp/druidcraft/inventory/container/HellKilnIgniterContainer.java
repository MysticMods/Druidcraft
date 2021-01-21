package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.registry.GUIRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HellKilnIgniterContainer extends Container {

    private final IInventory igniterInventory;
    protected final World world;
    private final IIntArray hellkiln_igniter_data;

    public HellKilnIgniterContainer(int id, PlayerInventory playerInventory) {
        this(GUIRegistry.hellkiln_igniter, id, playerInventory, new Inventory(1), new IntArray(1));
    }

    public HellKilnIgniterContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, IInventory inventory, IIntArray hellkilnIgniterData) {
        super(type, id);
        assertIntArraySize(hellkilnIgniterData, 1);
        this.igniterInventory = inventory;
        this.world = playerInventory.player.world;
        this.hellkiln_igniter_data = hellkilnIgniterData;
        this.addSlot(new HellkilnIgniterFuelSlot(this, inventory, 0, 80, 30));
        this.trackIntArray(hellkilnIgniterData);
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getFuelLevel() {
        return this.hellkiln_igniter_data.get(0);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.igniterInventory.isUsableByPlayer(playerIn);
    }
}
