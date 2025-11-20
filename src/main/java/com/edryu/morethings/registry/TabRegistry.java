package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TabRegistry {
    public static final ResourceKey<CreativeModeTab> MORE_BLOCKS_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(MoreThingsMain.MOD_ID, "more_blocks"));
    public static final CreativeModeTab MORE_BLOCKS_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(BlockRegistry.TELESCOPE)).title(Component.translatable("itemGroup.more_blocks")).build();

    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MORE_BLOCKS_GROUP_KEY, MORE_BLOCKS_GROUP);

        ItemGroupEvents.modifyEntriesEvent(MORE_BLOCKS_GROUP_KEY).register(itemGroup -> {
            itemGroup.accept(BlockRegistry.SACK.asItem());
            itemGroup.accept(BlockRegistry.SAFE.asItem());
            itemGroup.accept(BlockRegistry.DISPLAY.asItem());
            itemGroup.accept(BlockRegistry.SMALL_PEDESTAL.asItem());

            itemGroup.accept(BlockRegistry.DAUB.asItem());
            itemGroup.accept(BlockRegistry.DAUB_SLAB.asItem());
            itemGroup.accept(BlockRegistry.PEDESTAL.asItem());
            itemGroup.accept(BlockRegistry.STONE_PILLAR.asItem());

            itemGroup.accept(BlockRegistry.HAYSTACK.asItem());
            itemGroup.accept(BlockRegistry.THATCH.asItem());
            itemGroup.accept(BlockRegistry.THATCH_SLAB.asItem());
            itemGroup.accept(BlockRegistry.THATCH_STAIRS.asItem());

            itemGroup.accept(BlockRegistry.GLOBE.asItem());
            itemGroup.accept(BlockRegistry.GLOBE_SEPIA.asItem());
            itemGroup.accept(BlockRegistry.BOOK_STACK.asItem());
            itemGroup.accept(BlockRegistry.BOOK_PILE.asItem());
            itemGroup.accept(BlockRegistry.BOAT_IN_A_JAR.asItem());
            itemGroup.accept(BlockRegistry.TERRARIUM.asItem());
            itemGroup.accept(BlockRegistry.TELESCOPE.asItem());
            itemGroup.accept(BlockRegistry.SPYGLASS_STAND.asItem());

            itemGroup.accept(BlockRegistry.BIG_CHAIN.asItem());
            itemGroup.accept(BlockRegistry.ROPE.asItem());
            itemGroup.accept(ItemRegistry.BUNTING);

            itemGroup.accept(BlockRegistry.CRANK.asItem());
            itemGroup.accept(BlockRegistry.COG.asItem());
            itemGroup.accept(BlockRegistry.PULLEY.asItem());
            itemGroup.accept(BlockRegistry.TURN_TABLE.asItem());
            itemGroup.accept(BlockRegistry.RED_BUTTON.asItem());
            itemGroup.accept(BlockRegistry.CONSOLE.asItem());

            itemGroup.accept(BlockRegistry.ACACIA_PALISADE.asItem());
            itemGroup.accept(BlockRegistry.BAMBOO_PALISADE.asItem());
            itemGroup.accept(BlockRegistry.BIRCH_PALISADE.asItem());
            itemGroup.accept(BlockRegistry.CHERRY_PALISADE.asItem());
            itemGroup.accept(BlockRegistry.CRIMSON_PALISADE.asItem());
            itemGroup.accept(BlockRegistry.DARK_OAK_PALISADE.asItem());
            itemGroup.accept(BlockRegistry.JUNGLE_PALISADE.asItem());
            itemGroup.accept(BlockRegistry.MANGROVE_PALISADE.asItem());
            itemGroup.accept(BlockRegistry.OAK_PALISADE.asItem());
            itemGroup.accept(BlockRegistry.PALE_OAK_PALISADE.asItem());
            itemGroup.accept(BlockRegistry.SPRUCE_PALISADE.asItem());
            itemGroup.accept(BlockRegistry.WARPED_PALISADE.asItem());
            
            itemGroup.accept(BlockRegistry.BAR_PANEL.asItem());
            itemGroup.accept(BlockRegistry.LATTICE.asItem());

            itemGroup.accept(BlockRegistry.ACACIA_SHUTTER.asItem());
            itemGroup.accept(BlockRegistry.BIRCH_SHUTTER.asItem());
            itemGroup.accept(BlockRegistry.CHERRY_SHUTTER.asItem());
            itemGroup.accept(BlockRegistry.DARK_OAK_SHUTTER.asItem());
            itemGroup.accept(BlockRegistry.JUNGLE_SHUTTER.asItem());
            itemGroup.accept(BlockRegistry.MANGROVE_SHUTTER.asItem());
            itemGroup.accept(BlockRegistry.OAK_SHUTTER.asItem());
            itemGroup.accept(BlockRegistry.PALE_OAK_SHUTTER.asItem());
            itemGroup.accept(BlockRegistry.SPRUCE_SHUTTER.asItem());

            itemGroup.accept(ItemRegistry.ORB);
            itemGroup.accept(ItemRegistry.QUIVER);
            itemGroup.accept(ItemRegistry.BLUE_CRYSTAL);
            itemGroup.accept(ItemRegistry.GREEN_CRYSTAL);
            itemGroup.accept(ItemRegistry.PURPLE_CRYSTAL);
            itemGroup.accept(ItemRegistry.RED_CRYSTAL);

            itemGroup.accept(ItemRegistry.CITRINE);
            itemGroup.accept(ItemRegistry.JADE);
            itemGroup.accept(ItemRegistry.RUBY);
            itemGroup.accept(ItemRegistry.SAPPHIRE);
            itemGroup.accept(ItemRegistry.TANZANITE);
            itemGroup.accept(ItemRegistry.TOPAZ);
        });
    }
}