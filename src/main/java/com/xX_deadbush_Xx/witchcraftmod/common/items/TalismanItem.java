package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.common.world.data.CrystalEnergyStorage;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerCrystalEnergyProvider;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.LogicalSide;

public abstract class TalismanItem extends Item {

	public TalismanItem(Properties properties) {
		super(properties);
	}
	
	protected abstract int getTickInterval();
	
	public abstract int getManaCostPerTick();
	
	protected abstract boolean effectTick(ItemStack stack, PlayerEntity player, LogicalSide side);
	
	private static int readTickCounter(ItemStack stack) {
		CompoundNBT tag =  stack.getOrCreateTag();
		if(tag.contains("tick_counter")) {
			return tag.getInt("tick_counter");
		}
		
		return ((TalismanItem)stack.getItem()).getTickInterval();
	}
	
	public static void writeTickCounter(ItemStack stack, int tick) {
		stack.getOrCreateTag().putInt("tick_counter", tick);
	}


	public int tick(int totalEnergy, ItemStack stack, PlayerEntity player, LogicalSide side) {
		int interval = this.getTickInterval();
		if(interval <= 0) return 0;
		
		int tick = TalismanItem.readTickCounter(stack) - 1;
		if(tick > 0) {
			TalismanItem.writeTickCounter(stack, tick);
		} else {
			TalismanItem.writeTickCounter(stack, interval);
			if(this.effectTick(stack, player, side) && interval != 1) return this.getManaCostPerTick();
		}
		
		return 0;
	}
}
