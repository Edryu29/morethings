package com.edryu.morethings.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class RedSafeButtonBlock extends ButtonBlock {

    public static final BooleanProperty OPEN = BooleanProperty.of("open");

    public RedSafeButtonBlock(Settings settings) {
        super(BlockSetType.STONE, 30, settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(FACE, BlockFace.WALL).with(OPEN, false));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        boolean is_open = state.get(OPEN);

        if (player != null && player.isSneaking()) {
            world.setBlockState(pos, state.with(OPEN, !is_open));
            return ActionResult.SUCCESS;
            
        } else if (!is_open) {
           return ActionResult.SUCCESS; 

        } else {
            if ((Boolean)state.get(POWERED)) {
                return ActionResult.CONSUME;
            } else {
                this.powerOn(state, world, pos, player);
                return ActionResult.success(world.isClient);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, POWERED, FACE});
        builder.add(OPEN);
    }
}