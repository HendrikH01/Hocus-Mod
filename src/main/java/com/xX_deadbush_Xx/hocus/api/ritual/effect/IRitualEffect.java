package com.xX_deadbush_Xx.hocus.api.ritual.effect;

public interface IRitualEffect {

	public int getTick();

	public int getPriority();

	public void tick();

	public void setTick(int ticks);
}
