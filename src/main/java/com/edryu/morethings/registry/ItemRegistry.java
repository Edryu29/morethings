package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;
import com.edryu.morethings.item.RopeItem;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemLore;

public class ItemRegistry {
    public static final Item ORB = registerItem(new Item(new Item.Properties().stacksTo(16)), "orb");

    public static final Item ROPE = registerItemBlock(new RopeItem(BlockRegistry.ROPE, new Item.Properties()));
    public static final Item BUNTING = registerVerticallyAttachableItem(new StandingAndWallBlockItem(BlockRegistry.BUNTING_CEILING, BlockRegistry.BUNTING_WALL, new Item.Properties(), Direction.UP), "bunting");

    public static final Item BLUE_CRYSTAL = registerItem(new Item(new Item.Properties().stacksTo(16)), "blue_crystal");
    public static final Item GREEN_CRYSTAL = registerItem(new Item(new Item.Properties().stacksTo(16)), "green_crystal");
    public static final Item PURPLE_CRYSTAL = registerItem(new Item(new Item.Properties().stacksTo(16)), "purple_crystal");
    public static final Item RED_CRYSTAL = registerItem(new Item(new Item.Properties().stacksTo(16)), "red_crystal");

    public static final Item CITRINE = registerItem(new Item(new Item.Properties().stacksTo(16)), "citrine");
    public static final Item JADE = registerItem(new Item(new Item.Properties().stacksTo(16)), "jade");
    public static final Item RUBY = registerItem(new Item(new Item.Properties().stacksTo(16)), "ruby");
    public static final Item SAPPHIRE = registerItem(new Item(new Item.Properties().stacksTo(16)), "sapphire");
    public static final Item TANZANITE = registerItem(new Item(new Item.Properties().stacksTo(16)), "tanzanite");
    public static final Item TOPAZ = registerItem(new Item(new Item.Properties().stacksTo(16)), "topaz");

    public static final Item QUIVER = registerItem(new BundleItem((new Item.Properties()).stacksTo(1)
            .component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)
            .component(DataComponents.LORE, new ItemLore(List.of(Component.translatable("item.morethings.quiver.hint").withStyle(ChatFormatting.GRAY))))), "quiver");
    
	public static Item registerItem(Item item, String name) {
		ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath(MoreThingsMain.MOD_ID, name);
		return Registry.register(BuiltInRegistries.ITEM, itemID, item);
	}

	public static Item registerItemBlock(BlockItem item) {
		ResourceLocation itemID = BuiltInRegistries.BLOCK.getKey(item.getBlock());
        return Registry.register(BuiltInRegistries.ITEM, itemID, item);
	}

	public static Item registerVerticallyAttachableItem(StandingAndWallBlockItem item, String name) {
		ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath(MoreThingsMain.MOD_ID, name);
		return Registry.register(BuiltInRegistries.ITEM, itemID, item);
	}

    public static void initialize() {}
}