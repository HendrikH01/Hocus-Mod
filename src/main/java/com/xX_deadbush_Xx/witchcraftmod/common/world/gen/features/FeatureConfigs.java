package com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features;

import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraftforge.common.IPlantable;

public class FeatureConfigs {
	public static final BigMushroomFeatureConfig BIG_HELLSHROOM = new BigMushroomFeatureConfig(new SimpleBlockStateProvider(ModBlocks.HELLSHROOM_BLOCK.get().getDefaultState()), new SimpleBlockStateProvider(ModBlocks.HELLSHROOM_STEM.get().getDefaultState()), 2);
	public static final TreeFeatureConfig DREADWOOD_TREE = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.DREADWOOD_LOG.get().getDefaultState()), new SimpleBlockStateProvider(ModBlocks.DREADWOOD_LEAVES.get().getDefaultState()), null).baseHeight(5).heightRandA(1).heightRandB(0).trunkHeight(0).ignoreVines().setSapling((IPlantable)ModBlocks.DREADWOOD_SAPLING.get()).build();

}
