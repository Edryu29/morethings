package com.edryu.morethings.client.datagen;

import com.edryu.morethings.MoreThingsRegister;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class MoreThingsModelProvider extends FabricModelProvider {
    public MoreThingsModelProvider(FabricDataOutput output) {
        super(output);
    }
    
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(MoreThingsRegister.DAUB);
        blockStateModelGenerator.registerSimpleCubeAll(MoreThingsRegister.DAUB_BRACE);
        blockStateModelGenerator.registerSimpleCubeAll(MoreThingsRegister.DAUB_BRACE_FLIPPED);
        blockStateModelGenerator.registerSimpleCubeAll(MoreThingsRegister.DAUB_CROSS_BRACE);
        blockStateModelGenerator.registerSimpleCubeAll(MoreThingsRegister.DAUB_FRAME);
    }
    
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}