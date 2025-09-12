package com.edryu.morethings;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.registry.RegistryWrapper;

public class MoreThingsLootTableProvider extends FabricBlockLootTableProvider {
	protected MoreThingsLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
        addDrop(MoreThingsRegister.DAUB);
        addDrop(MoreThingsRegister.DAUB_BRACE);
        addDrop(MoreThingsRegister.DAUB_BRACE_FLIPPED);
        addDrop(MoreThingsRegister.DAUB_CROSS_BRACE);
        addDrop(MoreThingsRegister.DAUB_FRAME);
        addDrop(MoreThingsRegister.CAGE);
        addDrop(MoreThingsRegister.SACK);
        addDrop(MoreThingsRegister.SAFE);
        addDrop(MoreThingsRegister.ROPE);
        addDrop(MoreThingsRegister.JAR_BOAT);
        addDrop(MoreThingsRegister.BOOK_PILE);
	}
}