package com.xX_deadbush_Xx.witchcraftmod.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;

import java.util.List;

public class EnergyCrystal extends Item implements IPlayerInventoryTickingItem {

	private int maxEnergy;
	private int energyStored;

	public EnergyCrystal(Properties properties, int maxEnergy) {
		super(properties);
		this.maxEnergy = maxEnergy;
		this.energyStored = maxEnergy;
	}
	
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("\u00A78Energy stored: " + getEnergyStored(stack)));
    }
    
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		context.getPlayer().getHeldItem(context.getHand()).damageItem(1, context.getPlayer(), (p)->{});
		return super.onItemUse(context);
	}
	@Override
	public void tick(ItemStack stack, PlayerEntity player, LogicalSide side) {
		//stack.setDamage(stack.getDamage() +1 );
	}

	public static int getEnergyStored(ItemStack stack) {
		if(!isStackACrystal(stack)) return -1;
		return ((EnergyCrystal) stack.getItem()).energyStored;
	}
	
	public static int getMaxEnergy(ItemStack stack) {
		if(!isStackACrystal(stack)) return -1;
		return ((EnergyCrystal) stack.getItem()).maxEnergy;
	}
	
	public static void setEnergyStored(ItemStack stack, int amount) {
		if(!isStackACrystal(stack)) return;
		((EnergyCrystal) stack.getItem()).energyStored = amount;
	}

	public static void addStoredEnergy(ItemStack stack, int amount) {
		if(!isStackACrystal(stack)) return;
		EnergyCrystal crystal = (EnergyCrystal) stack.getItem();
		if(crystal.energyStored + amount > crystal.maxEnergy) return;
		crystal.energyStored += amount;
	}

	public static void removeStoredEnergy(ItemStack stack, int amount) {
		if(!isStackACrystal(stack)) return;
		EnergyCrystal crystal = (EnergyCrystal) stack.getItem();
		crystal.energyStored -= amount;
		if(crystal.energyStored < 0)
			crystal.energyStored = 0;
	}

	public static boolean isCrystalEmpty(ItemStack stack) {
		if(!isStackACrystal(stack)) return false;
		return ((EnergyCrystal) stack.getItem()).energyStored < 0;
	}

	public static boolean isStackACrystal(ItemStack stack) {
		return stack != null && stack.getItem() instanceof EnergyCrystal;
	}

	public static EnergyCrystal getEnergyCrystalByStack(ItemStack stack) {
		if(!isStackACrystal(stack)) return null;
		return (EnergyCrystal) stack.getItem();
	}
	
	public static void removeEnergyFromPlayer(PlayerEntity player, int amount) {
		if(amount == 0) return;
		int amountleft = amount;

		for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(stack.getItem() instanceof EnergyCrystal) {
				int energy = EnergyCrystal.getEnergyStored(stack);
				if(energy == 0) continue;
				if(energy >= amountleft) {
					EnergyCrystal.setEnergyStored(stack, energy - amount);
					return;
				} else {
					amountleft -= energy;
					EnergyCrystal.setEnergyStored(stack, 0);
				}
			}
		}
	}
 }
