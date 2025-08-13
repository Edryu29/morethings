package com.edryu.moreblocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.registry.Registries;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.DyeColor;
import net.minecraft.text.Text;

public class MoreBlocksRegister {

	public static Block register(Block block, String name) {
		Identifier id = Identifier.of(MoreBlocksMain.MOD_ID, name);
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, id, blockItem);
		return Registry.register(Registries.BLOCK, id, block);
	}

    public static final Block DAUB = register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub");
    public static final Block DAUB_BRACE = register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_brace");
    public static final Block DAUB_BRACE_FLIPPED = register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_brace_flipped");
    public static final Block DAUB_CROSS_BRACE = register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_cross_brace");
    public static final Block DAUB_FRAME = register(new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.PACKED_MUD).mapColor(DyeColor.WHITE).strength(1.5f, 3f)),"daub_frame");


    public static final RegistryKey<ItemGroup> MORE_BLOCKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MoreBlocksMain.MOD_ID, "more_blocks"));
    public static final ItemGroup MORE_BLOCKS_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(DAUB)).displayName(Text.translatable("itemGroup.more_blocks")).build();

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(MORE_BLOCKS_GROUP_KEY).register((itemGroup) -> {itemGroup.add(DAUB.asItem());});
        ItemGroupEvents.modifyEntriesEvent(MORE_BLOCKS_GROUP_KEY).register((itemGroup) -> {itemGroup.add(DAUB_BRACE.asItem());});
        ItemGroupEvents.modifyEntriesEvent(MORE_BLOCKS_GROUP_KEY).register((itemGroup) -> {itemGroup.add(DAUB_BRACE_FLIPPED.asItem());});
        ItemGroupEvents.modifyEntriesEvent(MORE_BLOCKS_GROUP_KEY).register((itemGroup) -> {itemGroup.add(DAUB_CROSS_BRACE.asItem());});
        ItemGroupEvents.modifyEntriesEvent(MORE_BLOCKS_GROUP_KEY).register((itemGroup) -> {itemGroup.add(DAUB_FRAME.asItem());});
    }
}