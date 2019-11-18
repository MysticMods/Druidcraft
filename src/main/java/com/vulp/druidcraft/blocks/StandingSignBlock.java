package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.tileentity.SignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class StandingSignBlock extends net.minecraft.block.StandingSignBlock {

    public StandingSignBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SignTileEntity();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return true;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof SignTileEntity) {
                SignTileEntity signtileentity = (SignTileEntity)tileentity;
                ItemStack itemstack = player.getHeldItem(handIn);
                if (itemstack.getItem() instanceof DyeItem && player.abilities.allowEdit) {
                    boolean flag = signtileentity.setTextColor(((DyeItem)itemstack.getItem()).getDyeColor());
                    if (flag && !player.isCreative()) {
                        itemstack.shrink(1);
                    }
                }

                return signtileentity.executeCommand(player);
            } else {
                return false;
            }
        }
    }
}
