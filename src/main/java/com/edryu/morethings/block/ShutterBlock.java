package com.edryu.morethings.block;

import com.edryu.morethings.util.BlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShutterBlock extends WaterloggableBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LEFT = BooleanProperty.create("rotating");
    public static final EnumProperty<BlockProperties.VerticalConnectingType> TYPE = BlockProperties.VERTICAL_CONNECTING_TYPE;

    protected static final VoxelShape SHAPE_1 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape SHAPE_2 = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_3 = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_4 = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

    public ShutterBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false).setValue(POWERED, false).setValue(LEFT, false).setValue(TYPE, BlockProperties.VerticalConnectingType.NONE));
    }

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE_1;
	}

	// @Nullable
	// @Override
	// public BlockState getPlacementState(ItemPlacementContext ctx) {
    //     Direction facing = ctx.getHorizontalPlayerFacing().getOpposite();
    //     BlockState blockState = this.getDefaultState().with(FACING, facing);

    //     WorldAccess world = ctx.getWorld();
    //     BlockPos clickedPos = ctx.getBlockPos();
    //     Vec3d clickLocation = ctx.getHitPos();

    //     boolean left;
    //     if (facing.getAxis() == Direction.Axis.X) {
    //         left = clickLocation.z - (double) clickedPos.getZ() > 0.5D;
    //     } else {
    //         left = clickLocation.x - (double) clickedPos.getX() > 0.5D;
    //     }
    //     if (context.getNearestLookingDirection() == Direction.NORTH || context.getNearestLookingDirection() == Direction.EAST)
    //         left = !left;
    //     blockState = blockState.setValue(LEFT, left);

    //     if (world.hasNeighborSignal(clickedPos)) {
    //         blockState = blockState.setValue(OPEN, true).setValue(POWERED, true);
    //     }

    //     blockState = blockState.setValue(TYPE, getType(blockState, world.getBlockState(clickedPos.above()), world.getBlockState(clickedPos.below())));

    //     return blockState.setValue(WATERLOGGED, world.getFluidState(clickedPos).getType() == Fluids.WATER);
	// }
}
