package com.xX_deadbush_Xx.hocus.common.items.wands;

import com.xX_deadbush_Xx.hocus.common.items.IManaTickingItem;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerManaProvider;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerManaStorage;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.LogicalSide;

public abstract class ContinuousWandItem extends WandItem implements IManaTickingItem {

	public ContinuousWandItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public int tick(ItemStack stack, PlayerEntity player, LogicalSide side) {
		if(!canUseWand(stack, player, side)) {
			stopUse(stack, player);
			return 0;
		}
		else return IManaTickingItem.super.tick(stack, player, side);
	}
	
	@Override
	protected boolean attemptWandUse(PlayerEntity player, ItemStack wand) {
		if(super.attemptWandUse(player, wand)) {
			return PlayerManaProvider.getPlayerCapability(player).orElse(new PlayerManaStorage()).getEnergy() > this.getManaCostPerTick();
		} else return false;
	}
		
	protected abstract boolean canUseWand(ItemStack stack, PlayerEntity player, LogicalSide side);

	protected void stopUse(ItemStack wand, PlayerEntity player) {
		CompoundNBT tag = wand.getTag();
		tag.remove("progress");
		tag.remove("target");
		PlayerSpellCapability.getSpellCap(player).stopSpell();
	}
}
