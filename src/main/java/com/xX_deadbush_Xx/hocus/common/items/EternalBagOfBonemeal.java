package com.xX_deadbush_Xx.hocus.common.items;

import com.xX_deadbush_Xx.hocus.common.items.wands.WandItem;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EternalBagOfBonemeal extends WandItem {
	
	private static final int GROW_RANGE = 3;
	
	public EternalBagOfBonemeal(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		World worldIn = player.world;
		ItemStack wand = player.getHeldItem(context.getHand());
		
		if (attemptWandUse(player, wand)) {
			growPlants(worldIn, context.getPos());
			return ActionResultType.SUCCESS;
		}
			
		return ActionResultType.PASS;
	}
	
	private void growPlants(World worldIn, BlockPos startingPos) {
		if(worldIn.isRemote) return;

		for (int i = 0; i < GROW_RANGE; i++) {
			for (int j = 0; j < GROW_RANGE; j++) {
				BlockPos blockPos = new BlockPos(startingPos.getX() + i - (GROW_RANGE / 2), startingPos.getY(), startingPos.getZ() + j - (GROW_RANGE / 2));
				BlockState blockstate = worldIn.getBlockState(blockPos);

				if (blockstate.getBlock() instanceof IGrowable) {
					IGrowable igrowable = (IGrowable) blockstate.getBlock();
					if (igrowable.canGrow(worldIn, blockPos, blockstate, worldIn.isRemote)) {
						if (igrowable.canUseBonemeal(worldIn, worldIn.rand, blockPos, blockstate)) {
							igrowable.grow((ServerWorld) worldIn, worldIn.rand, blockPos, blockstate);
						}
					}
				}
			}
		}
	}
	
	@Override
	public int getEnergyPerUse() {
		return 150;
	}


	@Override
	public int getCooldown() {
		return 10;
	}
 }