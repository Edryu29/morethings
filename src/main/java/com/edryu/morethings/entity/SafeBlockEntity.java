package com.edryu.morethings.entity;

import com.edryu.morethings.block.SafeBlock;
import com.edryu.morethings.registry.EntityRegistry;
import com.edryu.morethings.util.SimpleInventory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
	

public class SafeBlockEntity extends BlockEntity implements MenuProvider, SimpleInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(27, ItemStack.EMPTY);
    
    public SafeBlockEntity(BlockPos pos, BlockState state) {
        super(EntityRegistry.SAFE_ENTITY, pos, state);
    }
    
    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }
    
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, this.inventory, registries);
    }
    
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.inventory, registries);
    }
    
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return ChestMenu.threeRows(i, inventory, this);
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable(getBlockState().getBlock().getDescriptionId());
    }

   void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = ((Direction)state.getValue(SafeBlock.FACING)).getNormal();
        double d = this.worldPosition.getX() + 0.5 + vec3i.getX() / 2.0;
        double e = this.worldPosition.getY() + 0.5 + vec3i.getY() / 2.0;
        double f = this.worldPosition.getZ() + 0.5 + vec3i.getZ() / 2.0;
        this.level.playSound(null, d, e, f, SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
   }
   
   public void startOpen(Player player) {
        this.playSound(this.getBlockState(), SoundEvents.IRON_DOOR_OPEN);
        this.level.setBlock(this.getBlockPos(), (BlockState)this.getBlockState().setValue(SafeBlock.OPEN, true), 3);
   }

   public void stopOpen(Player player) {
        this.playSound(this.getBlockState(), SoundEvents.IRON_DOOR_CLOSE);
        this.level.setBlock(this.getBlockPos(), (BlockState)this.getBlockState().setValue(SafeBlock.OPEN, false), 3);
   }
}