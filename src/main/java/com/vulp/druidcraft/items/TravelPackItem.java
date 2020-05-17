package com.vulp.druidcraft.items;

import com.vulp.druidcraft.inventory.TravelPackInventory;
import com.vulp.druidcraft.inventory.container.TravelPackContainer;
import com.vulp.druidcraft.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

public class TravelPackItem extends Item {

    public TravelPackItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        if (!worldIn.isRemote && playerIn instanceof ServerPlayerEntity) {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return itemstack.getDisplayName();
                }

                @Nullable
                @Override
                public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    return new TravelPackContainer(i, playerInventory, new TravelPackInventory(itemstack), itemstack);
                }
            });
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int slot, boolean isHeldItem) {
        if (isHeldItem) {
            TravelPackInventory travelPackInventory = new TravelPackInventory(itemStack);
            ItemStack bedroll = travelPackInventory.getStackInSlot(0);
            int colorID = bedroll.getItem() instanceof BedrollItem ? ((BedrollItem) bedroll.getItem()).getColor().getId() : -1;
            CompoundNBT nbt = itemStack.getOrCreateTag();
            nbt.putInt("color", colorID);
        }
    }

    @Nullable
    public static DyeColor getColor(CompoundNBT nbt) {
        int colorID = nbt.getInt("color");
        if (colorID != -1) {
            return DyeColor.byId(nbt.getInt("color"));
        }
        return null;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, PlayerEntity player) {
        return !(player.openContainer instanceof TravelPackContainer);
    }

}
