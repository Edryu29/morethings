package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BoatInAJarBlock extends WaterloggedHorizontalBlock {
    protected static final VoxelShape SHAPE_Z = Block.box(0, 0, 3, 16, 12, 13);
    protected static final VoxelShape SHAPE_X = Block.box(3, 0, 0, 13, 12, 16);

    public BoatInAJarBlock(Properties settings) {
        super(settings);
    }

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		switch (state.getValue(FACING)) {
			case NORTH:
			case SOUTH:
			default:
				return SHAPE_Z;
			case EAST:
			case WEST:
				return SHAPE_X;
		}
	}
}