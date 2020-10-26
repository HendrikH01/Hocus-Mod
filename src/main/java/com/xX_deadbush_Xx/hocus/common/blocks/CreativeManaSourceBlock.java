package com.xX_deadbush_Xx.hocus.common.blocks;

import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
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
