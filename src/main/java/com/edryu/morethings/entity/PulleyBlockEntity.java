package com.edryu.morethings.entity;

import java.util.ArrayList;
import java.util.List;

import com.edryu.morethings.block.PulleyBlock;
import com.edryu.morethings.registry.EntityRegistry;
import com.edryu.morethings.screen.PulleyScreenHandler;
import com.edryu.morethings.util.BlockProperties.Winding;
import com.edryu.morethings.util.SimpleInventory;
import com.edryu.morethings.util.WindingHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.sound.BlockSoundGroup;
	
public class PulleyBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, SimpleInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    
    public PulleyBlockEntity(BlockPos pos, BlockState state) {
        super(EntityRegistry.PULLEY_ENTITY, pos, state);
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
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.inventory, registryLookup);
    }
    
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PulleyScreenHandler(syncId, playerInventory, this);
    }
    
    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

	public void playSound(SoundEvent soundEvent) {
		double d = this.pos.getX() + 0.5;
		double e = this.pos.getY() + 0.5;
		double f = this.pos.getZ() + 0.5;
		this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
	}

    public void onOpen(PlayerEntity player) {
        this.playSound(SoundEvents.BLOCK_BARREL_OPEN);
    }

    public void onClose(PlayerEntity player) {
        this.playSound(SoundEvents.BLOCK_BARREL_CLOSE);
    }

	public void markDirty() {
		super.markDirty();
        this.updateDisplayedItem();
	}

    public void updateDisplayedItem() {
        BlockState state = this.getCachedState();
        Winding type = WindingHelper.getWindingType(this.getItems().getFirst().getItem());
        if (state.get(PulleyBlock.WINDING) != type) this.world.setBlockState(this.pos, state.with(PulleyBlock.WINDING, type), Block.NOTIFY_LISTENERS);
    }

    public boolean operateDirectly(boolean retract) {
        return retract ? this.pullWindingUp() : this.releaseWindingDown();
    }

    public boolean pullWindingUp() {
        return pullWinding(Direction.DOWN, Integer.MAX_VALUE, true);
    }

    public boolean pullWinding(Direction moveDir, int maxDist, boolean addItem) {
        ItemStack stack = this.getStack(0);
        boolean addNewItem = false;
        if (stack.isEmpty()) {
            Item i = world.getBlockState(pos.down()).getBlock().asItem();
            if (WindingHelper.getWindingType(i) == Winding.NONE) return false;
            stack = new ItemStack(i);
            addNewItem = true;
        }
        if (stack.getCount() + 1 > stack.getMaxCount() || !(stack.getItem() instanceof BlockItem)) return false;
        Block windingBlock = ((BlockItem) stack.getItem()).getBlock();
        boolean success = WindingHelper.removeWinding(pos.offset(moveDir), world, windingBlock, moveDir, maxDist);
        if (success) {
            BlockSoundGroup soundGroup = windingBlock.getDefaultState().getSoundGroup();
            world.playSound(null, pos, soundGroup.getBreakSound(), SoundCategory.BLOCKS, (soundGroup.getVolume() + 1.0F) / 2.0F, soundGroup.getPitch() * 0.8F);
            if (addNewItem) this.setStack(0, stack);
            else if (addItem) stack.increment(1);
            this.markDirty();
        }
        return success;
    }

    public boolean releaseWindingDown() {
        return releaseWinding(Direction.DOWN, Integer.MAX_VALUE, true);
    }

    public boolean releaseWinding(Direction dir, int maxDist, boolean removeItem) {
        ItemStack stack = this.getStack(0);
        if (stack.getCount() < 1 || !(stack.getItem() instanceof BlockItem bi)) return false;
        Block windingBlock = bi.getBlock();

        boolean success = WindingHelper.addWinding(pos.offset(dir), world, null, Hand.MAIN_HAND, windingBlock, dir, maxDist);
        if (success) {
            BlockSoundGroup soundGroup = windingBlock.getDefaultState().getSoundGroup();
            world.playSound(null, pos, soundGroup.getPlaceSound(), SoundCategory.BLOCKS, (soundGroup.getVolume() + 1.0F) / 2.0F, soundGroup.getPitch() * 0.8F);
            if(removeItem) {
                stack.decrement(1);
                this.markDirty();
            }
        }
        return success;
    }

    public boolean operateIndirect(PlayerEntity player, Hand hand, Block windingBlock, Direction moveDir, boolean retracting) {
        ItemStack stack = getStack(0);
        if (stack.isEmpty()) {
            if (retracting) {
                return false;
            } else {
                this.setStack(0, new ItemStack(windingBlock));
                this.markDirty();
                return true;
            }
        }

        if (!stack.isOf(windingBlock.asItem())) return false;
        BlockState state = getCachedState();
        Direction.Axis axis = state.get(PulleyBlock.AXIS);
        if (axis == moveDir.getAxis()) return false;

        world.setBlockState(pos, state.cycle(PulleyBlock.FLIPPED));

        Direction[] order = moveDir.getAxis().isHorizontal() ? new Direction[]{Direction.DOWN} :
                new Direction[]{moveDir, moveDir.rotateClockwise(axis), moveDir.rotateCounterclockwise(axis)};

        List<Direction> remaining = new ArrayList<>();
        int maxSideDist = 7;
        for (var d : order) {
            if (WindingHelper.isCorrectWinding(windingBlock, world.getBlockState(pos.offset(d)), d)) {
                if (moveConnected(retracting, maxSideDist, d)) return true;
                return false;
            } else remaining.add(d);
        }
        for (var d : remaining) {
            if (moveConnected(retracting, maxSideDist, d)) return true;
        }
        if (retracting) {
            stack.decrement(1);
            this.markDirty();
            return true;
        }
        return false;
    }

    private boolean moveConnected(boolean retracting, int maxSideDist, Direction d) {
        int dist = d == Direction.DOWN ? Integer.MAX_VALUE : maxSideDist;
        return retracting ? pullWinding(d, dist, false) : releaseWinding(d, dist, false);
    }
}