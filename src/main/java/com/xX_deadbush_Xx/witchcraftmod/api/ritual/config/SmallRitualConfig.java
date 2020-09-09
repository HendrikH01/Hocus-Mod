package com.xX_deadbush_Xx.witchcraftmod.api.ritual.config;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.world.IWorldReader;

public class SmallRitualConfig implements IRitualConfig {
	Logger LOGGER;
	private final NonNullList<Block> anchorblocks;
	
	public SmallRitualConfig(Block... anchorblocks) { //S-W-N-E
		if(anchorblocks.length != 4) {
			LOGGER.warning("Small ritual config did NOT receive four anchor blocks!");
		}
		
		NonNullList<Block> list = NonNullList.create();
		for(Block block : anchorblocks){
			list.add(block);
		}
		this.anchorblocks = list;
	}

	@Override
	public boolean matchesTotems(IWorldReader world) {
		return true;
	}

	@Override
	public List<Block> getAnchorBlocks() {
		return this.anchorblocks;
	}
}
