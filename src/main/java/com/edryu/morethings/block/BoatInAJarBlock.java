package com.edryu.morethings.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BoatInAJarBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<BoatInAJarBlock> CODEC = Block.simpleCodec(BoatInAJarBlock::new);
	
    protected static final VoxelShape SHAPE_Z = Block.box(0, 0, 3, 16, 12, 13);
    protected static final VoxelShape SHAPE_X = Block.box(3, 0, 0, 13, 12, 16);

    public BoatInAJarBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

	@Override
	protected MapCodec<? extends BoatInAJarBlock> codec() {
		return CODEC;
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

    @Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
}