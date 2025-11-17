package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.registry.ItemRegistry;
import com.edryu.morethings.registry.SoundRegistry;
import com.edryu.morethings.util.BlockProperties.Color;
import com.edryu.morethings.util.BlockProperties;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class BuntingBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<BuntingBlock> CODEC = Block.simpleCodec(BuntingBlock::new);

    public static final EnumProperty<Color> COLOR = BlockProperties.COLOR;       

    public BuntingBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(COLOR, Color.WHITE));
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

    protected Color getRandomColor() {
        Color[] vals = Color.values();
        return vals[(int)(Math.random() * vals.length)];
    }

    public static Color getColorFromDye(Item dye) {
        if (dye.equals(Items.BLACK_DYE)) return Color.BLACK;
        if (dye.equals(Items.BLUE_DYE)) return Color.BLUE;
        if (dye.equals(Items.BROWN_DYE)) return Color.BROWN;
        if (dye.equals(Items.CYAN_DYE)) return Color.CYAN;
        if (dye.equals(Items.GRAY_DYE)) return Color.GRAY;
        if (dye.equals(Items.GREEN_DYE)) return Color.GREEN;
        if (dye.equals(Items.LIGHT_BLUE_DYE)) return Color.LIGHT_BLUE;
        if (dye.equals(Items.LIGHT_GRAY_DYE)) return Color.LIGHT_GRAY;
        if (dye.equals(Items.LIME_DYE)) return Color.LIME;
        if (dye.equals(Items.MAGENTA_DYE)) return Color.MAGENTA;
        if (dye.equals(Items.ORANGE_DYE)) return Color.ORANGE;
        if (dye.equals(Items.PINK_DYE)) return Color.PINK;
        if (dye.equals(Items.PURPLE_DYE)) return Color.PURPLE;
        if (dye.equals(Items.RED_DYE)) return Color.RED;
        if (dye.equals(Items.WHITE_DYE)) return Color.WHITE;
        if (dye.equals(Items.YELLOW_DYE)) return Color.YELLOW;
        return null;
    }
}