package com.edryu.morethings.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class TurnTableBlock extends Block {
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty INVERTED = Properties.INVERTED;
    public static final BooleanProperty ROTATING = BooleanProperty.of("rotating");

    public TurnTableBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(INVERTED, false).with(ROTATING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, INVERTED, ROTATING);
    }
}