package com.xX_deadbush_Xx.hocus.common.world.gen.features.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractSmallTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class DreadwoodTreeFeature extends AbstractSmallTreeFeature<TreeFeatureConfig> {
	private final static Direction[] CARDINALS = new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}; 
	
	public DreadwoodTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> p_i225796_1_) {
		super(p_i225796_1_);
	}

	@Override
	protected boolean place(IWorldGenerationReader world, Random rand, BlockPos pos, Set<BlockPos> trunk, Set<BlockPos> leaves, MutableBoundingBox bb, TreeFeatureConfig config) {
		int baseHeight = config.baseHeight + rand.nextInt(config.heightRandA + 1) + rand.nextInt(config.heightRandB + 1);
		int trunkHeight = config.trunkHeight >= 0 ? config.trunkHeight + rand.nextInt(config.trunkHeightRandom + 1) : baseHeight - (config.foliageHeight + rand.nextInt(config.foliageHeightRandom + 1));
		int foliageRadius = config.foliagePlacer.func_225573_a_(rand, trunkHeight, baseHeight, config);

		Optional<BlockPos> optional = this.func_227212_a_(world, baseHeight, trunkHeight, foliageRadius, pos, config);
		if (!optional.isPresent()) {
			return false;
		} else {
			BlockPos newPos = optional.get();
			setDirtAt(world, newPos, newPos.down());
			
			//Pillar in the center
			for (int i = 0; i < baseHeight; i++) 
				setLog(world, rand, pos.up(i), trunk, bb, config);
			
			for (Direction direction : CARDINALS) {
				//bottom roots
				if(Math.random() > 0.35) {
					 setLog(world, rand, pos.offset(direction), trunk, bb, config);
					if(Math.random() > 0.5) {
						 setLog(world, rand, pos.offset(direction).up(), trunk, bb, config);
					}
				}
				
				//Branches at the top
				boolean high = rand.nextFloat() > 0.7;
				int startHeight = baseHeight - (high ? 2 : 3);
				boolean didMoveForward = false;
				Direction side = null;

				BlockPos.Mutable currentpos = (Mutable) new BlockPos.Mutable(newPos).move(Direction.UP, startHeight).move(direction);
				setLog(world, rand, currentpos, trunk, bb, config);
				currentpos.move(Direction.UP);
				if(rand.nextFloat() > 0.2) {
					currentpos.move(direction);
					
					if(rand.nextBoolean()) {
						didMoveForward = true;
						side = rand.nextBoolean() ? direction.rotateY() : direction.rotateYCCW();
						BlockPos sidepos = currentpos.offset(side, 2).offset(direction.getOpposite());
						if(!(trunk.contains(sidepos) || trunk.contains(sidepos.up()))) {
							currentpos.move(side);
						}
					}
				}
				
				setLog(world, rand, currentpos, trunk, bb, config);

				if(high) continue;
				
				currentpos.move(Direction.UP);
				
				if(side == null && rand.nextBoolean()) {
					side = rand.nextBoolean() ? direction.rotateY() : direction.rotateYCCW();
					BlockPos sidepos = currentpos.offset(side, 2).offset(direction.getOpposite());
					if(!(trunk.contains(sidepos) || trunk.contains(sidepos.down()))) {
						currentpos.move(side);
					}
				}
				
				//move forward at least once
				if(!didMoveForward || rand.nextBoolean()) {
					currentpos.move(direction);
					
					//now the log has moved two blocks forward -> we need to cover it up with leaves
					setLeaf(world, rand, currentpos.offset(direction), leaves, bb, config);
					setLeaf(world, rand, currentpos.up(), leaves, bb, config);
					
					if(side != null) {
						setLeaf(world, rand, currentpos.offset(side.getOpposite()).up(), leaves, bb, config);
						setLeaf(world, rand, currentpos.offset(side.getOpposite()).offset(direction), leaves, bb, config);
					} else {
						Direction leafside = rand.nextBoolean() ? direction.rotateY() : direction.rotateYCCW();
						setLeaf(world, rand, currentpos.offset(leafside).offset(direction), leaves, bb, config);
						setLeaf(world, rand, currentpos.offset(leafside).up(), leaves, bb, config);
						setLeaf(world, rand, currentpos.offset(leafside, 2).offset(direction.getOpposite()), leaves, bb, config);
					}
				}
				
				setLog(world, rand, currentpos, trunk, bb, config);
			}
			
			//place leaves
			config.foliagePlacer.func_225571_a_(world, rand, config, baseHeight, trunkHeight, foliageRadius, newPos, leaves);
			return true;
		}
	}
}
