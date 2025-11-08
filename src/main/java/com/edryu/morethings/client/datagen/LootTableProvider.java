package com.edryu.morethings.client.datagen;

import java.util.concurrent.CompletableFuture;
import com.edryu.morethings.registry.BlockRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;

public class LootTableProvider extends FabricBlockLootTableProvider {
	protected LootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
                addDrop(BlockRegistry.DAUB);
                addDrop(BlockRegistry.SACK_BLOCK);
                addDrop(BlockRegistry.SAFE_BLOCK);
                addDrop(BlockRegistry.ROPE);
                addDrop(BlockRegistry.BOAT_IN_A_JAR);
                addDrop(BlockRegistry.TERRARIUM);
                addDrop(BlockRegistry.PEDESTAL);
                addDrop(BlockRegistry.SMALL_PEDESTAL);
                addDrop(BlockRegistry.DISPLAY);
                addDrop(BlockRegistry.BOOK_PILE_HORIZONTAL);
                addDrop(BlockRegistry.BOOK_PILE_VERTICAL);
                addDrop(BlockRegistry.RED_BUTTON);
                addDrop(BlockRegistry.RED_SAFE_BUTTON);
                addDrop(BlockRegistry.CONSOLE_LEVER);
                addDrop(BlockRegistry.STONE_PILLAR);
                addDrop(BlockRegistry.BIG_CHAIN);
                addDrop(BlockRegistry.BAR_PANEL);
                addDrop(BlockRegistry.LATTICE);
                addDrop(BlockRegistry.THATCH);
                addDrop(BlockRegistry.THATCH_SLAB);
                addDrop(BlockRegistry.THATCH_STAIRS);
                addDrop(BlockRegistry.BUNTING_CEILING);
                addDrop(BlockRegistry.BUNTING_WALL);

                addDrop(BlockRegistry.TELESCOPE, LootTable.builder().pool(addSurvivesExplosionCondition(BlockRegistry.TELESCOPE, LootPool.builder()
                    .rolls(new ConstantLootNumberProvider(1))
                    .with(ItemEntry.builder(BlockRegistry.TELESCOPE)
                        .conditionally(BlockStatePropertyLootCondition.builder(BlockRegistry.TELESCOPE).properties(StatePredicate.Builder.create().exactMatch(TallPlantBlock.HALF, DoubleBlockHalf.LOWER)))
                    )
                )));
	}
}