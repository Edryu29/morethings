package com.edryu.morethings.block;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlobeBlock extends WaterloggedHorizontalBlock {
	public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 2);

    public GlobeBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH).setValue(VARIANT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, VARIANT);
    }
    
    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState neighborState = level.getBlockState(pos.below());
		return neighborState.isFaceSturdy(level, pos.below(), Direction.UP, SupportType.CENTER);
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!Screen.hasShiftDown()) return InteractionResult.PASS;
        level.setBlockAndUpdate(pos, state.setValue(VARIANT, (state.getValue(VARIANT) + 1) % 3));
        level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
        return InteractionResult.SUCCESS;
    }

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Block.box(2, 0, 2, 14, 15, 14);
	}
}