package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TabRegistry {
    public static final RegistryKey<ItemGroup> MORE_BLOCKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MoreThingsMain.MOD_ID, "more_blocks"));
    public static final ItemGroup MORE_BLOCKS_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(BlockRegistry.TELESCOPE)).displayName(Text.translatable("itemGroup.more_blocks")).build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, MORE_BLOCKS_GROUP_KEY, MORE_BLOCKS_GROUP);

        ItemGroupEvents.modifyEntriesEvent(MORE_BLOCKS_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(BlockRegistry.BIG_CHAIN.asItem());
            itemGroup.add(BlockRegistry.BOOK_PILE_HORIZONTAL.asItem());
            itemGroup.add(BlockRegistry.BOOK_PILE_VERTICAL.asItem());
            itemGroup.add(BlockRegistry.BAR_PANEL.asItem());
            itemGroup.add(BlockRegistry.LATTICE.asItem());

            itemGroup.add(BlockRegistry.DAUB.asItem());
            itemGroup.add(BlockRegistry.DAUB_SLAB.asItem());
            itemGroup.add(BlockRegistry.PEDESTAL.asItem());
            itemGroup.add(BlockRegistry.STONE_PILLAR.asItem());

            itemGroup.add(BlockRegistry.THATCH.asItem());
            itemGroup.add(BlockRegistry.THATCH_SLAB.asItem());
            itemGroup.add(BlockRegistry.THATCH_STAIRS.asItem());
            itemGroup.add(BlockRegistry.HAYSTACK.asItem());

            itemGroup.add(BlockRegistry.BOAT_IN_A_JAR.asItem());
            itemGroup.add(BlockRegistry.TERRARIUM.asItem());
            itemGroup.add(BlockRegistry.TELESCOPE.asItem());
            itemGroup.add(BlockRegistry.GLOBE.asItem());
            itemGroup.add(BlockRegistry.GLOBE_SEPIA.asItem());

            itemGroup.add(BlockRegistry.ROPE.asItem());

            itemGroup.add(BlockRegistry.RED_BUTTON.asItem());
            itemGroup.add(BlockRegistry.RED_SAFE_BUTTON.asItem());
            itemGroup.add(BlockRegistry.CONSOLE_LEVER.asItem());
            itemGroup.add(BlockRegistry.COG.asItem());

            itemGroup.add(BlockRegistry.SACK_BLOCK.asItem());
            itemGroup.add(BlockRegistry.SAFE_BLOCK.asItem());
            itemGroup.add(BlockRegistry.SMALL_PEDESTAL.asItem());
            itemGroup.add(BlockRegistry.DISPLAY.asItem());

            itemGroup.add(BlockRegistry.ACACIA_PALISADE.asItem());
            itemGroup.add(BlockRegistry.BAMBOO_PALISADE.asItem());
            itemGroup.add(BlockRegistry.BIRCH_PALISADE.asItem());
            itemGroup.add(BlockRegistry.CHERRY_PALISADE.asItem());
            itemGroup.add(BlockRegistry.CRIMSON_PALISADE.asItem());
            itemGroup.add(BlockRegistry.DARK_OAK_PALISADE.asItem());
            itemGroup.add(BlockRegistry.JUNGLE_PALISADE.asItem());
            itemGroup.add(BlockRegistry.MANGROVE_PALISADE.asItem());
            itemGroup.add(BlockRegistry.OAK_PALISADE.asItem());
            itemGroup.add(BlockRegistry.PALE_OAK_PALISADE.asItem());
            itemGroup.add(BlockRegistry.SPRUCE_PALISADE.asItem());
            itemGroup.add(BlockRegistry.WARPED_PALISADE.asItem());

            itemGroup.add(ItemRegistry.ORB);

            itemGroup.add(ItemRegistry.BUNTING);

            itemGroup.add(ItemRegistry.BLUE_CRYSTAL);
            itemGroup.add(ItemRegistry.GREEN_CRYSTAL);
            itemGroup.add(ItemRegistry.PURPLE_CRYSTAL);
            itemGroup.add(ItemRegistry.RED_CRYSTAL);

            itemGroup.add(ItemRegistry.CITRINE);
            itemGroup.add(ItemRegistry.JADE);
            itemGroup.add(ItemRegistry.RUBY);
            itemGroup.add(ItemRegistry.SAPPHIRE);
            itemGroup.add(ItemRegistry.TANZANITE);
            itemGroup.add(ItemRegistry.TOPAZ);
        });
    }
}