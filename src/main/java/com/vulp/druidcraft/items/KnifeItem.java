package com.vulp.druidcraft.items;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vulp.druidcraft.api.IKnifeable;
import com.vulp.druidcraft.api.SideType;
import com.vulp.druidcraft.client.renders.RenderTypeDictionary;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.util.ParticleUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class KnifeItem extends Item {

    public static final ImmutableSet<Block> KNIFEABLE_BLOCKS = ImmutableSet.of(BlockRegistry.rope, BlockRegistry.crate/*, Blocks.SMITHING_TABLE, BlockRegistry.smithing_workbench*/);

    public KnifeItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return ActionResultType.PASS;
        }

        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);

        // Whether or not the block is worth calculating.
        if (KNIFEABLE_BLOCKS.contains(state.getBlock())) {
            Vector3d vec3d = context.getHitVec().subtract(pos.getX(), pos.getY(), pos.getZ());
            Direction dir = getConfigDirection(vec3d);

            if (dir != null && dir.getAxis() != Direction.Axis.Y && state.getBlock() == Blocks.SMITHING_TABLE) {
                return fuseSmithingTables(world, pos, dir, player);
            }
            if (state.getBlock() == BlockRegistry.crate) {
                if (dir == null) {
                    world.setBlockState(pos, Blocks.GLOWSTONE.getDefaultState());
                } else {
                    world.setBlockState(pos.offset(dir), Blocks.GREEN_CONCRETE.getDefaultState());
                }
                return ActionResultType.SUCCESS;
            }

        }

        return super.onItemUseFirst(stack, context);
    }

    private static ActionResultType fuseSmithingTables(World world, BlockPos pos, Direction dir, PlayerEntity player) {
 /*       if (world.getBlockState(pos.offset(dir)).getBlock() == Blocks.SMITHING_TABLE) {
            Direction facingDir = getSmithingWorkbenchOrientation(player, dir.getAxis());
            world.setBlockState(pos, BlockRegistry.smithing_workbench.getDefaultState().with(SmithingWorkbenchBlock.FACING, facingDir).with(SmithingWorkbenchBlock.TYPE, facingDir.rotateYCCW() == dir ? SideType.LEFT : SideType.RIGHT));
            world.setBlockState(pos.offset(dir), BlockRegistry.smithing_workbench.getDefaultState().with(SmithingWorkbenchBlock.FACING, facingDir).with(SmithingWorkbenchBlock.TYPE, facingDir.rotateY() == dir.getOpposite() ? SideType.RIGHT : SideType.LEFT));
            if (world.isRemote) {
                Random rand = new Random();
                ParticleUtil.makeBlockParticles(new BlockParticleData(ParticleTypes.BLOCK, Blocks.SMITHING_TABLE.getDefaultState()), world, pos, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D);
                ParticleUtil.makeBlockParticles(new BlockParticleData(ParticleTypes.BLOCK, Blocks.SMITHING_TABLE.getDefaultState()), world, pos.offset(dir), rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D);
                world.playSound(player, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResultType.SUCCESS;
        }*/
        return ActionResultType.PASS;
    }

    public static Direction getSmithingWorkbenchOrientation(Entity entityIn, Direction.Axis longSideAxis) {
        float trueYaw = -entityIn.getYaw(1.0F) * ((float)Math.PI / 180F);
        Direction direction = MathHelper.sin(trueYaw) > 0.0F ? Direction.EAST : Direction.WEST;
        Direction direction2 = MathHelper.cos(trueYaw) > 0.0F ? Direction.SOUTH : Direction.NORTH;
        if (longSideAxis == Direction.Axis.Z) {
            return direction.getOpposite();
        } else {
            return direction2.getOpposite();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderHighlight(Minecraft mc, Vector3d vector3d, VoxelShape shape, BlockPos blockPos, PlayerEntity playerEntity, WorldRenderer worldRenderer, MatrixStack matrixStack) {
       ItemStack stack1 = playerEntity.getHeldItemMainhand();
        ItemStack stack2 = playerEntity.getHeldItemOffhand();
        if ((stack1.isEmpty() && stack2.isEmpty()) || (stack1.getItem() != ItemRegistry.knife && stack2.getItem() != ItemRegistry.knife)) {
            return;
        }
        double xView = vector3d.getX();
        double yView = vector3d.getY();
        double zView = vector3d.getZ();

        WorldRenderer.drawShape(matrixStack, Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().getBuffer(RenderTypeDictionary.KNIFE_LINES), shape, blockPos.getX() - xView, blockPos.getY() - yView, blockPos.getZ() - zView, 1.0f, 1.0f, 1.0f, 1.0F);
    }

    @Nullable
    public static Direction getConfigDirection(Vector3d relative) {
        double lastDouble = 0.0;
        double trueX = 0.5 - relative.x;
        double trueY = 0.5 - relative.y;
        double trueZ = 0.5 - relative.z;

        if (Math.abs(trueX) > 0.25 && Math.abs(trueX) != 0.5) {
            lastDouble = trueX;
        }
        if (Math.abs(trueY) > 0.25 && Math.abs(trueY) != 0.5) {
            if (Math.abs(lastDouble) < Math.abs(trueY)) {
                lastDouble = trueY;
            }
        }
        if (Math.abs(trueZ) > 0.25 && Math.abs(trueZ) != 0.5) {
            if (Math.abs(lastDouble) < Math.abs(trueZ)) {
                lastDouble = trueZ;
            }
        }

        if (Math.abs(lastDouble) != 0.5) {
            if (lastDouble == trueX) {
                return lastDouble < 0.25 ? Direction.EAST : Direction.WEST;
            }
            else if (lastDouble == trueY) {
                return lastDouble < 0.25 ? Direction.UP : Direction.DOWN;
            }
            else if (lastDouble == trueZ) {
                return lastDouble < 0.25 ? Direction.SOUTH : Direction.NORTH;
            }
        }
        return null;
    }

    @Nullable
    private static Direction getConfigDirectionForRope(Vector3d relative) {
        if (relative.x < 0.25)
            return Direction.WEST;
        if (relative.x > 0.75)
            return Direction.EAST;
        if (relative.y < 0.25)
            return Direction.DOWN;
        if (relative.y > 0.75)
            return Direction.UP;
        if (relative.z < 0.25)
            return Direction.NORTH;
        if (relative.z > 0.75)
            return Direction.SOUTH;
        return null;
    }

@Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResultType.PASS;

        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof IKnifeable) {
            ActionResultType result = ((IKnifeable) state.getBlock()).onKnife(context);
            if (result != ActionResultType.PASS) {
                return result;
            }
        }

        if (player.isSneaking() && state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            BlockState state1 = cycleProperty(state, BlockStateProperties.HORIZONTAL_FACING);
            world.setBlockState(pos, state1, 18);
            return ActionResultType.SUCCESS;
        }

        return super.onItemUse(context);
    }


    private static <T extends Comparable<T>> BlockState cycleProperty(BlockState state, Property<T> propertyIn) {
        return state.with(propertyIn, getAdjacentValue(propertyIn.getAllowedValues(), state.get(propertyIn)));
    }

    private static <T> T getAdjacentValue(Iterable<T> p_195959_0_, @Nullable T p_195959_1_) {
        return Util.getElementAfter(p_195959_0_, p_195959_1_);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (worldIn == null) return;
        if (!Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("item.druidcraft.hold_shift").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
        } else {
            tooltip.add(new TranslationTextComponent("item.druidcraft.knife.description1").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
        }
    }
}
