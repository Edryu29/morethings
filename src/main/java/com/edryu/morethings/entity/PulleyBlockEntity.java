package com.edryu.morethings.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import com.edryu.morethings.block.PulleyBlock;
import com.edryu.morethings.registry.EntityRegistry;
import com.edryu.morethings.screen.PulleyScreenHandler;
import com.edryu.morethings.util.BlockProperties.Winding;
import com.edryu.morethings.util.SimpleInventory;
import com.edryu.morethings.util.WindingHelper;
	
public class PulleyBlockEntity extends BlockEntity implements MenuProvider, SimpleInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    
    public PulleyBlockEntity(BlockPos pos, BlockState state) {
        super(EntityRegistry.PULLEY_ENTITY, pos, state);
    }
    
    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }
    
    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);
        ContainerHelper.loadAllItems(nbt, this.inventory, registryLookup);
    }
    
    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);
        ContainerHelper.saveAllItems(nbt, this.inventory, registryLookup);
    }
    
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new PulleyScreenHandler(syncId, playerInventory, this);
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable(getBlockState().getBlock().getDescriptionId());
    }

	public void playSound(SoundEvent soundEvent) {
		double d = this.worldPosition.getX() + 0.5;
		double e = this.worldPosition.getY() + 0.5;
		double f = this.worldPosition.getZ() + 0.5;
		this.level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
	}

    public void startOpen(Player player) {
        this.playSound(SoundEvents.BARREL_OPEN);
    }

    public void stopOpen(Player player) {
        this.playSound(SoundEvents.BARREL_CLOSE);
    }

	public void setChanged() {
		super.setChanged();
        this.updateDisplayedItem();
	}

    public void updateDisplayedItem() {
        BlockState state = this.getBlockState();
        Winding windingType = WindingHelper.getWindingType(this.getItem(0).getItem());
        level.setBlock(worldPosition, state.setValue(PulleyBlock.WINDING, windingType).cycle(PulleyBlock.FLIPPED), Block.UPDATE_CLIENTS);

    }

    public boolean operateDirectly(boolean retracting) {
        return retracting ? this.pullWindingUp() : this.releaseWindingDown();
    }

    public boolean pullWindingUp() {
        return pullWinding(Direction.DOWN, Integer.MAX_VALUE, true);
    }

    public boolean pullWinding(Direction moveDir, int maxDist, boolean addItem) {
        ItemStack stack = this.getItem(0);
        boolean addNewItem = false;
        if (stack.isEmpty()) {
            Item i = level.getBlockState(worldPosition.below()).getBlock().asItem();
            if (WindingHelper.getWindingType(i) == Winding.NONE) return false;
            stack = new ItemStack(i);
            addNewItem = true;
        }
        if (stack.getCount() + 1 > stack.getMaxStackSize() || !(stack.getItem() instanceof BlockItem)) return false;
        Block windingBlock = ((BlockItem) stack.getItem()).getBlock();
        boolean success = WindingHelper.removeWinding(worldPosition.relative(moveDir), level, windingBlock, moveDir, maxDist);
        if (success) {
            SoundType soundGroup = windingBlock.this.defaultBlockState().getSoundType();
            level.playSound(null, worldPosition, soundGroup.getBreakSound(), SoundSource.BLOCKS, (soundGroup.getVolume() + 1.0F) / 2.0F, soundGroup.getPitch() * 0.8F);
            if (addNewItem) this.setItem(0, stack);
            else if (addItem) stack.grow(1);
            this.setChanged();
        }
        return success;
    }

    public boolean releaseWindingDown() {
        return releaseWinding(Direction.DOWN, Integer.MAX_VALUE, true);
    }

    public boolean releaseWinding(Direction dir, int maxDist, boolean removeItem) {
        ItemStack stack = this.getItem(0);
        if (stack.getCount() < 1 || !(stack.getItem() instanceof BlockItem bi)) return false;
        Block windingBlock = bi.getBlock();

        boolean success = WindingHelper.addWinding(worldPosition.relative(dir), level, null, InteractionHand.MAIN_HAND, windingBlock, dir, maxDist);
        if (success) {
            SoundType soundGroup = windingBlock.this.defaultBlockState().getSoundType();
            level.playSound(null, worldPosition, soundGroup.getPlaceSound(), SoundSource.BLOCKS, (soundGroup.getVolume() + 1.0F) / 2.0F, soundGroup.getPitch() * 0.8F);
            if(removeItem) {
                stack.shrink(1);
                this.setChanged();
            }
        }
        return success;
    }

    public boolean operateIndirect(Player player, InteractionHand hand, Block windingBlock, Direction moveDir, boolean retracting) {
        ItemStack stack = getItem(0);
        if (stack.isEmpty()) {
            if (retracting) {
                return false;
            } else {
                this.setItem(0, new ItemStack(windingBlock));
                this.setChanged();
                return true;
            }
        }
        if (!stack.is(windingBlock.asItem())) return false;
        Direction.Axis axis = this.getBlockState().getValue(PulleyBlock.AXIS);
        if (axis == moveDir.getAxis()) return false;

        Direction[] order = moveDir.getAxis().isHorizontal() ? new Direction[]{Direction.DOWN} :
                new Direction[]{moveDir, moveDir.getClockWise(axis), moveDir.getCounterClockWise(axis)};

        List<Direction> remaining = new ArrayList<>();
        int maxSideDist = 7;
        for (var d : order) {
            if (WindingHelper.isCorrectWinding(windingBlock, level.getBlockState(worldPosition.relative(d)), d)) {
                if (moveConnected(retracting, maxSideDist, d)) return true;
                return false;
            } else remaining.add(d);
        }
        for (var d : remaining) {
            if (moveConnected(retracting, maxSideDist, d)) return true;
        }
        if (retracting) {
            stack.shrink(1);
            this.setChanged();
            return true;
        }
        return false;
    }

    private boolean moveConnected(boolean retracting, int maxSideDist, Direction d) {
        int dist = d == Direction.DOWN ? Integer.MAX_VALUE : maxSideDist;
        boolean result = retracting ? pullWinding(d, dist, false) : releaseWinding(d, dist, false);
        if (result && !retracting) level.setBlock(worldPosition, this.getBlockState().cycle(PulleyBlock.FLIPPED), Block.UPDATE_CLIENTS);
        return result;
    }
}