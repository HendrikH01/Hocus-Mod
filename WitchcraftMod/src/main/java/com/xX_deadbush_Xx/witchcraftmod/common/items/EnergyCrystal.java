package com.xX_deadbush_Xx.witchcraftmod.common.items;

import java.util.List;

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

public class EnergyCrystal extends Item implements IPlayerInventoryTickingItem {
	public EnergyCrystal(Properties properties, int maxEnergy) {
		super(properties.maxDamage(maxEnergy));
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
		return stack.getMaxDamage() - stack.getDamage();
	}
	
	public static int getMaxEnergy(ItemStack stack) {
		return stack.getMaxDamage();
	}
	
	private static void setEnergyStored(ItemStack stack, int amount) {
		stack.setDamage(stack.getMaxDamage() - amount);
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