package com.xX_deadbush_Xx.hocus.api.ritual.activation;

import net.minecraftforge.eventbus.api.Event;

public abstract class RitualTask<T extends Event> {
	protected final int ticks;
	protected boolean completed = false;
	public RitualActivationTaskHandler handler;
	protected boolean isNecessary;
	
	public RitualTask(int ticksuntilexpire, boolean necessary) {
		this.ticks = ticksuntilexpire;
		this.isNecessary = necessary;
	}
	
	public int getTick() {
		return this.ticks;
	}
	
	public abstract void expire();

	protected abstract boolean tryComplete(T event);
	
	@SuppressWarnings("unchecked")
	public final <E extends Event> void checkEvent(E event, Class<? extends RitualTask<E>> clazz) {
		if(clazz.isInstance(this)) {
			if(tryComplete((T)event))
				this.completed = true;
		}
	}
	
	public void onComplete() {}
	
	public final boolean isCompleted() {
		return this.completed;
	}
	
	public final boolean isNecessary() {
		return this.isNecessary;
	}
}
