package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;
import com.edryu.morethings.item.RopeItem;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ItemRegistry {
    public static final Item ORB = registerItem(new Item(new Item.Settings().maxCount(16)), "orb");

    public static final Item ROPE = registerItemBlock(new RopeItem(BlockRegistry.ROPE, new Item.Settings()));
    public static final Item BUNTING = registerVerticallyAttachableItem(new VerticallyAttachableBlockItem(BlockRegistry.BUNTING_CEILING, BlockRegistry.BUNTING_WALL, new Item.Settings(), Direction.UP), "bunting");

    public static final Item BLUE_CRYSTAL = registerItem(new Item(new Item.Settings().maxCount(16)), "blue_crystal");
    public static final Item GREEN_CRYSTAL = registerItem(new Item(new Item.Settings().maxCount(16)), "green_crystal");
    public static final Item PURPLE_CRYSTAL = registerItem(new Item(new Item.Settings().maxCount(16)), "purple_crystal");
    public static final Item RED_CRYSTAL = registerItem(new Item(new Item.Settings().maxCount(16)), "red_crystal");

    public static final Item CITRINE = registerItem(new Item(new Item.Settings().maxCount(16)), "citrine");
    public static final Item JADE = registerItem(new Item(new Item.Settings().maxCount(16)), "jade");
    public static final Item RUBY = registerItem(new Item(new Item.Settings().maxCount(16)), "ruby");
    public static final Item SAPPHIRE = registerItem(new Item(new Item.Settings().maxCount(16)), "sapphire");
    public static final Item TANZANITE = registerItem(new Item(new Item.Settings().maxCount(16)), "tanzanite");
    public static final Item TOPAZ = registerItem(new Item(new Item.Settings().maxCount(16)), "topaz");
    
	public static Item registerItem(Item item, String name) {
		Identifier itemID = Identifier.of(MoreThingsMain.MOD_ID, name);
		return Registry.register(Registries.ITEM, itemID, item);
	}

	public static Item registerItemBlock(BlockItem item) {
		Identifier itemID = Registries.BLOCK.getId(item.getBlock());
        return Registry.register(Registries.ITEM, itemID, item);
	}

	public static Item registerVerticallyAttachableItem(VerticallyAttachableBlockItem item, String name) {
		Identifier itemID = Identifier.of(MoreThingsMain.MOD_ID, name);
		return Registry.register(Registries.ITEM, itemID, item);
	}

    public static void initialize() {}
}