package com.edryu.moreblocks.blocks;


import com.edryu.moreblocks.MoreBlocksRegister;
import com.edryu.moreblocks.MoreBlocksSounds;

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
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.World;

public class RopeBlock extends ChainBlock {
	public static final IntProperty ROPE_STATE = IntProperty.of("rope_state", 0, 3);
	public static final IntProperty CONNECTION_STATE = IntProperty.of("connection_state", 0, 15);
	public static final BooleanProperty CONNECTION_MODE = BooleanProperty.of("connection_mode");

	public static final BooleanProperty KNOT = BooleanProperty.of("knot");
	public static final BooleanProperty UP = BooleanProperty.of("up");
	public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty EAST = BooleanProperty.of("east");

    public RopeBlock(Settings settings) {
        super(settings);
        setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(WATERLOGGED, false)).with(AXIS, Axis.Y)
            .with(CONNECTION_MODE, false).with(CONNECTION_STATE, 0)
            .with(KNOT, false).with(ROPE_STATE, 0)
            .with(UP, true).with(DOWN, true)
            .with(NORTH, false).with(SOUTH, false)
            .with(WEST, false).with(EAST, false));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld || (player != null && !player.isHolding(MoreBlocksRegister.WRENCH))) {
            return ActionResult.PASS;
        } else {
            boolean connection_mode_value = state.get(CONNECTION_MODE);
            if (player.getStackInHand(Hand.OFF_HAND).isOf(MoreBlocksRegister.WRENCH)) {
                world.setBlockState(pos,state.with(CONNECTION_MODE, !connection_mode_value).with(CONNECTION_STATE, 0)
                    .with(KNOT, false).with(ROPE_STATE, 0)
                    .with(UP, true).with(DOWN, true)
                    .with(NORTH, false).with(SOUTH, false)
                    .with(WEST, false).with(EAST, false));
            } else{
                if (connection_mode_value){
                    int connection_state_updated = (state.get(CONNECTION_STATE) + 1) % 16;
                    switch (connection_state_updated) {
                        case 1:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, true).with(EAST, false).with(SOUTH, false).with(WEST, false));
                            break;
                        case 2:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, false).with(EAST, true).with(SOUTH, false).with(WEST, false));
                            break;
                        case 3:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, false).with(EAST, false).with(SOUTH, true).with(WEST, false));
                            break;
                        case 4:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, true));
                            break;

                        case 5:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, true).with(EAST, true).with(SOUTH, false).with(WEST, false));
                            break;
                        case 6:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, true).with(EAST, false).with(SOUTH, true).with(WEST, false));
                            break;
                        case 7:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, true).with(EAST, false).with(SOUTH, false).with(WEST, true));
                            break;
                        case 8:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, false).with(EAST, true).with(SOUTH, true).with(WEST, false));
                            break;
                        case 9:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, false).with(EAST, true).with(SOUTH, false).with(WEST, true));
                            break;
                        case 10:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, false).with(EAST, false).with(SOUTH, true).with(WEST, true));
                            break;

                        case 11:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, true).with(EAST, true).with(SOUTH, true).with(WEST, false));
                            break;
                        case 12:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, true).with(EAST, true).with(SOUTH, false).with(WEST, true));
                            break;
                        case 13:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, true).with(EAST, false).with(SOUTH, true).with(WEST, true));
                            break;
                        case 14:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, false).with(EAST, true).with(SOUTH, true).with(WEST, true));
                            break;

                        case 15:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, true).with(EAST, true).with(SOUTH, true).with(WEST, true));
                            break;

                        default:
                            world.setBlockState(pos, state.with(CONNECTION_STATE, connection_state_updated).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
                            break;
                    }
                } else {
                    int rope_state_updated = (state.get(ROPE_STATE) + 1) % 4;
                    switch (rope_state_updated) {
                        case 1:
                            world.setBlockState(pos, state.with(ROPE_STATE, rope_state_updated).with(KNOT, true).with(UP, true).with(DOWN, true));
                            break;
                        case 2:
                            world.setBlockState(pos, state.with(ROPE_STATE, rope_state_updated).with(KNOT, true).with(UP, false).with(DOWN, true));
                            break;
                        case 3:
                            world.setBlockState(pos, state.with(ROPE_STATE, rope_state_updated).with(KNOT, true).with(UP, true).with(DOWN, false));
                            break;
                        default:
                            world.setBlockState(pos, state.with(ROPE_STATE, rope_state_updated).with(KNOT, false).with(UP, true).with(DOWN, true));
                            break;
                    }
                }
            }

            world.playSound(player, pos, MoreBlocksSounds.ROPE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATERLOGGED}).add(new Property[]{AXIS});
        builder.add(CONNECTION_MODE).add(CONNECTION_STATE);
        builder.add(KNOT).add(ROPE_STATE).add(UP).add(DOWN);
        builder.add(NORTH).add(SOUTH).add(WEST).add(EAST);
    }

}