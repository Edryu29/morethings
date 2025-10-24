package com.edryu.morethings;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

public class MoreThingsRecipeProvider extends FabricRecipeProvider {
	public MoreThingsRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.DAUB, 2)
            .pattern("21")
            .pattern("12")
            .input('1', Items.CLAY_BALL)
            .input('2', Items.WHEAT)
            .criterion(FabricRecipeProvider.hasItem(Items.WHEAT), FabricRecipeProvider.conditionsFromItem(Items.WHEAT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.DAUB_BRACE, 1)
            .pattern("  1")
            .pattern("121")
            .pattern("1  ")
            .input('1', Items.STICK)
            .input('2', MoreThingsRegister.DAUB)
            .criterion(FabricRecipeProvider.hasItem(MoreThingsRegister.DAUB), FabricRecipeProvider.conditionsFromItem(MoreThingsRegister.DAUB))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.DAUB_BRACE_FLIPPED, 1)
            .pattern("1  ")
            .pattern("121")
            .pattern("  1")
            .input('1', Items.STICK)
            .input('2', MoreThingsRegister.DAUB)
            .criterion(FabricRecipeProvider.hasItem(MoreThingsRegister.DAUB), FabricRecipeProvider.conditionsFromItem(MoreThingsRegister.DAUB))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.DAUB_CROSS_BRACE, 1)
            .pattern("1 1")
            .pattern(" 2 ")
            .pattern("1 1")
            .input('1', Items.STICK)
            .input('2', MoreThingsRegister.DAUB)
            .criterion(FabricRecipeProvider.hasItem(MoreThingsRegister.DAUB), FabricRecipeProvider.conditionsFromItem(MoreThingsRegister.DAUB))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.DAUB_FRAME, 1)
            .pattern(" 1 ")
            .pattern("121")
            .pattern(" 1 ")
            .input('1', Items.STICK)
            .input('2', MoreThingsRegister.DAUB)
            .criterion(FabricRecipeProvider.hasItem(MoreThingsRegister.DAUB), FabricRecipeProvider.conditionsFromItem(MoreThingsRegister.DAUB))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.SACK_BLOCK, 1)
            .pattern("101")
            .pattern("1 1")
            .pattern("111")
            .input('0', Items.STRING)
            .input('1', Items.WHEAT)
            .criterion(FabricRecipeProvider.hasItem(Items.WHEAT), FabricRecipeProvider.conditionsFromItem(Items.WHEAT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.SAFE_BLOCK, 1)
            .pattern("111")
            .pattern("1 1")
            .pattern("111")
            .input('1', Items.IRON_INGOT)
            .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.ROPE, 3)
            .pattern("  1")
            .pattern(" 1 ")
            .pattern("1  ")
            .input('1', Items.STRING)
            .criterion(FabricRecipeProvider.hasItem(Items.STRING), FabricRecipeProvider.conditionsFromItem(Items.STRING))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.JAR_BOAT, 1)
            .pattern("111")
            .pattern("121")
            .pattern("000")
            .input('0', ItemTags.WOODEN_SLABS)
            .input('1', Items.GLASS_PANE)
            .input('2', ItemTags.BOATS)
            .criterion(FabricRecipeProvider.hasItem(Items.GLASS_PANE), FabricRecipeProvider.conditionsFromItem(Items.GLASS_PANE))
            .offerTo(recipeExporter);
            
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.BOOK_PILE_HORIZONTAL, 1)
            .pattern("   ")
            .pattern(" 1 ")
            .pattern("111")
            .input('1', Items.BOOK)
            .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
            .offerTo(recipeExporter);
            
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.BOOK_PILE_VERTICAL, 1)
            .pattern(" 1 ")
            .pattern(" 1 ")
            .pattern("11 ")
            .input('1', Items.BOOK)
            .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, MoreThingsRegister.ORB, 1)
            .pattern(" 1 ")
            .pattern("121")
            .pattern(" 1 ")
            .input('1', Items.GLASS)
            .input('2', Items.LAPIS_LAZULI)
            .criterion(FabricRecipeProvider.hasItem(Items.LAPIS_LAZULI), FabricRecipeProvider.conditionsFromItem(Items.LAPIS_LAZULI))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.PEDESTAL, 1)
            .pattern("111")
            .pattern(" 0 ")
            .pattern("111")
            .input('0', ItemTags.STONE_BRICKS)
            .input('1', Items.STONE_BRICK_SLAB)
            .criterion(FabricRecipeProvider.hasItem(Items.STONE_BRICKS), FabricRecipeProvider.conditionsFromItem(Items.STONE_BRICKS))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.ITEM_DISPLAY_BLOCK, 1)
            .pattern(" 0 ")
            .pattern("0 0")
            .pattern(" 0 ")
            .input('0', Items.STONE_BRICK_SLAB)
            .criterion(FabricRecipeProvider.hasItem(Items.STONE_BRICK_SLAB), FabricRecipeProvider.conditionsFromItem(Items.STONE_BRICK_SLAB))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, MoreThingsRegister.CRYSTAL_BLUE, 1)
            .pattern(" 1 ")
            .pattern("121")
            .pattern(" 1 ")
            .input('1', Items.BLUE_STAINED_GLASS_PANE)
            .input('2', Items.AMETHYST_SHARD)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, MoreThingsRegister.CRYSTAL_GREEN, 1)
            .pattern(" 1 ")
            .pattern("121")
            .pattern(" 1 ")
            .input('1', Items.GREEN_STAINED_GLASS_PANE)
            .input('2', Items.AMETHYST_SHARD)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, MoreThingsRegister.CRYSTAL_PURPLE, 1)
            .pattern(" 1 ")
            .pattern("121")
            .pattern(" 1 ")
            .input('1', Items.PURPLE_STAINED_GLASS_PANE)
            .input('2', Items.AMETHYST_SHARD)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, MoreThingsRegister.CRYSTAL_RED, 1)
            .pattern(" 1 ")
            .pattern("121")
            .pattern(" 1 ")
            .input('1', Items.RED_STAINED_GLASS_PANE)
            .input('2', Items.AMETHYST_SHARD)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, MoreThingsRegister.BUSHY_LEAVES, 4)
            .pattern(" 1 ")
            .pattern("1 1")
            .pattern(" 1 ")
            .input('1', ItemTags.LEAVES)
            .criterion(FabricRecipeProvider.hasItem(Items.OAK_LEAVES), FabricRecipeProvider.conditionsFromItem(Items.OAK_LEAVES))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, MoreThingsRegister.BUSHY_LEAVES_GREEN, 4)
            .pattern(" 1 ")
            .pattern("121")
            .pattern(" 1 ")
            .input('1', ItemTags.LEAVES)
            .input('2', Items.GREEN_DYE)
            .criterion(FabricRecipeProvider.hasItem(Items.OAK_LEAVES), FabricRecipeProvider.conditionsFromItem(Items.OAK_LEAVES))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, MoreThingsRegister.BUSHY_LEAVES_RED, 4)
            .pattern(" 1 ")
            .pattern("121")
            .pattern(" 1 ")
            .input('1', ItemTags.LEAVES)
            .input('2', Items.RED_DYE)
            .criterion(FabricRecipeProvider.hasItem(Items.OAK_LEAVES), FabricRecipeProvider.conditionsFromItem(Items.OAK_LEAVES))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, MoreThingsRegister.BUSHY_LEAVES_YELLOW, 4)
            .pattern(" 1 ")
            .pattern("121")
            .pattern(" 1 ")
            .input('1', ItemTags.LEAVES)
            .input('2', Items.YELLOW_DYE)
            .criterion(FabricRecipeProvider.hasItem(Items.OAK_LEAVES), FabricRecipeProvider.conditionsFromItem(Items.OAK_LEAVES))
            .offerTo(recipeExporter);

	}
}