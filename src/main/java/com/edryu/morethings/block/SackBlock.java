package com.edryu.morethings.block;

import com.edryu.morethings.MoreThingsRegister;
import com.edryu.morethings.MoreThingsSounds;
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
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.BlockView;

public class SackBlock extends BlockWithEntity  {
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
        // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
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
            // This will call the createScreenHandlerFactory method from BlockWithEntity, which will return our blockEntity casted to
            // a namedScreenHandlerFactory. If your block class does not extend BlockWithEntity, it needs to implement createScreenHandlerFactory.
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
 
            if (screenHandlerFactory != null) {
                // With this call the server will request the client to open the appropriate Screenhandler
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;

        // if (!player.getAbilities().allowModifyWorld || (player != null && !player.isHolding(MoreThingsRegister.ORB))) {
        //     return ActionResult.PASS;
        // } else {
        //     boolean is_open = state.get(OPEN);
        //     world.setBlockState(pos, state.with(OPEN, !is_open));
        //     world.playSound(player, pos, MoreThingsSounds.SACK_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
        //     return ActionResult.SUCCESS;
        // }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
    }
 
 
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SackBlockEntity SackBlockEntity) {
                ItemScatterer.spawn(world, pos, SackBlockEntity);
                // update comparators
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
 
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }
 
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }


}