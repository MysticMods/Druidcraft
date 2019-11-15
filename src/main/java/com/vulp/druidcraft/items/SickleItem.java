package com.vulp.druidcraft.items;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.vulp.druidcraft.util.SickleHarvestUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class SickleItem extends ToolItem implements RadialToolItem, EffectiveSickleItem {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT);
    private static final Set<Material> EFFECTIVE_MATERIALS = ImmutableSet.of(Material.PLANTS, Material.OCEAN_PLANT, Material.TALL_PLANTS);

    private final int radius;

    public SickleItem(ItemProperties builder) {
        super((float) builder.getAttackDamage(), builder.getAttackSpeed(), builder.getTier(), EFFECTIVE_ON, builder.addToolType(ToolType.get("sickle"), builder.getTier().getHarvestLevel()));
        this.radius = builder.getRadius();
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        if (blockIn.getHarvestTool() == ToolType.get("sickle")) {
            return true;
        }
        Material material = blockIn.getMaterial();
        return EFFECTIVE_MATERIALS.contains(material);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            SickleHarvestUtil.breakNeighbours(stack, world, pos, (PlayerEntity)entity);
        }
        return super.onBlockDestroyed(stack, world, state, pos, entity);
    }

    @Override
    public Set<Block> getEffectiveBlocks() {
        return EFFECTIVE_ON;
    }

    @Override
    public Set<Material> getEffectiveMaterials() {
        return EFFECTIVE_MATERIALS;
    }
}
