package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.entity.PulleyBlockEntity;
import com.edryu.morethings.util.BlockProperties;
import com.edryu.morethings.util.BlockProperties.Winding;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class PulleyBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty FLIPPED = BooleanProperty.create("flipped");
    public static final EnumProperty<Winding> WINDING = BlockProperties.WINDING;

    public PulleyBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(AXIS, Direction.Axis.X).setValue(FLIPPED, false).setValue(WINDING, Winding.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, FLIPPED, WINDING);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PulleyBlockEntity(pos, state);
    }

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(AXIS, ctx.getHorizontalDirection().getOpposite().getAxis());
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide) {
            MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);
            if (screenHandlerFactory != null) player.openMenu(screenHandlerFactory);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved)  {
		if (!state.is(newState.getBlock())) {
			if (world.getBlockEntity(pos) instanceof PulleyBlockEntity PulleyBlockEntity) Containers.dropContents(world, pos, PulleyBlockEntity);
		}
		super.onRemove(state, world, pos, newState, moved);
    }

	@Override
	protected boolean triggerEvent(BlockState state, Level world, BlockPos pos, int type, int data) {
		super.triggerEvent(state, world, pos, type, data);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.triggerEvent(type, data);
	}

	@Nullable
	@Override
	protected MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity instanceof MenuProvider ? (MenuProvider)blockEntity : null;
	}

    public boolean windPulley(BlockState state, Level world, BlockPos pos, boolean retract, @Nullable Direction direction) {
        boolean result = world.getBlockEntity(pos) instanceof PulleyBlockEntity pulleyEntity ? pulleyEntity.operateDirectly(retract) : false;
        if (direction != null){
            BlockPos connectedPos = pos.relative(direction);
            BlockState connectedPulley = world.getBlockState(connectedPos);
            if (connectedPulley.is(this) && state.getValue(AXIS) == connectedPulley.getValue(AXIS)) {
                ((PulleyBlock)connectedPulley.getBlock()).windPulley(connectedPulley, world, connectedPos, retract, direction);
            }
        }
        return result;
    }
}