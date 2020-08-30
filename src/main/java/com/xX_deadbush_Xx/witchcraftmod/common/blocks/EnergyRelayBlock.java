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

public class EnergyRelayBlock extends Block {
			private static final VoxelShape UP = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
			private static final VoxelShape DOWN = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
			private static final VoxelShape NORTH = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
			private static final VoxelShape EAST = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
			private static final VoxelShape SOUTH = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
			private static final VoxelShape WEST = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);

	public EnergyRelayBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public TileEntity createTileEntity(BlockState blockstate, IBlockReader world) {
		return ModTileEntities.ENERGY_RELAY_TILE.get().create();		
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return NORTH;
		/*
		switch ((AttachFace) state.get(FACE)) {
		case FLOOR:
			return UP;

		case WALL:
			switch ((Direction) state.get(HORIZONTAL_FACING)) {
			case EAST:
				return EAST;
			case WEST:
				return WEST;
			case SOUTH:
				return SOUTH;
			case NORTH:
			default:
				return NORTH;
			}

		default:
			return DOWN;
		}*/
	}

	public BlockPos getAttached(BlockPos pos, BlockState state) {
		return pos.down();// pos.offset(getFacing(state).getOpposite());
	}
}
