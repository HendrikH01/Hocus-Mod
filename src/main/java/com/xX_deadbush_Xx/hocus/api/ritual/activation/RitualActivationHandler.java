package com.xX_deadbush_Xx.hocus.api.ritual.activation;

import java.util.List;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.hocus.api.ritual.IRitual;
import com.xX_deadbush_Xx.hocus.api.ritual.LargeRitual;
import com.xX_deadbush_Xx.hocus.api.ritual.MediumRitual;
import com.xX_deadbush_Xx.hocus.api.ritual.RitualTier;
import com.xX_deadbush_Xx.hocus.api.ritual.SmallRitual;
import com.xX_deadbush_Xx.hocus.api.ritual.config.ConfigType;
import com.xX_deadbush_Xx.hocus.api.ritual.config.RitualConfig;
import com.xX_deadbush_Xx.hocus.api.util.RitualHelper;
import com.xX_deadbush_Xx.hocus.common.register.RitualRegistry;
import com.xX_deadbush_Xx.hocus.common.tile.RitualStoneTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.NonNullList;

public class RitualActivationHandler {
	public static IRitual getRitual(RitualStoneTile tile, PlayerEntity player) {
		 RitualTier tier = determineTier(tile);
		 System.out.println("Tier: " + tier);
		 if(tier == null) return null;
		 
		 Block[] junctionblocks = getJunctionBlocks(tile, tier).stream().map(s -> s.getBlock()).toArray(Block[]::new);
		 return createRitualFromJunctionBlocks(tier, NonNullList.from(null, junctionblocks), tile, player);
	}
	
	private static RitualTier determineTier(RitualStoneTile tile) {		 
		if(RitualHelper.isChalk(tile.getWorld(), tile.getPos().north())) {
			if(RitualHelper.isChalk(tile.getWorld(), tile.getPos().north(3))) {
				if(RitualHelper.isChalk(tile.getWorld(), tile.getPos().north(6))) {
					return RitualTier.LARGE;
				} else return RitualTier.MEDIUM;
			} else return RitualTier.SMALL;
		} else return null;
	}
	
	@SuppressWarnings("resource")
	private static List<BlockState> getJunctionBlocks(RitualStoneTile tile, RitualTier tier) {
		switch(tier) {
		case SMALL: {
			return SmallRitual.getRitualPositions(tile.getWorld(), tile.getPos()).anchorblocks.stream().map(tile.getWorld()::getBlockState).collect(Collectors.toList());
		}
		case MEDIUM: {
			return MediumRitual.getRitualPositions(tile.getWorld(), tile.getPos()).anchorblocks.stream().map(tile.getWorld()::getBlockState).collect(Collectors.toList());
		}
		case LARGE: {
			return LargeRitual.getRitualPositions(tile.getWorld(), tile.getPos()).anchorblocks.stream().map(tile.getWorld()::getBlockState).collect(Collectors.toList());
		}
		default: return null;
		}
	}
	
	private static IRitual createRitualFromJunctionBlocks(RitualTier tier, NonNullList<Block> blocks, RitualStoneTile tile, PlayerEntity player){
		ConfigType type;
		
		switch(tier) {
			case SMALL: type = ConfigType.SMALL; break;
			case MEDIUM: type = ConfigType.MEDIUM; break;
			case LARGE: type = ConfigType.LARGE; break;
			default: return null;
		}
		
		System.out.println(tier + " " + type);
		for (RitualConfig config : RitualRegistry.getConfigs().stream().filter(config -> {
			return config.type == type;
		}).collect(Collectors.toList())) {
			System.out.println(config.toString() + " " + config.getClass());
			if(config.matchesAnchorblocks(blocks)) return RitualRegistry.create(RitualRegistry.getIdFromConfig(config), tile, player);
		}
		System.out.println("No ritual found matching blocks: " + blocks);
		return null;
	}
}