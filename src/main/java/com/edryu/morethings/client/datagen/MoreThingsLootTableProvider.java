package com.edryu.morethings.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.edryu.morethings.MoreThingsRegister;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

import net.minecraft.registry.RegistryWrapper;

public class MoreThingsLootTableProvider extends FabricBlockLootTableProvider {
	protected MoreThingsLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
                addDrop(MoreThingsRegister.DAUB);
                addDrop(MoreThingsRegister.SACK_BLOCK);
                addDrop(MoreThingsRegister.SAFE_BLOCK);
                addDrop(MoreThingsRegister.ROPE);
                addDrop(MoreThingsRegister.JAR_BOAT);
                addDrop(MoreThingsRegister.PEDESTAL);
                addDrop(MoreThingsRegister.ITEM_DISPLAY_BLOCK);
                addDrop(MoreThingsRegister.BOOK_PILE_HORIZONTAL);
                addDrop(MoreThingsRegister.BOOK_PILE_VERTICAL);
                addDrop(MoreThingsRegister.RED_BUTTON);
                addDrop(MoreThingsRegister.RED_SAFE_BUTTON);
                addDrop(MoreThingsRegister.CONSOLE_LEVER);
                addDrop(MoreThingsRegister.STONE_PILLAR);
                addDrop(MoreThingsRegister.BIG_CHAIN);
                addDrop(MoreThingsRegister.BAR_PANEL);
                addDrop(MoreThingsRegister.LATTICE);
                addDrop(MoreThingsRegister.THATCH);
                addDrop(MoreThingsRegister.THATCH_SLAB);
                addDrop(MoreThingsRegister.THATCH_STAIRS);
	}
}