package com.xX_deadbush_Xx.hocus.common.world.gen.features;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class RandomPatchSelective extends Feature<BlockClusterFeatureConfig> {
	
	public RandomPatchSelective(Function<Dynamic<?>, ? extends BlockClusterFeatureConfig> p_i225816_1_) {
		super(p_i225816_1_);
	}

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
		BlockState state = config.stateProvider.getBlockState(rand, pos);
		BlockPos startPos;
		if (config.field_227298_k_) {
			startPos = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos);
		} else {
			startPos = pos;
		}

		int i = 0;
		BlockPos.Mutable current = new BlockPos.Mutable();

		for (int j = 0; j < config.tryCount; ++j) {
			current.setPos(startPos).move(rand.nextInt(config.xSpread + 1) - rand.nextInt(config.xSpread + 1),
					rand.nextInt(config.ySpread + 1) - rand.nextInt(config.ySpread + 1),
					rand.nextInt(config.zSpread + 1) - rand.nextInt(config.zSpread + 1));
			BlockPos down = current.down();
			BlockState downState = worldIn.getBlockState(down);
			
			if ((worldIn.isAirBlock(current)
					|| config.isReplaceable && worldIn.getBlockState(current).getMaterial().isReplaceable())
					&& state.isValidPosition(worldIn, current)
					&& (config.whitelist.isEmpty() || config.whitelist.contains(downState.getBlock()))
					&& !config.blacklist.contains(downState)
					&& (!config.requiresWater || worldIn.getFluidState(down.west()).isTagged(FluidTags.WATER)
							|| worldIn.getFluidState(down.east()).isTagged(FluidTags.WATER)
							|| worldIn.getFluidState(down.north()).isTagged(FluidTags.WATER)
							|| worldIn.getFluidState(down.south()).isTagged(FluidTags.WATER))) {
				config.blockPlacer.func_225567_a_(worldIn, current, state, rand);
				++i;
			}
		}

		return i > 0;
	}
}
