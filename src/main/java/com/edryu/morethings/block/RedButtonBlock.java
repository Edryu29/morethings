package com.edryu.morethings.block;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RedButtonBlock extends ButtonBlock {
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    private static final VoxelShape STONE_DOWN = Block.box(3, 0, 3, 13, 1, 13);
    private static final VoxelShape STONE_UP = Block.box(3, 15, 3, 13, 16, 13);
    private static final VoxelShape STONE_NORTH = Block.box(3, 3, 15, 13, 13, 16);
    private static final VoxelShape STONE_SOUTH = Block.box(3, 3, 0, 13, 13, 1);
    private static final VoxelShape STONE_EAST = Block.box(0, 3, 3, 1, 13, 13);
    private static final VoxelShape STONE_WEST = Block.box(15, 3, 3, 16, 13, 13);

    private static final VoxelShape FLOOR_CLOSED_SHAPE = Shapes.or(Block.box(4, 1, 4, 12, 8, 12), STONE_DOWN);
    private static final VoxelShape FLOOR_OPEN_SHAPE = Shapes.or(Block.box(5, 1, 5, 11, 5, 11), STONE_DOWN);
    private static final VoxelShape FLOOR_PRESSED_SHAPE = Shapes.or(Block.box(5, 1, 5, 11, 3, 11), STONE_DOWN);
    private static final VoxelShape CEILING_CLOSED_SHAPE = Shapes.or(Block.box(4, 8, 4, 12, 15, 12), STONE_UP);
    private static final VoxelShape CEILING_OPEN_SHAPE = Shapes.or(Block.box(5, 11, 5, 11, 15, 11), STONE_UP);
    private static final VoxelShape CEILING_PRESSED_SHAPE = Shapes.or(Block.box(5, 13, 5, 11, 15, 11), STONE_UP);
    private static final VoxelShape NORTH_CLOSED_SHAPE = Shapes.or(Block.box(4, 4, 8, 12, 12, 15), STONE_NORTH);
    private static final VoxelShape NORTH_OPEN_SHAPE = Shapes.or(Block.box(5, 5, 11, 11, 11, 15), STONE_NORTH);
    private static final VoxelShape NORTH_PRESSED_SHAPE = Shapes.or(Block.box(5, 5, 13, 11, 11, 15), STONE_NORTH);
    private static final VoxelShape SOUTH_CLOSED_SHAPE = Shapes.or(Block.box(4, 4, 1, 12, 12, 8), STONE_SOUTH);
    private static final VoxelShape SOUTH_OPEN_SHAPE = Shapes.or(Block.box(5, 5, 1, 11, 11, 5), STONE_SOUTH);
    private static final VoxelShape SOUTH_PRESSED_SHAPE = Shapes.or(Block.box(5, 5, 1, 11, 11, 3), STONE_SOUTH);
    private static final VoxelShape EAST_CLOSED_SHAPE = Shapes.or(Block.box(1, 4, 4, 8, 12, 12), STONE_EAST);
    private static final VoxelShape EAST_OPEN_SHAPE = Shapes.or(Block.box(1, 5, 5, 5, 11, 11), STONE_EAST);
    private static final VoxelShape EAST_PRESSED_SHAPE = Shapes.or(Block.box(1, 5, 5, 3, 11, 11), STONE_EAST);
    private static final VoxelShape WEST_CLOSED_SHAPE = Shapes.or(Block.box(8, 4, 4, 15, 12, 12), STONE_WEST);
    private static final VoxelShape WEST_OPEN_SHAPE = Shapes.or(Block.box(11, 5, 5, 15, 11, 11), STONE_WEST);
    private static final VoxelShape WEST_PRESSED_SHAPE = Shapes.or(Block.box(13, 5, 5, 15, 11, 11), STONE_WEST);

    public RedButtonBlock(Properties settings) {
        super(BlockSetType.STONE, 30, settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(FACE, AttachFace.WALL).setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, FACE, OPEN);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        boolean is_powered = state.getValue(POWERED);
        boolean is_open = state.getValue(OPEN);
        
        switch (state.getValue(FACE)) {
            case FLOOR:
                return is_powered ? FLOOR_PRESSED_SHAPE : (is_open ? FLOOR_OPEN_SHAPE : FLOOR_CLOSED_SHAPE);
            case WALL:
                switch (direction) {
                    case NORTH:
					default:
                        return is_powered ? PRESSED_NORTH_AABB : (is_open ? NORTH_OPEN_SHAPE : NORTH_CLOSED_SHAPE);
                    case EAST:
                        return is_powered ? PRESSED_EAST_AABB : (is_open ? EAST_OPEN_SHAPE : EAST_CLOSED_SHAPE);
                    case SOUTH:
                        return is_powered ? PRESSED_SOUTH_AABB : (is_open ? SOUTH_OPEN_SHAPE : SOUTH_CLOSED_SHAPE);
                    case WEST:
                        return is_powered ? PRESSED_WEST_AABB : (is_open ? WEST_OPEN_SHAPE : WEST_CLOSED_SHAPE);
                }
			default:
                return is_powered ? CEILING_PRESSED_SHAPE : (is_open ? CEILING_OPEN_SHAPE : CEILING_CLOSED_SHAPE);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        boolean is_open = state.getValue(OPEN);

        if (Screen.hasShiftDown()) {
            world.setBlockAndUpdate(pos, state.setValue(OPEN, !is_open));
            world.playSound(is_open ? player : null, pos, is_open ? SoundEvents.IRON_TRAPDOOR_OPEN : SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1f, 1);
            return InteractionResult.SUCCESS;

        } else if (!is_open) {
           return InteractionResult.SUCCESS; 

        } else {
            if ((Boolean)state.getValue(POWERED)) {
                return InteractionResult.CONSUME;
            } else {
                this.press(state, world, pos, player);
                return InteractionResult.sidedSuccess(world.isClientSide);
            }
        }
    }

    @Override
    protected void playSound(@Nullable Player player, LevelAccessor world, BlockPos pos, boolean powered) {
        world.playSound(powered ? player : null, pos, this.getSound(powered), SoundSource.BLOCKS, 1, powered ? 0.6f : 0.5f);
    }

    @Override
    protected SoundEvent getSound(boolean powered) {
        return SoundEvents.BONE_BLOCK_BREAK;
    }
}