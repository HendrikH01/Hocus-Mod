package com.xX_deadbush_Xx.hocus.common.blocks;

import java.util.Random;

import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModFeatures;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.config.ModFeatureConfigs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

public class FunkyMushroomBlock extends MushroomBlock {

	public FunkyMushroomBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean grow(ServerWorld worldIn, BlockPos blockpos, BlockState blockstate, Random rand) {
		
		worldIn.removeBlock(blockpos, false);
	    ConfiguredFeature<BigMushroomFeatureConfig, ?> configuredfeature;
	    
	    if (this == ModBlocks.FUNKY_MUSHROOM.get()) {
	    	configuredfeature = ModFeatures.HUGE_FUNKY_MUSHROOM.get().withConfiguration(ModFeatureConfigs.HUGE_FUNKY_MUSHROOM);
	    } else {
    		worldIn.setBlockState(blockpos, blockstate, 3);
    		return false;
	    }

	    if (configuredfeature.place(worldIn, worldIn.getChunkProvider().getChunkGenerator(), rand, blockpos)) {
	    	return true;
	    } else {
	    	worldIn.setBlockState(blockpos, blockstate, 3);
	    	return false;
	    }
	}
	
}
