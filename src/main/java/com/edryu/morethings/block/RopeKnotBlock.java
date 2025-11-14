package com.edryu.morethings.block;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.entity.RopeKnotBlockEntity;
import com.edryu.morethings.registry.BlockRegistry;
import com.edryu.morethings.registry.SoundRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class RopeKnotBlock extends WaterloggableBlock implements BlockEntityProvider {
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty EAST = BooleanProperty.of("east");

    private static final VoxelShape ROPE_KNOT = Block.createCuboidShape(6, 6, 6, 10, 10, 10);
    private static final VoxelShape ROPE_NORTH = Block.createCuboidShape(6, 6, 0, 10, 10, 9);
    private static final VoxelShape ROPE_SOUTH = Block.createCuboidShape(6, 6, 9, 10, 10, 16);
    private static final VoxelShape ROPE_WEST = Block.createCuboidShape(0, 6, 6, 9, 10, 10);
    private static final VoxelShape ROPE_EAST = Block.createCuboidShape(9, 6, 6, 16, 10, 10);
    private static final VoxelShape POST = Block.createCuboidShape(5, 7, 5, 11, 15, 11);
    private static final VoxelShape SHAPE = VoxelShapes.union(POST, ROPE_KNOT);

    public RopeKnotBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WATERLOGGED, false)
            .with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, NORTH, SOUTH, WEST, EAST);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RopeKnotBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union( SHAPE,
            state.get(NORTH) ? ROPE_NORTH : VoxelShapes.empty(),
            state.get(SOUTH) ? ROPE_SOUTH : VoxelShapes.empty(),
            state.get(EAST) ? ROPE_EAST : VoxelShapes.empty(),
            state.get(WEST) ? ROPE_WEST : VoxelShapes.empty()
        );
    }

    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = this.getDefaultState();
        for (Direction dir : Direction.Type.HORIZONTAL) {
            state = state.with(getDirState(dir), canConnectTo(world.getBlockState(pos.offset(dir)), dir));
        }
        return state;
    }

    @Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis().isHorizontal()) {
            boolean connected = canConnectTo(neighborState, direction);
            state = state.with(getDirectionProperty(direction), connected);
        }

        if (world.getBlockEntity(pos) instanceof RopeKnotBlockEntity be) {
            BlockState held = be.getHeldBlock();
            if (held != null) {
                BlockState updated = held.getStateForNeighborUpdate(direction, neighborState, world, pos, neighborPos);
                be.setHeldBlock(updated);
            }
        }
        return state;
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.getItem() instanceof ShearsItem || Screen.hasShiftDown()) {
            if (!world.isClient() && world.getBlockEntity(pos) instanceof RopeKnotBlockEntity be) {
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BlockRegistry.ROPE));
                world.spawnEntity(itemEntity);
                BlockState held = be.getHeldBlock();
                if (held != null && !held.isAir()) {
                    world.setBlockState(pos, held, Block.NOTIFY_ALL_AND_REDRAW);
                } else {
                    world.removeBlock(pos, false);
                }
                world.playSound(player, pos, SoundRegistry.ROPE_BREAK, SoundCategory.BLOCKS, 0.5F, 0.8F);

                if (stack.getItem() instanceof ShearsItem) {
                    stack.damage(1, player, null);
                }
                updateNeighbors(world, pos);
            }
            return world.isClient() ? ItemActionResult.CONSUME : ItemActionResult.SUCCESS;
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private boolean canConnectTo(BlockState neighbor, Direction dirTowardNeighbor) {
        if (neighbor.getBlock() instanceof RopeBlock) {
            Direction needOnNeighbor = dirTowardNeighbor.getOpposite();
            return neighbor.get(getRopeProperty(needOnNeighbor));
        }
        return false;
    }

    private void updateNeighbors(World world, BlockPos pos) {
        world.updateNeighbors(pos, this);
        for (Direction d : Direction.Type.HORIZONTAL) {
            BlockPos n = pos.offset(d);
            world.updateNeighbors(n, world.getBlockState(n).getBlock());
        }
    }


    private BooleanProperty getDirState(Direction direction) {
        return switch (direction) {
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            default -> NORTH;
        };
    }

    private static BooleanProperty getRopeProperty(Direction dir) {
        return switch (dir) {
            case NORTH -> RopeBlock.NORTH;
            case SOUTH -> RopeBlock.SOUTH;
            case EAST  -> RopeBlock.EAST;
            case WEST  -> RopeBlock.WEST;
            default -> throw new IllegalArgumentException();
        };
    }

    private BooleanProperty getDirectionProperty(Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST  -> EAST;
            case WEST  -> WEST;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
	}

    // @Override
    // public @NotNull List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
    //     List<ItemStack> drops = super.getDrops(state, builder);
    //     if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof RopeKnotBlockEntity be) {
    //         BlockState held = be.getHeldBlock();
    //         if (held != null && !held.isAir()) {
    //             drops.add(new ItemStack(held.getBlock().asItem()));
    //         }
    //     }
    //     return drops;
    // }

    // @Override
    // public boolean useShapeForLightOcclusion(BlockState state) {
    //     return true;
    // }

    // @Override
    // public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved) {
    //     if (!level.isClientSide && !state.is(oldState.getBlock())) {
    //         updateNeighbors(level, pos);
    //     }
    //     super.onPlace(state, level, pos, oldState, moved);
    // }

    // @Override
    // public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
    //     if (!level.isClientSide && !state.is(newState.getBlock())) {
    //         updateNeighbors(level, pos);
    //     }
    //     super.onRemove(state, level, pos, newState, moved);
    // }
}