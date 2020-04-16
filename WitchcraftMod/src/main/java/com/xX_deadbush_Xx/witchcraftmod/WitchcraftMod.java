package com.xX_deadbush_Xx.witchcraftmod;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.items.ModItems;
import com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features.Features;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("witchcraftmod")
@EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class WitchcraftMod {
	public static final String MOD_ID = "witchcraftmod";
	
    public WitchcraftMod() {
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	modEventBus.addListener(this::setup);
    	modEventBus.addListener(this::doClientStuff);
        
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        Features.FEATURES.register(modEventBus);
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    	RenderTypeLookup.setRenderLayer(ModBlocks.HELLSHROOM.get(), RenderType.getCutout());
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }
    /*
	@SubscribeEvent
	public static void onRegistryEvent(final RegistryEvent.Register<Item> evt) {
		System.out.println(" abqc \\u001B[32m REGISTER ITEMS");
		System.exit(0);
		final List<String> specialblocks = Arrays.asList("hellshroom");
		
		final IForgeRegistry<Item> registry = evt.getRegistry();
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(block -> !specialblocks.contains(block.getRegistryName().getNamespace())).forEach((block) -> {
			final Item.Properties properties = new Item.Properties().group(WitchcraftItemGroup.instance);
			BlockItem itemblock = new BlockItem(block, properties);
			itemblock.setRegistryName(block.getRegistryName());
			registry.register(itemblock);
			System.out.println(itemblock.toString());

		});
		
		if(!specialblocks.isEmpty()) {
			ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(block -> specialblocks.contains(block.getRegistryName().getNamespace())).forEach((block) -> {
				final Item.Properties properties = new Item.Properties().group(WitchcraftItemGroup.instance);
				BlockItem itemblock = new BlockItem(block, properties);
				itemblock.setRegistryName(block.getRegistryName());
				registry.register(itemblock);
				System.out.println("registered special block! :o");
			});
		}
	}*/
}
