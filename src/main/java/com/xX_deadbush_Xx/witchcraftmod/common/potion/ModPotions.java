package com.xX_deadbush_Xx.witchcraftmod.common.potion;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModPotions {
	public static final DeferredRegister<Potion> POTIONS = new DeferredRegister(ForgeRegistries.POTION_TYPES, WitchcraftMod.MOD_ID);

	public static final Effect BELLADONNA_POISION = new BelladonnaPoisionEffect().setRegistryName(WitchcraftMod.MOD_ID, "belladonna_poison");
		
	public static final RegistryObject<Potion> BELLADONNA_POISION_POTION = POTIONS.register("belladonna_poison_potion",
			() -> new Potion("belladonna_poison_potion", new EffectInstance(BELLADONNA_POISION, 800)));	
	
	@SubscribeEvent
	public static void registerEffects(RegistryEvent.Register<Effect> evt) {
		evt.getRegistry().register(BELLADONNA_POISION);
	}
}
