package com.edryu.morethings.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import org.jetbrains.annotations.Nullable;

public class RedButtonBlock extends ButtonBlock {
    private static final VoxelShape STONE_DOWN  = Block.createCuboidShape(4, 0, 4, 12, 1, 12);
    private static final VoxelShape STONE_UP    = Block.createCuboidShape(4, 15, 4, 12, 16, 12);
    private static final VoxelShape STONE_NORTH = Block.createCuboidShape(4, 4, 15, 12, 12, 16);
    private static final VoxelShape STONE_EAST  = Block.createCuboidShape(0, 4, 4, 1, 12, 12);
    private static final VoxelShape STONE_SOUTH = Block.createCuboidShape(4, 4, 0, 12, 12, 1);
    private static final VoxelShape STONE_WEST  = Block.createCuboidShape(15, 4, 4, 16, 12, 12);

    private static final VoxelShape FLOOR_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 1, 5, 11, 5, 11), STONE_DOWN);
    private static final VoxelShape FLOOR_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 1, 5, 11, 3, 11), STONE_DOWN);
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 5, 11, 11, 11, 15), STONE_NORTH);
    private static final VoxelShape NORTH_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 5, 13, 11, 11, 15), STONE_NORTH);
    private static final VoxelShape EAST_SHAPE = VoxelShapes.union(Block.createCuboidShape(1, 5, 5, 5, 11, 11), STONE_EAST);
    private static final VoxelShape EAST_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(1, 5, 5, 3, 11, 11), STONE_EAST);
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 5, 1, 11, 11, 5), STONE_SOUTH);
    private static final VoxelShape SOUTH_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 5, 1, 11, 11, 3), STONE_SOUTH);
    private static final VoxelShape WEST_SHAPE = VoxelShapes.union(Block.createCuboidShape(11, 5, 5, 15, 11, 11), STONE_WEST);
    private static final VoxelShape WEST_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(13, 5, 5, 15, 11, 11), STONE_WEST);
    private static final VoxelShape CEILING_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 11, 5, 11, 15, 11), STONE_UP);
    private static final VoxelShape CEILING_PRESSED_SHAPE = VoxelShapes.union(Block.createCuboidShape(5, 13, 5, 11, 15, 11), STONE_UP);

    public RedButtonBlock(Settings settings) {
        super(BlockSetType.STONE, 30, settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(FACE, BlockFace.WALL));
    }
    
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        boolean is_powered = state.get(POWERED);
        
        switch (state.get(FACE)) {
            case FLOOR -> {
                return is_powered ? FLOOR_PRESSED_SHAPE : FLOOR_SHAPE;
            }
            case CEILING -> {
                return is_powered ? CEILING_PRESSED_SHAPE : CEILING_SHAPE;
            }
            case WALL -> {
                switch (direction) {
                    case NORTH -> {
                        return is_powered ? NORTH_PRESSED_SHAPE : NORTH_SHAPE;
                    }
                    case EAST -> {
                        return is_powered ? EAST_PRESSED_SHAPE : EAST_SHAPE;
                    }
                    case SOUTH -> {
                        return is_powered ? SOUTH_PRESSED_SHAPE : SOUTH_SHAPE;
                    }
                    case WEST -> {
                        return is_powered ? WEST_PRESSED_SHAPE : WEST_SHAPE;
                    }
                    case UP -> {
                        return is_powered ? FLOOR_PRESSED_SHAPE : FLOOR_SHAPE;
                    }
                    case DOWN -> {
                        return is_powered ? CEILING_PRESSED_SHAPE : CEILING_SHAPE;
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void playClickSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos, boolean powered) {
        world.playSound(powered ? player : null, pos, this.getClickSound(powered), SoundCategory.BLOCKS, 1, powered ? 0.6f : 0.5f);
    }

    @Override
    protected SoundEvent getClickSound(boolean powered) {
        return SoundEvents.BLOCK_BONE_BLOCK_BREAK;
    }
}