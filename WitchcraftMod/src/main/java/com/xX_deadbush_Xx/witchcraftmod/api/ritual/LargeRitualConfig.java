package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

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
	public boolean matchesAnchorblocks(NonNullList<Block> anchorblocksIn) { 
		if(anchorblocksIn.size() != 8) return false; 
		
		blockloop:
		for(int i = 0; i < 8; i+=2) {
			if(anchorblocksIn.get(0).getRegistryName() == this.anchorblocks.get(i).getRegistryName()) {
				for(int j = 1; j < 8; j++) {
					if (anchorblocksIn.get(j).getRegistryName() != this.anchorblocks.get((i + j)%8).getRegistryName()) continue blockloop;
				}
				return true;
			}
		}	
		return false;
	}

	@Override
	public boolean matchesTotems(IWorldReader world) {
		return false;
	}
}