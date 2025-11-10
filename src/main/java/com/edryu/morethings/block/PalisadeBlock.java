package com.edryu.morethings.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class PalisadeBlock extends FenceBlock {
	protected final VoxelShape[] collisionShapes;
	protected final VoxelShape[] boundingShapes;

    public PalisadeBlock(Settings settings) {
        super(settings);
		this.collisionShapes = this.createShapes(3.0F, 3.0F, 24.0F, 0.0F, 24.0F);
		this.boundingShapes = this.createShapes(3.0F, 3.0F, 16.0F, 0.0F, 16.0F);
    }

	@Override
	public boolean canConnect(BlockState state, boolean neighborIsFullSquare, Direction dir) {
		Block block = state.getBlock();
		boolean bl = block instanceof PalisadeBlock || block instanceof PaneBlock || state.isIn(BlockTags.WALLS);
		boolean bl2 = block instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, dir);
		return !cannotConnect(state) && neighborIsFullSquare || bl || bl2;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.boundingShapes[this.getShapeIndex(state)];
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.collisionShapes[this.getShapeIndex(state)];
	}

}