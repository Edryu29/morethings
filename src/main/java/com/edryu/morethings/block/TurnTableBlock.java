package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.registry.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class TurnTableBlock extends Block {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;
    public static final BooleanProperty ROTATING = BooleanProperty.create("rotating");

    public TurnTableBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWER, 0).setValue(INVERTED, false).setValue(ROTATING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWER, INVERTED, ROTATING);
    }

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(FACING, ctx.getNearestLookingDirection().getOpposite());
	}

	@Override
	protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborChanged(state, world, pos, sourceBlock, sourcePos, notify);
        boolean powerUpdated = this.updatePower(state, world, pos);
        state = world.getBlockState(pos);
        if (state.getValue(POWER) != 0 && (powerUpdated || sourcePos.equals(pos.relative(state.getValue(FACING))))) {
            if (this.rotateFacingBlock(state, world, pos)) this.scheduleTick(world, pos, state);
        }
	}

	@Override
	protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (state.getValue(ROTATING)) {
			if (this.rotateFacingBlock(state, world, pos)) this.scheduleTick(world, pos, state);
        }
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (hit.getDirection() != state.getValue(FACING) && hit.getDirection() != state.getValue(FACING).getOpposite()) {
            state = state.cycle(INVERTED);
            world.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, state.getValue(INVERTED) ? 0.55F : 0.5F);
            world.setBlockAndUpdate(pos, state);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
	public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(world, pos, state, entity);
        this.rotateEntity(world, pos, state, entity);
	}

    @Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (this.updatePower(state, world, pos) && world.getBlockState(pos).getValue(POWER) != 0) this.rotateFacingBlock(state, world, pos);
	}

    private boolean updatePower(BlockState state, Level world, BlockPos pos) {
        int receivedPower = world.getBestNeighborSignal(pos);
        if (receivedPower != state.getValue(POWER)) {
            world.setBlockAndUpdate(pos, state.setValue(POWER, receivedPower).setValue(ROTATING, receivedPower != 0));
            return true;
        }
        return false;
    }

	private void scheduleTick(LevelAccessor world, BlockPos pos, BlockState state) {
		if (!world.isClientSide() && !world.getBlockTicks().hasScheduledTick(pos, this)) world.scheduleTick(pos, this, getPeriod(state));
	}

    private int getPeriod(BlockState state) {
        return (60 - state.getValue(POWER) * 4) + 4;
    }

    private boolean rotateFacingBlock(BlockState state, Level world, BlockPos pos) {
        Direction direction = state.getValue(FACING);
		BlockPos facingPos = pos.relative(direction);
		BlockState facingState = world.getBlockState(facingPos);
		Block facingBlock = facingState.getBlock();

        if (facingState.isAir()) return false;

        if (facingBlock instanceof PulleyBlock && direction.getAxis() == facingState.getValue(PulleyBlock.AXIS)){
			boolean result = ((PulleyBlock)facingBlock).windPulley(facingState, world, facingPos, state.getValue(INVERTED), direction);
            if (result) world.playSound(null, facingPos, SoundRegistry.BLOCK_ROTATE, SoundSource.BLOCKS, 1.0F, 1);
		} else {
            BlockState rotatedState = facingState.rotate(state.getValue(INVERTED) ? Rotation.COUNTERCLOCKWISE_90 : Rotation.CLOCKWISE_90);
            world.setBlock(facingPos, rotatedState, Block.UPDATE_CLIENTS);

            if (facingState.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
                DoubleBlockHalf doubleBlockHalf = facingState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
                BlockPos doubleBlockHalfPos = doubleBlockHalf == DoubleBlockHalf.LOWER ? facingPos.above() : facingPos.below();
                BlockState doubleBlockHalfState = world.getBlockState(doubleBlockHalfPos);
                BlockState doubleBlockHalfRotatedState = doubleBlockHalfState.rotate(state.getValue(INVERTED) ? Rotation.COUNTERCLOCKWISE_90 : Rotation.CLOCKWISE_90);
                world.setBlock(doubleBlockHalfPos, doubleBlockHalfRotatedState, Block.UPDATE_CLIENTS);
            }

            world.playSound(null, facingPos, SoundRegistry.BLOCK_ROTATE, SoundSource.BLOCKS, 1.0F, 1);
            world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        }
        return true;
    }

	private void rotateEntity(Level world, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(world, pos, state, entity);
        if (!entity.onGround()) return;
        if (state.getValue(POWER) != 0 && state.getValue(FACING) == Direction.UP) {
            float period = this.getPeriod(state) + 1;
            float angleIncrement = 90f / period;
            float increment = state.getValue(INVERTED) ? angleIncrement : -1 * angleIncrement;

            Vec3 origin = new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            Vec3 oldPos = entity.position();
            Vec3 oldOffset = oldPos.subtract(origin);
            Vec3 newOffset = rotateY(oldOffset, increment);
            Vec3 posDiff = origin.add(newOffset).subtract(oldPos);
            entity.move(MoverType.SHULKER_BOX, posDiff);

            entity.setYRot(entity.getYRot() - increment);
            entity.setYHeadRot(entity.getYHeadRot() - increment);
            entity.setYBodyRot(entity.getVisualRotationYInDegrees() - increment);
        }
	}

    private Vec3 rotateY(Vec3 vec, float deg) {
        if (deg == 0) return vec;
        if (vec == Vec3.ZERO) return vec;
        double x = vec.x;
        double y = vec.y;
        double z = vec.z;
        float angle = deg * Mth.DEG_TO_RAD;
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        return new Vec3(x * c + z * s, y, z * c - x * s);
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