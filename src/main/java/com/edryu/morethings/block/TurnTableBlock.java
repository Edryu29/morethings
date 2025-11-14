package com.edryu.morethings.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class TurnTableBlock extends Block {
	public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty INVERTED = Properties.INVERTED;
    public static final IntProperty POWER = Properties.POWER;

    public TurnTableBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(INVERTED, false).with(POWER, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, INVERTED, POWER);
    }

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
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
	protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        boolean powerUpdated = this.updatePower(state, world, pos);
        if (world.getBlockState(pos).get(POWERED) && powerUpdated) this.scheduleTick(world, pos, state);
	}

	@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(POWERED)) {
			this.turnFacingBlock(state, world, pos);
            world.scheduleBlockTick(pos, this, getPeriod(state));
        }
	}

    private void turnFacingBlock(BlockState state, World world, BlockPos pos) {
        Direction direction = state.get(FACING);
		BlockPos facingPos = pos.offset(direction);
		BlockState facingState = world.getBlockState(facingPos);
		Block facingBlock = facingState.getBlock();

        if (facingBlock instanceof PulleyBlock && direction.getAxis() == facingState.get(PulleyBlock.AXIS)){
			((PulleyBlock)facingBlock).windPulley(facingState, world, facingPos, state.get(INVERTED));
			return;
		}

		BlockState rotatedState = facingState.rotate(state.get(INVERTED) ? BlockRotation.COUNTERCLOCKWISE_90 : BlockRotation.CLOCKWISE_90);
		world.setBlockState(facingPos, rotatedState);
    }

    public boolean updatePower(BlockState state, World world, BlockPos pos) {
        int receivedPower = world.getReceivedRedstonePower(pos);
        if (receivedPower != state.get(POWER)) {
            world.setBlockState(pos, state.with(POWER, receivedPower).with(POWERED, receivedPower != 0));
            return true;
        }
        return false;
    }

    public int getPeriod(BlockState state) {
        return (60 - state.get(POWER) * 4) + 4;
    }

	private void scheduleTick(WorldAccess world, BlockPos pos, BlockState state) {
		if (!world.isClient() && !world.getBlockTickScheduler().isQueued(pos, this)) world.scheduleBlockTick(pos, this, getPeriod(state));
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