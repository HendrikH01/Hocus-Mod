package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

public enum RitualTier {
	SMALL(1),
	MEDIUM(2),
	LARGE(3);

	private int size;

	RitualTier(int i) {
		this.size = i;
	}
	
	public static RitualTier getTier(int i) throws IllegalArgumentException {
		for(RitualTier tier : RitualTier.values()) {
			if(tier.size == i) return tier;
		}
		
		throw new IllegalArgumentException("Not a valid ritual tier: " + i);
	}
}
