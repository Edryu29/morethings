package com.edryu.morethings.client;

import com.edryu.morethings.MoreThingsRegister;
import com.edryu.morethings.client.entity.DisplayBlockEntityRenderer;
import com.edryu.morethings.client.entity.SmallPedestalBlockEntityRenderer;
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
		BlockEntityRendererFactories.register(MoreThingsRegister.SMALL_PEDESTAL_BLOCK_ENTITY, SmallPedestalBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(MoreThingsRegister.DISPLAY_BLOCK_ENTITY, DisplayBlockEntityRenderer::new);

		HandledScreens.register(MoreThingsRegister.SACK_SCREEN_HANDLER, SimpleScreen::new);
		HandledScreens.register(MoreThingsRegister.SAFE_SCREEN_HANDLER, SimpleScreen::new);
		
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.BOAT_IN_A_JAR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.TERRARIUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.RED_BUTTON, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.RED_SAFE_BUTTON, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.CONSOLE_LEVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.BOOK_PILE_HORIZONTAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.BOOK_PILE_VERTICAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.BAR_PANEL, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.LATTICE, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.THATCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.THATCH_SLAB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.THATCH_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MoreThingsRegister.DISPLAY_BLOCK, RenderLayer.getCutout());

		ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getWaterColor(level, pos) : 0x3F76E4, MoreThingsRegister.BOAT_IN_A_JAR);
	}
}