package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.SoundRegistry;

import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.data.client.VariantSettings.Rotation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, POWER);
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

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        boolean ccw = Screen.hasShiftDown();
        spawnParticles(state, world, pos, ParticleTypes.SMOKE);
        this.turnPower(state, world, pos, ccw, player);

		Direction direction = state.get(FACING).getOpposite();
		BlockPos behindPos = pos.offset(direction);
		BlockState behindState = world.getBlockState(behindPos);
		Block behindBlock = behindState.getBlock();
		if (behindBlock instanceof PulleyBlock && direction.getAxis() == behindState.get(PulleyBlock.AXIS)) ((PulleyBlock)behindBlock).windPulley(behindState, world, behindPos, ccw);
        return ActionResult.SUCCESS;
    }

    public void turnPower(BlockState state, World world, BlockPos pos, boolean ccw, @Nullable PlayerEntity player) {
        int newPower = (16 + state.get(POWER) + (ccw ? -1 : 1)) % 16;
        world.setBlockState(pos, state.with(POWER, newPower));
        this.updateNeighbors(state, world, pos);
        world.playSound(player, pos, SoundRegistry.CRANK, SoundCategory.BLOCKS, 1.0F, 0.55f + state.get(POWER) * 0.04f);
		world.emitGameEvent(player, state.get(POWER) > 0 ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
    }

	private static void spawnParticles(BlockState state, WorldAccess world, BlockPos pos, ParticleEffect particle) {
		Direction direction = state.get(FACING).getOpposite();
		double d = pos.getX() + 0.5 + 0.1 * direction.getOffsetX() + 0.2 * direction.getOffsetX();
		double e = pos.getY() + 0.5 + 0.1 * direction.getOffsetY() + 0.2 * direction.getOffsetY();
		double f = pos.getZ() + 0.5 + 0.1 * direction.getOffsetZ() + 0.2 * direction.getOffsetZ();
		world.addParticle(particle, d, e, f, 0.0, 0.0, 0.0);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(POWER) > 0 && random.nextFloat() < 0.25F) spawnParticles(state, world, pos, new DustParticleEffect(DustParticleEffect.RED, 0.5F));
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
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return state.get(FACING).getOpposite() == direction && !state.canPlaceAt(world, pos)
                ? Blocks.AIR.getDefaultState(): super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.offset(state.get(FACING).getOpposite());
		return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, state.get(FACING));
	}

	@Override
	protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!moved && !state.isOf(newState.getBlock())) {
			this.updateNeighbors(state, world, pos);
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	private void updateNeighbors(BlockState state, World world, BlockPos pos) {
		world.updateNeighborsAlways(pos, this);
		world.updateNeighborsAlways(pos.offset(state.get(FACING).getOpposite()), this);
	}

	@Override
	protected boolean emitsRedstonePower(BlockState state) {
		return true;
	}

	@Override
	protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return state.get(POWER);
	}

	@Override
	protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return state.get(FACING) == direction ? state.get(POWER) : 0;
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}
}