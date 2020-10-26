package com.xX_deadbush_Xx.hocus.api.util;

import net.minecraft.util.math.BlockPos;

public class MiscUtil {
	
	public static String prettyPrint(BlockPos pos) {
		return String.format("X: %d, Y: %d, Z: %d", pos.getX(), pos.getY(), pos.getZ());
	}
}
