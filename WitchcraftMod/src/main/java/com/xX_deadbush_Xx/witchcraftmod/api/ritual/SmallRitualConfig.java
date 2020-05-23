package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.Collection;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;

public class SmallRitualConfig implements IRitualConfig {
	Logger LOGGER;
	private final NonNullList<Block> junctionBlocks;
	
	public SmallRitualConfig(Block... junctionBlocks) { //S-W-N-E
		if(junctionBlocks.length != 4) {
			LOGGER.warning("Small ritual config did NOT receive four junction blocks!");
		}
		
		NonNullList<Block> list = NonNullList.create();
		for(Block block : junctionBlocks){
			list.add(block);
		}
		this.junctionBlocks = list;
	}
	
	@Override
	public boolean matches(NonNullList<Block> blocks) { //S-W-N-E
		if(blocks.size() != 4) return false; 
		
		for(int i = 0; i < 4; i++) {
			if(blocks.get(0).getRegistryName() == this.junctionBlocks.get(i).getRegistryName()) {
				if(
					blocks.get(1).getRegistryName() == this.junctionBlocks.get((i + 1)%4).getRegistryName()	&&
					blocks.get(2).getRegistryName() == this.junctionBlocks.get((i + 2)%4).getRegistryName()	&&
					blocks.get(3).getRegistryName() == this.junctionBlocks.get((i + 3)%4).getRegistryName()
				) return true;
			}
		}	
		return false;
	}
}
