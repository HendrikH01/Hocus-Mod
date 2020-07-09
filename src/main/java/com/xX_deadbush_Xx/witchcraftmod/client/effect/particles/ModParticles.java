package com.xX_deadbush_Xx.witchcraftmod.client.effect.particles;

import java.awt.Color;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data.ScaledColoredParticleData;

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
	@ObjectHolder(WitchcraftMod.MOD_ID + ":shimmer") public static ScaledColoredParticleData SHIMMER;
	@ObjectHolder(WitchcraftMod.MOD_ID + ":lightning") public static ScaledColoredParticleData LIGHTNING;
	@ObjectHolder(WitchcraftMod.MOD_ID + ":manawave") public static ScaledColoredParticleData MANAWAVE;

	@SubscribeEvent
	public static void registerParticles(RegistryEvent.Register<ParticleType<?>> evt) {
		IForgeRegistry<ParticleType<?>> registry = evt.getRegistry();
		registry.register(new BasicParticleType(true).setRegistryName("minecraft", "flame"));
		registry.register(new ScaledColoredParticleData(SHIMMER, true, Color.WHITE.getRGB(), 1).setRegistryName(WitchcraftMod.MOD_ID, "shimmer"));
		registry.register(new ScaledColoredParticleData(LIGHTNING, true, Color.WHITE.getRGB(), 1).setRegistryName(WitchcraftMod.MOD_ID, "lightning"));
		registry.register(new ScaledColoredParticleData(MANAWAVE, true, Color.WHITE.getRGB(), 1).setRegistryName(WitchcraftMod.MOD_ID, "manawave"));

	}
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void registerFactories(ParticleFactoryRegisterEvent evt) {
		Minecraft.getInstance().particles.registerFactory(SMALL_FLAME, SmallFlameParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(SHIMMER, ShimmerParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(LIGHTNING, LightningParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(MANAWAVE, ManawaveParticle.Factory::new);

	}
}