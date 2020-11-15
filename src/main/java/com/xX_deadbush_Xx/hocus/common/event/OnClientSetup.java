package com.xX_deadbush_Xx.hocus.common.event;

import java.util.function.Function;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.api.util.render.RenderHelper;
import com.xX_deadbush_Xx.hocus.client.ModColorHandler;
import com.xX_deadbush_Xx.hocus.client.gui.BottomLessBagScreen;
import com.xX_deadbush_Xx.hocus.client.gui.CrystalRechargerScreen;
import com.xX_deadbush_Xx.hocus.client.gui.ToolTableScreen;
import com.xX_deadbush_Xx.hocus.client.models.model_loaders.GlowingModelLoader;
import com.xX_deadbush_Xx.hocus.client.renderers.entities.LightningBallRenderer;
import com.xX_deadbush_Xx.hocus.client.renderers.entities.RavenRenderer;
import com.xX_deadbush_Xx.hocus.client.renderers.tileEntities.DryingRackRenderer;
import com.xX_deadbush_Xx.hocus.client.renderers.tileEntities.EnergyRelayRenderer;
import com.xX_deadbush_Xx.hocus.client.renderers.tileEntities.MortarRenderer;
import com.xX_deadbush_Xx.hocus.client.renderers.tileEntities.RitualPedestalRenderer;
import com.xX_deadbush_Xx.hocus.client.renderers.tileEntities.RitualStoneRenderer;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModContainers;
import com.xX_deadbush_Xx.hocus.common.register.ModEntities;
import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class OnClientSetup {
	
	public static void clientSetup(final FMLClientSetupEvent event) {
		ModelLoaderRegistry.registerLoader(new ResourceLocation(Hocus.MOD_ID, "glowing"), new GlowingModelLoader());
		
		ModColorHandler.registerBlocks();
		ModColorHandler.registerItems();

		registerTER(ModTileEntities.MORTAR_TILE.get(), MortarRenderer::new);
    	registerTER(ModTileEntities.DRYING_RACK.get(), DryingRackRenderer::new);
    	registerTER(ModTileEntities.RITUAL_PEDESTAL.get(), RitualPedestalRenderer::new);
    	registerTER(ModTileEntities.RITUAL_STONE.get(), RitualStoneRenderer::new);
    	registerTER(ModTileEntities.ENERGY_RELAY_TILE.get(), EnergyRelayRenderer::new);
    	
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.LIGHTING_BALL.get(), LightningBallRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.RAVEN.get(), RavenRenderer::new);

    	ScreenManager.registerFactory(ModContainers.TOOL_TABLE.get(), ToolTableScreen::new);
    	ScreenManager.registerFactory(ModContainers.BOTTOM_LESS_BAG.get(), BottomLessBagScreen::new);
    	ScreenManager.registerFactory(ModContainers.CRYSTAL_RECHARGER.get(), CrystalRechargerScreen::new);
	
    	registerRenderLayers();
	}
	
	private static void registerRenderLayers() {
		RenderTypeLookup.setRenderLayer(ModBlocks.FUNKY_MUSHROOM.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.BELLADONNA.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.ADONIS.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.SWIRLY_PLANT.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.CAVE_FLOWER.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.POISON_IVY.get(), RenderType.getCutout());

    	RenderTypeLookup.setRenderLayer(ModBlocks.CHALK_BLOCK.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.FUNKY_MUSHROOM_BLOCK.get(), RenderType.getTranslucent());

    	RenderTypeLookup.setRenderLayer(ModBlocks.DREADWOOD_SAPLING.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.RITUAL_STONE.get(), RenderHelper::isSolidOrTranslucent);
    	RenderTypeLookup.setRenderLayer(ModBlocks.RITUAL_PEDESTAL.get(), RenderHelper::isSolidOrTranslucent);
    	RenderTypeLookup.setRenderLayer(ModBlocks.FIRE_BOWL.get(), RenderHelper::isSolidOrCutout);
	}
	
	private static <T extends TileEntity> void registerTER(TileEntityType<T> type, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> factory) {
		ClientRegistry.bindTileEntityRenderer(type, factory);
	}
}
