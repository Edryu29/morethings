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
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof FenceBlock) {
            if (!level.isClientSide()) {
                BlockState knot = BlockRegistry.ROPE_KNOT.defaultBlockState();
				level.setBlockAndUpdate(pos, knot);
				level.playSound(null, pos, SoundRegistry.ROPE_PLACE, SoundSource.BLOCKS, 0.5F, 0.8F);
				if (level.getBlockEntity(pos) instanceof RopeKnotBlockEntity be) be.setHeldBlock(state);
				context.getItemInHand().consume(1, context.getPlayer());
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
	}
}
