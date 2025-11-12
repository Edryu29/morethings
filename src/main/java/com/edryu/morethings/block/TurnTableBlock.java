package com.edryu.morethings.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class TurnTableBlock extends FacingBlock {
    public static final MapCodec<TurnTableBlock> CODEC = Block.createCodec(TurnTableBlock::new);

    public static final BooleanProperty INVERTED = Properties.INVERTED;
    public static final BooleanProperty POWERED = Properties.POWERED;

    public TurnTableBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(INVERTED, false).with(POWERED, false));
    }

	@Override
	protected MapCodec<? extends TurnTableBlock> getCodec() {
		return CODEC;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
	}

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, INVERTED, POWERED);
    }
}