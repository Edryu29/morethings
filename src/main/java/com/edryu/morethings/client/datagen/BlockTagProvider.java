package com.edryu.morethings.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.edryu.morethings.MoreThingsMain;
import com.edryu.morethings.registry.BlockRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public static final TagKey<Block> PALISADES = registerTag("palisades");
    public static final TagKey<Block> HANG_FROM_ROPES = registerTag("hang_from_ropes");
    public static final TagKey<Block> ROPE_SUPPORT = registerTag("rope_support");
    public static final TagKey<Block> UNMOVEABLE_BY_PULLEY = registerTag("unmoveable_by_pulley");
    public static final TagKey<Block> MOVEABLE_BY_PULLEY = registerTag("moveable_by_pulley");

	public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(PALISADES)
                .add(BlockRegistry.ACACIA_PALISADE)
                .add(BlockRegistry.BAMBOO_PALISADE)
                .add(BlockRegistry.BIRCH_PALISADE)
                .add(BlockRegistry.CHERRY_PALISADE)
                .add(BlockRegistry.CRIMSON_PALISADE)
                .add(BlockRegistry.DARK_OAK_PALISADE)
                .add(BlockRegistry.JUNGLE_PALISADE)
                .add(BlockRegistry.MANGROVE_PALISADE)
                .add(BlockRegistry.OAK_PALISADE)
                .add(BlockRegistry.PALE_OAK_PALISADE)
                .add(BlockRegistry.SPRUCE_PALISADE)
                .add(BlockRegistry.WARPED_PALISADE);
    }

	public static TagKey<Block> registerTag(String name) {
        Identifier tagID = Identifier.of(MoreThingsMain.MOD_ID, name);
		return TagKey.of(RegistryKeys.BLOCK, tagID);
	}
}