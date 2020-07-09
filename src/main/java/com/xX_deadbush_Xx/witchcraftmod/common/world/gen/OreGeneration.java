package com.xX_deadbush_Xx.witchcraftmod.common.world.gen;

import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration {
	public static void generateOre() {
		for (Biome biome : ForgeRegistries.BIOMES) {
			ConfiguredPlacement vibrantCrystalOreConfig = Placement.COUNT_RANGE.configure(new CountRangeConfig(3, 10, 0, 25));
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
					.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.VIBRANT_CRYSTAL_ORE.get().getDefaultState(), 3))
					.withPlacement(vibrantCrystalOreConfig));	
		}
	}
}

