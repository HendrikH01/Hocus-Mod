package com.xX_deadbush_Xx.hocus.common.world.biomes.surface;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class EnchantedForestSurface extends SurfaceBuilder<SurfaceBuilderConfig> {
	private static final SurfaceBuilderConfig MUD_CONFIG_ONE_DOWN = new SurfaceBuilderConfig(ModBlocks.MUD.get().getDefaultState(), ModBlocks.MUD.get().getDefaultState(), DIRT);
	private static final SurfaceBuilderConfig MUD_CONFIG = new SurfaceBuilderConfig(ModBlocks.MUD.get().getDefaultState(), ModBlocks.MUD.get().getDefaultState(), DIRT);

    public EnchantedForestSurface(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> config) {
        super(config);
    }

	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
		int y = chunkIn.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG).getHeight(x & 15, z & 15) - 1;
		if(noise > 4.1 && y < 70 && y > seaLevel) {
			SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, MUD_CONFIG_ONE_DOWN);

			if(y != 69 || noise > 4.2) {
				chunkIn.setBlockState(new BlockPos(x, y, z), AIR, false);
			}
			
		} else if(noise < -3.2f){
			SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock,
					defaultFluid, seaLevel, seed, SurfaceBuilder.MYCELIUM_DIRT_GRAVEL_CONFIG);
		}
		else {
			SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock,
				defaultFluid, seaLevel, seed, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);
		}
	}
}
