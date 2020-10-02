package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

public interface IContinuousRitual {
	double manaPerSecond();
	
	/**
	 * Client and server side
	 */
	void effectTick();
}
