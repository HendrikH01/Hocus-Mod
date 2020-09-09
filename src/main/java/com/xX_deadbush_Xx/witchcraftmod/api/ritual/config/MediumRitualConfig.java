package com.xX_deadbush_Xx.witchcraftmod.api.ritual.config;

import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;
import net.minecraft.world.IWorldReader;

import java.util.List;
import java.util.logging.Logger;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.RitualTotem;

public class MediumRitualConfig implements IRitualConfig {
    Logger LOGGER = Logger.getLogger(MediumRitualConfig.class.getName());
    private final NonNullList<Block> anchorblocks;

    public MediumRitualConfig(Block[] anchorblocks, RitualTotem[] totems) {
        if (anchorblocks.length != 8) LOGGER.warning("Medium ritual config did NOT receive eight anchor blocks!");
        if (totems.length != 8) LOGGER.warning("Medium ritual config did NOT receive four totems!");


        NonNullList<Block> list = NonNullList.create();
        for (Block block : anchorblocks) {
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