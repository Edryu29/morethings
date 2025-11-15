package com.edryu.morethings.entity;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.registry.EntityRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
	

public class RopeKnotBlockEntity extends BlockEntity {
    private BlockState heldBlock;

	public RopeKnotBlockEntity(BlockPos pos, BlockState state) {
		super(EntityRegistry.ROPE_KNOT_ENTITY, pos, state);
	}

    public BlockState getHeldBlock() {
        return this.heldBlock;
    }

    public void setHeldBlock(BlockState state) {
        this.heldBlock = state;
        this.markDirty();
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (this.heldBlock != null) BlockState.CODEC.encodeStart(NbtOps.INSTANCE, heldBlock).result().ifPresent(nbtElement -> nbt.put("heldBlock", nbtElement));
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains("heldBlock")) BlockState.CODEC.parse(NbtOps.INSTANCE, nbt.get("heldBlock")).result().ifPresent(blockState -> this.heldBlock = blockState);
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
