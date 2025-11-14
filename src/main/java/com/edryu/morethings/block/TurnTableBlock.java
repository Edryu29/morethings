package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.registry.SoundRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class TurnTableBlock extends Block {
	public static final DirectionProperty FACING = Properties.FACING;
    public static final IntProperty POWER = Properties.POWER;
    public static final BooleanProperty INVERTED = Properties.INVERTED;
    public static final BooleanProperty ROTATING = BooleanProperty.of("rotating");

    public TurnTableBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWER, 0).with(INVERTED, false).with(ROTATING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWER, INVERTED, ROTATING);
    }

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
	}

	@Override
	protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        boolean powerUpdated = this.updatePower(state, world, pos);
        state = world.getBlockState(pos);
        if (state.get(POWER) != 0 && (powerUpdated || sourcePos.equals(pos.offset(state.get(FACING))))) {
            if (this.rotateFacingBlock(state, world, pos)) this.scheduleTick(world, pos, state);
        }
	}

	@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(ROTATING)) {
			if (this.rotateFacingBlock(state, world, pos)) this.scheduleTick(world, pos, state);
        }
	}

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (hit.getSide() != state.get(FACING) && hit.getSide() != state.get(FACING).getOpposite()) {
            state = state.cycle(INVERTED);
            world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, state.get(INVERTED) ? 0.55F : 0.5F);
            world.setBlockState(pos, state);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);
        this.rotateEntity(world, pos, state, entity);
	}

    @Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (this.updatePower(state, world, pos) && world.getBlockState(pos).get(POWER) != 0) this.rotateFacingBlock(state, world, pos);
	}

    private boolean updatePower(BlockState state, World world, BlockPos pos) {
        int receivedPower = world.getReceivedRedstonePower(pos);
        if (receivedPower != state.get(POWER)) {
            world.setBlockState(pos, state.with(POWER, receivedPower).with(ROTATING, receivedPower != 0));
            return true;
        }
        return false;
    }

	private void scheduleTick(WorldAccess world, BlockPos pos, BlockState state) {
		if (!world.isClient() && !world.getBlockTickScheduler().isQueued(pos, this)) world.scheduleBlockTick(pos, this, getPeriod(state));
	}

    private int getPeriod(BlockState state) {
        return (60 - state.get(POWER) * 4) + 4;
    }

    private boolean rotateFacingBlock(BlockState state, World world, BlockPos pos) {
        Direction direction = state.get(FACING);
		BlockPos facingPos = pos.offset(direction);
		BlockState facingState = world.getBlockState(facingPos);
		Block facingBlock = facingState.getBlock();

        if (facingState.isAir()) return false;

        if (facingBlock instanceof PulleyBlock && direction.getAxis() == facingState.get(PulleyBlock.AXIS)){
			((PulleyBlock)facingBlock).windPulley(facingState, world, facingPos, state.get(INVERTED));
		} else {
            BlockState rotatedState = facingState.rotate(state.get(INVERTED) ? BlockRotation.COUNTERCLOCKWISE_90 : BlockRotation.CLOCKWISE_90);
            world.setBlockState(facingPos, rotatedState, Block.NOTIFY_LISTENERS);

            if (facingState.contains(Properties.DOUBLE_BLOCK_HALF)) {
                DoubleBlockHalf doubleBlockHalf = facingState.get(Properties.DOUBLE_BLOCK_HALF);
                BlockPos doubleBlockHalfPos = doubleBlockHalf == DoubleBlockHalf.LOWER ? facingPos.up() : facingPos.down();
                BlockState doubleBlockHalfState = world.getBlockState(doubleBlockHalfPos);
                BlockState doubleBlockHalfRotatedState = doubleBlockHalfState.rotate(state.get(INVERTED) ? BlockRotation.COUNTERCLOCKWISE_90 : BlockRotation.CLOCKWISE_90);
                world.setBlockState(doubleBlockHalfPos, doubleBlockHalfRotatedState, Block.NOTIFY_LISTENERS);
            }

            world.playSound(null, facingPos, SoundRegistry.BLOCK_ROTATE, SoundCategory.BLOCKS, 1.0F, 1);
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        }
        return true;
    }

	private void rotateEntity(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);
        if (!entity.isOnGround()) return;
        if (state.get(POWER) != 0 && state.get(FACING) == Direction.UP) {
            float period = this.getPeriod(state) + 1;
            float angleIncrement = 90f / period;
            float increment = state.get(INVERTED) ? angleIncrement : -1 * angleIncrement;

            Vec3d origin = new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            Vec3d oldPos = entity.getPos();
            Vec3d oldOffset = oldPos.subtract(origin);
            Vec3d newOffset = rotateY(oldOffset, increment);
            Vec3d posDiff = origin.add(newOffset).subtract(oldPos);
            entity.move(MovementType.SHULKER_BOX, posDiff);

            entity.setYaw(entity.getYaw() - increment);
            entity.setHeadYaw(entity.getHeadYaw() - increment);
            entity.setBodyYaw(entity.getBodyYaw() - increment);
        }
	}

    private Vec3d rotateY(Vec3d vec, float deg) {
        if (deg == 0) return vec;
        if (vec == Vec3d.ZERO) return vec;
        double x = vec.x;
        double y = vec.y;
        double z = vec.z;
        float angle = deg * MathHelper.RADIANS_PER_DEGREE;
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        return new Vec3d(x * c + z * s, y, z * c - x * s);
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