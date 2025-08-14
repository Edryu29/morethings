package com.edryu.moreblocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.registry.RegistryWrapper;

public class MoreBlocksLootTableProvider extends FabricBlockLootTableProvider {
	protected MoreBlocksLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
        addDrop(MoreBlocksRegister.DAUB);
        addDrop(MoreBlocksRegister.DAUB_BRACE);
        addDrop(MoreBlocksRegister.DAUB_BRACE_FLIPPED);
        addDrop(MoreBlocksRegister.DAUB_CROSS_BRACE);
        addDrop(MoreBlocksRegister.DAUB_FRAME);
        addDrop(MoreBlocksRegister.CAGE);
        addDrop(MoreBlocksRegister.SACK);
        addDrop(MoreBlocksRegister.SAFE);
        addDrop(MoreBlocksRegister.ROPE);
        addDrop(MoreBlocksRegister.JAR_BOAT);
	}
}