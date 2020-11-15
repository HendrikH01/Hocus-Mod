package com.xX_deadbush_Xx.hocus.common.world.gen.features;

import java.util.Random;
import java.util.Set;

import com.mojang.datafixers.Dynamic;
import com.xX_deadbush_Xx.hocus.api.util.RitualHelper;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModFeatures;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

public class DreadwoodTreeFoliagePlacer extends FoliagePlacer {
		
	//field_227381_a_ = radius of the foliage
	//field_227382_b_ = radius randomization offset
	
	public DreadwoodTreeFoliagePlacer(int radius, int radiusRandomizationOffset) {
		super(radius, radiusRandomizationOffset, ModFeatures.DREADWOOD_FOLIAGE_PLACER);
	}
	
	/*
	 * SUPERCLASS METHODS:
	 * func_227384_a_ places a horizontal square of leaves
	 * func_227385_a_ places a single leaves block if possible
	 */
	
	
	public <T> DreadwoodTreeFoliagePlacer(Dynamic<T> dynamic) {
		this(dynamic.get("radius").asInt(0), dynamic.get("radius_random").asInt(0));
	}
	
	/**
	 * Generates the foliage of the tree
	 */
	@Override
	public void func_225571_a_(IWorldGenerationReader world, Random rand, TreeFeatureConfig config, int baseHeight, int foliageHeight, int randomRadius, BlockPos pos, Set<BlockPos> leaves) {
        this.func_227384_a_(world, rand, config, baseHeight, pos, baseHeight, randomRadius - 1, leaves);
        this.func_227384_a_(world, rand, config, baseHeight, pos, baseHeight - 1, randomRadius, leaves);
        
        int prevlength = 0;
        BlockPos.Mutable currentPos = new BlockPos.Mutable(pos.add(randomRadius, baseHeight - 2, -randomRadius));
        for(Direction d : RitualHelper.getHorizontalDirections()) {
        	for(int i = 0; i < randomRadius * 2; i++) {
        		currentPos.move(d);
        		float f = rand.nextFloat();
        		if(i == 0 && f > 0.7){
        			prevlength = growLeafVines(prevlength, world, baseHeight, randomRadius, currentPos.down(), rand, leaves, config);
        		} else {
        			prevlength = growLeafVines(prevlength, world, baseHeight, randomRadius, currentPos, rand, leaves, config);
        		}
        	}
        }
	}
	
	private int growLeafVines(int prevlength, IWorldGenerationReader world, int baseHeight, int randomRadius, BlockPos pos, Random rand, Set<BlockPos> leaves, TreeFeatureConfig config) {
		int length = prevlength == 1 ? (rand.nextFloat() > 0.3 ? 3 : 2 )
				: prevlength == 2 ? (rand.nextFloat() > 0.7 ? 3 : 1)
						: rand.nextFloat() > 0.6 ? 1 : 2;
		
		for(int i=0; i < length; i++)  {
			this.func_227385_a_(world, rand, pos.down(i), config, leaves);
		}
		
		return length;
	}

	/**
	 * Gets a randomized radius
	 */
	@Override
	public int func_225573_a_(Random rand, int foliageHeight, int trunkHeight, TreeFeatureConfig config) {
		return this.field_227381_a_ + rand.nextInt(this.field_227382_b_ + 1);
	}
	
	/**
	 * Return true if the leaf block at this position is not valid. Only called when a square of leaves is made using func_227384_a_
	 */
	@Override
	protected boolean func_225572_a_(Random rand, int baseHeight, int dx, int y, int dz, int size) {
		return Math.abs(dx) == size && Math.abs(dz) == size && size > 0 && ((y == baseHeight - 1) || rand.nextFloat() > 0.3); //cuts off corners
	}
	
	/**
	 * Gets the radius that needs to be checked for unreplaceable blocks at current trunk height. Loops from 0 to the maximum trunk height + 1
	 */
	@Override
	public int func_225570_a_(int foliageHeight, int trunkHeight, int randomRadius, int currentTrunkHeight) {
		return currentTrunkHeight == 0 ? 0 : 1;
	}
}
