package com.vulp.druidcraft.blocks;

import javafx.geometry.Pos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WetMudBlock extends Block {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    private Block block;
    private int maxTicks = 0;

    public WetMudBlock(Block convertedBlock, Properties properties) {
        super(properties);
        this.block = convertedBlock;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, World worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if (!isNextToWater(worldIn, pos)) {
            if (!worldIn.isRemote && (worldIn.rand.nextInt(6) == 0 || maxTicks >= 10)) {
                worldIn.setBlockState(pos, block.getDefaultState());
            } else ++maxTicks;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack p_190948_1_, @Nullable IBlockReader p_190948_2_, List<ITextComponent> p_190948_3_, ITooltipFlag p_190948_4_) {
        super.addInformation(p_190948_1_, p_190948_2_, p_190948_3_, p_190948_4_);
        p_190948_3_.add(new TranslationTextComponent("druidcraft.crafting.requires_water", new TranslationTextComponent("druidcraft.crafting.requires_water.water").setStyle(new Style().setColor(TextFormatting.BLUE).setBold(true))).setStyle(new Style().setColor(TextFormatting.BLUE)));
    }

    private boolean isNextToWater(IBlockReader p_203943_1_, BlockPos p_203943_2_) {
        Direction[] var3 = Direction.values();

        for (Direction direction : var3) {
            IFluidState ifluidstate = p_203943_1_.getFluidState(p_203943_2_.offset(direction));
            if (ifluidstate.isTagged(FluidTags.WATER)) {
                return true;
            }
        }

        return false;
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setMotion(entityIn.getMotion().mul(0.5D, 1.0D, 0.5D));
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return true;
    }
}
