package com.edryu.morethings.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edryu.morethings.block.PulleyBlock;
import com.edryu.morethings.block.RopeBlock;
import com.edryu.morethings.registry.EntityRegistry;
import com.edryu.morethings.screen.PulleyScreenHandler;
import com.edryu.morethings.util.BlockProperties.Winding;
import com.edryu.morethings.util.SimpleInventory;
import com.edryu.morethings.client.datagen.BlockTagProvider;

import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.SideShapeType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
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
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.sound.BlockSoundGroup;
	
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
        if (state.get(PulleyBlock.WINDING) != type) this.world.setBlockState(this.pos, state.with(PulleyBlock.WINDING, type), Block.NOTIFY_LISTENERS);
    }

    public boolean pullWinding(boolean retract) {
        return retract ? this.pullRope() : this.releaseRope();
    }

    public boolean pullRope() {
        ItemStack stack = this.getStack(0);
        int stackCount = stack.getCount();
        if (stackCount < 1 || !(stack.getItem() instanceof BlockItem bi) || stackCount >= stack.getItem().getMaxCount()) return false;

        Block windingBlock = bi.getBlock();
		World world = this.getWorld();
		BlockState blockState;
        if (!(world.getBlockState(pos.down()).isOf(windingBlock))) return false; // Block below pulley is not a chain/rope, so nothing to pull

        BlockPos.Mutable windingPos = pos.mutableCopy().move(Direction.DOWN);
        while (windingPos.getY() >= world.getBottomY()) {
            blockState = world.getBlockState(windingPos);
            if (blockState.isOf(windingBlock)) { // Block is chain/rope, go down
                windingPos.move(Direction.DOWN);
            } else {
                if (!(blockState.isReplaceable()) && this.isPushable(blockState, world, windingPos, Direction.UP)){ // Block can be pushed
                    world.removeBlock(windingPos, false); // Remove block from old position
                    windingPos.move(Direction.UP);
                    world.setBlockState(windingPos, blockState); // Put block at new position replacing chain/rope
                } else {
                    windingPos.move(Direction.UP);
                    world.removeBlock(windingPos, false); // Remove chain/rope adove block
                }
                BlockSoundGroup soundGroup = windingBlock.getDefaultState().getSoundGroup();
                world.playSound(null, pos, soundGroup.getBreakSound(), SoundCategory.BLOCKS, (soundGroup.getVolume() + 1.0F) / 2.0F, soundGroup.getPitch() * 0.8F);
                world.setBlockState(this.pos, this.getCachedState().cycle(PulleyBlock.FLIPPED), Block.NOTIFY_LISTENERS);
                stack.setCount(stackCount + 1);
                return true;
            }
        }
        return false;
    }

    public boolean releaseRope() {
        ItemStack stack = this.getStack(0);
        int stackCount = stack.getCount();
        if (stackCount < 1 || !(stack.getItem() instanceof BlockItem bi)) return false;

        Block windingBlock = bi.getBlock();
		World world = this.getWorld();
		BlockState blockState;

        BlockPos.Mutable windingPos = this.getPos().mutableCopy().move(Direction.DOWN);
        while (windingPos.getY() >= world.getBottomY()) {
            blockState = world.getBlockState(windingPos);
            if (!blockState.isOf(windingBlock)) {  // Block is not a chain/rope
                FluidState fluid = blockState.getFluidState();
                if (!fluid.isOf(Fluids.WATER) && !fluid.isEmpty()) return false; // Block is a fluid that is not water

                if (blockState.isReplaceable()){
                    this.placeWindingBlock(windingBlock.getDefaultState(), windingPos);
                    stack.setCount(stackCount - 1);
                    return true;
                } else if (this.isPushable(blockState, world, windingPos, Direction.DOWN)){ // Block is not replaceable and can be pushed 
                    BlockState belowMovingState = world.getBlockState(windingPos.down()); // Check block below pushable block
                    if (!belowMovingState.isReplaceable()) return false;
                    if (!belowMovingState.getFluidState().isOf(Fluids.WATER) && !fluid.isEmpty()) return false;
                    world.setBlockState(windingPos.down(), blockState); // Replace block below pushable block with pushable block
                    this.placeWindingBlock(windingBlock.getDefaultState(), windingPos); // Replace old pushable block with winding block
                    stack.setCount(stackCount - 1);
                    return true;
                }
                break;
            }
            windingPos.move(Direction.DOWN);
        }
        return false;
    }

    public void placeWindingBlock(BlockState state, BlockPos pos){
        BlockState windingState = Block.postProcessState(state, world, pos);
        world.setBlockState(pos, windingState);
        BlockSoundGroup soundGroup = state.getSoundGroup();
        world.playSound(null, pos, soundGroup.getPlaceSound(), SoundCategory.BLOCKS, (soundGroup.getVolume() + 1.0F) / 2.0F, soundGroup.getPitch() * 0.8F);
        world.setBlockState(this.pos, this.getCachedState().cycle(PulleyBlock.FLIPPED), Block.NOTIFY_LISTENERS);
    }

    public boolean isPushable(BlockState state, World world, BlockPos pos, Direction dir) {
        if (state.isAir()) return true;
        if (state.getBlock() instanceof PulleyBlock) return false;
        if (state.isOf(Blocks.OBSIDIAN) || state.isOf(Blocks.CRYING_OBSIDIAN) || state.isOf(Blocks.RESPAWN_ANCHOR) || state.isOf(Blocks.REINFORCED_DEEPSLATE)) return false;
        if (pos.getY() < world.getBottomY() || pos.getY() > world.getTopY() - 1 || !world.getWorldBorder().contains(pos)) return false;
        if (state.isIn(BlockTagProvider.MOVEABLE_BY_PULLEY)) return true;
        if (state.isIn(BlockTagProvider.UNMOVEABLE_BY_PULLEY)) return false;
        if (state.hasBlockEntity()) return false;
        if (state.isIn(BlockTagProvider.HANG_FROM_ROPES)) return true;
        return state.isSideSolid(world, pos, dir, SideShapeType.CENTER);
    }

    public static Winding getContentType(Item item) {
        if (item instanceof BlockItem bi && bi.getBlock() instanceof ChainBlock) return Winding.CHAIN;
        if (item instanceof BlockItem bi && bi.getBlock() instanceof RopeBlock) return Winding.ROPE;
        return Winding.NONE;
    }
}