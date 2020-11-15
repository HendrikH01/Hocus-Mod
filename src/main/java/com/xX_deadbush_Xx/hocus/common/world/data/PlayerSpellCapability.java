package com.xX_deadbush_Xx.hocus.common.world.data;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.api.spell.SpellCast;
import com.xX_deadbush_Xx.hocus.common.items.wands.WandItem;
import com.xX_deadbush_Xx.hocus.common.network.HocusPacketHandler;
import com.xX_deadbush_Xx.hocus.common.network.HocusSSpellCapUpdatePacket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

//TODO: clean this spaghetti code

public class PlayerSpellCapability {
	
	@CapabilityInject(PlayerSpellCapability.class)
    public static net.minecraftforge.common.capabilities.Capability<PlayerSpellCapability> SPELL_CAP;
	
	private LazyOptional<ItemStack> activeWand = LazyOptional.empty();
	public SpellCast spell;
	
	@SuppressWarnings("unchecked")
	public void setActiveWand(PlayerEntity caster, ItemStack wand, int... args) {
 		if(!(wand.getItem() instanceof WandItem)) {
 			Hocus.LOGGER.warn("Server and Client out of sync! Attempted to set non WandItem '" + wand.toString() + "' as active wand!");
 		}

		this.activeWand = LazyOptional.of(() -> wand); 
		this.spell = ((WandItem<? extends SpellCast>)wand.getItem()).getSpell(caster, wand, args);
	
		HocusPacketHandler.sendToAll(caster.world, new HocusSSpellCapUpdatePacket(caster, this));
	}
	
	public void setActiveWand(ItemStack wand, SpellCast spell) {
 		if(!(wand.getItem() instanceof WandItem)) {
 			Hocus.LOGGER.warn("Server and Client out of sync! Attempted to set non WandItem '" + wand.toString() + "' as active wand!");
 		}

		this.activeWand = LazyOptional.of(() -> wand); 
		this.spell = spell;
	}
	
	public SpellCast getSpell() {
		return spell;
	}
	
	public void stopSpell() {
		this.activeWand.invalidate();
		this.spell = null;
	}
	
	public LazyOptional<ItemStack> getActiveWand() {
		return this.activeWand;
	}
	
	public boolean isPerformingSpell() {
		return this.activeWand.isPresent() && this.spell != null;
	}
	
	public static PlayerSpellCapability getSpellCap(PlayerEntity player) {
		return player.getCapability(SPELL_CAP).orElse(new PlayerSpellCapability());
	}

	public static net.minecraftforge.common.capabilities.Capability<PlayerSpellCapability> getCap() {
		return SPELL_CAP;
	}

	public static void sendToClient(ServerPlayerEntity player, World world) {
		PlayerSpellCapability cap = getSpellCap(player);
		if(!cap.isPerformingSpell()) return;
		HocusPacketHandler.sendToAll(world, new HocusSSpellCapUpdatePacket(player, cap));
	}


}
