package com.xX_deadbush_Xx.witchcraftmod.common.event;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book.GuideBookContent;

import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class OnRecipeLoad {

	@SubscribeEvent
    public static void RecipesUpdatedEvent(RecipesUpdatedEvent event) {
		GuideBookContent.loadBookContent(event.getRecipeManager());
	}
}
