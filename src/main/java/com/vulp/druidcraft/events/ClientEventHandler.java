package com.vulp.druidcraft.events;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.items.KnifeItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Druidcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onRenderWorldLast(final DrawHighlightEvent.HighlightBlock event) {

        final PlayerEntity playerEntity = Minecraft.getInstance().player;
        if (playerEntity == null) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        RayTraceResult raytraceresult = mc.objectMouseOver;

        if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockRayTraceResult) raytraceresult).getPos();
            World world = playerEntity.getEntityWorld();
            if (KnifeItem.KNIFEABLE_BLOCKS.contains(world.getBlockState(blockPos).getBlock())) {
                VoxelShape shape = VoxelShapes.create(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                Vector3d vector3d = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
                if (world.getBlockState(blockPos).getBlock() == Blocks.SMITHING_TABLE) {
                    Direction dir = KnifeItem.getConfigDirection(raytraceresult.getHitVec().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                    if (dir != null && dir.getAxis() != Direction.Axis.Y && world.getBlockState(blockPos.offset(dir)).getBlock() == Blocks.SMITHING_TABLE) {
                        KnifeItem.renderHighlight(mc, vector3d, VoxelShapes.or(shape, VoxelShapes.create(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D).withOffset(dir.getXOffset(), dir.getYOffset(), dir.getZOffset())), blockPos, playerEntity, event.getContext(), event.getMatrix());
                        event.setCanceled(true);
                    }
                }
                // KnifeItem.renderHighlight(mc, vector3d, shape, blockPos, playerEntity, event.getContext(), event.getMatrixStack(), event.getPartialTicks(), event.getProjectionMatrix(), event.getFinishTimeNano());
            }
        }
    }

}
