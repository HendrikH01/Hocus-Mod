package com.xX_deadbush_Xx.hocus.common.event;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.client.gui.guide_book.GuideBookContent;

import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Hocus.MOD_ID, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class OnRecipeLoad {

	@SubscribeEvent
    public static void RecipesUpdatedEvent(RecipesUpdatedEvent event) {
		GuideBookContent.loadBookContent(event.getRecipeManager());
	}
}
