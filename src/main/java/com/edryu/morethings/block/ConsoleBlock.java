package com.edryu.morethings.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ConsoleBlock extends LeverBlock {
    protected static final VoxelShape CEILING_X_SHAPE = Block.createCuboidShape(3, 14, 4, 13, 16, 12);
    protected static final VoxelShape CEILING_Z_SHAPE = Block.createCuboidShape(4, 14, 3, 12, 16, 13);
    protected static final VoxelShape FLOOR_X_SHAPE = Block.createCuboidShape(3, 0, 4, 13, 2, 12);
    protected static final VoxelShape FLOOR_Z_SHAPE = Block.createCuboidShape(4, 0, 3, 12, 2, 13);
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(4, 3, 14, 12, 13, 16);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(4, 3, 0, 12, 13, 2);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0, 3, 4, 2, 13, 12);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(14, 3, 4, 16, 13, 12);

    public ConsoleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        switch (state.get(FACE)) {
            case FLOOR:
                return direction.getAxis() == Direction.Axis.X ? FLOOR_X_SHAPE : FLOOR_Z_SHAPE;
            case WALL:
                switch (direction) {
                    case NORTH:
					default:
                        return NORTH_SHAPE;
                    case EAST:
                        return EAST_SHAPE;
                    case SOUTH:
                        return SOUTH_SHAPE;
                    case WEST:
                        return WEST_SHAPE;
                }
            default:
                return direction.getAxis() == Direction.Axis.X ? CEILING_X_SHAPE : CEILING_Z_SHAPE;
        }
    }

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.isClient) {
			state.cycle(POWERED);
			return ActionResult.SUCCESS;
		} else {
			this.togglePower(state, world, pos, null);
			return ActionResult.CONSUME;
		}
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {}
}