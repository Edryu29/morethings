package com.edryu.moreblocks;

import java.util.concurrent.CompletableFuture;

import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

public class MoreBlocksRecipeProvider extends FabricRecipeProvider {
	public MoreBlocksRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.DAUB, 2)
            .pattern("21")
            .pattern("12")
            .input('1', Items.CLAY_BALL)
            .input('2', Items.WHEAT)
            .criterion(FabricRecipeProvider.hasItem(Items.CLAY_BALL), FabricRecipeProvider.conditionsFromItem(Items.CLAY_BALL))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.DAUB_BRACE, 1)
            .pattern("  1")
            .pattern("121")
            .pattern("1  ")
            .input('1', Items.STICK)
            .input('2', MoreBlocksRegister.DAUB)
            .criterion(FabricRecipeProvider.hasItem(MoreBlocksRegister.DAUB), FabricRecipeProvider.conditionsFromItem(MoreBlocksRegister.DAUB))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.DAUB_BRACE_FLIPPED, 1)
            .pattern("1  ")
            .pattern("121")
            .pattern("  1")
            .input('1', Items.STICK)
            .input('2', MoreBlocksRegister.DAUB)
            .criterion(FabricRecipeProvider.hasItem(MoreBlocksRegister.DAUB), FabricRecipeProvider.conditionsFromItem(MoreBlocksRegister.DAUB))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.DAUB_CROSS_BRACE, 1)
            .pattern("1 1")
            .pattern(" 2 ")
            .pattern("1 1")
            .input('1', Items.STICK)
            .input('2', MoreBlocksRegister.DAUB)
            .criterion(FabricRecipeProvider.hasItem(MoreBlocksRegister.DAUB), FabricRecipeProvider.conditionsFromItem(MoreBlocksRegister.DAUB))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.DAUB_FRAME, 1)
            .pattern(" 1 ")
            .pattern("121")
            .pattern(" 1 ")
            .input('1', Items.STICK)
            .input('2', MoreBlocksRegister.DAUB)
            .criterion(FabricRecipeProvider.hasItem(MoreBlocksRegister.DAUB), FabricRecipeProvider.conditionsFromItem(MoreBlocksRegister.DAUB))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.CAGE, 1)
            .pattern("111")
            .pattern("2 2")
            .pattern("000")
            .input('0', ItemTags.WOODEN_SLABS)
            .input('1', Items.IRON_INGOT)
            .input('2', Items.IRON_BARS)
            .criterion(FabricRecipeProvider.hasItem(Items.IRON_BARS), FabricRecipeProvider.conditionsFromItem(Items.IRON_BARS))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.SACK, 1)
            .pattern("101")
            .pattern("1 1")
            .pattern("111")
            .input('0', Items.STRING)
            .input('1', Items.WHEAT)
            .criterion(FabricRecipeProvider.hasItem(Items.WHEAT), FabricRecipeProvider.conditionsFromItem(Items.WHEAT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.SAFE, 1)
            .pattern("111")
            .pattern("1 1")
            .pattern("111")
            .input('1', Items.IRON_INGOT)
            .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.ROPE, 3)
            .pattern("  1")
            .pattern(" 1 ")
            .pattern("1  ")
            .input('1', Items.STRING)
            .criterion(FabricRecipeProvider.hasItem(Items.STRING), FabricRecipeProvider.conditionsFromItem(Items.STRING))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.JAR_BOAT, 1)
            .pattern("111")
            .pattern("121")
            .pattern("000")
            .input('0', ItemTags.WOODEN_SLABS)
            .input('1', Items.GLASS_PANE)
            .input('2', ItemTags.BOATS)
            .criterion(FabricRecipeProvider.hasItem(Items.GLASS_PANE), FabricRecipeProvider.conditionsFromItem(Items.GLASS_PANE))
            .offerTo(recipeExporter);
            
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreBlocksRegister.BOOK_PILE, 1)
            .pattern("   ")
            .pattern(" 1 ")
            .pattern("111")
            .input('1', Items.BOOK)
            .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, MoreBlocksRegister.WRENCH, 1)
            .pattern("1 1")
            .pattern(" 1 ")
            .pattern(" 1 ")
            .input('1', Items.COPPER_INGOT)
            .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT), FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
            .offerTo(recipeExporter);
	}
}