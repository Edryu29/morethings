package com.edryu.morethings.screen;

import com.edryu.morethings.registry.ScreenRegistry;
import com.edryu.morethings.util.BlockProperties.Winding;
import com.edryu.morethings.util.PulleyHelper;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;


public class PulleyScreenHandler extends AbstractContainerMenu {
    private final Container inventory;
    
    public PulleyScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(1));
    }
    
    public PulleyScreenHandler(int syncId, Inventory playerInventory, Container inventory) {
        super(ScreenRegistry.PULLEY_SCREEN_HANDLER, syncId);
        checkContainerSize(inventory, 1);
        this.inventory = inventory;

        inventory.startOpen(playerInventory.player);
    
        int m;
        int l;
        
        this.addSlot(new Slot(inventory, 0, 79, 39) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return PulleyHelper.getWindingType(stack.getItem()) != Winding.NONE;
            }
        });

        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }
    
    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }
    
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (index < this.inventory.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }
    
            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
    
        return newStack;
    }

   public void removed(Player player) {
        super.removed(player);
        this.inventory.stopOpen(player);
    }
}