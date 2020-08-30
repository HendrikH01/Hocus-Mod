package com.xX_deadbush_Xx.witchcraftmod.api.util.helpers;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.AbstractMediumRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.AbstractSmallRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.RitualTier;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.IRitualEffect;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.tags.ModTags;

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
	public static void colorChalk(GlowType type, int power, Set<BlockPos> chalkpositions, World worldIn, BlockPos ritualstonepos) {
		for(BlockPos pos : chalkpositions) {
			int dist = Math.abs(ritualstonepos.getX() - pos.getX()) + Math.abs(ritualstonepos.getZ() - pos.getZ());
			worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(ChalkBlock.POWER, Math.max(0, (int)(power+50/(dist+8)-6.25))).with(ChalkBlock.GLOW_TYPE, type));
		}
	}

	public static boolean stopsRitual(World worldIn, BlockPos pos) {
		BlockState state = worldIn.getBlockState(pos);
		return state.getBlock().isIn(ModTags.RITUAL_BLOCK) || state.isSolid();
	}
	
	public static Class<? extends IRitual> getAbstractClassFromTier(RitualTier tier) {
		Class<? extends IRitual> clazz;
		switch(tier) {
			case SMALL: {
				clazz = AbstractSmallRitual.class;
				break;
			}
			case MEDIUM: {
				clazz = AbstractMediumRitual.class;
				break;
			}
			default: return null;
		}
		return clazz;
	}
	
	public static RitualPositionHolder getRitualPositionsLarge(IWorldReader worldIn, BlockPos pos) {
		RitualPositionHolder out = new RitualPositionHolder();

		for(Direction direction : getHorizontalDirections()) {
			Direction right = direction.rotateY(); Direction back = direction.getOpposite();
			BlockPos pos1 = pos.offset(direction);
			BlockPos pos2 = pos.offset(direction, 2);
			BlockPos pos3 = pos.offset(direction, 3);
			BlockPos pos4 = pos.offset(direction, 4);
			BlockPos pos5 = pos.offset(direction, 5);
			BlockPos pos6 = pos.offset(direction, 6);
			BlockPos pos7 = pos.offset(direction, 7);
			BlockPos pos8 = pos.offset(direction, 8);
			BlockPos pos9 = pos.offset(direction, 9);
			BlockPos pos10 = pos5.offset(right, 5);

			out.chalkpositions.add(pos1); out.chalkpositions.add(pos2); out.chalkpositions.add(pos3); out.chalkpositions.add(pos4); out.chalkpositions.add(pos6); out.chalkpositions.add(pos7); out.chalkpositions.add(pos8);
			out.junctionBlocks.add(pos5); out.junctionBlocks.add(pos2.offset(right, 2));
			out.totems.add(new BlockPos[]{pos10, pos10.up()});
			out.totems.add(new BlockPos[]{pos10.offset(direction, 3), pos10.offset(direction, 3).up(), pos10.offset(direction, 3).up(2)});
			out.totems.add(new BlockPos[]{pos10.offset(right, 3), pos10.offset(right, 3).up(), pos10.offset(right, 3).up(2)});

			out.chalkpositions.add(pos1.offset(right, 2));
			out.chalkpositions.add(pos2.offset(right));
			
			for(int i = 1; i < 5; i++) {
				out.chalkpositions.add(pos5.offset(right, i));
				out.chalkpositions.add(pos8.offset(right, i));
				out.chalkpositions.add(pos10.offset(back, i));
				
				out.nonRitualBlocks.add(pos3.offset(right, i));
				out.nonRitualBlocks.add(pos4.offset(right, i));
				out.nonRitualBlocks.add(pos6.offset(right, i));
				out.nonRitualBlocks.add(pos7.offset(right, i));
			}
			
			out.nonRitualBlocks.add(pos2.offset(right, 3));
			out.nonRitualBlocks.add(pos2.offset(right, 4));				
			out.nonRitualBlocks.add(pos1.offset(right, 3));
			out.nonRitualBlocks.add(pos1.offset(right, 4));
			for(int i = -5; i < 5; i++) {
				if(i != 0) out.nonRitualBlocks.add(pos6.offset(right, i));
				out.nonRitualBlocks.add(pos9.offset(right, i));
			}
		}
		return out;
	}
	
	public static RitualPositionHolder getRitualPositionsMedium(IWorldReader worldIn, BlockPos pos) {
		RitualPositionHolder out = new RitualPositionHolder();

		for(Direction direction : getHorizontalDirections()) {
			Direction right = direction.rotateY(); Direction back = direction.getOpposite();
			BlockPos pos1 = pos.offset(direction);
			BlockPos pos2 = pos.offset(direction, 2);
			BlockPos pos3 = pos.offset(direction, 3);
			BlockPos pos4 = pos.offset(direction, 4);
			BlockPos pos5 = pos.offset(direction, 5);
			BlockPos pos6 = pos.offset(direction, 6);
			BlockPos pos7 = pos5.offset(right, 5);

			out.chalkpositions.add(pos1); out.chalkpositions.add(pos2); out.chalkpositions.add(pos3); out.chalkpositions.add(pos4);
			out.junctionBlocks.add(pos5); out.junctionBlocks.add(pos2.offset(right, 2));
			out.totems.add(new BlockPos[]{pos7, pos7.up()});

			out.chalkpositions.add(pos1.offset(right, 2));
			out.chalkpositions.add(pos2.offset(right));
			for(int i = 1; i < 5; i++) {
				out.chalkpositions.add(pos5.offset(right, i));
				out.chalkpositions.add(pos7.offset(back, i));
				
				out.nonRitualBlocks.add(pos3.offset(right, i));
				out.nonRitualBlocks.add(pos4.offset(right, i));
			}
			out.nonRitualBlocks.add(pos2.offset(right, 3));
			out.nonRitualBlocks.add(pos2.offset(right, 4));				
			out.nonRitualBlocks.add(pos1.offset(right, 3));
			out.nonRitualBlocks.add(pos1.offset(right, 4));
			for(int i = -5; i < 5; i++) out.nonRitualBlocks.add(pos6.offset(right, i));
		}
		return out;
	}
	
	public static RitualPositionHolder getRitualPositionsSmall(World world, BlockPos pos) {
		RitualPositionHolder out = new RitualPositionHolder();
		
		for(Direction direction : getHorizontalDirections()) {
			Direction right = direction.rotateY();
			BlockPos pos1 = pos.offset(direction);
			BlockPos pos2 = pos.offset(direction, 2);
			BlockPos pos3 = pos.offset(direction, 3);

			out.chalkpositions.add(pos2.offset(right));
			out.chalkpositions.add(pos1.offset(right, 2)); 
			out.chalkpositions.add(pos1); out.chalkpositions.add(pos2);

			out.nonRitualBlocks.add(pos1.offset(right));
			for(int i = -2; i < 2; i++) out.nonRitualBlocks.add(pos3.offset(right, i));
			
			out.junctionBlocks.add(pos2.offset(right, 2));
		}
		return out;
	}
	
	public static class PrioritySorter implements Comparator<IRitualEffect> {
		@Override
		public int compare(IRitualEffect effect1, IRitualEffect effect2) {
			return effect1.getPriority() - effect2.getPriority();
		}
	}
	
	public static class RitualPositionHolder {
		public Set<BlockPos> chalkpositions = new HashSet<>();
		public Set<BlockPos> nonRitualBlocks = new HashSet<>();
		public Set<BlockPos> junctionBlocks = new HashSet<>();
		public Set<BlockPos[]> totems = new HashSet<>();
	}
}
