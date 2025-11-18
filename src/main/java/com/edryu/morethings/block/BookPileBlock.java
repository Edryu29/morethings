package com.edryu.morethings.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BookPileBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<BookPileBlock> CODEC = Block.simpleCodec(BookPileBlock::new);
    
	public static final IntegerProperty BOOKS = IntegerProperty.create("books", 0, 3);

    protected static final VoxelShape BOOKS_SHAPE_Z1 = Block.box(4, 0, 3, 12, 4, 13);
    protected static final VoxelShape BOOKS_SHAPE_X1 = Block.box(3, 0, 4, 13, 4, 12);
    
    protected static final VoxelShape BOOKS_SHAPE_N2 = Block.box(1, 0, 1, 15, 8, 15);
    protected static final VoxelShape BOOKS_SHAPE_N3 = Block.box(1, 0, 1, 15, 12, 15);
    protected static final VoxelShape BOOKS_SHAPE_N4 = Block.box(1, 0, 1, 15, 16, 15);

    public BookPileBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(BOOKS, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BOOKS);
    }

	@Override
	protected MapCodec<? extends BookPileBlock> codec() {
		return CODEC;
	}

    @Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!Screen.hasShiftDown()) return InteractionResult.PASS;
        level.setBlockAndUpdate(pos, state.setValue(BOOKS, (state.getValue(BOOKS) + 1) % 4));
        level.playSound(player, pos, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F);
        return InteractionResult.SUCCESS;
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