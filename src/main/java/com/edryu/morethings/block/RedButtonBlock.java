package com.edryu.morethings.block;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.util.math.Direction;

public class RedButtonBlock extends ButtonBlock {

    public RedButtonBlock(Settings settings) {
        super(BlockSetType.STONE, 30, settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(FACE, BlockFace.WALL));
    }
}