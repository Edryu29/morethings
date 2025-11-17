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

public class WindingHelper {

    public static boolean addWindingDown(BlockPos pos, Level world, @Nullable Player player, InteractionHand hand, Block windingBlock) {
        return addWinding(pos, world, player, hand, windingBlock, Direction.DOWN, Integer.MAX_VALUE);
    }

    public static boolean addWinding(BlockPos pos, Level world, @Nullable Player player, InteractionHand hand, Block windingBlock, Direction moveDir, int maxDist) {
        BlockState state = world.getBlockState(pos);
        if (maxDist <= 0) return false;
        else maxDist--;
        if (isCorrectWinding(windingBlock, state, moveDir)) {
            return addWinding(pos.relative(moveDir), world, player, hand, windingBlock, moveDir, maxDist);

        } else if (state.getBlock() instanceof PulleyBlock && world.getBlockEntity(pos) instanceof PulleyBlockEntity te) {
            return te.operateIndirect(player, hand, windingBlock, moveDir, false);

        } else {
            return placeAndMove(player, hand, world, pos, moveDir, windingBlock);
        }
    }

    public static boolean removeWindingDown(BlockPos pos, Level world, Block windingBlock) {
        return removeWinding(pos, world, windingBlock, Direction.DOWN, Integer.MAX_VALUE);
    }

    public static boolean removeWinding(BlockPos pos, Level world, Block windingBlock, Direction moveDir, int maxDist) {
        BlockState state = world.getBlockState(pos);
        if (maxDist < 0) return false;
        else maxDist--;
        if (isCorrectWinding(windingBlock, state, moveDir)) {
            return removeWinding(pos.relative(moveDir), world, windingBlock, moveDir, maxDist);

        } else if (state.getBlock() instanceof PulleyBlock && world.getBlockEntity(pos) instanceof PulleyBlockEntity pe && !pe.isEmpty()) {
            return pe.operateIndirect(null, InteractionHand.MAIN_HAND, windingBlock, moveDir, true);

        } else {
            BlockPos up = pos.relative(moveDir.getOpposite());
            if ((world.getBlockState(up).getBlock() != windingBlock)) return false;
            if (!placeAndMove(null, InteractionHand.MAIN_HAND, world, pos, moveDir.getOpposite(), null)) {
                world.setBlockAndUpdate(up, world.getFluidState(up).createLegacyBlock());
            }
            return true;
        }
    }

    public static boolean placeAndMove(@Nullable Player player, InteractionHand hand, Level world, BlockPos originPos, Direction moveDir, @Nullable Block placeWhereItWas) {
        BlockState originalState = world.getBlockState(originPos);
        BlockPos targetPos = originPos.relative(moveDir);
        BlockState targetState = world.getBlockState(targetPos);
        CompoundTag beTag = null;

        boolean needsToPush = !originalState.canBeReplaced();
        if (needsToPush) {
            if (!targetState.canBeReplaced() && placeWhereItWas != null) return false;
            if (!isPushableWithPulley(originalState, world, originPos, moveDir)) return false;

            BlockEntity be = world.getBlockEntity(originPos);
            if (be != null) {
                be.setRemoved();
                beTag = be.saveWithoutMetadata(world.registryAccess());
            }
        }

        FluidState originalFluid = world.getFluidState(originPos);
        
        // Replace original block with air and place rope
        if (placeWhereItWas != null) {
            world.setBlock(originPos, originalFluid.createLegacyBlock(), Block.UPDATE_IMMEDIATE | Block.UPDATE_CLIENTS);
            ItemStack stack = new ItemStack(placeWhereItWas);
            BlockHitResult hitResult = new BlockHitResult(Vec3.atCenterOf(originPos), moveDir.getOpposite(), originPos, false);
            BlockPlaceContext context = new BlockPlaceContext(world, player, hand, stack, hitResult);

            if (stack.getItem() instanceof BlockItem bi) {
                InteractionResult placeResult = bi.place(context);
                if (placeResult == InteractionResult.PASS || placeResult == InteractionResult.FAIL) {
                    world.setBlock(originPos, originalState, Block.UPDATE_CLIENTS);
                    return false;
                }
                if (!needsToPush) return true;
            }
        } else {
            world.setBlockAndUpdate(originPos, originalFluid.createLegacyBlock());
        }

        FluidState targetFluid = world.getFluidState(targetPos);
        boolean waterFluid = targetFluid.is(Fluids.WATER);

        if (originalState.hasProperty(BlockStateProperties.WATERLOGGED)) {
            originalState = originalState.setValue(BlockStateProperties.WATERLOGGED, waterFluid);
        } else if (originalState.getBlock() instanceof AbstractCauldronBlock) {
            if (waterFluid && originalState.is(Blocks.CAULDRON) || originalState.is(Blocks.WATER_CAULDRON)) {
                originalState = Blocks.WATER_CAULDRON.this.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
            } else if (targetFluid.is(Fluids.LAVA) && originalState.is(Blocks.CAULDRON) || originalState.is(Blocks.LAVA_CAULDRON)) {
                originalState = Blocks.LAVA_CAULDRON.this.defaultBlockState();
            }
        }

        if (needsToPush){ // Condition added to skip replaceable blocks like water from being moved
            originalState = Block.updateFromNeighbourShapes(originalState, world, targetPos);
            world.setBlockAndUpdate(targetPos, originalState);
        } else {
            world.removeBlock(targetPos, false);
        }
        if (beTag != null) {
            BlockEntity te = world.getBlockEntity(targetPos);
            if (te != null) te.loadWithComponents(beTag, world.registryAccess());
        }
        return true;
    }

    public static boolean isPushableWithPulley(BlockState state, Level world, BlockPos pos, Direction moveDir) {
        if (state.isAir()) return true;

        if (pos.getY() < world.getMinBuildHeight() || pos.getY() > world.getMaxBuildHeight() - 1 || !world.getWorldBorder().isWithinBounds(pos)) return false;
        if (moveDir == Direction.DOWN && pos.getY() == world.getMinBuildHeight()) return false;
        if (moveDir == Direction.UP && pos.getY() == world.getMaxBuildHeight() - 1) return false;
        if (state.is(BlockTagProvider.UNMOVEABLE_BY_PULLEY)) return false;

        if (state.getBlock() instanceof PulleyBlock) return false;
        if ((state.is(Blocks.PISTON) || state.is(Blocks.STICKY_PISTON)) && state.getValue(BlockStateProperties.EXTENDED)) return false;
        if (state.is(Blocks.OBSIDIAN) || state.is(Blocks.CRYING_OBSIDIAN) || state.is(Blocks.RESPAWN_ANCHOR) || state.is(Blocks.BEDROCK)) return false;

        if (state.getBlock() instanceof AbstractCauldronBlock) return true;
        if (moveDir.getAxis().isVertical() && state.is(BlockTagProvider.HANG_FROM_ROPES)) return true;
        if (state.is(BlockTagProvider.MOVEABLE_BY_PULLEY)) return true;
        // if (state.hasBlockEntity()) return false;

        return state.isFaceSturdy(world, pos, moveDir, SupportType.CENTER);
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
