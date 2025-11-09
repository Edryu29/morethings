package com.edryu.morethings.item;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.block.RopeBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class RopeItem extends BlockItem {

	public RopeItem(Block block, Item.Settings settings) {
		super(block, settings);
	}

	// @Nullable
	// @Override
	// public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
	// 	BlockPos blockPos = context.getBlockPos();
	// 	World world = context.getWorld();
	// 	BlockState blockState = world.getBlockState(blockPos);
	// 	Block block = this.getBlock();

	// 	if (!blockState.isOf(block)) return context;
        
    //     Direction direction;
    //     if (context.shouldCancelInteraction()) {
    //         direction = context.getSide();
    //     } else {
    //         direction = Direction.DOWN;
    //     }

    //     BlockPos.Mutable mutable = blockPos.mutableCopy().move(direction);

    //     int i = 0;
    //     while (i < 5) {
    //         blockState = world.getBlockState(mutable);
    //         if (!blockState.isOf(block)){
    //             FluidState fluid = blockState.getFluidState();
    //             if (!fluid.isOf(Fluids.WATER) && !fluid.isEmpty()) return null;
    //             if (blockState.canReplace(context)) return ItemPlacementContext.offset(context, mutable, direction);
    //         }
    //         mutable.move(direction);
    //         i++;
    //     }
    //     return null;
	// }
}
