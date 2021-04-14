/*
package com.vulp.druidcraft.mixin;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.entities.FieryGlassGlowEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Optional;


@Mixin(IForgeBlockState.class)
public interface IForgeBlockStateMixin {
    // @Inject(method = "getLightValue", at = @At("RETURN"), cancellable = true, remap = false)
    */
/**
     * @author VulpTheHorseDog
     *//*

    @Overwrite(remap = false)
    default int getLightValue(IBlockReader reader, BlockPos pos) {
        if (!(reader instanceof ChunkRenderCache)) {
            final World[] properWorld = new World[1];
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player != null && player.getServer() != null) {
                Iterable<ServerWorld> worlds = player.getServer().getWorlds();
                worlds.forEach((world) -> {
                    if (world.getChunk(pos) == reader) {
                        properWorld[0] = world;
                        Druidcraft.LOGGER.debug("YES!");
                    }
                });
            }
            if (properWorld[0].getEntitiesWithinAABB(FieryGlassGlowEntity.class, new AxisAlignedBB(pos.add(1.0D, 1.0D, 1.0D), pos)).size() > 0) {
                return 15;
            }
        }
        return getBlockState().getBlock().getLightValue(getBlockState(), reader, pos);
        */
/*} else if (reader instanceof ChunkPrimer) {
            List<CompoundNBT> entities = ((ChunkPrimer) reader).getEntities();
            for (CompoundNBT nbt : entities) {
                World world = (World) ((ChunkPrimer) reader).getWorldForge();
                if (world == null) {
                    Iterable<ServerWorld> worlds = Minecraft.getInstance().world.getServer().getWorlds();
                    worlds.forEach((serverWorld) -> {

                    });
                    world = Minecraft.getInstance().world.getServer().getWorld();
                }
                if (world != null) {
                    Optional<EntityType<?>> entityType2 = EntityType.readEntityType(nbt);
                    if (entityType2.isPresent()) {
                        Entity entity = entityType2.get().create(world);
                        if (entity != null) {
                            entity.read(nbt);
                            if (entity.getEntity() instanceof FieryGlassGlowEntity) {
                                if (entity.getPosition() == pos) {
                                    return 15;
                                }
                            }
                        }
                    }
                }
            }
        }
        reader.getMaxLightLevel();*//*

    }



    @Shadow
    BlockState getBlockState();

}
*/
