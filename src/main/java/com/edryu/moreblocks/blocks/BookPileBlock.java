package com.edryu.moreblocks.blocks;

import com.edryu.moreblocks.MoreBlocksRegister;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.Hand;

public class BookPileBlock extends HorizontalFacingBlock {
    public static final MapCodec<BookPileBlock> CODEC = Block.createCodec(BookPileBlock::new);
	public static final BooleanProperty VERTICAL = BooleanProperty.of("vertical");
	public static final IntProperty BOOKS = IntProperty.of("books", 0, 3);

    public BookPileBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(VERTICAL, false).with(BOOKS, 0).with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

	@Override
	protected MapCodec<? extends BookPileBlock> getCodec() {
		return CODEC;
	}

    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld || player == null || !player.isHolding(MoreBlocksRegister.WRENCH)) {
            return ActionResult.PASS;
        } else {
            boolean vertical_pile = state.get(VERTICAL);
            int books_amount = state.get(BOOKS);

            if (player.getStackInHand(Hand.OFF_HAND).isOf(MoreBlocksRegister.WRENCH)) {
                world.setBlockState(pos, state.with(VERTICAL, !vertical_pile).with(BOOKS, books_amount));
            } else {
                world.setBlockState(pos, state.with(VERTICAL, vertical_pile).with(BOOKS, (books_amount + 1) % 4));
            }
            
            world.playSound(player, pos, SoundEvents.BLOCK_SCAFFOLDING_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL).add(BOOKS);
        builder.add(Properties.HORIZONTAL_FACING);
    }
}