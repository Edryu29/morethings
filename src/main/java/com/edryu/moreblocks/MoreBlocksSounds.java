package com.edryu.moreblocks;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class MoreBlocksSounds {

    public static final SoundEvent SACK_BREAK = registerSound("block.sack.break");
    public static final SoundEvent SACK_PLACE = registerSound("block.sack.place");
    public static final SoundEvent SACK_OPEN = registerSound("block.sack.open");

    public static final SoundEvent ROPE_BREAK = registerSound("block.rope.break");
    public static final SoundEvent ROPE_PLACE = registerSound("block.rope.place");
    public static final SoundEvent ROPE_STEP = registerSound("block.rope.step");
    public static final SoundEvent ROPE_HIT = registerSound("block.rope.hit");
    public static final SoundEvent ROPE_FALL = registerSound("block.rope.fall");

    public static final BlockSoundGroup BOOKS = new BlockSoundGroup(1.0F, 1.0F, SoundEvents.BLOCK_CHISELED_BOOKSHELF_PICKUP, SoundEvents.ITEM_BOOK_PUT, SoundEvents.BLOCK_CHISELED_BOOKSHELF_PLACE, SoundEvents.ITEM_BOOK_PUT, SoundEvents.ITEM_BOOK_PUT);
    public static final BlockSoundGroup SACK = new BlockSoundGroup(1.0F, 1.0F, SACK_BREAK, SoundEvents.BLOCK_WOOL_STEP, SACK_PLACE, SoundEvents.BLOCK_WOOL_HIT, SoundEvents.BLOCK_WOOL_FALL);
    public static final BlockSoundGroup ROPE = new BlockSoundGroup(1.0F, 1.0F, ROPE_BREAK, ROPE_STEP, ROPE_PLACE, ROPE_HIT, ROPE_FALL);

	private static SoundEvent registerSound(String id) {
		Identifier identifier = Identifier.of(MoreBlocksMain.MOD_ID, id);
		return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
	}

	public static void initialize() {

	}

}
