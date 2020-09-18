package com.xX_deadbush_Xx.witchcraftmod.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FireBowlBlock extends Block {
	 
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	private static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 10.0D, 15.0D);
	
	public FireBowlBlock(Properties properties) {
		super(properties);
		this.stateContainer.getBaseState().with(LIT, Boolean.valueOf(true));
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return super.propagatesSkylightDown(state, reader, pos);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack stack = player.getHeldItem(handIn);
		if(!state.get(LIT)) {
			if(stack.getItem() instanceof FlintAndSteelItem) {
				worldIn.setBlockState(pos, state.with(LIT, true));
				stack.damageItem(1, player, (playerentity) -> {
		               playerentity.sendBreakAnimation(handIn);
				});
				return ActionResultType.SUCCESS;
			}
		} else if(stack.isEmpty()) {
			worldIn.setBlockState(pos, state.with(LIT, false));
            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.get(LIT)) {
			if (rand.nextInt(10) == 0) {
				worldIn.playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F),
						(double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS,
						0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
			}
			for(int i = 0; i < 3; i++) {
			    worldIn.addParticle(ParticleTypes.SMOKE, true, (double)pos.getX() + 0.5D + rand.nextDouble() / 3.0D * (double)(rand.nextBoolean() ? 1 : -1), (double)pos.getY() + rand.nextDouble() + rand.nextDouble(), (double)pos.getZ() + 0.5D + rand.nextDouble() / 3.0D * (double)(rand.nextBoolean() ? 1 : -1), 0.0D, rand.nextDouble()/50, 0.0D);
			}
		}
	}

	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!entityIn.isImmuneToFire() && state.get(LIT) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
			entityIn.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
		}

		super.onEntityCollision(state, worldIn, pos, entityIn);
	}

	@Override
	public int getLightValue(BlockState state) {
		return state.get(LIT) ? 10 : 0;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}
}
