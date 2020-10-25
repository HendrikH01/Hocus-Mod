package com.xX_deadbush_Xx.witchcraftmod.client;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.xX_deadbush_Xx.witchcraftmod.client.renderers.ManawaveRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModRenderTypes extends RenderType {
	private ModRenderTypes(String nameIn, VertexFormat formatIn, int drawModeIn, int bufferSizeIn,
			boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
		super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
	}
	
	public static final RenderType MANAWAVE = makeType("manawave", DefaultVertexFormats.POSITION_COLOR_TEX, 7, 262144, true, true, RenderType.State.getBuilder()
			.shadeModel(SHADE_DISABLED)
			.texture(new TextureState(ManawaveRenderer.TEXTURE_RL, false, true))
			.transparency(new TransparencyState("manawave_transparency", () -> {
				//pre render
				RenderSystem.enableBlend();
				RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			}, () -> {
				//post render
				RenderSystem.disableBlend();
			})).build(false));

	public static final IParticleRenderType SHIMMER_PARTICLE_TYPE = new IParticleRenderType() {
		@SuppressWarnings("deprecation")
		public void beginRender(BufferBuilder buf, TextureManager texmanager) {
			RenderSystem.enableBlend();
			RenderSystem.disableCull();
			RenderSystem.depthFunc(GL11.GL_LEQUAL);
			RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			RenderSystem.alphaFunc(GL11.GL_GEQUAL, 0.003921569F);
			RenderSystem.disableLighting();

			texmanager.bindTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE);
			texmanager.getTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE).setBlurMipmap(true, false);
			buf.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		}

		@SuppressWarnings({ "deprecation", "resource" })
		public void finishRender(Tessellator tessellator) {
			tessellator.draw();
			RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
			RenderSystem.disableBlend();
			RenderSystem.enableCull();
			Minecraft.getInstance().textureManager.getTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE)
					.restoreLastBlurMipmap();
		}

		public String toString() {
			return "SHIMMER_PARTICLE_TYPE";
		}
	};

	public static final IParticleRenderType GLOWING_TRANSLUCENT_PARTICLE_TYPE = new IParticleRenderType() {
		@SuppressWarnings("deprecation")
		public void beginRender(BufferBuilder buf, TextureManager texmanager) {
			RenderSystem.enableBlend();
			RenderSystem.depthFunc(GL11.GL_LEQUAL);
			RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			RenderSystem.alphaFunc(GL11.GL_GEQUAL, 0.003921569F);
			RenderSystem.disableLighting();

			texmanager.bindTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE);
			buf.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		}

		public void finishRender(Tessellator tessellator) {
			tessellator.draw();
			RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
			RenderSystem.disableBlend();
		}

		public String toString() {
			return "GLOWING_TRANSLUCENT_PARTICLE_TYPE";
		}
	};
}
