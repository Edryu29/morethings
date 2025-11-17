package com.edryu.morethings;

import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.EntityRegistry;
import com.edryu.morethings.registry.ItemRegistry;
import com.edryu.morethings.registry.ScreenRegistry;
import com.edryu.morethings.registry.SoundRegistry;
import com.edryu.morethings.registry.TabRegistry;

import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.enchantment.Enchantments;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.util.TriState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreThingsMain implements ModInitializer {
	public static final String MOD_ID = "morethings";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

        // Infinity for crossbows
		EnchantmentEvents.ALLOW_ENCHANTING.register((enchantment, target, enchantingContext) -> {
			if (target.getItem() instanceof CrossbowItem && enchantment.unwrapKey().get().location().equals(Enchantments.INFINITY.location())) return TriState.TRUE;
			return TriState.DEFAULT;
		});

		SoundRegistry.initialize();
		ScreenRegistry.initialize();
		BlockRegistry.initialize();
		EntityRegistry.initialize();
		ItemRegistry.initialize();
		TabRegistry.initialize();
		LOGGER.info("More Things initialized!");
	}
}