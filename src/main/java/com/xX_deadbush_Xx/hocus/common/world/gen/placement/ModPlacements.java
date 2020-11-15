package com.xX_deadbush_Xx.hocus.common.world.gen.placement;

import com.xX_deadbush_Xx.hocus.Hocus;

import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Hocus.MOD_ID, bus = Bus.FORGE)
public class ModPlacements {
	
	public static final Placement<AtSurfaceWithExtraConfig> DREADWOOD_TREE = new DreadwoodTreePlacement(AtSurfaceWithExtraConfig::deserialize);
	
	@SubscribeEvent
	public static void registerPlacements(RegistryEvent.Register<Placement<?>> event) {
		event.getRegistry().register(DREADWOOD_TREE);
	}
}
