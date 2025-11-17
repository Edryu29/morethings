package com.edryu.morethings.entity;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.registry.EntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
	

public class SmallPedestalBlockEntity extends BlockEntity {
    private ItemStack storedItem = ItemStack.EMPTY;

	public SmallPedestalBlockEntity(BlockPos pos, BlockState state) {
		super(EntityRegistry.SMALL_PEDESTAL_ENTITY, pos, state);
	}

    public ItemStack getStoredItem() {
        return this.storedItem;
    }

    public void setStoredItem(ItemStack item) {
        this.storedItem = item;
        this.setChanged();
    }
    
    public ItemStack removeStoredItem() {
        ItemStack item = storedItem;
        this.storedItem = ItemStack.EMPTY;
        this.setChanged();
        return item;
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, storedItem).result().ifPresent(nbtElement -> tag.put("storedItem", nbtElement));
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.storedItem = ItemStack.CODEC.parse(NbtOps.INSTANCE, tag.get("storedItem")).result().orElse(ItemStack.EMPTY);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
