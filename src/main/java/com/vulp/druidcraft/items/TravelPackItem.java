package com.vulp.druidcraft.items;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.api.BedrollDyeColorIndex;
import com.vulp.druidcraft.blocks.BedrollBlock;
import com.vulp.druidcraft.entities.TameableMonsterEntity;
import com.vulp.druidcraft.inventory.TravelPackInventory;
import com.vulp.druidcraft.inventory.container.TravelPackContainer;
import com.vulp.druidcraft.network.PacketHandler;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.lang.reflect.Proxy;
import java.util.List;

public class TravelPackItem extends Item {

    public TravelPackItem(Properties properties) {
        super(properties);
    }

    @Override
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


    // FIX BED!
    public static void deployBedroll(int entityID, @Nullable World world) {
        if(world != null) {
            Entity entity = world.getEntityByID(entityID);
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                ItemStack itemStack = player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof TravelPackItem ? player.getHeldItem(Hand.MAIN_HAND) : player.getHeldItem(Hand.OFF_HAND);
                CompoundNBT nbt = itemStack.getTag();
                TravelPackInventory inventory = new TravelPackInventory(itemStack);
                BlockPos pos = player.getPosition();
                Direction direction = player.getHorizontalFacing();
                    if (!player.isCreative()) {
                        List<MonsterEntity> list = world.getEntitiesWithinAABB(MonsterEntity.class, new AxisAlignedBB((double) pos.getX() - 10.0D, (double) pos.getY() - 6.0D, (double) pos.getZ() - 10.0D, (double) pos.getX() + 10.0D, (double) pos.getY() + 6.0D, (double) pos.getZ() + 10.0D), (monsterEntity) -> monsterEntity.func_230292_f_(player));
                        List<TameableMonsterEntity> list2 = world.getEntitiesWithinAABB(TameableMonsterEntity.class, new AxisAlignedBB((double) pos.getX() - 10.0D, (double) pos.getY() - 6.0D, (double) pos.getZ() - 10.0D, (double) pos.getX() + 10.0D, (double) pos.getY() + 6.0D, (double) pos.getZ() + 10.0D), (tameableMonsterEntity) -> tameableMonsterEntity.preventPlayerSleep(player));
                        if (!list.isEmpty() || !list2.isEmpty()) {
                            player.sendStatusMessage(new TranslationTextComponent("block.druidcraft.bedroll.fail.monsters"), true);
                            return;
                        }
                    }
                    if (!world.getDimensionType().doesBedWork()) {
                        player.sendStatusMessage(new TranslationTextComponent("block.druidcraft.bedroll.fail.dimension"), true);
                        return;
                    }
                    if (!world.isRemote && !net.minecraftforge.event.ForgeEventFactory.fireSleepingTimeCheck(player, java.util.Optional.of(pos))) {
                        player.sendStatusMessage(new TranslationTextComponent("block.druidcraft.bedroll.fail.time"), true);
                        return;
                    }
                    Druidcraft.LOGGER.debug("Deploying!");
                    for (Direction dir : Direction.values()) {
                        if (dir != Direction.UP && dir != Direction.DOWN && dir != direction) {
                            BlockPos bedrollPos = bedrollPlacementHelper(world, pos, direction, DyeColor.byId(nbt.getInt("Color")));
                            if (bedrollPos != null) {
                                nbt.putInt("BedrollPosX", bedrollPos.getX());
                                nbt.putInt("BedrollPosY", bedrollPos.getY());
                                nbt.putInt("BedrollPosZ", bedrollPos.getZ());
                                inventory.removeStackFromSlot(0);
                                inventory.writeItemStack();
                                nbt.putInt("Color", -1);
                                player.trySleep(bedrollPos).ifLeft((p_220173_1_) -> {
                                    if (p_220173_1_ != null) {
                                        player.sendStatusMessage(p_220173_1_.getMessage(), true);
                                    }

                                });
                                return;
                            }
                        }
                    }
                    for (Direction dir : Direction.values()) {
                        if (dir != Direction.UP && dir != Direction.DOWN && dir != direction) {
                            for (int i = -1; i < 2; i++) {
                                for (int j = -1; j < 2; j++) {
                                    for (int k = -1; k < 2; k++) {
                                        BlockPos bedrollPos = bedrollPlacementHelper(world, pos, direction, DyeColor.byId(nbt.getInt("Color")));
                                        if (bedrollPos != null) {
                                            nbt.putInt("BedrollPosX", bedrollPos.getX());
                                            nbt.putInt("BedrollPosY", bedrollPos.getY());
                                            nbt.putInt("BedrollPosZ", bedrollPos.getZ());
                                            inventory.removeStackFromSlot(0);
                                            inventory.writeItemStack();
                                            nbt.putInt("Color", -1);
                                            player.trySleep(bedrollPos).ifLeft((p_220173_1_) -> {
                                                if (p_220173_1_ != null) {
                                                    player.sendStatusMessage(p_220173_1_.getMessage(), true);
                                                }

                                            });
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    player.sendStatusMessage(new TranslationTextComponent("block.druidcraft.bedroll.fail.placement"), true);
                }
        }
    }

    public static void bedrollSleepHelper(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        ((BedrollBlock)state.getBlock()).onBlockActivated(state, worldIn, pos, player, Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(0.0d, 0.0d, 0.0d), Direction.NORTH, pos, true));
    }

    @Nullable
    public static BlockPos bedrollPlacementHelper(World world, BlockPos pos, Direction direction, DyeColor color) {
        BlockPos foot = pos;
        BlockPos head = pos.offset(direction);
        if (world.getBlockState(head).getMaterial().isReplaceable() && world.getBlockState(foot).getMaterial().isReplaceable()) {
            world.setBlockState(foot, BedrollDyeColorIndex.byIndex(color.getId()).getBedrollBlock().getDefaultState().with(BedrollBlock.HORIZONTAL_FACING, direction).with(BedrollBlock.PART, BedPart.FOOT).with(BedrollBlock.WATERLOGGED, world.getBlockState(foot).getBlock() == Blocks.WATER));
            world.setBlockState(head, BedrollDyeColorIndex.byIndex(color.getId()).getBedrollBlock().getDefaultState().with(BedrollBlock.HORIZONTAL_FACING, direction).with(BedrollBlock.PART, BedPart.HEAD).with(BedrollBlock.WATERLOGGED, world.getBlockState(head).getBlock() == Blocks.WATER));
            return head;
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    foot = pos.add(i, j, k);
                    head = pos.add(i, j, k).offset(direction);
                    if (world.getBlockState(head).getMaterial().isReplaceable() && world.getBlockState(foot).getMaterial().isReplaceable()) {
                        world.setBlockState(foot, BedrollDyeColorIndex.byIndex(color.getId()).getBedrollBlock().getDefaultState().with(BedrollBlock.HORIZONTAL_FACING, direction).with(BedrollBlock.PART, BedPart.FOOT).with(BedrollBlock.WATERLOGGED, world.getBlockState(foot).getBlock() == Blocks.WATER));
                        world.setBlockState(head, BedrollDyeColorIndex.byIndex(color.getId()).getBedrollBlock().getDefaultState().with(BedrollBlock.HORIZONTAL_FACING, direction).with(BedrollBlock.PART, BedPart.HEAD).with(BedrollBlock.WATERLOGGED, world.getBlockState(head).getBlock() == Blocks.WATER));
                        return head;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int slot, boolean isHeldItem) {
        if (isHeldItem) {
            TravelPackInventory travelPackInventory = new TravelPackInventory(itemStack);
            ItemStack bedroll = travelPackInventory.getStackInSlot(0);
            int colorID = bedroll.getItem() instanceof BedrollItem ? ((BedrollItem) bedroll.getItem()).getColor().getId() : -1;
            CompoundNBT nbt = itemStack.getOrCreateTag();
            nbt.putInt("Color", colorID);
        }
    }

    @Nullable
    public static DyeColor getColor(CompoundNBT nbt) {
        int colorID = nbt.getInt("Color");
        if (colorID != -1) {
            return DyeColor.byId(nbt.getInt("Color"));
        }
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (worldIn == null) return;
        if (!Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("item.druidcraft.hold_shift").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
        } else {
            tooltip.add(new TranslationTextComponent("item.druidcraft.travel_pack.description1").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
        }
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, PlayerEntity player) {
        return !(player.openContainer instanceof TravelPackContainer);
    }

}
