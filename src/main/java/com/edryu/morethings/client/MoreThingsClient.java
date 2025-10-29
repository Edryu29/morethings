package com.edryu.morethings.client;

import com.edryu.morethings.MoreThingsRegister;
import com.edryu.morethings.client.entity.ItemDisplayBlockEntityRenderer;
import com.edryu.morethings.client.screen.SimpleScreen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
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

		ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getFoliageColor(level, pos) : 0x77AB2F, MoreThingsRegister.BUSHY_LEAVES);

		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x73A456, MoreThingsRegister.BUSHY_LEAVES_GREEN);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x73A456, MoreThingsRegister.BUSHY_LEAVES_GREEN.asItem());

		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xEC5448, MoreThingsRegister.BUSHY_LEAVES_RED);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0xEC5448, MoreThingsRegister.BUSHY_LEAVES_RED.asItem());

		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xF3B437, MoreThingsRegister.BUSHY_LEAVES_YELLOW);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0xF3B437, MoreThingsRegister.BUSHY_LEAVES_YELLOW.asItem());
	}
}