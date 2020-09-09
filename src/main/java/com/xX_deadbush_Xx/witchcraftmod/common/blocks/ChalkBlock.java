package com.xX_deadbush_Xx.witchcraftmod.common.blocks;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.ModBlockStateProperties;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ChalkBlock extends Block {
   public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
   public static final BooleanProperty EAST = BlockStateProperties.EAST;
   public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
   public static final BooleanProperty WEST = BlockStateProperties.WEST;
   public static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;
   public static final EnumProperty<GlowType> GLOW_TYPE = ModBlockStateProperties.GLOW_TYPE;
   
   public static final Map<Direction, BooleanProperty> FACING_PROPERTY_MAP = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));
   protected static final VoxelShape[] SHAPES = new VoxelShape[]{Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D), Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D), Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Block.makeCuboidShape(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Block.makeCuboidShape(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 13.0D, 1.0D, 16.0D), Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Block.makeCuboidShape(0.0D, 0.0D, 3.0D, 16.0D, 1.0D, 16.0D), Block.makeCuboidShape(3.0D, 0.0D, 0.0D, 16.0D, 1.0D, 13.0D), Block.makeCuboidShape(3.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 13.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D)};

	public ChalkBlock(Properties properties) {
		super(properties);
	    this.setDefaultState(this.stateContainer.getBaseState().with(POWER, 0));
	}
	
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	    return SHAPES[getAABBIndex(state)];
   	}
    
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        return this.getDefaultState()
        		.with(WEST, this.getSide(iblockreader, blockpos, Direction.WEST))
        		.with(EAST, this.getSide(iblockreader, blockpos, Direction.EAST))
        		.with(NORTH, this.getSide(iblockreader, blockpos, Direction.NORTH))
        		.with(SOUTH, this.getSide(iblockreader, blockpos, Direction.SOUTH))
        		.with(GLOW_TYPE, GlowType.WHITE);
    }
    
    public static BlockState getStateForPlacement(IBlockReader worldIn, BlockPos pos) {
    	ChalkBlock instance = (ChalkBlock) ModBlocks.CHALK_BLOCK.get();
        BlockState state =  instance.getDefaultState()
        		.with(WEST, instance.getSide(worldIn, pos, Direction.WEST))
        		.with(EAST, instance.getSide(worldIn, pos, Direction.EAST))
        		.with(NORTH, instance.getSide(worldIn, pos, Direction.NORTH))
        		.with(SOUTH, instance.getSide(worldIn, pos, Direction.SOUTH))
        		.with(GLOW_TYPE, GlowType.WHITE);
	    return state;    
    }
    
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return blockstate.isSolidSide(worldIn, blockpos, Direction.UP);
    }
    
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
        	if (!state.isValidPosition(worldIn, pos)) {
        		worldIn.removeBlock(pos, false);
        	}
        }
    }
    
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, @Nullable BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.DOWN) {
           return stateIn;
        } else if(facing == Direction.UP) {
            return stateIn.with(WEST, this.getSide(worldIn, currentPos, Direction.WEST))
         		   .with(EAST, this.getSide(worldIn, currentPos, Direction.EAST))
         		   .with(NORTH, this.getSide(worldIn, currentPos, Direction.NORTH))
         		   .with(SOUTH, this.getSide(worldIn, currentPos, Direction.SOUTH));
        } else {
		   return stateIn.with(FACING_PROPERTY_MAP.get(facing), this.getSide(worldIn, currentPos, facing));
        }
     }
    
    private static int getAABBIndex(BlockState state) { //copied from vanilla redstone wire, gets the index of the shapes array from a blockstate
        int i = 0;
        boolean flag = state.get(NORTH) != false;
        boolean flag1 = state.get(EAST) != false;
        boolean flag2 = state.get(SOUTH) != false;
        boolean flag3 = state.get(WEST) != false;
        if (flag || flag2 && !flag && !flag1 && !flag3) {
           i |= 1 << Direction.NORTH.getHorizontalIndex();
        }

        if (flag1 || flag3 && !flag && !flag1 && !flag2) {
           i |= 1 << Direction.EAST.getHorizontalIndex();
        }

        if (flag2 || flag && !flag1 && !flag2 && !flag3) {
           i |= 1 << Direction.SOUTH.getHorizontalIndex();
        }

        if (flag3 || flag1 && !flag && !flag2 && !flag3) {
           i |= 1 << Direction.WEST.getHorizontalIndex();
        }

        return i;
     }
	
	private boolean getSide(IBlockReader worldIn, BlockPos pos, Direction face) {
		BlockPos blockpos = pos.offset(face);
	    BlockState blockstate = worldIn.getBlockState(blockpos);
	    return canConnectTo(blockstate, worldIn, blockpos, face);
	}
	
	protected static boolean canConnectTo(BlockState blockState, IBlockReader world, BlockPos pos, @Nullable Direction side) {
	    Block block = blockState.getBlock();
	    if(block.equals(ModBlocks.RITUAL_STONE.get())) return true;
	    if(block.equals(ModBlocks.CHALK_BLOCK.get())) return true;
	    return false;
	}
	
   /**
    * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
    * fine.
    */
   public BlockState rotate(BlockState state, Rotation rot) {
      switch(rot) {
      case CLOCKWISE_180:
         return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
      case COUNTERCLOCKWISE_90:
         return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
      case CLOCKWISE_90:
         return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
      default:
         return state;
      }
   }

   /**
    * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
    */
   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      switch(mirrorIn) {
      case LEFT_RIGHT:
         return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
      case FRONT_BACK:
         return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
      default:
         return super.mirror(state, mirrorIn);
      }
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(NORTH, EAST, SOUTH, WEST, GLOW_TYPE, POWER);
   }

   public static int getColor(BlockState state) {
	   return state.get(GLOW_TYPE).getColor(state.get(POWER));
   }
}
