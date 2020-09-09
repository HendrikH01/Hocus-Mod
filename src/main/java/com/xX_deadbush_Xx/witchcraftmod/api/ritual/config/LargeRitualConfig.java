package com.xX_deadbush_Xx.witchcraftmod.api.ritual.config;

import java.util.List;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;
import net.minecraft.world.IWorldReader;

public class LargeRitualConfig implements IRitualConfig {
	Logger LOGGER;
	private final NonNullList<Block> anchorblocks;
	
	public LargeRitualConfig(Block... anchorblocks) { 
		if(anchorblocks.length != 8) {
			LOGGER.warning("Large ritual config did NOT receive eight anchor blocks!");
		}
		
		NonNullList<Block> list = NonNullList.create();
		for(Block block : anchorblocks){
			list.add(block);
		}
		this.anchorblocks = list;
	}

	@Override
	public boolean matchesTotems(IWorldReader world) {
		return false;
	}

	@Override
	public List<Block> getAnchorBlocks() {
		return this.anchorblocks;
	}
}