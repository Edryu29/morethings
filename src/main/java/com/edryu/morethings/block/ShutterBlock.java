package com.edryu.morethings.block;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.util.BlockProperties;
import com.edryu.morethings.util.BlockProperties.Color;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.data.client.VariantSettings.Rotation;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ShutterBlock extends WaterloggableBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty LEFT = BooleanProperty.of("rotating");
    public static final EnumProperty<BlockProperties.VerticalConnectingType> TYPE = BlockProperties.VERTICAL_CONNECTING_TYPE;

    protected static final VoxelShape SHAPE_1 = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape SHAPE_2 = Block.createCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_3 = Block.createCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_4 = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

    public ShutterBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH)
                .with(OPEN, false).with(POWERED, false).with(LEFT, false).with(TYPE, BlockProperties.VerticalConnectingType.NONE));
    }

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
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
