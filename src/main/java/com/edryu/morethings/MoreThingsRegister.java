package com.edryu.morethings;

import com.edryu.morethings.block.BigChainBlock;
import com.edryu.morethings.block.BookPileHorizontalBlock;
import com.edryu.morethings.block.BookPileVerticalBlock;
import com.edryu.morethings.block.DaubBlock;
import com.edryu.morethings.block.ItemDisplayBlock;
import com.edryu.morethings.block.JarBoatBlock;
import com.edryu.morethings.block.PedestalBlock;
import com.edryu.morethings.block.RedButtonBlock;
import com.edryu.morethings.block.RedSafeButtonBlock;
import com.edryu.morethings.block.RopeBlock;
import com.edryu.morethings.block.SackBlock;
import com.edryu.morethings.block.SafeBlock;
import com.edryu.morethings.block.StonePillarBlock;
import com.edryu.morethings.entity.ItemDisplayBlockEntity;
import com.edryu.morethings.entity.SackBlockEntity;
import com.edryu.morethings.entity.SafeBlockEntity;
import com.edryu.morethings.screen.SimpleScreenHandler;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class MoreThingsRegister {

    // SETTINGS
    public static final AbstractBlock.Settings BOOK_PILE_SETTINGS = AbstractBlock.Settings.create().sounds(MoreThingsSounds.BOOKS).strength(0.5F).nonOpaque();

    // BLOCKS
    public static final Block DAUB = registerBlock(new DaubBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)), "daub");
    public static final Block BOOK_PILE_HORIZONTAL = registerBlock(new BookPileHorizontalBlock(BOOK_PILE_SETTINGS),"book_pile_horizontal");
    public static final Block BOOK_PILE_VERTICAL = registerBlock(new BookPileVerticalBlock(BOOK_PILE_SETTINGS),"book_pile_vertical");
    public static final Block ROPE = registerBlock(new RopeBlock(AbstractBlock.Settings.create().sounds(MoreThingsSounds.ROPE).strength(0.25f).nonOpaque()),"rope");
    public static final Block JAR_BOAT = registerBlock(new JarBoatBlock(AbstractBlock.Settings.copy(Blocks.GLASS)),"jar_boat");
    public static final Block PEDESTAL = registerBlock(new PedestalBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)),"pedestal");
    public static final Block BIG_CHAIN = registerBlock(new BigChainBlock(AbstractBlock.Settings.create().solid().sounds(BlockSoundGroup.CHAIN).strength(5.0F, 6.0F).nonOpaque()),"big_chain");
    public static final Block STONE_PILLAR = registerBlock(new StonePillarBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)),"stone_pillar");
    public static final Block BAR_PANEL = registerBlock(new TrapdoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR)),"bar_panel");
    public static final Block LATTICE = registerBlock(new TrapdoorBlock(BlockSetType.OAK, AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR)),"lattice");

    // BUTTONS
    public static final Block RED_BUTTON = registerBlock(new RedButtonBlock(AbstractBlock.Settings.create().noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY)),"red_button");
    public static final Block RED_SAFE_BUTTON = registerBlock(new RedSafeButtonBlock(AbstractBlock.Settings.create().noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY).nonOpaque()),"red_safe_button");

    // BLOCKS WITH ENTITIES
    public static final Block ITEM_DISPLAY_BLOCK = registerBlock(new ItemDisplayBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).strength(0.2F)),"item_display");
    public static final Block SACK_BLOCK = registerBlock(new SackBlock(AbstractBlock.Settings.create().sounds(MoreThingsSounds.SACK).strength(0.8f)),"sack");
    public static final Block SAFE_BLOCK = registerBlock(new SafeBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)),"safe");

    // ENTITIES
    public static final BlockEntityType<ItemDisplayBlockEntity> ITEM_DISPLAY_BLOCK_ENTITY = registerEntity("item_display", ItemDisplayBlockEntity::new, MoreThingsRegister.ITEM_DISPLAY_BLOCK);
    public static final BlockEntityType<SackBlockEntity> SACK_BLOCK_ENTITY = registerEntity("sack", SackBlockEntity::new, MoreThingsRegister.SACK_BLOCK);
    public static final BlockEntityType<SafeBlockEntity> SAFE_BLOCK_ENTITY = registerEntity("safe", SafeBlockEntity::new, MoreThingsRegister.SAFE_BLOCK);

    // SCREENS
    public static final ScreenHandlerType<SimpleScreenHandler> SACK_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MoreThingsMain.MOD_ID, "sack"), new ScreenHandlerType<>(SimpleScreenHandler::new, FeatureSet.empty()));
    public static final ScreenHandlerType<SimpleScreenHandler> SAFE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MoreThingsMain.MOD_ID, "safe"), new ScreenHandlerType<>(SimpleScreenHandler::new, FeatureSet.empty()));

    // ITEMS
    public static final Item ORB = registerItem(new Item(new Item.Settings().maxCount(1)), "orb");
    public static final Item CRYSTAL_BLUE = registerItem(new Item(new Item.Settings().maxCount(16)), "crystal_blue");
    public static final Item CRYSTAL_GREEN = registerItem(new Item(new Item.Settings().maxCount(16)), "crystal_green");
    public static final Item CRYSTAL_PURPLE = registerItem(new Item(new Item.Settings().maxCount(16)), "crystal_purple");
    public static final Item CRYSTAL_RED = registerItem(new Item(new Item.Settings().maxCount(16)), "crystal_red");

    // ITEM GROUP
    public static final RegistryKey<ItemGroup> MORE_BLOCKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MoreThingsMain.MOD_ID, "more_blocks"));
    public static final ItemGroup MORE_BLOCKS_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(SACK_BLOCK)).displayName(Text.translatable("itemGroup.more_blocks")).build();

    // METHODS
	public static Block registerBlock(Block block, String name) {
		Identifier blockID = Identifier.of(MoreThingsMain.MOD_ID, name);
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, blockID, blockItem);
		return Registry.register(Registries.BLOCK, blockID, block);
	}

	public static Item registerItem(Item item, String name) {
		Identifier itemID = Identifier.of(MoreThingsMain.MOD_ID, name);
		return Registry.register(Registries.ITEM, itemID, item);
	}

    private static <T extends BlockEntity> BlockEntityType<T> registerEntity(String name, BlockEntityType.BlockEntityFactory<? extends T> entityFactory, Block... blocks) {
        Identifier entityID = Identifier.of(MoreThingsMain.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, entityID, BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
    }

    public static void initialize() {
        // Register the group.
        Registry.register(Registries.ITEM_GROUP, MORE_BLOCKS_GROUP_KEY, MORE_BLOCKS_GROUP);

        // Register items to the custom item group.
        ItemGroupEvents.modifyEntriesEvent(MORE_BLOCKS_GROUP_KEY).register(itemGroup -> {

            // BLOCKS
            itemGroup.add(DAUB.asItem());

            itemGroup.add(BOOK_PILE_HORIZONTAL.asItem());
            itemGroup.add(BOOK_PILE_VERTICAL.asItem());

            itemGroup.add(SACK_BLOCK.asItem());
            itemGroup.add(SAFE_BLOCK.asItem());
            itemGroup.add(ROPE.asItem());
            itemGroup.add(JAR_BOAT.asItem());
            itemGroup.add(PEDESTAL.asItem());
            itemGroup.add(ITEM_DISPLAY_BLOCK.asItem());
            itemGroup.add(STONE_PILLAR.asItem());
            itemGroup.add(BIG_CHAIN.asItem());
            itemGroup.add(BAR_PANEL.asItem());
            itemGroup.add(LATTICE.asItem());

            itemGroup.add(RED_BUTTON.asItem());
            itemGroup.add(RED_SAFE_BUTTON.asItem());

            // ITEMS
            itemGroup.add(ORB);
            itemGroup.add(CRYSTAL_BLUE);
            itemGroup.add(CRYSTAL_GREEN);
            itemGroup.add(CRYSTAL_PURPLE);
            itemGroup.add(CRYSTAL_RED);
        });
    }
}