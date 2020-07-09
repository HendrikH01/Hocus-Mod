package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.List;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.register.RitualRegistry;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;

public class RitualActivationHandler {
	public static IRitual getRitual(AbstractRitualCore tile, PlayerEntity player) {
		 RitualTier tier = determineTier(tile);
		 System.out.println("Tier: " + tier);
		 if(tier == null) return null;
		 
		 Block[] junctionblocks = getJunctionBlocks(tile, tier).stream().map(s -> s.getBlock()).toArray(Block[]::new);
		 return createRitualFromJunctionBlocks(tier, NonNullList.from(null, junctionblocks), tile, player);
	}
	
	private static RitualTier determineTier(AbstractRitualCore tile) {
		System.out.println(tile.getPos().north().toString());
		 
		if(RitualHelper.isChalk(tile.getWorld(), tile.getPos().north())) {
			 System.out.println("1");
			if(RitualHelper.isChalk(tile.getWorld(), tile.getPos().north(3))) {
				 System.out.println("2");
				if(RitualHelper.isChalk(tile.getWorld(), tile.getPos().north(6))) {
					return RitualTier.LARGE;
				} else return RitualTier.MEDIUM;
			} else return RitualTier.SMALL;
		} else return null;
	}
	
	private static List<BlockState> getJunctionBlocks(AbstractRitualCore tile, RitualTier tier) {
		switch(tier) {
		case SMALL: {
			return RitualHelper.getRitualPositionsSmall(tile.getWorld(), tile.getPos()).junctionBlocks.stream().map(tile.getWorld()::getBlockState).collect(Collectors.toList());
		}
		case MEDIUM: {
			return RitualHelper.getRitualPositionsMedium(tile.getWorld(), tile.getPos()).junctionBlocks.stream().map(tile.getWorld()::getBlockState).collect(Collectors.toList());
		}
		case LARGE: {
			
		}
		default: return null;
		}
	}
	
	private static IRitual createRitualFromJunctionBlocks(RitualTier tier, NonNullList<Block> blocks, AbstractRitualCore tile, PlayerEntity player){
		Class<? extends IRitualConfig> clazz;
		switch(tier) {
		case SMALL: clazz = SmallRitualConfig.class; break;
		case MEDIUM: clazz = MediumRitualConfig.class; break;
		case LARGE: clazz = SmallRitualConfig.class; break;
		default: return null;
		}
		System.out.println(tier + " " + clazz);

		for(IRitualConfig config : RitualRegistry.getConfigs().stream().filter(clazz::isInstance).collect(Collectors.toList())) {
			System.out.println(config.toString() + " " + config.getClass());
			if(config.matchesAnchorblocks(blocks)) return RitualRegistry.create(RitualRegistry.getConfigName(config), tile, player);
		}
		System.out.println("No ritual found mathcing blocks: " + blocks);
		return null;
	}
}
