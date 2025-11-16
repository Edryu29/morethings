package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.entity.SackBlockEntity;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SackBlock extends WaterloggableBlock implements BlockEntityProvider {
    public static final MapCodec<SackBlock> CODEC = createCodec(SackBlock::new);
    
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final BooleanProperty OPEN = BooleanProperty.of("open");

    public SackBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WATERLOGGED, false).with(OPEN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, OPEN);
    }

    @Override
    protected MapCodec<? extends SackBlock> getCodec() {
        return createCodec(SackBlock::new);
    }
 
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SackBlockEntity(pos, state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        if (state.get(OPEN)){
            return VoxelShapes.union(
                VoxelShapes.cuboid(0.125f, 0f, 0.125f, 0.875f, 0.75f, 0.875f),
                VoxelShapes.cuboid(0.1875f, 0.75f, 0.1875f, 0.8125f, 0.875f, 0.8125f)
            );
        } else {
            return VoxelShapes.union(
                VoxelShapes.cuboid(0.125f, 0f, 0.125f, 0.875f, 0.75f, 0.875f),
                VoxelShapes.cuboid(0.3125f, 0.8125f, 0.3125f, 0.6875f, 1f, 0.6875f),
                VoxelShapes.cuboid(0.375f, 0.75f, 0.375f, 0.625f, 0.8125f, 0.625f)
            );
        }
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
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved)  {
		if (!state.isOf(newState.getBlock())) {
			if (world.getBlockEntity(pos) instanceof SackBlockEntity SackBlockEntity) ItemScatterer.spawn(world, pos, SackBlockEntity);
		}
		super.onStateReplaced(state, world, pos, newState, moved);
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
}