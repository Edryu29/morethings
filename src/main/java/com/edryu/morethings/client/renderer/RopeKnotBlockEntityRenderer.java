package com.edryu.morethings.client.renderer;

import com.edryu.morethings.entity.RopeKnotBlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.random.Random;

public class RopeKnotBlockEntityRenderer implements BlockEntityRenderer<RopeKnotBlockEntity>{

    public RopeKnotBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(RopeKnotBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (blockEntity.getWorld() == null) return;
        BlockState held = blockEntity.getHeldBlock();
        if (held == null || held.isAir()) return;

        matrices.push();
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(held, blockEntity.getPos(), blockEntity.getWorld(), matrices, vertexConsumers.getBuffer(RenderLayer.getCutout()), false, Random.create());
        matrices.pop();
    }
}