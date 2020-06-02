package com.xX_deadbush_Xx.witchcraftmod.common.blocks;

import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualPedestalTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class MortarBlock extends Block {

	public MortarBlock(Properties properties) {
		super(properties);
	}
	
	private static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
	
	@Override
	public TileEntity createTileEntity(BlockState blockstate, IBlockReader world) {
		return ModTileEntities.RITUAL_PEDESTAL.get().create();		
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof RitualPedestalTile) {
            ((RitualPedestalTile) tileEntity).swapItems(worldIn, pos, player);
        }
		return ActionResultType.SUCCESS;
	}
	
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
