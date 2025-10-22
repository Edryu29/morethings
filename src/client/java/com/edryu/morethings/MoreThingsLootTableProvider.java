package com.edryu.morethings;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import java.util.concurrent.CompletableFuture;

import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
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
                addDrop(MoreThingsRegister.SACK_BLOCK);
                addDrop(MoreThingsRegister.SAFE_BLOCK);
                addDrop(MoreThingsRegister.ROPE);
                addDrop(MoreThingsRegister.JAR_BOAT);
                addDrop(MoreThingsRegister.PEDESTAL);
                addDrop(MoreThingsRegister.ITEM_DISPLAY_BLOCK);

                addDrop(MoreThingsRegister.BOOK_PILE_HORIZONTAL, LootTable.builder().pool(addSurvivesExplosionCondition(Items.BOOK, LootPool.builder()
                        .rolls(new ConstantLootNumberProvider(4)).with(ItemEntry.builder(Items.BOOK)))));
                addDrop(MoreThingsRegister.BOOK_PILE_VERTICAL, LootTable.builder().pool(addSurvivesExplosionCondition(Items.BOOK, LootPool.builder()
                        .rolls(new ConstantLootNumberProvider(4)).with(ItemEntry.builder(Items.BOOK)))));
	}
}