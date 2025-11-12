package com.edryu.morethings.block;

import com.edryu.morethings.util.BlockProperties;
import com.edryu.morethings.util.BlockProperties.Winding;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PulleyBlock extends PillarBlock {
    public static final BooleanProperty FLIPPED = BooleanProperty.of("flipped");
    public static final EnumProperty<Winding> WINDING = BlockProperties.WINDING;

    public PulleyBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(AXIS, Direction.Axis.Y).with(FLIPPED, false).with(WINDING, Winding.NONE));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!Screen.hasShiftDown()) return ActionResult.PASS;
        world.setBlockState(pos, state.with(FLIPPED, !state.get(FLIPPED)));
        world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
        return ActionResult.SUCCESS;
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS, FLIPPED, WINDING);
    }
}