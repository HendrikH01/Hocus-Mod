package com.xX_deadbush_Xx.witchcraftmod.common.register;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.IRitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.common.rituals.medium.MediumFusionRitual;
import com.xX_deadbush_Xx.witchcraftmod.common.rituals.small.SmallAnimalGrowthRitual;
import com.xX_deadbush_Xx.witchcraftmod.common.rituals.small.SmallFusionRitual;
import com.xX_deadbush_Xx.witchcraftmod.common.rituals.small.SmallGrowthRitual;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class RitualRegistry {
	public static RitualRegistry INSTANCE = new RitualRegistry();
	private BiMap<Integer, ResourceLocation> names = HashBiMap.create();
	private BiMap<Integer, IRitualConfig> configs = HashBiMap.create();
	private Map<Integer, IFactory> factories = new HashMap<>();
	private static int id = 0;
	
	private void register(ResourceLocation name, IRitualConfig config, IFactory factory) {
		id++;
		if(this.names.values().contains(name)) throw new IllegalArgumentException("Ritual with the same name registered twice: " + name); 
		this.factories.put(id, factory);
		this.names.put(id, name);
		this.configs.put(id, config);
	}
	
	private void register(String name, IRitualConfig config, IFactory factory) {
		register(new ResourceLocation(WitchcraftMod.MOD_ID, name), config, factory);
	}
	
	public static IFactory getRitual(int id) {
		return INSTANCE.factories.get(id);
	}
	
	public static ResourceLocation getName(int id) {
		return INSTANCE.names.get(id);
	}
	
	public static IRitual create(int id, AbstractRitualCore tile, PlayerEntity player) {
		return INSTANCE.factories.get(id).create(tile, player);
	}
	
	public void registerRituals() {
		//SMALL
		register("small_growth_ritual", SmallGrowthRitual.config, SmallGrowthRitual::new);
		register("small_animal_growth_ritual", SmallAnimalGrowthRitual.config, SmallAnimalGrowthRitual::new);
		register("small_fusion_ritual", SmallFusionRitual.config, SmallFusionRitual::new);
		//MEDIUM
		register("medium_growth_ritual", MediumFusionRitual.config, MediumFusionRitual::new);
	}

	public static Set<IRitualConfig> getConfigs() {
		return INSTANCE.configs.values();
	}

	public static int getIdFromConfig(IRitualConfig config) {
		return INSTANCE.configs.inverse().get(config);
	}
	
	@FunctionalInterface
	private interface IFactory {
		public IRitual create(AbstractRitualCore tile, PlayerEntity player);
	}
}