package com.xX_deadbush_Xx.witchcraftmod.api.ritual.config;

import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.TotemPattern;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.CraftingHelper;

import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;
import net.minecraft.world.IWorldReader;

public class RitualConfig {
	protected final NonNullList<Block> anchorblocks = NonNullList.create();
	protected final NonNullList<Block[]> totems = NonNullList.create();
	public final ConfigType type;
	
	public RitualConfig(ConfigType type) {
		this.type = type;
	}
	
	public boolean matchesAnchorblocks(NonNullList<Block> blocks) {
		return CraftingHelper.checkMatchUnordered(this.anchorblocks, blocks, (w, g) -> w.equals(g));
	}
	
	public boolean matchesTotems(IWorldReader world, List<TotemPattern> totems) {
		return CraftingHelper.checkMatchUnordered(this.totems, totems, (totem, pattern) -> pattern.areBlocksEqual(world, totem));
	}
	
	public RitualConfig addAnchorBlocks(Block... blocks) {
		for(Block b : blocks) this.anchorblocks.add(b);
		return this;	
	}
	
	public RitualConfig addAnchorBlocks(int amount, Block... blocks) {
		for(int i = 0; i < amount; i++)
			for(Block b : blocks)
				this.anchorblocks.add(b);
		
		return this;
	}

	public RitualConfig addTotems(Block[]... blocks) {
		for(Block[] t : blocks) this.totems.add(t);
		return this;
	}
	
	public RitualConfig addTotems(int amount, Block[]... blocks) {
		for(int i = 0; i < amount; i++)
			for(Block[] b : blocks)
				this.totems.add(b);
		return this;
	}
	
	public RitualConfig build() {
		if(this.anchorblocks.size() != this.type.getAnchorblockCount())
			WitchcraftMod.LOGGER.warning("MediumRitualConfig did not receive " + this.type.getAnchorblockCount() + " anchor blocks!");
		if(this.totems.size() != this.type.getTotemCount())
			WitchcraftMod.LOGGER.warning("MediumRitualConfig did not receive " + this.type.getTotemCount() + " totems!");
		return this;
	}}
