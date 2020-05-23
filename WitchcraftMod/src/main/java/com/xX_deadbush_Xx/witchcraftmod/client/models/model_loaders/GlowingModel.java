package com.xX_deadbush_Xx.witchcraftmod.client.models.model_loaders;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelTransformComposition;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.geometry.IModelGeometry;

public class GlowingModel implements IModelGeometry<GlowingModel> {

	private ImmutableList<IUnbakedModel> models;
	private int lightlevel;

	public GlowingModel(ImmutableList<IUnbakedModel> models, int lightlevel) {
		this.models = models;
		this.lightlevel = lightlevel;
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		return new GlowingBakedModel(
                owner.useSmoothLighting(), 
                owner.isShadedInGui(),
                owner.isSideLit(), 
                spriteGetter.apply(owner.resolveTexture("particle")), 
                overrides,
                this.models.stream().map(model -> model.bakeModel(bakery, spriteGetter, modelTransform, modelLocation)).collect(ImmutableList.toImmutableList()),
                PerspectiveMapWrapper.getTransforms(new ModelTransformComposition(owner.getCombinedTransform(), modelTransform)),
                this.lightlevel);
	}

    @Override
    public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        Set<Material> materials = Sets.newHashSet();
        materials.add(owner.resolveTexture("particle"));
        for (IUnbakedModel model : this.models)
            materials.addAll(model.getTextures(modelGetter, missingTextureErrors));
        
        return materials;
    }
}
