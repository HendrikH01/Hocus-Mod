package com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.api.util.RitualHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

import net.minecraft.entity.player.PlayerEntity;

public abstract class RitualEffectHandler {
	
	public List<IRitualEffect> effects = new ArrayList<>();
	public final PlayerEntity player;
	public final RitualStoneTile ritualStone;
	private int ticks;
	
	protected RitualEffectHandler(RitualStoneTile tile, PlayerEntity player) {
		this.ritualStone = tile;
		this.player = player;
		
		init();
	}
	
	public abstract void init();
	
	public void tick() {
		this.ticks++;
		List<IRitualEffect> tickeffects = new ArrayList<>();
		List<Integer> indexes = new ArrayList<>();

		for(int i = 0; i < this.effects.size(); i++) {
			IRitualEffect effect = this.effects.get(i);
			System.out.println(effects.size() + " " + effect.getTick() + "  " + ticks);
			if(effect.getTick() == this.ticks) {
				tickeffects.add(effect);
				indexes.add(i);
			}
		}
		
		if(tickeffects.size() == 0) return;
		Collections.sort(tickeffects, new RitualHelper.PrioritySorter());
		
		for(IRitualEffect effect : tickeffects) {
			effect.tick();
		}
		
		//remove effects
		Collections.sort(indexes, Collections.reverseOrder());
		System.out.println(indexes + " " + ritualStone.getWorld().isRemote);
		for(int i : indexes) effects.remove(i);
		}
	
	public void stopEffect(boolean shouldDoPowerDownAnimation) {
		this.ritualStone.currentritual.ifPresent(ritual -> ritual.stopRitual(shouldDoPowerDownAnimation));
	}
	
	public void queueEffect(IRitualEffect effect, int ticksuntilfired) {
		this.addEffect(effect, this.ticks + ticksuntilfired);
	}
	
	public void addEffect(IRitualEffect effect, int ticks) {
		System.out.println(ticks + " " + effect);
		effect.setTick(ticks);
		this.effects.add(effect);
	}
	
	/*
	public CompoundNBT writeNBT(CompoundNBT compoundIn) {
		CompoundNBT nbt = new CompoundNBT();
		RitualEffect effect = this.effects.get(0);
		nbt.putInt("ticks", effect.ticksLeft);
		nbt.putInt("effectsleft", this.effects.size());
			
		compoundIn.put("animation", nbt);
		return compoundIn;
	}
	
	public RitualAnimation readNBT(CompoundNBT compoundIn) {
		return  this();
	}
	*/
	
}