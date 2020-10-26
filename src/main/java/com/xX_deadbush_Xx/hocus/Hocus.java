package com.xX_deadbush_Xx.hocus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xX_deadbush_Xx.hocus.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.hocus.client.effect.particles.ModParticles;
import com.xX_deadbush_Xx.hocus.common.event.OnClientSetup;
import com.xX_deadbush_Xx.hocus.common.event.OnCommonSetup;
import com.xX_deadbush_Xx.hocus.common.potion.ModPotions;
import com.xX_deadbush_Xx.hocus.common.register.ModBiomes;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModContainers;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;
import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("hocus")
@EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Hocus {
	public static final String MOD_ID = "hocus";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID + "_logger");

	public Hocus() {
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
