package com.xX_deadbush_Xx.witchcraftmod.common.world.biomes;

import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.BiomeHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModFeatures;
import com.xX_deadbush_Xx.witchcraftmod.common.world.biomes.surface.ModSurfaces;
import com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features.config.FlowerFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class EnchantedForestBiome extends Biome {

    public EnchantedForestBiome() {
        super((new Builder().surfaceBuilder(ModSurfaces.ENCHANTED_FOREST, new SurfaceBuilderConfig(Blocks.PODZOL.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState()))
                .precipitation(RainType.RAIN).category(Category.PLAINS).depth(0.125F).scale(0.07F).temperature(0.75F)
                .downfall(0.1F).waterColor(4159204).waterFogColor(329011).parent(null)));
        DefaultBiomeFeatures.addCarvers(this);
        BiomeHelper.DEFAULT_ENTITIES.forEach((biomeEntityData) -> this.addSpawn(biomeEntityData.getEntityClassification(), biomeEntityData.getSpawnListEntry()));

        addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG)
                .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(10, 0.1f, 1))));
        addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.FLOWER_FEATURE.withConfiguration(new FlowerFeatureConfig(2, 2, 3)));
        DefaultBiomeFeatures.addLakes(this);

        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addExtraGoldOre(this);
        DefaultBiomeFeatures.addBerryBushes(this);
        DefaultBiomeFeatures.addExtraEmeraldOre(this);
    }
}
