package com.edryu.morethings.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CogBlock extends Block {
	public static final IntProperty POWER = Properties.POWER;

    public CogBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(POWER, 0));
    }

    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(POWER, ctx.getWorld().getReceivedRedstonePower(ctx.getBlockPos()));
	}

	@Override
	protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
		super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
		world.setBlockState(pos, state.with(POWER, world.getReceivedRedstonePower(pos)));
	}

	@Override
	protected boolean emitsRedstonePower(BlockState state) {
		return true;
	}

	@Override
	protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return Math.max(0, state.get(POWER) - 1);
	}

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }
}