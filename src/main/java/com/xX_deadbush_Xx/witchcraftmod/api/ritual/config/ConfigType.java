package com.xX_deadbush_Xx.witchcraftmod.api.ritual.config;

public enum ConfigType {
	SMALL(4, 0),
	MEDIUM(8, 4),
	LARGE(8, 12);

	private int anchorblocks;
	private int totems;

	ConfigType(int anchorblocks, int totems) {
		this.anchorblocks = anchorblocks;
		this.totems = totems;
	}

	public int getAnchorblockCount() {
		return anchorblocks;
	}

	public int getTotemCount() {
		return totems;
	}
}
