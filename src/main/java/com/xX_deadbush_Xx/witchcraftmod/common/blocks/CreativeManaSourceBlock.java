package com.xX_deadbush_Xx.witchcraftmod.common.blocks;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CreativeManaSourceBlock extends Block {

	public CreativeManaSourceBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public TileEntity createTileEntity(BlockState blockstate, IBlockReader world) {
		return ModTileEntities.CREATIVE_MANA_SOURCE.get().create();		
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

}
