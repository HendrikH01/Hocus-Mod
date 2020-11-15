package com.xX_deadbush_Xx.hocus.client.effect.particles;

import java.awt.Color;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.client.effect.particles.data.ScaledColoredParticleData;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ModParticles {	
    public static final DeferredRegister<ParticleType<?>> PARTICLES = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, Hocus.MOD_ID);

    public static final RegistryObject<ScaledColoredParticleData> SHIMMER = PARTICLES.register("shimmer", () -> ShimmerParticle.getData(true, Color.WHITE.getRGB(), 1));
    public static final RegistryObject<ScaledColoredParticleData> FADING_SHIMMER = PARTICLES.register("fading_shimmer", () -> ShimmerParticle.getData(true, Color.WHITE.getRGB(), 1));
    public static final RegistryObject<BasicParticleType> SMALL_FLAME = PARTICLES.register("small_flame", () -> new BasicParticleType(true));
   
	@SuppressWarnings("resource")
	public static void registerFactories() {
		Minecraft.getInstance().particles.registerFactory(SMALL_FLAME.get(), (e) -> new SmallFlameParticle.Factory(e));
		Minecraft.getInstance().particles.registerFactory(SHIMMER.get(), ShimmerParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(FADING_SHIMMER.get(), FadingShimmerParticle.Factory::new);
	}
}