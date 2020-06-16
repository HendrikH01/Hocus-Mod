package com.xX_deadbush_Xx.witchcraftmod.common.register;

import com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features.BigHellshroomFeature;
import com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features.DreadwoodTree;
import com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features.config.DreadwoodTreeConfig;

import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class ModFeatures {
	//public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, WitchcraftMod.MOD_ID);
	
	public static final Feature<BigMushroomFeatureConfig> HUGE_HELLSHROOM = new BigHellshroomFeature(BigMushroomFeatureConfig::deserialize);
	public static final Feature<TreeFeatureConfig> DREADWOOD_TREE = new DreadwoodTreeConfig((data) -> TreeFeatureConfig.func_227338_a_(data));
}
