package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;
import com.edryu.morethings.entity.*;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityRegistry {
    public static final BlockEntityType<SackBlockEntity> SACK_BLOCK_ENTITY = registerEntity("sack", SackBlockEntity::new, BlockRegistry.SACK_BLOCK);
    public static final BlockEntityType<SafeBlockEntity> SAFE_BLOCK_ENTITY = registerEntity("safe", SafeBlockEntity::new, BlockRegistry.SAFE_BLOCK);
    public static final BlockEntityType<SmallPedestalBlockEntity> SMALL_PEDESTAL_ENTITY = registerEntity("small_pedestal", SmallPedestalBlockEntity::new, BlockRegistry.SMALL_PEDESTAL);
    public static final BlockEntityType<DisplayBlockEntity> DISPLAY_ENTITY = registerEntity("display", DisplayBlockEntity::new, BlockRegistry.DISPLAY);

    public static <T extends BlockEntity> BlockEntityType<T> registerEntity(String name, BlockEntityType.BlockEntityFactory<? extends T> entityFactory, Block... blocks) {
        Identifier entityID = Identifier.of(MoreThingsMain.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, entityID, BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
    }

    public static void initialize() {}
}