package com.xX_deadbush_Xx.hocus.api.ritual;

public interface IContinuousRitual {
	double manaPerSecond();
	
	/**
	 * Client and server side
	 */
	void effectTick();
}
