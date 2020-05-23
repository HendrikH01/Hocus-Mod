package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;

public interface IRitualConfig {

	boolean matches(NonNullList<Block> blocks);

}
