package com.edryu.morethings.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PalisadeBlock extends FenceBlock {
	protected final VoxelShape[] collisionShapeByIndex;
	protected final VoxelShape[] shapeByIndex;

    public PalisadeBlock(Properties settings) {
        super(settings);
		this.collisionShapeByIndex = this.makeShapes(3.0F, 3.0F, 24.0F, 0.0F, 24.0F);
		this.shapeByIndex = this.makeShapes(3.0F, 3.0F, 16.0F, 0.0F, 16.0F);
    }

	@Override
	public boolean connectsTo(BlockState state, boolean isSideSolid, Direction direction) {
		Block block = state.getBlock();
		boolean bl = block instanceof PalisadeBlock || block instanceof IronBarsBlock || state.is(BlockTags.WALLS);
		boolean bl2 = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
		return !isExceptionForConnection(state) && isSideSolid || bl || bl2;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.shapeByIndex[this.getAABBIndex(state)];
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.collisionShapeByIndex[this.getAABBIndex(state)];
	}
}