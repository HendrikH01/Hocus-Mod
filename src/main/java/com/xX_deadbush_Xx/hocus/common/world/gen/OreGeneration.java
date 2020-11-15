package com.xX_deadbush_Xx.hocus.common.world.gen;

import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration {

	private static final BlockState SHALE = ModBlocks.SHALE.get().getDefaultState();

	public static void generateOre() {
		for (Biome biome : ForgeRegistries.BIOMES) {
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
				.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.VIBRANT_CRYSTAL_ORE.get().getDefaultState(), 5))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 8, 0, 24))));

			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
					.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.ONYX_ORE.get().getDefaultState(), 5))
					.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 8, 0, 24))));
			
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
				.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, SHALE, 33))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(12, 0, 0, 256))));

		}
	}
}
