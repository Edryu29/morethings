package com.edryu.morethings.client;

import com.edryu.morethings.MoreThingsRegister;
import com.edryu.morethings.client.entity.ItemDisplayBlockEntityRenderer;
import com.edryu.morethings.client.screen.SimpleScreen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;


public class MoreThingsClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.JAR_BOAT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.RED_SAFE_BUTTON, RenderLayer.getCutout());

		BlockEntityRendererFactories.register(MoreThingsRegister.ITEM_DISPLAY_BLOCK_ENTITY, ItemDisplayBlockEntityRenderer::new);

		HandledScreens.register(MoreThingsRegister.SACK_SCREEN_HANDLER, SimpleScreen::new);
		HandledScreens.register(MoreThingsRegister.SAFE_SCREEN_HANDLER, SimpleScreen::new);
	}
}