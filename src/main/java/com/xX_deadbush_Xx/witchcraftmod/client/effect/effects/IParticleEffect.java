package com.xX_deadbush_Xx.witchcraftmod.client.effect.effects;

import net.minecraft.world.World;

public interface IParticleEffect {
	public int getDuration();
	
	int getTicksLeft();
	
	public void addParticles();
	
	public void tick();
}
