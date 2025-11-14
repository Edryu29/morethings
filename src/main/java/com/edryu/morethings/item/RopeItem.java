package com.edryu.morethings.item;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.entity.RopeKnotBlockEntity;
import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.SoundRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class RopeItem extends BlockItem {

    public RopeItem(Block block, Item.Settings settings) {
        super(block, settings);
    }

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);

        if (blockState.getBlock() instanceof FenceBlock) {
            if (!world.isClient) {
                BlockState knot = BlockRegistry.ROPE_KNOT.getDefaultState();
				world.setBlockState(blockPos, knot);
				world.playSound(null, blockPos, SoundRegistry.ROPE_PLACE, SoundCategory.BLOCKS, 0.5F, 0.8F);
				if (world.getBlockEntity(blockPos) instanceof RopeKnotBlockEntity be) be.setHeldBlock(blockState);
				context.getStack().decrementUnlessCreative(1, context.getPlayer());
            }
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
	}

	@Nullable
	@Override
	public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos().offset(context.getSide().getOpposite());
		BlockState blockState = world.getBlockState(blockPos);

		if (!blockState.isOf(this.getBlock()) || !Screen.hasShiftDown() || context.shouldCancelInteraction()) return context;
        
        Direction direction = Direction.DOWN;
        BlockPos.Mutable mutable = blockPos.mutableCopy().move(direction);
        for (int i = 0; i < 256; i++) {
            blockState = world.getBlockState(mutable);
            if (!blockState.isOf(this.getBlock())) {
                FluidState fluid = blockState.getFluidState();
                if (!fluid.isOf(Fluids.WATER) && !fluid.isEmpty()) return null;
                if (blockState.canReplace(context)) return ItemPlacementContext.offset(context, mutable, direction);
                break;
            }
            mutable.move(direction);
        }
        return null;
	}
}
