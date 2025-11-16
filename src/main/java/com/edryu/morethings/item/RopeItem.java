package com.edryu.morethings.item;

import com.edryu.morethings.entity.RopeKnotBlockEntity;
import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.SoundRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
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
}
