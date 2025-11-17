package com.edryu.morethings.item;

import com.edryu.morethings.entity.RopeKnotBlockEntity;
import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RopeItem extends BlockItem {

    public RopeItem(Block block, Item.Properties settings) {
        super(block, settings);
    }

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		BlockState blockState = world.getBlockState(blockPos);

        if (blockState.getBlock() instanceof FenceBlock) {
            if (!world.isClientSide) {
                BlockState knot = BlockRegistry.ROPE_KNOT.this.defaultBlockState();
				world.setBlockAndUpdate(blockPos, knot);
				world.playSound(null, blockPos, SoundRegistry.ROPE_PLACE, SoundSource.BLOCKS, 0.5F, 0.8F);
				if (world.getBlockEntity(blockPos) instanceof RopeKnotBlockEntity be) be.setHeldBlock(blockState);
				context.getItemInHand().consume(1, context.getPlayer());
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
	}
}
