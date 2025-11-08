package com.edryu.morethings.client;

import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.EntityRegistry;
import com.edryu.morethings.registry.ScreenRegistry;
import com.edryu.morethings.client.renderer.DisplayBlockEntityRenderer;
import com.edryu.morethings.client.renderer.SmallPedestalBlockEntityRenderer;
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
		BlockEntityRendererFactories.register(EntityRegistry.SMALL_PEDESTAL_ENTITY, SmallPedestalBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(EntityRegistry.DISPLAY_ENTITY, DisplayBlockEntityRenderer::new);

		HandledScreens.register(ScreenRegistry.SACK_SCREEN_HANDLER, SimpleScreen::new);
		HandledScreens.register(ScreenRegistry.SAFE_SCREEN_HANDLER, SimpleScreen::new);
		
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BOAT_IN_A_JAR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.TERRARIUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.RED_BUTTON, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.RED_SAFE_BUTTON, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CONSOLE_LEVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BOOK_PILE_HORIZONTAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BOOK_PILE_VERTICAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BAR_PANEL, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.LATTICE, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.THATCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.THATCH_SLAB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.THATCH_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.DISPLAY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BUNTING_CEILING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BUNTING_WALL, RenderLayer.getCutout());

		ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getWaterColor(level, pos) : 0x3F76E4, BlockRegistry.BOAT_IN_A_JAR);
	}
}