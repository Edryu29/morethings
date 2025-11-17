package com.edryu.morethings.registry;

import com.edryu.morethings.MoreThingsMain;
import com.edryu.morethings.entity.*;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class EntityRegistry {
    public static final BlockEntityType<SackBlockEntity> SACK_ENTITY = registerEntity("sack", SackBlockEntity::new, BlockRegistry.SACK);
    public static final BlockEntityType<SafeBlockEntity> SAFE_ENTITY = registerEntity("safe", SafeBlockEntity::new, BlockRegistry.SAFE);
    public static final BlockEntityType<SmallPedestalBlockEntity> SMALL_PEDESTAL_ENTITY = registerEntity("small_pedestal", SmallPedestalBlockEntity::new, BlockRegistry.SMALL_PEDESTAL);
    public static final BlockEntityType<DisplayBlockEntity> DISPLAY_ENTITY = registerEntity("display", DisplayBlockEntity::new, BlockRegistry.DISPLAY);
    public static final BlockEntityType<PulleyBlockEntity> PULLEY_ENTITY = registerEntity("pulley", PulleyBlockEntity::new, BlockRegistry.PULLEY);
    public static final BlockEntityType<RopeKnotBlockEntity> ROPE_KNOT_ENTITY = registerEntity("rope_knot", RopeKnotBlockEntity::new, BlockRegistry.ROPE_KNOT);

    public static <T extends BlockEntity> BlockEntityType<T> registerEntity(String name, BlockEntityType.BlockEntitySupplier<? extends T> entityFactory, Block... blocks) {
        ResourceLocation entityID = ResourceLocation.fromNamespaceAndPath(MoreThingsMain.MOD_ID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, entityID, BlockEntityType.Builder.<T>of(entityFactory, blocks).build());
    }

    public static void initialize() {}
}