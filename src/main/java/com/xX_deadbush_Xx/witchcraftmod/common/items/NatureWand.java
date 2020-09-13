package com.xX_deadbush_Xx.witchcraftmod.common.items;

<<<<<<< master
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class NatureWand extends WandItem {
	
	private static final int GROW_RANGE = 3;
	
	public NatureWand(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		World worldIn = player.world;
		ItemStack wand = player.getHeldItem(context.getHand());
		
		if (onWandUse(player, wand)) {
			growPlants(worldIn, context.getPos());
			return ActionResultType.SUCCESS;
		}
			
		return ActionResultType.PASS;
	}
	
	private void growPlants(World worldIn, BlockPos startingPos) {
		if(worldIn.isRemote) return;

		for (int i = 0; i < GROW_RANGE; i++) {
			for (int j = 0; j < GROW_RANGE; j++) {
				BlockPos blockPos = new BlockPos(startingPos.getX() + i - (GROW_RANGE / 2), startingPos.getY(),
						startingPos.getZ() + j - (GROW_RANGE / 2));
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
=======
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.CrystalEnergyStorage;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerCrystalEnergyProvider;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
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
		
		int energyneeded = getEnergyPerUse(wand);
		CrystalEnergyStorage storage = PlayerCrystalEnergyProvider.getPlayerCapability(playerIn).orElse(null);

		if (storage == null)
			return ActionResultType.PASS;
		if (storage.getEnergy() < energyneeded)
			return ActionResultType.PASS;

		ActionResult<ItemStack> result = onWandUse(worldIn, playerIn, handIn, wand);
		
		// Grow plants inside area
		
		this.growPlants(worldIn, context.getPos());
		
		if (result.getType() == ActionResultType.SUCCESS)
			EnergyCrystal.removeEnergyFromPlayer(playerIn, energyneeded);
			playerIn.getCooldownTracker().setCooldown(this, getCooldown());
			
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack wand = playerIn.getHeldItem(handIn);
		return ActionResult.resultPass(wand);
	}
	
	@Override
	protected ActionResult<ItemStack> onWandUse(World worldIn, PlayerEntity player, Hand handIn, ItemStack wand) {
		return ActionResult.resultSuccess(wand);
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
	public int getEnergyPerUse(ItemStack wand) {
>>>>>>> 694e921 Nature Wand
		return 150;
	}


	@Override
	public int getCooldown() {
		return 10;
	}
 }