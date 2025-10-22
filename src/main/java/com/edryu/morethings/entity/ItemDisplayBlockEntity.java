package com.edryu.morethings.entity;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.MoreThingsRegister;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
	

public class ItemDisplayBlockEntity extends BlockEntity implements ItemDisplayInventory {

	public ItemDisplayBlockEntity(BlockPos pos, BlockState state) {
		super(MoreThingsRegister.ITEM_DISPLAY_BLOCK_ENTITY, pos, state);
	}

    private ItemStack storedItem = ItemStack.EMPTY;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    int tickCount = 0;

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, storedItem)
                .result()
                .ifPresent(nbtElement -> {
                    nbt.put("StoredItem", nbtElement);
                });
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        // Load item from NBT
        storedItem = ItemStack.CODEC.parse(NbtOps.INSTANCE, nbt.get("StoredItem"))
                .result()
                .orElse(ItemStack.EMPTY);
        //System.out.println("Item loaded from NBT: " + (storedItem.isEmpty() ? "None" : storedItem.getItem().getName(storedItem)));  // Debugging line
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public boolean hasStoredItem() {
        return !storedItem.isEmpty();
    }

    public void setStoredItem(ItemStack item) {
        storedItem = item;
        markDirty();  // Important to update the state
    }

    public ItemStack removeStoredItem() {
        ItemStack item = storedItem;
        storedItem = ItemStack.EMPTY;
        markDirty();
        return item;
    }

    public ItemStack getStoredItem() {
        return storedItem;  // Ensure you're returning 'storedItem', not items[0] or other values
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

}
