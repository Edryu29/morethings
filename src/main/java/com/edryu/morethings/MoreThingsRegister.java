package com.edryu.morethings;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.registry.Registries;

import com.edryu.morethings.block.BookPileBlock;
import com.edryu.morethings.block.JarBoatBlock;
import com.edryu.morethings.block.PedestalBlock;
import com.edryu.morethings.block.RopeBlock;
import com.edryu.morethings.block.SackBlock;
import com.edryu.morethings.block.SafeBlock;
import com.edryu.morethings.entity.ItemDisplayBlockEntity;
import com.edryu.morethings.entity.SackBlockEntity;
import com.edryu.morethings.screen.SackScreenHandler;
import com.edryu.morethings.block.ItemDisplayBlock;
import net.minecraft.resource.featuretoggle.FeatureSet;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.DyeColor;
import net.minecraft.text.Text;

public class MoreThingsRegister {

    // BLOCKS
    public static final Block DAUB = registerBlock(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub");
    public static final Block DAUB_BRACE = registerBlock(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_brace");
    public static final Block DAUB_BRACE_FLIPPED = registerBlock(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_brace_flipped");
    public static final Block DAUB_CROSS_BRACE = registerBlock(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_cross_brace");
    public static final Block DAUB_FRAME = registerBlock(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_frame");
    public static final Block SAFE = registerBlock(new SafeBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)),"safe");
    public static final Block ROPE = registerBlock(new RopeBlock(AbstractBlock.Settings.create().sounds(MoreThingsSounds.ROPE).mapColor(DyeColor.BROWN).strength(0.25f)),"rope");
    public static final Block JAR_BOAT = registerBlock(new JarBoatBlock(AbstractBlock.Settings.copy(Blocks.GLASS)),"jar_boat");
    public static final Block BOOK_PILE_HORIZONTAL = registerBlock(new BookPileBlock(AbstractBlock.Settings.create().sounds(MoreThingsSounds.BOOKS).mapColor(DyeColor.BROWN).strength(0.5F).nonOpaque()),"book_pile_horizontal");
    public static final Block BOOK_PILE_VERTICAL = registerBlock(new BookPileBlock(AbstractBlock.Settings.create().sounds(MoreThingsSounds.BOOKS).mapColor(DyeColor.BROWN).strength(0.5F).nonOpaque()),"book_pile_vertical");
    public static final Block PEDESTAL = registerBlock(new PedestalBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)),"pedestal");

    // BLOCKS WITH ENTITIES
    public static final Block ITEM_DISPLAY_BLOCK = registerBlock(new ItemDisplayBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(DyeColor.GRAY).strength(0.2F)),"item_display");
    public static final Block SACK_BLOCK = registerBlock(new SackBlock(AbstractBlock.Settings.create().sounds(MoreThingsSounds.SACK).mapColor(DyeColor.BROWN).strength(0.8f)),"sack");

    // ENTITIES
    public static final BlockEntityType<ItemDisplayBlockEntity> ITEM_DISPLAY_BLOCK_ENTITY = registerEntity("item_display", ItemDisplayBlockEntity::new, MoreThingsRegister.ITEM_DISPLAY_BLOCK);
    public static final BlockEntityType<SackBlockEntity> SACK_BLOCK_ENTITY = registerEntity("sack", SackBlockEntity::new, MoreThingsRegister.SACK_BLOCK);

    // SCREENS
    public static final ScreenHandlerType<SackScreenHandler> SACK_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MoreThingsMain.MOD_ID, "sack"), new ScreenHandlerType<>(SackScreenHandler::new, FeatureSet.empty()));

    // ITEMS
    public static final Item ORB = registerItem(new Item(new Item.Settings().maxCount(1)), "orb");


    // ITEM GROUP
    public static final RegistryKey<ItemGroup> MORE_BLOCKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MoreThingsMain.MOD_ID, "more_blocks"));
    public static final ItemGroup MORE_BLOCKS_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(SACK_BLOCK)).displayName(Text.translatable("itemGroup.more_blocks")).build();


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
            itemGroup.add(DAUB_BRACE.asItem());
            itemGroup.add(DAUB_BRACE_FLIPPED.asItem());
            itemGroup.add(DAUB_CROSS_BRACE.asItem());
            itemGroup.add(DAUB_FRAME.asItem());
            itemGroup.add(SACK_BLOCK.asItem());
            itemGroup.add(SAFE.asItem());
            itemGroup.add(ROPE.asItem());
            itemGroup.add(JAR_BOAT.asItem());
            itemGroup.add(BOOK_PILE_HORIZONTAL.asItem());
            itemGroup.add(BOOK_PILE_VERTICAL.asItem());
            itemGroup.add(PEDESTAL.asItem());
            itemGroup.add(ITEM_DISPLAY_BLOCK.asItem());

            // ITEMS
            itemGroup.add(ORB);
        });
    }
}