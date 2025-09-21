package com.edryu.morethings.mixin;

import net.minecraft.entity.ai.brain.task.OpenDoorsTask;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

@Mixin(OpenDoorsTask.class)
public class OpenDoorsTaskMixin {

	@ModifyExpressionValue( method="create", at=@At(value="INVOKE", ordinal=1, target="net/minecraft/block/DoorBlock.isOpen (Lnet/minecraft/block/BlockState;)Z") )
	static private boolean DontIgnoreOpenDoors(boolean original){
		return false;
	}

	@ModifyExpressionValue( method="create", at=@At(value="INVOKE", target="java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z"))
	static private boolean	AlwaysBeAwareOfDoors(boolean original, @Local Path path, @Local ServerWorld world){
		return !original;
	}
}