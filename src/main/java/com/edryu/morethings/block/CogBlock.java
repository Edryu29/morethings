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
import net.minecraft.world.WorldAccess;
import net.minecraft.util.math.MathHelper;

public class CogBlock extends Block {
	public static final IntProperty POWER = Properties.POWER;

    public CogBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(POWER, 0));
    }

    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
        int pow = MathHelper.clamp(ctx.getWorld().getReceivedStrongRedstonePower(ctx.getBlockPos()), 0, 15);
		return this.getDefaultState().with(POWER, pow);
	}

	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        int pow = MathHelper.clamp(world.getReceivedStrongRedstonePower(pos), 0, 15);
		return super.getStateForNeighborUpdate(getDefaultState().with(POWER, pow), direction, neighborState, world, pos, neighborPos);
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