package com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate;

import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;

public class ModBlockStateProperties {
	public static final IntegerProperty CANDLES_1_3 = IntegerProperty.create("candlecount", 1, 3);
	public static final EnumProperty<GlowType> GLOW_TYPE = EnumProperty.create("glowtype", GlowType.class);
}
