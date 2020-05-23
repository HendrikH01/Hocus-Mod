package com.xX_deadbush_Xx.witchcraftmod.client.models.model_loaders;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.client.model.IModelLoader;

public class GlowingModelLoader implements IModelLoader<GlowingModel> {

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {}

	@Override
	public GlowingModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
		ImmutableList.Builder<IUnbakedModel> builder = ImmutableList.builder();
        JsonObject glowingObj = JSONUtils.getJsonObject(modelContents, "render-glowing");
        int lightlevel = 15;
        if(glowingObj.has("brightness")) lightlevel =  JSONUtils.getInt(glowingObj, "brightness");
        System.out.println(lightlevel);
        builder.add((IUnbakedModel)deserializationContext.deserialize(glowingObj, BlockModel.class));
        return new GlowingModel(builder.build(), lightlevel);
	}
}
