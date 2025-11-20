package com.edryu.morethings.block;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.client.datagen.BlockTagProvider;
import com.edryu.morethings.registry.ItemRegistry;
import com.edryu.morethings.registry.SoundRegistry;
import com.edryu.morethings.util.MoreThingsHelper;

public class RopeBlock extends WaterloggedBlock {
	public static final BooleanProperty KNOT = BooleanProperty.create("knot");
	public static final BooleanProperty UP = BooleanProperty.create("up");
	public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty BELL = BooleanProperty.create("bell");

    private static final VoxelShape ROPE_KNOT = Block.box(6, 9, 6, 10, 13, 10);
    private static final VoxelShape ROPE_UP = Block.box(6, 9, 6, 10, 16, 10);
    private static final VoxelShape ROPE_DOWN = Block.box(6, 0, 6, 10, 9, 10);
    private static final VoxelShape ROPE_NORTH = Block.box(6, 9, 0, 10, 13, 9);
    private static final VoxelShape ROPE_SOUTH = Block.box(6, 9, 9, 10, 13, 16);
    private static final VoxelShape ROPE_WEST = Block.box(0, 9, 6, 9, 13, 10);
    private static final VoxelShape ROPE_EAST = Block.box(9, 9, 6, 16, 13, 10);
    private static final VoxelShape COLLISION_SHAPE = Block.box(0, 0, 0, 16, 13, 16);

    public RopeBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false)
            .setValue(KNOT, false).setValue(BELL, false).setValue(UP, false).setValue(DOWN, false)
            .setValue(NORTH, false).setValue(SOUTH, false).setValue(WEST, false).setValue(EAST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, KNOT, BELL, UP, DOWN, NORTH, SOUTH, WEST, EAST);
    }

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor level = context.getLevel();
        BlockPos pos = context.getClickedPos();

		boolean wl = level.getFluidState(pos).is(Fluids.WATER);

        boolean north = canConnectTo(level, pos.north(), Direction.SOUTH);
        boolean south = canConnectTo(level, pos.south(), Direction.NORTH);
        boolean east = canConnectTo(level, pos.east(),  Direction.WEST);
        boolean west = canConnectTo(level, pos.west(),  Direction.EAST);
        boolean up = canConnectTo(level, pos.above(),    Direction.DOWN);
        boolean down = canConnectTo(level, pos.below(),  Direction.UP);
        boolean bell = canConnectToBell(level, pos);

        boolean hasVertical   = up || down;
        boolean hasHorizontal = north || south || east || west;
        boolean noConnections = !hasHorizontal && !hasVertical;
        boolean isCorner      = (north || south) && (east || west);
        boolean singleAxis    = (north ^ south) || (east ^ west) || (up ^ down);
        boolean ropeKnot      = (hasHorizontal && (isCorner || hasVertical)) || noConnections || singleAxis;

		BlockState state = super.getStateForPlacement(context)
            .setValue(WATERLOGGED, wl)
            .setValue(KNOT, ropeKnot)
            .setValue(BELL, bell)
            .setValue(NORTH, north)
            .setValue(SOUTH, south)
            .setValue(EAST, east)
            .setValue(WEST, west)
            .setValue(UP, up)
            .setValue(DOWN, down);

        if (hasNoConnection(state) || !hasFixedAnchor(level, pos)) return null;
        return state;
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        boolean wl = state.getValue(WATERLOGGED);
		if (wl) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

        state = state.setValue(getDirectionState(direction), canConnectTo(level, neighborPos, direction.getOpposite()));

        boolean north = state.getValue(NORTH);
        boolean south = state.getValue(SOUTH);
        boolean east = state.getValue(EAST);
        boolean west = state.getValue(WEST);
        boolean up = state.getValue(UP);
        boolean down = state.getValue(DOWN);
        boolean bell  = canConnectToBell(level, pos);

        boolean hasVertical   = up || down;
        boolean hasHorizontal = north || south || east || west;
        boolean noConnections = !hasHorizontal && !hasVertical;
        boolean isCorner      = (north || south) && (east || west);
        boolean singleAxis    = (north ^ south) || (east ^ west) || (up ^ down);
        boolean ropeKnot      = (hasHorizontal && (isCorner || hasVertical)) || noConnections || singleAxis;

        state = this.defaultBlockState()
                .setValue(WATERLOGGED, wl)
                .setValue(KNOT, ropeKnot)
                .setValue(BELL, bell)
                .setValue(NORTH, north)
                .setValue(SOUTH, south)
                .setValue(EAST, east)
                .setValue(WEST, west)
                .setValue(UP, up)
                .setValue(DOWN, down);

        if (hasNoConnection(state) || !hasFixedAnchor(level, pos)) return Blocks.AIR.defaultBlockState();
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        ItemStack stack = player.getMainHandItem();
        if (stack.is(this.asItem())) {
            if (!Screen.hasShiftDown()) return InteractionResult.PASS; 
            if (MoreThingsHelper.addWindingDown(pos.below(), level, player, InteractionHand.MAIN_HAND, this)) {
                SoundType soundType = state.getSoundType();
                level.playSound(player, pos, soundType.getPlaceSound(), SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
                stack.consume(1, player);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        } else {
            if (state.getValue(UP)) {
                if (findConnectedBell(level, pos, player, 0) && !Screen.hasShiftDown()) return InteractionResult.SUCCESS;
                if (findConnectedPulley(level, pos, player, 0, Screen.hasShiftDown())) return InteractionResult.SUCCESS;
            }
            if (Screen.hasShiftDown()) {
                if (level.getBlockState(pos.below()).is(this) || level.getBlockState(pos.above()).is(this)) {
                    if (MoreThingsHelper.removeWindingDown(pos.below(), level, this)) {
                        level.playSound(player, pos, SoundRegistry.ROPE_SLIDE, SoundSource.BLOCKS, 1, 0.6F);
                        if (!player.isCreative()) player.addItem(new ItemStack(ItemRegistry.ROPE, 1));
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    private boolean canConnectTo(LevelAccessor level, BlockPos neighborPos, Direction dirTowardRope) {
        BlockState neighborState = level.getBlockState(neighborPos);
        if (neighborState.getBlock() instanceof RopeBlock) return true;
        if (neighborState.getBlock() instanceof RopeKnotBlock) return true;

        if (dirTowardRope == Direction.DOWN) { // Bottom side of block above rope
            if (neighborState.getBlock() instanceof BellBlock) return true;
            if (neighborState.is(BlockTagProvider.ROPE_SUPPORT)) return true;
        }
        if (dirTowardRope == Direction.UP) { // Top side of block below rope
            if (neighborState.getBlock() instanceof LanternBlock) return true;
            if (neighborState.getBlock() instanceof BellBlock) return true;
            if (neighborState.getBlock() instanceof AbstractCauldronBlock) return true;
            if (neighborState.is(BlockTagProvider.HANG_FROM_ROPES)) return true;
            return neighborState.isFaceSturdy(level, neighborPos, Direction.DOWN, SupportType.CENTER);
        }

        return neighborState.isFaceSturdy(level, neighborPos, dirTowardRope, SupportType.CENTER);
    }

    private boolean canConnectToBell(LevelAccessor level, BlockPos pos) {
        return level.getBlockState(pos.above()).getBlock() instanceof BellBlock;
    }

    private boolean hasNoConnection(BlockState state) {
        return !(state.getValue(NORTH) || state.getValue(SOUTH) || state.getValue(EAST) || state.getValue(WEST) || state.getValue(UP) || state.getValue(DOWN));
    }

    private boolean hasFixedAnchor(LevelAccessor level, BlockPos start) {
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> seen = new HashSet<>();
        queue.add(start);
        int steps = 0;
        while (!queue.isEmpty() && steps < 256) {
            BlockPos p = queue.poll();
            if (!seen.add(p)) continue;
            for (Direction d : Direction.values()) {
                BlockPos n = p.relative(d);
                BlockState s = level.getBlockState(n);
                if (s.getBlock() instanceof RopeKnotBlock) return true;
                if (s.getBlock() instanceof RopeBlock) {
                    if (!seen.contains(n)) queue.add(n);
                    continue;
                }
                if (d == Direction.UP) {
                    if (s.is(BlockTagProvider.ROPE_SUPPORT) || s.getBlock() instanceof BellBlock) return true;
                    if (s.isFaceSturdy(level, n, Direction.DOWN, SupportType.CENTER)) return true;
                } else if (d != Direction.DOWN) {
                    if (s.isFaceSturdy(level, n, d.getOpposite(), SupportType.CENTER)) return true;
                }
            }
            steps++;
        }
        return false;
    }

    private boolean findConnectedPulley(Level level, BlockPos pos, Player player, int it, boolean retracting) {
        if (it > 64) return false;
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof RopeBlock) return findConnectedPulley(level, pos.above(), player, it + 1, retracting);
        else if (block instanceof PulleyBlock pulley && it != 0) return pulley.windPulley(state, level, pos, retracting, null);
        return false;
    }

    private boolean findConnectedBell(Level level, BlockPos pos, Player player, int it) {
        if (it > 64) return false;
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof RopeBlock) return findConnectedBell(level, pos.above(), player, it + 1);
        else if (block instanceof BellBlock bell && it != 0) return bell.attemptToRing(level, pos, player.getDirection().getClockWise());;
        return false;
    }
    
    private BooleanProperty getDirectionState(Direction direction) {
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
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(
            state.getValue(KNOT) ? ROPE_KNOT : Shapes.empty(),
            state.getValue(NORTH) ? ROPE_NORTH : Shapes.empty(),
            state.getValue(SOUTH) ? ROPE_SOUTH : Shapes.empty(),
            state.getValue(EAST) ? ROPE_EAST : Shapes.empty(),
            state.getValue(WEST) ? ROPE_WEST : Shapes.empty(),
            state.getValue(UP) ? ROPE_UP : Shapes.empty(),
            state.getValue(DOWN) ? ROPE_DOWN : Shapes.empty()
        );
    }

    @Override
    protected @NotNull VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.or(this.getCollisionShape(state, level, pos, CollisionContext.empty()), ROPE_DOWN);
    }

    @Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return !state.getValue(UP) && (!state.getValue(DOWN) || (context.isAbove(COLLISION_SHAPE, pos, true) && !Screen.hasShiftDown())) ? state.getShape(level, pos) : Shapes.empty();
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
		return false;
	}
}