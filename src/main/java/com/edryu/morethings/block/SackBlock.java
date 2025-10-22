package com.edryu.morethings.block;

import com.edryu.morethings.entity.SackBlockEntity;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SackBlock extends BlockWithEntity {
   public static final MapCodec<SackBlock> CODEC = createCodec(SackBlock::new);
	public static final BooleanProperty OPEN = BooleanProperty.of("open");

    public SackBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(OPEN, false));
    }

    @Override
    protected MapCodec<? extends SackBlock> getCodec() {
        return createCodec(SackBlock::new);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
 
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SackBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        if (state.get(OPEN)){
            return VoxelShapes.union(
                VoxelShapes.cuboid(0.125f, 0f, 0.125f, 0.875f, 0.75f, 0.875f),
                VoxelShapes.cuboid(0.1875f, 0.75f, 0.1875f, 0.8125f, 0.875f, 0.8125f)
            );
        } else {
            return VoxelShapes.union(
                VoxelShapes.cuboid(0.125f, 0f, 0.125f, 0.875f, 0.75f, 0.875f),
                VoxelShapes.cuboid(0.3125f, 0.8125f, 0.3125f, 0.6875f, 1f, 0.6875f),
                VoxelShapes.cuboid(0.375f, 0.75f, 0.375f, 0.625f, 0.8125f, 0.625f)
            );
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }
 
    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof SackBlockEntity SackBlockEntity) {
            ItemScatterer.spawn(world, pos, SackBlockEntity);
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
    }
}