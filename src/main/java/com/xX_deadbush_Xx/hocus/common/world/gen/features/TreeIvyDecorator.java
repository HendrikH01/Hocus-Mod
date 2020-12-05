package com.xX_deadbush_Xx.hocus.common.world.gen.features;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import com.xX_deadbush_Xx.hocus.common.blocks.IvyBlock;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModFeatures;

import net.minecraft.block.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

public class TreeIvyDecorator extends TreeDecorator {
	private static final int CHANCE = 7;
	
	public TreeIvyDecorator() {
		super(ModFeatures.IVY_DECORATOR);
	}

	public <T> TreeIvyDecorator(Dynamic<T> p_i225867_1_) {
		this();
	}

	public <T> T serialize(DynamicOps<T> ops) {
		return (new Dynamic<>(ops,
				ops.createMap(ImmutableMap.of(ops.createString("type"),
						ops.createString(Registry.TREE_DECORATOR_TYPE.getKey(this.field_227422_a_).toString())))))
								.getValue();
	}

	protected void placeIvy(IWorldWriter world, BlockPos pos, BooleanProperty side, Set<BlockPos> decorations, MutableBoundingBox bb) {
		this.func_227423_a_(world, pos, ModBlocks.POISON_IVY.get().getDefaultState().with(side, Boolean.valueOf(true)), decorations, bb);
	}
	   
	@Override
	public void func_225576_a_(IWorld world, Random rand, List<BlockPos> trunk, List<BlockPos> leaves, Set<BlockPos> decorations, MutableBoundingBox bb) {
		int minY = 999;
		int maxCount = rand.nextInt(5);
		
		if(rand.nextFloat() > 0.2)
			return;
		
		for (BlockPos pos : trunk) {
			int y = pos.getY();
			minY = y < minY ? y : minY;
		}

		for (BlockPos pos : trunk) {
			if(decorations.size() >= maxCount)
				return;
			
			if (pos.getY() >= minY + 3)
				continue;

			if (rand.nextInt(CHANCE) == 0) {
				BlockPos blockpos = pos.west();
				if (AbstractTreeFeature.isAir(world, blockpos)) {
					this.placeIvy(world, blockpos, IvyBlock.EAST, decorations, bb);
					continue;
				}
			}

			if (rand.nextInt(CHANCE) == 0) {
				BlockPos blockpos1 = pos.east();
				if (AbstractTreeFeature.isAir(world, blockpos1)) {
					this.placeIvy(world, blockpos1, IvyBlock.WEST, decorations, bb);
					continue;
				}
			}

			if (rand.nextInt(CHANCE) == 0) {
				BlockPos blockpos2 = pos.north();
				if (AbstractTreeFeature.isAir(world, blockpos2)) {
					this.placeIvy(world, blockpos2, IvyBlock.SOUTH, decorations, bb);
					continue;
				}
			}

			if (rand.nextInt(CHANCE) == 0) {
				BlockPos blockpos3 = pos.south();
				if (AbstractTreeFeature.isAir(world, blockpos3)) {
					this.placeIvy(world, blockpos3, IvyBlock.NORTH, decorations, bb);
					continue;
				}
			}

			if (rand.nextInt(CHANCE) == 0) {
				BlockPos blockpos2 = pos.up();
				if (AbstractTreeFeature.isAir(world, blockpos2)) {
					this.placeIvy(world, blockpos2, IvyBlock.UP, decorations, bb);
					continue;
				}
			}
		}
	}
}
