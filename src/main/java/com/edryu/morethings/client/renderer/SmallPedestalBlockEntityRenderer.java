package com.edryu.morethings.client.renderer;

import com.edryu.morethings.block.SmallPedestalBlock;
import com.edryu.morethings.entity.SmallPedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class SmallPedestalBlockEntityRenderer implements BlockEntityRenderer<SmallPedestalBlockEntity>{
    
    public SmallPedestalBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(SmallPedestalBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        boolean rotate = blockEntity.getBlockState().getValue(SmallPedestalBlock.ROTATE);
        Direction facing = blockEntity.getBlockState().getValue(SmallPedestalBlock.FACING);
        ItemStack storedItem = blockEntity.getStoredItem();

        if (facing == Direction.NORTH || facing == Direction.SOUTH) facing = facing.getOpposite();

        if (!storedItem.isEmpty()) {
            matrices.pushPose();

            if (storedItem.is(ItemTags.SWORDS)) {

                // facing == Direction.NORTH
                Quaternionf angle = Axis.ZP.rotationDegrees(230);
                double translateX = 0.65;
                double translateZ = 0.5;

                if (facing == Direction.EAST) {
                    angle = Axis.ZP.rotationDegrees(230);
                    translateX = 0.5;
                    translateZ = 0.35;

                } else if (facing == Direction.SOUTH) {
                    angle = Axis.ZN.rotationDegrees(130);
                    translateX = 0.35;
                    translateZ = 0.5;

                } else if (facing == Direction.WEST) {
                    angle = Axis.ZN.rotationDegrees(130);
                    translateX = 0.5;
                    translateZ = 0.65;
                }

                matrices.translate(translateX, 0.5, translateZ);
                matrices.scale(1.5f, 1.5f, 1.5f);
                matrices.mulPose(Axis.YP.rotationDegrees(facing.toYRot()));
                matrices.mulPose(angle);

            } else {
                double yOffset = 0.3 + 0.05 * Math.sin((blockEntity.getLevel().getGameTime() + tickDelta) / 8.0) / 4.0;
                float rotation = (System.currentTimeMillis() / 20) % 360;
                if (!rotate) rotation = facing.toYRot();
                matrices.translate(0.5, yOffset, 0.5);
                matrices.mulPose(Axis.YP.rotationDegrees(rotation));
                matrices.scale(1.2f, 1.2f, 1.2f);
            }

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderStatic(storedItem, ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, blockEntity.getLevel(), 0);
            matrices.popPose();
        }
    }
}