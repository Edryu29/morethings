package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class RedSafeButtonBlock extends ButtonBlock {

    public static final BooleanProperty OPEN = BooleanProperty.of("open");

    private static final VoxelShape STONE_DOWN = Block.createCuboidShape(3, 0, 3, 13, 1, 13);
    private static final VoxelShape STONE_UP = Block.createCuboidShape(3, 15, 3, 13, 16, 13);
    private static final VoxelShape STONE_NORTH = Block.createCuboidShape(3, 3, 15, 13, 13, 16);
    private static final VoxelShape STONE_EAST = Block.createCuboidShape(0, 3, 3, 1, 13, 13);
    private static final VoxelShape STONE_SOUTH = Block.createCuboidShape(3, 3, 0, 13, 13, 1);
    private static final VoxelShape STONE_WEST = Block.createCuboidShape(15, 3, 3, 16, 13, 13);

    private static final VoxelShape FLOOR_CLOSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(4, 1, 4, 12, 8, 12), STONE_DOWN);
    private static final VoxelShape FLOOR_OPEN_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 1, 5, 11, 5, 11), STONE_DOWN);
    private static final VoxelShape FLOOR_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 1, 5, 11, 3, 11), STONE_DOWN);
    private static final VoxelShape CEILING_CLOSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(4, 8, 4, 12, 15, 12), STONE_UP);
    private static final VoxelShape CEILING_OPEN_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 11, 5, 11, 15, 11), STONE_UP);
    private static final VoxelShape CEILING_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 13, 5, 11, 15, 11), STONE_UP);
    private static final VoxelShape NORTH_CLOSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(4, 4, 8, 12, 12, 15), STONE_NORTH);
    private static final VoxelShape NORTH_OPEN_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 5, 11, 11, 11, 15), STONE_NORTH);
    private static final VoxelShape NORTH_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 5, 13, 11, 11, 15), STONE_NORTH);
    private static final VoxelShape EAST_CLOSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(1, 4, 4, 8, 12, 12), STONE_EAST);
    private static final VoxelShape EAST_OPEN_SHAPE = VoxelShapes.union(Block.createCuboidShape(1, 5, 5, 5, 11, 11), STONE_EAST);
    private static final VoxelShape EAST_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(1, 5, 5, 3, 11, 11), STONE_EAST);
    private static final VoxelShape SOUTH_CLOSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(4, 4, 1, 12, 12, 8), STONE_SOUTH);
    private static final VoxelShape SOUTH_OPEN_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 5, 1, 11, 11, 5), STONE_SOUTH);
    private static final VoxelShape SOUTH_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 5, 1, 11, 11, 3), STONE_SOUTH);
    private static final VoxelShape WEST_CLOSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(8, 4, 4, 15, 12, 12), STONE_WEST);
    private static final VoxelShape WEST_OPEN_SHAPE = VoxelShapes.union(Block.createCuboidShape(11, 5, 5, 15, 11, 11), STONE_WEST);
    private static final VoxelShape WEST_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(13, 5, 5, 15, 11, 11), STONE_WEST);

    public RedSafeButtonBlock(Settings settings) {
        super(BlockSetType.STONE, 30, settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(FACE, BlockFace.WALL).with(OPEN, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        boolean is_powered = state.get(POWERED);
        boolean is_open = state.get(OPEN);
        
        switch (state.get(FACE)) {
            case FLOOR -> {
                if (is_powered) {
                    return FLOOR_PRESSED_SHAPE;
                } else {
                    return is_open ? FLOOR_OPEN_SHAPE : FLOOR_CLOSED_SHAPE;
                }
            }
            case CEILING -> {
                if (is_powered) {
                    return CEILING_PRESSED_SHAPE;
                } else {
                    return is_open ? CEILING_OPEN_SHAPE : CEILING_CLOSED_SHAPE;
                }
            }
            case WALL -> {
                switch (direction) {
                    case NORTH -> {
                        if (is_powered) {
                            return NORTH_PRESSED_SHAPE;
                        } else {
                            return is_open ? NORTH_OPEN_SHAPE : NORTH_CLOSED_SHAPE;
                        }
                    }
                    case EAST -> {
                        if (is_powered) {
                            return EAST_PRESSED_SHAPE;
                        } else {
                            return is_open ? EAST_OPEN_SHAPE : EAST_CLOSED_SHAPE;
                        }
                    }
                    case SOUTH -> {
                        if (is_powered) {
                            return SOUTH_PRESSED_SHAPE;
                        } else {
                            return is_open ? SOUTH_OPEN_SHAPE : SOUTH_CLOSED_SHAPE;
                        }
                    }
                    case WEST -> {
                        if (is_powered) {
                            return WEST_PRESSED_SHAPE;
                        } else {
                            return is_open ? WEST_OPEN_SHAPE : WEST_CLOSED_SHAPE;
                        }
                    }
                    case UP -> {
                        return FLOOR_OPEN_SHAPE;
                    }
                    case DOWN -> {
                        return CEILING_OPEN_SHAPE;
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        boolean is_open = state.get(OPEN);

        if (Screen.hasShiftDown()) {
            world.setBlockState(pos, state.with(OPEN, !is_open));
            world.playSound(is_open ? player : null, pos, is_open ? SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN : SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1f, 1);
            return ActionResult.SUCCESS;

        } else if (!is_open) {
           return ActionResult.SUCCESS; 

        } else {
            if ((Boolean)state.get(POWERED)) {
                return ActionResult.CONSUME;} else {
                    this.powerOn(state, world, pos, player);
                    return ActionResult.success(world.isClient);
            }
        }
    }

    @Override
    protected void playClickSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos, boolean powered) {
        world.playSound(powered ? player : null, pos, this.getClickSound(powered), SoundCategory.BLOCKS, 1, powered ? 0.6f : 0.5f);
    }

    @Override
    protected SoundEvent getClickSound(boolean powered) {
        return SoundEvents.BLOCK_BONE_BLOCK_BREAK;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, FACE, OPEN);
    }
}