package com.xX_deadbush_Xx.witchcraftmod.common.items.wands;

public class StaffOfDestruction extends WandItem {
	public StaffOfDestruction(Properties properties) {
		super(properties);
	}
	
	@Override
	public int getEnergyPerUse() {
		return 150;
	}


	@Override
	public int getCooldown() {
		return 10;
	}
 }