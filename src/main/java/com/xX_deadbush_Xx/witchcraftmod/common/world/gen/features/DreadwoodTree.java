package com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features;

import java.util.Random;

import com.xX_deadbush_Xx.witchcraftmod.common.register.ModFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class DreadwoodTree extends Tree {

	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
	      return ModFeatures.DREADWOOD_TREE.withConfiguration(FeatureConfigs.DREADWOOD_TREE);
	}
}
