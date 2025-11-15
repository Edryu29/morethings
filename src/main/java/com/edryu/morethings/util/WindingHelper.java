package com.edryu.morethings.util;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.block.PulleyBlock;
import com.edryu.morethings.block.RopeBlock;
import com.edryu.morethings.client.datagen.BlockTagProvider;
import com.edryu.morethings.entity.PulleyBlockEntity;
import com.edryu.morethings.util.BlockProperties.Winding;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.SideShapeType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WindingHelper {

    public static boolean addWindingDown(BlockPos pos, World world, @Nullable PlayerEntity player, Hand hand, Block windingBlock) {
        return addWinding(pos, world, player, hand, windingBlock, Direction.DOWN, Integer.MAX_VALUE);
    }

    public static boolean addWinding(BlockPos pos, World world, @Nullable PlayerEntity player, Hand hand, Block windingBlock, Direction moveDir, int maxDist) {
        BlockState state = world.getBlockState(pos);
        if (maxDist <= 0) return false;
        else maxDist--;
        if (isCorrectWinding(windingBlock, state, moveDir)) {
            return addWinding(pos.offset(moveDir), world, player, hand, windingBlock, moveDir, maxDist);

        } else if (state.getBlock() instanceof PulleyBlock && world.getBlockEntity(pos) instanceof PulleyBlockEntity te) {
            return te.rotateIndirect(player, hand, windingBlock, moveDir, false);

        } else {
            return placeAndMove(player, hand, world, pos, moveDir, windingBlock);
        }
    }

    public static boolean removeWindingDown(BlockPos pos, World world, Block windingBlock) {
        return removeWinding(pos, world, windingBlock, Direction.DOWN, Integer.MAX_VALUE);
    }

    public static boolean removeWinding(BlockPos pos, World world, Block windingBlock, Direction moveDir, int maxDist) {
        BlockState state = world.getBlockState(pos);
        if (maxDist <= 0) return false;
        else maxDist--;
        if (isCorrectWinding(windingBlock, state, moveDir)) {
            return removeWinding(pos.offset(moveDir), world, windingBlock, moveDir, maxDist);

        } else if (state.getBlock() instanceof PulleyBlock && world.getBlockEntity(pos) instanceof PulleyBlockEntity pe && !pe.isEmpty()) {
            return pe.rotateIndirect(null, Hand.MAIN_HAND, windingBlock, moveDir, true);

        } else {
            BlockPos up = pos.offset(moveDir.getOpposite());
            if ((world.getBlockState(up).getBlock() != windingBlock)) return false;
            if (!placeAndMove(null, Hand.MAIN_HAND, world, pos, moveDir.getOpposite(), null)) {
                world.setBlockState(up, world.getFluidState(up).getBlockState());
            }
            return true;
        }
    }

    public static boolean placeAndMove(@Nullable PlayerEntity player, Hand hand, World world, BlockPos originPos, Direction moveDir, @Nullable Block placeWhereItWas) {
        BlockState originalState = world.getBlockState(originPos);
        BlockPos targetPos = originPos.offset(moveDir);
        BlockState targetState = world.getBlockState(targetPos);

        boolean needsToPush = !originalState.isReplaceable();
        if (needsToPush) {
            if (!targetState.isReplaceable() && placeWhereItWas != null) return false;
            if (!isPushableWithPulley(originalState, world, originPos, moveDir)) return false;
        }

        FluidState originalFluid = world.getFluidState(originPos);

        if (placeWhereItWas != null) {
            ItemStack stack = new ItemStack(placeWhereItWas);
            BlockHitResult hitResult = new BlockHitResult(Vec3d.ofCenter(originPos), moveDir.getOpposite(), originPos, false);
            ItemPlacementContext context = new ItemPlacementContext(world, player, hand, stack, hitResult);

            if (stack.getItem() instanceof BlockItem bi) {
                ActionResult placeResult = bi.place(context);
                if (placeResult == ActionResult.PASS || placeResult == ActionResult.FAIL) {
                    world.setBlockState(originPos, originalState, Block.NOTIFY_LISTENERS);
                    return false;
                }
                if (!needsToPush) return true;
            }
        } else {
            world.setBlockState(originPos, originalFluid.getBlockState());
        }

        FluidState targetFluid = world.getFluidState(targetPos);
        boolean waterFluid = targetFluid.isOf(Fluids.WATER);

        if (originalState.contains(Properties.WATERLOGGED)) {
            originalState = originalState.with(Properties.WATERLOGGED, waterFluid);
        } else if (originalState.getBlock() instanceof AbstractCauldronBlock) {
            if (waterFluid && originalState.isOf(Blocks.CAULDRON) || originalState.isOf(Blocks.WATER_CAULDRON)) {
                originalState = Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3);
            } else if (targetFluid.isOf(Fluids.LAVA) && originalState.isOf(Blocks.CAULDRON) || originalState.isOf(Blocks.LAVA_CAULDRON)) {
                originalState = Blocks.LAVA_CAULDRON.getDefaultState();
            }
        }

        originalState = Block.postProcessState(originalState, world, targetPos);
        world.setBlockState(targetPos, originalState);
        return true;
    }


    public static boolean isPushableWithPulley(BlockState state, World world, BlockPos pos, Direction moveDir) {
        if (state.getBlock() instanceof PulleyBlock) return false;
        if (state.isIn(BlockTagProvider.UNMOVEABLE_BY_PULLEY)) return false;
        if (!state.isSolid()) return false;
        if (moveDir.getAxis().isVertical() && state.isIn(BlockTagProvider.HANG_FROM_ROPES)) {
            return true;
        }
        boolean couldBreak = !state.isSolid();
        return isPushable(state, world, pos, moveDir, couldBreak, moveDir);
    }

    //same as PistonBaseBlock.isPushable but ignores some stuff like Block Entities
    private static boolean isPushable(BlockState state, World world, BlockPos pos, Direction movementDirection, boolean allowDestroy, Direction pistonFacing) {
        if (pos.getY() >= world.getBottomY() && pos.getY() <= world.getTopY() - 1 && world.getWorldBorder().contains(pos)) {
            if (state.isAir()) {
                return true;
            } else if (!state.isOf(Blocks.OBSIDIAN) && !state.isOf(Blocks.CRYING_OBSIDIAN) && !state.isOf(Blocks.RESPAWN_ANCHOR) && !state.isOf(Blocks.REINFORCED_DEEPSLATE)) {
                if (movementDirection == Direction.DOWN && pos.getY() == world.getBottomY()) {
                    return false;
                } else if (movementDirection == Direction.UP && pos.getY() == world.getTopY() - 1) {
                    return false;
                } else {
                    if (!state.isOf(Blocks.PISTON) && !state.isOf(Blocks.STICKY_PISTON)) {
                        if (state.getHardness(world, pos) == -1.0F) {
                            return false;
                        }

                        switch (state.getPistonBehavior()) {
                            case BLOCK -> {
                                return false;
                            }
                            case DESTROY -> {

                                return allowDestroy;
                            }
                            case PUSH_ONLY -> {
                                return movementDirection == pistonFacing;
                            }
                        }
                    } else if (state.get(PistonBlock.EXTENDED)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // public static boolean isPushableWithPulley(BlockState state, World world, BlockPos pos, Direction moveDir) {
    //     if (state.isAir()) return false;
    //     if (state.getBlock() instanceof PulleyBlock) return false;
    //     if ((state.isOf(Blocks.PISTON) || state.isOf(Blocks.STICKY_PISTON)) && state.get(Properties.EXTENDED)) return false;
    //     if (state.isOf(Blocks.OBSIDIAN) || state.isOf(Blocks.CRYING_OBSIDIAN) || state.isOf(Blocks.RESPAWN_ANCHOR) || state.isOf(Blocks.BEDROCK)) return false;

    //     if (pos.getY() < world.getBottomY() || pos.getY() > world.getTopY() - 1 || !world.getWorldBorder().contains(pos)) return false;
    //     if (moveDir == Direction.DOWN && pos.getY() == world.getBottomY()) return false;
    //     if (moveDir == Direction.UP && pos.getY() == world.getTopY() - 1) return false;

    //     if (state.isIn(BlockTagProvider.MOVEABLE_BY_PULLEY)) return true;
    //     if (state.isIn(BlockTagProvider.UNMOVEABLE_BY_PULLEY)) return false;
    //     if (moveDir.getAxis().isVertical() && state.isIn(BlockTagProvider.HANG_FROM_ROPES)) return true;
    //     if (state.hasBlockEntity()) return false;
    //     return state.isSideSolid(world, pos, moveDir, SideShapeType.CENTER);
    // }

    public static boolean isCorrectWinding(Block windingBlock, BlockState state, Direction moveDir) {
        if (state.getBlock() instanceof ChainBlock && state.get(ChainBlock.AXIS) != moveDir.getAxis()) return false;
        return windingBlock == state.getBlock();
    }

    public static Winding getWindingType(Item item) {
        if (item instanceof BlockItem bi && bi.getBlock() instanceof ChainBlock) return Winding.CHAIN;
        if (item instanceof BlockItem bi && bi.getBlock() instanceof RopeBlock) return Winding.ROPE;
        return Winding.NONE;
    }
}
