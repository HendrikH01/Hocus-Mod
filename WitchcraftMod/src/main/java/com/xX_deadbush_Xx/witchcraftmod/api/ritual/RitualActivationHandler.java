package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.List;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;

public class RitualActivationHandler {
	public static IRitual getRitual(RitualStoneTile tile, PlayerEntity player) {
		 RitualTier tier = determineTier(tile);
		 if(tier == null) return null;
		 
		 Block[] junctionblocks = getJunctionBlocks(tile, tier).stream().map(s -> s.getBlock()).toArray(Block[]::new);
		 return createRitualFromJunctionBlocks(tier, NonNullList.from(null, junctionblocks), tile, player);
	}
	
	private static RitualTier determineTier(RitualStoneTile tile) {
		if(RitualHelper.isChalk(tile.getWorld(), tile.getPos().offset(Direction.NORTH))) {
			if(RitualHelper.isChalk(tile.getWorld(), tile.getPos().offset(Direction.NORTH, 3))) {
				if(RitualHelper.isChalk(tile.getWorld(), tile.getPos().offset(Direction.NORTH, 6))) {
					return RitualTier.LARGE;
				} else return RitualTier.MEDIUM;
			} else return RitualTier.SMALL;
		} else return null;
	}
	
	private static List<BlockState> getJunctionBlocks(RitualStoneTile tile, RitualTier tier) {
		switch(tier) {
		case SMALL: {
			return RitualHelper.getRitualPositionsSmall(tile.getWorld(), tile.getPos()).get("junctionblocks").stream().map(tile.getWorld()::getBlockState).collect(Collectors.toList());
		}
		case MEDIUM: {
			
		}
		case LARGE: {
			
		}
		default: return null;
		}
	}
	
	private static IRitual createRitualFromJunctionBlocks(RitualTier tier, NonNullList<Block> blocks, RitualStoneTile tile, PlayerEntity player){
		Class<? extends IRitualConfig> clazz;
		switch(tier) {
		case SMALL: clazz = SmallRitualConfig.class; break;
		case MEDIUM: clazz = SmallRitualConfig.class; break;
		case LARGE: clazz = SmallRitualConfig.class; break;
		default: return null;
		}
		
		for(IRitualConfig config : RitualRegistry.getConfigs().stream().filter(clazz::isInstance).collect(Collectors.toList())) {
			if(config.matches(blocks)) return RitualRegistry.create(RitualRegistry.getConfigName(config), tile, player);
		}
		return null;
	}
}
