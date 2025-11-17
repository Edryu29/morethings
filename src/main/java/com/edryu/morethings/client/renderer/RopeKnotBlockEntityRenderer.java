package com.edryu.morethings.client.renderer;

import com.edryu.morethings.entity.RopeKnotBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class RopeKnotBlockEntityRenderer implements BlockEntityRenderer<RopeKnotBlockEntity>{

    public RopeKnotBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(RopeKnotBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getLevel() == null) return;
        BlockState heldBlock = blockEntity.getHeldBlock();
        if (heldBlock == null || heldBlock.isAir()) return;

        poseStack.pushPose();
        Minecraft.getInstance().getBlockRenderer().renderBatched(heldBlock, blockEntity.getBlockPos(), blockEntity.getLevel(), poseStack, bufferSource.getBuffer(RenderType.cutout()), false, RandomSource.create());
        poseStack.popPose();
    }
}