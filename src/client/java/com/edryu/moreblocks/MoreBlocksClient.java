package com.edryu.moreblocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class MoreBlocksClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MoreBlocksRegister.CAGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreBlocksRegister.JAR_BOAT, RenderLayer.getCutout());
	}
}