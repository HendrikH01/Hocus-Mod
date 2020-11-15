package com.xX_deadbush_Xx.hocus.common.event;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.items.EnergyCrystal;
import com.xX_deadbush_Xx.hocus.common.items.IManaTickingItem;
import com.xX_deadbush_Xx.hocus.common.items.IPlayerInventoryTickingItem;
import com.xX_deadbush_Xx.hocus.common.items.talisman.TalismanItem;
import com.xX_deadbush_Xx.hocus.common.potion.ModPotions;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerManaProvider;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerManaStorage;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Hocus.MOD_ID, bus=Bus.FORGE)
public class PlayerTick {
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if(event.phase == TickEvent.Phase.END) {
			//Energy
			PlayerManaStorage storage = PlayerManaProvider.getPlayerCapability(player);
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
					} else if(item instanceof TalismanItem) {
						ItemStack stack = player.inventory.getStackInSlot(talismans.getInt(item));
						((TalismanItem)item).inactiveTick(stack, player, event.side);
					}
				}

				EnergyCrystal.removeEnergyFromPlayer(player, consumed);
				storage.setEnergy(inventoryEnergy - consumed);
				storage.setMaxEnergy(maxenergy);
				storage.setConsumeZero();
			
			
			//Anti-flight
			if (!player.abilities.isCreativeMode && player.isPotionActive(ModPotions.FALLEN_ANGEL_CURSE))
				player.abilities.allowFlying = false;
		} else {
			//Spell cap tick
			PlayerSpellCapability cap = PlayerSpellCapability.getSpellCap(player);
			if(cap.getSpell() != null) {
				cap.getSpell().tick(player);
			} else {
				cap.stopSpell();
			}
		}
	}
}
