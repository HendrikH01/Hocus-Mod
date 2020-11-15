package com.xX_deadbush_Xx.hocus.common.world.gen.structures;

import java.util.Random;
import java.util.function.Function;

import org.apache.logging.log4j.Level;

import com.mojang.datafixers.Dynamic;
import com.xX_deadbush_Xx.hocus.Hocus;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class WizardTowerStructure extends Structure<NoFeatureConfig> {

	private final static int[] heightTestPoints = new int[] {
			0, 0, 
			13, 13, 
			0, 13, 
			13, 0,
			7, 7
	};
	
	public WizardTowerStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> config) {
		super(config);
	}

	@Override
	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
		int maxDistance = 12;
		int minDistance = 7;

		int xTemp = x + maxDistance * spacingOffsetsX;
		int ztemp = z + maxDistance * spacingOffsetsZ;
		int xTemp2 = xTemp < 0 ? xTemp - maxDistance + 1 : xTemp;
		int zTemp2 = ztemp < 0 ? ztemp - maxDistance + 1 : ztemp;
		int validChunkX = xTemp2 / maxDistance;
		int validChunkZ = zTemp2 / maxDistance;

		((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), validChunkX, validChunkZ, this.getSeedModifier());
		validChunkX = validChunkX * maxDistance;
		validChunkZ = validChunkZ * maxDistance;
		validChunkX = validChunkX + random.nextInt(maxDistance - minDistance);
		validChunkZ = validChunkZ + random.nextInt(maxDistance - minDistance);

		return new ChunkPos(validChunkX, validChunkZ);
	}


	@Override
	public String getStructureName() {
		return Hocus.MOD_ID + ":run_down_house";
	}


	@Override
	public int getSize() {
		return 0;
	}


	@Override
	public Structure.IStartFactory getStartFactory() {
		return Start::new;
	}


	protected int getSeedModifier() {
		return 93485795;
	}

	@Override
	public boolean canBeGenerated(BiomeManager biomemanager, ChunkGenerator<?> generator, Random rand, int chunkPosX, int chunkPosZ, Biome biome) {
		ChunkPos chunkpos = this.getStartPositionForPosition(generator, rand, chunkPosX, chunkPosZ, 0, 0);

		return chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z && generator.hasStructure(biome, this);
	}

	public static class Start extends StructureStart {
		
		public Start(Structure<?> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		@Override
		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {

			Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];

			int x = (chunkX << 4) + 7;
			int z = (chunkZ << 4) + 7;

			int min = 999;
			int max = -999;
			int average = 0;
			
			for(int i = 0; i < heightTestPoints.length; i += 2) {
				int y = generator.getHeight(heightTestPoints[i] + (chunkX << 4), heightTestPoints[i+1] + (chunkZ << 4), Heightmap.Type.WORLD_SURFACE_WG);
				min = y < min ? y : min;
				max = y > max ? y : max;
				average += y;
			}
			
			if(max - min >= 5) return; //we don't want the tower to spawn on a hill
			
			int surfaceY = average / (int) Math.floor(heightTestPoints.length / 2);
			BlockPos blockpos = new BlockPos(x, surfaceY + 5, z);

			WizardTowerPieces.start(templateManagerIn, blockpos, rotation, this.components, this.rand);

			this.recalculateStructureSize();


			Hocus.LOGGER.debug("Rundown House at " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
		}

	}
}