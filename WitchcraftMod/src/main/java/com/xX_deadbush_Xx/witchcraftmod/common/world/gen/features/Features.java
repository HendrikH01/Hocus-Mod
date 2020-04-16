package com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Features {
	public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, WitchcraftMod.MOD_ID);
	
	public static final RegistryObject<Feature<BigMushroomFeatureConfig>> HUGE_HELLSHROOM = FEATURES.register("huge_hellshroom",
			() -> new BigHellshroomFeature(BigMushroomFeatureConfig::deserialize));	 	
}
