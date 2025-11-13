package com.edryu.morethings.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edryu.morethings.block.PulleyBlock;
import com.edryu.morethings.block.RopeBlock;
import com.edryu.morethings.registry.EntityRegistry;
import com.edryu.morethings.registry.ItemRegistry;
import com.edryu.morethings.registry.SoundRegistry;
import com.edryu.morethings.screen.PulleyScreenHandler;
import com.edryu.morethings.util.BlockProperties.Winding;
import com.edryu.morethings.util.SimpleInventory;

import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.data.client.VariantSettings.Rotation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.item.ItemPlacementContext;
	

public class PulleyBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, SimpleInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    
	public static final String MOD_ID = "morethings";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
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
        this.onContentChanged();
	}

    public void onContentChanged() {
        Winding type = getContentType(this.getItems().getFirst().getItem());
        BlockState state = this.getCachedState();
        if (state.get(PulleyBlock.WINDING) != type) this.world.setBlockState(this.pos, state.with(PulleyBlock.WINDING, type), 3);
    }

    public boolean pullWinding(boolean retract) {
        return retract ? this.pullRope() : this.releaseRope();
    }

    public boolean pullRope() {
        ItemStack stack = this.getStack(0);
        if (stack.getCount() < 1 || !(stack.getItem() instanceof BlockItem bi)) return false;
        Block windingBlock = bi.getBlock();
		World world = this.getWorld();
		BlockState blockState;

        if (!(world.getBlockState(pos.down()).isOf(windingBlock))) return false;
        BlockPos.Mutable reelingPos = pos.mutableCopy().move(Direction.DOWN);
        while (reelingPos.getY() >= world.getBottomY()) {
            blockState  = world.getBlockState(reelingPos);
            if (blockState.isOf(windingBlock)) {
                reelingPos.move(Direction.DOWN);
            } else {
                reelingPos.move(Direction.UP);
                world.breakBlock(reelingPos, false, null);
                return true;
            }
        }
        
        return false;
    }

    public boolean releaseRope() {
        ItemStack stack = this.getStack(0);
        if (stack.getCount() < 1 || !(stack.getItem() instanceof BlockItem bi)) return false;
        Block windingBlock = bi.getBlock();
		World world = this.getWorld();
		BlockState blockState;

        Direction direction = Direction.DOWN;
        BlockPos.Mutable mutablePos = this.getPos().mutableCopy().move(direction);
        for (int i = 0; i < 256; i++) {
            blockState = world.getBlockState(mutablePos);


            if (!blockState.isOf(windingBlock)) {
                FluidState fluid = blockState.getFluidState();
                if (!fluid.isOf(Fluids.WATER) && !fluid.isEmpty()) return false;

                if (blockState.isReplaceable()){
                    world.setBlockState(mutablePos, Blocks.CHAIN.getDefaultState());
                }
                break;
            }
            mutablePos.move(direction);
        }
        
        return false;
    }

    public static Winding getContentType(Item item) {
        if (item instanceof BlockItem bi && bi.getBlock() instanceof ChainBlock) return Winding.CHAIN;
        if (item instanceof BlockItem bi && bi.getBlock() instanceof RopeBlock) return Winding.ROPE;
        return Winding.NONE;
    }
}