package com.edryu.morethings.client.renderer;

import com.edryu.morethings.block.DisplayBlock;
import com.edryu.morethings.entity.DisplayBlockEntity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class DisplayBlockEntityRenderer implements BlockEntityRenderer<DisplayBlockEntity>{
    
    public DisplayBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(DisplayBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Direction facing = blockEntity.getCachedState().get(DisplayBlock.FACING);
        ItemStack storedItem = blockEntity.getStack(0);

        if (facing == Direction.NORTH || facing == Direction.SOUTH) facing = facing.getOpposite();

        if (!storedItem.isEmpty()) {
            float rotation = facing.asRotation();
            double yOffset = 0.3 + 0.05 * Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 2.0;

            matrices.push();
            matrices.translate(0.5, 0.6 + yOffset * 0.05, 0.5);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
            matrices.scale(0.75f, 0.75f, 0.75f);

            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            itemRenderer.renderItem(storedItem, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            matrices.pop();
        }
    }
}