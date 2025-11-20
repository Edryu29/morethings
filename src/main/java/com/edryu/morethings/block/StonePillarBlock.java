package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StonePillarBlock extends WaterloggedBlock {

    public StonePillarBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.box(0.125f, 0f, 0.125f, 0.875f, 1f, 0.875f);
    }
}