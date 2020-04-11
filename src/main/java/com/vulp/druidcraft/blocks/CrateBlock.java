package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.api.CrateType;
import com.vulp.druidcraft.blocks.tileentities.CrateTileEntity;
import com.vulp.druidcraft.inventory.OctoSidedInventory;
import com.vulp.druidcraft.inventory.QuadSidedInventory;
import com.vulp.druidcraft.inventory.container.CrateContainer;
import com.vulp.druidcraft.registry.ItemRegistry;
import javafx.util.Pair;
import net.minecraft.block.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrateBlock extends ContainerBlock {
    public static final BooleanProperty PROPERTY_OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty SHIFT_PLACED = BooleanProperty.create("shift_placed");
    public static final EnumProperty TYPE = EnumProperty.create("type", CrateType.class);
    public static final IntegerProperty POS_NUM = IntegerProperty.create("pos_num", 1, 8);
    public static final BooleanProperty PARENT = BooleanProperty.create("parent");
    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    private static final CrateBlock.InventoryFactory<IInventory> inventoryFactory = new CrateBlock.InventoryFactory<IInventory>() {
        public IInventory forOcto(CrateTileEntity inv1, CrateTileEntity inv2, CrateTileEntity inv3, CrateTileEntity inv4, CrateTileEntity inv5, CrateTileEntity inv6, CrateTileEntity inv7, CrateTileEntity inv8) {
            return new OctoSidedInventory(inv1, inv2, inv3, inv4, inv5, inv6, inv7, inv8);
        }

        public IInventory forQuad(CrateTileEntity inv1, CrateTileEntity inv2, CrateTileEntity inv3, CrateTileEntity inv4) {
            return new QuadSidedInventory(inv1, inv2, inv3, inv4);
        }

        public IInventory forDouble(CrateTileEntity inv1, CrateTileEntity inv2) {
            return new DoubleSidedInventory(inv1, inv2);
        }

        public IInventory forSingle(CrateTileEntity tileEntity) {
            return tileEntity;
        }
    };
    private static final CrateBlock.InventoryFactory<INamedContainerProvider> guiFactory = new CrateBlock.InventoryFactory<INamedContainerProvider>() {


        public INamedContainerProvider forOcto(final CrateTileEntity tileEntity1, final CrateTileEntity tileEntity2, final CrateTileEntity tileEntity3, final CrateTileEntity tileEntity4, final CrateTileEntity tileEntity5, final CrateTileEntity tileEntity6, final CrateTileEntity tileEntity7, final CrateTileEntity tileEntity8) {
            final IInventory iinventory = new OctoSidedInventory(tileEntity1, tileEntity2, tileEntity3, tileEntity4, tileEntity5, tileEntity6, tileEntity7, tileEntity8);
            return new INamedContainerProvider() {
                @Nullable
                public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                    if (tileEntity1.canOpen(p_createMenu_3_) && tileEntity2.canOpen(p_createMenu_3_)) {
                        tileEntity1.fillWithLoot(p_createMenu_2_.player);
                        tileEntity2.fillWithLoot(p_createMenu_2_.player);
                        return CrateContainer.createGeneric9X24(p_createMenu_1_, p_createMenu_2_, iinventory);
                    } else {
                        return null;
                    }
                }

                public ITextComponent getDisplayName() {
                    if (tileEntity1.hasCustomName()) {
                        return tileEntity1.getDisplayName();
                    } else {
                        return (ITextComponent)(tileEntity2.hasCustomName() ? tileEntity2.getDisplayName() : new TranslationTextComponent("container.crateOcto"));
                    }
                }
            };
        }

        public INamedContainerProvider forQuad(final CrateTileEntity tileEntity1, final CrateTileEntity tileEntity2, final CrateTileEntity tileEntity3, final CrateTileEntity tileEntity4) {
            final IInventory iinventory = new QuadSidedInventory(tileEntity1, tileEntity2, tileEntity3, tileEntity4);
            return new INamedContainerProvider() {
                @Nullable
                public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                    if (tileEntity1.canOpen(p_createMenu_3_) && tileEntity2.canOpen(p_createMenu_3_)) {
                        tileEntity1.fillWithLoot(p_createMenu_2_.player);
                        tileEntity2.fillWithLoot(p_createMenu_2_.player);
                        return CrateContainer.createGeneric9X12(p_createMenu_1_, p_createMenu_2_, iinventory);
                    } else {
                        return null;
                    }
                }

                public ITextComponent getDisplayName() {
                    if (tileEntity1.hasCustomName()) {
                        return tileEntity1.getDisplayName();
                    } else {
                        return (ITextComponent)(tileEntity2.hasCustomName() ? tileEntity2.getDisplayName() : new TranslationTextComponent("container.crateQuad"));
                    }
                }
            };
        }

        public INamedContainerProvider forDouble(final CrateTileEntity tileEntity1, final CrateTileEntity tileEntity2) {
            final IInventory iinventory = new DoubleSidedInventory(tileEntity1, tileEntity2);
            return new INamedContainerProvider() {
                @Nullable
                public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                    if (tileEntity1.canOpen(p_createMenu_3_) && tileEntity2.canOpen(p_createMenu_3_)) {
                        tileEntity1.fillWithLoot(p_createMenu_2_.player);
                        tileEntity2.fillWithLoot(p_createMenu_2_.player);
                        return ChestContainer.createGeneric9X6(p_createMenu_1_, p_createMenu_2_, iinventory);
                    } else {
                        return null;
                    }
                }

                public ITextComponent getDisplayName() {
                    if (tileEntity1.hasCustomName()) {
                        return tileEntity1.getDisplayName();
                    } else {
                        return (ITextComponent)(tileEntity2.hasCustomName() ? tileEntity2.getDisplayName() : new TranslationTextComponent("container.crateDouble"));
                    }
                }
            };
        }

        public INamedContainerProvider forSingle(CrateTileEntity p_212856_1_) {
            return p_212856_1_;
        }
    };

    public CrateBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState()
                .with(TYPE, CrateType.SINGLE)
                .with(POS_NUM, 1)
                .with(PROPERTY_OPEN, false)
                .with(PARENT, true)
                .with(ROTATED, false)
                .with(NORTH, true)
                .with(EAST, true)
                .with(SOUTH, true)
                .with(WEST, true)
                .with(UP, true)
                .with(DOWN, true)
                .with(SHIFT_PLACED, true));
    }

    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        Item item = itemstack.getItem();
        if (item == ItemRegistry.crate) {
            return false;
        }
        if (worldIn.isRemote) {
            return true;
        } else {
            INamedContainerProvider inamedcontainerprovider = this.getContainer(state, worldIn, pos);
            if (inamedcontainerprovider != null) {
                player.openContainer(inamedcontainerprovider);
            }
            return true;
        }
    }

    @Nullable
    public ArrayList<BlockPos> checkForCrates(World world, BlockPos pos) {

        ArrayList<BlockPos> shapeConfig = new ArrayList<>();

        // OCTO
        if (checkCrateBlocks(world, pos.east(), pos.south(), pos.south().east(), pos.up(), pos.up().east(), pos.up().south(), pos.up().south().east())) {
            shapeConfig.clear();
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

    public static ArrayList<BlockPos> getBlockPositions(World world, BlockPos pos) {
        ArrayList<BlockPos> config = new ArrayList<>();
        CrateType type = CrateType.SINGLE;
        int num = world.getBlockState(pos).get(POS_NUM);

        if (world.getBlockState(pos).get(TYPE) == CrateType.DOUBLE_X) {
            type = CrateType.DOUBLE_X;
        }
        if (world.getBlockState(pos).get(TYPE) == CrateType.DOUBLE_Y) {
            type = CrateType.DOUBLE_Y;
        }
        if (world.getBlockState(pos).get(TYPE) == CrateType.DOUBLE_Z) {
            type = CrateType.DOUBLE_Z;
        }
        if (world.getBlockState(pos).get(TYPE) == CrateType.QUAD_X) {
            type = CrateType.QUAD_X;
        }
        if (world.getBlockState(pos).get(TYPE) == CrateType.QUAD_Y) {
            type = CrateType.QUAD_Y;
        }
        if (world.getBlockState(pos).get(TYPE) == CrateType.QUAD_Z) {
            type = CrateType.QUAD_Z;
        }
        if (world.getBlockState(pos).get(TYPE) == CrateType.OCTO) {
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

    public static boolean checkBlockPositions(World world, BlockPos pos, CrateType type, ArrayList<BlockPos> posList) {
        int num = world.getBlockState(pos).get(POS_NUM);

        for (int i = 1; i > posList.size() + 1; i++) {
            if (type == CrateType.OCTO) {
                if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(POS_NUM) == i && world.getBlockState(posList.get(i)).get(TYPE) == CrateType.OCTO)) {
                    return false;
                }
            }
            if (type == CrateType.QUAD_X) {
                if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(POS_NUM) == i && world.getBlockState(posList.get(i)).get(TYPE) == CrateType.QUAD_X)) {
                    return false;
                }
            }
            if (type == CrateType.QUAD_Y) {
                if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(POS_NUM) == i && world.getBlockState(posList.get(i)).get(TYPE) == CrateType.QUAD_Y)) {
                    return false;
                }
            }
            if (type == CrateType.QUAD_Z) {
                if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(POS_NUM) == i && world.getBlockState(posList.get(i)).get(TYPE) == CrateType.QUAD_Z)) {
                    return false;
                }
            }
            if (type == CrateType.DOUBLE_X) {
                if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(POS_NUM) == i && world.getBlockState(posList.get(i)).get(TYPE) == CrateType.DOUBLE_X)) {
                    return false;
                }
            }
            if (type == CrateType.DOUBLE_Y) {
                if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(POS_NUM) == i && world.getBlockState(posList.get(i)).get(TYPE) == CrateType.DOUBLE_Y)) {
                    return false;
                }
            }
            if (type == CrateType.DOUBLE_Z) {
                if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(POS_NUM) == i && world.getBlockState(posList.get(i)).get(TYPE) == CrateType.DOUBLE_Z)) {
                    return false;
                }
            }
            if (type == CrateType.SINGLE) {
                if (!(world.getBlockState(posList.get(i)).getBlock() instanceof CrateBlock && world.getBlockState(posList.get(i)).get(POS_NUM) == i && world.getBlockState(posList.get(i)).get(TYPE) == CrateType.SINGLE)) {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean checkIllegalCrates(World world, BlockPos pos, CrateType type, ArrayList<BlockPos> blockPosList) {
        if (type == CrateType.DOUBLE_X || type == CrateType.DOUBLE_Y || type == CrateType.DOUBLE_Z) {
            for (int i = 0; i < blockPosList.size(); i++) {
                if (blockPosList.get(i) != pos) {
                    if (world.getBlockState(blockPosList.get(i)).get(TYPE) != CrateType.SINGLE) {
                        return false;
                    }
                }
            }
        }
        if (type == CrateType.OCTO) {
            for (int i = 0; i < blockPosList.size(); i++) {
                if (blockPosList.get(i) != pos) {
                    if (world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.OCTO) {
                        return false;
                    }
                }
            }
        }
        if (type == CrateType.QUAD_X) {
            for (int i = 0; i < blockPosList.size(); i++) {
                if (blockPosList.get(i) != pos) {
                    if ((world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.DOUBLE_X || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.QUAD_X || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.QUAD_Y || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.QUAD_Z || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.OCTO)) {
                        return false;
                    }
                }
            }
        }
        if (type == CrateType.QUAD_Y) {
            for (int i = 0; i < blockPosList.size(); i++) {
                if (blockPosList.get(i) != pos) {
                    if ((world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.DOUBLE_Y || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.QUAD_X || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.QUAD_Y || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.QUAD_Z || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.OCTO)) {
                        return false;
                    }
                }
            }
        }
        if (type == CrateType.QUAD_Z) {
            for (int i = 0; i < blockPosList.size(); i++) {
                if (blockPosList.get(i) != pos) {
                    if ((world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.DOUBLE_Z || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.QUAD_X || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.QUAD_Y || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.QUAD_Z || world.getBlockState(blockPosList.get(i)).get(TYPE) == CrateType.OCTO)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkSides(World world, ArrayList<BlockPos> blockPosList) {
        for (int i = 0; i < blockPosList.size(); i++) {
            CrateType type = getCrateType(blockPosList);
            int num = i + 1;
            // OCTO
            if (type == CrateType.OCTO && num == 1)
                if (!world.getBlockState(blockPosList.get(i)).get(SOUTH) || !world.getBlockState(blockPosList.get(i)).get(EAST) || !world.getBlockState(blockPosList.get(i)).get(UP))
                    return false;
            if (type == CrateType.OCTO && num == 2)
                if (!world.getBlockState(blockPosList.get(i)).get(SOUTH) || !world.getBlockState(blockPosList.get(i)).get(WEST) || !world.getBlockState(blockPosList.get(i)).get(UP))
                    return false;
            if (type == CrateType.OCTO && num == 3)
                if (!world.getBlockState(blockPosList.get(i)).get(NORTH) || !world.getBlockState(blockPosList.get(i)).get(EAST) || !world.getBlockState(blockPosList.get(i)).get(UP))
                    return false;
            if (type == CrateType.OCTO && num == 4)
                if (!world.getBlockState(blockPosList.get(i)).get(NORTH) || !world.getBlockState(blockPosList.get(i)).get(WEST) || !world.getBlockState(blockPosList.get(i)).get(UP))
                    return false;
            if (type == CrateType.OCTO && num == 5)
                if (!world.getBlockState(blockPosList.get(i)).get(SOUTH) || !world.getBlockState(blockPosList.get(i)).get(EAST) || !world.getBlockState(blockPosList.get(i)).get(DOWN))
                    return false;
            if (type == CrateType.OCTO && num == 6)
                if (!world.getBlockState(blockPosList.get(i)).get(SOUTH) || !world.getBlockState(blockPosList.get(i)).get(WEST) || !world.getBlockState(blockPosList.get(i)).get(DOWN))
                    return false;
            if (type == CrateType.OCTO && num == 7)
                if (!world.getBlockState(blockPosList.get(i)).get(NORTH) || !world.getBlockState(blockPosList.get(i)).get(EAST) || !world.getBlockState(blockPosList.get(i)).get(DOWN))
                    return false;
            if (type == CrateType.OCTO && num == 8)
                if (!world.getBlockState(blockPosList.get(i)).get(NORTH) || !world.getBlockState(blockPosList.get(i)).get(WEST) || !world.getBlockState(blockPosList.get(i)).get(DOWN))
                    return false;
            // QUAD X
            if (type == CrateType.QUAD_X && num == 1)
                if (!world.getBlockState(blockPosList.get(i)).get(SOUTH) || !world.getBlockState(blockPosList.get(i)).get(UP))
                    return false;
            if (type == CrateType.QUAD_X && num == 2)
                if (!world.getBlockState(blockPosList.get(i)).get(NORTH) || !world.getBlockState(blockPosList.get(i)).get(UP))
                    return false;
            if (type == CrateType.QUAD_X && num == 3)
                if (!world.getBlockState(blockPosList.get(i)).get(SOUTH) || !world.getBlockState(blockPosList.get(i)).get(DOWN))
                    return false;
            if (type == CrateType.QUAD_X && num == 4)
                if (!world.getBlockState(blockPosList.get(i)).get(NORTH) || !world.getBlockState(blockPosList.get(i)).get(DOWN))
                    return false;
            // QUAD Y
            if (type == CrateType.QUAD_Y && num == 1)
                if (!world.getBlockState(blockPosList.get(i)).get(SOUTH) || !world.getBlockState(blockPosList.get(i)).get(EAST))
                    return false;
            if (type == CrateType.QUAD_Y && num == 2)
                if (!world.getBlockState(blockPosList.get(i)).get(SOUTH) || !world.getBlockState(blockPosList.get(i)).get(WEST))
                    return false;
            if (type == CrateType.QUAD_Y && num == 3)
                if (!world.getBlockState(blockPosList.get(i)).get(NORTH) || !world.getBlockState(blockPosList.get(i)).get(EAST))
                    return false;
            if (type == CrateType.QUAD_Y && num == 4)
                if (!world.getBlockState(blockPosList.get(i)).get(NORTH) || !world.getBlockState(blockPosList.get(i)).get(WEST))
                    return false;
            // QUAD Z
            if (type == CrateType.QUAD_Z && num == 1)
                if (!world.getBlockState(blockPosList.get(i)).get(UP) || !world.getBlockState(blockPosList.get(i)).get(EAST))
                    return false;
            if (type == CrateType.QUAD_Z && num == 2)
                if (!world.getBlockState(blockPosList.get(i)).get(UP) || !world.getBlockState(blockPosList.get(i)).get(WEST))
                    return false;
            if (type == CrateType.QUAD_Z && num == 3)
                if (!world.getBlockState(blockPosList.get(i)).get(DOWN) || !world.getBlockState(blockPosList.get(i)).get(EAST))
                    return false;
            if (type == CrateType.QUAD_Z && num == 4)
                if (!world.getBlockState(blockPosList.get(i)).get(DOWN) || !world.getBlockState(blockPosList.get(i)).get(WEST))
                    return false;
            // DOUBLE X
            if (type == CrateType.DOUBLE_X && num == 1)
                if (!world.getBlockState(blockPosList.get(i)).get(EAST))
                    return false;
            if (type == CrateType.DOUBLE_X && num == 2)
                if (!world.getBlockState(blockPosList.get(i)).get(WEST))
                    return false;
            // DOUBLE Y
            if (type == CrateType.DOUBLE_Y && num == 1)
                if (!world.getBlockState(blockPosList.get(i)).get(UP))
                    return false;
            if (type == CrateType.DOUBLE_Y && num == 2)
                if (!world.getBlockState(blockPosList.get(i)).get(DOWN))
                    return false;
            // DOUBLE Z
            if (type == CrateType.DOUBLE_Z && num == 1)
                if (!world.getBlockState(blockPosList.get(i)).get(SOUTH))
                    return false;
            if (type == CrateType.DOUBLE_Z && num == 2)
                if (!world.getBlockState(blockPosList.get(i)).get(NORTH))
                    return false;
        }
        return true;
    }

    public boolean checkCrateBlocks(World world, BlockPos pos1) {
        if (world.getBlockState(pos1).getBlock() instanceof CrateBlock) {
            return true;
        }
        return false;
    }

    public boolean checkCrateBlocks(World world, BlockPos pos1, BlockPos pos2, BlockPos pos3) {
        if (world.getBlockState(pos1).getBlock() instanceof CrateBlock && world.getBlockState(pos2).getBlock() instanceof CrateBlock && world.getBlockState(pos3).getBlock() instanceof CrateBlock) {
            return true;
        }
        return false;
    }

    public boolean checkCrateBlocks(World world, BlockPos pos1, BlockPos pos2, BlockPos pos3, BlockPos pos4, BlockPos pos5, BlockPos pos6, BlockPos pos7) {
        if (world.getBlockState(pos1).getBlock() instanceof CrateBlock && world.getBlockState(pos2).getBlock() instanceof CrateBlock && world.getBlockState(pos3).getBlock() instanceof CrateBlock && world.getBlockState(pos4).getBlock() instanceof CrateBlock) {
            if (world.getBlockState(pos5).getBlock() instanceof CrateBlock && world.getBlockState(pos6).getBlock() instanceof CrateBlock && world.getBlockState(pos7).getBlock() instanceof CrateBlock) {
                return true;
            }
        }
        return false;
    }

    public boolean rotateCrate(BlockPos pos) {
        Random bool = new Random();
        bool.setSeed(pos.getX() + pos.getY() + pos.getZ());
        bool.nextBoolean();
        return (bool.nextBoolean());
    }

    public int getCrateNumber(BlockPos pos, ArrayList<BlockPos> blockPosList) {
        for (int i = 0; i < blockPosList.size(); i++) {
            if (blockPosList.get(i) == pos) {
                return i;
            }
        }
        return 0;
    }

    public BlockPos getCrateByNumber(int num, ArrayList<BlockPos> blockPosList) {
        return blockPosList.get(num);
    }

    public CrateType getCrateType(ArrayList<BlockPos> blockPosList) {
        switch (blockPosList.size()) {
            case 1:
                return CrateType.SINGLE;
            case 2:
                BlockPos pos1_2 = getCrateByNumber(0, blockPosList);
                BlockPos pos2_2 = getCrateByNumber(1, blockPosList);
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
                BlockPos pos1_4 = getCrateByNumber(0, blockPosList);
                BlockPos pos2_4 = getCrateByNumber(1, blockPosList);
                BlockPos pos3_4 = getCrateByNumber(2, blockPosList);
                BlockPos pos4_4 = getCrateByNumber(3, blockPosList);
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

    public ArrayList<BlockState> calculateSides(ArrayList<BlockPos> blockPosList, World world) {
        ArrayList<BlockState> blockStateList = new ArrayList<>();
        for (int i = 0; i < blockPosList.size(); i++) {
            CrateType type = getCrateType(blockPosList);
            int num = i + 1;
            // OCTO
            if (type == CrateType.OCTO && num == 1)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, false).with(EAST, true).with(SOUTH, true).with(WEST, false).with(UP, true).with(DOWN, false));
            if (type == CrateType.OCTO && num == 2)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, false).with(EAST, false).with(SOUTH, true).with(WEST, true).with(UP, true).with(DOWN, false));
            if (type == CrateType.OCTO && num == 3)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, false).with(WEST, false).with(UP, true).with(DOWN, false));
            if (type == CrateType.OCTO && num == 4)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, false).with(SOUTH, false).with(WEST, true).with(UP, true).with(DOWN, false));
            if (type == CrateType.OCTO && num == 5)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, false).with(EAST, true).with(SOUTH, true).with(WEST, false).with(UP, false).with(DOWN, true));
            if (type == CrateType.OCTO && num == 6)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, false).with(EAST, false).with(SOUTH, true).with(WEST, true).with(UP, false).with(DOWN, true));
            if (type == CrateType.OCTO && num == 7)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, true));
            if (type == CrateType.OCTO && num == 8)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, false).with(SOUTH, false).with(WEST, true).with(UP, false).with(DOWN, true));
            // QUAD X
            if (type == CrateType.QUAD_X && num == 1)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, false).with(EAST, true).with(SOUTH, true).with(WEST, true).with(UP, true).with(DOWN, false));
            if (type == CrateType.QUAD_X && num == 2)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, false).with(WEST, true).with(UP, true).with(DOWN, false));
            if (type == CrateType.QUAD_X && num == 3)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, false).with(EAST, true).with(SOUTH, true).with(WEST, true).with(UP, false).with(DOWN, true));
            if (type == CrateType.QUAD_X && num == 4)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, false).with(WEST, true).with(UP, false).with(DOWN, true));
            // QUAD Y
            if (type == CrateType.QUAD_Y && num == 1)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, false).with(EAST, true).with(SOUTH, true).with(WEST, false).with(UP, true).with(DOWN, true));
            if (type == CrateType.QUAD_Y && num == 2)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, false).with(EAST, false).with(SOUTH, true).with(WEST, true).with(UP, true).with(DOWN, true));
            if (type == CrateType.QUAD_Y && num == 3)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, false).with(WEST, false).with(UP, true).with(DOWN, true));
            if (type == CrateType.QUAD_Y && num == 4)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, false).with(SOUTH, false).with(WEST, true).with(UP, true).with(DOWN, true));
            // QUAD Z
            if (type == CrateType.QUAD_Z && num == 1)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, true).with(WEST, false).with(UP, true).with(DOWN, false));
            if (type == CrateType.QUAD_Z && num == 2)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, false).with(SOUTH, true).with(WEST, true).with(UP, true).with(DOWN, false));
            if (type == CrateType.QUAD_Z && num == 3)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, true).with(WEST, false).with(UP, false).with(DOWN, true));
            if (type == CrateType.QUAD_Z && num == 4)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, false).with(SOUTH, true).with(WEST, true).with(UP, false).with(DOWN, true));
            // DOUBLE X
            if (type == CrateType.DOUBLE_X && num == 1)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, true).with(WEST, false).with(UP, true).with(DOWN, true));
            if (type == CrateType.DOUBLE_X && num == 2)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, false).with(SOUTH, true).with(WEST, true).with(UP, true).with(DOWN, true));
            // DOUBLE Y
            if (type == CrateType.DOUBLE_Y && num == 1)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, true).with(WEST, true).with(UP, true).with(DOWN, false));
            if (type == CrateType.DOUBLE_Y && num == 2)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, true).with(WEST, true).with(UP, false).with(DOWN, true));
            // DOUBLE Z
            if (type == CrateType.DOUBLE_Z && num == 1)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, false).with(EAST, true).with(SOUTH, true).with(WEST, true).with(UP, true).with(DOWN, true));
            if (type == CrateType.DOUBLE_Z && num == 2)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, false).with(WEST, true).with(UP, true).with(DOWN, true));
            // SINGLE
            if (type == CrateType.SINGLE)
                blockStateList.add(world.getBlockState(blockPosList.get(i)).with(NORTH, true).with(EAST, true).with(SOUTH, true).with(WEST, true).with(UP, true).with(DOWN, true));
        }
        return blockStateList;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, PROPERTY_OPEN, PARENT, ROTATED, POS_NUM, NORTH, EAST, SOUTH, WEST, UP, DOWN, SHIFT_PLACED);
    }

    // DO THIS AND REMEMBER TO KEEP MATCHING CHESTBLOCK CODE TO THIS CLASS.

    @Nullable
    public static <T> T getCrateInventory(BlockState state, IWorld world, BlockPos pos, boolean allowBlocked, CrateBlock.InventoryFactory<T> factory) {

        // \/ REWRITE \/ (Remember the new code I created.)

        TileEntity tileentity = world.getTileEntity(pos);
        if (!(tileentity instanceof CrateTileEntity)) {
            return (T)null;
        } else {
            CrateTileEntity crateTileEntity = (CrateTileEntity) tileentity;
            ArrayList<BlockPos> blockList = getBlockPositions((World) world, pos);
            if (blockList.size() == 1) {
                return factory.forSingle(crateTileEntity);
            }
            if (blockList.size() == 2) {
                return factory.forDouble((CrateTileEntity) world.getTileEntity(blockList.get(0)), (CrateTileEntity) world.getTileEntity(blockList.get(1)));
            }
            if (blockList.size() == 4) {
                return factory.forQuad((CrateTileEntity) world.getTileEntity(blockList.get(0)), (CrateTileEntity) world.getTileEntity(blockList.get(1)), (CrateTileEntity) world.getTileEntity(blockList.get(2)), (CrateTileEntity) world.getTileEntity(blockList.get(3)));
            }
            if (blockList.size() == 8) {
                return factory.forOcto((CrateTileEntity) world.getTileEntity(blockList.get(0)), (CrateTileEntity) world.getTileEntity(blockList.get(1)), (CrateTileEntity) world.getTileEntity(blockList.get(2)), (CrateTileEntity) world.getTileEntity(blockList.get(3)), (CrateTileEntity) world.getTileEntity(blockList.get(4)), (CrateTileEntity) world.getTileEntity(blockList.get(5)), (CrateTileEntity) world.getTileEntity(blockList.get(6)), (CrateTileEntity) world.getTileEntity(blockList.get(7)));
            }
        }
        return factory.forSingle((CrateTileEntity)tileentity);
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof IInventory) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }

    }

    @Nullable
    public static IInventory getInventory(BlockState state, World world, BlockPos pos, boolean allowBlocked) {
        return getCrateInventory(state, world, pos, allowBlocked, inventoryFactory);
    }

    @Nullable
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        return getCrateInventory(state, worldIn, pos, false, guiFactory);
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new CrateTileEntity();
    }

    private static boolean isBlocked(IWorld world, BlockPos pos) {
        return isCatSittingOn(world, pos);
    }

    private static boolean isCatSittingOn(IWorld world, BlockPos pos) {
        List<CatEntity> list = world.getEntitiesWithinAABB(CatEntity.class, new AxisAlignedBB((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 2), (double)(pos.getZ() + 1)));
        if (!list.isEmpty()) {
            for(CatEntity catentity : list) {
                if (catentity.isSitting()) {
                    return true;
                }
            }
        }

        return false;
    }

    /** @deprecated */
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    /** @deprecated */
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    /** @deprecated */
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstoneFromInventory(getInventory(blockState, worldIn, pos, false));
    }

    protected boolean canPlace(BlockItemUseContext p_195944_1_, BlockState p_195944_2_) {
        PlayerEntity playerentity = p_195944_1_.getPlayer();
        ISelectionContext iselectioncontext = playerentity == null ? ISelectionContext.dummy() : ISelectionContext.forEntity(playerentity);
        return (!this.checkPosition() || p_195944_2_.isValidPosition(p_195944_1_.getWorld(), p_195944_1_.getPos())) && p_195944_1_.getWorld().func_217350_a(p_195944_2_, p_195944_1_.getPos(), iselectioncontext);
    }

    protected boolean checkPosition() {
        return true;
    }

    private TileEntity getTileEntity(World world, BlockPos pos) {
        return world.getTileEntity(pos);
    }

    private ArrayList<BlockPos> getNeighborsFromTE(TileEntity tileEntity) {
        return ((CrateTileEntity) tileEntity).getNeighbors();
    }

    public ArrayList<Boolean> checkMissingCrates(World world, BlockPos pos, ArrayList<BlockPos> neighborList) {
        ArrayList<Boolean> missingList = new ArrayList<>();
        for (int i = 0; i < neighborList.size(); i++) {
            missingList.add(world.getBlockState(neighborList.get(i)).getBlock() instanceof CrateBlock);
        }
        // Druidcraft.LOGGER.debug(missingList);
        return missingList;
    }

    public Pair<ArrayList<BlockPos>, ArrayList<Boolean>> updateCrateShapeHelper(ArrayList<BlockPos> neighborList, ArrayList<Boolean> missingList, int num1, int num2, int num3, int num4) {
        ArrayList<BlockPos> newNeighborList = new ArrayList<>();
        ArrayList<Boolean> newMissingList = missingList;
        if (missingList.get(num1) && missingList.get(num2) && missingList.get(num3) && missingList.get(num4)) {
            newNeighborList.add(neighborList.get(num1));
            newNeighborList.add(neighborList.get(num2));
            newNeighborList.add(neighborList.get(num3));
            newNeighborList.add(neighborList.get(num4));
            newMissingList.set(num1, false);
            newMissingList.set(num2, false);
            newMissingList.set(num3, false);
            newMissingList.set(num4, false);
        }
        return new Pair<ArrayList<BlockPos>, ArrayList<Boolean>>(newNeighborList, newMissingList);
    }

    public Pair<ArrayList<BlockPos>, ArrayList<Boolean>> updateCrateShapeHelper(ArrayList<BlockPos> neighborList, ArrayList<Boolean> missingList, int num1, int num2) {
        ArrayList<BlockPos> newNeighborList = new ArrayList<>();
        ArrayList<Boolean> newMissingList = missingList;
        if (missingList.get(num1) && missingList.get(num2)) {
            newNeighborList.add(neighborList.get(num1));
            newNeighborList.add(neighborList.get(num2));
            newMissingList.set(num1, false);
            newMissingList.set(num2, false);
        }
        return new Pair<ArrayList<BlockPos>, ArrayList<Boolean>>(newNeighborList, newMissingList);
    }

    public ArrayList<ArrayList<BlockPos>> updateCrateShape(World world, BlockState state, BlockPos pos, ArrayList<BlockPos> neighborList, ArrayList<Boolean> missingList) {
        CrateType type = getCrateType(neighborList);
        ArrayList<ArrayList<BlockPos>> cratesList = new ArrayList<>();
        if (type == CrateType.OCTO) {
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try1 = updateCrateShapeHelper(neighborList, missingList, 0, 1, 2, 3);
            if (!try1.getKey().isEmpty()) {
                cratesList.add(try1.getKey());
                missingList = try1.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try2 = updateCrateShapeHelper(neighborList, missingList, 4, 5, 6, 7);
            if (!try2.getKey().isEmpty()) {
                cratesList.add(try2.getKey());
                missingList = try2.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try3 = updateCrateShapeHelper(neighborList, missingList, 0, 2, 4, 6);
            if (!try3.getKey().isEmpty()) {
                cratesList.add(try3.getKey());
                missingList = try3.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try4 = updateCrateShapeHelper(neighborList, missingList, 1, 3, 5, 7);
            if (!try4.getKey().isEmpty()) {
                cratesList.add(try4.getKey());
                missingList = try4.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try5 = updateCrateShapeHelper(neighborList, missingList, 0, 1, 4, 5);
            if (!try5.getKey().isEmpty()) {
                cratesList.add(try5.getKey());
                missingList = try5.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try6 = updateCrateShapeHelper(neighborList, missingList, 2, 3, 6, 7);
            if (!try6.getKey().isEmpty()) {
                cratesList.add(try6.getKey());
                missingList = try6.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try7 = updateCrateShapeHelper(neighborList, missingList, 0, 1);
            if (!try7.getKey().isEmpty()) {
                cratesList.add(try7.getKey());
                missingList = try7.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try8 = updateCrateShapeHelper(neighborList, missingList, 2, 3);
            if (!try8.getKey().isEmpty()) {
                cratesList.add(try8.getKey());
                missingList = try8.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try9 = updateCrateShapeHelper(neighborList, missingList, 4, 5);
            if (!try9.getKey().isEmpty()) {
                cratesList.add(try9.getKey());
                missingList = try9.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try10 = updateCrateShapeHelper(neighborList, missingList, 6, 7);
            if (!try10.getKey().isEmpty()) {
                cratesList.add(try10.getKey());
                missingList = try10.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try11 = updateCrateShapeHelper(neighborList, missingList, 0, 2);
            if (!try11.getKey().isEmpty()) {
                cratesList.add(try11.getKey());
                missingList = try11.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try12 = updateCrateShapeHelper(neighborList, missingList, 1, 3);
            if (!try12.getKey().isEmpty()) {
                cratesList.add(try12.getKey());
                missingList = try12.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try13 = updateCrateShapeHelper(neighborList, missingList, 4, 6);
            if (!try13.getKey().isEmpty()) {
                cratesList.add(try13.getKey());
                missingList = try13.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try14 = updateCrateShapeHelper(neighborList, missingList, 5, 7);
            if (!try14.getKey().isEmpty()) {
                cratesList.add(try14.getKey());
                missingList = try14.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try15 = updateCrateShapeHelper(neighborList, missingList, 0, 4);
            if (!try15.getKey().isEmpty()) {
                cratesList.add(try15.getKey());
                missingList = try15.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try16 = updateCrateShapeHelper(neighborList, missingList, 1, 5);
            if (!try16.getKey().isEmpty()) {
                cratesList.add(try16.getKey());
                missingList = try16.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try17 = updateCrateShapeHelper(neighborList, missingList, 2, 6);
            if (!try17.getKey().isEmpty()) {
                cratesList.add(try17.getKey());
                missingList = try17.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try18 = updateCrateShapeHelper(neighborList, missingList, 3, 7);
            if (!try18.getKey().isEmpty()) {
                cratesList.add(try18.getKey());
                missingList = try18.getValue();
            }
        }
        if (type == CrateType.QUAD_X || type == CrateType.QUAD_Y || type == CrateType.QUAD_Z) {
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try1 = updateCrateShapeHelper(neighborList, missingList, 0, 1);
            if (!try1.getKey().isEmpty()) {
                cratesList.add(try1.getKey());
                missingList = try1.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try2 = updateCrateShapeHelper(neighborList, missingList, 2, 3);
            if (!try2.getKey().isEmpty()) {
                cratesList.add(try2.getKey());
                missingList = try2.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try3 = updateCrateShapeHelper(neighborList, missingList, 0, 2);
            if (!try3.getKey().isEmpty()) {
                cratesList.add(try3.getKey());
                missingList = try3.getValue();
            }
            Pair<ArrayList<BlockPos>, ArrayList<Boolean>> try4 = updateCrateShapeHelper(neighborList, missingList, 1, 3);
            if (!try4.getKey().isEmpty()) {
                cratesList.add(try4.getKey());
                missingList = try4.getValue();
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

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        World world = (World) worldIn;
        ArrayList<BlockPos> neighborList = getBlockPositions(world, currentPos);
        ArrayList<Boolean> missingList = checkMissingCrates(world, currentPos, neighborList);
        if (missingList.contains(false)) {
            // Block is missing.
            ArrayList<ArrayList<BlockPos>> cratesList = updateCrateShape(world, stateIn, currentPos, neighborList, missingList);
            for (int i = 0; i < cratesList.size(); i++) {
                ArrayList<BlockState> newStateList = calculateSides(cratesList.get(i), world);
                newStateList.set(0, newStateList.get(0).with(PARENT, true));
                for (int j = 0; j < newStateList.size(); j++) {
                    boolean rotate = rotateCrate(cratesList.get(i).get(0));
                    world.setBlockState(cratesList.get(i).get(j), newStateList.get(j).with(ROTATED, rotate).with(POS_NUM, j + 1).with(TYPE, getCrateType(cratesList.get(i))).with(PARENT, (j == 0)));
                    if (currentPos == cratesList.get(i).get(j)) {
                        return newStateList.get(j).with(ROTATED, rotate).with(POS_NUM, j + 1).with(TYPE, getCrateType(cratesList.get(i))).with(PARENT, (j == 0));
                    }
                }
            }
        }
        // Block is not missing.
        return stateIn;
    }

/*    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!state.get(SHIFT_PLACED)) {
            ArrayList<BlockPos> positionList = checkForCrates(world, pos);
            boolean rotate = rotateCrate(positionList.get(0));
            ArrayList<BlockState> stateList = calculateSides(positionList, world);
            for (int i = 0; i < positionList.size(); i++) {
                world.setBlockState(positionList.get(i), stateList.get(i).with(ROTATED, rotate)
                        .with(POS_NUM, getCrateNumber(positionList.get(i), positionList) + 1)
                        .with(PARENT, (getCrateNumber(positionList.get(i), positionList)) == 0)
                        .with(TYPE, getCrateType(positionList)));
            }
        }
    }*/

    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer != null) {
            if (!placer.getEntity().isSneaking()) {
                ArrayList<BlockPos> positionList = checkForCrates(world, pos);
                boolean rotate = rotateCrate(positionList.get(0));
                ArrayList<BlockState> stateList = calculateSides(positionList, world);
                for (int i = 0; i < positionList.size(); i++) {
                    world.setBlockState(positionList.get(i), stateList.get(i).with(ROTATED, rotate)
                            .with(POS_NUM, getCrateNumber(positionList.get(i), positionList) + 1)
                            .with(PARENT, (getCrateNumber(positionList.get(i), positionList)) == 0)
                            .with(TYPE, getCrateType(positionList)));
                }
            }
        }
        if (stack.hasDisplayName()) {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof CrateTileEntity) {
                ((CrateTileEntity)tileentity).setCustomName(stack.getDisplayName());
            }
        }

    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean rotate = rotateCrate(context.getPos());
        return this.getDefaultState().with(ROTATED, rotate);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (worldIn == null) return;
        tooltip.add(new TranslationTextComponent("block.druidcraft.crate.description1").setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true)));
    }

    interface InventoryFactory<T> {
        T forOcto(CrateTileEntity var1, CrateTileEntity var2, CrateTileEntity var3, CrateTileEntity var4, CrateTileEntity var5, CrateTileEntity var6, CrateTileEntity var7, CrateTileEntity var8);

        T forQuad(CrateTileEntity var1, CrateTileEntity var2, CrateTileEntity var3, CrateTileEntity var4);

        T forDouble(CrateTileEntity var1, CrateTileEntity var2);

        T forSingle(CrateTileEntity var1);
    }
}