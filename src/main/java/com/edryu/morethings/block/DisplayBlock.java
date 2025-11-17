package com.edryu.morethings.block;

import com.edryu.morethings.entity.DisplayBlockEntity;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DisplayBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public DisplayBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected MapCodec<? extends DisplayBlock> codec() {
        return null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DisplayBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(Block.box(1, 0, 1, 15, 7, 15), Block.box(2, 7, 2, 14, 16, 14));
    }

    @Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        ItemStack playerHeldItem = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (level.getBlockEntity(pos) instanceof DisplayBlockEntity DisplayBlockEntity) {
            ItemStack storedItem = DisplayBlockEntity.getStoredItem();
            ItemEntity storedItemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), storedItem);

            if (storedItem.isEmpty() ) {
                DisplayBlockEntity.setStoredItem(playerHeldItem.split(1));
                level.playSound(player, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
                level.addFreshEntity(storedItemEntity);
                level.playSound(player, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                DisplayBlockEntity.removeStoredItem();
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.getBlockEntity(pos) instanceof DisplayBlockEntity DisplayBlockEntity) {
            ItemStack storedItem = DisplayBlockEntity.getStoredItem();
            if (!storedItem.isEmpty()) {
                ItemEntity storedItemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), storedItem);
                level.addFreshEntity(storedItemEntity);
                DisplayBlockEntity.removeStoredItem();
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
