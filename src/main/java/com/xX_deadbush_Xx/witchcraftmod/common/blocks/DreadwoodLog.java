package com.xX_deadbush_Xx.witchcraftmod.common.blocks;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.ModBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class DreadwoodLog extends LogBlock {
	public static final IntegerProperty TYPE = ModBlockStateProperties.TYPE_0_3;

	public DreadwoodLog(MaterialColor verticalColorIn, Properties properties) {
		super(verticalColorIn, properties);
	    this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y).with(TYPE, 0));
	}
	
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
	    builder.add(TYPE, AXIS);
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
	   	return this.getDefaultState().with(AXIS, context.getFace().getAxis()).with(TYPE, 0);
	}
}