package com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractSmallTreeFeature;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class DreadwoodTreeConfig extends AbstractSmallTreeFeature<TreeFeatureConfig> {
	private final static Direction[] cardinals = new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}; 
	
	public DreadwoodTreeConfig(Function<Dynamic<?>, ? extends TreeFeatureConfig> p_i225796_1_) {
		super(p_i225796_1_);
	}

	@Override
	protected boolean place(IWorldGenerationReader world, Random rand, BlockPos pos, Set<BlockPos> posset1, Set<BlockPos> posset2, MutableBoundingBox boundingbox, TreeFeatureConfig config) {
		int baseheight = config.baseHeight + rand.nextInt(config.heightRandA + 1) + rand.nextInt(config.heightRandB + 1);
		setDirtAt(world, pos, pos.down());
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
		world.destroyBlock(pos, true);
		posset1.add(pos);
		placeTrunk(baseheight, world, rand, pos, posset2, posset2, boundingbox, config);
		placeBranches(pos.up(baseheight - 1), world, rand, pos, posset2, posset2, boundingbox, config);
		placeLeaves(baseheight, world, rand, pos, posset2, posset2, boundingbox, config);
		return true;
	}
	
	private void placeTrunk(int baseheight, IWorldGenerationReader world, Random rand, BlockPos pos, Set<BlockPos> leavesset, Set<BlockPos> trunkset, MutableBoundingBox boundingbox, TreeFeatureConfig config) {
		BlockPos top = pos.up(baseheight - 1);
		
		for (int i = 0; i < baseheight; i++) setLog(world, rand, pos.up(i), trunkset, boundingbox, config);
		for (Direction direction : cardinals) {
			//bottom
			if(Math.random() > 0.35) {
				 setLog(world, rand, pos.offset(direction), trunkset, boundingbox, config);
				if(Math.random() > 0.5) {
					 setLog(world, rand, pos.offset(direction).up(), trunkset, boundingbox, config);
				}
			}
			
			//top
			if(Math.random() > 0.55) {
				 setLog(world, rand, top.offset(direction), trunkset, boundingbox, config);
				if(Math.random() > 0.35) {
					 setLog(world, rand, top.offset(direction).down(), trunkset, boundingbox, config);
				}
			}
		}
	}
	
	private void placeBranches(BlockPos top, IWorldGenerationReader world, Random rand, BlockPos pos, Set<BlockPos> trunkset, Set<BlockPos> leavesset, MutableBoundingBox boundingbox, TreeFeatureConfig config) {
		List<Direction> copy = new ArrayList<>(Arrays.asList(cardinals));
		copy.remove(rand.nextInt(4));
		if(Math.random() > 0.6) copy.remove(rand.nextInt(3));
		
		copy.forEach((direction) -> {
			boolean f1 = false;
			boolean f2 = rand.nextBoolean();
			f1 = f2 ? rand.nextBoolean() : true; //either f1 or f2 is always true, but both can be true as well
			
			setLog(world, rand, top.offset(direction).offset(direction.rotateY()), trunkset, boundingbox, config);
			setLog(world, rand, top.offset(direction, f1 ? 2 : 1).offset(direction.rotateY(), f2 ? 2 : 1).up(), trunkset, boundingbox, config);
		});
	}
	
	private void placeLeaves(int baseheight, IWorldGenerationReader world, Random rand, BlockPos pos, Set<BlockPos> trunkset, Set<BlockPos> leavesset, MutableBoundingBox boundingbox, TreeFeatureConfig config) {
		BlockPos pos1 = pos.up(baseheight);
		BlockPos pos2 = pos.up(baseheight+1);
		boolean hasCorner = false;
		
		placeLeavesAt(world, pos1, rand, leavesset, config);
		placeLeavesAt(world, pos2, rand, leavesset, config);
		for (Direction direction : cardinals) {
			//layer1
			for(int i = 0; i < 4; i++) placeLeavesAt(world, pos1.offset(direction).offset(direction.rotateY(), i), rand, leavesset, config);
			for(int i = 0; i < 3; i++) placeLeavesAt(world, pos1.offset(direction, 2).offset(direction.rotateY(), i), rand, leavesset, config);
			for(int i = 0; i < 2; i++) placeLeavesAt(world, pos1.offset(direction, 3).offset(direction.rotateY(), i), rand, leavesset, config);
			BlockPos pos3 = pos1.offset(direction, 2).offset(direction.rotateY(), 2);
			if(world.hasBlockState(pos3, (state) -> {
		         return state.getBlock() == ModBlocks.DREADWOOD_LOG.get();
		      })) {
				placeLeavesAt(world, pos3.offset(direction), rand, leavesset, config);
				placeLeavesAt(world, pos3.offset(direction.rotateY()), rand, leavesset, config);
				placeLeavesAt(world, pos3.up(), rand, leavesset, config);
				hasCorner = true;
			}
			//layer2
			for(int i = 0; i < 3; i++) placeLeavesAt(world, pos2.offset(direction, 1).offset(direction.rotateY(), i), rand, leavesset, config);
			for(int i = 0; i < 2; i++) placeLeavesAt(world, pos2.offset(direction, 2 ).offset(direction.rotateY(), i), rand, leavesset, config);
			//vines
			BlockPos pos4 = pos1.down(1);
			int length = rand.nextInt(3) + 1;
			if(!hasCorner) {
				length = growVinesAt(length, world, pos4.offset(direction, 3), rand, leavesset, config);
				length = growVinesAt(length, world, pos4.offset(direction, 3).offset(direction.rotateY()), rand, leavesset, config);
				length = growVinesAt(length, world, pos4.offset(direction, 2).offset(direction.rotateY(), 2), rand, leavesset, config);
				growVinesAt(length, world, pos4.offset(direction, 1).offset(direction.rotateY(), 3), rand, leavesset, config);
			} else {
				length = growVinesAt(length, world, pos4.offset(direction, 3), rand, leavesset, config);
				length = growVinesAt(length, world, pos4.offset(direction, 3).offset(direction.rotateY()), rand, leavesset, config);
				length = growVinesAt(length, world, pos4.offset(direction, 3).offset(direction.rotateY(), 2), rand, leavesset, config);
				length = growVinesAt(length, world, pos4.offset(direction, 2).offset(direction.rotateY(), 3), rand, leavesset, config);
				growVinesAt(length, world, pos4.offset(direction, 1).offset(direction.rotateY(), 3), rand, leavesset, config);
			}
		}
	}
	
	private void placeLeavesAt(IWorldGenerationReader world, BlockPos pos, Random rand, Set<BlockPos> leavesset, TreeFeatureConfig config) {
		if (AbstractTreeFeature.isAirOrLeaves(world, pos) || AbstractTreeFeature.isTallPlants(world, pos) || AbstractTreeFeature.isWater(world, pos)) {
			world.setBlockState(pos, config.leavesProvider.getBlockState(rand, pos), 3);
          	leavesset.add(pos.toImmutable());
		}
	}
	
	private int growVinesAt(int prevlength, IWorldGenerationReader world, BlockPos pos, Random rand, Set<BlockPos> leavesset, TreeFeatureConfig config) {
		int length = prevlength == 1 ? (Math.random() > 0.7 ? 3 : 2 )
				: prevlength == 2 ? (Math.random() > 0.6 ? 3 : 1)
						: Math.random() > 0.7 ? 1 : 2;
		for(int i=0; i < length; i++) placeLeavesAt(world, pos.down(i), rand, leavesset, config);
		return length;
	}
}
