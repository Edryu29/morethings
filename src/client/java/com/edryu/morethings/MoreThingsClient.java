package com.edryu.morethings;

import com.edryu.morethings.entity.ItemDisplayBlockEntityRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class MoreThingsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.CAGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.JAR_BOAT, RenderLayer.getCutout());

		BlockEntityRendererFactories.register(MoreThingsRegister.ITEM_DISPLAY_BLOCK_ENTITY, ItemDisplayBlockEntityRenderer::new);
	}
}