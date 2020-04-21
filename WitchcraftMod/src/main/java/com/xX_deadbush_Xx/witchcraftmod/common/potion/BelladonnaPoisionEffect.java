package com.xX_deadbush_Xx.witchcraftmod.common.potion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class BelladonnaPoisionEffect extends Effect {
	public BelladonnaPoisionEffect() {
		super(EffectType.HARMFUL, 0x3D2444);
	}
	
	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
		//System.out.println(entityLivingBaseIn);
        entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 0.5F + 0.5F * amplifier);
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		int j = 30 >> amplifier;
     	if (j > 0) {
     		return duration % j == 0;
     	} else {
     		return true;
     	}	
	}
	
	@Override
	public List<ItemStack> getCurativeItems() {
		List<ItemStack> list = new ArrayList<>();
		list.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1));
		return list;
	}
}
