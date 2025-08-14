package com.edryu.moreblocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CageBlock extends Block {

    public CageBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(
            VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.9375f, 0.0625f, 0.9375f),
            VoxelShapes.cuboid(0.0625f, 0.0625f, 0.0625f, 0.9375f, 1f, 0.0625f),
            VoxelShapes.cuboid(0.0625f, 0.0625f, 0.9375f, 0.9375f, 1f, 0.9375f),
            VoxelShapes.cuboid(0.0625f, 0.9375f, 0.0625f, 0.9375f, 1f, 0.9375f),
            VoxelShapes.cuboid(0.9375f, 0.0625f, 0.0625f, 0.9375f, 1f, 0.9375f),
            VoxelShapes.cuboid(0.0625f, 0.0625f, 0.0625f, 0.0625f, 1f, 0.9375f)
        );
    }
}