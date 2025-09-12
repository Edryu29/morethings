package com.edryu.morethings;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MoreThingsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(MoreThingsModelProvider::new);
		pack.addProvider(MoreThingsRecipeProvider::new);
		pack.addProvider(MoreThingsLootTableProvider::new);
	}
}
