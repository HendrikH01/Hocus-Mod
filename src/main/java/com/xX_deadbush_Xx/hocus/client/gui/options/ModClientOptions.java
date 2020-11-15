package com.xX_deadbush_Xx.hocus.client.gui.options;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModClientOptions {
	
	public static final ModClientOptions INSTANCE;
	public static final ForgeConfigSpec SPEC;
	
	public final ForgeConfigSpec.EnumValue<ManabarModeOption> manabar_mode;

	public ModClientOptions(ForgeConfigSpec.Builder builder) {
		this.manabar_mode = builder
				.comment("Set to 'shown' to show the manabar, 'hidden' to hide it, and 'no_number' to only show the bar but not the number")
				.defineEnum("manabar_mode", ManabarModeOption.SHOWN);
	}

	static {
		final Pair<ModClientOptions, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(ModClientOptions::new);
		SPEC = pair.getRight();
		INSTANCE = pair.getLeft();
	}
}
