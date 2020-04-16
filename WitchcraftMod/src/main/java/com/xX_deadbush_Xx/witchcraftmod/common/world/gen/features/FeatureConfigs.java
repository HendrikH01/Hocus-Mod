package com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ModBlocks;

import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

public class FeatureConfigs {
	   public static final BigMushroomFeatureConfig BIG_HELLSHROOM = new BigMushroomFeatureConfig(new SimpleBlockStateProvider(ModBlocks.HELLSHROOM_BLOCK.get().getDefaultState()), new SimpleBlockStateProvider(ModBlocks.HELLSHROOM_STEM.get().getDefaultState()), 2);

}
