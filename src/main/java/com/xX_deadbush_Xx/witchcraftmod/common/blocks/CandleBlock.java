package com.xX_deadbush_Xx.witchcraftmod.common.blocks;
import java.util.Random;

import javax.annotation.Nullable;

import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.ModParticles;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.ModBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CandleBlock extends Block {
	   public static final IntegerProperty CANDLECOUNT = ModBlockStateProperties.CANDLES_1_3;
	   protected static final VoxelShape ONE_SHAPE = Block.makeCuboidShape(6.5D, 0.0D, 6.5D, 9.5D, 6.0D, 9.5D);
	   protected static final VoxelShape TWO_SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
	   protected static final VoxelShape THREE_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);
	   
	public CandleBlock(Properties properties) {
		super(properties);
	    this.setDefaultState(this.stateContainer.getBaseState().with(CANDLECOUNT, 1));
	}
	
    /**
     * Amount of light emitted
     * @deprecated prefer calling {@link IBlockState#getLightValue()}
     */
    public int getLightValue(BlockState state) {
    	return super.getLightValue(state) + Math.min(11, 4 * state.get(CANDLECOUNT));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
    	BlockState blockstate = context.getWorld().getBlockState(context.getPos());
    	if (blockstate.getBlock() == this) {
    		return blockstate.with(CANDLECOUNT, Math.min(3, blockstate.get(CANDLECOUNT) + 1));
    	} else {
    		return this.getDefaultState().with(CANDLECOUNT, 1);
    	}
    }
    
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
    	return useContext.getItem().getItem() == this.asItem() && state.get(CANDLECOUNT) < 3 ? true : super.isReplaceable(state, useContext);
    }
	
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
       double x = (double)pos.getX();
       double y = (double)pos.getY();
       double z = (double)pos.getZ();
       worldIn.addParticle(ParticleTypes.SMOKE, x+0.5D, y+0.7D, z+0.5D, 0.0D, 0.02D, 0.0D);
       switch(state.get(CANDLECOUNT)) {
       case 1: {
           worldIn.addParticle(ModParticles.SMALL_FLAME, x+0.5D, y+0.6D, z+0.5D, 0.0D, 0.0D, 0.0D);
    	   break;
       }
       case 2: {
           worldIn.addParticle(ModParticles.SMALL_FLAME, x+0.38D, y+0.51D, z+0.44D, 0.0D, 0.0D, 0.0D);
           worldIn.addParticle(ModParticles.SMALL_FLAME, x+0.66D, y+0.57D, z+0.6D, 0.0D, 0.0D, 0.0D);
    	   break;
       }
       case 3: {
           worldIn.addParticle(ModParticles.SMALL_FLAME, x+0.31D, y+0.41D, z+0.44D, 0.0D, 0.0D, 0.0D);
           worldIn.addParticle(ModParticles.SMALL_FLAME, x+0.59D, y+0.47D, z+0.66D, 0.0D, 0.0D, 0.0D);
           worldIn.addParticle(ModParticles.SMALL_FLAME, x+0.66D, y+0.56D, z+0.34, 0.0D, 0.0D, 0.0D);
    	   break;
       }
       }
	}

   	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
	   return hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
   	}
   	
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(CANDLECOUNT)) {
	        case 1: return ONE_SHAPE;
	        case 2: return TWO_SHAPE;
	        case 3: return THREE_SHAPE;
	        default: return ONE_SHAPE;
        }
    }
    
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CANDLECOUNT);
    }
}
