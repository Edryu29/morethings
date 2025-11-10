package com.edryu.morethings.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.edryu.morethings.MoreThingsMain;
import com.edryu.morethings.registry.BlockRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public static final TagKey<Item> PALISADES = registerTag("palisades");

	public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(PALISADES)
                .add(BlockRegistry.ACACIA_PALISADE.asItem())
                .add(BlockRegistry.BAMBOO_PALISADE.asItem())
                .add(BlockRegistry.BIRCH_PALISADE.asItem())
                .add(BlockRegistry.CHERRY_PALISADE.asItem())
                .add(BlockRegistry.CRIMSON_PALISADE.asItem())
                .add(BlockRegistry.DARK_OAK_PALISADE.asItem())
                .add(BlockRegistry.JUNGLE_PALISADE.asItem())
                .add(BlockRegistry.MANGROVE_PALISADE.asItem())
                .add(BlockRegistry.OAK_PALISADE.asItem())
                .add(BlockRegistry.PALE_OAK_PALISADE.asItem())
                .add(BlockRegistry.SPRUCE_PALISADE.asItem())
                .add(BlockRegistry.WARPED_PALISADE.asItem());
    }

	public static TagKey<Item> registerTag(String name) {
        Identifier tagID = Identifier.of(MoreThingsMain.MOD_ID, name);
		return TagKey.of(RegistryKeys.ITEM, tagID);
	}
}