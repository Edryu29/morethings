package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SideShapeType;
import net.minecraft.block.TripwireBlock;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class CrankBlock extends WaterloggableBlock {
    public static final DirectionProperty FACING = Properties.FACING;
    public static final IntProperty POWER = Properties.POWER;

    protected static final VoxelShape SHAPE_DOWN = Block.createCuboidShape(2, 11, 2, 14, 16, 14);
    protected static final VoxelShape SHAPE_UP = Block.createCuboidShape(2, 0, 2, 14, 5, 14);
    protected static final VoxelShape SHAPE_NORTH = Block.createCuboidShape(2, 2, 11, 14, 14, 16);
    protected static final VoxelShape SHAPE_SOUTH = Block.createCuboidShape(2, 2, 0, 14, 14, 5);
    protected static final VoxelShape SHAPE_EAST = Block.createCuboidShape(0, 2, 2, 5, 14, 14);
    protected static final VoxelShape SHAPE_WEST = Block.createCuboidShape(11, 2, 2, 16, 14, 14);

    public CrankBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH).with(POWER, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
            case UP -> SHAPE_UP;
            case DOWN -> SHAPE_DOWN;
            default -> SHAPE_SOUTH;
        };
    }

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
        boolean wl = ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER);
		for (Direction direction : ctx.getPlacementDirections()) {
			BlockState blockState = getDefaultState().with(WATERLOGGED, wl).with(FACING, direction.getOpposite());
			if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) return blockState;
		}
		return null;
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.offset(state.get(FACING).getOpposite());
		return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, state.get(FACING));
	}

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, POWER);
    }
}