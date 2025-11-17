package com.edryu.morethings.block;

import com.edryu.morethings.entity.SmallPedestalBlockEntity;
import com.edryu.morethings.registry.ItemRegistry;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallPedestalBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final BooleanProperty ROTATE = BooleanProperty.create("rotate");
    public static final BooleanProperty VISIBLE = BooleanProperty.create("visible");

    public SmallPedestalBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(ROTATE, true).setValue(VISIBLE, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ROTATE, VISIBLE);
    }

    @Override
    protected MapCodec<? extends SmallPedestalBlock> codec() {
        return null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SmallPedestalBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
        return Shapes.box(0.1875f, 0f, 0.1875f, 0.8125f, 0.0625f, 0.8125f);
    }

    @Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return super.getStateForPlacement(ctx).setValue(FACING, ctx.getHorizontalDirection().getOpposite());
	}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack playerHeldItem = player.getItemInHand(InteractionHand.MAIN_HAND);

        // Wax display
        if (player != null && player.isHolding(Items.HONEYCOMB) && state.getValue(ROTATE)) {
            world.setBlockAndUpdate(pos, state.setValue(ROTATE, false));
            world.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0f, 1.0f);
            player.level().levelEvent(null, LevelEvent.PARTICLES_AND_SOUND_WAX_ON, pos, 0);
            return InteractionResult.SUCCESS;
        // Hide holder
        } else if (player != null && player.isHolding(ItemRegistry.ORB)) {
            boolean is_visible = state.getValue(VISIBLE);
            world.setBlockAndUpdate(pos, state.setValue(VISIBLE, !is_visible));
            world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        // Manage stored item
        } else if (world.getBlockEntity(pos) instanceof SmallPedestalBlockEntity SmallPedestalBlockEntity) {
            ItemStack storedItem = SmallPedestalBlockEntity.getStoredItem();
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), storedItem);

            if (storedItem.isEmpty() ) {
                SmallPedestalBlockEntity.setStoredItem(playerHeldItem.split(1));
                world.playSound(player, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
                world.addFreshEntity(itemEntity);
                world.playSound(player, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                SmallPedestalBlockEntity.removeStoredItem();
            }

            SmallPedestalBlockEntity.setChanged();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.getBlockEntity(pos) instanceof SmallPedestalBlockEntity SmallPedestalBlockEntity) {
            ItemStack storedItem = SmallPedestalBlockEntity.getStoredItem();
            if (!storedItem.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), storedItem);
                world.addFreshEntity(itemEntity);
                SmallPedestalBlockEntity.removeStoredItem();
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }
}
