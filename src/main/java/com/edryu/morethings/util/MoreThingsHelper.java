package com.edryu.morethings.util;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.block.PulleyBlock;
import com.edryu.morethings.block.RopeBlock;
import com.edryu.morethings.client.datagen.BlockTagProvider;
import com.edryu.morethings.entity.PulleyBlockEntity;
import com.edryu.morethings.util.BlockProperties.Winding;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class MoreThingsHelper {

    public static boolean addWindingDown(BlockPos pos, Level level, @Nullable Player player, InteractionHand hand, Block windingBlock) {
        return addWinding(pos, level, player, hand, windingBlock, Direction.DOWN, Integer.MAX_VALUE);
    }

    public static boolean addWinding(BlockPos pos, Level level, @Nullable Player player, InteractionHand hand, Block windingBlock, Direction moveDir, int maxDist) {
        BlockState state = level.getBlockState(pos);
        if (maxDist <= 0) return false;
        else maxDist--;
        if (isCorrectWinding(windingBlock, state, moveDir)) {
            return addWinding(pos.relative(moveDir), level, player, hand, windingBlock, moveDir, maxDist);

        } else if (state.getBlock() instanceof PulleyBlock && level.getBlockEntity(pos) instanceof PulleyBlockEntity te) {
            return te.operateIndirect(player, hand, windingBlock, moveDir, false);

        } else {
            return placeAndMove(player, hand, level, pos, moveDir, windingBlock);
        }
    }

    public static boolean removeWindingDown(BlockPos pos, Level level, Block windingBlock) {
        return removeWinding(pos, level, windingBlock, Direction.DOWN, Integer.MAX_VALUE);
    }

    public static boolean removeWinding(BlockPos pos, Level level, Block windingBlock, Direction moveDir, int maxDist) {
        BlockState state = level.getBlockState(pos);
        if (maxDist < 0) return false;
        else maxDist--;
        if (isCorrectWinding(windingBlock, state, moveDir)) {
            return removeWinding(pos.relative(moveDir), level, windingBlock, moveDir, maxDist);

        } else if (state.getBlock() instanceof PulleyBlock && level.getBlockEntity(pos) instanceof PulleyBlockEntity pe && !pe.isEmpty()) {
            return pe.operateIndirect(null, InteractionHand.MAIN_HAND, windingBlock, moveDir, true);

        } else {
            BlockPos up = pos.relative(moveDir.getOpposite());
            if ((level.getBlockState(up).getBlock() != windingBlock)) return false;
            if (!placeAndMove(null, InteractionHand.MAIN_HAND, level, pos, moveDir.getOpposite(), null)) {
                level.setBlockAndUpdate(up, level.getFluidState(up).createLegacyBlock());
            }
            return true;
        }
    }

    public static boolean placeAndMove(@Nullable Player player, InteractionHand hand, Level level, BlockPos originPos, Direction moveDir, @Nullable Block placeWhereItWas) {
        BlockState originalState = level.getBlockState(originPos);
        BlockPos targetPos = originPos.relative(moveDir);
        BlockState targetState = level.getBlockState(targetPos);
        CompoundTag beTag = null;

        boolean needsToPush = !originalState.canBeReplaced();
        if (needsToPush) {
            if (!targetState.canBeReplaced() && placeWhereItWas != null) return false;
            if (!isPushableWithPulley(originalState, level, originPos, moveDir)) return false;

            BlockEntity be = level.getBlockEntity(originPos);
            if (be != null) {
                be.setRemoved();
                beTag = be.saveWithoutMetadata(level.registryAccess());
            }
        }

        FluidState originalFluid = level.getFluidState(originPos);
        
        // Replace original block with air and place rope
        if (placeWhereItWas != null) {
            level.setBlock(originPos, originalFluid.createLegacyBlock(), Block.UPDATE_IMMEDIATE | Block.UPDATE_CLIENTS);
            ItemStack stack = new ItemStack(placeWhereItWas);
            BlockHitResult hitResult = new BlockHitResult(Vec3.atCenterOf(originPos), moveDir.getOpposite(), originPos, false);
            BlockPlaceContext context = new BlockPlaceContext(level, player, hand, stack, hitResult);

            if (stack.getItem() instanceof BlockItem bi) {
                InteractionResult placeResult = bi.place(context);
                if (placeResult == InteractionResult.PASS || placeResult == InteractionResult.FAIL) {
                    level.setBlock(originPos, originalState, Block.UPDATE_CLIENTS);
                    return false;
                }
                if (!needsToPush) return true;
            }
        } else {
            level.setBlockAndUpdate(originPos, originalFluid.createLegacyBlock());
        }

        FluidState targetFluid = level.getFluidState(targetPos);
        boolean waterFluid = targetFluid.is(Fluids.WATER);

        if (originalState.hasProperty(BlockStateProperties.WATERLOGGED)) {
            originalState = originalState.setValue(BlockStateProperties.WATERLOGGED, waterFluid);
        } else if (originalState.getBlock() instanceof AbstractCauldronBlock) {
            if (waterFluid && originalState.is(Blocks.CAULDRON) || originalState.is(Blocks.WATER_CAULDRON)) {
                originalState = Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
            } else if (targetFluid.is(Fluids.LAVA) && originalState.is(Blocks.CAULDRON) || originalState.is(Blocks.LAVA_CAULDRON)) {
                originalState = Blocks.LAVA_CAULDRON.defaultBlockState();
            }
        }

        if (needsToPush){ // Condition added to skip replaceable blocks like water from being moved
            originalState = Block.updateFromNeighbourShapes(originalState, level, targetPos);
            level.setBlockAndUpdate(targetPos, originalState);
        } else {
            level.removeBlock(targetPos, false);
        }
        if (beTag != null) {
            BlockEntity te = level.getBlockEntity(targetPos);
            if (te != null) te.loadWithComponents(beTag, level.registryAccess());
        }
        return true;
    }

    public static boolean isPushableWithPulley(BlockState state, Level level, BlockPos pos, Direction moveDir) {
        if (state.isAir()) return true;

        if (pos.getY() < level.getMinBuildHeight() || pos.getY() > level.getMaxBuildHeight() - 1 || !level.getWorldBorder().isWithinBounds(pos)) return false;
        if (moveDir == Direction.DOWN && pos.getY() == level.getMinBuildHeight()) return false;
        if (moveDir == Direction.UP && pos.getY() == level.getMaxBuildHeight() - 1) return false;
        if (state.is(BlockTagProvider.UNMOVEABLE_BY_PULLEY)) return false;

        if (state.getBlock() instanceof PulleyBlock) return false;
        if ((state.is(Blocks.PISTON) || state.is(Blocks.STICKY_PISTON)) && state.getValue(BlockStateProperties.EXTENDED)) return false;
        if (state.is(Blocks.OBSIDIAN) || state.is(Blocks.CRYING_OBSIDIAN) || state.is(Blocks.RESPAWN_ANCHOR) || state.is(Blocks.BEDROCK)) return false;

        if (state.getBlock() instanceof AbstractCauldronBlock) return true;
        if (moveDir.getAxis().isVertical() && state.is(BlockTagProvider.HANG_FROM_ROPES)) return true;
        if (state.is(BlockTagProvider.MOVEABLE_BY_PULLEY)) return true;
        // if (state.hasBlockEntity()) return false;

        return state.isFaceSturdy(level, pos, moveDir, SupportType.CENTER);
    }

    public static boolean isCorrectWinding(Block windingBlock, BlockState state, Direction moveDir) {
        if (state.getBlock() instanceof ChainBlock && state.getValue(ChainBlock.AXIS) != moveDir.getAxis()) return false;
        return windingBlock == state.getBlock();
    }

    public static Winding getWindingType(Item item) {
        if (item instanceof BlockItem bi && bi.getBlock() instanceof ChainBlock) return Winding.CHAIN;
        if (item instanceof BlockItem bi && bi.getBlock() instanceof RopeBlock) return Winding.ROPE;
        return Winding.NONE;
    }

}
