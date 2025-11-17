package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BuntingWallBlock extends BuntingBlock {
    protected static final VoxelShape SHAPE_NORTH = Block.box(0, 0, 15, 16, 16, 16);
    protected static final VoxelShape SHAPE_SOUTH = Block.box(0, 0, 0, 16, 16, 1);
    protected static final VoxelShape SHAPE_EAST = Block.box(0, 0, 0, 1, 16, 16);
    protected static final VoxelShape SHAPE_WEST = Block.box(15, 0, 0, 16, 16, 16);

    public BuntingWallBlock(Properties settings) {
        super(settings);
    }

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		switch (state.getValue(FACING)) {
			case NORTH:
			default:
				return SHAPE_NORTH;
			case EAST:
				return SHAPE_EAST;
			case SOUTH:
				return SHAPE_SOUTH;
			case WEST:
				return SHAPE_WEST;
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = this.defaultBlockState();
		LevelReader level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Direction[] directions = context.getNearestLookingDirections();

		for (Direction direction : directions) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction2 = direction.getOpposite();
				state = state.setValue(FACING, direction2);
				if (state.canSurvive(level, pos)) return state;
			}
		}
		return null;
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		Direction facing = state.getValue(FACING);
		BlockPos neighborPos = pos.relative(facing.getOpposite());
		BlockState neighborState = level.getBlockState(neighborPos);
		if (neighborState.getBlock() instanceof TripWireBlock) return true;
		return neighborState.isFaceSturdy(level, neighborPos, facing);
	}
}