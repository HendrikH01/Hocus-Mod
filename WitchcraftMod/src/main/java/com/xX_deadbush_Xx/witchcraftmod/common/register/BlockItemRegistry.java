package com.xX_deadbush_Xx.witchcraftmod.common.register;

import java.util.Arrays;
import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.client.WitchcraftItemGroup;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus = Bus.MOD)
public class BlockItemRegistry {
	@SubscribeEvent
	public static void onRegistryEvent(final RegistryEvent.Register<Item> evt) {
		final List<String> specialblocks = Arrays.asList("chalk_block");
		
		final IForgeRegistry<Item> registry = evt.getRegistry();
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(block -> !specialblocks.contains(block.getRegistryName().getNamespace())).forEach((block) -> {
			final Item.Properties properties = new Item.Properties().group(WitchcraftItemGroup.instance);
			BlockItem itemblock = new BlockItem(block, properties);
			itemblock.setRegistryName(block.getRegistryName());
			registry.register(itemblock);
			System.out.println(itemblock.toString());

		});
		
		//REGISTER SPECIAL ITEMS HERE
	}
}
