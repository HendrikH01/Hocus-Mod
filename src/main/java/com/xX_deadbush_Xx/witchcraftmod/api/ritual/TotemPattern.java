package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.function.BiPredicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

//aka pillar
public class TotemPattern {
	private final BlockPos[] positions;
	
	public TotemPattern(BlockPos... pos) { //starting at the bottom
		this.positions = pos;
	}
	
	public BlockPos getBottomPos() {
		return this.positions[0];
	}
	
	public BlockState getTop(IWorldReader worldIn) {
		return worldIn.getBlockState(this.positions[this.positions.length - 1]);
	}
	
	public boolean areBlocksEqual(IWorldReader world, Block[] blocks) {
		if(positions.length != blocks.length) return false;
		else return checkMatch(world, (index, state) -> {
			return state.getBlock().equals(blocks[index]);
		});
	}
	
	public boolean areBlockstatesEqual(IWorldReader world, BlockState[] states) {
		if(positions.length != states.length) return false;
		else return checkMatch(world, (index, state) -> state.equals(states[index]));
	}
	
	private boolean checkMatch(IWorldReader world, BiPredicate<Integer, BlockState> comp) {
		for(int i = 0; i < this.positions.length; i++) {
			BlockPos pos = positions[i];
			BlockState b = world.getBlockState(pos);
			if(!comp.test(i, b)) return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		String out = "TotemPattern: [";
		for(BlockPos p : this.positions) {
			out += p.toString();
		}
		out += "]";
		return out;
	}
}