package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PedestalBlock extends WaterloggedBlock {
	public static final BooleanProperty UP = BooleanProperty.create("up");
	public static final BooleanProperty DOWN = BooleanProperty.create("down");

    protected static final VoxelShape SHAPE = Shapes.or(
            Shapes.box(0.1875f, 0.125f, 0.1875f, 0.815f, 0.885f, 0.815f),
            Shapes.box(0.0625f, 0.8125f, 0.0625f, 0.9375f, 1f, 0.9375f),
            Shapes.box(0.0625f, 0f, 0.0625f, 0.9375f, 0.1875f, 0.9375f));
    protected static final VoxelShape SHAPE_UP = Shapes.or(
            Shapes.box(0.1875f, 0.125f, 0.1875f, 0.815f, 1f, 0.815D),
            Shapes.box(0.0625f, 0f, 0.0625f, 0.9375f, 0.1875f, 0.9375D));
    protected static final VoxelShape SHAPE_DOWN = Shapes.or(
            Shapes.box(0.1875f, 0f, 0.1875f, 0.815f, 0.885f, 0.815D),
            Shapes.box(0.0625f, 0.8125f, 0.0625f, 0.9375f, 1f, 0.9375D));
    protected static final VoxelShape SHAPE_UP_DOWN = Shapes.box(0.1875f, 0f, 0.1875f, 0.815f, 1f, 0.815f);

    public PedestalBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, UP, DOWN);
    }

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor level = context.getLevel();
        BlockPos pos = context.getClickedPos();

		boolean wl = level.getFluidState(pos).is(Fluids.WATER);

        boolean up    = canConnectTo(level, pos.above());
        boolean down  = canConnectTo(level, pos.below());
        
        return super.getStateForPlacement(context).setValue(WATERLOGGED, wl).setValue(UP, up).setValue(DOWN, down);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

        boolean up    = canConnectTo(level, pos.above());
        boolean down  = canConnectTo(level, pos.below());

		state = this.defaultBlockState().setValue(WATERLOGGED, state.getValue(WATERLOGGED)).setValue(UP, up).setValue(DOWN, down);
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

    private boolean canConnectTo(LevelAccessor level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof PedestalBlock ? true : false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (!state.getValue(UP)) return !state.getValue(DOWN) ? SHAPE : SHAPE_DOWN;
        else return !state.getValue(DOWN) ? SHAPE_UP : SHAPE_UP_DOWN;
    }
}