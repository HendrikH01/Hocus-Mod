package com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect;

import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

import net.minecraft.entity.player.PlayerEntity;

public abstract class RepeatingEffect implements IRitualEffect {
	protected final PlayerEntity player;
	protected final RitualStoneTile tile;
	private int priority;
	private int tick;
	private int repeatsleft;
	private int repeatdelay;
	private RitualEffectHandler handler;
	
	public RepeatingEffect(RitualEffectHandler effecthandler, int priority, int repeatamount, int repeatdelay) {
		this.player = effecthandler.player;
		this.tile = effecthandler.ritualStone;
		this.priority = priority;
		this.repeatsleft = repeatamount;
		this.repeatdelay = repeatdelay;
		this.handler = effecthandler;
	}
	
	public RepeatingEffect(RitualEffectHandler effecthandler, int priority, int repeatamount) {
		this(effecthandler, priority, repeatamount, 1);
	}


	@Override
	public int getPriority() {
		return this.priority;
	}

	@Override
	public void tick() {
		this.execute();
		 
		if(this.repeatsleft > 0) {		
			RepeatingEffect outer = this;
			this.handler.queueEffect(new RepeatingEffect(this.handler, priority, repeatsleft-1, repeatdelay) {
				@Override
				protected void execute() {
					outer.execute();
				}
			}, repeatdelay);
		}
	}

	@Override
	public void setTick(int tick) {
		this.tick = tick;
	}
	
	@Override
	public int getTick() {
		return tick;
	}
		
	protected abstract void execute();
}