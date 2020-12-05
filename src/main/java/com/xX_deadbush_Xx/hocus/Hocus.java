package com.xX_deadbush_Xx.hocus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xX_deadbush_Xx.hocus.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.hocus.client.effect.particles.ModParticles;
import com.xX_deadbush_Xx.hocus.client.gui.guide_book.GuideBookContent;
import com.xX_deadbush_Xx.hocus.client.gui.options.ModClientOptions;
import com.xX_deadbush_Xx.hocus.common.event.OnClientSetup;
import com.xX_deadbush_Xx.hocus.common.event.OnCommonSetup;
import com.xX_deadbush_Xx.hocus.common.potion.ModPotions;
import com.xX_deadbush_Xx.hocus.common.register.ModBiomes;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModContainers;
import com.xX_deadbush_Xx.hocus.common.register.ModEntities;
import com.xX_deadbush_Xx.hocus.common.register.ModFeatures;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;
import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
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

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBiomes.BIOMES.register(modEventBus);
        ModContainers.CONTAINER_TYPES.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModTileEntities.TILE_ENTITIES.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
        ModPotions.POTIONS.register(modEventBus);
        ModRecipeTypes.SERIALIZERS.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModClientOptions.SPEC);
    }
	
	public static void log(Object... args) {
		String out = "";
		for(Object o : args) {
			out += o.toString() + " ";
		}
		
		System.out.println(out);
	}
	
    @SubscribeEvent
    public static void onRegisterBiomes(final RegistryEvent.Register<Biome> event) {
    	ModBiomes.register();
    }
    
	@SubscribeEvent
    public static void recipesUpdatedEvent(RecipesUpdatedEvent event) {
		GuideBookContent.loadBookContent(event.getRecipeManager());
	}

	@SubscribeEvent
	public static void registerParticleFactories(ParticleFactoryRegisterEvent evt) {
		ModParticles.registerFactories();
	}
}
