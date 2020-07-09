package com.xX_deadbush_Xx.witchcraftmod.common.event;

import com.xX_deadbush_Xx.witchcraftmod.common.items.EnergyCrystal;
import com.xX_deadbush_Xx.witchcraftmod.common.items.IPlayerInventoryTickingItem;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.CrystalEnergyStorage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTick {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if(event.phase == TickEvent.Phase.END) {
			PlayerEntity player = event.player;
			int energy = 0;
			int maxenergy = 0;

			for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				if(stack.getItem() instanceof IPlayerInventoryTickingItem) {
					((IPlayerInventoryTickingItem) stack.getItem()).tick(stack, player, event.side);
					if(stack.getItem() instanceof EnergyCrystal) {
						energy += EnergyCrystal.getEnergyStored(stack);
						maxenergy += EnergyCrystal.getMaxEnergy(stack);
					}
				}
			}

			CrystalEnergyStorage storage = player.getCapability(CrystalEnergyStorage.Capability.get(), null).orElse(null);
			if(storage == null) return;
			storage.setEnergy(energy);
			storage.setMaxEnergy(maxenergy);
		}
	}
}
