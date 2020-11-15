package com.xX_deadbush_Xx.hocus.common.blocks;

import java.util.Random;

import com.xX_deadbush_Xx.hocus.common.blocks.blockstate.ModBlockStateProperties;
import com.xX_deadbush_Xx.hocus.common.potion.ModPotions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BelladonnaFlower extends FlowerBlock implements IGrowable {

	public static final BooleanProperty HAS_BERRIES = ModBlockStateProperties.BERRIES;
	
	public BelladonnaFlower(Effect effectIn, int effectDuration, Properties properties) {
		super(effectIn, effectDuration, properties);
	}
	
	
	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity) {
		if(entity instanceof LivingEntity && entity.getType() != EntityType.WITCH && entity.getType() != EntityType.SKELETON && entity.getType() != EntityType.VEX) {
			((LivingEntity)entity).addPotionEffect(new EffectInstance(ModPotions.BELLADONNA_POISION, 100));
		}
	}
	

	@SuppressWarnings("deprecation")
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		boolean berries = state.get(HAS_BERRIES);

		if (berries) {
			spawnAsEntity(worldIn, pos, new ItemStack(Items.SWEET_BERRIES, ((int) Math.random() * 3)));
			worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
			worldIn.setBlockState(pos, state.with(HAS_BERRIES, Boolean.valueOf(false)), 2);
			return ActionResultType.SUCCESS;
			
		} else {
			return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		}
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HAS_BERRIES);
	}

	/**
	 * Whether this IGrowable can grow
	 */
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return !state.get(HAS_BERRIES);
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		worldIn.setBlockState(pos, state.with(HAS_BERRIES, Boolean.valueOf(true)), 2);
	}
}
