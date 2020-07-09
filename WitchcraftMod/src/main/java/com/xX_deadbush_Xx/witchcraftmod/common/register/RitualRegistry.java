package com.xX_deadbush_Xx.witchcraftmod.common.register;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IRitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.RitualTier;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.rituals.medium.MediumFusionRitual;
import com.xX_deadbush_Xx.witchcraftmod.common.rituals.small.SmallAnimalGrowthRitual;
import com.xX_deadbush_Xx.witchcraftmod.common.rituals.small.SmallFusionRitual;
import com.xX_deadbush_Xx.witchcraftmod.common.rituals.small.SmallGrowthRitual;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.entity.player.PlayerEntity;

public class RitualRegistry {
	public static RitualRegistry INSTANCE = new RitualRegistry();
	private BiMap<String, Class<? extends IRitual>> names = HashBiMap.create();
	private List<Class<? extends IRitual>> rituals = new ArrayList<>();
	private BiMap<String, IRitualConfig> configs = HashBiMap.create();
	
	private void register(Class<? extends IRitual> ritual, IRitualConfig config, String name) {
		if(this.names.keySet().contains(name)) throw new IllegalArgumentException("Ritual with the same name registered twice: " + name); 

		this.rituals.add(ritual);
		this.names.put(name, ritual);
		this.configs.put(name, config);
	}
	
	public static Class<? extends IRitual> getByID(int id) {
		return INSTANCE.rituals.get(id);
	}
	
	public static Class<? extends IRitual> getByName(String name) {
		return INSTANCE.names.get(name);
	}
	
	public static String getName(IRitual ritual) {
		return INSTANCE.names.inverse().get(ritual.getClass());
	}
	
	public static List<Class<? extends IRitual>> getRituals() {
		return INSTANCE.rituals;
	}
	
	public static IRitual create(String name, AbstractRitualCore tile, PlayerEntity player) {
		Class<? extends IRitual> clazz = getByName(name);
		try {
			Constructor c = clazz.getConstructors()[0];
			System.out.println(name + " " + c.getParameters()[0].getName() + " " + c.getParameterTypes()[0].getName()+ " " + c.getParameterTypes()[1].getName());
			return (IRitual) clazz.getConstructor(AbstractRitualCore.class, PlayerEntity.class).newInstance(tile, (PlayerEntity)player);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Class<? extends IRitual>> getRitualsFromTier(RitualTier tier) {
		Class<? extends IRitual> abstractritualclass = RitualHelper.getAbstractClassFromTier(tier);
		return this.rituals.stream().filter(r -> abstractritualclass.isInstance(r)).collect(Collectors.toList());
	}
	
	public void registerRituals() {
		//SMALL
		register(SmallGrowthRitual.class, SmallGrowthRitual.config, "small_growth");
		register(SmallAnimalGrowthRitual.class, SmallAnimalGrowthRitual.config, "small_animal_growth");
		register(SmallFusionRitual.class, SmallFusionRitual.config,"small_fusion");
		//MEDIUM
		register(MediumFusionRitual.class, MediumFusionRitual.config,"medium_fusion");
	}

	public static Set<IRitualConfig> getConfigs() {
		return INSTANCE.configs.values();
	}

	public static String getConfigName(IRitualConfig config) {
		return INSTANCE.configs.inverse().get(config);
	}
}