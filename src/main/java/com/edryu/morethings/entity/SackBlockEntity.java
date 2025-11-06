package com.edryu.morethings.entity;

import com.edryu.morethings.MoreThingsRegister;
import com.edryu.morethings.MoreThingsSounds;
import com.edryu.morethings.block.SackBlock;
import com.edryu.morethings.util.SimpleInventory;
import com.edryu.morethings.util.SimpleScreenHandler;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
	

public class SackBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, SimpleInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    
    public SackBlockEntity(BlockPos pos, BlockState state) {
        super(MoreThingsRegister.SACK_BLOCK_ENTITY, pos, state);
    }
    
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    
    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, this.inventory, registryLookup);
    }
    
    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, this.inventory, registryLookup);
        super.writeNbt(nbt, registryLookup);
    }
    
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SimpleScreenHandler(syncId, playerInventory, this);
    }
    
    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

   void playSound() {
        double d = (double)this.pos.getX() + 0.5;
        double e = (double)this.pos.getY() + 1;
        double f = (double)this.pos.getZ() + 0.5;
        this.world.playSound((PlayerEntity)null, d, e, f, MoreThingsSounds.SACK_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
   }

   public void onOpen(PlayerEntity player) {
        this.playSound();
        this.world.setBlockState(this.getPos(), (BlockState)this.getCachedState().with(SackBlock.OPEN, true), 3);
   }

   public void onClose(PlayerEntity player) {
        this.playSound();
        this.world.setBlockState(this.getPos(), (BlockState)this.getCachedState().with(SackBlock.OPEN, false), 3);
   }

}