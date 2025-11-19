package com.edryu.morethings.block;

import com.edryu.morethings.util.BlockProperties;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShutterBlock extends WaterloggableBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
    public static final EnumProperty<BlockProperties.ConnectingType> TYPE = BlockProperties.CONNECTING_TYPE;

    protected static final VoxelShape[] SHAPES = new VoxelShape[] {
        Block.box(0, 0, 0, 16, 16, 3),
        Block.box(13, 0, 0, 16, 16, 16),
        Block.box(0, 0, 13, 16, 16, 16),
        Block.box(0, 0, 0, 3, 16, 16)
    };

    public ShutterBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH)
            .setValue(OPEN, false).setValue(POWERED, false).setValue(LEFT, false).setValue(TYPE, BlockProperties.ConnectingType.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, TYPE, OPEN, LEFT, POWERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Vec3 hitLocation = context.getClickLocation();
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockState state = this.defaultBlockState().setValue(FACING, facing);

        boolean left = (facing.getAxis() == Direction.Axis.X) ? (hitLocation.z - (double) pos.getZ() > 0.5D) : (hitLocation.x - (double) pos.getX() > 0.5D);

        if (context.getNearestLookingDirection() == Direction.NORTH || context.getNearestLookingDirection() == Direction.EAST) left = !left;
        state = state.setValue(LEFT, left);

        if (level.hasNeighborSignal(pos)) state = state.setValue(OPEN, true).setValue(POWERED, true);

        state = state.setValue(TYPE, getType(state, level.getBlockState(pos.above()), level.getBlockState(pos.below())));
        return state.setValue(WATERLOGGED, level.getFluidState(pos).is(Fluids.WATER));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        boolean powered = level.hasNeighborSignal(pos);
        if (powered != state.getValue(POWERED)) {
            if (state.getValue(OPEN) != powered) {
                state = state.setValue(OPEN, powered);
                level.playSound(null, pos, shutterSound(powered), SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            state = state.setValue(POWERED, powered);

            if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        
        BlockProperties.ConnectingType type = getType(state, level.getBlockState(pos.above()), level.getBlockState(pos.below()));
        if (state.getValue(TYPE) != type) state = state.setValue(TYPE, type);

        level.setBlockAndUpdate(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        state = state.cycle(OPEN);
        level.setBlockAndUpdate(pos, state);
        if (!player.isCrouching()) toggleNeighborShutters(state, level, pos, state.getValue(OPEN));
        level.playSound(null, pos, shutterSound(state.getValue(OPEN)), SoundSource.BLOCKS, 1.0F, 1.0F);
        if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return InteractionResult.SUCCESS;
    }

    public void toggleNeighborShutters(BlockState state, Level level, BlockPos pos, boolean open) {
        BlockState updateState = state;
        BlockPos updatePos = pos;
        if (state.getValue(TYPE) == BlockProperties.ConnectingType.MIDDLE || state.getValue(TYPE) == BlockProperties.ConnectingType.BOTTOM) {
            int heightUp = level.dimensionType().height() - updatePos.getY();
            for (int i = 0; i < heightUp; i++) {
                BlockState above = level.getBlockState(updatePos.above());
                if (above.is(state.getBlock()) && above.getValue(FACING) == updateState.getValue(FACING) && above.getValue(LEFT) == updateState.getValue(LEFT) && above.getValue(OPEN) != open) {
                    updateState = above;
                    updatePos = updatePos.above();
                    level.setBlockAndUpdate(updatePos, updateState.setValue(OPEN, open));
                } else break;
            }
        }
        if (state.getValue(TYPE) == BlockProperties.ConnectingType.MIDDLE || state.getValue(TYPE) == BlockProperties.ConnectingType.TOP) {
            updateState = state;
            updatePos = pos;
            int heightDown = level.dimensionType().minY() - updatePos.getY();
            heightDown = (heightDown < 0) ? -heightDown : heightDown;
            for (int i = 0; i < heightDown; i++) {
                BlockState below = level.getBlockState(updatePos.below());
                if (below.is(state.getBlock()) && below.getValue(FACING) == updateState.getValue(FACING) && below.getValue(LEFT) == updateState.getValue(LEFT) && below.getValue(OPEN) != open) {
                    updateState = below;
                    updatePos = updatePos.below();
                    level.setBlockAndUpdate(updatePos, updateState.setValue(OPEN, open));
                } else break;
            }
        }
    }

    public static SoundEvent shutterSound(boolean open) {
        return open ? SoundEvents.BAMBOO_WOOD_DOOR_OPEN : SoundEvents.BAMBOO_WOOD_DOOR_CLOSE;
    }

    public BlockProperties.ConnectingType getType(BlockState state, BlockState above, BlockState below) {
        boolean shape_above_same = above.getBlock() == state.getBlock() && above.getValue(FACING) == state.getValue(FACING)
                                && above.getValue(OPEN) == state.getValue(OPEN) && above.getValue(LEFT) == state.getValue(LEFT);
        boolean shape_below_same = below.getBlock() == state.getBlock() && below.getValue(FACING) == state.getValue(FACING)
                                && below.getValue(OPEN) == state.getValue(OPEN) && below.getValue(LEFT) == state.getValue(LEFT);

        if (shape_above_same && !shape_below_same) return BlockProperties.ConnectingType.BOTTOM;
        else if (!shape_above_same && shape_below_same) return BlockProperties.ConnectingType.TOP;
        else if (shape_above_same) return BlockProperties.ConnectingType.MIDDLE;
        return BlockProperties.ConnectingType.NONE;
    }

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int shape = state.getValue(FACING).get2DDataValue() + (state.getValue(OPEN) ? (state.getValue(LEFT) ? 3 : 1) : 0);
        return SHAPES[shape % 4];
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
