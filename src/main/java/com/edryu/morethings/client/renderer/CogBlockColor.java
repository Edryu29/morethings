package com.edryu.morethings.client.renderer;

import com.edryu.morethings.block.CogBlock;

import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.util.math.MathHelper;

public class CogBlockColor implements BlockColorProvider{

    private static final int[] COLORS = new int[16];

    static {
        for (int i = 0; i <= 15; ++i) {
            float f =  i / 15.0F;
            float f1 = f * 0.5F + (f > 0.0F ? 0.5F : 0.3F);
            float f2 = MathHelper.clamp(f * f * 0.5F - 0.3F, 0.0F, 1.0F);
            float f3 = MathHelper.clamp(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
            COLORS[i] = MathHelper.packRgb(f1, f2, f3);
        }
    }

    @Override
    public int getColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        return tintIndex != 1 ? -1 : COLORS[state.get(CogBlock.POWER)];
    }
}