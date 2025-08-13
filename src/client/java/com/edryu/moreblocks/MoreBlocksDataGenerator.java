package com.edryu.moreblocks;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MoreBlocksDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(MoreBlocksModelProvider::new);
		pack.addProvider(MoreBlocksRecipeProvider::new);
		pack.addProvider(MoreBlocksLootTableProvider::new);
	}
}
