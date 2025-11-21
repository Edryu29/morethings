package com.edryu.morethings.client;

import com.edryu.morethings.client.renderer.DisplayBlockEntityRenderer;
import com.edryu.morethings.client.renderer.SmallPedestalBlockEntityRenderer;
import com.edryu.morethings.client.renderer.RopeKnotBlockEntityRenderer;
import com.edryu.morethings.client.screen.PulleyScreen;
import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.EntityRegistry;
import com.edryu.morethings.registry.ScreenRegistry;
import com.edryu.morethings.util.CogColor;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class MoreThingsClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		BlockEntityRenderers.register(EntityRegistry.SMALL_PEDESTAL_ENTITY, SmallPedestalBlockEntityRenderer::new);
		BlockEntityRenderers.register(EntityRegistry.DISPLAY_ENTITY, DisplayBlockEntityRenderer::new);
		BlockEntityRenderers.register(EntityRegistry.ROPE_KNOT_ENTITY,RopeKnotBlockEntityRenderer::new);

        MenuScreens.register(ScreenRegistry.PULLEY_SCREEN_HANDLER, PulleyScreen::new);
		
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BOAT_IN_A_JAR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.TERRARIUM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.TELESCOPE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.GLOBE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.GLOBE_SEPIA, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.RED_BUTTON, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BOOK_STACK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BOOK_PILE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.COG, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CRANK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CONSOLE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BAR_PANEL, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.LATTICE, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.THATCH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.THATCH_SLAB, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.THATCH_STAIRS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.HAYSTACK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.DISPLAY, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BUNTING_CEILING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BUNTING_WALL, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.WALL_LANTERN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SOUL_WALL_LANTERN, RenderType.cutout());

		ColorProviderRegistry.BLOCK.register(new CogColor(), BlockRegistry.COG);
		ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageWaterColor(level, pos) : 0x3F76E4, BlockRegistry.BOAT_IN_A_JAR);
	}
}