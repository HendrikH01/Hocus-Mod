package com.xX_deadbush_Xx.hocus.client.gui.options;

import com.xX_deadbush_Xx.hocus.Hocus;

import net.minecraft.client.gui.screen.VideoSettingsScreen;
import net.minecraft.client.settings.IteratableOption;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class OnGuiOpen {
	
	private static final IteratableOption MANABAR_MODE = new IteratableOption(Hocus.MOD_ID + ".options.manabar_mode", (gamesettings, i) -> {
		ManabarModeOption mode = ModClientOptions.INSTANCE.manabar_mode.get();
		switch (mode) {
		case HIDDEN:
			ModClientOptions.INSTANCE.manabar_mode.set(ManabarModeOption.NO_NUMBER);
			break;
		case NO_NUMBER:
			ModClientOptions.INSTANCE.manabar_mode.set(ManabarModeOption.SHOWN);
			break;
		case SHOWN:
			ModClientOptions.INSTANCE.manabar_mode.set(ManabarModeOption.HIDDEN);
			break;
		}
	}, (gamesettings, option) -> {
		ManabarModeOption mode = ModClientOptions.INSTANCE.manabar_mode.get();
		return option.getDisplayString() + mode.getTranslatedName();
	});
	
	@SubscribeEvent
	public static void onOpenGuiEvent(GuiScreenEvent.InitGuiEvent.Post event) {
		if(event.getGui() instanceof VideoSettingsScreen) {
			VideoSettingsScreen gui = (VideoSettingsScreen)event.getGui();
		      gui.optionsRowList.addOption(MANABAR_MODE);
		      
		}
	}
}
