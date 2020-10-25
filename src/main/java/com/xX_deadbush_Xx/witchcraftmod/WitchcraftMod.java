package com.xX_deadbush_Xx.witchcraftmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.ModParticles;
import com.xX_deadbush_Xx.witchcraftmod.common.event.OnClientSetup;
import com.xX_deadbush_Xx.witchcraftmod.common.event.OnCommonSetup;
import com.xX_deadbush_Xx.witchcraftmod.common.potion.ModPotions;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBiomes;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModContainers;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModItems;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("witchcraftmod")
@EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class WitchcraftMod {
	public static final String MOD_ID = "witchcraftmod";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID + "_logger");

	public WitchcraftMod() {
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	modEventBus.addListener(OnCommonSetup::commonSetup);
    	modEventBus.addListener(OnClientSetup::clientSetup);

        ModParticles.PARTICLES.register(modEventBus);
        ModContainers.CONTAINER_TYPES.register(modEventBus);
        ModPotions.POTIONS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModRecipeTypes.SERIALIZERS.register(modEventBus);
        ModTileEntities.TILE_ENTITIES.register(modEventBus);
        ModBiomes.BIOMES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
