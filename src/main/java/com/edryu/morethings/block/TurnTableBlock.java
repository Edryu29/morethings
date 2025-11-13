package com.edryu.morethings.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
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
import net.minecraft.world.World;

public class TurnTableBlock extends Block {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
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
		return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
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
        if (world.getBlockState(pos).get(POWER) != 0 && (powerUpdated || sourcePos.equals(pos.offset(state.get(FACING))))) this.tryRotate(world, pos);
	}

    private void tryRotate(World world, BlockPos pos) {}

    public boolean updatePower(BlockState state, World world, BlockPos pos) {
        int bestNeighborPower = world.getReceivedRedstonePower(pos);
        int currentPower = state.get(POWER);
        if (bestNeighborPower != currentPower) {
            world.setBlockState(pos, state.with(POWER, bestNeighborPower).with(ROTATING, bestNeighborPower > 0));
            return true;
        }
        return false;
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