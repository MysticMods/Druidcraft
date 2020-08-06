package com.vulp.druidcraft.blocks;

import com.mojang.datafixers.util.Pair;
import com.vulp.druidcraft.api.CrateDataCarrier;
import com.vulp.druidcraft.api.CrateIndex;
import com.vulp.druidcraft.api.CrateType;
import com.vulp.druidcraft.blocks.tileentities.CrateTileEntity;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
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
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings({"NullableProblems", "Duplicates"})
public class CrateBlock extends ContainerBlock {
  public static final BooleanProperty PROPERTY_OPEN = BlockStateProperties.OPEN;
  public static final EnumProperty<CrateIndex> INDEX = EnumProperty.create("index", CrateIndex.class);
  private static final BooleanProperty ROTATED = BooleanProperty.create("rot");

  public CrateBlock(Properties properties) {
    super(properties);
    this.setDefaultState(this.stateContainer.getBaseState()
        .with(INDEX, CrateIndex.CRATE00_1)
        .with(PROPERTY_OPEN, false)
        .with(ROTATED, false));
  }

  @SuppressWarnings("deprecation")
  @Override
  public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
    ItemStack itemstack = player.getHeldItem(handIn);
    Item item = itemstack.getItem();
    if (item == ItemRegistry.crate) {
      return ActionResultType.PASS;
    }
    if (worldIn.isRemote) {
      return ActionResultType.SUCCESS;
    } else {
      // TODO: What is this
/*      INamedContainerProvider inamedcontainerprovider = this.getContainer(state, worldIn, pos);
      if (inamedcontainerprovider != null) {
        player.openContainer(inamedcontainerprovider);
      }*/
      return ActionResultType.SUCCESS;
    }
  }

  private ArrayList<BlockPos> checkForCrates(World world, BlockPos pos) {

    ArrayList<BlockPos> shapeConfig = new ArrayList<>();

    // OCTO
    if (checkCrateBlocks(world, pos.east(), pos.south(), pos.south().east(), pos.up(), pos.up().east(), pos.up().south(), pos.up().south().east())) {
      shapeConfig.add(pos);
      shapeConfig.add(pos.east());
      shapeConfig.add(pos.south());
      shapeConfig.add(pos.south().east());
      shapeConfig.add(pos.up());
      shapeConfig.add(pos.up().east());
      shapeConfig.add(pos.up().south());
      shapeConfig.add(pos.up().south().east());
      if (checkIllegalCrates(world, pos, CrateType.OCTO, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.west(), pos.south().west(), pos.south(), pos.up().west(), pos.up(), pos.up().south().west(), pos.up().south())) {
      shapeConfig.clear();
      shapeConfig.add(pos.west());
      shapeConfig.add(pos);
      shapeConfig.add(pos.south().west());
      shapeConfig.add(pos.south());
      shapeConfig.add(pos.up().west());
      shapeConfig.add(pos.up());
      shapeConfig.add(pos.up().south().west());
      shapeConfig.add(pos.up().south());
      if (checkIllegalCrates(world, pos, CrateType.OCTO, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.north(), pos.north().east(), pos.east(), pos.up().north(), pos.up().north().east(), pos.up(), pos.up().east())) {
      shapeConfig.clear();
      shapeConfig.add(pos.north());
      shapeConfig.add(pos.north().east());
      shapeConfig.add(pos);
      shapeConfig.add(pos.east());
      shapeConfig.add(pos.up().north());
      shapeConfig.add(pos.up().north().east());
      shapeConfig.add(pos.up());
      shapeConfig.add(pos.up().east());
      if (checkIllegalCrates(world, pos, CrateType.OCTO, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.north().west(), pos.north(), pos.west(), pos.up().north().west(), pos.up().north(), pos.up().west(), pos.up())) {
      shapeConfig.clear();
      shapeConfig.add(pos.north().west());
      shapeConfig.add(pos.north());
      shapeConfig.add(pos.west());
      shapeConfig.add(pos);
      shapeConfig.add(pos.up().north().west());
      shapeConfig.add(pos.up().north());
      shapeConfig.add(pos.up().west());
      shapeConfig.add(pos.up());
      if (checkIllegalCrates(world, pos, CrateType.OCTO, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.down(), pos.down().east(), pos.down().south(), pos.down().south().east(), pos.east(), pos.south(), pos.south().east())) {
      shapeConfig.clear();
      shapeConfig.add(pos.down());
      shapeConfig.add(pos.down().east());
      shapeConfig.add(pos.down().south());
      shapeConfig.add(pos.down().south().east());
      shapeConfig.add(pos);
      shapeConfig.add(pos.east());
      shapeConfig.add(pos.south());
      shapeConfig.add(pos.south().east());
      if (checkIllegalCrates(world, pos, CrateType.OCTO, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.down().west(), pos.down(), pos.down().south().west(), pos.down().south(), pos.west(), pos.south().west(), pos.south())) {
      shapeConfig.clear();
      shapeConfig.add(pos.down().west());
      shapeConfig.add(pos.down());
      shapeConfig.add(pos.down().south().west());
      shapeConfig.add(pos.down().south());
      shapeConfig.add(pos.west());
      shapeConfig.add(pos);
      shapeConfig.add(pos.south().west());
      shapeConfig.add(pos.south());
      if (checkIllegalCrates(world, pos, CrateType.OCTO, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.down().north(), pos.down().north().east(), pos.down(), pos.down().east(), pos.north(), pos.north().east(), pos.east())) {
      shapeConfig.clear();
      shapeConfig.add(pos.down().north());
      shapeConfig.add(pos.down().north().east());
      shapeConfig.add(pos.down());
      shapeConfig.add(pos.down().east());
      shapeConfig.add(pos.north());
      shapeConfig.add(pos.north().east());
      shapeConfig.add(pos);
      shapeConfig.add(pos.east());
      if (checkIllegalCrates(world, pos, CrateType.OCTO, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.down().north().west(), pos.down().north(), pos.down().west(), pos.down(), pos.north().west(), pos.north(), pos.west())) {
      shapeConfig.clear();
      shapeConfig.add(pos.down().north().west());
      shapeConfig.add(pos.down().north());
      shapeConfig.add(pos.down().west());
      shapeConfig.add(pos.down());
      shapeConfig.add(pos.north().west());
      shapeConfig.add(pos.north());
      shapeConfig.add(pos.west());
      shapeConfig.add(pos);
      if (checkIllegalCrates(world, pos, CrateType.OCTO, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }

    // QUAD X
    if (checkCrateBlocks(world, pos.south(), pos.up(), pos.up().south())) {
      shapeConfig.clear();
      shapeConfig.add(pos);
      shapeConfig.add(pos.south());
      shapeConfig.add(pos.up());
      shapeConfig.add(pos.up().south());
      if (checkIllegalCrates(world, pos, CrateType.QUAD_X, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.north(), pos.north().up(), pos.up())) {
      shapeConfig.clear();
      shapeConfig.add(pos.north());
      shapeConfig.add(pos);
      shapeConfig.add(pos.up().north());
      shapeConfig.add(pos.up());
      if (checkIllegalCrates(world, pos, CrateType.QUAD_X, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.down(), pos.down().south(), pos.south())) {
      shapeConfig.clear();
      shapeConfig.add(pos.down());
      shapeConfig.add(pos.down().south());
      shapeConfig.add(pos);
      shapeConfig.add(pos.south());
      if (checkIllegalCrates(world, pos, CrateType.QUAD_X, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.down().north(), pos.down(), pos.north())) {
      shapeConfig.clear();
      shapeConfig.add(pos.down().north());
      shapeConfig.add(pos.down());
      shapeConfig.add(pos.north());
      shapeConfig.add(pos);
      if (checkIllegalCrates(world, pos, CrateType.QUAD_X, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }

    // QUAD Y
    if (checkCrateBlocks(world, pos.east(), pos.south(), pos.south().east())) {
      shapeConfig.clear();
      shapeConfig.add(pos);
      shapeConfig.add(pos.east());
      shapeConfig.add(pos.south());
      shapeConfig.add(pos.south().east());
      if (checkIllegalCrates(world, pos, CrateType.QUAD_Y, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.west(), pos.south(), pos.south().west())) {
      shapeConfig.clear();
      shapeConfig.add(pos.west());
      shapeConfig.add(pos);
      shapeConfig.add(pos.south().west());
      shapeConfig.add(pos.south());
      if (checkIllegalCrates(world, pos, CrateType.QUAD_Y, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.north(), pos.east(), pos.north().east())) {
      shapeConfig.clear();
      shapeConfig.add(pos.north());
      shapeConfig.add(pos.north().east());
      shapeConfig.add(pos);
      shapeConfig.add(pos.east());
      if (checkIllegalCrates(world, pos, CrateType.QUAD_Y, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.north(), pos.west(), pos.north().west())) {
      shapeConfig.clear();
      shapeConfig.add(pos.north().west());
      shapeConfig.add(pos.north());
      shapeConfig.add(pos.west());
      shapeConfig.add(pos);
      if (checkIllegalCrates(world, pos, CrateType.QUAD_Y, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }

    // QUAD Z
    if (checkCrateBlocks(world, pos.east(), pos.up(), pos.up().east())) {
      shapeConfig.clear();
      shapeConfig.add(pos);
      shapeConfig.add(pos.east());
      shapeConfig.add(pos.up());
      shapeConfig.add(pos.up().east());
      if (checkIllegalCrates(world, pos, CrateType.QUAD_Z, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.west(), pos.up().west(), pos.up())) {
      shapeConfig.clear();
      shapeConfig.add(pos.west());
      shapeConfig.add(pos);
      shapeConfig.add(pos.up().west());
      shapeConfig.add(pos.up());
      if (checkIllegalCrates(world, pos, CrateType.QUAD_Z, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.down(), pos.down().east(), pos.east())) {
      shapeConfig.clear();
      shapeConfig.add(pos.down());
      shapeConfig.add(pos.down().east());
      shapeConfig.add(pos);
      shapeConfig.add(pos.east());
      if (checkIllegalCrates(world, pos, CrateType.QUAD_Z, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.down().west(), pos.down(), pos.west())) {
      shapeConfig.clear();
      shapeConfig.add(pos.down().west());
      shapeConfig.add(pos.down());
      shapeConfig.add(pos.west());
      shapeConfig.add(pos);
      if (checkIllegalCrates(world, pos, CrateType.QUAD_Z, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }

    // DOUBLE X
    if (checkCrateBlocks(world, pos.east())) {
      shapeConfig.clear();
      shapeConfig.add(pos);
      shapeConfig.add(pos.east());
      if (checkIllegalCrates(world, pos, CrateType.DOUBLE_X, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.west())) {
      shapeConfig.clear();
      shapeConfig.add(pos.west());
      shapeConfig.add(pos);
      if (checkIllegalCrates(world, pos, CrateType.DOUBLE_X, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }

    // DOUBLE Y
    if (checkCrateBlocks(world, pos.up())) {
      shapeConfig.clear();
      shapeConfig.add(pos);
      shapeConfig.add(pos.up());
      if (checkIllegalCrates(world, pos, CrateType.DOUBLE_Y, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.down())) {
      shapeConfig.clear();
      shapeConfig.add(pos.down());
      shapeConfig.add(pos);
      if (checkIllegalCrates(world, pos, CrateType.DOUBLE_Y, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }

    // DOUBLE Z
    if (checkCrateBlocks(world, pos.south())) {
      shapeConfig.clear();
      shapeConfig.add(pos);
      shapeConfig.add(pos.south());
      if (checkIllegalCrates(world, pos, CrateType.DOUBLE_Z, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }
    if (checkCrateBlocks(world, pos.north())) {
      shapeConfig.clear();
      shapeConfig.add(pos.north());
      shapeConfig.add(pos);
      if (checkIllegalCrates(world, pos, CrateType.DOUBLE_Z, shapeConfig) && checkSides(world, shapeConfig)) {
        return shapeConfig;
      }
    }

    shapeConfig.clear();
    shapeConfig.add(pos);
    return shapeConfig;
  }

  @SuppressWarnings("deprecation")
  @Override
  public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
    TileEntity tileentity = worldIn.getTileEntity(pos);
    if (tileentity instanceof CrateTileEntity) {
      ((CrateTileEntity) tileentity).crateTick();
    }

  }

  public static ArrayList<BlockPos> getBlockPositions(World world, BlockPos pos) {
    ArrayList<BlockPos> config = new ArrayList<>();
    CrateType type = CrateType.SINGLE;
    int num = world.getBlockState(pos).get(INDEX).getPosNumber();

    if (world.getBlockState(pos).get(INDEX).getType() == CrateType.DOUBLE_X) {
      type = CrateType.DOUBLE_X;
    }
    if (world.getBlockState(pos).get(INDEX).getType() == CrateType.DOUBLE_Y) {
      type = CrateType.DOUBLE_Y;
    }
    if (world.getBlockState(pos).get(INDEX).getType() == CrateType.DOUBLE_Z) {
      type = CrateType.DOUBLE_Z;
    }
    if (world.getBlockState(pos).get(INDEX).getType() == CrateType.QUAD_X) {
      type = CrateType.QUAD_X;
    }
    if (world.getBlockState(pos).get(INDEX).getType() == CrateType.QUAD_Y) {
      type = CrateType.QUAD_Y;
    }
    if (world.getBlockState(pos).get(INDEX).getType() == CrateType.QUAD_Z) {
      type = CrateType.QUAD_Z;
    }
    if (world.getBlockState(pos).get(INDEX).getType() == CrateType.OCTO) {
      type = CrateType.OCTO;
    }

    if (type == CrateType.OCTO) {
      if (num == 1) {
        config.add(pos);
        config.add(pos.east());
        config.add(pos.south());
        config.add(pos.east().south());
        config.add(pos.up());
        config.add(pos.east().up());
        config.add(pos.south().up());
        config.add(pos.east().south().up());
      }
      if (num == 2) {
        config.add(pos.west());
        config.add(pos);
        config.add(pos.south().west());
        config.add(pos.south());
        config.add(pos.west().up());
        config.add(pos.up());
        config.add(pos.south().west().up());
        config.add(pos.south().up());
      }
      if (num == 3) {
        config.add(pos.north());
        config.add(pos.north().east());
        config.add(pos);
        config.add(pos.east());
        config.add(pos.north().up());
        config.add(pos.north().east().up());
        config.add(pos.up());
        config.add(pos.east().up());
      }
      if (num == 4) {
        config.add(pos.north().west());
        config.add(pos.north());
        config.add(pos.west());
        config.add(pos);
        config.add(pos.north().west().up());
        config.add(pos.north().up());
        config.add(pos.west().up());
        config.add(pos.up());
      }
      if (num == 5) {
        config.add(pos.down());
        config.add(pos.east().down());
        config.add(pos.south().down());
        config.add(pos.east().south().down());
        config.add(pos);
        config.add(pos.east());
        config.add(pos.south());
        config.add(pos.east().south());
      }
      if (num == 6) {
        config.add(pos.west().down());
        config.add(pos.down());
        config.add(pos.south().west().down());
        config.add(pos.south().down());
        config.add(pos.west());
        config.add(pos);
        config.add(pos.south().west());
        config.add(pos.south());
      }
      if (num == 7) {
        config.add(pos.north().down());
        config.add(pos.north().east().down());
        config.add(pos.down());
        config.add(pos.east().down());
        config.add(pos.north());
        config.add(pos.north().east());
        config.add(pos);
        config.add(pos.east());
      }
      if (num == 8) {
        config.add(pos.north().west().down());
        config.add(pos.north().down());
        config.add(pos.west().down());
        config.add(pos.down());
        config.add(pos.north().west());
        config.add(pos.north());
        config.add(pos.west());
        config.add(pos);
      }
    }
    if (type == CrateType.QUAD_X) {
      if (num == 1) {
        config.add(pos);
        config.add(pos.south());
        config.add(pos.up());
        config.add(pos.up().south());
      }
      if (num == 2) {
        config.add(pos.north());
        config.add(pos);
        config.add(pos.north().up());
        config.add(pos.up());
      }
      if (num == 3) {
        config.add(pos.down());
        config.add(pos.down().south());
        config.add(pos);
        config.add(pos.south());
      }
      if (num == 4) {
        config.add(pos.down().north());
        config.add(pos.down());
        config.add(pos.north());
        config.add(pos);
      }
    }
    if (type == CrateType.QUAD_Y) {
      if (num == 1) {
        config.add(pos);
        config.add(pos.east());
        config.add(pos.south());
        config.add(pos.south().east());
      }
      if (num == 2) {
        config.add(pos.west());
        config.add(pos);
        config.add(pos.west().south());
        config.add(pos.south());
      }
      if (num == 3) {
        config.add(pos.north());
        config.add(pos.north().east());
        config.add(pos);
        config.add(pos.east());
      }
      if (num == 4) {
        config.add(pos.north().west());
        config.add(pos.north());
        config.add(pos.west());
        config.add(pos);
      }
    }
    if (type == CrateType.QUAD_Z) {
      if (num == 1) {
        config.add(pos);
        config.add(pos.east());
        config.add(pos.up());
        config.add(pos.up().east());
      }
      if (num == 2) {
        config.add(pos.west());
        config.add(pos);
        config.add(pos.west().up());
        config.add(pos.up());
      }
      if (num == 3) {
        config.add(pos.down());
        config.add(pos.down().east());
        config.add(pos);
        config.add(pos.east());
      }
      if (num == 4) {
        config.add(pos.down().west());
        config.add(pos.down());
        config.add(pos.west());
        config.add(pos);
      }
    }
    if (type == CrateType.DOUBLE_X) {
      if (num == 1) {
        config.add(pos);
        config.add(pos.east());
      }
      if (num == 2) {
        config.add(pos.west());
        config.add(pos);
      }
    }
    if (type == CrateType.DOUBLE_Y) {
      if (num == 1) {
        config.add(pos);
        config.add(pos.up());
      }
      if (num == 2) {
        config.add(pos.down());
        config.add(pos);
      }
    }
    if (type == CrateType.DOUBLE_Z) {
      if (num == 1) {
        config.add(pos);
        config.add(pos.south());
      }
      if (num == 2) {
        config.add(pos.north());
        config.add(pos);
      }
    }
    if (type == CrateType.SINGLE) {
      config.add(pos);
    }
    return config;
  }

  @Deprecated
  public static boolean checkBlockPositions(World world, BlockPos pos, CrateType type, ArrayList<BlockPos> posList) {
    //int num = world.getBlockState(pos).get(INDEX).getPosNumber();

    for (int i = 1; i > posList.size() + 1; i++) {
      if (type == CrateType.OCTO) {
        if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(INDEX).getPosNumber() == i && world.getBlockState(posList.get(i)).get(INDEX).getType() == CrateType.OCTO)) {
          return false;
        }
      }
      if (type == CrateType.QUAD_X) {
        if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(INDEX).getPosNumber() == i && world.getBlockState(posList.get(i)).get(INDEX).getType() == CrateType.QUAD_X)) {
          return false;
        }
      }
      if (type == CrateType.QUAD_Y) {
        if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(INDEX).getPosNumber() == i && world.getBlockState(posList.get(i)).get(INDEX).getType() == CrateType.QUAD_Y)) {
          return false;
        }
      }
      if (type == CrateType.QUAD_Z) {
        if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(INDEX).getPosNumber() == i && world.getBlockState(posList.get(i)).get(INDEX).getType() == CrateType.QUAD_Z)) {
          return false;
        }
      }
      if (type == CrateType.DOUBLE_X) {
        if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(INDEX).getPosNumber() == i && world.getBlockState(posList.get(i)).get(INDEX).getType() == CrateType.DOUBLE_X)) {
          return false;
        }
      }
      if (type == CrateType.DOUBLE_Y) {
        if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(INDEX).getPosNumber() == i && world.getBlockState(posList.get(i)).get(INDEX).getType() == CrateType.DOUBLE_Y)) {
          return false;
        }
      }
      if (type == CrateType.DOUBLE_Z) {
        if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(INDEX).getPosNumber() == i && world.getBlockState(posList.get(i)).get(INDEX).getType() == CrateType.DOUBLE_Z)) {
          return false;
        }
      }
      if (type == CrateType.SINGLE) {
        if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(INDEX).getPosNumber() == i && world.getBlockState(posList.get(i)).get(INDEX).getType() == CrateType.SINGLE)) {
          return false;
        }
      }
    }
    return false;
  }

  private boolean checkIllegalCrates(World world, BlockPos pos, CrateType type, ArrayList<BlockPos> blockPosList) {
    if (type == CrateType.DOUBLE_X || type == CrateType.DOUBLE_Y || type == CrateType.DOUBLE_Z) {
      for (BlockPos blockPos : blockPosList) {
        if (blockPos != pos) {
          if (world.getBlockState(blockPos).get(INDEX).getType() != CrateType.SINGLE) {
            return false;
          }
        }
      }
    }
    if (type == CrateType.OCTO) {
      for (BlockPos blockPos : blockPosList) {
        if (blockPos != pos) {
          if (world.getBlockState(blockPos).get(INDEX).getType() == CrateType.OCTO) {
            return false;
          }
        }
      }
    }
    if (type == CrateType.QUAD_X) {
      for (BlockPos blockPos : blockPosList) {
        if (blockPos != pos) {
          if ((world.getBlockState(blockPos).get(INDEX).getType() == CrateType.DOUBLE_X || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.QUAD_X || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.QUAD_Y || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.QUAD_Z || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.OCTO)) {
            return false;
          }
        }
      }
    }
    if (type == CrateType.QUAD_Y) {
      for (BlockPos blockPos : blockPosList) {
        if (blockPos != pos) {
          if ((world.getBlockState(blockPos).get(INDEX).getType() == CrateType.DOUBLE_Y || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.QUAD_X || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.QUAD_Y || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.QUAD_Z || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.OCTO)) {
            return false;
          }
        }
      }
    }
    if (type == CrateType.QUAD_Z) {
      for (BlockPos blockPos : blockPosList) {
        if (blockPos != pos) {
          if ((world.getBlockState(blockPos).get(INDEX).getType() == CrateType.DOUBLE_Z || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.QUAD_X || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.QUAD_Y || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.QUAD_Z || world.getBlockState(blockPos).get(INDEX).getType() == CrateType.OCTO)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  private boolean checkSides(World world, ArrayList<BlockPos> blockPosList) {
    for (int i = 0; i < blockPosList.size(); i++) {
      CrateType type = getCrateType(blockPosList);
      int num = i + 1;
      // OCTO
      if (type == CrateType.OCTO && num == 1)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isSouth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isEast() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isUp())
          return false;
      if (type == CrateType.OCTO && num == 2)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isSouth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isWest() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isUp())
          return false;
      if (type == CrateType.OCTO && num == 3)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isNorth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isEast() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isUp())
          return false;
      if (type == CrateType.OCTO && num == 4)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isNorth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isWest() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isUp())
          return false;
      if (type == CrateType.OCTO && num == 5)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isSouth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isEast() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isDown())
          return false;
      if (type == CrateType.OCTO && num == 6)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isSouth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isWest() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isDown())
          return false;
      if (type == CrateType.OCTO && num == 7)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isNorth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isEast() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isDown())
          return false;
      if (type == CrateType.OCTO && num == 8)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isNorth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isWest() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isDown())
          return false;
      // QUAD X
      if (type == CrateType.QUAD_X && num == 1)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isSouth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isUp())
          return false;
      if (type == CrateType.QUAD_X && num == 2)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isNorth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isUp())
          return false;
      if (type == CrateType.QUAD_X && num == 3)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isSouth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isDown())
          return false;
      if (type == CrateType.QUAD_X && num == 4)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isNorth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isDown())
          return false;
      // QUAD Y
      if (type == CrateType.QUAD_Y && num == 1)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isSouth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isEast())
          return false;
      if (type == CrateType.QUAD_Y && num == 2)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isSouth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isWest())
          return false;
      if (type == CrateType.QUAD_Y && num == 3)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isNorth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isEast())
          return false;
      if (type == CrateType.QUAD_Y && num == 4)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isNorth() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isWest())
          return false;
      // QUAD Z
      if (type == CrateType.QUAD_Z && num == 1)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isUp() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isEast())
          return false;
      if (type == CrateType.QUAD_Z && num == 2)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isUp() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isWest())
          return false;
      if (type == CrateType.QUAD_Z && num == 3)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isDown() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isEast())
          return false;
      if (type == CrateType.QUAD_Z && num == 4)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isDown() || !world.getBlockState(blockPosList.get(i)).get(INDEX).isWest())
          return false;
      // DOUBLE X
      if (type == CrateType.DOUBLE_X && num == 1)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isEast())
          return false;
      if (type == CrateType.DOUBLE_X && num == 2)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isWest())
          return false;
      // DOUBLE Y
      if (type == CrateType.DOUBLE_Y && num == 1)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isUp())
          return false;
      if (type == CrateType.DOUBLE_Y && num == 2)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isDown())
          return false;
      // DOUBLE Z
      if (type == CrateType.DOUBLE_Z && num == 1)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isSouth())
          return false;
      if (type == CrateType.DOUBLE_Z && num == 2)
        if (!world.getBlockState(blockPosList.get(i)).get(INDEX).isNorth())
          return false;
    }
    return true;
  }

  // TODO: Convert to varargs(?)
  private boolean checkCrateBlocks(World world, BlockPos... positions) {
    for (BlockPos pos : positions) {
      if (!(world.getBlockState(pos).getBlock() instanceof CrateBlock)) {
        return false;
      }
    }

    return true;
  }

  private static Random bool = new Random();

  private boolean rotateCrate(BlockPos pos) {
    bool.setSeed(pos.getX() + pos.getY() + pos.getZ());
    bool.nextBoolean();
    return (bool.nextBoolean());
  }

  private CrateType getCrateType(ArrayList<BlockPos> blockPosList) {
    switch (blockPosList.size()) {
      case 1:
        return CrateType.SINGLE;
      case 2:
        BlockPos pos1_2 = blockPosList.get(0); //getCrateByNumber(0, blockPosList);
        BlockPos pos2_2 = blockPosList.get(1); //getCrateByNumber(1, blockPosList);
        if (pos1_2.getX() != pos2_2.getX()) {
          return CrateType.DOUBLE_X;
        }
        if (pos1_2.getY() != pos2_2.getY()) {
          return CrateType.DOUBLE_Y;
        }
        if (pos1_2.getZ() != pos2_2.getZ()) {
          return CrateType.DOUBLE_Z;
        }
      case 4:
        BlockPos pos1_4 = blockPosList.get(0); //getCrateByNumber(0, blockPosList);
        BlockPos pos2_4 = blockPosList.get(1); //getCrateByNumber(1, blockPosList);
        BlockPos pos3_4 = blockPosList.get(2); //getCrateByNumber(2, blockPosList);
        BlockPos pos4_4 = blockPosList.get(3); //getCrateByNumber(3, blockPosList);
        if (pos1_4.getX() == pos2_4.getX() && pos3_4.getX() == pos4_4.getX() && pos1_4.getX() == pos3_4.getX()) {
          return CrateType.QUAD_X;
        }
        if (pos1_4.getY() == pos2_4.getY() && pos3_4.getY() == pos4_4.getY() && pos1_4.getY() == pos3_4.getY()) {
          return CrateType.QUAD_Y;
        }
        if (pos1_4.getZ() == pos2_4.getZ() && pos3_4.getZ() == pos4_4.getZ() && pos1_4.getZ() == pos3_4.getZ()) {
          return CrateType.QUAD_Z;
        }
      case 8:
        return CrateType.OCTO;
    }
    return null;
  }

  private ArrayList<CrateDataCarrier> calculateSides(ArrayList<BlockPos> blockPosList) {
    ArrayList<CrateDataCarrier> crateDataList = new ArrayList<>();
    for (int i = 0; i < blockPosList.size(); i++) {
      CrateType type = getCrateType(blockPosList);
      int num = i + 1;
      // OCTO
      if (type == CrateType.OCTO && num == 1)
        crateDataList.add(new CrateDataCarrier(false, true, true, false, true, false));
      if (type == CrateType.OCTO && num == 2)
        crateDataList.add(new CrateDataCarrier(false, false, true, true, true, false));
      if (type == CrateType.OCTO && num == 3)
        crateDataList.add(new CrateDataCarrier(true, true, false, false, true, false));
      if (type == CrateType.OCTO && num == 4)
        crateDataList.add(new CrateDataCarrier(true, false, false, true, true, false));
      if (type == CrateType.OCTO && num == 5)
        crateDataList.add(new CrateDataCarrier(false, true, true, false, false, true));
      if (type == CrateType.OCTO && num == 6)
        crateDataList.add(new CrateDataCarrier(false, false, true, true, false, true));
      if (type == CrateType.OCTO && num == 7)
        crateDataList.add(new CrateDataCarrier(true, true, false, false, false, true));
      if (type == CrateType.OCTO && num == 8)
        crateDataList.add(new CrateDataCarrier(true, false, false, true, false, true));
      // QUAD X
      if (type == CrateType.QUAD_X && num == 1)
        crateDataList.add(new CrateDataCarrier(false, true, true, true, true, false));
      if (type == CrateType.QUAD_X && num == 2)
        crateDataList.add(new CrateDataCarrier(true, true, false, true, true, false));
      if (type == CrateType.QUAD_X && num == 3)
        crateDataList.add(new CrateDataCarrier(false, true, true, true, false, true));
      if (type == CrateType.QUAD_X && num == 4)
        crateDataList.add(new CrateDataCarrier(true, true, false, true, false, true));
      // QUAD Y
      if (type == CrateType.QUAD_Y && num == 1)
        crateDataList.add(new CrateDataCarrier(false, true, true, false, true, true));
      if (type == CrateType.QUAD_Y && num == 2)
        crateDataList.add(new CrateDataCarrier(false, false, true, true, true, true));
      if (type == CrateType.QUAD_Y && num == 3)
        crateDataList.add(new CrateDataCarrier(true, true, false, false, true, true));
      if (type == CrateType.QUAD_Y && num == 4)
        crateDataList.add(new CrateDataCarrier(true, false, false, true, true, true));
      // QUAD Z
      if (type == CrateType.QUAD_Z && num == 1)
        crateDataList.add(new CrateDataCarrier(true, true, true, false, true, false));
      if (type == CrateType.QUAD_Z && num == 2)
        crateDataList.add(new CrateDataCarrier(true, false, true, true, true, false));
      if (type == CrateType.QUAD_Z && num == 3)
        crateDataList.add(new CrateDataCarrier(true, true, true, false, false, true));
      if (type == CrateType.QUAD_Z && num == 4)
        crateDataList.add(new CrateDataCarrier(true, false, true, true, false, true));
      // DOUBLE X
      if (type == CrateType.DOUBLE_X && num == 1)
        crateDataList.add(new CrateDataCarrier(true, true, true, false, true, true));
      if (type == CrateType.DOUBLE_X && num == 2)
        crateDataList.add(new CrateDataCarrier(true, false, true, true, true, true));
      // DOUBLE Y
      if (type == CrateType.DOUBLE_Y && num == 1)
        crateDataList.add(new CrateDataCarrier(true, true, true, true, true, false));
      if (type == CrateType.DOUBLE_Y && num == 2)
        crateDataList.add(new CrateDataCarrier(true, true, true, true, false, true));
      // DOUBLE Z
      if (type == CrateType.DOUBLE_Z && num == 1)
        crateDataList.add(new CrateDataCarrier(false, true, true, true, true, true));
      if (type == CrateType.DOUBLE_Z && num == 2)
        crateDataList.add(new CrateDataCarrier(true, true, false, true, true, true));
      // SINGLE
      if (type == CrateType.SINGLE)
        crateDataList.add(new CrateDataCarrier(true, true, true, true, true, true));
    }
    return crateDataList;
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(INDEX, PROPERTY_OPEN, ROTATED);
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
    if (state.getBlock() != newState.getBlock()) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof IInventory) {
        InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
        worldIn.updateComparatorOutputLevel(pos, this);
      }

      super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

  }

  @Override
  @Nullable
  @Deprecated
  public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
    return null;
    // return getCrateInventory(worldIn, pos, guiFactory);
  }

  @Override
  public TileEntity createNewTileEntity(IBlockReader worldIn) {
    return new CrateTileEntity();
  }

/*    private static boolean isBlocked(IWorld world, BlockPos pos) {
        return isCatSittingOn(world, pos);
    }*/

/*    private static boolean isCatSittingOn(IWorld world, BlockPos pos) {
        List<CatEntity> list = world.getEntitiesWithinAABB(CatEntity.class, new AxisAlignedBB((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 2), (double)(pos.getZ() + 1)));
        if (!list.isEmpty()) {
            for(CatEntity catentity : list) {
                if (catentity.isSitting()) {
                    return true;
                }
            }
        }

        return false;
    }*/

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean hasComparatorInputOverride(BlockState state) {
    return true;
  }

  @SuppressWarnings("deprecation")
  @Override
  public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
    // TODO
    //return Container.calcRedstoneFromInventory(getInventory(worldIn, pos));
    return 0;
  }

  private ArrayList<Boolean> checkMissingCrates(World world, ArrayList<BlockPos> neighborList) {
    ArrayList<Boolean> missingList = new ArrayList<>();
    for (int i = 0; i < neighborList.size(); i++) {
      missingList.add(world.getBlockState(neighborList.get(i)).getBlock() instanceof CrateBlock);
    }
    return missingList;
  }

  private Pair<ArrayList<BlockPos>, ArrayList<Boolean>> updateCrateShapeHelper(ArrayList<BlockPos> neighborList, ArrayList<Boolean> missingList, int num1, int num2, int num3, int num4) {
    ArrayList<BlockPos> newNeighborList = new ArrayList<>();
    if (missingList.get(num1) && missingList.get(num2) && missingList.get(num3) && missingList.get(num4)) {
      newNeighborList.add(neighborList.get(num1));
      newNeighborList.add(neighborList.get(num2));
      newNeighborList.add(neighborList.get(num3));
      newNeighborList.add(neighborList.get(num4));
      missingList.set(num1, false);
      missingList.set(num2, false);
      missingList.set(num3, false);
      missingList.set(num4, false);
    }
    return new Pair<>(newNeighborList, missingList);
  }

  private Pair<ArrayList<BlockPos>, ArrayList<Boolean>> updateCrateShapeHelper(ArrayList<BlockPos> neighborList, ArrayList<Boolean> missingList, int num1, int num2) {
    ArrayList<BlockPos> newNeighborList = new ArrayList<>();
    if (missingList.get(num1) && missingList.get(num2)) {
      newNeighborList.add(neighborList.get(num1));
      newNeighborList.add(neighborList.get(num2));
      missingList.set(num1, false);
      missingList.set(num2, false);
    }
    return new Pair<>(newNeighborList, missingList);
  }

  private ArrayList<ArrayList<BlockPos>> updateCrateShape(ArrayList<BlockPos> neighborList, ArrayList<Boolean> missingList) {
    CrateType type = getCrateType(neighborList);
    ArrayList<ArrayList<BlockPos>> cratesList = new ArrayList<>();
    if (type == CrateType.OCTO) {
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try1 = updateCrateShapeHelper(neighborList, missingList, 0, 1, 2, 3);
      if (!try1.getFirst().isEmpty()) {
        cratesList.add(try1.getFirst());
        missingList = try1.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try2 = updateCrateShapeHelper(neighborList, missingList, 4, 5, 6, 7);
      if (!try2.getFirst().isEmpty()) {
        cratesList.add(try2.getFirst());
        missingList = try2.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try3 = updateCrateShapeHelper(neighborList, missingList, 0, 2, 4, 6);
      if (!try3.getFirst().isEmpty()) {
        cratesList.add(try3.getFirst());
        missingList = try3.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try4 = updateCrateShapeHelper(neighborList, missingList, 1, 3, 5, 7);
      if (!try4.getFirst().isEmpty()) {
        cratesList.add(try4.getFirst());
        missingList = try4.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try5 = updateCrateShapeHelper(neighborList, missingList, 0, 1, 4, 5);
      if (!try5.getFirst().isEmpty()) {
        cratesList.add(try5.getFirst());
        missingList = try5.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try6 = updateCrateShapeHelper(neighborList, missingList, 2, 3, 6, 7);
      if (!try6.getFirst().isEmpty()) {
        cratesList.add(try6.getFirst());
        missingList = try6.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try7 = updateCrateShapeHelper(neighborList, missingList, 0, 1);
      if (!try7.getFirst().isEmpty()) {
        cratesList.add(try7.getFirst());
        missingList = try7.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try8 = updateCrateShapeHelper(neighborList, missingList, 2, 3);
      if (!try8.getFirst().isEmpty()) {
        cratesList.add(try8.getFirst());
        missingList = try8.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try9 = updateCrateShapeHelper(neighborList, missingList, 4, 5);
      if (!try9.getFirst().isEmpty()) {
        cratesList.add(try9.getFirst());
        missingList = try9.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try10 = updateCrateShapeHelper(neighborList, missingList, 6, 7);
      if (!try10.getFirst().isEmpty()) {
        cratesList.add(try10.getFirst());
        missingList = try10.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try11 = updateCrateShapeHelper(neighborList, missingList, 0, 2);
      if (!try11.getFirst().isEmpty()) {
        cratesList.add(try11.getFirst());
        missingList = try11.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try12 = updateCrateShapeHelper(neighborList, missingList, 1, 3);
      if (!try12.getFirst().isEmpty()) {
        cratesList.add(try12.getFirst());
        missingList = try12.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try13 = updateCrateShapeHelper(neighborList, missingList, 4, 6);
      if (!try13.getFirst().isEmpty()) {
        cratesList.add(try13.getFirst());
        missingList = try13.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try14 = updateCrateShapeHelper(neighborList, missingList, 5, 7);
      if (!try14.getFirst().isEmpty()) {
        cratesList.add(try14.getFirst());
        missingList = try14.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try15 = updateCrateShapeHelper(neighborList, missingList, 0, 4);
      if (!try15.getFirst().isEmpty()) {
        cratesList.add(try15.getFirst());
        missingList = try15.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try16 = updateCrateShapeHelper(neighborList, missingList, 1, 5);
      if (!try16.getFirst().isEmpty()) {
        cratesList.add(try16.getFirst());
        missingList = try16.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try17 = updateCrateShapeHelper(neighborList, missingList, 2, 6);
      if (!try17.getFirst().isEmpty()) {
        cratesList.add(try17.getFirst());
        missingList = try17.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try18 = updateCrateShapeHelper(neighborList, missingList, 3, 7);
      if (!try18.getFirst().isEmpty()) {
        cratesList.add(try18.getFirst());
        missingList = try18.getSecond();
      }
    }
    if (type == CrateType.QUAD_X || type == CrateType.QUAD_Y || type == CrateType.QUAD_Z) {
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try1 = updateCrateShapeHelper(neighborList, missingList, 0, 1);
      if (!try1.getFirst().isEmpty()) {
        cratesList.add(try1.getFirst());
        missingList = try1.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try2 = updateCrateShapeHelper(neighborList, missingList, 2, 3);
      if (!try2.getFirst().isEmpty()) {
        cratesList.add(try2.getFirst());
        missingList = try2.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try3 = updateCrateShapeHelper(neighborList, missingList, 0, 2);
      if (!try3.getFirst().isEmpty()) {
        cratesList.add(try3.getFirst());
        missingList = try3.getSecond();
      }
      Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try4 = updateCrateShapeHelper(neighborList, missingList, 1, 3);
      if (!try4.getFirst().isEmpty()) {
        cratesList.add(try4.getFirst());
        missingList = try4.getSecond();
      }
    }
    for (int i = 0; i < missingList.size(); i++) {
      if (missingList.get(i)) {
        ArrayList<BlockPos> singleCrate = new ArrayList<>();
        singleCrate.add(neighborList.get(i));
        cratesList.add(singleCrate);
      }
    }
    return cratesList;
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
    World world = (World) worldIn;
    ArrayList<BlockPos> neighborList = getBlockPositions(world, currentPos);
    ArrayList<Boolean> missingList = checkMissingCrates(world, neighborList);
    if (missingList.contains(false)) {
      // Block is missing.
      ArrayList<ArrayList<BlockPos>> cratesList = updateCrateShape(neighborList, missingList);
      for (ArrayList<BlockPos> blockPos : cratesList) {
        ArrayList<CrateDataCarrier> cardinalData = calculateSides(blockPos);
        for (int j = 0; j < cardinalData.size(); j++) {
          boolean rotate = rotateCrate(blockPos.get(0));
          CrateIndex index = CrateIndex.matchCrateIndex(getCrateType(blockPos), j + 1, cardinalData.get(j).isNorth(), cardinalData.get(j).isSouth(), cardinalData.get(j).isEast(), cardinalData.get(j).isWest(), cardinalData.get(j).isUp(), cardinalData.get(j).isDown());
          world.setBlockState(blockPos.get(j), this.getDefaultState().with(INDEX, index).with(ROTATED, rotate));
          if (currentPos == blockPos.get(j)) {
            return this.getDefaultState().with(INDEX, index).with(ROTATED, rotate).with(ROTATED, rotate);
          }
        }
      }
    }
    // Block is not missing.
    return stateIn;
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
    if (placer != null) {
      if (!placer.getEntity().isSneaking()) {
        ArrayList<BlockPos> positionList = checkForCrates(world, pos);
        boolean rotate = rotateCrate(positionList.get(0));
        ArrayList<CrateDataCarrier> cardinalList = calculateSides(positionList);
        for (int i = 0; i < positionList.size(); i++) {
          CrateIndex index = CrateIndex.matchCrateIndex(getCrateType(positionList), i + 1, cardinalList.get(i).isNorth(), cardinalList.get(i).isSouth(), cardinalList.get(i).isEast(), cardinalList.get(i).isWest(), cardinalList.get(i).isUp(), cardinalList.get(i).isDown());
          world.setBlockState(positionList.get(i), state.with(INDEX, index).with(ROTATED, rotate));
        }
      }
    }
    if (stack.hasDisplayName()) {
      TileEntity tileentity = world.getTileEntity(pos);
      //if (tileentity instanceof CrateTileEntity) {
        // TODO:
        //((CrateTileEntity) tileentity).setCustomName(stack.getDisplayName());
      //}
    }

  }

  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    boolean rotate = rotateCrate(context.getPos());
    return this.getDefaultState().with(ROTATED, rotate);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    if (worldIn == null) return;
    if (!Screen.hasShiftDown()) {
      tooltip.add(new TranslationTextComponent("block.druidcraft.hold_shift").setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true)));
    } else {
      tooltip.add(new TranslationTextComponent("block.druidcraft.crate.description1").setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true)));
    }
  }
}