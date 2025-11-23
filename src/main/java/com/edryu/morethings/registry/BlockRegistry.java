package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;
import com.edryu.morethings.block.*;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BlockRegistry {
    public static final Block BIG_CHAIN = registerBlock(new BigChainBlock(BlockBehaviour.Properties.of().sound(SoundType.CHAIN).mapColor(MapColor.METAL).strength(5f, 6f).requiresCorrectToolForDrops().forceSolidOn().noOcclusion()),"big_chain", true);
    public static final Block BOOK_STACK = registerBlock(new BookStackBlock(bookPileSettings()),"book_stack", true);
    public static final Block BOOK_PILE = registerBlock(new BookPileBlock(bookPileSettings()),"book_pile", true);
    public static final Block BAR_PANEL = registerBlock(new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_TRAPDOOR)),"bar_panel", true);
    public static final Block LATTICE = registerBlock(new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)),"lattice", true);

    public static final Block WALL_LANTERN = registerBlock(new WallLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)),"wall_lantern", false);
    public static final Block SOUL_WALL_LANTERN = registerBlock(new WallLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_LANTERN)),"soul_wall_lantern", false);

    public static final Block DAUB = registerBlock(new DaubBlock(BlockBehaviour.Properties.of().sound(SoundType.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)), "daub", true);
    public static final Block DAUB_SLAB = registerBlock(new DaubSlabBlock(BlockBehaviour.Properties.ofFullCopy(DAUB)), "daub_slab", true);
    public static final Block PEDESTAL = registerBlock(new PedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)),"pedestal", true);
    public static final Block STONE_PILLAR = registerBlock(new StonePillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)),"stone_pillar", true);

    public static final Block THATCH = registerBlock(new HayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)),"thatch", true);
    public static final Block THATCH_SLAB = registerBlock(new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)),"thatch_slab", true);
    public static final Block THATCH_STAIRS = registerBlock(new StairBlock(THATCH.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(THATCH)),"thatch_stairs", true);
    public static final Block HAYSTACK = registerBlock(new HaystackBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_YELLOW)),"haystack", true);

    public static final Block BOAT_IN_A_JAR = registerBlock(new BoatInAJarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)),"boat_in_a_jar", true);
    public static final Block TERRARIUM = registerBlock(new TerrariumBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)),"terrarium", true);
    public static final Block TELESCOPE = registerBlock(new TelescopeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)),"telescope", true);
    public static final Block GLOBE = registerBlock(new GlobeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(2f, 4).noOcclusion()),"globe", true);
    public static final Block GLOBE_SEPIA = registerBlock(new GlobeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(2f, 4).noOcclusion()),"globe_sepia", true);
    
    public static final Block ROPE = registerBlock(new RopeBlock(BlockBehaviour.Properties.of().sound(SoundRegistry.ROPE).strength(0.25f).noOcclusion()),"rope", true);
    public static final Block ROPE_KNOT = registerBlock(new RopeKnotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)),"rope_knot", false);
    public static final Block BUNTING_CEILING = registerBlock(new BuntingCeilingBlock(BlockBehaviour.Properties.ofFullCopy(ROPE).noCollission()),"bunting_ceiling", false);
    public static final Block BUNTING_WALL = registerBlock(new BuntingWallBlock(BlockBehaviour.Properties.ofFullCopy(ROPE).noCollission()),"bunting_wall", false);

    public static final Block CRANK = registerBlock(new CrankBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LEVER)),"crank", true);
    public static final Block COG = registerBlock(new CogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)),"cog", true);
    public static final Block PULLEY = registerBlock(new PulleyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL)),"pulley", true);
    public static final Block TURN_TABLE = registerBlock(new TurnTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DISPENSER)),"turn_table", true);
    public static final Block ILLUMINATOR = registerBlock(new IlluminatorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SEA_LANTERN)),"redstone_illuminator", true);
    public static final Block CONSOLE = registerBlock(new ConsoleBlock(buttonSettings()),"console", true);
    public static final Block RED_BUTTON = registerBlock(new RedButtonBlock(buttonSettings()),"red_button", true);

    public static final Block SACK = registerBlock(new SackBlock(BlockBehaviour.Properties.of().sound(SoundRegistry.SACK).strength(0.8f)),"sack", true);
    public static final Block SAFE = registerBlock(new SafeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)),"safe", true);
    public static final Block SMALL_PEDESTAL = registerBlock(new SmallPedestalBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(0.2f)),"small_pedestal", true);
    public static final Block DISPLAY = registerBlock(new DisplayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)),"display", true);

    public static final Block ACACIA_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_FENCE)),"acacia_palisade", true);
    public static final Block BAMBOO_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_FENCE)),"bamboo_palisade", true);
    public static final Block BIRCH_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_FENCE)),"birch_palisade", true);
    public static final Block CHERRY_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_FENCE)),"cherry_palisade", true);
    public static final Block CRIMSON_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FENCE)),"crimson_palisade", true);
    public static final Block DARK_OAK_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_FENCE)),"dark_oak_palisade", true);
    public static final Block JUNGLE_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_FENCE)),"jungle_palisade", true);
    public static final Block MANGROVE_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_FENCE)),"mangrove_palisade", true);
    public static final Block OAK_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)),"oak_palisade", true);
    public static final Block PALE_OAK_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_FENCE)),"pale_oak_palisade", true);
    public static final Block SPRUCE_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_FENCE)),"spruce_palisade", true);
    public static final Block WARPED_PALISADE = registerBlock(new PalisadeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_FENCE)),"warped_palisade", true);

    public static BlockBehaviour.Properties bookPileSettings() {
        return BlockBehaviour.Properties.of().sound(SoundRegistry.BOOKS).strength(0.3f).noOcclusion();
    }

    public static BlockBehaviour.Properties buttonSettings() {
        return BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(0.5f).pushReaction(PushReaction.DESTROY).noCollission().noOcclusion();
    }

	public static Block registerBlock(Block block, String name, boolean withItem) {
		ResourceLocation blockID = ResourceLocation.fromNamespaceAndPath(MoreThingsMain.MOD_ID, name);
        if (withItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, blockID, blockItem);
        }
		return Registry.register(BuiltInRegistries.BLOCK, blockID, block);
	}

    public static void initialize() {}
}