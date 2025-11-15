package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.entity.PulleyBlockEntity;
import com.edryu.morethings.util.BlockProperties;
import com.edryu.morethings.util.BlockProperties.Winding;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PulleyBlock extends Block implements BlockEntityProvider {
	public static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;
    public static final BooleanProperty FLIPPED = BooleanProperty.of("flipped");
    public static final EnumProperty<Winding> WINDING = BlockProperties.WINDING;

    public PulleyBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(AXIS, Direction.Axis.X).with(FLIPPED, false).with(WINDING, Winding.NONE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS, FLIPPED, WINDING);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PulleyBlockEntity(pos, state);
    }

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(AXIS, ctx.getHorizontalPlayerFacing().getOpposite().getAxis());
	}

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if (screenHandlerFactory != null) player.openHandledScreen(screenHandlerFactory);
        }
        return ActionResult.SUCCESS;
    }
    
    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof PulleyBlockEntity PulleyBlockEntity) ItemScatterer.spawn(world, pos, PulleyBlockEntity);
        return super.onBreak(world, pos, state, player);
    }

	@Override
	protected boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
		super.onSyncedBlockEvent(state, world, pos, type, data);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.onSyncedBlockEvent(type, data);
	}

	@Nullable
	@Override
	protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)blockEntity : null;
	}

    public void windPulley(BlockState state, World world, BlockPos pos, boolean retract) {
        if (world.getBlockEntity(pos) instanceof PulleyBlockEntity pulleyEntity) pulleyEntity.pullWinding(retract);
    }
}