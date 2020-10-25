package com.xX_deadbush_Xx.witchcraftmod.common.event;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.items.EnergyCrystal;
import com.xX_deadbush_Xx.witchcraftmod.common.items.IPlayerInventoryTickingItem;
import com.xX_deadbush_Xx.witchcraftmod.common.items.IManaTickingItem;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaStorage;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerSpellCapability;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus=Bus.FORGE)
public class PlayerTick {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if(event.phase == TickEvent.Phase.END) {
			//Energy
			player.getCapability(PlayerManaStorage.getCap(), null).ifPresent(storage -> {
				double inventoryEnergy = 0;
				double maxenergy = 0;
				double consumed = storage.getConsumedEnergy();
				
				Object2IntOpenHashMap<IManaTickingItem> talismans = new Object2IntOpenHashMap<>();
				
				for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
					ItemStack stack = player.inventory.getStackInSlot(i);
					Item item = stack.getItem();
					if(item instanceof EnergyCrystal) {
						inventoryEnergy += EnergyCrystal.getEnergyStored(stack);
						maxenergy += EnergyCrystal.getMaxEnergy(stack);
					} 
					else if(item instanceof IPlayerInventoryTickingItem) {
						((IPlayerInventoryTickingItem) item).tick(stack, player, event.side);
					} else if (item instanceof IManaTickingItem) {
						talismans.put((IManaTickingItem) item, i);
					}
				}

				for(IManaTickingItem item : talismans.keySet()) {
					if(item.getManaCostPerTick() <= inventoryEnergy) {
						ItemStack stack = player.inventory.getStackInSlot(talismans.getInt(item));
						consumed += item.tick(stack, player, event.side);
					}
				}

				EnergyCrystal.removeEnergyFromPlayer(player, consumed);
				storage.setEnergy(inventoryEnergy - consumed);
				storage.setMaxEnergy(maxenergy);
				storage.setConsumeZero();
			});			
		} else {
			//Spell cap
			player.getCapability(PlayerSpellCapability.getCap(), null).ifPresent(spell -> {
				spell.ticks++;
			});
		}
	}
}
