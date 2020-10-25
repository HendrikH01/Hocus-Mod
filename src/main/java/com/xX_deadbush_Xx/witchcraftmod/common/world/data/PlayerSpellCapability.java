package com.xX_deadbush_Xx.witchcraftmod.common.world.data;

import com.xX_deadbush_Xx.witchcraftmod.common.items.wands.WandItem;
import com.xX_deadbush_Xx.witchcraftmod.common.network.HocusPacketHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.network.HocusSSpellCapUpdatePacket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerSpellCapability {
	
	@CapabilityInject(PlayerSpellCapability.class)
    public static net.minecraftforge.common.capabilities.Capability<PlayerSpellCapability> SPELL_CAP;
	
	private LazyOptional<ItemStack> activeWand = LazyOptional.empty();
	public int ticks = 0;
	public int[] args;
	
	public void setActiveWand(ItemStack wand, int... args) {
 		if(!(wand.getItem() instanceof WandItem)) throw new IllegalArgumentException("Attempted to set non WandItem '" + wand.toString() + "' as active wand!");
		else {
			this.activeWand = LazyOptional.of(() -> wand); 
			this.ticks = 0;
			this.args = args;
		}
	}
	
	public void stopSpell() {
		this.activeWand.invalidate();
	}
	
	public LazyOptional<ItemStack> getActiveWand() {
		return this.activeWand;
	}
	
	public boolean isPerformingSpell() {
		return this.activeWand.isPresent();
	}
	
	public static PlayerSpellCapability getSpellCap(PlayerEntity player) {
		return player.getCapability(SPELL_CAP).orElse(new PlayerSpellCapability());
	}

	public static net.minecraftforge.common.capabilities.Capability<PlayerSpellCapability> getCap() {
		return SPELL_CAP;
	}

	public void sendToClient(PlayerEntity player, World world) {
		HocusPacketHandler.sendToNearby(world, player, new HocusSSpellCapUpdatePacket(player, this));
	}
}
