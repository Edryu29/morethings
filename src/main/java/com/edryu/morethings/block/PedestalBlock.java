package com.edryu.morethings.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class PedestalBlock extends WaterloggableBlock {
	public static final BooleanProperty UP = BooleanProperty.of("up");
	public static final BooleanProperty DOWN = BooleanProperty.of("down");

    protected static final VoxelShape SHAPE = VoxelShapes.union(
            VoxelShapes.cuboid(0.1875f, 0.125f, 0.1875f, 0.815f, 0.885f, 0.815f),
            VoxelShapes.cuboid(0.0625f, 0.8125f, 0.0625f, 0.9375f, 1f, 0.9375f),
            VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.9375f, 0.1875f, 0.9375f));
    protected static final VoxelShape SHAPE_UP = VoxelShapes.union(
            VoxelShapes.cuboid(0.1875f, 0.125f, 0.1875f, 0.815f, 1f, 0.815D),
            VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.9375f, 0.1875f, 0.9375D));
    protected static final VoxelShape SHAPE_DOWN = VoxelShapes.union(
            VoxelShapes.cuboid(0.1875f, 0f, 0.1875f, 0.815f, 0.885f, 0.815D),
            VoxelShapes.cuboid(0.0625f, 0.8125f, 0.0625f, 0.9375f, 1f, 0.9375D));
    protected static final VoxelShape SHAPE_UP_DOWN = VoxelShapes.cuboid(0.1875f, 0f, 0.1875f, 0.815f, 1f, 0.815f);

    public PedestalBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WATERLOGGED, false).with(UP, false).with(DOWN, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        boolean up = state.get(UP);
        boolean down = state.get(DOWN);
        if (!up) {
            if (!down) {
                return SHAPE;
            } else {
                return SHAPE_DOWN;
            }
        } else {
            if (!down) {
                return SHAPE_UP;
            } else {
                return SHAPE_UP_DOWN;
            }
        }
    }

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        boolean up    = canConnectTo(world, pos.up());
        boolean down  = canConnectTo(world, pos.down());

		FluidState fluidState = world.getFluidState(pos);
		boolean wl = fluidState.getFluid() == Fluids.WATER;

        return super.getPlacementState(ctx).with(WATERLOGGED, wl).with(UP, up).with(DOWN, down);
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        boolean wl = state.get(WATERLOGGED);
		if (wl) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        boolean up    = canConnectTo(world, pos.up());
        boolean down  = canConnectTo(world, pos.down());

		state = getDefaultState().with(WATERLOGGED, wl).with(UP, up).with(DOWN, down);
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

    private boolean canConnectTo(WorldAccess world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof PedestalBlock ? true : false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, UP, DOWN);
    }
}