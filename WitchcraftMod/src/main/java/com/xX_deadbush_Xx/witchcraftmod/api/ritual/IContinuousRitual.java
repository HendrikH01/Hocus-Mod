package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

public interface IContinuousRitual {
	int manaPerSecond();
	
	/**
	 * Client and server side
	 */
	void effectTick();
}
