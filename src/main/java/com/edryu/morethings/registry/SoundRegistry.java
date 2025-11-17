package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public class SoundRegistry {
    public static final SoundEvent SACK_BREAK = registerSound("block.sack.break");
    public static final SoundEvent SACK_PLACE = registerSound("block.sack.place");
    public static final SoundEvent SACK_OPEN = registerSound("block.sack.open");

    public static final SoundEvent ROPE_BREAK = registerSound("block.rope.break");
    public static final SoundEvent ROPE_PLACE = registerSound("block.rope.place");
    public static final SoundEvent ROPE_STEP = registerSound("block.rope.step");
    public static final SoundEvent ROPE_HIT = registerSound("block.rope.hit");
    public static final SoundEvent ROPE_FALL = registerSound("block.rope.fall");
    public static final SoundEvent ROPE_SLIDE = registerSound("block.rope.slide");
    
    public static final SoundEvent CRANK = registerSound("block.crank");
    public static final SoundEvent BLOCK_ROTATE = registerSound("block.rotate");

    public static final SoundType BOOKS = new SoundType(1.0F, 1.0F, SoundEvents.CHISELED_BOOKSHELF_PICKUP, SoundEvents.BOOK_PUT, SoundEvents.CHISELED_BOOKSHELF_PLACE, SoundEvents.BOOK_PUT, SoundEvents.BOOK_PUT);
    public static final SoundType SACK = new SoundType(1.0F, 1.0F, SACK_BREAK, SoundEvents.WOOL_STEP, SACK_PLACE, SoundEvents.WOOL_HIT, SoundEvents.WOOL_FALL);
    public static final SoundType ROPE = new SoundType(1.0F, 1.0F, ROPE_BREAK, ROPE_STEP, ROPE_PLACE, ROPE_HIT, ROPE_FALL);

	private static SoundEvent registerSound(String id) {
		ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(MoreThingsMain.MOD_ID, id);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
	}

	public static void initialize() {}
}
