package com.edryu.morethings.block;

import java.util.List;

import com.edryu.morethings.entity.RopeKnotBlockEntity;
import com.edryu.morethings.registry.BlockRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RopeKnotBlock extends WaterloggableBlock implements EntityBlock {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");

    private static final VoxelShape ROPE_KNOT = Block.box(5, 8, 5, 11, 14, 11);
    private static final VoxelShape ROPE_NORTH = Block.box(6, 9, 0, 10, 13, 9);
    private static final VoxelShape ROPE_SOUTH = Block.box(6, 9, 9, 10, 13, 16);
    private static final VoxelShape ROPE_WEST = Block.box(0, 9, 6, 9, 13, 10);
    private static final VoxelShape ROPE_EAST = Block.box(9, 9, 6, 16, 13, 10);
    private static final VoxelShape POST = Block.box(6, 0, 6, 10, 16, 10);
    private static final VoxelShape SHAPE = Shapes.or(POST, ROPE_KNOT);

    public RopeKnotBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false)
            .setValue(NORTH, false).setValue(SOUTH, false).setValue(WEST, false).setValue(EAST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, NORTH, SOUTH, WEST, EAST);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RopeKnotBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or( SHAPE,
            state.getValue(NORTH) ? ROPE_NORTH : Shapes.empty(),
            state.getValue(SOUTH) ? ROPE_SOUTH : Shapes.empty(),
            state.getValue(EAST) ? ROPE_EAST : Shapes.empty(),
            state.getValue(WEST) ? ROPE_WEST : Shapes.empty()
        );
    }

    @Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = this.defaultBlockState();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            state = state.setValue(getDirectionProperty(direction), canConnectTo(level.getBlockState(pos.relative(direction)), direction));
        }
        return state;
    }

    @Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction.getAxis().isHorizontal()) state = state.setValue(getDirectionProperty(direction), canConnectTo(neighborState, direction));
        if (level.getBlockEntity(pos) instanceof RopeKnotBlockEntity ropeKnotBE) {
            BlockState heldBlock = ropeKnotBE.getHeldBlock();
            if (heldBlock != null) ropeKnotBE.setHeldBlock(heldBlock.updateShape(direction, neighborState, level, pos, neighborPos));
        }
        return state;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.getItem() instanceof ShearsItem) {
            if (level.getBlockEntity(pos) instanceof RopeKnotBlockEntity ropeKnotBE) {
                ItemEntity storedItemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BlockRegistry.ROPE));
                BlockState heldBlock = ropeKnotBE.getHeldBlock();

                if (heldBlock != null && !heldBlock.isAir()) level.setBlock(pos, heldBlock, Block.UPDATE_ALL_IMMEDIATE);
                else level.removeBlock(pos, false);

                level.addFreshEntity(storedItemEntity);
                level.playSound(null, pos, SoundEvents.SNOW_GOLEM_SHEAR, SoundSource.BLOCKS, 0.8F, 1.3F);
                stack.hurtAndBreak(1, player, null);

                updateNeighbors(level, pos);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = super.getDrops(state, params);
        if (params.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof RopeKnotBlockEntity ropeKnotBE) {
            BlockState heldBlock = ropeKnotBE.getHeldBlock();
            if (heldBlock != null && !heldBlock.isAir()) {
                drops.add(new ItemStack(heldBlock.getBlock().asItem()));
            }
        }
        return drops;
    }

    private boolean canConnectTo(BlockState neighbor, Direction dirTowardNeighbor) {
        if (neighbor.getBlock() instanceof RopeBlock) {
            Direction needOnNeighbor = dirTowardNeighbor.getOpposite();
            return neighbor.getValue(getRopeProperty(needOnNeighbor));
        }
        return false;
    }

    private void updateNeighbors(Level level, BlockPos pos) {
        level.blockUpdated(pos, this);
        for (Direction d : Direction.Plane.HORIZONTAL) {
            BlockPos n = pos.relative(d);
            level.blockUpdated(n, level.getBlockState(n).getBlock());
        }
    }

    private BooleanProperty getDirectionProperty(Direction direction) {
        return switch (direction) {
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            default -> NORTH;
        };
    }

    private static BooleanProperty getRopeProperty(Direction direction) {
        return switch (direction) {
            case NORTH -> RopeBlock.NORTH;
            case SOUTH -> RopeBlock.SOUTH;
            case EAST  -> RopeBlock.EAST;
            case WEST  -> RopeBlock.WEST;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide() && !state.is(oldState.getBlock())) updateNeighbors(level, pos);
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston)  {
        if (!level.isClientSide() && !state.is(newState.getBlock())) updateNeighbors(level, pos);
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
	}

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }
}