package com.edryu.morethings.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.edryu.morethings.MoreThingsRegister;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
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
            .pattern("10")
            .pattern("01")
            .input('0', Items.CLAY_BALL)
            .input('1', Items.WHEAT)
            .criterion(FabricRecipeProvider.hasItem(Items.WHEAT), FabricRecipeProvider.conditionsFromItem(Items.WHEAT))
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
            .pattern("000")
            .pattern("0 0")
            .pattern("000")
            .input('0', Items.IRON_INGOT)
            .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.ROPE, 3)
            .pattern("  0")
            .pattern(" 0 ")
            .pattern("0  ")
            .input('0', Items.STRING)
            .criterion(FabricRecipeProvider.hasItem(Items.STRING), FabricRecipeProvider.conditionsFromItem(Items.STRING))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, MoreThingsRegister.BUNTING, 6)
            .pattern("000")
            .pattern("111")
            .pattern(" 1 ")
            .input('0', Items.STRING)
            .input('1', ItemTags.WOOL)
            .criterion(FabricRecipeProvider.hasItem(Items.STRING), FabricRecipeProvider.conditionsFromItem(Items.STRING))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, MoreThingsRegister.BOAT_IN_A_JAR, 1)
            .pattern("111")
            .pattern("121")
            .pattern("000")
            .input('0', ItemTags.WOODEN_SLABS)
            .input('1', Items.GLASS_PANE)
            .input('2', ItemTags.BOATS)
            .criterion(FabricRecipeProvider.hasItem(Items.GLASS_PANE), FabricRecipeProvider.conditionsFromItem(Items.GLASS_PANE))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, MoreThingsRegister.TERRARIUM, 1)
            .pattern("222")
            .pattern("304")
            .pattern("111")
            .input('0', ItemTags.SAPLINGS)
            .input('1', Items.DIRT)
            .input('2', Items.GLASS_PANE)
            .input('3', Items.SHORT_GRASS)
            .input('4', Items.STONE)
            .criterion(FabricRecipeProvider.hasItem(Items.GLASS_PANE), FabricRecipeProvider.conditionsFromItem(Items.GLASS_PANE))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, MoreThingsRegister.TELESCOPE, 1)
            .pattern(" 0 ")
            .pattern(" 1 ")
            .pattern("1 1")
            .input('0', Items.SPYGLASS)
            .input('1', Items.STICK)
            .criterion(FabricRecipeProvider.hasItem(Items.SPYGLASS), FabricRecipeProvider.conditionsFromItem(Items.SPYGLASS))
            .offerTo(recipeExporter);
            
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, MoreThingsRegister.BOOK_PILE_HORIZONTAL, 1)
            .pattern("   ")
            .pattern(" 0 ")
            .pattern("000")
            .input('0', Items.BOOK)
            .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
            .offerTo(recipeExporter);
            
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, MoreThingsRegister.BOOK_PILE_VERTICAL, 1)
            .pattern(" 0 ")
            .pattern(" 0 ")
            .pattern("00 ")
            .input('0', Items.BOOK)
            .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, MoreThingsRegister.ORB, 1)
            .pattern(" 0 ")
            .pattern("010")
            .pattern(" 0 ")
            .input('0', Items.GLASS)
            .input('1', Items.LAPIS_LAZULI)
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.SMALL_PEDESTAL_BLOCK, 1)
            .pattern(" 0 ")
            .pattern("0 0")
            .pattern(" 0 ")
            .input('0', Items.STONE_BRICK_SLAB)
            .criterion(FabricRecipeProvider.hasItem(Items.STONE_BRICK_SLAB), FabricRecipeProvider.conditionsFromItem(Items.STONE_BRICK_SLAB))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.DISPLAY_BLOCK, 1)
            .pattern("111")
            .pattern("1 1")
            .pattern("000")
            .input('0', ItemTags.PLANKS)
            .input('1', Items.GLASS_PANE)
            .criterion(FabricRecipeProvider.hasItem(Items.GLASS_PANE), FabricRecipeProvider.conditionsFromItem(Items.GLASS_PANE))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, MoreThingsRegister.CRYSTAL_BLUE, 1)
            .pattern(" 0 ")
            .pattern("010")
            .pattern(" 0 ")
            .input('0', Items.BLUE_STAINED_GLASS_PANE)
            .input('1', Items.AMETHYST_SHARD)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, MoreThingsRegister.CRYSTAL_GREEN, 1)
            .pattern(" 0 ")
            .pattern("010")
            .pattern(" 0 ")
            .input('0', Items.GREEN_STAINED_GLASS_PANE)
            .input('1', Items.AMETHYST_SHARD)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, MoreThingsRegister.CRYSTAL_PURPLE, 1)
            .pattern(" 0 ")
            .pattern("010")
            .pattern(" 0 ")
            .input('0', Items.PURPLE_STAINED_GLASS_PANE)
            .input('1', Items.AMETHYST_SHARD)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, MoreThingsRegister.CRYSTAL_RED, 1)
            .pattern(" 0 ")
            .pattern("010")
            .pattern(" 0 ")
            .input('0', Items.RED_STAINED_GLASS_PANE)
            .input('1', Items.AMETHYST_SHARD)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, MoreThingsRegister.RED_BUTTON)
                .input(Items.STONE_BUTTON)
                .input(Items.RED_DYE)
                .criterion(FabricRecipeProvider.hasItem(Items.STONE_BUTTON), FabricRecipeProvider.conditionsFromItem(Items.STONE_BUTTON))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, MoreThingsRegister.RED_SAFE_BUTTON, 1)
            .pattern("   ")
            .pattern("111")
            .pattern("101")
            .input('0', MoreThingsRegister.RED_BUTTON)
            .input('1', Items.GLASS_PANE)
            .criterion(FabricRecipeProvider.hasItem(MoreThingsRegister.RED_BUTTON), FabricRecipeProvider.conditionsFromItem(MoreThingsRegister.RED_BUTTON))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, MoreThingsRegister.CONSOLE_LEVER, 1)
            .pattern("101")
            .pattern("121")
            .pattern("111")
            .input('0', Items.GLASS_PANE)
            .input('1', Items.IRON_NUGGET)
            .input('2', Items.REDSTONE)
            .criterion(FabricRecipeProvider.hasItem(Items.REDSTONE), FabricRecipeProvider.conditionsFromItem(Items.REDSTONE))
            .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.STONE), RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.STONE_PILLAR)
            .criterion(FabricRecipeProvider.hasItem(Items.STONE), FabricRecipeProvider.conditionsFromItem(Items.STONE))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.BIG_CHAIN, 4)
            .pattern("11 ")
            .pattern("00 ")
            .pattern("11 ")
            .input('0', Items.IRON_INGOT)
            .input('1', Items.IRON_NUGGET)
            .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.BAR_PANEL, 2)
            .pattern("00 ")
            .pattern("   ")
            .pattern("   ")
            .input('0', Items.IRON_BARS)
            .criterion(FabricRecipeProvider.hasItem(Items.IRON_BARS), FabricRecipeProvider.conditionsFromItem(Items.IRON_BARS))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.LATTICE, 4)
            .pattern(" 0 ")
            .pattern("0 0")
            .pattern(" 0 ")
            .input('0', ItemTags.PLANKS)
            .criterion(FabricRecipeProvider.hasItem(Items.OAK_PLANKS), FabricRecipeProvider.conditionsFromItem(Items.OAK_PLANKS))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.THATCH_SLAB, 6)
            .pattern("000")
            .pattern("   ")
            .pattern("   ")
            .input('0', MoreThingsRegister.THATCH)
            .criterion(FabricRecipeProvider.hasItem(MoreThingsRegister.THATCH), FabricRecipeProvider.conditionsFromItem(MoreThingsRegister.THATCH))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.THATCH_STAIRS, 4)
            .pattern("0  ")
            .pattern("00 ")
            .pattern("000")
            .input('0', MoreThingsRegister.THATCH)
            .criterion(FabricRecipeProvider.hasItem(MoreThingsRegister.THATCH), FabricRecipeProvider.conditionsFromItem(MoreThingsRegister.THATCH))
            .offerTo(recipeExporter);

        CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(Items.HAY_BLOCK), RecipeCategory.BUILDING_BLOCKS, MoreThingsRegister.THATCH, 0.1f, 300)
            .criterion(FabricRecipeProvider.hasItem(Items.HAY_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.HAY_BLOCK))
            .offerTo(recipeExporter);
    }
}