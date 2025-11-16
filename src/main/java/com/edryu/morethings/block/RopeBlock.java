package com.edryu.morethings.block;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.client.datagen.BlockTagProvider;
import com.edryu.morethings.registry.ItemRegistry;
import com.edryu.morethings.registry.SoundRegistry;
import com.edryu.morethings.util.WindingHelper;

import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SideShapeType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class RopeBlock extends WaterloggableBlock {
	public static final BooleanProperty KNOT = BooleanProperty.of("knot");
	public static final BooleanProperty UP = BooleanProperty.of("up");
	public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty BELL = BooleanProperty.of("bell");

    private static final VoxelShape ROPE_KNOT = Block.createCuboidShape(6, 9, 6, 10, 13, 10);
    private static final VoxelShape ROPE_UP = Block.createCuboidShape(6, 9, 6, 10, 16, 10);
    private static final VoxelShape ROPE_DOWN = Block.createCuboidShape(6, 0, 6, 10, 9, 10);
    private static final VoxelShape ROPE_NORTH = Block.createCuboidShape(6, 9, 0, 10, 13, 9);
    private static final VoxelShape ROPE_SOUTH = Block.createCuboidShape(6, 9, 9, 10, 13, 16);
    private static final VoxelShape ROPE_WEST = Block.createCuboidShape(0, 9, 6, 9, 13, 10);
    private static final VoxelShape ROPE_EAST = Block.createCuboidShape(9, 9, 6, 16, 13, 10);
    private static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 13, 16);

    public RopeBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WATERLOGGED, false)
            .with(KNOT, false).with(BELL, false).with(UP, false).with(DOWN, false)
            .with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, KNOT, BELL, UP, DOWN, NORTH, SOUTH, WEST, EAST);
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
        boolean east = canConnectTo(world, pos.east(),  Direction.WEST);
        boolean west = canConnectTo(world, pos.west(),  Direction.EAST);
        boolean up = canConnectTo(world, pos.up(),    Direction.DOWN);
        boolean down = canConnectTo(world, pos.down(),  Direction.UP);
        boolean bell = canConnectToBell(world, pos);

        boolean hasVertical   = up || down;
        boolean hasHorizontal = north || south || east || west;
        boolean noConnections = !hasHorizontal && !hasVertical;
        boolean isCorner      = (north || south) && (east || west);
        boolean singleAxis    = (north ^ south) || (east ^ west) || (up ^ down);
        boolean ropeKnot      = (hasHorizontal && (isCorner || hasVertical)) || noConnections || singleAxis;

		FluidState fluidState = world.getFluidState(pos);
		boolean wl = fluidState.getFluid() == Fluids.WATER;

		BlockState state = super.getPlacementState(ctx)
            .with(WATERLOGGED, wl)
            .with(KNOT, ropeKnot)
            .with(BELL, bell)
            .with(NORTH, north)
            .with(SOUTH, south)
            .with(EAST, east)
            .with(WEST, west)
            .with(UP, up)
            .with(DOWN, down);

        if (hasNoConnection(state) || !hasFixedAnchor(world, pos)) return null;
        return state;
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        boolean wl = state.get(WATERLOGGED);
		if (wl) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        state = state.with(getDirState(direction), canConnectTo(world, neighborPos, direction.getOpposite()));

        boolean north = state.get(NORTH);
        boolean south = state.get(SOUTH);
        boolean east = state.get(EAST);
        boolean west = state.get(WEST);
        boolean up = state.get(UP);
        boolean down = state.get(DOWN);
        boolean bell  = canConnectToBell(world, pos);

        boolean hasVertical   = up || down;
        boolean hasHorizontal = north || south || east || west;
        boolean noConnections = !hasHorizontal && !hasVertical;
        boolean isCorner      = (north || south) && (east || west);
        boolean singleAxis    = (north ^ south) || (east ^ west) || (up ^ down);
        boolean ropeKnot      = (hasHorizontal && (isCorner || hasVertical)) || noConnections || singleAxis;

        state = getDefaultState()
                .with(WATERLOGGED, wl)
                .with(KNOT, ropeKnot)
                .with(BELL, bell)
                .with(NORTH, north)
                .with(SOUTH, south)
                .with(EAST, east)
                .with(WEST, west)
                .with(UP, up)
                .with(DOWN, down);

        if (hasNoConnection(state) || !hasFixedAnchor(world, pos)) return Blocks.AIR.getDefaultState();
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player == null) return ActionResult.PASS;

        ItemStack stack = player.getMainHandStack();
        if (stack.isOf(this.asItem())) {
            if (!Screen.hasShiftDown()) return ActionResult.PASS; 
            if (WindingHelper.addWindingDown(pos.down(), world, player, Hand.MAIN_HAND, this)) {
                BlockSoundGroup soundGroup = state.getSoundGroup();
                world.playSound(player, pos, soundGroup.getPlaceSound(), SoundCategory.BLOCKS, (soundGroup.getVolume() + 1.0F) / 2.0F, soundGroup.getPitch() * 0.8F);
                stack.decrementUnlessCreative(1, player);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        } else {
            if (state.get(UP)) {
                if (findConnectedBell(world, pos, player, 0) && !Screen.hasShiftDown()) return ActionResult.SUCCESS;
                if (findConnectedPulley(world, pos, player, 0, Screen.hasShiftDown())) return ActionResult.SUCCESS;
            }
            if (Screen.hasShiftDown()) {
                if (world.getBlockState(pos.down()).isOf(this) || world.getBlockState(pos.up()).isOf(this)) {
                    if (WindingHelper.removeWindingDown(pos.down(), world, this)) {
                        world.playSound(player, pos, SoundRegistry.ROPE_SLIDE, SoundCategory.BLOCKS, 1, 0.6F);
                        if (!player.isInCreativeMode()) player.giveItemStack(new ItemStack(ItemRegistry.ROPE, 1));
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return ActionResult.PASS;
    }

    private boolean canConnectTo(WorldAccess world, BlockPos neighborPos, Direction dirTowardRope) {
        BlockState neighborState = world.getBlockState(neighborPos);
        if (neighborState.getBlock() instanceof RopeBlock) return true;
        if (neighborState.getBlock() instanceof RopeKnotBlock) return true;

        if (dirTowardRope == Direction.DOWN) { // Bottom side of block above rope
            if (neighborState.getBlock() instanceof BellBlock) return true;
            if (neighborState.isIn(BlockTagProvider.ROPE_SUPPORT)) return true;
        }
        if (dirTowardRope == Direction.UP) { // Top side of block below rope
            if (neighborState.getBlock() instanceof LanternBlock) return true;
            if (neighborState.getBlock() instanceof BellBlock) return true;
            if (neighborState.isIn(BlockTagProvider.HANG_FROM_ROPES)) return true;
            return neighborState.isSideSolid(world, neighborPos, Direction.DOWN, SideShapeType.CENTER);
        }

        return neighborState.isSideSolid(world, neighborPos, dirTowardRope, SideShapeType.CENTER);
    }

    private boolean canConnectToBell(WorldAccess world, BlockPos pos) {
        return world.getBlockState(pos.up()).getBlock() instanceof BellBlock;
    }

    private boolean hasNoConnection(BlockState state) {
        return !(state.get(NORTH) || state.get(SOUTH) || state.get(EAST) || state.get(WEST) || state.get(UP) || state.get(DOWN));
    }

    private boolean hasFixedAnchor(WorldAccess world, BlockPos start) {
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> seen = new HashSet<>();
        queue.add(start);
        int steps = 0;
        while (!queue.isEmpty() && steps < 256) {
            BlockPos p = queue.poll();
            if (!seen.add(p)) continue;
            for (Direction d : Direction.values()) {
                BlockPos n = p.offset(d);
                BlockState s = world.getBlockState(n);
                if (s.getBlock() instanceof RopeKnotBlock) return true;
                if (s.getBlock() instanceof RopeBlock) {
                    if (!seen.contains(n)) queue.add(n);
                    continue;
                }
                if (d == Direction.UP) {
                    if (s.isIn(BlockTagProvider.ROPE_SUPPORT) || s.getBlock() instanceof BellBlock) return true;
                    if (s.isSideSolid(world, n, Direction.DOWN, SideShapeType.CENTER)) return true;
                } else if (d != Direction.DOWN) {
                    if (s.isSideSolid(world, n, d.getOpposite(), SideShapeType.CENTER)) return true;
                }
            }
            steps++;
        }
        return false;
    }

    private boolean findConnectedPulley(World world, BlockPos pos, PlayerEntity player, int it, boolean retracting) {
        if (it > 64) return false;
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof RopeBlock) return findConnectedPulley(world, pos.up(), player, it + 1, retracting);
        else if (block instanceof PulleyBlock pulley && it != 0) return pulley.windPulley(state, world, pos, retracting, null);
        return false;
    }

    private boolean findConnectedBell(World world, BlockPos pos, PlayerEntity player, int it) {
        if (it > 64) return false;
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof RopeBlock) return findConnectedBell(world, pos.up(), player, it + 1);
        else if (block instanceof BellBlock bell && it != 0) return bell.ring(world, pos, player.getHorizontalFacing().rotateYClockwise());;
        return false;
    }
    
    private BooleanProperty getDirState(Direction direction) {
        return switch (direction) {
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            case UP -> UP;
            case DOWN -> DOWN;
            default -> NORTH;
        };
    }

    @Override
    protected @NotNull VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.union(this.getCollisionShape(state, world, pos, ShapeContext.absent()), ROPE_DOWN);
    }

    @Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return !state.get(UP) && (!state.get(DOWN) || (context.isAbove(COLLISION_SHAPE, pos, true) && !Screen.hasShiftDown())) ? state.getOutlineShape(world, pos) : VoxelShapes.empty();
	}

	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}
}