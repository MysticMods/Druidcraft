package com.vulp.druidcraft.items;

import com.vulp.druidcraft.inventory.TravelPackInventory;
import com.vulp.druidcraft.inventory.container.TravelPackContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
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

    @Nullable
    public static DyeColor getBedrollColor(ItemStack bedrollItem) {
        CompoundNBT tags = bedrollItem.getTag();
        if (tags != null) {
            if (tags.contains("color")) {
                return DyeColor.byId(tags.getInt("color"));
            }
        }
        return null;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, PlayerEntity player) {
        return !(player.openContainer instanceof TravelPackContainer);
    }

}
