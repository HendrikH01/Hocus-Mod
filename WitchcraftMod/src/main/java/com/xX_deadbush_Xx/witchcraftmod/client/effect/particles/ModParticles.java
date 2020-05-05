package com.xX_deadbush_Xx.witchcraftmod.client.effect.particles;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;


@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WitchcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles {	
	@ObjectHolder("minecraft:flame") public static BasicParticleType SMALL_FLAME;
	
	@SubscribeEvent
	public static void registerParticles(RegistryEvent.Register<ParticleType<?>> evt) {
		IForgeRegistry<ParticleType<?>> registry = evt.getRegistry();
		registry.register(new BasicParticleType(true).setRegistryName("minecraft", "flame"));
	}
	
	@SubscribeEvent
	public static void registerFactories(ParticleFactoryRegisterEvent evt) {
		Minecraft.getInstance().particles.registerFactory(SMALL_FLAME, SmallFlameParticle.Factory::new);
	}
}