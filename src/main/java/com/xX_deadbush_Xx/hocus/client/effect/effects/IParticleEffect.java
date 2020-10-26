package com.xX_deadbush_Xx.hocus.client.effect.effects;

public interface IParticleEffect {
	public int getDuration();
	
	int getTicksLeft();
	
	public void addParticles();
	
	public void tick();
}
