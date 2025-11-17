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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
	

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
        this.setChanged();
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.heldBlock != null) BlockState.CODEC.encodeStart(NbtOps.INSTANCE, heldBlock).result().ifPresent(nbtElement -> tag.put("heldBlock", nbtElement));
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("heldBlock")) BlockState.CODEC.parse(NbtOps.INSTANCE, tag.get("heldBlock")).result().ifPresent(blockState -> this.heldBlock = blockState);
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
