package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BigChainBlock extends ChainBlock {
    protected static final VoxelShape X_SHAPE = Block.box(0, 4, 4, 16, 12, 12);
    protected static final VoxelShape Y_SHAPE = Block.box(4, 0, 4, 12, 16, 12);
    protected static final VoxelShape Z_SHAPE = Block.box(4, 4, 0, 12, 12, 16);

    public BigChainBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(AXIS, Direction.Axis.Y));
    }

    @Override
   	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AXIS);
    }

	@Override
   protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		switch ((Direction.Axis)blockState.getValue(AXIS)) {
			case X:
			default:
				return X_SHAPE;
			case Y:
				return Y_SHAPE;
			case Z:
				return Z_SHAPE;
		}
	}
}