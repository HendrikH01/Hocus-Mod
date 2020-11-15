package com.xX_deadbush_Xx.hocus.common.potion;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class CurseOfTheFallenAngel extends Effect {
	public CurseOfTheFallenAngel() {
		super(EffectType.HARMFUL, 0xE82A00);
	}
	
	/**
	 * This is moved to PlayerTickEvent.POST
	 */
	@Override
	public void performEffect(LivingEntity entity, int amplifier) {}
	
	@Override
	public List<ItemStack> getCurativeItems() {
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(Items.GOLDEN_CARROT));
		return list;
	}
}
