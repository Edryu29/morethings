package com.edryu.morethings.client.renderer;

import com.edryu.morethings.block.SmallPedestalBlock;
import com.edryu.morethings.entity.SmallPedestalBlockEntity;

import org.joml.Quaternionf;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class SmallPedestalBlockEntityRenderer implements BlockEntityRenderer<SmallPedestalBlockEntity>{
    
    public SmallPedestalBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(SmallPedestalBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        boolean rotate = blockEntity.getCachedState().get(SmallPedestalBlock.ROTATE);
        Direction facing = blockEntity.getCachedState().get(SmallPedestalBlock.FACING);
        ItemStack storedItem = blockEntity.getStack(0);

        if (facing == Direction.NORTH || facing == Direction.SOUTH) facing = facing.getOpposite();

        if (!storedItem.isEmpty()) {
            matrices.push();

            if (storedItem.isIn(ItemTags.SWORDS)) {

                // facing == Direction.NORTH
                Quaternionf angle = RotationAxis.POSITIVE_Z.rotationDegrees(230);
                double translateX = 0.65;
                double translateZ = 0.5;

                if (facing == Direction.EAST) {
                    angle = RotationAxis.POSITIVE_Z.rotationDegrees(230);
                    translateX = 0.5;
                    translateZ = 0.35;

                } else if (facing == Direction.SOUTH) {
                    angle = RotationAxis.NEGATIVE_Z.rotationDegrees(130);
                    translateX = 0.35;
                    translateZ = 0.5;

                } else if (facing == Direction.WEST) {
                    angle = RotationAxis.NEGATIVE_Z.rotationDegrees(130);
                    translateX = 0.5;
                    translateZ = 0.65;
                }

                matrices.translate(translateX, 0.5, translateZ);
                matrices.scale(1.5f, 1.5f, 1.5f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(facing.asRotation()));
                matrices.multiply(angle);

            } else {
                double yOffset = 0.3 + 0.05 * Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
                float rotation = (System.currentTimeMillis() / 20) % 360;
                if (!rotate) rotation = facing.asRotation();
                matrices.translate(0.5, yOffset, 0.5);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
                matrices.scale(1.2f, 1.2f, 1.2f);
            }

            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            itemRenderer.renderItem(storedItem, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
            matrices.pop();
        }
    }
}