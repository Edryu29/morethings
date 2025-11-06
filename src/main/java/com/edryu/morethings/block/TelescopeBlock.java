package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;


public class TelescopeBlock extends HorizontalFacingBlock {
    public static final MapCodec<TelescopeBlock> CODEC = Block.createCodec(TelescopeBlock::new);
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

    protected static final VoxelShape SHAPE_BOTTOM = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
    protected static final VoxelShape SHAPE_TOP = Block.createCuboidShape(4, 0, 4, 12, 8, 12);

    public TelescopeBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER));
    }

	@Override
	protected MapCodec<? extends TelescopeBlock> getCodec() {
		return CODEC;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(HALF) == DoubleBlockHalf.UPPER ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockPos blockPos = ctx.getBlockPos();
		World world = ctx.getWorld();
		return blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx) ? super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite()) : null;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		BlockPos blockPos = pos.up();
		world.setBlockState(blockPos, withWaterloggedState(world, blockPos, this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(Properties.HORIZONTAL_FACING, state.get(FACING))), Block.NOTIFY_ALL);
	}

    // Copied from TallPlantBlock class
	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		DoubleBlockHalf doubleBlockHalf = state.get(HALF);
		if (direction.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf) {
			return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)
				? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
		} else {
			return Blocks.AIR.getDefaultState();
		}
	}

    // Copied from TallPlantBlock class
	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		if (state.get(HALF) != DoubleBlockHalf.UPPER) {
			return super.canPlaceAt(state, world, pos);
		} else {
			BlockState blockState = world.getBlockState(pos.down());
			return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
		}
	}

    // Copied from TallPlantBlock class
	public static void placeAt(WorldAccess world, BlockState state, BlockPos pos, int flags) {
		BlockPos blockPos = pos.up();
		world.setBlockState(pos, withWaterloggedState(world, pos, state.with(HALF, DoubleBlockHalf.LOWER)), flags);
		world.setBlockState(blockPos, withWaterloggedState(world, blockPos, state.with(HALF, DoubleBlockHalf.UPPER)), flags);
	}

	public static BlockState withWaterloggedState(WorldView world, BlockPos pos, BlockState state) {
		return state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, world.isWater(pos)) : state;
	}

    // Copied from TallPlantBlock class
	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
		super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, tool);
	}

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING, HALF);
    }
}