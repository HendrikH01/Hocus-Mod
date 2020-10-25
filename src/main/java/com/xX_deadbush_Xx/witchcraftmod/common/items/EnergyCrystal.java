package com.xX_deadbush_Xx.witchcraftmod.common.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class EnergyCrystal extends Item {

	public EnergyCrystal(Properties properties, int maxEnergy) {
		super(properties.maxDamage(maxEnergy));
		
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.clear();
		tooltip.add(getDisplayName(stack));
		tooltip.add(new StringTextComponent("\u00A78Energy stored: " + (int)getEnergyStored(stack)));
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		ItemStack itemstack = context.getPlayer().getHeldItem(context.getHand());
		itemstack.damageItem(1, context.getPlayer(), (player) -> {});
		return ActionResultType.PASS;
	}

	public static double getEnergyStored(ItemStack stack) {
		return stack.getTag().contains("mana") ? stack.getTag().getDouble("mana") : stack.getMaxDamage();
	}

	public static double getMaxEnergy(ItemStack stack) {
		return (double)stack.getMaxDamage();
	}

	public static void setEnergyStored(ItemStack stack, double amount) {
		stack.getOrCreateTag().putDouble("mana", amount);
		stack.setDamage(stack.getMaxDamage() - (int)stack.getTag().getDouble("mana"));
	}

	public static void addStoredEnergy(ItemStack stack, double amount) {
		stack.getOrCreateTag().putDouble("mana", stack.getTag().contains("mana") ? stack.getTag().getDouble("mana") + amount : amount);
		stack.setDamage(stack.getMaxDamage() - (int)stack.getTag().getDouble("mana"));
	}

	public static boolean isStackEnergyCrystal(ItemStack stack) {
		return !stack.isEmpty() && stack.getItem() instanceof EnergyCrystal;
	}

	public static void removeEnergyFromPlayer(PlayerEntity player, double amount) {
		if(amount == 0) return;
		for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(stack.getItem() instanceof EnergyCrystal) {
				double energy = EnergyCrystal.getEnergyStored(stack);
				if(energy == 0) continue;
				if(energy >= amount) {
					EnergyCrystal.setEnergyStored(stack, energy - amount);
					return;
				} else {
					amount -= energy;
					EnergyCrystal.setEnergyStored(stack, 0);
				}
			}
		}
	}
 }
