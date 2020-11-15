package com.xX_deadbush_Xx.hocus.common.spell;

import com.xX_deadbush_Xx.hocus.api.spell.ISpellRenderer;
import com.xX_deadbush_Xx.hocus.api.spell.SpellCast;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class NoSpell extends SpellCast {

	public NoSpell(PlayerEntity player, ItemStack wand, int... args) {
		super(player, wand, args);
	}

	public NoSpell(int ticks, int... args) {
		super(ticks, args);
	}

	@Override
	public ISpellRenderer<? extends SpellCast> getRenderer() {
		return null;
	}
	
	@Override
	public void tick(PlayerEntity player) {}
}
