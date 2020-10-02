package com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect;

import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

import net.minecraft.entity.player.PlayerEntity;

public abstract class BasicEffect implements IRitualEffect {
	protected final PlayerEntity player;
	protected final RitualStoneTile tile;
	private final int priority;
	private int tick;
	
	public BasicEffect(PlayerEntity player, RitualStoneTile tile, int priority) {
		this.player = player;
		this.tile = tile;
		this.priority = priority;
	}
	
	/**
	 * Default priority is zero
	 */
	public BasicEffect(PlayerEntity player, RitualStoneTile tile) {
		this(player, tile, 0);
	}
	
	@Override
	public int getTick() {
		return this.tick;
	}

	@Override
	public int getPriority() {
		return this.priority;
	}

	@Override
	public void tick() {
		this.execute();
	}

	@Override
	public void setTick(int ticks) {
		this.tick = ticks;
	}
	
	protected abstract void execute();
}
