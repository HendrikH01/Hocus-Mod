package com.xX_deadbush_Xx.witchcraftmod.common.event;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class LoadComplete {

	@SubscribeEvent
    public static void onLoadComplete(WorldEvent.Load event) {
		
	}
}
