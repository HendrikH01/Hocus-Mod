package com.xX_deadbush_Xx.witchcraftmod.common.event;

import com.xX_deadbush_Xx.witchcraftmod.common.world.gen.OreGeneration;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class LoadComplete {
    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
    	OreGeneration.generateOre();
    }
}
