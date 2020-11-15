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

public class ModMushroomBlock extends MushroomBlock implements IGrowable {

	public ModMushroomBlock(Properties properties) {
		super(properties);
	}
	
	public boolean growHugeMushroom(ServerWorld worldIn, BlockPos blockpos, BlockState blockstate, Random rand) {
		
		worldIn.removeBlock(blockpos, false);
	    ConfiguredFeature<BigMushroomFeatureConfig, ?> configuredfeature;
	    
	    if (this == ModBlocks.FUNKY_MUSHROOM.get()) {
	    	configuredfeature = ModFeatures.BIG_HELLSHROOM.get().withConfiguration(ModFeatureConfigs.HUGE_FUNKY_MUSHROOM);
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
	
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
	   return (double)rand.nextFloat() < 0.4D;
	}

	public void grow(ServerWorld worldIn, Random rand, BlockPos blockpos, BlockState blockstate) {
		this.growHugeMushroom(worldIn, blockpos, blockstate, rand);
	}
}
