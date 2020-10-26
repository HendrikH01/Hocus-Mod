package com.xX_deadbush_Xx.hocus.common.blocks;

import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;
import com.xX_deadbush_Xx.hocus.common.tile.EnergyRelayTile;
import com.xX_deadbush_Xx.hocus.common.world.data.TileEntityManaStorage;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class EnergyRelayBlock extends Block {
	private static final VoxelShape UP = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
	private static final VoxelShape DOWN = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
	private static final VoxelShape NORTH = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
	private static final VoxelShape EAST = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
	private static final VoxelShape SOUTH = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
	private static final VoxelShape WEST = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
	
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	
	public EnergyRelayBlock(Properties properties) {
		super(properties);
		setDefaultState(stateContainer.getBaseState().with(POWERED, false));
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

	public boolean canProvidePower(BlockState p_149744_1_) {
		return true;
	}

	public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_,
			BlockPos p_220069_5_, boolean p_220069_6_) {
		if (!p_220069_2_.isRemote) {
			boolean flag = p_220069_1_.get(POWERED);
			if (flag != p_220069_2_.isBlockPowered(p_220069_3_)) {
				if (flag) {
					p_220069_2_.getPendingBlockTicks().scheduleTick(p_220069_3_, this, 4);
				} else {
					p_220069_2_.setBlockState(p_220069_3_, p_220069_1_.cycle(POWERED), 2);
				}
			}

		}
	}
	
	@Override
	public int getComparatorInputOverride(BlockState state, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof EnergyRelayTile) {
			return (int) (tile.getCapability(TileEntityManaStorage.getCap()).orElse(new TileEntityManaStorage(0, 0, 0)).getEnergy() / EnergyRelayTile.CAPACITY * 15);
		}
		return 0;
	}
	
	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}
}
