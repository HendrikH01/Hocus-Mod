package com.xX_deadbush_Xx.witchcraftmod.common.event;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.items.EnergyCrystal;
import com.xX_deadbush_Xx.witchcraftmod.common.items.IPlayerInventoryTickingItem;
import com.xX_deadbush_Xx.witchcraftmod.common.items.TalismanItem;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaStorage;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTick {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if(event.phase == TickEvent.Phase.END) {
			PlayerEntity player = event.player;
			PlayerManaStorage storage = player.getCapability(PlayerManaStorage.Capability.get(), null).orElse(null);
			if(storage == null) return;
			int inventoryEnergy = 0;
			int maxenergy = 0;
			int consumed = storage.getConsumedEnergy();
			
			Object2IntOpenHashMap<TalismanItem> talismans = new Object2IntOpenHashMap<>();
			
			for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				Item item = stack.getItem();

				if(item instanceof IPlayerInventoryTickingItem) {
					((IPlayerInventoryTickingItem) item).tick(stack, player, event.side);
					if(item instanceof EnergyCrystal) {
						inventoryEnergy += EnergyCrystal.getEnergyStored(stack);
						maxenergy += EnergyCrystal.getMaxEnergy(stack);
					} 
				} else if (item instanceof TalismanItem) {
					talismans.put((TalismanItem) item, i);
				}
			}

			for(TalismanItem talisman : talismans.keySet()) {
				if(talisman.getManaCostPerTick() <= inventoryEnergy) {
					ItemStack stack = player.inventory.getStackInSlot(talismans.getInt(talisman));
					consumed += talisman.tick(stack, player, event.side);
				}
			}

			EnergyCrystal.removeEnergyFromPlayer(player, consumed);
			storage.setEnergy(inventoryEnergy - consumed);
			storage.setMaxEnergy(maxenergy);
			storage.setConsumeZero();
		}
	}
}
