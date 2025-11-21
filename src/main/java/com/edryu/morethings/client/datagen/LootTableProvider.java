package com.edryu.morethings.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.ItemRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;

public class LootTableProvider extends FabricBlockLootTableProvider {
    
	protected LootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
                dropSelf(BlockRegistry.SACK);
                dropSelf(BlockRegistry.SAFE);
                dropSelf(BlockRegistry.PEDESTAL);
                dropSelf(BlockRegistry.SMALL_PEDESTAL);
                dropSelf(BlockRegistry.DISPLAY);
                dropSelf(BlockRegistry.BAR_PANEL);
                dropSelf(BlockRegistry.LATTICE);
                dropSelf(BlockRegistry.BOOK_STACK);
                dropSelf(BlockRegistry.BOOK_PILE);
                dropSelf(BlockRegistry.STONE_PILLAR);
                dropSelf(BlockRegistry.BIG_CHAIN);

                dropSelf(BlockRegistry.COG);
                dropSelf(BlockRegistry.PULLEY);
                dropSelf(BlockRegistry.TURN_TABLE);
                dropSelf(BlockRegistry.CRANK);
                dropSelf(BlockRegistry.CONSOLE);
                dropSelf(BlockRegistry.RED_BUTTON);

                dropOther(BlockRegistry.WALL_LANTERN, Items.LANTERN);
                dropOther(BlockRegistry.SOUL_WALL_LANTERN, Items.SOUL_LANTERN);

                dropSelf(BlockRegistry.ROPE);
                dropOther(BlockRegistry.ROPE_KNOT, ItemRegistry.ROPE);
                dropSelf(BlockRegistry.BUNTING_CEILING);
                dropSelf(BlockRegistry.BUNTING_WALL);

                dropSelf(BlockRegistry.DAUB);
                add(BlockRegistry.DAUB_SLAB, createSlabItemTable(BlockRegistry.DAUB_SLAB));
                dropSelf(BlockRegistry.THATCH);
                dropSelf(BlockRegistry.THATCH_STAIRS);
                add(BlockRegistry.THATCH_SLAB, createSlabItemTable(BlockRegistry.THATCH_SLAB));
                dropSelf(BlockRegistry.HAYSTACK);

                dropSelf(BlockRegistry.BOAT_IN_A_JAR);
                dropSelf(BlockRegistry.TERRARIUM);
                dropSelf(BlockRegistry.GLOBE);
                dropSelf(BlockRegistry.GLOBE_SEPIA);
                add(BlockRegistry.TELESCOPE, createDoorTable(BlockRegistry.TELESCOPE));


                dropSelf(BlockRegistry.ACACIA_PALISADE);
                dropSelf(BlockRegistry.BAMBOO_PALISADE);
                dropSelf(BlockRegistry.BIRCH_PALISADE);
                dropSelf(BlockRegistry.CHERRY_PALISADE);
                dropSelf(BlockRegistry.CRIMSON_PALISADE);
                dropSelf(BlockRegistry.DARK_OAK_PALISADE);
                dropSelf(BlockRegistry.JUNGLE_PALISADE);
                dropSelf(BlockRegistry.MANGROVE_PALISADE);
                dropSelf(BlockRegistry.OAK_PALISADE);
                dropSelf(BlockRegistry.PALE_OAK_PALISADE);
                dropSelf(BlockRegistry.SPRUCE_PALISADE);
                dropSelf(BlockRegistry.WARPED_PALISADE);
	}
}