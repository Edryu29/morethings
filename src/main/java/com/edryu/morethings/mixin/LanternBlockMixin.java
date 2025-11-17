package com.edryu.morethings.mixin;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.edryu.morethings.registry.BlockRegistry;

@Mixin(LanternBlock.class)
public class LanternBlockMixin {
    @Inject(at = @At("HEAD"), method = "getStateForPlacement", cancellable = true)
    private void placeWallLantern(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (context.getClickedFace().getAxis().isHorizontal()) {
            BlockState state = null;
            Item itemInHand = context.getItemInHand().getItem();
            
            if (itemInHand.equals(Blocks.LANTERN.asItem())) state = BlockRegistry.WALL_LANTERN.getStateForPlacement(context);
            else if (itemInHand.equals(Blocks.SOUL_LANTERN.asItem())) state = BlockRegistry.SOUL_WALL_LANTERN.getStateForPlacement(context);

            if (state != null) cir.setReturnValue(state);
        }
    }
}