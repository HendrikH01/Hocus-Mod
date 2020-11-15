package com.xX_deadbush_Xx.hocus.common.world.gen.placement;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;

public class DreadwoodTreePlacement  extends Placement<AtSurfaceWithExtraConfig> {
	
	private static final int DENSITY = 3;
	
	public DreadwoodTreePlacement(Function<Dynamic<?>, ? extends AtSurfaceWithExtraConfig> config) {
		super(config);
	}

	public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generatorIn, Random random, AtSurfaceWithExtraConfig config, BlockPos pos) {
		Set<BlockPos> set = new HashSet<>();
		for (int i = 0; i < config.count + (random.nextFloat() < config.extraChance ? 0 : config.extraCount); i++) {
			int x = random.nextInt(16) + pos.getX();
			int z = random.nextInt(16) + pos.getZ();
			int y = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, x, z);
			BlockPos surface = new BlockPos(x, y - 1, z);
			Block block = worldIn.getBlockState(surface).getBlock();
			if (block == Blocks.GRASS_BLOCK) {
				set.add(surface);
			}
		}

		return set.stream();
	}
}
