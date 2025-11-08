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
    public static final Block BIG_CHAIN = registerBlock(new BigChainBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.CHAIN).mapColor(MapColor.IRON_GRAY).strength(5f, 6f).solid().nonOpaque()),"big_chain");
    public static final Block DAUB = registerBlock(new DaubBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)), "daub");
    public static final Block PEDESTAL = registerBlock(new PedestalBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)),"pedestal");
    public static final Block STONE_PILLAR = registerBlock(new StonePillarBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)),"stone_pillar");

    public static final Block BAR_PANEL = registerBlock(new TrapdoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR)),"bar_panel");
    public static final Block LATTICE = registerBlock(new TrapdoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)),"lattice");

    public static final Block THATCH = registerBlock(new HayBlock(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)),"thatch");
    public static final Block THATCH_SLAB = registerBlock(new SlabBlock(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)),"thatch_slab");
    public static final Block THATCH_STAIRS = registerBlock(new StairsBlock(THATCH.getDefaultState(), AbstractBlock.Settings.copy(THATCH)),"thatch_stairs");
    public static final Block HAYSTACK = registerBlock(new HaystackBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).mapColor(MapColor.YELLOW).strength(0.2F).noCollision()),"haystack");

    public static final Block BOOK_PILE_HORIZONTAL = registerBlock(new BookPileHorizontalBlock(bookPileSettings()),"book_pile_horizontal");
    public static final Block BOOK_PILE_VERTICAL = registerBlock(new BookPileVerticalBlock(bookPileSettings()),"book_pile_vertical");

    public static final Block BOAT_IN_A_JAR = registerBlock(new BoatInAJarBlock(AbstractBlock.Settings.copy(Blocks.GLASS)),"boat_in_a_jar");
    public static final Block TERRARIUM = registerBlock(new TerrariumBlock(AbstractBlock.Settings.copy(Blocks.GLASS)),"terrarium");
    public static final Block TELESCOPE = registerBlock(new TelescopeBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)),"telescope");
    public static final Block GLOBE = registerBlock(new GlobeBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(2f, 4).nonOpaque()),"globe");
    public static final Block GLOBE_SEPIA = registerBlock(new GlobeBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(2f, 4).nonOpaque()),"globe_sepia");
    
    public static final Block ROPE = registerBlock(new RopeBlock(AbstractBlock.Settings.create().sounds(SoundRegistry.ROPE).strength(0.25f).nonOpaque()),"rope");
    public static final Block BUNTING_CEILING = registerBlock(new BuntingCeilingBlock(AbstractBlock.Settings.copy(ROPE).noCollision()),"bunting_ceiling");
    public static final Block BUNTING_WALL = registerBlock(new BuntingWallBlock(AbstractBlock.Settings.copy(ROPE).noCollision()),"bunting_wall");

    public static final Block RED_BUTTON = registerBlock(new RedButtonBlock(buttonSettings()),"red_button");
    public static final Block RED_SAFE_BUTTON = registerBlock(new RedSafeButtonBlock(buttonSettings()),"red_safe_button");
    public static final Block CONSOLE_LEVER = registerBlock(new ConsoleLeverBlock(buttonSettings()),"console_lever");

    public static final Block SACK_BLOCK = registerBlock(new SackBlock(AbstractBlock.Settings.create().sounds(SoundRegistry.SACK).strength(0.8f)),"sack");
    public static final Block SAFE_BLOCK = registerBlock(new SafeBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)),"safe");
    public static final Block SMALL_PEDESTAL = registerBlock(new SmallPedestalBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).strength(0.2f)),"small_pedestal");
    public static final Block DISPLAY = registerBlock(new DisplayBlock(AbstractBlock.Settings.copy(Blocks.GLASS)),"display");


    public static AbstractBlock.Settings bookPileSettings() {
        return AbstractBlock.Settings.create().sounds(SoundRegistry.BOOKS).strength(0.3f).nonOpaque();
    }

    public static AbstractBlock.Settings buttonSettings() {
        return AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).strength(0.5f).pistonBehavior(PistonBehavior.DESTROY).noCollision().nonOpaque();
    }

	public static Block registerBlock(Block block, String name) {
		Identifier blockID = Identifier.of(MoreThingsMain.MOD_ID, name);
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, blockID, blockItem);
		return Registry.register(Registries.BLOCK, blockID, block);
	}

    public static void initialize() {}
}