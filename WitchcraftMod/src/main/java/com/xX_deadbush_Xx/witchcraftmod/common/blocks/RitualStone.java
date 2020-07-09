package com.xX_deadbush_Xx.witchcraftmod.common.blocks;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.RitualActivationHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.ModBlockStateProperties;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModItems;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class RitualStone extends Block implements IRitualBlock {
	   public static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;
	   public static final EnumProperty<GlowType> GLOW_TYPE = ModBlockStateProperties.GLOW_TYPE;
	   
	   private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

	public RitualStone(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState());
	}
	
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (!worldIn.isRemote) {
	    	if(worldIn.isBlockPowered(pos)) {
	    		TileEntity tileEntity = worldIn.getTileEntity(pos);
	    		if (tileEntity instanceof AbstractRitualCore) {
	    			AbstractRitualCore altar = (AbstractRitualCore) tileEntity;

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
		if(worldIn.isRemote) return ActionResultType.SUCCESS;
		if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
	        if (tileEntity instanceof AbstractRitualCore) {
	        	AbstractRitualCore ritualStoneTile = ((AbstractRitualCore) tileEntity);
	        	if(player.getHeldItemMainhand().getItem().equals(ModItems.RITUAL_ACTIVATOR.get())) {
	        		if(ritualStoneTile.currentritual == null) ritualStoneTile.activateRitual(worldIn, player, RitualActivationHandler.getRitual(ritualStoneTile, player));
	        		else ritualStoneTile.currentritual.stopRitual(true);
	        	}
	        	else ritualStoneTile.swapItems(worldIn, player);
	        }
			return ActionResultType.SUCCESS;
		} else return ActionResultType.PASS;
	} 
	
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(GLOW_TYPE, POWER);
	}

	public static int getColor(BlockState state) {
		return state.get(GLOW_TYPE).getColor(state.get(POWER));
	}
}