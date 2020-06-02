package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;

public class MediumRitualConfig implements IRitualConfig {
	Logger LOGGER;
	private final NonNullList<Block> junctionBlocks;
	
	public MediumRitualConfig(Block... junctionBlocks) { 
		if(junctionBlocks.length != 8) {
			LOGGER.warning("Small ritual config did NOT receive eight junction blocks!");
		}
		
		NonNullList<Block> list = NonNullList.create();
		for(Block block : junctionBlocks){
			list.add(block);
		}
		this.junctionBlocks = list;
	}
	
	@Override
	public boolean matches(NonNullList<Block> blocks) { 
		if(blocks.size() != 8) return false; 
		
		blockloop:
		for(int i = 0; i < 8; i+=2) {
			if(blocks.get(0).getRegistryName() == this.junctionBlocks.get(i).getRegistryName()) {
				for(int j = 1; j < 8; j++) {
					if (blocks.get(j).getRegistryName() != this.junctionBlocks.get((i + j)%8).getRegistryName()) continue blockloop;
				}
				return true;
			}
		}	
		return false;
	}
}