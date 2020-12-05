package com.xX_deadbush_Xx.hocus.common.world.gen.features.config;

import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.CobwebDecorator;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.DreadwoodTreeFoliagePlacer;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.TreeIvyDecorator;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraftforge.common.IPlantable;

public class ModFeatureConfigs {
	
	private static final Set<Block> GRASS_WHITELIST = ImmutableSet.of(Blocks.GRASS_BLOCK);
	private static final Set<Block> MYCELIUM_WHITELIST = ImmutableSet.of(Blocks.MYCELIUM);

	public static final BigMushroomFeatureConfig HUGE_FUNKY_MUSHROOM = new BigMushroomFeatureConfig(
			new SimpleBlockStateProvider(ModBlocks.FUNKY_MUSHROOM_BLOCK.get().getDefaultState()), 
			new SimpleBlockStateProvider(ModBlocks.FUNKY_MUSHROOM_STEM.get().getDefaultState()), 2);
	
	public static final TreeFeatureConfig DREADWOOD_TREE = new TreeFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModBlocks.DREADWOOD_LOG.get().getDefaultState()), 
			new SimpleBlockStateProvider(ModBlocks.DREADWOOD_LEAVES.get().getDefaultState()), new DreadwoodTreeFoliagePlacer(3, 0))
			.baseHeight(6)
			.heightRandA(1)
			.heightRandB(1)
			.trunkHeight(5)
			.trunkHeightRandom(2)
			.decorators(ImmutableList.of(new BeehiveTreeDecorator(0.01F), new TreeIvyDecorator(), new CobwebDecorator()))
			.ignoreVines()
			.setSapling((IPlantable)ModBlocks.DREADWOOD_SAPLING.get())
			.build();
	
	public static final BlockClusterFeatureConfig ADONIS_FEATURE = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModBlocks.ADONIS.get().getDefaultState()), new SimpleBlockPlacer())
			.tries(32)
			.whitelist(GRASS_WHITELIST)
			.func_227317_b_()
			.build();
	
	public static final BlockClusterFeatureConfig BELLADONNA_FEATURE = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModBlocks.BELLADONNA.get().getDefaultState()), new SimpleBlockPlacer())
			.tries(32)
			.whitelist(GRASS_WHITELIST)
			.func_227317_b_()
			.build();
	
	public static final BlockClusterFeatureConfig CORNFLOWER_WHITELISTED = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(Blocks.CORNFLOWER.getDefaultState()), new SimpleBlockPlacer())
			.tries(32)
			.whitelist(GRASS_WHITELIST)
			.func_227317_b_()
			.build();
	
	public static final BlockClusterFeatureConfig DANDELION_WHITELISTED = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(Blocks.DANDELION.getDefaultState()), new SimpleBlockPlacer())
			.tries(32).whitelist(GRASS_WHITELIST).func_227317_b_().build();
	
	public static final BlockClusterFeatureConfig SWIRLY_PLANT_FEATURE = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModBlocks.SWIRLY_PLANT.get().getDefaultState()), new DoublePlantBlockPlacer())
			.tries(64).whitelist(GRASS_WHITELIST).func_227317_b_().build();
	
	public static final BlockClusterFeatureConfig LILAC_WHITELISTED_FEATURE = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(Blocks.LILAC.getDefaultState()), new DoublePlantBlockPlacer())
			.tries(64).whitelist(GRASS_WHITELIST).func_227317_b_().build();
	
	public static final BlockClusterFeatureConfig ROSE_BUSH_WHITELISTED = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(Blocks.ROSE_BUSH.getDefaultState()), new DoublePlantBlockPlacer())
			.tries(64).func_227317_b_().whitelist(GRASS_WHITELIST).build();

	public static final BlockClusterFeatureConfig FUNKY_MUSHROOM_CONFIG = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModBlocks.FUNKY_MUSHROOM.get().getDefaultState()), new SimpleBlockPlacer())
			.tries(32).func_227317_b_().build();

	public static final BlockClusterFeatureConfig FUNKY_MUSHROOM_CONFIG_WHITELISTED = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModBlocks.FUNKY_MUSHROOM.get().getDefaultState()), new SimpleBlockPlacer())
			.tries(32).func_227317_b_().whitelist(MYCELIUM_WHITELIST).build();
	
	public static final BlockClusterFeatureConfig RED_MUSHROOM_WHITELISTED = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(Blocks.RED_MUSHROOM.getDefaultState()), new SimpleBlockPlacer())
			.tries(32).func_227317_b_().whitelist(MYCELIUM_WHITELIST).build();
	
	public static final BlockClusterFeatureConfig BROWN_MUSHROOM_WHITELISTED = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(Blocks.BROWN_MUSHROOM.getDefaultState()), new SimpleBlockPlacer())
			.tries(32).func_227317_b_().whitelist(MYCELIUM_WHITELIST).build();
	
	public static final BlockClusterFeatureConfig GRASS_CONFIG_WHITELISTED = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(Blocks.GRASS.getDefaultState()), new SimpleBlockPlacer()).tries(32).whitelist(GRASS_WHITELIST).build();
	
	public static final BlockClusterFeatureConfig TALL_GRASS_CONFIG_WHITELISTED = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(Blocks.TALL_GRASS.getDefaultState()), new SimpleBlockPlacer()).tries(32).whitelist(GRASS_WHITELIST).build();
	
	public static final BlockClusterFeatureConfig FUNGAL_GRASS = new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModBlocks.FUNGAL_GRASS.get().getDefaultState()), new SimpleBlockPlacer()).tries(32).whitelist(MYCELIUM_WHITELIST).build();

}
