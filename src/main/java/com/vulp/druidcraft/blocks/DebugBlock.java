package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DebugBlock extends Block {

    public PlayerEntity player;

    public DebugBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        checkStuff(player);
        return ActionResultType.SUCCESS;
    }

    public void checkStuff(PlayerEntity player) {

        if (!player.world.isRemote()) {
            CompoundNBT data = player.getPersistentData();
            int spawnX;
            int spawnY;
            int spawnZ;
            int tempX = 0;
            int tempY = 0;
            int tempZ = 0;
            int holderX = 0;
            int holderY = 0;
            int holderZ = 0;
            BlockPos mainSpawn = player.getBedLocation(player.dimension);
                spawnX = mainSpawn.getX();
                spawnY = mainSpawn.getY();
                spawnZ = mainSpawn.getZ();
            if (data.contains("TempSpawnX")) {
                tempX = data.getInt("TempSpawnX");
                tempY = data.getInt("TempSpawnY");
                tempZ = data.getInt("TempSpawnZ");
            }
            if (data.contains("HolderSpawnX")) {
                holderX = data.getInt("HolderSpawnX");
                holderY = data.getInt("HolderSpawnY");
                holderZ = data.getInt("HolderSpawnZ");
            }
            Druidcraft.LOGGER.debug("----------------");
            Druidcraft.LOGGER.debug("Spawn = " + spawnX + "-" + spawnY + "-" + spawnZ + ".");
            if (!(tempX == 0 && tempY == 0 && tempZ == 0)) {
                Druidcraft.LOGGER.debug("Temp = " + tempX + "-" + tempY + "-" + tempZ + ".");
            } else
                Druidcraft.LOGGER.debug("Temp = null...");
            if (!(holderX == 0 && holderY == 0 && holderZ == 0)) {
                Druidcraft.LOGGER.debug("Holder = " + holderX + "-" + holderY + "-" + holderZ + ".");
            } else
                Druidcraft.LOGGER.debug("Holder = null...");
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (worldIn == null) return;
        if (!Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("block.druidcraft.hold_shift").setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true)));
        } else {
            tooltip.add(new TranslationTextComponent("block.druidcraft.debug_block.description1").setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true)));
        }
    }

}
