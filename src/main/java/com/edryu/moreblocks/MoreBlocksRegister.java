package com.edryu.moreblocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.registry.Registries;

import com.edryu.moreblocks.blocks.CageBlock;
import com.edryu.moreblocks.blocks.RopeBlock;
import com.edryu.moreblocks.blocks.SackBlock;
import com.edryu.moreblocks.blocks.SafeBlock;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.DyeColor;
import net.minecraft.text.Text;

public class MoreBlocksRegister {

	public static Block registerBlock(Block block, String name) {
		Identifier blockID = Identifier.of(MoreBlocksMain.MOD_ID, name);
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, blockID, blockItem);
		return Registry.register(Registries.BLOCK, blockID, block);
	}

	public static Item registerItem(Item item, String id) {
		Identifier itemID = Identifier.of(MoreBlocksMain.MOD_ID, id);
		Item registeredItem = Registry.register(Registries.ITEM, itemID, item);
		return registeredItem;
	}


    // ITEMS
    public static final Item WRENCH = registerItem(new Item(new Item.Settings()), "wrench");

    
    // BLOCKS
    public static final Block DAUB = registerBlock(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub");
    public static final Block DAUB_BRACE = registerBlock(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_brace");
    public static final Block DAUB_BRACE_FLIPPED = registerBlock(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_brace_flipped");
    public static final Block DAUB_CROSS_BRACE = registerBlock(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_cross_brace");
    public static final Block DAUB_FRAME = registerBlock(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_frame");

    public static final Block CAGE = registerBlock(new CageBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).mapColor(DyeColor.GRAY).strength(3f, 6f).nonOpaque()),"cage");
    public static final Block SACK = registerBlock(new SackBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOL).mapColor(DyeColor.BROWN).strength(0.8f)),"sack");
    public static final Block SAFE = registerBlock(new SafeBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).mapColor(DyeColor.GRAY).strength(5.0F, 6.0F)),"safe");
    public static final Block ROPE = registerBlock(new RopeBlock(AbstractBlock.Settings.copy(Blocks.CHAIN)),"rope");


    // ITEM GROUP
    public static final RegistryKey<ItemGroup> MORE_BLOCKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MoreBlocksMain.MOD_ID, "more_blocks"));
    public static final ItemGroup MORE_BLOCKS_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(SACK)).displayName(Text.translatable("itemGroup.more_blocks")).build();


    public static void initialize() {
        // Register the group.
        Registry.register(Registries.ITEM_GROUP, MORE_BLOCKS_GROUP_KEY, MORE_BLOCKS_GROUP);

        // Register items to the custom item group.
        ItemGroupEvents.modifyEntriesEvent(MORE_BLOCKS_GROUP_KEY).register(itemGroup -> {
            // ITEMS
            itemGroup.add(WRENCH);

            // BLOCKS
            itemGroup.add(DAUB.asItem());
            itemGroup.add(DAUB_BRACE.asItem());
            itemGroup.add(DAUB_BRACE_FLIPPED.asItem());
            itemGroup.add(DAUB_CROSS_BRACE.asItem());
            itemGroup.add(DAUB_FRAME.asItem());
            itemGroup.add(CAGE.asItem());
            itemGroup.add(SACK.asItem());
            itemGroup.add(SAFE.asItem());
            itemGroup.add(ROPE.asItem());
        });
    }
}