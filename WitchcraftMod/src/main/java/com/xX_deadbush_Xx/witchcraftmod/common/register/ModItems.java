package com.xX_deadbush_Xx.witchcraftmod.common.register;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.client.WitchcraftItemGroup;
import com.xX_deadbush_Xx.witchcraftmod.common.items.MagicChalk;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, WitchcraftMod.MOD_ID);
	
	public static final RegistryObject<Item> VIBRANT_CRYSTAL = ITEMS.register("vibrant_crystal", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> VIBRANT_DUST = ITEMS.register("vibrant_dust", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> MAGIC_CHALK = ITEMS.register("magic_chalk", () -> new MagicChalk(new Item.Properties().group(WitchcraftItemGroup.instance).maxDamage(50)));	
	public static final RegistryObject<Item> WET_CHALK = ITEMS.register("wet_chalk", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	

}
