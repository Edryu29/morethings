package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TelescopeBlock extends DoublePlantBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape SHAPE_BOTTOM = Block.box(4, 0, 4, 12, 16, 12);
    protected static final VoxelShape SHAPE_TOP = Block.box(4, 0, 4, 12, 8, 12);

    public TelescopeBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos pos = context.getClickedPos();
		Level level = context.getLevel();
		return pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context) ? super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()) : null;
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		BlockPos blockPos = pos.above();
		level.setBlockAndUpdate(blockPos, copyWaterloggedFrom(level, blockPos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(FACING, state.getValue(FACING))));
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState neighborState = level.getBlockState(pos.below());
		if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return neighborState.is(this) && neighborState.getValue(HALF) == DoubleBlockHalf.LOWER;
		return neighborState.isFaceSturdy(level, pos.below(), Direction.UP, SupportType.CENTER);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(HALF) == DoubleBlockHalf.UPPER ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Override
	protected BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
}