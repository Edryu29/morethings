package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TerrariumBlock extends WaterloggedHorizontalBlock {

    public TerrariumBlock(Properties settings) {
        super(settings);
    }

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.or(
            Shapes.box(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f),
            Shapes.box(0.0625f, 0.125f, 0.0625f, 0.9375f, 0.875f, 0.9375f)
        );
	}
}