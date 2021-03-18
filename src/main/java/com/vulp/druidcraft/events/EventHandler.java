package com.vulp.druidcraft.events;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.api.BedrollDyeColorIndex;
import com.vulp.druidcraft.blocks.BedrollBlock;
import com.vulp.druidcraft.config.DropRateConfig;
import com.vulp.druidcraft.entities.DuragemProtectionEntity;
import com.vulp.druidcraft.inventory.TravelPackInventory;
import com.vulp.druidcraft.items.TravelPackItem;
import com.vulp.druidcraft.network.PacketHandler;
import com.vulp.druidcraft.network.message.BlockProtectionVisualMessage;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.PistonType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.PistonEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid=Druidcraft.MODID, bus= Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    @SubscribeEvent
    public static void onBlockDamaged(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        // Block Protection
        List<DuragemProtectionEntity> list = world.getEntitiesWithinAABB(DuragemProtectionEntity.class, new AxisAlignedBB(pos.add(1.0D, 1.0D, 1.0D), pos));
        if (list.size() > 0) {
            list.get(0).setVisible(true);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onExplosionEvent(ExplosionEvent event) {
        World world = event.getWorld();
        Explosion explosion = event.getExplosion();
        // Block Protection
        for (BlockPos pos : explosion.getAffectedBlockPositions().toArray(new BlockPos[0])) {
            List<DuragemProtectionEntity> list = world.getEntitiesWithinAABB(DuragemProtectionEntity.class, new AxisAlignedBB(pos.add(1.0D, 1.0D, 1.0D), pos));
            if (list.size() > 0) {
                Supplier<Chunk> chunkSupplier = () -> (Chunk) world.getChunk(pos);
                PacketHandler.instance.send(PacketDistributor.TRACKING_CHUNK.with(chunkSupplier), new BlockProtectionVisualMessage(pos));
                event.getExplosion().getAffectedBlockPositions().remove(pos);
            }
        }
    }

    @SubscribeEvent
    public static void onMobGrief(LivingDestroyBlockEvent event) {
        World world = event.getEntity().getEntityWorld();
        BlockPos pos = event.getPos();
        // Block Protection
        List<DuragemProtectionEntity> list = world.getEntitiesWithinAABB(DuragemProtectionEntity.class, new AxisAlignedBB(pos.add(1.0D, 1.0D, 1.0D), pos));
        if (list.size() > 0) {
            Supplier<Chunk> chunkSupplier = () -> (Chunk) world.getChunk(pos);
            PacketHandler.instance.send(PacketDistributor.TRACKING_CHUNK.with(chunkSupplier), new BlockProtectionVisualMessage(pos));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void beforePistonUsed(PistonEvent.Pre event) {
        IWorld world = event.getWorld();
        int modifier = event.getPistonMoveType() == PistonEvent.PistonMoveType.EXTEND ? 1 : 2;
        BlockPos pos = event.getPos().offset(event.getDirection(), modifier);
        // Block Protection
        if (!(modifier == 2 && world.getBlockState(event.getFaceOffsetPos()).get(PistonHeadBlock.TYPE) == PistonType.DEFAULT)) {
            List<DuragemProtectionEntity> list = world.getEntitiesWithinAABB(DuragemProtectionEntity.class, new AxisAlignedBB(pos.add(1.0D, 1.0D, 1.0D), pos));
            if (list.size() > 0) {
                Supplier<Chunk> chunkSupplier = () -> (Chunk) world.getChunk(pos);
                PacketHandler.instance.send(PacketDistributor.TRACKING_CHUNK.with(chunkSupplier), new BlockProtectionVisualMessage(pos));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        IWorld world = event.getWorld();
        BlockPos pos = event.getPos();
        // Block Protection
        List<DuragemProtectionEntity> list = world.getEntitiesWithinAABB(DuragemProtectionEntity.class, new AxisAlignedBB(pos.add(1.0D, 1.0D, 1.0D), pos));
        if (list.size() > 0) {
            list.get(0).setVisible(true);
            event.setCanceled(true);
        }
        // Seed Drops
        if (DropRateConfig.drop_seeds.get()) {
            if (!event.getWorld().isRemote()) {
                if ((event.getPlayer().getHeldItemMainhand().getItem() != Items.SHEARS) && (!event.getPlayer().isCreative())) {
                    if (event.getState().getBlock() == Blocks.GRASS || event.getState().getBlock() == Blocks.TALL_GRASS || event.getState().getBlock() == Blocks.FERN || event.getState().getBlock() == Blocks.LARGE_FERN) {
                        if (Math.random() <= (double) DropRateConfig.hemp_seed_drops.get() / 100) {
                            event.getWorld().setBlockState(event.getPos(), Blocks.AIR.getDefaultState(), 2);
                            event.getWorld().addEntity(new ItemEntity((World) event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(ItemRegistry.hemp_seeds, 1)));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
        PlayerEntity player = event.getPlayer();
        BlockPos pos = event.getNewSpawn();
        World world = player.getEntityWorld();
        if (pos != null && !world.isRemote) {
            if (player.getEntityWorld().getBlockState(pos).getBlock() instanceof BedrollBlock) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        PlayerEntity player = event.getPlayer();
        ItemStack itemStack = player.getHeldItemMainhand();
        if (!(itemStack.getItem() instanceof TravelPackItem)) {
            itemStack = player.getHeldItemOffhand();
            if (!(itemStack.getItem() instanceof TravelPackItem)) {
                return;
            }
        }
        CompoundNBT nbt = itemStack.getOrCreateTag();
        if (nbt.contains("BedrollPosX")) {
            BlockPos pos = new BlockPos(nbt.getInt("BedrollPosX"), nbt.getInt("BedrollPosY"), nbt.getInt("BedrollPosZ"));
            World world = player.getEntityWorld();
            BlockState blockState = world.getBlockState(pos);
            if (blockState.getBlock() instanceof BedrollBlock) {
                BedrollDyeColorIndex bedrollDyeColor = BedrollDyeColorIndex.byBlock((BedrollBlock) blockState.getBlock());
                nbt.putInt("Color", bedrollDyeColor.getIndex());
                TravelPackInventory inventory = new TravelPackInventory(itemStack);
                inventory.setInventorySlotContents(0, new ItemStack(bedrollDyeColor.getBedrollItem(), 1));
                inventory.writeItemStack();
                Direction facing = blockState.get(BedrollBlock.HORIZONTAL_FACING);
                BlockState headBlock = world.getBlockState(pos);
                BlockState footBlock = world.getBlockState(pos.offset(facing.getOpposite()));
                world.setBlockState(pos, headBlock.get(BedrollBlock.WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState());
                world.setBlockState(pos.offset(facing.getOpposite()), footBlock.get(BedrollBlock.WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState());
            }
            nbt.remove("BedrollPosX");
            nbt.remove("BedrollPosY");
            nbt.remove("BedrollPosZ");
        }
    }


/*    @SubscribeEvent
    public static void onSpawn(PlayerEvent.PlayerRespawnEvent event) {
        World world = event.getPlayer().world;
        if (!world.isRemote()) {
            Druidcraft.LOGGER.debug("----------");
            Druidcraft.LOGGER.debug("Spawn Event");
            Druidcraft.LOGGER.debug("----------");
            PlayerEntity player = event.getPlayer();
            CompoundNBT playerData = player.getPersistentData();
            BlockPos mainSpawn = player.getBedLocation(player.dimension);
            if (playerData.contains("TempSpawnX")) {
                Druidcraft.LOGGER.debug("Initiating...");
                int tempX = playerData.getInt("TempSpawnX");
                int tempY = playerData.getInt("TempSpawnY");
                int tempZ = playerData.getInt("TempSpawnZ");
                BlockPos tempPos = new BlockPos(tempX, tempY, tempZ);
                if (tempPos != mainSpawn && playerData.contains("HolderSpawnX")) {
                    int holderX = playerData.getInt("HolderSpawnX");
                    int holderY = playerData.getInt("HolderSpawnY");
                    int holderZ = playerData.getInt("HolderSpawnZ");
                    BlockPos holderSpawn = new BlockPos(holderX, holderY, holderZ);
                    if (!holderSpawn.equals(mainSpawn)) {
                        Druidcraft.LOGGER.debug("Detected change in main spawn. Spawn holder modified.");
                        playerData.putInt("HolderSpawnX", mainSpawn.getX());
                        playerData.putInt("HolderSpawnY", mainSpawn.getY());
                        playerData.putInt("HolderSpawnZ", mainSpawn.getZ());
                    }
                }
                if (event.getPlayer().world.getBlockState(tempPos).getBlock() instanceof BedrollBlock) {
                    Druidcraft.LOGGER.debug("Detected existing bedroll.");
                    // Spawn holder created every time I die with an existing bedroll. Should only happen once.
                    if (!playerData.contains("HolderSpawnX")) {
                        Druidcraft.LOGGER.debug("Spawn holder created.");
                        playerData.putInt("HolderSpawnX", mainSpawn.getX());
                        playerData.putInt("HolderSpawnY", mainSpawn.getY());
                        playerData.putInt("HolderSpawnZ", mainSpawn.getZ());
                    }
                    player.setSpawnPoint(tempPos, true, player.dimension);
                } else {
                    Druidcraft.LOGGER.debug("Bedroll not existent.");
                    if (playerData.contains("HolderSpawnX")) {
                        Druidcraft.LOGGER.debug("Main spawn restored.");
                        int holderX = playerData.getInt("HolderSpawnX");
                        int holderY = playerData.getInt("HolderSpawnY");
                        int holderZ = playerData.getInt("HolderSpawnZ");
                        BlockPos holderSpawn = new BlockPos(holderX, holderY, holderZ);
                        player.setSpawnPoint(holderSpawn, false, player.dimension);
                        playerData.remove("HolderSpawnX");
                        playerData.remove("HolderSpawnY");
                        playerData.remove("HolderSpawnZ");
                    }
                    playerData.remove("TempSpawnX");
                    playerData.remove("TempSpawnY");
                    playerData.remove("TempSpawnZ");
                }
            }
        }
    }*/

/*    @SubscribeEvent
    public void onCloned(PlayerEvent.Clone event) {
        if (!event.getPlayer().world.isRemote()) {
            CompoundNBT oldData = event.getOriginal().getPersistentData();
            CompoundNBT newData = event.getPlayer().getPersistentData();
            if (oldData.contains("TempSpawnX")) {
                newData.putInt("TempSpawnX", oldData.getInt("TempSpawnX"));
                newData.putInt("TempSpawnY", oldData.getInt("TempSpawnY"));
                newData.putInt("TempSpawnZ", oldData.getInt("TempSpawnZ"));
            }
            if (oldData.contains("HolderSpawnX")) {
                newData.putInt("HolderSpawnX", oldData.getInt("HolderSpawnX"));
                newData.putInt("HolderSpawnY", oldData.getInt("HolderSpawnY"));
                newData.putInt("HolderSpawnZ", oldData.getInt("HolderSpawnZ"));
            }
        }
    }*/

}
