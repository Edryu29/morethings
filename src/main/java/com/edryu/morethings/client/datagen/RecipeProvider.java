package com.edryu.morethings.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.ItemRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class RecipeProvider extends FabricRecipeProvider {
    
	public RecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void buildRecipes(RecipeOutput recipeExporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DAUB, 2)
            .pattern("10")
            .pattern("01")
            .define('0', Items.CLAY_BALL)
            .define('1', Items.WHEAT)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.WHEAT), FabricRecipeProvider.has(Items.WHEAT))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DAUB_SLAB, 6)
            .pattern("000")
            .pattern("   ")
            .pattern("   ")
            .define('0', BlockRegistry.DAUB)
            .unlockedBy(FabricRecipeProvider.getHasName(BlockRegistry.DAUB), FabricRecipeProvider.has(BlockRegistry.DAUB))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SACK, 1)
            .pattern("101")
            .pattern("1 1")
            .pattern("111")
            .define('0', Items.STRING)
            .define('1', Items.WHEAT)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.WHEAT), FabricRecipeProvider.has(Items.WHEAT))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SAFE, 1)
            .pattern("000")
            .pattern("0 0")
            .pattern("000")
            .define('0', Items.IRON_INGOT)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.IRON_INGOT), FabricRecipeProvider.has(Items.IRON_INGOT))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ROPE, 3)
            .pattern("  0")
            .pattern(" 0 ")
            .pattern("0  ")
            .define('0', Items.STRING)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.STRING), FabricRecipeProvider.has(Items.STRING))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegistry.BUNTING, 6)
            .pattern("000")
            .pattern("111")
            .pattern(" 1 ")
            .define('0', Items.STRING)
            .define('1', ItemTags.WOOL)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.STRING), FabricRecipeProvider.has(Items.STRING))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.BOAT_IN_A_JAR, 1)
            .pattern("111")
            .pattern("121")
            .pattern("000")
            .define('0', ItemTags.WOODEN_SLABS)
            .define('1', Items.GLASS_PANE)
            .define('2', ItemTags.BOATS)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.GLASS_PANE), FabricRecipeProvider.has(Items.GLASS_PANE))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TERRARIUM, 1)
            .pattern("222")
            .pattern("304")
            .pattern("111")
            .define('0', ItemTags.SAPLINGS)
            .define('1', Items.DIRT)
            .define('2', Items.GLASS_PANE)
            .define('3', Items.SHORT_GRASS)
            .define('4', Items.STONE)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.GLASS_PANE), FabricRecipeProvider.has(Items.GLASS_PANE))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TELESCOPE, 1)
            .pattern(" 0 ")
            .pattern(" 1 ")
            .pattern("1 1")
            .define('0', Items.SPYGLASS)
            .define('1', Items.COPPER_INGOT)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.SPYGLASS), FabricRecipeProvider.has(Items.SPYGLASS))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.GLOBE, 1)
            .pattern(" 0 ")
            .pattern("12 ")
            .pattern(" 1 ")
            .define('0', Items.MAP)
            .define('1', Items.GOLD_INGOT)
            .define('2', Items.BLUE_WOOL)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.MAP), FabricRecipeProvider.has(Items.MAP))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.GLOBE_SEPIA, 1)
            .pattern(" 0 ")
            .pattern("12 ")
            .pattern(" 1 ")
            .define('0', Items.MAP)
            .define('1', Items.GOLD_INGOT)
            .define('2', Items.GRAY_WOOL)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.MAP), FabricRecipeProvider.has(Items.MAP))
            .save(recipeExporter);
            
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.BOOK_STACK, 1)
            .pattern("   ")
            .pattern("000")
            .pattern("   ")
            .define('0', Items.BOOK)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.BOOK), FabricRecipeProvider.has(Items.BOOK))
            .save(recipeExporter);
            
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.BOOK_PILE, 1)
            .pattern(" 0 ")
            .pattern(" 0 ")
            .pattern(" 0 ")
            .define('0', Items.BOOK)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.BOOK), FabricRecipeProvider.has(Items.BOOK))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.PEDESTAL, 1)
            .pattern("111")
            .pattern(" 0 ")
            .pattern("111")
            .define('0', ItemTags.STONE_BRICKS)
            .define('1', Items.STONE_BRICK_SLAB)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.STONE_BRICKS), FabricRecipeProvider.has(Items.STONE_BRICKS))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SMALL_PEDESTAL, 1)
            .pattern(" 0 ")
            .pattern("010")
            .pattern(" 0 ")
            .define('0', Items.STONE_BRICK_SLAB)
            .define('1', Items.ITEM_FRAME)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.ITEM_FRAME), FabricRecipeProvider.has(Items.ITEM_FRAME))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DISPLAY, 1)
            .pattern("111")
            .pattern("1 1")
            .pattern("000")
            .define('0', ItemTags.PLANKS)
            .define('1', Items.GLASS_PANE)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.GLASS_PANE), FabricRecipeProvider.has(Items.GLASS_PANE))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockRegistry.COG, 1)
            .pattern("000")
            .pattern("010")
            .pattern("000")
            .define('0', Items.COPPER_INGOT)
            .define('1', Items.REDSTONE_BLOCK)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.REDSTONE_BLOCK), FabricRecipeProvider.has(Items.REDSTONE_BLOCK))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockRegistry.PULLEY, 1)
            .pattern("010")
            .pattern("020")
            .pattern("010")
            .define('0', ItemTags.PLANKS)
            .define('1', ItemTags.WOODEN_SLABS)
            .define('2', Items.IRON_INGOT)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.IRON_INGOT), FabricRecipeProvider.has(Items.IRON_INGOT))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockRegistry.TURN_TABLE, 1)
            .pattern("000")
            .pattern("121")
            .pattern("131")
            .define('0', ItemTags.PLANKS)
            .define('1', Items.COBBLESTONE)
            .define('2', Items.COPPER_INGOT)
            .define('3', Items.REDSTONE)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.REDSTONE), FabricRecipeProvider.has(Items.REDSTONE))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockRegistry.ILLUMINATOR, 1)
            .pattern(" 0 ")
            .pattern("010")
            .pattern(" 0 ")
            .define('0', Items.REDSTONE)
            .define('1', Items.SEA_LANTERN)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.REDSTONE), FabricRecipeProvider.has(Items.REDSTONE))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockRegistry.CRANK, 1)
            .pattern("   ")
            .pattern(" 1 ")
            .pattern("000")
            .define('0', Items.SMOOTH_STONE_SLAB)
            .define('1', ItemTags.PLANKS)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.SMOOTH_STONE_SLAB), FabricRecipeProvider.has(Items.SMOOTH_STONE_SLAB))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockRegistry.CONSOLE, 1)
            .pattern("101")
            .pattern("121")
            .pattern("111")
            .define('0', Items.GLASS_PANE)
            .define('1', Items.IRON_NUGGET)
            .define('2', Items.REDSTONE)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.REDSTONE), FabricRecipeProvider.has(Items.REDSTONE))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockRegistry.RED_BUTTON, 1)
            .pattern(" 2 ")
            .pattern("111")
            .pattern("101")
            .define('0', Items.STONE_BUTTON)
            .define('1', Items.GLASS_PANE)
            .define('2', Items.RED_DYE)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.STONE_BUTTON), FabricRecipeProvider.has(Items.STONE_BUTTON))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.BIG_CHAIN, 4)
            .pattern("11 ")
            .pattern("00 ")
            .pattern("11 ")
            .define('0', Items.IRON_INGOT)
            .define('1', Items.IRON_NUGGET)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.IRON_INGOT), FabricRecipeProvider.has(Items.IRON_INGOT))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.BAR_PANEL, 2)
            .pattern("00 ")
            .pattern("   ")
            .pattern("   ")
            .define('0', Items.IRON_BARS)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.IRON_BARS), FabricRecipeProvider.has(Items.IRON_BARS))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.LATTICE, 4)
            .pattern(" 0 ")
            .pattern("0 0")
            .pattern(" 0 ")
            .define('0', ItemTags.PLANKS)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.OAK_PLANKS), FabricRecipeProvider.has(Items.OAK_PLANKS))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.THATCH, 2)
            .pattern("00 ")
            .pattern("00 ")
            .pattern("   ")
            .define('0', Items.WHEAT)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.WHEAT), FabricRecipeProvider.has(Items.WHEAT))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.THATCH_SLAB, 6)
            .pattern("000")
            .pattern("   ")
            .pattern("   ")
            .define('0', BlockRegistry.THATCH)
            .unlockedBy(FabricRecipeProvider.getHasName(BlockRegistry.THATCH), FabricRecipeProvider.has(BlockRegistry.THATCH))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.THATCH_STAIRS, 4)
            .pattern("0  ")
            .pattern("00 ")
            .pattern("000")
            .define('0', BlockRegistry.THATCH)
            .unlockedBy(FabricRecipeProvider.getHasName(BlockRegistry.THATCH), FabricRecipeProvider.has(BlockRegistry.THATCH))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.HAYSTACK, 4)
            .pattern("00 ")
            .pattern("00 ")
            .pattern("   ")
            .define('0', Items.HAY_BLOCK)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.HAY_BLOCK), FabricRecipeProvider.has(Items.HAY_BLOCK))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.ORB, 1)
            .pattern(" 0 ")
            .pattern("010")
            .pattern(" 0 ")
            .define('0', Items.GLASS)
            .define('1', Items.LAPIS_LAZULI)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.LAPIS_LAZULI), FabricRecipeProvider.has(Items.LAPIS_LAZULI))
            .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.QUIVER, 1)
            .pattern("0  ")
            .pattern("1  ")
            .pattern("2  ")
            .define('0', Items.STRING)
            .define('1', Items.LEATHER)
            .define('2', Items.FEATHER)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.LEATHER), FabricRecipeProvider.has(Items.LEATHER))
            .save(recipeExporter);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.STONE), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.STONE_PILLAR)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.STONE), FabricRecipeProvider.has(Items.STONE))
            .save(recipeExporter);

        // StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(Items.STONE), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.PEDESTAL)
        //     .criterion(FabricRecipeProvider.hasItem(Items.STONE_BRICKS), FabricRecipeProvider.conditionsFromItem(Items.STONE_BRICKS))
        //     .offerTo(recipeExporter);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.CITRINE)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.AMETHYST_SHARD), FabricRecipeProvider.has(Items.AMETHYST_SHARD))
            .save(recipeExporter);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.JADE)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.AMETHYST_SHARD), FabricRecipeProvider.has(Items.AMETHYST_SHARD))
            .save(recipeExporter);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.RUBY)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.AMETHYST_SHARD), FabricRecipeProvider.has(Items.AMETHYST_SHARD))
            .save(recipeExporter);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.SAPPHIRE)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.AMETHYST_SHARD), FabricRecipeProvider.has(Items.AMETHYST_SHARD))
            .save(recipeExporter);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.TANZANITE)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.AMETHYST_SHARD), FabricRecipeProvider.has(Items.AMETHYST_SHARD))
            .save(recipeExporter);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.AMETHYST_SHARD), RecipeCategory.MISC, ItemRegistry.TOPAZ)
            .unlockedBy(FabricRecipeProvider.getHasName(Items.AMETHYST_SHARD), FabricRecipeProvider.has(Items.AMETHYST_SHARD))
            .save(recipeExporter);

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

    public static void createPalisadeRecipe(ItemLike output, ItemLike ingredient, RecipeOutput recipeExporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
            .pattern("00 ")
            .pattern("   ")
            .pattern("   ")
            .define('0', ingredient)
            .unlockedBy(FabricRecipeProvider.getHasName(ingredient), FabricRecipeProvider.has(ingredient))
            .save(recipeExporter);
	}
}