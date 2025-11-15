package com.edryu.morethings.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edryu.morethings.block.PulleyBlock;
import com.edryu.morethings.block.RopeBlock;
import com.edryu.morethings.registry.EntityRegistry;
import com.edryu.morethings.registry.SoundRegistry;
import com.edryu.morethings.screen.PulleyScreenHandler;
import com.edryu.morethings.util.BlockProperties.Winding;
import com.edryu.morethings.util.SimpleInventory;
import com.edryu.morethings.client.datagen.BlockTagProvider;

import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.SideShapeType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
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
        BlockState state = this.getCachedState();
        Winding type = getContentType(this.getItems().getFirst().getItem());
        if (state.get(PulleyBlock.WINDING) != type) this.world.setBlockState(this.pos, state.with(PulleyBlock.WINDING, type), Block.NOTIFY_LISTENERS);
    }

    public boolean pullWinding(boolean retract) {
        return retract ? this.pullRope(Direction.UP) : this.releaseRope(Direction.DOWN);
    }

    public boolean pullRope(Direction dir) {
        ItemStack stack = this.getStack(0);
        int stackCount = stack.getCount();
        if (stackCount < 1 || !(stack.getItem() instanceof BlockItem bi) || stackCount >= stack.getItem().getMaxCount()) return false;

        Block windingBlock = bi.getBlock();
		World world = this.getWorld();
		BlockState blockState;
        if (!(world.getBlockState(pos.down()).isOf(windingBlock))) return false; // Block below pulley is not a chain/rope, so nothing to pull

        BlockPos.Mutable windingPos = pos.mutableCopy().move(dir.getOpposite()); // Go opposite the pulling direction
        while (windingPos.getY() >= world.getBottomY()) {
            blockState = world.getBlockState(windingPos);

            if (!blockState.isOf(windingBlock)) {  // Block is not a chain/rope

                if (!(blockState.isReplaceable()) && this.isMovable(blockState, world, windingPos, dir)){ // Block can be pulled
                    world.removeBlock(windingPos, false); // Remove block from old position
                    windingPos.move(dir); // Move towards pulling direction
                    world.setBlockState(windingPos, blockState); // Put block at new position replacing chain/rope
                } else {
                    windingPos.move(dir);// Move towards pulling direction
                    world.removeBlock(windingPos, false); // Remove chain/rope adove block
                }

                BlockSoundGroup soundGroup = windingBlock.getDefaultState().getSoundGroup();
                world.playSound(null, pos, soundGroup.getBreakSound(), SoundCategory.BLOCKS, (soundGroup.getVolume() + 1.0F) / 2.0F, soundGroup.getPitch() * 0.8F);
                world.setBlockState(this.pos, this.getCachedState().cycle(PulleyBlock.FLIPPED), Block.NOTIFY_LISTENERS);
                stack.increment(1);
                return true;
            }
            windingPos.move(dir.getOpposite()); // Block is chain/rope, continue opposite the pulling direction
        }
        return false;
    }

    public boolean releaseRope(Direction dir) {
        ItemStack stack = this.getStack(0);
        int stackCount = stack.getCount();
        if (stackCount < 1 || !(stack.getItem() instanceof BlockItem bi)) return false;

        Block windingBlock = bi.getBlock();
		World world = this.getWorld();
		BlockState blockState;

        BlockPos.Mutable windingPos = this.getPos().mutableCopy().move(dir); // Go towards the release direction
        while (windingPos.getY() > world.getBottomY()) {
            blockState = world.getBlockState(windingPos);

            if (!blockState.isOf(windingBlock)) {  // Block is not a chain/rope
                
                FluidState fluid = blockState.getFluidState();
                if (!fluid.isOf(Fluids.WATER) && !fluid.isEmpty()) return false; // Block is a fluid that is not water

                if (blockState.isReplaceable()) return this.placeWindingBlock(windingPos, stack, dir);

                if (this.isMovable(blockState, world, windingPos, dir.getOpposite())){ // Block is not replaceable and can be pushed 
                    BlockState belowMovingState = world.getBlockState(windingPos.offset(dir)); // Get block after pushable block
                    if (!belowMovingState.isReplaceable()) return false;
                    if (!belowMovingState.getFluidState().isOf(Fluids.WATER) && !fluid.isEmpty()) return false;

                    world.removeBlock(windingPos, false); // Remove pushable block from old position
                    world.setBlockState(windingPos.offset(dir), blockState); // Replace block after pushable block with pushable block
                    return this.placeWindingBlock(windingPos, stack, dir); // Place winding block in old position
                }
                break;
            }
            windingPos.move(dir); // Block is chain/rope, continue towards the release direction
        }
        return false;
    }

    public boolean placeWindingBlock(BlockPos pos, ItemStack stack, Direction dir){
        if (!(stack.getItem() instanceof BlockItem bi)) return false;
        BlockHitResult hitResult = new BlockHitResult(Vec3d.ofCenter(pos), dir.getOpposite(), pos, false);
        ItemPlacementContext context = new ItemPlacementContext(world, null, null, stack, hitResult);
        world.setBlockState(this.pos, this.getCachedState().cycle(PulleyBlock.FLIPPED), Block.NOTIFY_LISTENERS);
        bi.place(context);
        return true;
    }

    public boolean isMovable(BlockState state, World world, BlockPos pos, Direction direction) {
        if (state.isAir()) return false;
        if (state.getBlock() instanceof PulleyBlock) return false;
        if ((state.isOf(Blocks.PISTON) || state.isOf(Blocks.STICKY_PISTON)) && state.get(Properties.EXTENDED)) return false;
        if (state.isOf(Blocks.OBSIDIAN) || state.isOf(Blocks.CRYING_OBSIDIAN) || state.isOf(Blocks.RESPAWN_ANCHOR) || state.isOf(Blocks.BEDROCK)) return false;

        if (pos.getY() < world.getBottomY() || pos.getY() > world.getTopY() - 1 || !world.getWorldBorder().contains(pos)) return false;
        if (direction == Direction.DOWN && pos.getY() == world.getBottomY()) return false;
        if (direction == Direction.UP && pos.getY() == world.getTopY() - 1) return false;

        if (state.isIn(BlockTagProvider.MOVEABLE_BY_PULLEY)) return true;
        if (state.isIn(BlockTagProvider.UNMOVEABLE_BY_PULLEY)) return false;
        if (state.isIn(BlockTagProvider.HANG_FROM_ROPES)) return true;
        
        if (state.hasBlockEntity()) return false;
        return state.isSideSolid(world, pos, direction, SideShapeType.CENTER); // Check the side from where is the chain/rope
    }

    public static Winding getContentType(Item item) {
        if (item instanceof BlockItem bi && bi.getBlock() instanceof ChainBlock) return Winding.CHAIN;
        if (item instanceof BlockItem bi && bi.getBlock() instanceof RopeBlock) return Winding.ROPE;
        return Winding.NONE;
    }
}