package com.edryu.morethings.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BookPileVerticalBlock extends BookPileBlock {

    protected static final VoxelShape BOOKS_SHAPE_Z1 = Block.createCuboidShape(4, 0, 3, 12, 4, 13);
    protected static final VoxelShape BOOKS_SHAPE_Z2 = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
    protected static final VoxelShape BOOKS_SHAPE_Z3 = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
    protected static final VoxelShape BOOKS_SHAPE_Z4 = Block.createCuboidShape(4, 0, 4, 12, 16, 12);

    protected static final VoxelShape BOOKS_SHAPE_X1 = Block.createCuboidShape(3, 0, 4, 13, 4, 12);
    protected static final VoxelShape BOOKS_SHAPE_X2 = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
    protected static final VoxelShape BOOKS_SHAPE_X3 = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
    protected static final VoxelShape BOOKS_SHAPE_X4 = Block.createCuboidShape(4, 0, 4, 12, 16, 12);

    public BookPileVerticalBlock(Settings settings) {
        super(settings);
    }

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case NORTH:
            case SOUTH:
            default:
                switch (state.get(BOOKS)) {
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
            case WEST:
            case EAST:
                switch (state.get(BOOKS)) {
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