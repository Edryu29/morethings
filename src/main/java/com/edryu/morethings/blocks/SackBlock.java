package com.edryu.morethings.blocks;

import com.edryu.morethings.MoreThingsRegister;
import com.edryu.morethings.MoreThingsSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.BlockView;

public class SackBlock extends Block {
	public static final BooleanProperty OPEN = BooleanProperty.of("open");

    public SackBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(OPEN, false));
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
        if (!player.getAbilities().allowModifyWorld || (player != null && !player.isHolding(MoreThingsRegister.STAFF))) {
            return ActionResult.PASS;
        } else {
            boolean is_open = state.get(OPEN);
            world.setBlockState(pos, state.with(OPEN, !is_open));
            world.playSound(player, pos, MoreThingsSounds.SACK_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
    }
}