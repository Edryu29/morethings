package com.edryu.morethings.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class JarBoatBlock extends HorizontalFacingBlock {
    public static final MapCodec<JarBoatBlock> CODEC = Block.createCodec(JarBoatBlock::new);
	
    protected static final VoxelShape SHAPE_Z = Block.createCuboidShape(0, 0, 3, 16, 12, 14);
    protected static final VoxelShape SHAPE_X = Block.createCuboidShape(3, 0, 0, 14, 12, 16);

    public JarBoatBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

	@Override
	protected MapCodec<? extends JarBoatBlock> getCodec() {
		return CODEC;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch (state.get(FACING)) {
			case NORTH:
			case SOUTH:
			default:
				return SHAPE_Z;
			case WEST:
			case EAST:
				return SHAPE_X;
		}
	}

    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }
}