package com.xX_deadbush_Xx.witchcraftmod.api.util.helpers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.AbstractSmallRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.RitualTier;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.IRitualEffect;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.IRitualBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RitualHelper {
	public static boolean isChalk(IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock().equals(ModBlocks.CHALK_BLOCK.get());
	}
	
	public static Direction[] getHorizontalDirections() {
		Direction[] directions = {Direction.SOUTH,  Direction.WEST, Direction.NORTH, Direction.EAST};
		return directions;
	}
	
	/**
	 * only call when block is a chalk block!
	 */
	public void setChalkState(BlockPos pos, World worldIn, GlowType type, int power) {
		worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(ChalkBlock.GLOW_TYPE, type).with(ChalkBlock.POWER, power));
	}
	
	/**
	 * Only call when chalk is in place!
	 */
	public static void colorChalk(GlowType type, int power, List<BlockPos> chalkpositions, World worldIn, BlockPos ritualstonepos) {
		for(BlockPos pos : chalkpositions) {
			int dist = Math.abs(ritualstonepos.getX() - pos.getX()) + Math.abs(ritualstonepos.getZ() - pos.getZ());
			worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(ChalkBlock.POWER, Math.max(0, power-dist)).with(ChalkBlock.GLOW_TYPE, type));
		}
	}

	public static boolean stopsRitual(World worldIn, BlockPos pos) {
		BlockState state = worldIn.getBlockState(pos);
		return state.getBlock() instanceof IRitualBlock || state.isSolid();
	}
	
	public static Class<? extends IRitual> getAbstractClassFromTier(RitualTier tier) {
		Class<? extends IRitual> clazz;
		switch(tier) {
			case SMALL: {
				clazz = AbstractSmallRitual.class;
				break;
			}
			default: return null;
		}
		return clazz;
	}
	
	public static Map<String, List<BlockPos>> getRitualPositionsSmall(IWorldReader worldIn, BlockPos pos) {
		List<BlockPos> chalkpositions = new ArrayList<>();
		List<BlockPos> nonRitualBlocks = new ArrayList<>();
		List<BlockPos> junctionBlocks = new ArrayList<>();

		for(Direction direction : getHorizontalDirections()) {
			Direction left = direction.rotateYCCW(); Direction right = direction.rotateY();
			BlockPos pos1 = pos.offset(direction);
			BlockPos pos2 = pos1.offset(direction);
			chalkpositions.add(pos2.offset(left));
			chalkpositions.add(pos2.offset(right)); 
			chalkpositions.add(pos1); chalkpositions.add(pos2);

			nonRitualBlocks.add(pos1.offset(right));
			nonRitualBlocks.add(pos1.offset(left));
			BlockPos pos3 = pos2.offset(right, 2).offset(direction);
			nonRitualBlocks.add(pos3);
			for(int i = 1; i < 5; i++) nonRitualBlocks.add(pos3.offset(left, i));
			
			junctionBlocks.add(pos2.offset(right, 2));
		}
		
		Map<String, List<BlockPos>> out = new HashMap<>();
		out.put("chalk", chalkpositions); out.put("nonritual", nonRitualBlocks); out.put("junctionblocks", junctionBlocks);
		return out;
	}
	
	public static class PrioritySorter implements Comparator<IRitualEffect> {
		@Override
		public int compare(IRitualEffect effect1, IRitualEffect effect2) {
			return effect1.getPriority() - effect2.getPriority();
		}
	}
}
