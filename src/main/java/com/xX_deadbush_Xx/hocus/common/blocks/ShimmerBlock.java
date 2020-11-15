package com.xX_deadbush_Xx.hocus.common.blocks;

import java.util.Random;

import com.xX_deadbush_Xx.hocus.client.effect.particles.FadingShimmerParticle;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShimmerBlock extends Block {

	public ShimmerBlock(Properties properties) {
		super(properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.isRemote)
			worldIn.addParticle(FadingShimmerParticle.getData(true, 0xFFAC48, 1.3f),
					pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
		
			worldIn.addParticle(FadingShimmerParticle.getData(true, 0xFFD890, 1.0f), 
					pos.getX() + 0.5+ (rand.nextFloat() - 0.5)/50, pos.getY() + 0.5, pos.getZ() + 0.5 + (rand.nextFloat() - 0.5)/50,  + (rand.nextFloat() - 0.5)/50,  + (rand.nextFloat() - 0.5)/50 + 0.01,  + (rand.nextFloat() - 0.5)/50);
	}

	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.create(6, 6, 6, 10, 10, 10);
	}
}
