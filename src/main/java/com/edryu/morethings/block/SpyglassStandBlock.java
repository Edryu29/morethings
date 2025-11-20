package com.edryu.morethings.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpyglassStandBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<SpyglassStandBlock> CODEC = Block.simpleCodec(SpyglassStandBlock::new);

    public SpyglassStandBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

	@Override
	protected MapCodec<? extends SpyglassStandBlock> codec() {
		return CODEC;
	}

    @Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState neighborState = level.getBlockState(pos.below());
		return neighborState.isFaceSturdy(level, pos.below(), Direction.UP, SupportType.CENTER);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Block.box(4, 0, 4, 12, 11, 12);
	}
}