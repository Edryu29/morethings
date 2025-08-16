package com.edryu.moreblocks;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreBlocksMain implements ModInitializer {
	public static final String MOD_ID = "moreblocks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MoreBlocksRegister.initialize();
		MoreBlocksSounds.initialize(); 
	}
}