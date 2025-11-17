package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class CogBlock extends Block {
	public static final IntegerProperty POWER = BlockStateProperties.POWER;

    public CogBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(POWER, 0));
    }

    @Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(POWER, ctx.getLevel().getBestNeighborSignal(ctx.getClickedPos()));
	}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }

	@Override
	protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
		super.neighborChanged(state, world, pos, sourceBlock, sourcePos, notify);
		world.setBlockAndUpdate(pos, state.setValue(POWER, world.getBestNeighborSignal(pos)));
	}

	@Override
	protected boolean isSignalSource(BlockState state) {
		return true;
	}

	@Override
	protected int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
		return Math.max(0, state.getValue(POWER) - 1);
	}
}