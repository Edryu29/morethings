package com.edryu.morethings.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.edryu.morethings.MoreThingsRegister;

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
                addDrop(MoreThingsRegister.BOAT_IN_A_JAR);
                addDrop(MoreThingsRegister.TERRARIUM);
                addDrop(MoreThingsRegister.PEDESTAL);
                addDrop(MoreThingsRegister.SMALL_PEDESTAL_BLOCK);
                addDrop(MoreThingsRegister.DISPLAY_BLOCK);
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
                addDrop(MoreThingsRegister.BUNTING_CEILING);
                addDrop(MoreThingsRegister.BUNTING_WALL);

                addDrop(MoreThingsRegister.TELESCOPE, LootTable.builder().pool(addSurvivesExplosionCondition(MoreThingsRegister.TELESCOPE, LootPool.builder()
                    .rolls(new ConstantLootNumberProvider(1))
                    .with(ItemEntry.builder(MoreThingsRegister.TELESCOPE)
                        .conditionally(BlockStatePropertyLootCondition.builder(MoreThingsRegister.TELESCOPE).properties(StatePredicate.Builder.create().exactMatch(TallPlantBlock.HALF, DoubleBlockHalf.LOWER)))
                    )
                )));
	}
}