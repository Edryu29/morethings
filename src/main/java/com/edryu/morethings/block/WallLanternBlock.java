package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallLanternBlock extends WaterloggableBlock {
    public static final MapCodec<WallLanternBlock> CODEC = Block.simpleCodec(WallLanternBlock::new);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape SHAPE_LANTERN = Shapes.or(Block.box(5.0, 0.0, 5.0, 11.0, 7.0, 11.0), Block.box(6.0, 7.0, 6.0, 10.0, 9.0, 10.0), Block.box(7.0, 9.0, 7.0, 9.0, 11.0, 9.0));
    protected static final VoxelShape SHAPE_HOLDER_NORTH = Shapes.or(Block.box(7, 11, 5, 9, 13, 16), Block.box(6, 5, 15, 10, 14, 16));
    protected static final VoxelShape SHAPE_HOLDER_SOUTH = Shapes.or(Block.box(7, 11, 0, 9, 13, 11), Block.box(6, 5, 0, 10, 14, 1));
    protected static final VoxelShape SHAPE_HOLDER_EAST = Shapes.or(Block.box(0, 11, 7, 11, 13, 9), Block.box(0, 5, 6, 1, 14, 10));
    protected static final VoxelShape SHAPE_HOLDER_WEST = Shapes.or(Block.box(5, 11, 7, 16, 13, 9), Block.box(15, 5, 6, 16, 14, 10));

    public WallLanternBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		switch (state.getValue(FACING)) {
			case NORTH:
			default:
				return Shapes.or(SHAPE_HOLDER_NORTH, SHAPE_LANTERN);
			case EAST:
				return Shapes.or(SHAPE_HOLDER_EAST, SHAPE_LANTERN);
			case SOUTH:
				return Shapes.or(SHAPE_HOLDER_SOUTH, SHAPE_LANTERN);
			case WEST:
				return Shapes.or(SHAPE_HOLDER_WEST, SHAPE_LANTERN);
		}
    }

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		BlockState state = this.defaultBlockState();
		LevelReader level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Direction[] directions = context.getNearestLookingDirections();

		for (Direction direction : directions) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction2 = direction.getOpposite();
				state = state.setValue(FACING, direction2);
				if (state.canSurvive(level, pos)) return state.setValue(WATERLOGGED, fluidState.is(Fluids.WATER));
			}
		}
		return null;
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		Direction facing = state.getValue(FACING);
		BlockPos neighborPos = pos.relative(facing.getOpposite());
		BlockState neighborState = level.getBlockState(neighborPos);
		return neighborState.isFaceSturdy(level, neighborPos, facing);
	}

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }
}
