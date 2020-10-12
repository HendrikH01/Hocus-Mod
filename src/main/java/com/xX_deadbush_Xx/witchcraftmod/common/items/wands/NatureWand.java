package com.xX_deadbush_Xx.witchcraftmod.common.items.wands;

import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaProvider;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaStorage;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class NatureWand extends WandItem {
	
	private int range = 3;
	
	public NatureWand(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		PlayerEntity playerIn = context.getPlayer();
		World worldIn = playerIn.world;
		Hand handIn = context.getHand();
		ItemStack wand = playerIn.getHeldItem(handIn);
		
		int energyneeded = getEnergyPerUse();
		PlayerManaStorage storage = PlayerManaProvider.getPlayerCapability(playerIn).orElse(null);

		if (storage == null)
			return ActionResultType.PASS;
		if (storage.getEnergy() < energyneeded)
			return ActionResultType.PASS;

		boolean result = attemptWandUse(playerIn, wand);
		
		// Grow plants inside area
		
		
		if (result) {
			this.growPlants(worldIn, context.getPos());
			return ActionResultType.SUCCESS;
		}
		
		return ActionResultType.PASS;
	}

	private void growPlants(World worldIn, BlockPos startingPos) {
		for(int i = 0; i < range; i ++) {
			System.out.println(i - (range / 2));
			for(int j = 0; j < range; j ++) {
				BlockPos blockPos = new BlockPos(startingPos.getX() + i - (range / 2), startingPos.getY(), startingPos.getZ() + j - (range / 2));
				BlockState blockstate = worldIn.getBlockState(blockPos);
				if (blockstate.getBlock() instanceof IGrowable) {
					IGrowable igrowable = (IGrowable) blockstate.getBlock();
					if (igrowable.canGrow(worldIn, blockPos, blockstate, worldIn.isRemote)) {
						if (worldIn instanceof ServerWorld) {
							if (igrowable.canUseBonemeal(worldIn, worldIn.rand, blockPos, blockstate)) {
								igrowable.grow((ServerWorld) worldIn, worldIn.rand, blockPos, blockstate);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public int getCooldown() {
		return 10;
	}

	@Override
	public int getEnergyPerUse() {
		return 150;
	}
 }