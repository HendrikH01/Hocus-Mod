package com.xX_deadbush_Xx.hocus.common.register;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.client.ModItemGroups;
import com.xX_deadbush_Xx.hocus.common.potion.ModPotions;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Hocus.MOD_ID, bus = Bus.MOD)
public class ModBlockItems {
	
	@SubscribeEvent
	public static void onRegistryEvent(final RegistryEvent.Register<Item> evt) {
		final List<RegistryObject<Block>> specialblocks = ImmutableList.of(ModBlocks.CHALK_BLOCK, ModBlocks.SHIMMER_BLOCK, ModBlocks.BELLADONNA, ModBlocks.ADONIS);
		
		final IForgeRegistry<Item> registry = evt.getRegistry();
		
		ModBlocks.BLOCKS.getEntries().stream().filter(block -> !specialblocks.contains(block)).map(RegistryObject::get).forEach((block) -> {
			final Item.Properties properties = new Item.Properties().group(ModItemGroups.BLOCKS);
			BlockItem itemblock = new BlockItem(block, properties);
			itemblock.setRegistryName(block.getRegistryName());
			registry.register(itemblock);
		});
		
		
		BlockItem belladonna = new BlockItem(ModBlocks.BELLADONNA.get(), new Item.Properties().food(
				new Food.Builder().effect(new EffectInstance(ModPotions.BELLADONNA_POISION),  200).hunger(2).saturation(2).setAlwaysEdible().build()).group(ModItemGroups.ITEMS));
		belladonna.setRegistryName("belladonna_berry");
		registry.register(belladonna);
		
		BlockItem adonis = new BlockItem(ModBlocks.ADONIS.get(), new Item.Properties().group(ModItemGroups.ITEMS));
		adonis.setRegistryName("adonis_seed_pod");
		registry.register(adonis);
	}
}
