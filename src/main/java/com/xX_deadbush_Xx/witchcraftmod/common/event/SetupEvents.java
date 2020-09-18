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
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaStorage;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.TileEntityManaStorage;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
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
    	RenderTypeLookup.setRenderLayer(ModBlocks.SWIRLY_PLANT.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.CAVE_FLOWER.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.POISON_IVY.get(), RenderType.getCutout());

    	RenderTypeLookup.setRenderLayer(ModBlocks.CHALK_BLOCK.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.DREADWOOD_SAPLING.get(), RenderType.getCutout());
    	RenderTypeLookup.setRenderLayer(ModBlocks.RITUAL_STONE.get(), RenderHelper::isSolidOrTranslucent);
    	RenderTypeLookup.setRenderLayer(ModBlocks.FIRE_BOWL.get(), RenderHelper::isSolidOrCutout);

    	MortarRenderer.register();
    	DryingRackRenderer.register();
    	RitualStoneRenderer.register();
    	RitualPedestalRenderer.register();
    	
    	ScreenManager.registerFactory(ModContainers.TOOL_TABLE.get(), ToolTableScreen::new);
    	ScreenManager.registerFactory(ModContainers.BOTTOM_LESS_BAG.get(), BottomLessBagScreen::new);
    	ScreenManager.registerFactory(ModContainers.CRYSTAL_RECHARGER.get(), CrystalRechargerScreen::new);
	}

	public static void commonSetup(FMLCommonSetupEvent event) {
		WitchcraftPacketHandler.registerPackets();
		RitualRegistry.INSTANCE.registerRituals();
		CapabilityManager.INSTANCE.register(PlayerManaStorage.class, new Capability.IStorage<PlayerManaStorage>( ) {
			
			@Override
			public INBT writeNBT(Capability<PlayerManaStorage> capability, PlayerManaStorage instance, Direction side) {
				return new IntArrayNBT(new int[] {instance.getEnergy(), instance.getMaxEnergy()});
			}
			
			@Override
			public void readNBT(Capability<PlayerManaStorage> capability, PlayerManaStorage instance, Direction side, INBT nbt) {
				instance.setEnergy(((IntArrayNBT)nbt).get(0).getInt());
				instance.setMaxEnergy(((IntArrayNBT)nbt).get(1).getInt());
			}
			
		}, PlayerManaStorage::new);
		
		CapabilityManager.INSTANCE.register(TileEntityManaStorage.class, new Capability.IStorage<TileEntityManaStorage>( ) {
			
			@Override
			public INBT writeNBT(Capability<TileEntityManaStorage> capability, TileEntityManaStorage instance, Direction side) {
				return IntNBT.valueOf(instance.getEnergy());
			}
			
			@Override
			public void readNBT(Capability<TileEntityManaStorage> capability, TileEntityManaStorage instance, Direction side, INBT nbt) {
				instance.setEnergy(((IntNBT)nbt).getInt());
			}
			
		}, () -> new TileEntityManaStorage(1000, 20 , 20));
	}
}
