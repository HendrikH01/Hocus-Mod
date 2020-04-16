package com.xX_deadbush_Xx.witchcraftmod.common.setup;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ModBlocks;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class SetupEvents {
	public static void clientSetup(final FMLClientSetupEvent event) {
    	RenderTypeLookup.setRenderLayer(ModBlocks.HELLSHROOM.get(), RenderType.getCutout());
	}

	public static void commonSetup(FMLCommonSetupEvent event) {
		
	}
}
