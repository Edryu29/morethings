package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ConsoleBlock extends LeverBlock {
    protected static final VoxelShape CEILING_X_SHAPE = Block.box(3, 14, 4, 13, 16, 12);
    protected static final VoxelShape CEILING_Z_SHAPE = Block.box(4, 14, 3, 12, 16, 13);
    protected static final VoxelShape FLOOR_X_SHAPE = Block.box(3, 0, 4, 13, 2, 12);
    protected static final VoxelShape FLOOR_Z_SHAPE = Block.box(4, 0, 3, 12, 2, 13);
    protected static final VoxelShape NORTH_SHAPE = Block.box(4, 3, 14, 12, 13, 16);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(4, 3, 0, 12, 13, 2);
    protected static final VoxelShape EAST_SHAPE = Block.box(0, 3, 4, 2, 13, 12);
    protected static final VoxelShape WEST_SHAPE = Block.box(14, 3, 4, 16, 13, 12);

    public ConsoleBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        switch (state.getValue(FACE)) {
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
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide()) {
			state.cycle(POWERED);
			return InteractionResult.SUCCESS;
		} else {
			this.pull(state, level, pos, null);
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {}
}