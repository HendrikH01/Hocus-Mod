package com.xX_deadbush_Xx.hocus.common.blocks;

import com.xX_deadbush_Xx.hocus.api.util.ItemStackHelper;
import com.xX_deadbush_Xx.hocus.common.blocks.blockstate.ModBlockStateProperties;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;
import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;
import com.xX_deadbush_Xx.hocus.common.tile.MortarTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
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
	
	public static final IntegerProperty OIL_FILLLEVEL = ModBlockStateProperties.OIL_FILLLEVEL;
	
	public MortarBlock(Properties properties) {
		super(properties);
		setDefaultState(getDefaultState().with(OIL_FILLLEVEL, 0));
	}
	
	private static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 7.0D, 12.0D);
	
	@Override
	public TileEntity createTileEntity(BlockState blockstate, IBlockReader world) {
		return ModTileEntities.MORTAR_TILE.get().create();		
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
		if(handIn != Hand.MAIN_HAND) return ActionResultType.PASS;
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		ItemStack stack = player.getHeldItem(handIn);
        if (tileEntity instanceof MortarTile) {
            if(stack.getItem() == ModItems.PESTLE.get()) ((MortarTile) tileEntity).attemptCrafting();
            else if(stack.getItem() == Items.BOWL) {
            	int level = worldIn.getBlockState(pos).get(OIL_FILLLEVEL);
            	if(level == 0) return ActionResultType.PASS;
            	worldIn.setBlockState(pos, this.getDefaultState().with(OIL_FILLLEVEL, level - 1), 3);
            	if(!player.addItemStackToInventory(new ItemStack(ModItems.PLANT_OIL.get()))) {
            		ItemStackHelper.spawnItem(worldIn, new ItemStack(ModItems.PLANT_OIL.get()), pos);
            	}
            }
            else ((MortarTile) tileEntity).swapItems(worldIn, pos, player);
    		return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
	}
	
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(OIL_FILLLEVEL);
	}
}
