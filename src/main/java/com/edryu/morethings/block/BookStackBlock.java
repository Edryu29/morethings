package com.edryu.morethings.block;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BookStackBlock extends WaterloggedHorizontalBlock {
	public static final IntegerProperty BOOKS = IntegerProperty.create("books", 0, 3);

    protected static final VoxelShape BOOKS_SHAPE_Z1 = Block.box(6, 0, 4, 10, 10, 12);
    protected static final VoxelShape BOOKS_SHAPE_Z2 = Block.box(1, 0, 3, 13, 10, 12);
    protected static final VoxelShape BOOKS_SHAPE_Z3 = Block.box(0, 0, 4, 15, 10, 13);
    protected static final VoxelShape BOOKS_SHAPE_Z4 = Block.box(0, 0, 4, 16, 10, 13);
    
    protected static final VoxelShape BOOKS_SHAPE_X1 = Block.box(4, 0, 6, 12, 10, 10);
    protected static final VoxelShape BOOKS_SHAPE_X2 = Block.box(3, 0, 3, 12, 10, 15);
    protected static final VoxelShape BOOKS_SHAPE_X3 = Block.box(4, 0, 1, 13, 10, 16);
    protected static final VoxelShape BOOKS_SHAPE_X4 = Block.box(4, 0, 0, 13, 10, 16);

    public BookStackBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH).setValue(BOOKS, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, BOOKS);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!Screen.hasShiftDown()) return InteractionResult.PASS;
        level.setBlockAndUpdate(pos, state.setValue(BOOKS, (state.getValue(BOOKS) + 1) % 4));
        level.playSound(player, pos, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F);
        return InteractionResult.SUCCESS;
    }
    
    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState neighborState = level.getBlockState(pos.below());
		return neighborState.isFaceSturdy(level, pos.below(), Direction.UP, SupportType.CENTER);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
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