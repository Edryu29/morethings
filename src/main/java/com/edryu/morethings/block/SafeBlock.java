package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.entity.SafeBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SafeBlock extends WaterloggedHorizontalBlock implements EntityBlock {
	public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public SafeBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, OPEN);
    }
 
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SafeBlockEntity(pos, state);
    }
    
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston)  {
		if (!state.is(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof SafeBlockEntity SafeBlockEntity) Containers.dropContents(level, pos, SafeBlockEntity);
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.box(0.0625f, 0f, 0.0625f, 0.9375f, 1, 0.9375f);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            MenuProvider menuProvider = state.getMenuProvider(level, pos);
            if (menuProvider != null) player.openMenu(menuProvider);
        }
        return InteractionResult.SUCCESS;
    }

	@Nullable
	@Override
	protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		return blockEntity instanceof MenuProvider ? (MenuProvider)blockEntity : null;
	}

	@Override
	protected boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
		super.triggerEvent(state, level, pos, id, param);
		BlockEntity blockEntity = level.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.triggerEvent(id, param);
	}
}