package com.xX_deadbush_Xx.witchcraftmod.common.blocks;

import javax.annotation.Nullable;

import com.xX_deadbush_Xx.witchcraftmod.common.items.LinkingWand;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.DryingRackTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class DryingRackBlock extends Block {

	private static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(2.0D, 0.0D, 0.0D, 14.0D, 16.0D, 16.0D);
	private static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 16.0D, 16.0D, 14.0D);


	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	public DryingRackBlock(Properties properties) {
		super(properties);
	}
	
	public static Direction getDirection(BlockState state) {
		return state.get(FACING);
	}
	
	@Override
	public TileEntity createTileEntity(@Nullable BlockState blockstate, IBlockReader world) {
		return ModTileEntities.DRYING_RACK.get().create();		
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch(state.get(FACING)) {
		case NORTH:
			return SHAPE_NORTH;
		case WEST:
			return SHAPE_WEST;
		case SOUTH:
			return SHAPE_NORTH;
		case EAST:
			return SHAPE_WEST;
		default:
			return SHAPE_NORTH;
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
		if(player.getHeldItemMainhand().getItem() instanceof LinkingWand) {
			return ActionResultType.PASS;
		}
		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof DryingRackTile) {
            ((DryingRackTile) tileEntity).swapItems(worldIn, pos, player);
        }
		return ActionResultType.SUCCESS;
	}
}
