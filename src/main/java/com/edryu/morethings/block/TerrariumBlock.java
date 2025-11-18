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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TerrariumBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<TerrariumBlock> CODEC = Block.simpleCodec(TerrariumBlock::new);

    public TerrariumBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

	@Override
	protected MapCodec<? extends TerrariumBlock> codec() {
		return CODEC;
	}

    @Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.or(
            Shapes.box(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f),
            Shapes.box(0.0625f, 0.125f, 0.0625f, 0.9375f, 0.875f, 0.9375f)
        );
	}
}