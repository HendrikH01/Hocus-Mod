package com.xX_deadbush_Xx.witchcraftmod.api.util;

import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class RitualHelper {
	public static boolean isChalk(IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock().equals(ModBlocks.CHALK_BLOCK.get());
	}
}
