package com.edryu.morethings.block;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.MoreThingsSounds;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SideShapeType;
import net.minecraft.block.Waterloggable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class RopeBlock extends Block implements Waterloggable {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final BooleanProperty KNOT = BooleanProperty.of("knot");
	public static final BooleanProperty UP = BooleanProperty.of("up");
	public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty EAST = BooleanProperty.of("east");

    private static final VoxelShape ROPE_KNOT = Block.createCuboidShape(6, 6, 6, 10, 10, 10);
    private static final VoxelShape ROPE_UP = Block.createCuboidShape(7, 9, 7, 9, 16, 9);
    private static final VoxelShape ROPE_DOWN = Block.createCuboidShape(7, 0, 7, 9, 9, 9);
    private static final VoxelShape ROPE_NORTH = Block.createCuboidShape(7, 7, 0, 9, 9, 9);
    private static final VoxelShape ROPE_SOUTH = Block.createCuboidShape(7, 7, 9, 9, 9, 16);
    private static final VoxelShape ROPE_WEST = Block.createCuboidShape(0, 7, 7, 9, 9, 9);
    private static final VoxelShape ROPE_EAST = Block.createCuboidShape(9, 7, 7, 16, 9, 9);

    public RopeBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WATERLOGGED, false)
            .with(KNOT, false).with(UP, false).with(DOWN, false)
            .with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(
            state.get(KNOT) ? ROPE_KNOT : VoxelShapes.empty(),
            state.get(NORTH) ? ROPE_NORTH : VoxelShapes.empty(),
            state.get(SOUTH) ? ROPE_SOUTH : VoxelShapes.empty(),
            state.get(EAST) ? ROPE_EAST : VoxelShapes.empty(),
            state.get(WEST) ? ROPE_WEST : VoxelShapes.empty(),
            state.get(UP) ? ROPE_UP : VoxelShapes.empty(),
            state.get(DOWN) ? ROPE_DOWN : VoxelShapes.empty()
        );
    }

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        boolean north = canConnectTo(world, pos.north(), Direction.SOUTH);
        boolean south = canConnectTo(world, pos.south(), Direction.NORTH);
        boolean east  = canConnectTo(world, pos.east(),  Direction.WEST);
        boolean west  = canConnectTo(world, pos.west(),  Direction.EAST);
        boolean up    = canConnectTo(world, pos.up(),    Direction.DOWN);
        boolean down  = canConnectTo(world, pos.down(),  Direction.UP);

        boolean hasAxisY      = up || down;
        boolean hasAxisZ      = north || south;
        boolean hasAxisX      = east || west;
        boolean isCorner      = (north || south) && (east || west);
        boolean singleAxis    = (north ^ south) || (east ^ west) || (up ^ down);
        boolean ropeKnot      = ((hasAxisZ || hasAxisX) && (isCorner || hasAxisY)) || (!(hasAxisZ || hasAxisX) && !hasAxisY) || singleAxis;

		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		boolean bl = fluidState.getFluid() == Fluids.WATER;

		BlockState state = super.getPlacementState(ctx)
            .with(WATERLOGGED, bl)
            .with(KNOT, ropeKnot)
            .with(NORTH, north)
            .with(SOUTH, south)
            .with(EAST, east)
            .with(WEST, west)
            .with(UP, up)
            .with(DOWN, down);

        return state;
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        boolean wl = state.get(WATERLOGGED);
		if (wl) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        boolean north = canConnectTo(world, pos.north(), Direction.SOUTH);
        boolean south = canConnectTo(world, pos.south(), Direction.NORTH);
        boolean east  = canConnectTo(world, pos.east(),  Direction.WEST);
        boolean west  = canConnectTo(world, pos.west(),  Direction.EAST);
        boolean up    = canConnectTo(world, pos.up(),    Direction.DOWN);
        boolean down  = canConnectTo(world, pos.down(),  Direction.UP);

        boolean hasAxisY      = up || down;
        boolean hasAxisZ      = north || south;
        boolean hasAxisX      = east || west;
        boolean isCorner      = (north || south) && (east || west);
        boolean singleAxis    = (north ^ south) || (east ^ west) || (up ^ down);
        boolean ropeKnot      = ((hasAxisZ || hasAxisX) && (isCorner || hasAxisY)) || (!(hasAxisZ || hasAxisX) && !hasAxisY) || singleAxis;

        state = getDefaultState()
                .with(WATERLOGGED, wl)
                .with(KNOT, ropeKnot)
                .with(NORTH, north)
                .with(SOUTH, south)
                .with(EAST, east)
                .with(WEST, west)
                .with(UP, up)
                .with(DOWN, down);
        
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

    private boolean canConnectTo(WorldAccess world, BlockPos neighborPos, Direction dirTowardNeighbor) {
        BlockState neighborState = world.getBlockState(neighborPos);
        if (neighborState.getBlock() instanceof RopeBlock) return true;
        // if (dirTowardNeighbor == Direction.UP) return neighborState.isSideSolid(world, neighborPos, Direction.DOWN, SideShapeType.CENTER);
        return neighborState.isSideSolid(world, neighborPos, dirTowardNeighbor, SideShapeType.CENTER);
    }

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, KNOT, UP, DOWN, NORTH, SOUTH, WEST, EAST);
    }
}