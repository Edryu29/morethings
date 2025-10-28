package com.edryu.morethings;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.util.TriState;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.CrossbowItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreThingsMain implements ModInitializer {
	public static final String MOD_ID = "morethings";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

        // Infinity for crossbows
		EnchantmentEvents.ALLOW_ENCHANTING.register((enchantment, target, enchantingContext) -> {
			if (target.getItem() instanceof CrossbowItem && enchantment.getKey().get().getValue().equals(Enchantments.INFINITY.getValue())) {
				return TriState.TRUE;
			}
			return TriState.DEFAULT;
		});

		MoreThingsRegister.initialize();
		MoreThingsSounds.initialize();
	}
}