package com.xX_deadbush_Xx.witchcraftmod.common.event;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RenderHelper;
import com.xX_deadbush_Xx.witchcraftmod.client.ModColorHandler;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.BottomLessBagScreen;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.CrystalRechargerScreen;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.ToolTableScreen;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book.GuideBookContent;
import com.xX_deadbush_Xx.witchcraftmod.client.models.model_loaders.GlowingModelLoader;
import com.xX_deadbush_Xx.witchcraftmod.client.renderers.tileEntities.DryingRackRenderer;
import com.xX_deadbush_Xx.witchcraftmod.client.renderers.tileEntities.MortarRenderer;
import com.xX_deadbush_Xx.witchcraftmod.client.renderers.tileEntities.RitualPedestalRenderer;
import com.xX_deadbush_Xx.witchcraftmod.client.renderers.tileEntities.RitualStoneRenderer;
import com.xX_deadbush_Xx.witchcraftmod.common.network.WitchcraftPacketHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModContainers;
import com.xX_deadbush_Xx.witchcraftmod.common.register.RitualRegistry;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.CrystalEnergyStorage;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
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
    	
//    	GuideBookContent.loadBookContent();
    	ScreenManager.registerFactory(ModContainers.TOOL_TABLE.get(), ToolTableScreen::new);
    	ScreenManager.registerFactory(ModContainers.BOTTOM_LESS_BAG.get(), BottomLessBagScreen::new);
    	ScreenManager.registerFactory(ModContainers.CRYSTAL_RECHARGER.get(), CrystalRechargerScreen::new);
	}

	public static void commonSetup(FMLCommonSetupEvent event) {
		WitchcraftPacketHandler.registerPackets();
		RitualRegistry.INSTANCE.registerRituals();
		
		CapabilityManager.INSTANCE.register(CrystalEnergyStorage.class, new Capability.IStorage<CrystalEnergyStorage>( ) {
			@Override
			public INBT writeNBT(Capability<CrystalEnergyStorage> capability, CrystalEnergyStorage instance, Direction side) {return null;}
			@Override
			public void readNBT(Capability<CrystalEnergyStorage> capability, CrystalEnergyStorage instance, Direction side, INBT nbt) {}
			
		}, CrystalEnergyStorage::new);
	}
}
