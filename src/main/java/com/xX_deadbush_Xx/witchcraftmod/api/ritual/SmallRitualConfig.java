package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.Collection;
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
	public boolean matchesAnchorblocks(NonNullList<Block> anchorblocksIn) { //S-W-N-E
		if(anchorblocksIn.size() != 4) return false; 
		
		for(int i = 0; i < 4; i++) {
			if(anchorblocksIn.get(0).getRegistryName() == this.anchorblocks.get(i).getRegistryName()) {
				if(
					anchorblocksIn.get(1).getRegistryName() == this.anchorblocks.get((i + 1)%4).getRegistryName()	&&
					anchorblocksIn.get(2).getRegistryName() == this.anchorblocks.get((i + 2)%4).getRegistryName()	&&
					anchorblocksIn.get(3).getRegistryName() == this.anchorblocks.get((i + 3)%4).getRegistryName()
				) return true;
			}
		}	
		return false;
	}

	@Override
	public boolean matchesTotems(IWorldReader world) {
		return true;
	}
}
