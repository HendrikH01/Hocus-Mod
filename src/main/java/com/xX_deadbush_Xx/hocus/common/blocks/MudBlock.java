package com.xX_deadbush_Xx.hocus.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class MudBlock extends Block {

	public MudBlock(Properties properties) {
		super(properties);
	}

	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if(entityIn.getMotion().y < 0) {
			entityIn.setMotionMultiplier(state, new Vec3d(0.8D, (double) 0.0F, 0.8D));
		} else 
			entityIn.setMotionMultiplier(state, new Vec3d(0.8D, (double) 0.35F, 0.8D));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return worldIn.getBlockState(pos.up()).getBlock() == this ? super.getCollisionShape(state, worldIn, pos, context) : VoxelShapes.empty();
	}
}
