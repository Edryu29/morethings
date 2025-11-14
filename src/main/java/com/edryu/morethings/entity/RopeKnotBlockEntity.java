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
    private BlockState held;

	public RopeKnotBlockEntity(BlockPos pos, BlockState state) {
		super(EntityRegistry.ROPE_KNOT_ENTITY, pos, state);
	}

    public BlockState getHeldBlock() {
        return held;
    }

    public void setHeldBlock(BlockState state) {
        this.held = state;
        this.markDirty();
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        if (this.held != null) BlockState.CODEC.encodeStart(NbtOps.INSTANCE, held).result().ifPresent(nbtElement -> nbt.put("held", nbtElement));
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        if (nbt.contains("held")) BlockState.CODEC.parse(NbtOps.INSTANCE, nbt.get("held")).result().ifPresent(blockState -> this.held = blockState);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }
}
