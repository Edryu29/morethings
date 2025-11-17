package com.edryu.morethings.client.renderer;

import com.edryu.morethings.block.DisplayBlock;
import com.edryu.morethings.entity.DisplayBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DisplayBlockEntityRenderer implements BlockEntityRenderer<DisplayBlockEntity>{
    
    public DisplayBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(DisplayBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Direction facing = blockEntity.getBlockState().getValue(DisplayBlock.FACING);
        ItemStack storedItem = blockEntity.getStoredItem();

        if (facing == Direction.NORTH || facing == Direction.SOUTH) facing = facing.getOpposite();

        if (!storedItem.isEmpty()) {
            float rotation = facing.toYRot();
            double yOffset = 0.3 + 0.05 * Math.sin((blockEntity.getLevel().getGameTime() + partialTick) / 8.0) / 2.0;

            poseStack.pushPose();
            poseStack.translate(0.5, 0.6 + yOffset * 0.05, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
            poseStack.scale(0.75f, 0.75f, 0.75f);

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderStatic(storedItem, ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }
    }
}