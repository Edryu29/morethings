package com.edryu.morethings.entity;

import com.edryu.morethings.block.SackBlock;
import com.edryu.morethings.registry.EntityRegistry;
import com.edryu.morethings.registry.SoundRegistry;
import com.edryu.morethings.util.SimpleInventory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
	

public class SackBlockEntity extends BlockEntity implements MenuProvider, SimpleInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(9, ItemStack.EMPTY);
    
    public SackBlockEntity(BlockPos pos, BlockState state) {
        super(EntityRegistry.SACK_ENTITY, pos, state);
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
        ContainerHelper.saveAllItems(tag, this.inventory, registries);
        super.saveAdditional(tag, registries);
    }
    
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new DispenserMenu(i, inventory, this);
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable(getBlockState().getBlock().getDescriptionId());
    }

   public void playSound() {
        double d = (double)this.worldPosition.getX() + 0.5;
        double e = (double)this.worldPosition.getY() + 1;
        double f = (double)this.worldPosition.getZ() + 0.5;
        this.level.playSound(null, d, e, f, SoundRegistry.SACK_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
   }

   public void startOpen(Player player) {
        this.playSound();
        this.level.setBlock(this.getBlockPos(), (BlockState)this.getBlockState().setValue(SackBlock.OPEN, true), 3);
   }

   public void stopOpen(Player player) {
        this.playSound();
        this.level.setBlock(this.getBlockPos(), (BlockState)this.getBlockState().setValue(SackBlock.OPEN, false), 3);
   }

}