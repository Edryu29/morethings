package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.registry.ItemRegistry;
import com.edryu.morethings.registry.SoundRegistry;
import com.edryu.morethings.util.BlockProperties;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class BuntingBlock extends HorizontalDirectionalBlock {
    public static final EnumProperty<DyeColor> COLOR = BlockProperties.COLOR;      
    
    public static final MapCodec<BuntingBlock> CODEC = Block.simpleCodec(BuntingBlock::new); 

    public BuntingBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(COLOR, DyeColor.WHITE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, COLOR);
    }

	@Override
	protected MapCodec<? extends BuntingBlock> codec() {
		return CODEC;
	}

    @Override
   protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        Item itemInHand = stack.getItem();
        if (getColorFromDye(itemInHand) != null) {
            level.setBlockAndUpdate(pos, state.setValue(COLOR, getColorFromDye(itemInHand)));
            level.playSound(player, pos, SoundRegistry.ROPE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            return ItemInteractionResult.SUCCESS;
        } else if (itemInHand.equals(ItemRegistry.ORB)) {
            level.setBlockAndUpdate(pos, state.setValue(COLOR, getRandomColor()));
            level.playSound(player, pos, SoundRegistry.ROPE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        level.setBlockAndUpdate(pos, state.setValue(COLOR, getRandomColor()));
	}

    protected DyeColor getRandomColor() {
        DyeColor[] vals = DyeColor.values();
        return vals[(int)(Math.random() * vals.length)];
    }

    public static DyeColor getColorFromDye(Item item) {
        return item instanceof DyeItem dye ? dye.getDyeColor() : null;
    }
}