package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.common.world.data.CrystalEnergyStorage;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerCrystalEnergyProvider;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class WandItem extends Item implements IWandItem {

	public WandItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack wand = playerIn.getHeldItem(handIn);
			
		int energyneeded = getEnergyPerUse(wand);
		CrystalEnergyStorage storage = PlayerCrystalEnergyProvider.getPlayerCapability(playerIn).orElse(null);

		if (storage == null)
			return ActionResult.resultPass(wand);
		if (storage.getEnergy() <= energyneeded)
			return ActionResult.resultPass(wand);

		ActionResult<ItemStack> result = onWandUse(worldIn, playerIn, handIn, wand);
		if (result.getType() == ActionResultType.SUCCESS)
			EnergyCrystal.removeEnergyFromPlayer(playerIn, energyneeded);
			playerIn.getCooldownTracker().setCooldown(this, getCooldown());

		return result;
	}

	protected abstract ActionResult<ItemStack> onWandUse(World worldIn, PlayerEntity player, Hand handIn,
			ItemStack wand);


}