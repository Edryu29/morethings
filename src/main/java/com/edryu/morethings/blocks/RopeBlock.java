package com.edryu.morethings.blocks;

import com.edryu.morethings.MoreThingsSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChainBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
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

	public static final IntProperty KNOT_STATE = IntProperty.of("knot_state", 0, 7);
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
            .with(KNOT_STATE, 0).with(KNOT, false)
            .with(UP, true).with(DOWN, true)
            .with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false));
    }


    
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld || (player != null && !player.isHolding(Items.STICK))) {
            return ActionResult.PASS;
        } else {
            int knot_value = (state.get(KNOT_STATE) + 1) % 8;
            switch (knot_value) {
                // Original
                case 1:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value)
                        .with(KNOT, true).with(UP, true).with(DOWN, true)
                        .with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false));
                    break;
                case 2:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value)
                        .with(KNOT, true).with(UP, false).with(DOWN, true)
                        .with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false));
                    break;
                case 3:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value)
                        .with(KNOT, true).with(UP, true).with(DOWN, false)
                        .with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false));
                    break;
                // Connections
                case 4:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value)
                        .with(KNOT, true).with(UP, true).with(DOWN, true)
                        .with(NORTH, true).with(SOUTH, false).with(WEST, false).with(EAST, false));
                    break;
                case 5:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value)
                        .with(KNOT, true).with(UP, true).with(DOWN, true)
                        .with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, true));
                    break;
                case 6:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value)
                        .with(KNOT, true).with(UP, true).with(DOWN, true)
                        .with(NORTH, false).with(SOUTH, true).with(WEST, false).with(EAST, false));
                    break;
                case 7:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value)
                        .with(KNOT, true).with(UP, true).with(DOWN, true)
                        .with(NORTH, false).with(SOUTH, false).with(WEST, true).with(EAST, false));
                    break;

                default:
                    world.setBlockState(pos, state.with(KNOT_STATE, knot_value)
                        .with(KNOT, false).with(UP, true).with(DOWN, true)
                        .with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false));
                    break;
            }
            world.playSound(player, pos, MoreThingsSounds.ROPE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATERLOGGED}).add(new Property[]{AXIS});
        builder.add(KNOT_STATE).add(KNOT).add(UP).add(DOWN);
        builder.add(NORTH).add(SOUTH).add(WEST).add(EAST);
    }

}