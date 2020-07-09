package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

//aka pillar
public class RitualTotem {
	private BlockPos pos;
	private BlockState[] states;
	
	public RitualTotem(BlockPos bottomPos, BlockState... states) { //starting at the bottom
		this.pos = bottomPos;
		this.states = states;
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
	public boolean isComplete(IWorldReader worldIn) {
		for(int i = 0; i < this.states.length; i++) {
			if(this.states[i] != worldIn.getBlockState(this.pos.up(i))) return false;
		}
		return true;
	}
	
	public BlockState getTop(IWorldReader worldIn) {
		return worldIn.getBlockState(this.pos.up(this.states.length-1));
	}
}