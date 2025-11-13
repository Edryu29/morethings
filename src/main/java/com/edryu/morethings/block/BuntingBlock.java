package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.registry.ItemRegistry;
import com.edryu.morethings.registry.SoundRegistry;
import com.edryu.morethings.util.BlockProperties.Color;
import com.edryu.morethings.util.BlockProperties;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BuntingBlock extends HorizontalFacingBlock {
    public static final MapCodec<BuntingBlock> CODEC = Block.createCodec(BuntingBlock::new);

    public static final EnumProperty<Color> COLOR = BlockProperties.COLOR;       

    public BuntingBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(COLOR, Color.WHITE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING, COLOR);
    }

	@Override
	protected MapCodec<? extends BuntingBlock> getCodec() {
		return CODEC;
	}

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld || player == null) {
            return ActionResult.PASS;
        } else {
            Item itemInHand = player.getStackInHand(Hand.MAIN_HAND).getItem();
            if (getColorFromDye(itemInHand) != null) {
                world.setBlockState(pos, state.with(COLOR, getColorFromDye(itemInHand)));
                world.playSound(player, pos, SoundRegistry.ROPE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResult.SUCCESS;
            } else if (itemInHand.equals(ItemRegistry.ORB)) {
                world.setBlockState(pos, state.with(COLOR, getRandomColor()));
                world.playSound(player, pos, SoundRegistry.ROPE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos, state.with(COLOR, getRandomColor()));
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