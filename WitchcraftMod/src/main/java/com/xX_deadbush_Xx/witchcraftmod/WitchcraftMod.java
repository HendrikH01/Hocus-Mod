package com.xX_deadbush_Xx.witchcraftmod;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.items.ModItems;
import com.xX_deadbush_Xx.witchcraftmod.common.setup.SetupEvents;
import com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features.Features;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("witchcraftmod")
@EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class WitchcraftMod {
	public static final String MOD_ID = "witchcraftmod";
	
    public WitchcraftMod() {
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	modEventBus.addListener(this::setup);
    	modEventBus.addListener(this::doClientStuff);
    	
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        Features.FEATURES.register(modEventBus);
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    	SetupEvents.commonSetup(event);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    	SetupEvents.clientSetup(event);
    }
 
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    	
    }
}
