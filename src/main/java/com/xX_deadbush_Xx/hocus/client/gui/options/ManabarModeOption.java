package com.xX_deadbush_Xx.hocus.client.gui.options;

import com.xX_deadbush_Xx.hocus.Hocus;

import net.minecraft.client.resources.I18n;

public enum ManabarModeOption {
	HIDDEN("hidden"),
	NO_NUMBER("no_number"),
	SHOWN("shown");
	
	private final String name;

	ManabarModeOption(String name) {
		this.name = name;
	}

	String getTranslatedName() {
		return I18n.format(Hocus.MOD_ID + ".options." + name);
	}
}
