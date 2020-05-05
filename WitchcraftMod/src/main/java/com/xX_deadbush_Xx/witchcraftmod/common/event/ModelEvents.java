package com.xX_deadbush_Xx.witchcraftmod.common.event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.xX_deadbush_Xx.witchcraftmod.client.renderers.GlowingBakedModel;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModelEvents {
	
	@SubscribeEvent
	public static void onBakeModels(ModelBakeEvent event) {
		System.out.println("event");
		Map<ResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();
		
		Map<ResourceLocation, Integer> chalkGlowingTextures = new HashMap<>();
		chalkGlowingTextures.put(new ResourceLocation("witchcraftmod:blocks/modelled/chalk/dot"), 5);
		chalkGlowingTextures.put(new ResourceLocation("witchcraftmod:blocks/modelled/chalk/line"), 5);

		ModBlocks.CHALK_BLOCK.get().getStateContainer().getValidStates().stream().map(BlockModelShapes::getModelLocation)
		.forEach(modelResourceLocation -> overrideGlowing(modelResourceLocation, modelRegistry, chalkGlowingTextures));
	}
	
	public static void overrideGlowing(ModelResourceLocation modelResource, Map<ResourceLocation, IBakedModel> modelRegistry, Map<ResourceLocation, Integer> glowingTextures) {
		Function<IBakedModel, IBakedModel> glowingModel = (modelIn) -> {return new GlowingBakedModel(modelIn, glowingTextures);};
		System.out.println(glowingModel);

		getModelOverrider(glowingModel).accept(modelResource, modelRegistry);
	}
	
	public static BiConsumer<ModelResourceLocation, Map<ResourceLocation, IBakedModel>> getModelOverrider(Function<IBakedModel, IBakedModel> modelFunction) {
		return (modelResource, registry) -> {
			if (registry.containsKey(modelResource)) {
 				registry.put(modelResource, modelFunction.apply(registry.get(modelResource)));
			}
		};
	}
}
