package com.edryu.moreblocks;

import java.util.concurrent.CompletableFuture;

import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

public class MoreBlocksRecipeProvider extends FabricRecipeProvider {
	public MoreBlocksRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate(RecipeExporter recipeExporter) {
        FabricRecipeProvider.offerStonecuttingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.DAUB, Items.JUNGLE_PLANKS);
        FabricRecipeProvider.offerStonecuttingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.DAUB_BRACE, Items.JUNGLE_PLANKS);
        FabricRecipeProvider.offerStonecuttingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.DAUB_BRACE_FLIPPED, Items.JUNGLE_PLANKS);
        FabricRecipeProvider.offerStonecuttingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.DAUB_CROSS_BRACE, Items.JUNGLE_PLANKS);
        FabricRecipeProvider.offerStonecuttingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.DAUB_FRAME, Items.JUNGLE_PLANKS);
        FabricRecipeProvider.offerStonecuttingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.SACK, Items.JUNGLE_PLANKS);
        FabricRecipeProvider.offerStonecuttingRecipe(recipeExporter, RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.SAFE, Items.JUNGLE_PLANKS);
	}
}