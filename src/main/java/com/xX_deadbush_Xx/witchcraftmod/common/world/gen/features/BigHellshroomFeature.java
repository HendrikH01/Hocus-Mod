package com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractBigMushroomFeature;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

public class BigHellshroomFeature extends AbstractBigMushroomFeature {
	   public BigHellshroomFeature(Function<Dynamic<?>, ? extends BigMushroomFeatureConfig> p_i49864_1_) {
		      super(p_i49864_1_);
		   }
		   
		   //generate cap
		   protected void func_225564_a_(IWorld worldIn, Random rand, BlockPos blockpos, int baseHeight, BlockPos.Mutable blockpos$mutable, BigMushroomFeatureConfig config) {
			      for(int i = baseHeight - 3; i <= baseHeight; ++i) {
			         int j = i < baseHeight ? config.field_227274_c_ : config.field_227274_c_ - 1;
			         int k = config.field_227274_c_ - 2; //field_227274_c_ = cap radius, field_227272_a_ = cap block

			         for(int l = -j; l <= j; ++l) {
			            for(int i1 = -j; i1 <= j; ++i1) {
			               boolean flag = l == -j;
			               boolean flag1 = l == j;
			               boolean flag2 = i1 == -j;
			               boolean flag3 = i1 == j;
			               boolean flag4 = flag || flag1;
			               boolean flag5 = flag2 || flag3;
			               if (i >= baseHeight || flag4 != flag5) {
			                  blockpos$mutable.setPos(blockpos).move(l, i, i1);
			                  if (worldIn.getBlockState(blockpos$mutable).canBeReplacedByLeaves(worldIn, blockpos$mutable)) {
			                     this.setBlockState(worldIn, blockpos$mutable, ModBlocks.HELLSHROOM_BLOCK.get().getDefaultState());
			                  }
			               }
			            }
			         }
			      }

			   }

			protected int func_225563_a_(int p_225563_1_, int p_225563_2_, int p_225563_3_, int p_225563_4_) {
				int i = 0;
				if (p_225563_4_ < p_225563_2_ && p_225563_4_ >= p_225563_2_ - 3) {
					i = p_225563_3_;
				} else if (p_225563_4_ == p_225563_2_) {
					i = p_225563_3_;
				}

				return i;
			}
		}