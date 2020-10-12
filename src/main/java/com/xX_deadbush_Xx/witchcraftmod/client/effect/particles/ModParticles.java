package com.xX_deadbush_Xx.witchcraftmod.client.effect.particles;

import java.awt.Color;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data.ManawaveParticleData;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data.ScaledColoredParticleData;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WitchcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles {	
    public static final DeferredRegister<ParticleType<?>> PARTICLES = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, WitchcraftMod.MOD_ID);

    public static final RegistryObject<ScaledColoredParticleData> SHIMMER = PARTICLES.register("shimmer", () -> ShimmerParticle.getData(true, Color.WHITE.getRGB(), 1));
    public static final RegistryObject<BasicParticleType> SMALL_FLAME = PARTICLES.register("small_flame", () -> new BasicParticleType(true));
    public static final RegistryObject<ScaledColoredParticleData> LIGHTNING = PARTICLES.register("lightning", () -> LightningParticle.getData(true, Color.WHITE.getRGB(), 1));
    public static final RegistryObject<ManawaveParticleData> MANAWAVE = PARTICLES.register("manawave", () -> ManawaveParticle.getData(true, Color.WHITE.getRGB(), 1, 0.15));
    public static final RegistryObject<ScaledColoredParticleData> WAVE_SHIMMER = PARTICLES.register("mana_shimmer", () -> ShimmerParticle.getData(true, Color.WHITE.getRGB(), 1));

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void registerFactories(ParticleFactoryRegisterEvent evt) {
		Minecraft.getInstance().particles.registerFactory(SMALL_FLAME.get(), (e) -> new SmallFlameParticle.Factory(e));
		Minecraft.getInstance().particles.registerFactory(SHIMMER.get(), ShimmerParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(LIGHTNING.get(), LightningParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(MANAWAVE.get(), ManawaveParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(WAVE_SHIMMER.get(), WaveShimmerParticle.Factory::new);
	}
}