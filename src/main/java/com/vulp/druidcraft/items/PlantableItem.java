package com.vulp.druidcraft.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class PlantableItem extends Item implements IPlantable
{
    private final PlantType plantType;
    private Block crop;

    public PlantableItem(Properties properties, PlantType plantType, Block crop) {
        super(properties);
        this.plantType = plantType;
        this.crop = crop;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
    Direction direction = context.getFace();
    PlayerEntity player = context.getPlayer();
    BlockPos pos = context.getPos();
    ItemStack stack = context.getPlayer().getHeldItem(context.getHand());
    BlockState state = context.getWorld().getBlockState(context.getPos());

    if (direction == Direction.UP && player.canPlayerEdit(pos.offset(direction), direction, stack) && context.getWorld().isAirBlock(pos.up()) && (state.getBlock().canSustainPlant(state, context.getWorld(), pos, Direction.UP, this)))
		{
		    context.getWorld().setBlockState(pos.up(), crop.getDefaultState());

		    if (player instanceof ServerPlayerEntity) {
		        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos.up(), stack);
		    }

		    if (!player.isCreative())
		        stack.shrink(1);
		    return ActionResultType.SUCCESS;
		} else {
		    return ActionResultType.FAIL;
		}
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, PlayerEntity player, LivingEntity entity, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!entity.world.isRemote && !entity.isChild() && (int) ((AgeableEntity) entity).getGrowingAge() == 0) {
            if (entity instanceof ChickenEntity) {
                if (((ChickenEntity) entity).isInLove()) {
                    return false;
                } else {
                    ((ChickenEntity) entity).setInLove(player);
                    if (!player.isCreative())
                        stack.shrink(1);
                    return true;
                }
            }

            if (entity instanceof ParrotEntity)
                if (!entity.world.isRemote) {
                    if (!((ParrotEntity) entity).isTamed())
                        if (Math.random() < 0.33) {
                            ((ParrotEntity) entity).setTamedBy(player);
                            ((ParrotEntity) entity).setInLove(player);
                        }
                    if (!player.isCreative())
                        stack.shrink(1);
                }
        }

        if (entity.isChild()) {
            if (!player.isCreative())
                stack.shrink(1);
            ((AgeableEntity) entity).ageUp((int) ((float) (-((AgeableEntity) entity).getGrowingAge() / 20) * 0.1F),
                    true);
            return true;
        }
        return false;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos)
    {
        return this.plantType;
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos)
    {
        return crop.getDefaultState();
    }
}