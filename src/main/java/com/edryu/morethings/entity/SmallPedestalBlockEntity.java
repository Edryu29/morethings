package com.edryu.morethings.entity;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.registry.EntityRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
	

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
        this.markDirty();
    }
    
    public ItemStack removeStoredItem() {
        ItemStack item = storedItem;
        this.storedItem = ItemStack.EMPTY;
        this.markDirty();
        return item;
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, storedItem).result().ifPresent(nbtElement -> nbt.put("storedItem", nbtElement));
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.storedItem = ItemStack.CODEC.parse(NbtOps.INSTANCE, nbt.get("storedItem")).result().orElse(ItemStack.EMPTY);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
