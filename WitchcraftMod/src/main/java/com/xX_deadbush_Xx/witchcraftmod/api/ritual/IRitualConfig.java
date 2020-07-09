package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;
import net.minecraft.world.IWorldReader;

public interface IRitualConfig {

	boolean matchesAnchorblocks(NonNullList<Block> blocks);
	
	boolean matchesTotems(IWorldReader world);
}
