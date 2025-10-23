package com.edryu.morethings.entity;

import com.edryu.morethings.block.ItemDisplayBlock;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.RotationAxis;

public class ItemDisplayBlockEntityRenderer implements BlockEntityRenderer<ItemDisplayBlockEntity>{

    public ItemDisplayBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(ItemDisplayBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        int rotate = (Boolean)blockEntity.getCachedState().get(ItemDisplayBlock.ROTATE) ? 1 : 0;
        ItemStack storedItem = blockEntity.getStack(0);

        if (!storedItem.isEmpty()) {
            matrices.push();

            if (storedItem.isIn(ItemTags.SWORDS)) {
                matrices.translate(0.3, 0.5, 0.5);
                matrices.scale(1.5f, 1.5f, 1.5f);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(235));

            } else {
                double yOffset = 0.3 + 0.05 * Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0);
                float rotation = (System.currentTimeMillis() / 20) % 360;
                matrices.translate(0.5, yOffset, 0.5);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation * rotate));
                matrices.scale(1.2f, 1.2f, 1.2f);
            }

            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            itemRenderer.renderItem(storedItem, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            matrices.pop();
        } else {
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            itemRenderer.renderItem(ItemStack.EMPTY, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
        }
    }
}