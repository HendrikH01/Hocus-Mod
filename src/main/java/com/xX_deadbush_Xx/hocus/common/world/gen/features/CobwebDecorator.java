package com.xX_deadbush_Xx.hocus.common.world.gen.features;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import com.xX_deadbush_Xx.hocus.common.register.ModFeatures;

import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

public class CobwebDecorator extends TreeDecorator {
	private static final int CHANCE = 4;
	
	public CobwebDecorator() {
		super(ModFeatures.COB_WEB_DECORATOR);
	}

	public <T> CobwebDecorator(Dynamic<T> p_i225867_1_) {
		this();
	}

	public <T> T serialize(DynamicOps<T> ops) {
		return (new Dynamic<>(ops,
				ops.createMap(ImmutableMap.of(ops.createString("type"),
						ops.createString(Registry.TREE_DECORATOR_TYPE.getKey(this.field_227422_a_).toString())))))
								.getValue();
	}
	   
	@Override
	public void func_225576_a_(IWorld world, Random rand, List<BlockPos> trunk, List<BlockPos> leaves, Set<BlockPos> decorations, MutableBoundingBox bb) {
		int max = -999;
		int maxCount = rand.nextInt(3);
		
		for (BlockPos pos : trunk) {
			int y = pos.getY();
			max = y > max ? y : max;
		}

		for (BlockPos pos : leaves) {
			if(decorations.size() >= maxCount)
				return;
			
			if (pos.getY() < max - 4)
				continue;

			if (rand.nextInt(CHANCE) > 0) {
				BlockPos blockpos = pos.down();
				if (AbstractTreeFeature.isAir(world, blockpos)) {
					int leafcount = 0;
					for(Direction d : Direction.values()) {
						if(leaves.contains(blockpos.offset(d)))
							leafcount++;
					}
					
					if(leafcount > 2) {
						this.func_227423_a_(world, pos, Blocks.COBWEB.getDefaultState(), decorations, bb);
		
					}
				}
			}
		}
	}
}
