package com.edryu.morethings.client.datagen;

import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.ItemRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

public class ModelProvider extends FabricModelProvider {
    
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }
    
    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ItemRegistry.ORB, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.BUNTING, ModelTemplates.FLAT_ITEM);
        
        itemModelGenerator.generateFlatItem(ItemRegistry.CITRINE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.JADE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.RUBY, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.SAPPHIRE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.TANZANITE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.TOPAZ, ModelTemplates.FLAT_ITEM);
        
        itemModelGenerator.generateFlatItem(BlockRegistry.BIG_CHAIN.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(BlockRegistry.BOAT_IN_A_JAR.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(BlockRegistry.DISPLAY.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(BlockRegistry.ROPE.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(BlockRegistry.TELESCOPE.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(BlockRegistry.TERRARIUM.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(BlockRegistry.GLOBE.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(BlockRegistry.GLOBE_SEPIA.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(BlockRegistry.CRANK.asItem(), ModelTemplates.FLAT_ITEM);
    }

  @Override
  public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {}
}