package com.xX_deadbush_Xx.witchcraftmod.client.effect;

import java.util.ArrayList;
import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.client.effect.effects.GrowthRitualEffect;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.effects.IParticleEffect;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.effects.PedestalDisappearEffect;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.effects.RitualItemCreateEffect;

import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ClientParticleHandler {
	private static List<IParticleEffect> effects = new ArrayList<>();
	
	@SubscribeEvent
	public static void worldTick(WorldTickEvent event) {
		for(int i = 0; i < effects.size(); i++) {
			if(effects.get(i) != null) {
				effects.get(i).tick();
			}
		}
	}
	
	public static void addEffect(EffectType type, World worldIn, double x, double y, double z, float... args) {
		IParticleEffect effect = getEffectObject(type, worldIn, x, y, z, args);
		effects.add(effect);
		effect.tick();

	}
	
	public static IParticleEffect getEffectObject(EffectType type, World worldIn, double x, double y, double z, float... args) {
		switch(type) {
			case PEDESTAL_DISAPPEAR: return new PedestalDisappearEffect(worldIn, x, y, z, args);
			case RITUAL_ITEM_CREATE: return new RitualItemCreateEffect(worldIn, x, y, z, args);
			case GROWTH_RITUAL: return new GrowthRitualEffect(worldIn, x, y, z, args);
		}
		return null;
	}
	
	public static void remove(IParticleEffect effect) {
		effects.remove(effect);
	}
	
	public enum EffectType {
		PEDESTAL_DISAPPEAR,
		RITUAL_ITEM_CREATE, 
		GROWTH_RITUAL;
	}
}
