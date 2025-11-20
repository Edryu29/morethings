package com.edryu.morethings.block;

import com.edryu.morethings.entity.DisplayBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DisplayBlock extends WaterloggedHorizontalBlock implements EntityBlock {

    public DisplayBlock(Properties settings) {
        super(settings);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DisplayBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        ItemStack playerHeldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (level.getBlockEntity(pos) instanceof DisplayBlockEntity displayBE) {
            ItemStack storedItem = displayBE.getStoredItem();
            ItemEntity storedItemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), storedItem);

            if (storedItem.isEmpty()) {
                if (playerHeldItem.isEmpty()) return InteractionResult.PASS;
                displayBE.setStoredItem(playerHeldItem.split(1));
                level.playSound(player, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
                level.addFreshEntity(storedItemEntity);
                level.playSound(player, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                displayBE.removeStoredItem();
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (level.getBlockEntity(pos) instanceof DisplayBlockEntity displayBE && !state.is(newState.getBlock())) {
            ItemStack storedItem = displayBE.getStoredItem();
            if (!storedItem.isEmpty()) {
                ItemEntity storedItemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), storedItem);
                level.addFreshEntity(storedItemEntity);
                displayBE.removeStoredItem();
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(Block.box(1, 0, 1, 15, 7, 15), Block.box(2, 7, 2, 14, 16, 14));
    }
}
