package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;
import com.edryu.morethings.block.*;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class BlockRegistry {
    public static final Block BIG_CHAIN = registerBlock(new BigChainBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.CHAIN).mapColor(MapColor.IRON_GRAY).strength(5f, 6f).requiresTool().solid().nonOpaque()),"big_chain", true);
    public static final Block BOOK_PILE_HORIZONTAL = registerBlock(new BookPileHorizontalBlock(bookPileSettings()),"book_pile_horizontal", true);
    public static final Block BOOK_PILE_VERTICAL = registerBlock(new BookPileVerticalBlock(bookPileSettings()),"book_pile_vertical", true);
    public static final Block BAR_PANEL = registerBlock(new TrapdoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR)),"bar_panel", true);
    public static final Block LATTICE = registerBlock(new TrapdoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)),"lattice", true);

    public static final Block DAUB = registerBlock(new DaubBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)), "daub", true);
    public static final Block DAUB_SLAB = registerBlock(new DaubSlabBlock(AbstractBlock.Settings.copy(DAUB)), "daub_slab", true);
    public static final Block PEDESTAL = registerBlock(new PedestalBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)),"pedestal", true);
    public static final Block STONE_PILLAR = registerBlock(new StonePillarBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)),"stone_pillar", true);

    public static final Block THATCH = registerBlock(new HayBlock(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)),"thatch", true);
    public static final Block THATCH_SLAB = registerBlock(new SlabBlock(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)),"thatch_slab", true);
    public static final Block THATCH_STAIRS = registerBlock(new StairsBlock(THATCH.getDefaultState(), AbstractBlock.Settings.copy(THATCH)),"thatch_stairs", true);
    public static final Block HAYSTACK = registerBlock(new HaystackBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).mapColor(MapColor.YELLOW).strength(0.2F).noCollision()),"haystack", true);

    public static final Block BOAT_IN_A_JAR = registerBlock(new BoatInAJarBlock(AbstractBlock.Settings.copy(Blocks.GLASS)),"boat_in_a_jar", true);
    public static final Block TERRARIUM = registerBlock(new TerrariumBlock(AbstractBlock.Settings.copy(Blocks.GLASS)),"terrarium", true);
    public static final Block TELESCOPE = registerBlock(new TelescopeBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)),"telescope", true);
    public static final Block SPYGLASS_STAND = registerBlock(new SpyglassStandBlock(AbstractBlock.Settings.copy(TELESCOPE)),"spyglass_stand", true);
    public static final Block GLOBE = registerBlock(new GlobeBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(2f, 4).nonOpaque()),"globe", true);
    public static final Block GLOBE_SEPIA = registerBlock(new GlobeBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(2f, 4).nonOpaque()),"globe_sepia", true);
    
    public static final Block ROPE = registerBlock(new RopeBlock(AbstractBlock.Settings.create().sounds(SoundRegistry.ROPE).strength(0.25f).nonOpaque()),"rope", true);
    public static final Block ROPE_KNOT = registerBlock(new RopeKnotBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)),"rope_knot", false);
    public static final Block BUNTING_CEILING = registerBlock(new BuntingCeilingBlock(AbstractBlock.Settings.copy(ROPE).noCollision()),"bunting_ceiling", false);
    public static final Block BUNTING_WALL = registerBlock(new BuntingWallBlock(AbstractBlock.Settings.copy(ROPE).noCollision()),"bunting_wall", false);

    public static final Block CRANK = registerBlock(new CrankBlock(AbstractBlock.Settings.copy(Blocks.LEVER)),"crank", true);
    public static final Block COG = registerBlock(new CogBlock(AbstractBlock.Settings.copy(Blocks.COPPER_BLOCK)),"cog", true);
    public static final Block PULLEY = registerBlock(new PulleyBlock(AbstractBlock.Settings.copy(Blocks.BARREL)),"pulley", true);
    public static final Block TURN_TABLE = registerBlock(new TurnTableBlock(AbstractBlock.Settings.copy(Blocks.DISPENSER)),"turn_table", true);
    public static final Block CONSOLE = registerBlock(new ConsoleBlock(buttonSettings()),"console", true);
    public static final Block RED_BUTTON = registerBlock(new RedButtonBlock(buttonSettings()),"red_button", true);

    public static final Block SACK = registerBlock(new SackBlock(AbstractBlock.Settings.create().sounds(SoundRegistry.SACK).strength(0.8f)),"sack", true);
    public static final Block SAFE = registerBlock(new SafeBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)),"safe", true);
    public static final Block SMALL_PEDESTAL = registerBlock(new SmallPedestalBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).strength(0.2f)),"small_pedestal", true);
    public static final Block DISPLAY = registerBlock(new DisplayBlock(AbstractBlock.Settings.copy(Blocks.GLASS)),"display", true);

    public static final Block ACACIA_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_FENCE)),"acacia_palisade", true);
    public static final Block BAMBOO_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.BAMBOO_FENCE)),"bamboo_palisade", true);
    public static final Block BIRCH_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_FENCE)),"birch_palisade", true);
    public static final Block CHERRY_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.CHERRY_FENCE)),"cherry_palisade", true);
    public static final Block CRIMSON_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_FENCE)),"crimson_palisade", true);
    public static final Block DARK_OAK_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_FENCE)),"dark_oak_palisade", true);
    public static final Block JUNGLE_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.JUNGLE_FENCE)),"jungle_palisade", true);
    public static final Block MANGROVE_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_FENCE)),"mangrove_palisade", true);
    public static final Block OAK_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)),"oak_palisade", true);
    public static final Block PALE_OAK_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_FENCE)),"pale_oak_palisade", true);
    public static final Block SPRUCE_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_FENCE)),"spruce_palisade", true);
    public static final Block WARPED_PALISADE = registerBlock(new PalisadeBlock(AbstractBlock.Settings.copy(Blocks.WARPED_FENCE)),"warped_palisade", true);

    public static AbstractBlock.Settings bookPileSettings() {
        return AbstractBlock.Settings.create().sounds(SoundRegistry.BOOKS).strength(0.3f).nonOpaque();
    }

    public static AbstractBlock.Settings buttonSettings() {
        return AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(0.5f).pistonBehavior(PistonBehavior.DESTROY).noCollision().nonOpaque();
    }

	public static Block registerBlock(Block block, String name, boolean withItem) {
		Identifier blockID = Identifier.of(MoreThingsMain.MOD_ID, name);
        if (withItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, blockID, blockItem);
        }
		return Registry.register(Registries.BLOCK, blockID, block);
	}

    public static void initialize() {}
}