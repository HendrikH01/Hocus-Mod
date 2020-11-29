package com.xX_deadbush_Xx.hocus.common.blocks;

import java.util.Random;

import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;
import com.xX_deadbush_Xx.hocus.common.tile.AutoToolTableTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class AutoToolTable extends SimpleFourWayBlock {
	private static final ITextComponent NAME = new TranslationTextComponent("container.auto_tooltable");
	private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);

	public AutoToolTable(Properties properties) {
		super(properties);
	}
	
	@Override 
	public TileEntity createTileEntity(BlockState blockstate, IBlockReader world) {
		return ModTileEntities.AUTO_TOOLTABLE.get().create();		
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
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
			return new WorkbenchContainer(p_220270_2_, p_220270_3_, IWorldPosCallable.of(worldIn, pos));
	    }, NAME);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
		if (!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile != null && tile instanceof AutoToolTableTile) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (AutoToolTableTile) tile, pos);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.SUCCESS;
	}
}
