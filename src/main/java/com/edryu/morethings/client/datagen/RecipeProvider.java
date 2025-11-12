package com.edryu.morethings.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.ItemRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

public class RecipeProvider extends FabricRecipeProvider {
    
	public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DAUB, 2)
            .pattern("10")
            .pattern("01")
            .input('0', Items.CLAY_BALL)
            .input('1', Items.WHEAT)
            .criterion(FabricRecipeProvider.hasItem(Items.WHEAT), FabricRecipeProvider.conditionsFromItem(Items.WHEAT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DAUB_SLAB, 6)
            .pattern("000")
            .pattern("   ")
            .pattern("   ")
            .input('0', BlockRegistry.DAUB)
            .criterion(FabricRecipeProvider.hasItem(BlockRegistry.DAUB), FabricRecipeProvider.conditionsFromItem(BlockRegistry.DAUB))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SACK, 1)
            .pattern("101")
            .pattern("1 1")
            .pattern("111")
            .input('0', Items.STRING)
            .input('1', Items.WHEAT)
            .criterion(FabricRecipeProvider.hasItem(Items.WHEAT), FabricRecipeProvider.conditionsFromItem(Items.WHEAT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SAFE, 1)
            .pattern("000")
            .pattern("0 0")
            .pattern("000")
            .input('0', Items.IRON_INGOT)
            .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ROPE, 3)
            .pattern("  0")
            .pattern(" 0 ")
            .pattern("0  ")
            .input('0', Items.STRING)
            .criterion(FabricRecipeProvider.hasItem(Items.STRING), FabricRecipeProvider.conditionsFromItem(Items.STRING))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ItemRegistry.BUNTING, 6)
            .pattern("000")
            .pattern("111")
            .pattern(" 1 ")
            .input('0', Items.STRING)
            .input('1', ItemTags.WOOL)
            .criterion(FabricRecipeProvider.hasItem(Items.STRING), FabricRecipeProvider.conditionsFromItem(Items.STRING))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockRegistry.BOAT_IN_A_JAR, 1)
            .pattern("111")
            .pattern("121")
            .pattern("000")
            .input('0', ItemTags.WOODEN_SLABS)
            .input('1', Items.GLASS_PANE)
            .input('2', ItemTags.BOATS)
            .criterion(FabricRecipeProvider.hasItem(Items.GLASS_PANE), FabricRecipeProvider.conditionsFromItem(Items.GLASS_PANE))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockRegistry.TERRARIUM, 1)
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockRegistry.TELESCOPE, 1)
            .pattern(" 0 ")
            .pattern(" 1 ")
            .pattern("1 1")
            .input('0', Items.SPYGLASS)
            .input('1', Items.STICK)
            .criterion(FabricRecipeProvider.hasItem(Items.SPYGLASS), FabricRecipeProvider.conditionsFromItem(Items.SPYGLASS))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockRegistry.GLOBE, 1)
            .pattern(" 0 ")
            .pattern("12 ")
            .pattern(" 1 ")
            .input('0', Items.MAP)
            .input('1', Items.GOLD_INGOT)
            .input('2', Items.BLUE_WOOL)
            .criterion(FabricRecipeProvider.hasItem(Items.MAP), FabricRecipeProvider.conditionsFromItem(Items.MAP))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockRegistry.GLOBE_SEPIA, 1)
            .pattern(" 0 ")
            .pattern("12 ")
            .pattern(" 1 ")
            .input('0', Items.MAP)
            .input('1', Items.GOLD_INGOT)
            .input('2', Items.GRAY_WOOL)
            .criterion(FabricRecipeProvider.hasItem(Items.MAP), FabricRecipeProvider.conditionsFromItem(Items.MAP))
            .offerTo(recipeExporter);
            
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockRegistry.BOOK_PILE_HORIZONTAL, 1)
            .pattern("   ")
            .pattern(" 0 ")
            .pattern("000")
            .input('0', Items.BOOK)
            .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
            .offerTo(recipeExporter);
            
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockRegistry.BOOK_PILE_VERTICAL, 1)
            .pattern(" 0 ")
            .pattern(" 0 ")
            .pattern("00 ")
            .input('0', Items.BOOK)
            .criterion(FabricRecipeProvider.hasItem(Items.BOOK), FabricRecipeProvider.conditionsFromItem(Items.BOOK))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.PEDESTAL, 1)
            .pattern("111")
            .pattern(" 0 ")
            .pattern("111")
            .input('0', ItemTags.STONE_BRICKS)
            .input('1', Items.STONE_BRICK_SLAB)
            .criterion(FabricRecipeProvider.hasItem(Items.STONE_BRICKS), FabricRecipeProvider.conditionsFromItem(Items.STONE_BRICKS))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SMALL_PEDESTAL, 1)
            .pattern(" 0 ")
            .pattern("010")
            .pattern(" 0 ")
            .input('0', Items.STONE_BRICK_SLAB)
            .input('1', Items.ITEM_FRAME)
            .criterion(FabricRecipeProvider.hasItem(Items.ITEM_FRAME), FabricRecipeProvider.conditionsFromItem(Items.ITEM_FRAME))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DISPLAY, 1)
            .pattern("111")
            .pattern("1 1")
            .pattern("000")
            .input('0', ItemTags.PLANKS)
            .input('1', Items.GLASS_PANE)
            .criterion(FabricRecipeProvider.hasItem(Items.GLASS_PANE), FabricRecipeProvider.conditionsFromItem(Items.GLASS_PANE))
            .offerTo(recipeExporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BlockRegistry.RED_BUTTON)
                .input(Items.STONE_BUTTON)
                .input(Items.RED_DYE)
                .criterion(FabricRecipeProvider.hasItem(Items.STONE_BUTTON), FabricRecipeProvider.conditionsFromItem(Items.STONE_BUTTON))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BlockRegistry.RED_SAFE_BUTTON, 1)
            .pattern("   ")
            .pattern("111")
            .pattern("101")
            .input('0', BlockRegistry.RED_BUTTON)
            .input('1', Items.GLASS_PANE)
            .criterion(FabricRecipeProvider.hasItem(BlockRegistry.RED_BUTTON), FabricRecipeProvider.conditionsFromItem(BlockRegistry.RED_BUTTON))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BlockRegistry.COG, 1)
            .pattern("000")
            .pattern("010")
            .pattern("000")
            .input('0', Items.COPPER_INGOT)
            .input('1', Items.REDSTONE_BLOCK)
            .criterion(FabricRecipeProvider.hasItem(Items.REDSTONE_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.REDSTONE_BLOCK))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BlockRegistry.PULLEY, 1)
            .pattern("010")
            .pattern("020")
            .pattern("010")
            .input('0', ItemTags.PLANKS)
            .input('1', ItemTags.WOODEN_SLABS)
            .input('2', Items.IRON_INGOT)
            .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BlockRegistry.CONSOLE_LEVER, 1)
            .pattern("101")
            .pattern("121")
            .pattern("111")
            .input('0', Items.GLASS_PANE)
            .input('1', Items.IRON_NUGGET)
            .input('2', Items.REDSTONE)
            .criterion(FabricRecipeProvider.hasItem(Items.REDSTONE), FabricRecipeProvider.conditionsFromItem(Items.REDSTONE))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.BIG_CHAIN, 4)
            .pattern("11 ")
            .pattern("00 ")
            .pattern("11 ")
            .input('0', Items.IRON_INGOT)
            .input('1', Items.IRON_NUGGET)
            .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.BAR_PANEL, 2)
            .pattern("00 ")
            .pattern("   ")
            .pattern("   ")
            .input('0', Items.IRON_BARS)
            .criterion(FabricRecipeProvider.hasItem(Items.IRON_BARS), FabricRecipeProvider.conditionsFromItem(Items.IRON_BARS))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.LATTICE, 4)
            .pattern(" 0 ")
            .pattern("0 0")
            .pattern(" 0 ")
            .input('0', ItemTags.PLANKS)
            .criterion(FabricRecipeProvider.hasItem(Items.OAK_PLANKS), FabricRecipeProvider.conditionsFromItem(Items.OAK_PLANKS))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.THATCH_SLAB, 6)
            .pattern("000")
            .pattern("   ")
            .pattern("   ")
            .input('0', BlockRegistry.THATCH)
            .criterion(FabricRecipeProvider.hasItem(BlockRegistry.THATCH), FabricRecipeProvider.conditionsFromItem(BlockRegistry.THATCH))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.THATCH_STAIRS, 4)
            .pattern("0  ")
            .pattern("00 ")
            .pattern("000")
            .input('0', BlockRegistry.THATCH)
            .criterion(FabricRecipeProvider.hasItem(BlockRegistry.THATCH), FabricRecipeProvider.conditionsFromItem(BlockRegistry.THATCH))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.HAYSTACK, 4)
            .pattern("00 ")
            .pattern("00 ")
            .pattern("   ")
            .input('0', Items.HAY_BLOCK)
            .criterion(FabricRecipeProvider.hasItem(Items.HAY_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.HAY_BLOCK))
            .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ItemRegistry.ORB, 1)
            .pattern(" 0 ")
            .pattern("010")
            .pattern(" 0 ")
            .input('0', Items.GLASS)
            .input('1', Items.LAPIS_LAZULI)
            .criterion(FabricRecipeProvider.hasItem(Items.LAPIS_LAZULI), FabricRecipeProvider.conditionsFromItem(Items.LAPIS_LAZULI))
            .offerTo(recipeExporter);

        CookingRecipeJsonBuilder.createSmoking(Ingredient.ofItems(Items.HAY_BLOCK), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.THATCH, 0.1f, 300)
            .criterion(FabricRecipeProvider.hasItem(Items.HAY_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.HAY_BLOCK))
            .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.STONE), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.STONE_PILLAR)
            .criterion(FabricRecipeProvider.hasItem(Items.STONE), FabricRecipeProvider.conditionsFromItem(Items.STONE))
            .offerTo(recipeExporter);

        // StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.STONE), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.PEDESTAL)
        //     .criterion(FabricRecipeProvider.hasItem(Items.STONE_BRICKS), FabricRecipeProvider.conditionsFromItem(Items.STONE_BRICKS))
        //     .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.BLUE_CRYSTAL)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.GREEN_CRYSTAL)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.PURPLE_CRYSTAL)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.RED_CRYSTAL)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.CITRINE)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.JADE)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.RUBY)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.SAPPHIRE)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.TANZANITE)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.TOPAZ)
            .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD), FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
            .offerTo(recipeExporter);

        createPalisadeRecipe(BlockRegistry.ACACIA_PALISADE, Items.STRIPPED_ACACIA_LOG, recipeExporter);
        createPalisadeRecipe(BlockRegistry.BAMBOO_PALISADE, Items.STRIPPED_BAMBOO_BLOCK, recipeExporter);
        createPalisadeRecipe(BlockRegistry.BIRCH_PALISADE, Items.STRIPPED_BIRCH_LOG, recipeExporter);
        createPalisadeRecipe(BlockRegistry.CHERRY_PALISADE, Items.STRIPPED_CHERRY_LOG, recipeExporter);
        createPalisadeRecipe(BlockRegistry.CRIMSON_PALISADE, Items.STRIPPED_CRIMSON_STEM, recipeExporter);
        createPalisadeRecipe(BlockRegistry.DARK_OAK_PALISADE, Items.STRIPPED_DARK_OAK_LOG, recipeExporter);
        createPalisadeRecipe(BlockRegistry.JUNGLE_PALISADE, Items.STRIPPED_JUNGLE_LOG, recipeExporter);
        createPalisadeRecipe(BlockRegistry.MANGROVE_PALISADE, Items.STRIPPED_MANGROVE_LOG, recipeExporter);
        createPalisadeRecipe(BlockRegistry.OAK_PALISADE, Items.STRIPPED_OAK_LOG, recipeExporter);
        // createPalisadeRecipe(BlockRegistry.PALE_OAK_PALISADE, Items.BIRCH_LOG, recipeExporter);
        createPalisadeRecipe(BlockRegistry.SPRUCE_PALISADE, Items.STRIPPED_SPRUCE_LOG, recipeExporter);
        createPalisadeRecipe(BlockRegistry.WARPED_PALISADE, Items.STRIPPED_WARPED_STEM, recipeExporter);
    }

    public static void createPalisadeRecipe(ItemConvertible output, ItemConvertible ingredient, RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 6)
            .pattern("00 ")
            .pattern("   ")
            .pattern("   ")
            .input('0', ingredient)
            .criterion(FabricRecipeProvider.hasItem(ingredient), FabricRecipeProvider.conditionsFromItem(ingredient))
            .offerTo(recipeExporter);
	}
}