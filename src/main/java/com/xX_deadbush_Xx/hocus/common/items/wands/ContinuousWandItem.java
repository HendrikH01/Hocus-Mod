package com.xX_deadbush_Xx.hocus.common.items.wands;

import com.xX_deadbush_Xx.hocus.api.spell.SpellCast;
import com.xX_deadbush_Xx.hocus.common.items.IManaTickingItem;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerManaProvider;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;

public abstract class ContinuousWandItem<S extends SpellCast> extends WandItem<S> implements IManaTickingItem {

	public ContinuousWandItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public int tick(ItemStack stack, PlayerEntity player, LogicalSide side) {
		SpellCast spell = PlayerSpellCapability.getSpellCap(player).getSpell();
		if(spell != null || !getSpellClass().isInstance(spell))
			return 0;
		
		if(!canUseWand(stack, player, side)) {
			stopUse(stack, player);
			return 0;
		}
		else return IManaTickingItem.super.tick(stack, player, side);
	}
	
	@Override
	protected boolean attemptWandUse(PlayerEntity player, ItemStack wand) {
		if(super.attemptWandUse(player, wand)) {
			return PlayerManaProvider.getPlayerCapability(player).getEnergy() > this.getManaCostPerTick();
		} else return false;
	}
		
	protected abstract boolean canUseWand(ItemStack stack, PlayerEntity player, LogicalSide side);

	protected abstract Class<S> getSpellClass();
	
	protected void stopUse(ItemStack wand, PlayerEntity player) {
		PlayerSpellCapability.getSpellCap(player).stopSpell();
	}
}
