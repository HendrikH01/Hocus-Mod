package com.xX_deadbush_Xx.witchcraftmod.client.renderers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.data.EmptyModelData;

//credit for this class goes to Commoble 

public class GlowingBakedModel implements IBakedModel {
	public static final int FULLBRIGHT = getLightLevel(15);
	
	private static final LoadingCache<FaceKey, List<BakedQuad>> QUAD_CACHE = CacheBuilder.newBuilder().build(new CacheLoader<FaceKey, List<BakedQuad>>() {
		@Override
		public List<BakedQuad> load(FaceKey key) {
			return lightQuads(key.model.getQuads(key.state, key.side, key.rand, EmptyModelData.INSTANCE), key.glowingTextures);
		}
	});

	protected final IBakedModel base;
	private final Map<ResourceLocation, Integer> glowingTextures;

	public GlowingBakedModel(IBakedModel base, Map<ResourceLocation, Integer> glowingTextures) {
		this.base = base;
		this.glowingTextures = glowingTextures;
	}

	@Override
	@Deprecated
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		if (state == null) {
			return this.base.getQuads(state, side, rand);
		}
		return QUAD_CACHE.getUnchecked(new FaceKey(state, side, this.base, this.glowingTextures, rand));
	}

	private static List<BakedQuad> lightQuads(List<BakedQuad> oldQuads, Map<ResourceLocation, Integer> glowingTextures) {
		List<BakedQuad> newQuads = new ArrayList<>(oldQuads);

		int quadCount = newQuads.size();
		for (int i = 0; i < quadCount; i++) {
			BakedQuad quad = newQuads.get(i);

			Integer lightLevel = glowingTextures.get(quad.func_187508_a().getName());
			if(lightLevel != null) newQuads.set(i, lightQuad(quad, lightLevel));
		}
		return newQuads;
	}

	private static BakedQuad lightQuad(BakedQuad quad, int light) {
		int[] vertexData = quad.getVertexData().clone();
		int lightLevel = getLightLevel(light);
		
		vertexData[6] = Math.max(lightLevel, vertexData[6]);
		vertexData[6 + 8] = Math.max(lightLevel, vertexData[6 + 8]);
		vertexData[6 + 8 + 8] = Math.max(lightLevel, vertexData[6 + 8 + 8]);
		vertexData[6 + 8 + 8 + 8] = Math.max(lightLevel, vertexData[6 + 8 + 8 + 8]);
		return new BakedQuad(vertexData, quad.getTintIndex(), quad.getFace(), quad.func_187508_a(), quad.shouldApplyDiffuseLighting());
	}

	@Override
	public boolean isAmbientOcclusion() {
		return this.base.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return this.base.isGui3d();
	}

	@Override
	public boolean func_230044_c_() {
		return this.base.func_230044_c_();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return this.base.isBuiltInRenderer();
	}

	@Override
	@Deprecated
	public TextureAtlasSprite getParticleTexture() {
		return this.base.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return this.base.getOverrides();
	}
	
	public static int getLightLevel(int lightIn) {
		return (lightIn << 4) | ((lightIn << 4) << 16);
	}

	private static class FaceKey {
		private final BlockState state;
		private final Direction side;
		private final IBakedModel model;
		private final Map<ResourceLocation, Integer> glowingTextures;
		private final Random rand;

		public FaceKey(@Nonnull BlockState state, @Nullable Direction side, IBakedModel model, Map<ResourceLocation, Integer> textures, Random rand) {
			this.state = state;
			this.side = side;
			this.model = model;
			this.glowingTextures = textures;
			this.rand = rand;
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof FaceKey)) {
				return false;
			} else {
				FaceKey otherKey = (FaceKey) other;
				return this.side == otherKey.side && this.state.equals(otherKey.state);
			}
		}

		@Override
		public int hashCode() {
			return this.state.hashCode() + 113 * (this.side == null ? 0 : this.side.hashCode());
		}
	}
}
