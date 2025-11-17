package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.registry.SoundRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrankBlock extends WaterloggableBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    protected static final VoxelShape SHAPE_DOWN = Block.box(2, 11, 2, 14, 16, 14);
    protected static final VoxelShape SHAPE_UP = Block.box(2, 0, 2, 14, 5, 14);
    protected static final VoxelShape SHAPE_NORTH = Block.box(2, 2, 11, 14, 14, 16);
    protected static final VoxelShape SHAPE_SOUTH = Block.box(2, 2, 0, 14, 14, 5);
    protected static final VoxelShape SHAPE_EAST = Block.box(0, 2, 2, 5, 14, 14);
    protected static final VoxelShape SHAPE_WEST = Block.box(11, 2, 2, 16, 14, 14);

    public CrankBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH).setValue(POWER, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, POWER);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
            case UP -> SHAPE_UP;
            case DOWN -> SHAPE_DOWN;
            default -> SHAPE_SOUTH;
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        boolean ccw = Screen.hasShiftDown();
        spawnParticles(state, world, pos, ParticleTypes.SMOKE);
        this.turnPower(state, world, pos, ccw, player);

		Direction direction = state.getValue(FACING).getOpposite();
		BlockPos behindPos = pos.relative(direction);
		BlockState behindState = world.getBlockState(behindPos);
		Block behindBlock = behindState.getBlock();
		if (behindBlock instanceof PulleyBlock && direction.getAxis() == behindState.getValue(PulleyBlock.AXIS)) {
			((PulleyBlock)behindBlock).windPulley(behindState, world, behindPos, Screen.hasShiftDown(), direction);
		}
        return InteractionResult.SUCCESS;
    }

    public void turnPower(BlockState state, Level world, BlockPos pos, boolean ccw, @Nullable Player player) {
        int newPower = (16 + state.getValue(POWER) + (ccw ? -1 : 1)) % 16;
        world.setBlockAndUpdate(pos, state.setValue(POWER, newPower));
        this.updateNeighbors(state, world, pos);
        world.playSound(player, pos, SoundRegistry.CRANK, SoundSource.BLOCKS, 1.0F, 0.55f + state.getValue(POWER) * 0.04f);
		world.gameEvent(player, state.getValue(POWER) > 0 ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
    }

	private static void spawnParticles(BlockState state, LevelAccessor world, BlockPos pos, ParticleOptions particle) {
		Direction direction = state.getValue(FACING).getOpposite();
		double d = pos.getX() + 0.5 + 0.1 * direction.getStepX() + 0.2 * direction.getStepX();
		double e = pos.getY() + 0.5 + 0.1 * direction.getStepY() + 0.2 * direction.getStepY();
		double f = pos.getZ() + 0.5 + 0.1 * direction.getStepZ() + 0.2 * direction.getStepZ();
		world.addParticle(particle, d, e, f, 0.0, 0.0, 0.0);
	}

	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		if (state.getValue(POWER) > 0 && random.nextFloat() < 0.25F) spawnParticles(state, world, pos, new DustParticleOptions(DustParticleOptions.REDSTONE_PARTICLE_COLOR, 0.5F));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        boolean wl = ctx.getLevel().getFluidState(ctx.getClickedPos()).is(Fluids.WATER);
		for (Direction direction : ctx.getNearestLookingDirections()) {
			BlockState blockState = defaultBlockState().setValue(WATERLOGGED, wl).setValue(FACING, direction.getOpposite());
			if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) return blockState;
		}
		return null;
	}

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		return state.getValue(FACING).getOpposite() == direction && !state.canSurvive(world, pos)
                ? Blocks.AIR.defaultBlockState(): super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

	@Override
	protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockPos blockPos = pos.relative(state.getValue(FACING).getOpposite());
		return world.getBlockState(blockPos).isFaceSturdy(world, blockPos, state.getValue(FACING));
	}

	@Override
	protected void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
		if (!moved && !state.is(newState.getBlock())) {
			this.updateNeighbors(state, world, pos);
			super.onRemove(state, world, pos, newState, moved);
		}
	}

	private void updateNeighbors(BlockState state, Level world, BlockPos pos) {
		world.updateNeighborsAt(pos, this);
		world.updateNeighborsAt(pos.relative(state.getValue(FACING).getOpposite()), this);
	}

	@Override
	protected boolean isSignalSource(BlockState state) {
		return true;
	}

	@Override
	protected int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
		return state.getValue(POWER);
	}

	@Override
	protected int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
		return state.getValue(FACING) == direction ? state.getValue(POWER) : 0;
	}

	@Override
	protected BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
}