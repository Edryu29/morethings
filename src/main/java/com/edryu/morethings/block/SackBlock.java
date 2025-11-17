package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.entity.SackBlockEntity;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SackBlock extends WaterloggableBlock implements EntityBlock {
    public static final MapCodec<SackBlock> CODEC = simpleCodec(SackBlock::new);
    
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public SackBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, OPEN);
    }

    @Override
    protected MapCodec<? extends SackBlock> codec() {
        return simpleCodec(SackBlock::new);
    }
 
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SackBlockEntity(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(OPEN)){
            return Shapes.or(
                Shapes.box(0.125f, 0f, 0.125f, 0.875f, 0.75f, 0.875f),
                Shapes.box(0.1875f, 0.75f, 0.1875f, 0.8125f, 0.875f, 0.8125f)
            );
        } else {
            return Shapes.or(
                Shapes.box(0.125f, 0f, 0.125f, 0.875f, 0.75f, 0.875f),
                Shapes.box(0.3125f, 0.8125f, 0.3125f, 0.6875f, 1f, 0.6875f),
                Shapes.box(0.375f, 0.75f, 0.375f, 0.625f, 0.8125f, 0.625f)
            );
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            MenuProvider menuProvider = state.getMenuProvider(level, pos);
            if (menuProvider != null) player.openMenu(menuProvider);
        }
        return InteractionResult.SUCCESS;
    }
 
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston)  {
		if (!state.is(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof SackBlockEntity SackBlockEntity) Containers.dropContents(level, pos, SackBlockEntity);
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
    }

	@Override
	protected boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
		super.triggerEvent(state, level, pos, id, param);
		BlockEntity blockEntity = level.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.triggerEvent(id, param);
	}

	@Nullable
	@Override
	protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		return blockEntity instanceof MenuProvider ? (MenuProvider)blockEntity : null;
	}
}