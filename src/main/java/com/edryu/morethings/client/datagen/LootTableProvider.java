package com.edryu.morethings.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.edryu.morethings.registry.BlockRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

public class LootTableProvider extends FabricBlockLootTableProvider {
    
	protected LootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
                addDrop(BlockRegistry.SACK_BLOCK);
                addDrop(BlockRegistry.SAFE_BLOCK);
                addDrop(BlockRegistry.PEDESTAL);
                addDrop(BlockRegistry.SMALL_PEDESTAL);
                addDrop(BlockRegistry.DISPLAY);
                addDrop(BlockRegistry.BAR_PANEL);
                addDrop(BlockRegistry.LATTICE);
                addDrop(BlockRegistry.BOOK_PILE_HORIZONTAL);
                addDrop(BlockRegistry.BOOK_PILE_VERTICAL);
                addDrop(BlockRegistry.STONE_PILLAR);
                addDrop(BlockRegistry.BIG_CHAIN);

                addDrop(BlockRegistry.RED_BUTTON);
                addDrop(BlockRegistry.RED_SAFE_BUTTON);
                addDrop(BlockRegistry.CONSOLE_LEVER);
                addDrop(BlockRegistry.COG);
                addDrop(BlockRegistry.PULLEY);

                addDrop(BlockRegistry.ROPE);
                addDrop(BlockRegistry.BUNTING_CEILING);
                addDrop(BlockRegistry.BUNTING_WALL);

                addDrop(BlockRegistry.DAUB);
                addDrop(BlockRegistry.DAUB_SLAB, slabDrops(BlockRegistry.DAUB_SLAB));
                addDrop(BlockRegistry.THATCH);
                addDrop(BlockRegistry.THATCH_STAIRS);
                addDrop(BlockRegistry.THATCH_SLAB, slabDrops(BlockRegistry.THATCH_SLAB));
                addDrop(BlockRegistry.HAYSTACK);


                addDrop(BlockRegistry.ACACIA_PALISADE);
                addDrop(BlockRegistry.BAMBOO_PALISADE);
                addDrop(BlockRegistry.BIRCH_PALISADE);
                addDrop(BlockRegistry.CHERRY_PALISADE);
                addDrop(BlockRegistry.CRIMSON_PALISADE);
                addDrop(BlockRegistry.DARK_OAK_PALISADE);
                addDrop(BlockRegistry.JUNGLE_PALISADE);
                addDrop(BlockRegistry.MANGROVE_PALISADE);
                addDrop(BlockRegistry.OAK_PALISADE);
                addDrop(BlockRegistry.PALE_OAK_PALISADE);
                addDrop(BlockRegistry.SPRUCE_PALISADE);
                addDrop(BlockRegistry.WARPED_PALISADE);

                addDrop(BlockRegistry.BOAT_IN_A_JAR);
                addDrop(BlockRegistry.TERRARIUM);
                addDrop(BlockRegistry.GLOBE);
                addDrop(BlockRegistry.GLOBE_SEPIA);
                addDrop(BlockRegistry.TELESCOPE, doorDrops(BlockRegistry.TELESCOPE));
	}
}