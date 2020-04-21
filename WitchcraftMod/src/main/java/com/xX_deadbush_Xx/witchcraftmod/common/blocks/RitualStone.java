package com.xX_deadbush_Xx.witchcraftmod.common.blocks;

import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

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

public class RitualStone extends Block {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

	public RitualStone(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState());
	}
	
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (!worldIn.isRemote) {
	    	if(worldIn.isBlockPowered(pos)) {
	    		TileEntity tileEntity = worldIn.getTileEntity(pos);
	    		if (tileEntity instanceof RitualStoneTile) {
	    			RitualStoneTile altar = (RitualStoneTile) tileEntity;

	    		}
	    	}
	    }
	}
	
	@Override 
	public TileEntity createTileEntity(BlockState blockstate, IBlockReader world) {
		return ModTileEntities.RITUAL_STONE.get().create();		
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof RitualStoneTile) {
        	RitualStoneTile ritualStoneTile = ((RitualStoneTile) tileEntity);
        }
		return ActionResultType.SUCCESS;
	}
}
