package com.edryu.morethings.block;

import org.jetbrains.annotations.Nullable;

import com.edryu.morethings.MoreThingsRegister;
import com.edryu.morethings.MoreThingsSounds;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BuntingBlock extends HorizontalFacingBlock {
    public static final MapCodec<BuntingBlock> CODEC = Block.createCodec(BuntingBlock::new);

	public static final IntProperty VARIANT = IntProperty.of("variant", 0, 15);
	public static final Item[] BUNTING_COLORS = {
        Items.BLACK_DYE,        //0     
        Items.BLUE_DYE,         //1  
        Items.BROWN_DYE,        //2      
        Items.CYAN_DYE,         //3  
        Items.GRAY_DYE,         //4  
        Items.GREEN_DYE,        //5      
        Items.LIGHT_BLUE_DYE,   //6          
        Items.LIGHT_GRAY_DYE,   //7        
        Items.LIME_DYE,         //8         
        Items.MAGENTA_DYE,      //9  
        Items.ORANGE_DYE,       //10      
        Items.PINK_DYE,         //11     
        Items.PURPLE_DYE,       //12      
        Items.RED_DYE,          //13 
        Items.WHITE_DYE,        //14     
        Items.YELLOW_DYE        //15
    };         

    public BuntingBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(VARIANT, 0));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld || player == null) {
            return ActionResult.PASS;

        } else {
            for (int i = 0; i < BUNTING_COLORS.length; i++) {
                if (player.isHolding(BUNTING_COLORS[i])){
                    world.setBlockState(pos, state.with(VARIANT, i));
                    world.playSound(player, pos, MoreThingsSounds.ROPE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
            }

            if (player.isHolding(MoreThingsRegister.ORB)){
                world.setBlockState(pos, state.with(VARIANT, getRandomVariant()));
                world.playSound(player, pos, MoreThingsSounds.ROPE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

	@Override
	protected MapCodec<? extends BuntingBlock> getCodec() {
		return CODEC;
	}

	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos, state.with(VARIANT, getRandomVariant()));
	}

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING, VARIANT);
    }

    protected int getRandomVariant() {
        return (int)(Math.random() * 16);
    }
}