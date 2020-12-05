package com.xX_deadbush_Xx.hocus.common.world.biomes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.xX_deadbush_Xx.hocus.common.register.ModEntities;
import com.xX_deadbush_Xx.hocus.common.register.ModFeatures;
import com.xX_deadbush_Xx.hocus.common.world.biomes.surface.ModSurfaces;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.config.ModFeatureConfigs;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.config.MultipleWithChanceBlockDependantConfig;
import com.xX_deadbush_Xx.hocus.common.world.gen.placement.ModPlacements;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleWithChanceRandomFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class EnchantedForestBiome extends HocusBiome {

	private static final int COLOR = 0x3F7200;
	
    public EnchantedForestBiome() {
        super((new Builder().surfaceBuilder(ModSurfaces.ENCHANTED_FOREST, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)
                .precipitation(RainType.RAIN).category(Category.FOREST).depth(0.3F).scale(0.2F).temperature(0.75F)
                .downfall(0.1F).waterColor(0x2586C6).waterFogColor(0x2586C6).parent(null)));
        
    }

	@Override
	protected void addEntities() {
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.RABBIT, 8, 2, 3));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.SHEEP, 16, 3, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.WOLF, 5, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(ModEntities.RAVEN.get(), 8, 2, 3));
        this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 20, 5, 8));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 30, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 65, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 60, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 15, 1, 3));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 30, 1, 1));
	}

	@Override
	protected void addFeatures() {
	    this.addStructure(Feature.MINESHAFT.withConfiguration(new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL)));
	    this.addStructure(Feature.STRONGHOLD.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addStructure(ModFeatures.WIZARD_TOWER.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModFeatures.WIZARD_TOWER.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
				.withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_RANDOM_SELECTOR.withConfiguration(
        		new MultipleWithChanceRandomFeatureConfig(ImmutableList.of(
        				Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.ADONIS_FEATURE),
        				Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.BELLADONNA_FEATURE), 
        				Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.SWIRLY_PLANT_FEATURE), 
        				Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.ROSE_BUSH_WHITELISTED),
        				Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.LILAC_WHITELISTED_FEATURE), 
        				Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.CORNFLOWER_WHITELISTED),
						Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.DANDELION_WHITELISTED)), 2))
        		.withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
	
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.BLOCK_DEPENDANT_SELECTOR.get().withConfiguration(
        		new MultipleWithChanceBlockDependantConfig(ImmutableList.of(
        				Pair.of(Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM).withChance(0.05F), ImmutableMap.of(Blocks.GRASS_BLOCK, 0.3f, Blocks.MYCELIUM, 0.7f)), 
        				Pair.of(Feature.HUGE_RED_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_RED_MUSHROOM).withChance(0.05F), ImmutableMap.of(Blocks.GRASS_BLOCK, 0.3f, Blocks.MYCELIUM, 0.7f)), 
        				Pair.of(ModFeatures.HUGE_FUNKY_MUSHROOM.get().withConfiguration(ModFeatureConfigs.HUGE_FUNKY_MUSHROOM).withChance(0.04F), ImmutableMap.of(Blocks.GRASS_BLOCK, 0.3f, Blocks.MYCELIUM, 0.7f)), 
        				Pair.of(ModFeatures.DREADWOOD_TREE.get().withConfiguration(ModFeatureConfigs.DREADWOOD_TREE).withChance(0.8F), ImmutableMap.of(Blocks.GRASS_BLOCK, 1.0f, Blocks.MYCELIUM, 0.02f)), 
        				Pair.of(Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.BIRCH_TREE_CONFIG).withChance(0.08F), ImmutableMap.of(Blocks.GRASS_BLOCK, 1.0f, Blocks.MYCELIUM, 0.05f)), 
        				Pair.of(Feature.FANCY_TREE.withConfiguration(DefaultBiomeFeatures.FANCY_TREE_CONFIG).withChance(0.06F), ImmutableMap.of(Blocks.GRASS_BLOCK, 1.0f, Blocks.MYCELIUM, 0.05f)),
        				Pair.of(Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG).withChance(0.1F), ImmutableMap.of(Blocks.GRASS_BLOCK, 1.0f, Blocks.MYCELIUM, 0.05f))), //sometimes leave empty spaces
        				Feature.NO_OP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)))
        		.withPlacement(Placement.DARK_OAK_TREE.configure(new NoPlacementConfig())));
	
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.LILY_PAD_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(4))));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.GRASS_CONFIG_WHITELISTED).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(5))));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.TALL_GRASS_CONFIG_WHITELISTED).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(7))));
        
        //shrooms
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(5))));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(5))));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.FUNKY_MUSHROOM_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(4))));
       
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.FUNKY_MUSHROOM_CONFIG_WHITELISTED).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(5))));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.RED_MUSHROOM_WHITELISTED).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(6))));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.BROWN_MUSHROOM_WHITELISTED).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(6))));

        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ModFeatureConfigs.FUNGAL_GRASS).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(5))));

        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addExtraEmeraldOre(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addReedsAndPumpkins(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addStructures(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addSedimentDisks(this);
	}
	
	@Override
	public int getGrassColor(double posX, double posZ) {
		return COLOR;
	}
	
	@Override
	public int getFoliageColor() {
		return 0x64A339;
	}
}
