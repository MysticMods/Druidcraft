package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.api.SideType;
import com.vulp.druidcraft.api.StackPart;
import com.vulp.druidcraft.inventory.container.SmithingWorkbenchContainer;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.SmithingTableContainer;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class SmithingWorkbenchBlock extends Block {

    private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container." + Druidcraft.MODID + ".smithing_workbench");

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final EnumProperty<SideType> TYPE = EnumProperty.create("side", SideType.class);

    public SmithingWorkbenchBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(TYPE, SideType.LEFT));
    }

    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        return new SimpleNamedContainerProvider((id, inventory, player) -> new SmithingWorkbenchContainer(id, inventory, IWorldPosCallable.of(worldIn, pos)), CONTAINER_NAME);
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (canFunction(worldIn, pos)) {
            if (worldIn.isRemote) {
                return ActionResultType.SUCCESS;
            } else {
                player.openContainer(state.getContainer(worldIn, pos));
                player.addStat(Stats.field_232864_aE_);
                return ActionResultType.CONSUME;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    public boolean canFunction(World world, BlockPos pos) {
        BlockState currentState = world.getBlockState(pos);
        return world.getBlockState(pos.offset(getDirectionToOther(currentState.get(FACING), currentState.get(TYPE)))).isIn(this);
    }

    private static Direction getDirectionToOther(Direction facing, SideType side) {
        return side == SideType.LEFT ? facing.rotateYCCW() : facing.rotateY();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction directionToOther = getDirectionToOther(stateIn.get(FACING), stateIn.get(TYPE));
        BlockState oppositeBlock = worldIn.getBlockState(currentPos.offset(directionToOther));
        return (oppositeBlock.isIn(this) && oppositeBlock.get(TYPE) != stateIn.get(TYPE) && oppositeBlock.get(FACING) == stateIn.get(FACING)) ? super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos) : Blocks.SMITHING_TABLE.getDefaultState();
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

}
