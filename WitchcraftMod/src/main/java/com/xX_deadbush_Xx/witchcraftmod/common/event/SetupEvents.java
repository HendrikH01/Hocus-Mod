package com.xX_deadbush_Xx.witchcraftmod.common.event;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.CraftingHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RenderHelper;
import com.xX_deadbush_Xx.witchcraftmod.client.ModColorHandler;
import com.xX_deadbush_Xx.witchcraftmod.client.models.model_loaders.GlowingModelLoader;
import com.xX_deadbush_Xx.witchcraftmod.client.renderers.tileEntities.DryingRackRenderer;
import com.xX_deadbush_Xx.witchcraftmod.client.renderers.tileEntities.MortarRenderer;
import com.xX_deadbush_Xx.witchcraftmod.client.renderers.tileEntities.RitualPedestalRenderer;
import com.xX_deadbush_Xx.witchcraftmod.client.renderers.tileEntities.RitualStoneRenderer;
import com.xX_deadbush_Xx.witchcraftmod.common.gui.ToolTableScreen;
import com.xX_deadbush_Xx.witchcraftmod.common.network.WitchcraftPacketHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModContainers;
import com.xX_deadbush_Xx.witchcraftmod.common.register.RitualRegistry;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class SetupEvents {
	public static void clientSetup(final FMLClientSetupEvent event) {
		ModelLoaderRegistry.registerLoader(new ResourceLocation(WitchcraftMod.MOD_ID, "glowing"), new GlowingModelLoader());
		
		ModColorHandler.registerBlocks();
		ModColorHandler.registerItems();

    	RenderTypeLookup.setRenderLayer(ModBlocks.HELLSHROOM.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.BELLADONNA.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.ADONIS.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.CHALK_BLOCK.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.DREADWOOD_SAPLING.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.RITUAL_STONE.get(), RenderHelper::isSolidOrTranslucent);
    	RenderTypeLookup.setRenderLayer(ModBlocks.RITUAL_PEDESTAL.get(), RenderHelper::isSolidOrTranslucent);

    	MortarRenderer.register();
    	DryingRackRenderer.register();
    	RitualStoneRenderer.register();
    	RitualPedestalRenderer.register();
    	
    	ScreenManager.registerFactory(ModContainers.TOOL_TABLE.get(), ToolTableScreen::new);
	}

	public static void commonSetup(FMLCommonSetupEvent event) {
		WitchcraftPacketHandler.registerPackets();
		RitualRegistry.INSTANCE.registerRituals();
	}
}
