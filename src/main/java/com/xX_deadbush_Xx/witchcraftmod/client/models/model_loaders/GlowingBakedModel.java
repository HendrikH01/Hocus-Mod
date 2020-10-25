package com.xX_deadbush_Xx.witchcraftmod.client.models.model_loaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.PerspectiveMapWrapper;

@OnlyIn(Dist.CLIENT)
public class GlowingBakedModel implements IBakedModel {	
    private final ImmutableList<IBakedModel> models;
    private final ImmutableMap<TransformType, TransformationMatrix> cameraTransforms;
    protected final boolean ambientOcclusion;
    protected final boolean gui3d;
    protected final boolean isSideLit;
    protected final TextureAtlasSprite particle;
    protected final ItemOverrideList overrides;
    private final int brightness;
    
	public GlowingBakedModel(boolean useSmoothLighting, boolean shadedInGui, boolean sideLit, TextureAtlasSprite particle, ItemOverrideList overrides, ImmutableList<IBakedModel> models, ImmutableMap<TransformType, TransformationMatrix> transforms, int lightlevel) {
		this.isSideLit = sideLit;
        this.models = models;
        this.cameraTransforms = transforms;
        this.ambientOcclusion = useSmoothLighting;
        this.gui3d = shadedInGui;
        this.particle = particle;
        this.overrides = overrides;
        this.brightness = getBrightness(lightlevel);
   	}

	@Override
	@Deprecated
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		List<BakedQuad> quads = new ArrayList<>();
		for(IBakedModel model : this.models) {
			quads.addAll(lightQuads(model.getQuads(state, side, rand)));
		}
		return quads;
	}

	private List<BakedQuad> lightQuads(List<BakedQuad> oldQuads) {
		return oldQuads.stream().map(quad -> lightQuad(quad, this.brightness)).collect(Collectors.toList());
	}

	private static BakedQuad lightQuad(BakedQuad quad, int brightness) {
		int[] vertexData = quad.getVertexData().clone();
		
		vertexData[6] = Math.max(brightness, vertexData[6]);
		vertexData[6 + 8] = Math.max(brightness, vertexData[6 + 8]);
		vertexData[6 + 8 + 8] = Math.max(brightness, vertexData[6 + 8 + 8]);
		vertexData[6 + 8 + 8 + 8] = Math.max(brightness, vertexData[6 + 8 + 8 + 8]);
		return new BakedQuad(vertexData, quad.getTintIndex(), quad.getFace(), quad.func_187508_a(), quad.shouldApplyDiffuseLighting());
	}

	@Override
	public boolean isAmbientOcclusion() {
		return this.ambientOcclusion;
	}
	
    @Override
    public boolean isAmbientOcclusion(BlockState state){
        return isAmbientOcclusion();
    }
    
	@Override
	public boolean isGui3d() {
		return this.gui3d;
	}

	@Override
	public boolean func_230044_c_() {
		return this.isSideLit;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}
	
	@Override
	@Deprecated
	public TextureAtlasSprite getParticleTexture() {
		return this.particle;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.EMPTY;
	}
	
    @Override
    public boolean doesHandlePerspectives() {
        return true;
    }

    @Override
    public IBakedModel handlePerspective(TransformType cameraTransformType, MatrixStack mat){
        return PerspectiveMapWrapper.handlePerspective(this, cameraTransforms, cameraTransformType, mat);
    }
    
	public static int getBrightness(int lightIn) {
		return (lightIn << 4) | ((lightIn << 4) << 16);
	}
}
