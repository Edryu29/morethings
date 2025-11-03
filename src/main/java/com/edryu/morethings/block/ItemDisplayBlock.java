package com.edryu.morethings.block;

import com.edryu.morethings.MoreThingsRegister;
import com.edryu.morethings.entity.ItemDisplayBlockEntity;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class ItemDisplayBlock extends Block implements BlockEntityProvider {
    public static final BooleanProperty ROTATE = BooleanProperty.of("rotate");
    public static final BooleanProperty VISIBLE = BooleanProperty.of("visible");

    public ItemDisplayBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ROTATE, true).with(VISIBLE, true));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemDisplayBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.1875f, 0f, 0.1875f, 0.8125f, 0.0625f, 0.8125f);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack playerHeldItem = player.getStackInHand(Hand.MAIN_HAND);

        // Wax display
        if (player != null && player.isHolding(Items.HONEYCOMB) && state.get(ROTATE)) {
            world.setBlockState(pos, state.with(ROTATE, false));
            world.playSound(player, pos, SoundEvents.ITEM_HONEYCOMB_WAX_ON, SoundCategory.BLOCKS, 1.0f, 1.0f);
            playerHeldItem.decrementUnlessCreative(1, player);
            player.getWorld().syncWorldEvent(null, WorldEvents.BLOCK_WAXED, pos, 0);
            return ActionResult.SUCCESS;

        // Hide holder
        } else if (player != null && player.isHolding(MoreThingsRegister.ORB)) {
            boolean is_visible = state.get(VISIBLE);
            world.setBlockState(pos, state.with(VISIBLE, !is_visible));
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;

        } else if (world.getBlockEntity(pos) instanceof ItemDisplayBlockEntity ItemDisplayBlockEntity) {
            ItemStack storedItem = ItemDisplayBlockEntity.getStack(0);
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), storedItem);

            if (storedItem.isEmpty() ) {
                ItemDisplayBlockEntity.setStack(0, playerHeldItem.split(1));
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
                ItemDisplayBlockEntity.markDirty();
            } else {
                world.spawnEntity(itemEntity);
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
                ItemDisplayBlockEntity.removeStack(0);
                ItemDisplayBlockEntity.markDirty();
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {

        if (world.getBlockEntity(pos) instanceof ItemDisplayBlockEntity ItemDisplayBlockEntity) {
            ItemStack storedItem = ItemDisplayBlockEntity.getStack(0);

            if (!storedItem.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), storedItem);
                world.spawnEntity(itemEntity);
                ItemDisplayBlockEntity.removeStack(0);
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATE, VISIBLE);
    }

}
