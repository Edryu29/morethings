package com.edryu.morethings.mixin;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.edryu.morethings.util.MoreThingsHelper;

@Mixin(LanternBlock.class)
public class LanternBlockMixin {
    @Inject(at = @At("HEAD"), method = "getStateForPlacement", cancellable = true)
    private void placeWallLanternHorizontal(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (context.getClickedFace().getAxis().isHorizontal()) {
            BlockState state = MoreThingsHelper.getWallLanternPlacement(context);        
            if (state != null) cir.setReturnValue(state);
        }
    }

    @Inject(at = @At("RETURN"), method = "getStateForPlacement", cancellable = true)
    private void placeWallLanternVertical(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (context.getClickedFace().getAxis().isVertical() && cir.getReturnValue() == null) {
            BlockState state = MoreThingsHelper.getWallLanternPlacement(context);        
            if (state != null) cir.setReturnValue(state);
        }
    }
}