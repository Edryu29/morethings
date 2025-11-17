package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BookPileVerticalBlock extends BookPileBlock {
    protected static final VoxelShape BOOKS_SHAPE_Z1 = Block.box(4, 0, 3, 12, 4, 13);
    protected static final VoxelShape BOOKS_SHAPE_X1 = Block.box(3, 0, 4, 13, 4, 12);
    
    protected static final VoxelShape BOOKS_SHAPE_N2 = Block.box(1, 0, 1, 15, 8, 15);
    protected static final VoxelShape BOOKS_SHAPE_N3 = Block.box(1, 0, 1, 15, 12, 15);
    protected static final VoxelShape BOOKS_SHAPE_N4 = Block.box(1, 0, 1, 15, 16, 15);

    public BookPileVerticalBlock(Properties settings) {
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
                        return BOOKS_SHAPE_N2;
                    case 2:
                        return BOOKS_SHAPE_N3;
                    case 3:
                        return BOOKS_SHAPE_N4;
                }
            case EAST:
            case WEST:
                switch (state.getValue(BOOKS)) {
                    case 0:
                    default:
                        return BOOKS_SHAPE_X1;
                    case 1:
                        return BOOKS_SHAPE_N2;
                    case 2:
                        return BOOKS_SHAPE_N3;
                    case 3:
                        return BOOKS_SHAPE_N4;
                }
        }
	}
}