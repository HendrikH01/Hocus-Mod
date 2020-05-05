package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

import net.minecraft.entity.player.PlayerEntity;

public abstract class RitualAnimation {
	public List<RitualEffect> effects = new ArrayList<>();
	private PlayerEntity player;
	private RitualStoneTile ritualStone;
	
	protected RitualAnimation(RitualStoneTile tile, PlayerEntity player) {
		this.ritualStone = tile;
		this.player = player;
		initAnimation();
	}
	
	public void addEffect(BiConsumer<RitualStoneTile, PlayerEntity> callback, int duration) {
		this.effects.add(new RitualEffect(callback, duration));
	}
	
	public void tick() { //called in an instance that is deleted after usage
		RitualEffect currentEffect = effects.get(0);

		if(currentEffect.ticksLeft < 1) {
			effects.remove(0);

			currentEffect.effect.accept(this.ritualStone, this.player);

			if(effects.size() < 1) {
				this.ritualStone.animationHandler.remove();
			}
		} else {
			currentEffect.ticksLeft--;
		}
	}
	
	public abstract void initAnimation();
	
	private class RitualEffect {
		private BiConsumer<RitualStoneTile, PlayerEntity> effect;
		private int ticksLeft;

		public RitualEffect(BiConsumer<RitualStoneTile, PlayerEntity> callback, int duration) {
			this.effect = callback;
			this.ticksLeft = duration;
		}
	}
}