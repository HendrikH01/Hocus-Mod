package com.xX_deadbush_Xx.witchcraftmod.common.register;

import java.util.Arrays;
import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.client.ModItemGroups;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus = Bus.MOD)
public class ModBlockItems {
	@SubscribeEvent
	public static void onRegistryEvent(final RegistryEvent.Register<Item> evt) {
		final List<RegistryObject<Block>> specialblocks = Arrays.asList(ModBlocks.CHALK_BLOCK);
		
		final IForgeRegistry<Item> registry = evt.getRegistry();
		
		ModBlocks.BLOCKS.getEntries().stream().filter(block -> !specialblocks.contains(block)).map(RegistryObject::get).forEach((block) -> {
			final Item.Properties properties = new Item.Properties().group(ModItemGroups.BLOCKS);
			BlockItem itemblock = new BlockItem(block, properties);
			itemblock.setRegistryName(block.getRegistryName());
			registry.register(itemblock);
		});
	}
}
