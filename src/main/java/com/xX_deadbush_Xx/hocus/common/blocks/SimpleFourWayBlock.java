package com.xX_deadbush_Xx.hocus.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;

public class SimpleFourWayBlock extends HorizontalBlock {
	 
	public SimpleFourWayBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder.add(HORIZONTAL_FACING));
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		      return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
}
