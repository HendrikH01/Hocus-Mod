package com.xX_deadbush_Xx.witchcraftmod.common.register;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.client.WitchcraftItemGroup;
import com.xX_deadbush_Xx.witchcraftmod.common.items.MagicChalk;
import com.xX_deadbush_Xx.witchcraftmod.common.items.ParticleDebugger;
import com.xX_deadbush_Xx.witchcraftmod.common.items.SacrificeKnife;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, WitchcraftMod.MOD_ID);
	
	public static final RegistryObject<Item> VIBRANT_CRYSTAL = ITEMS.register("vibrant_crystal", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> VIBRANT_DUST = ITEMS.register("vibrant_dust", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> ADONIS_SEED_POD = ITEMS.register("adonis_seed_pod", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> BELLADONNA_BERRY = ITEMS.register("belladonna_berry", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> DREADWOOD_STICK = ITEMS.register("dreadwood_stick", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> WOOD_SAW = ITEMS.register("wood_saw", () -> new Item(new Item.Properties().maxDamage(200).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> SLEDGE_HAMMER = ITEMS.register("sledge_hammer", () -> new Item(new Item.Properties().maxDamage(200).group(WitchcraftItemGroup.instance)));

	public static final RegistryObject<Item> MAGIC_CHALK = ITEMS.register("magic_chalk", () -> new MagicChalk(new Item.Properties().group(WitchcraftItemGroup.instance).maxDamage(50)));	
	public static final RegistryObject<Item> WET_CHALK = ITEMS.register("wet_chalk", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> RITUAL_ACTIVATOR = ITEMS.register("ritual_activator", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> PARTICLE_DEBUGGER = ITEMS.register("particle_debugger", () -> new ParticleDebugger(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> SACRIFICE_KNIFE = ITEMS.register("sacrifice_knife", () -> new SacrificeKnife());	
	public static final RegistryObject<Item> PESTLE = ITEMS.register("pestle", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	

}
