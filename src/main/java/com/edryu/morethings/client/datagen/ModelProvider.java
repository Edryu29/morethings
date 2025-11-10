package com.edryu.morethings.client.datagen;

import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.ItemRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModelProvider extends FabricModelProvider {
    
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }
    
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ItemRegistry.ORB, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.BUNTING, Models.GENERATED);

        itemModelGenerator.register(ItemRegistry.BLUE_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.GREEN_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.PURPLE_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.RED_CRYSTAL, Models.GENERATED);
        
        itemModelGenerator.register(ItemRegistry.CITRINE, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.JADE, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.RUBY, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.SAPPHIRE, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.TANZANITE, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.TOPAZ, Models.GENERATED);
        
        itemModelGenerator.register(BlockRegistry.BIG_CHAIN.asItem(), Models.GENERATED);
        itemModelGenerator.register(BlockRegistry.BOAT_IN_A_JAR.asItem(), Models.GENERATED);
        itemModelGenerator.register(BlockRegistry.DISPLAY.asItem(), Models.GENERATED);
        itemModelGenerator.register(BlockRegistry.ROPE.asItem(), Models.GENERATED);
        itemModelGenerator.register(BlockRegistry.TELESCOPE.asItem(), Models.GENERATED);
        itemModelGenerator.register(BlockRegistry.TERRARIUM.asItem(), Models.GENERATED);
        itemModelGenerator.register(BlockRegistry.GLOBE.asItem(), Models.GENERATED);
        itemModelGenerator.register(BlockRegistry.GLOBE_SEPIA.asItem(), Models.GENERATED);
    }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}
}