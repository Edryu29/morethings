package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BookPileHorizontalBlock extends BookPileBlock {
    protected static final VoxelShape BOOKS_SHAPE_Z1 = Block.box(6, 0, 4, 10, 10, 12);
    protected static final VoxelShape BOOKS_SHAPE_Z2 = Block.box(1, 0, 3, 13, 10, 12);
    protected static final VoxelShape BOOKS_SHAPE_Z3 = Block.box(0, 0, 4, 15, 10, 13);
    protected static final VoxelShape BOOKS_SHAPE_Z4 = Block.box(0, 0, 4, 16, 10, 13);
    
    protected static final VoxelShape BOOKS_SHAPE_X1 = Block.box(4, 0, 6, 12, 10, 10);
    protected static final VoxelShape BOOKS_SHAPE_X2 = Block.box(3, 0, 3, 12, 10, 15);
    protected static final VoxelShape BOOKS_SHAPE_X3 = Block.box(4, 0, 1, 13, 10, 16);
    protected static final VoxelShape BOOKS_SHAPE_X4 = Block.box(4, 0, 0, 13, 10, 16);

    public BookPileHorizontalBlock(Properties settings) {
        super(settings);
    }

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case NORTH:
            case SOUTH:
            default:
                switch (state.getValue(BOOKS)) {
                    case 0:
                    default:
                        return BOOKS_SHAPE_Z1;
                    case 1:
                        return BOOKS_SHAPE_Z2;
                    case 2:
                        return BOOKS_SHAPE_Z3;
                    case 3:
                        return BOOKS_SHAPE_Z4;
                }
            case EAST:
            case WEST:
                switch (state.getValue(BOOKS)) {
                    case 0:
                    default:
                        return BOOKS_SHAPE_X1;
                    case 1:
                        return BOOKS_SHAPE_X2;
                    case 2:
                        return BOOKS_SHAPE_X3;
                    case 3:
                        return BOOKS_SHAPE_X4;
                }
        }
	}
}