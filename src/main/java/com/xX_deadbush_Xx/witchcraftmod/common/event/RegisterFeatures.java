package com.xX_deadbush_Xx.witchcraftmod.common.event;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModFeatures;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegisterFeatures {
    @SubscribeEvent
    public static void onRegisterBiomes(final RegistryEvent.Register<Feature<?>> event) {
    	registerFeature(ModFeatures.HUGE_HELLSHROOM, "huge_hellshroom");
    	registerFeature(ModFeatures.FLOWER_FEATURE, "flower_feature");
    }
    
    public static void registerFeature(Feature<?> entry, String name) {
        entry.setRegistryName(new ResourceLocation(WitchcraftMod.MOD_ID, name));
        ForgeRegistries.FEATURES.register(entry);
    }
}