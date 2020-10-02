package com.xX_deadbush_Xx.witchcraftmod.api.util.helpers;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.TotemPattern;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.IRitualEffect;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.RitualPedestal;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.tags.ModTags;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaStorage;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.TileEntityManaStorage;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class RitualHelper {
	public static boolean isChalk(IWorldReader world, BlockPos pos) {
		return world.getBlockState(pos).getBlock().equals(ModBlocks.CHALK_BLOCK.get());
	}
	
	public static Direction[] getHorizontalDirections() {
		Direction[] directions = {Direction.SOUTH,  Direction.WEST, Direction.NORTH, Direction.EAST};
		return directions;
	}
	
	/**
	 * only call when block is a chalk block!
	 */
	public void setChalkState(BlockPos pos, World world, GlowType type, int power) {
		world.setBlockState(pos, world.getBlockState(pos).with(ChalkBlock.GLOW_TYPE, type).with(ChalkBlock.POWER, power));
	}
	
	/**
	 * Only call when chalk is in place!
	 */
	public static void colorChalk(GlowType type, int power, Set<BlockPos> chalkpositions, World world, BlockPos ritualstonepos) {
		for(BlockPos pos : chalkpositions) {
			int dist = Math.abs(ritualstonepos.getX() - pos.getX()) + Math.abs(ritualstonepos.getZ() - pos.getZ());
			world.setBlockState(pos, world.getBlockState(pos).with(ChalkBlock.POWER, Math.max(0, (int)(power+50/(dist+8)-6.25))).with(ChalkBlock.GLOW_TYPE, type));
		}
	}
	

	public static void colorPedestals(GlowType glowtype, int power, Set<BlockPos> anchorBlocks, World world, BlockPos ritualstonepos) {
		for(BlockPos pos : anchorBlocks) {
			BlockState state = world.getBlockState(pos);
			if(!state.getBlock().equals(ModBlocks.RITUAL_PEDESTAL.get())) continue;
			int dist = Math.abs(ritualstonepos.getX() - pos.getX()) + Math.abs(ritualstonepos.getZ() - pos.getZ());
			world.setBlockState(pos, state.with(RitualPedestal.POWER, Math.max(0, (int)(power+50/(dist+8)-6.25))).with(RitualPedestal.GLOW_TYPE, glowtype));
		}		
	}

	public static boolean stopsRitual(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return state.getBlock().isIn(ModTags.RITUAL_BLOCK) || state.isSolid();
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
		public Set<BlockPos> anchorblocks = new HashSet<>();
		public Set<TotemPattern> totems = new HashSet<>();
	}

	public static boolean hasEnergy(@Nullable TileEntityManaStorage storage, double amount, RitualStoneTile tile, PlayerEntity player) {
		if(storage == null) {
			storage = tile.getCapability(TileEntityManaStorage.getCap()).orElse(new TileEntityManaStorage(0, 0, 0));
		}
		
		if (storage.getEnergy() >= amount)
			return true;
		PlayerManaStorage playerstorage = player.getCapability(PlayerManaStorage.getCap()).orElse(null);
		if (playerstorage != null) {
			return storage.getEnergy() + playerstorage.getEnergy() >= amount;
		}
		return false;
	}

	public static boolean removeEnergy(double amount, RitualStoneTile tile, PlayerEntity player) {
		TileEntityManaStorage storage = tile.getCapability(TileEntityManaStorage.getCap()).orElse(new TileEntityManaStorage(0, 0, 0));		
		if (amount > 0 && hasEnergy(storage, amount, tile, player)) {
			amount -= storage.extractEnergy(amount, false);
			if(amount > 0) {
				PlayerManaStorage playerstorage = player.getCapability(PlayerManaStorage.getCap()).orElse(null);
				if(playerstorage != null) playerstorage.removeEnergy(amount);
			}
			
			return true;
		} else
			return false;
	}
}
