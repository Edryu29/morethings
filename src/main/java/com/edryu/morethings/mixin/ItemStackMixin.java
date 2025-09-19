package com.edryu.morethings.mixin;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "getMaxCount", at = @At("RETURN"), cancellable = true)
    private void increaseStackSize(CallbackInfoReturnable<Integer> cir) {
        // If the item normally stacks to 16, make it stack to 64
        if (cir.getReturnValue() == 16) {
            cir.setReturnValue(64);
        }
    }
}