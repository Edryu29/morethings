package com.edryu.moreblocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class MoreBlocksModelProvider extends FabricModelProvider {
    public MoreBlocksModelProvider(FabricDataOutput output) {
        super(output);
    }
    
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(MoreBlocksRegister.DAUB);
        blockStateModelGenerator.registerSimpleCubeAll(MoreBlocksRegister.DAUB_BRACE);
        blockStateModelGenerator.registerSimpleCubeAll(MoreBlocksRegister.DAUB_BRACE_FLIPPED);
        blockStateModelGenerator.registerSimpleCubeAll(MoreBlocksRegister.DAUB_CROSS_BRACE);
        blockStateModelGenerator.registerSimpleCubeAll(MoreBlocksRegister.DAUB_FRAME);

        blockStateModelGenerator.registerItemModel(MoreBlocksRegister.ROPE);
    }
    
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}