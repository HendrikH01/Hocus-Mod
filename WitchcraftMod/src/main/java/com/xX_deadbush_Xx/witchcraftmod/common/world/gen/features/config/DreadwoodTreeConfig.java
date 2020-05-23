package com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features.config;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractSmallTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class DreadwoodTreeConfig extends AbstractSmallTreeFeature<TreeFeatureConfig> {

	public DreadwoodTreeConfig(Function<Dynamic<?>, ? extends TreeFeatureConfig> p_i225796_1_) {
		super(p_i225796_1_);
	}

	@Override
	protected boolean func_225557_a_(IWorldGenerationReader worldIn, Random rand, BlockPos pos, Set<BlockPos> posset1, Set<BlockPos> posset2, MutableBoundingBox boundingbox, TreeFeatureConfig config) {
	      int baseheight = config.baseHeight + rand.nextInt(config.heightRandA + 1) + rand.nextInt(config.heightRandB + 1);
	      int trunkheight = config.trunkHeight >= 0 ? config.trunkHeight + rand.nextInt(config.trunkHeightRandom + 1) : baseheight - (config.foliageHeight + rand.nextInt(config.foliageHeightRandom + 1));
	      int k = config.foliagePlacer.func_225573_a_(rand, trunkheight, baseheight, config);
	      Optional<BlockPos> optional = this.func_227212_a_(worldIn, baseheight, trunkheight, k, pos, config);
	      if (!optional.isPresent()) {
	         return false;
	      } else {
	         BlockPos blockpos = optional.get();
	         this.setDirtAt(worldIn, blockpos.down(), blockpos);
	         Direction direction = Direction.Plane.HORIZONTAL.random(rand);
	         int l = baseheight - rand.nextInt(4) - 1;
	         int i1 = 3 - rand.nextInt(3);
	         BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
	         int j1 = blockpos.getX();
	         int k1 = blockpos.getZ();
	         int l1 = 0;

	         for(int i2 = 0; i2 < baseheight; ++i2) {
	            int j2 = blockpos.getY() + i2;
	            if (i2 >= l && i1 > 0) {
	               j1 += direction.getXOffset();
	               k1 += direction.getZOffset();
	               --i1;
	            }

	            if (this.func_227216_a_(worldIn, rand, blockpos$mutable.setPos(j1, j2, k1), posset1, boundingbox, config)) {
	               l1 = j2;
	            }
	         }

	         BlockPos blockpos1 = new BlockPos(j1, l1, k1);
	         config.foliagePlacer.func_225571_a_(worldIn, rand, config, baseheight, trunkheight, k + 1, blockpos1, posset2);
	         j1 = blockpos.getX();
	         k1 = blockpos.getZ();
	         Direction direction1 = Direction.Plane.HORIZONTAL.random(rand);
	         if (direction1 != direction) {
	            int j3 = l - rand.nextInt(2) - 1;
	            int k2 = 1 + rand.nextInt(3);
	            l1 = 0;

	            for(int l2 = j3; l2 < baseheight && k2 > 0; --k2) {
	               if (l2 >= 1) {
	                  int i3 = blockpos.getY() + l2;
	                  j1 += direction1.getXOffset();
	                  k1 += direction1.getZOffset();
	                  if (this.func_227216_a_(worldIn, rand, blockpos$mutable.setPos(j1, i3, k1), posset1, boundingbox, config)) {
	                     l1 = i3;
	                  }
	               }

	               ++l2;
	            }

	            if (l1 > 0) {
	               BlockPos blockpos2 = new BlockPos(j1, l1, k1);
	               config.foliagePlacer.func_225571_a_(worldIn, rand, config, baseheight, trunkheight, k, blockpos2, posset2);
	            }
	         }

	         return true;
	      }
	}
}
