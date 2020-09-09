package com.xX_deadbush_Xx.witchcraftmod.api.ritual.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;
import net.minecraft.world.IWorldReader;

public interface IRitualConfig {

	default boolean matchesAnchorblocks(NonNullList<Block> blocks) {
		List<Block> missing = new ArrayList<>(getAnchorBlocks());
		
		outer:
        for (int i = getAnchorBlocks().size() - 1; i >= 0; i--) {
            for (int j = 0; j < missing.size(); j++) {
                if (blocks.get(i).equals(missing.get(j))) {
                	missing.remove(j);
                	continue outer;
                }
            }
            return false;
            
        }
        return true;
	}
	
	List<Block> getAnchorBlocks();
	
	boolean matchesTotems(IWorldReader world);
}
