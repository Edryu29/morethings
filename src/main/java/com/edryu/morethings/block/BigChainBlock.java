package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BigChainBlock extends ChainBlock {
    protected static final VoxelShape X_AXIS_AABB = Block.box(0, 4, 4, 16, 12, 12);
    protected static final VoxelShape Y_AXIS_AABB = Block.box(4, 0, 4, 12, 16, 12);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(4, 4, 0, 12, 12, 16);

    public BigChainBlock(Properties settings) {
        super(settings);
    }

	@Override
   	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		switch (state.getValue(AXIS)) {
			case X:
			default:
				return X_AXIS_AABB;
			case Y:
				return Y_AXIS_AABB;
			case Z:
				return Z_AXIS_AABB;
		}
	}
}