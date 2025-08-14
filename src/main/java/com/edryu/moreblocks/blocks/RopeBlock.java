package com.edryu.moreblocks.blocks;

import com.edryu.moreblocks.MoreBlocksRegister;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChainBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.World;

public class RopeBlock extends ChainBlock {
	public static final BooleanProperty KNOT = BooleanProperty.of("knot");
	public static final BooleanProperty UP = BooleanProperty.of("up");
	public static final BooleanProperty DOWN = BooleanProperty.of("down");
	public static final IntProperty KNOT_STATE = IntProperty.of("knot_state", 0, 3);

    public RopeBlock(Settings settings) {
        super(settings);
        setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(WATERLOGGED, false)).with(AXIS, Axis.Y).with(KNOT, false).with(UP, true).with(DOWN, true).with(KNOT_STATE, 0));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld || (player != null && !player.isHolding(MoreBlocksRegister.WRENCH))) {
            return ActionResult.PASS;
        } else {
            int knot_value = (state.get(KNOT_STATE) + 1) % 4;

            switch (knot_value) {
                case 1:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value).with(KNOT, true).with(UP, true).with(DOWN, true));
                    break;
                case 2:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value).with(KNOT, true).with(UP, false).with(DOWN, true));
                    break;
                case 3:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value).with(KNOT, true).with(UP, true).with(DOWN, false));
                    break;
                default:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value).with(KNOT, false).with(UP, true).with(DOWN, true));
                    break;
            }

            world.playSound(player, pos, SoundEvents.BLOCK_ROOTS_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATERLOGGED}).add(new Property[]{AXIS});
        builder.add(KNOT).add(UP).add(DOWN).add(KNOT_STATE);
    }

}