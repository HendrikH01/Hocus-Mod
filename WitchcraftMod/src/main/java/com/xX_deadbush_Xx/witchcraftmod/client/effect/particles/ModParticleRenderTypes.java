package com.xX_deadbush_Xx.witchcraftmod.client.effect.particles;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModParticleRenderTypes {
	public static IParticleRenderType SHIMMER_PARTICLE_TYPE = new IParticleRenderType() {
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
	         RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F); //reset alpha
	         RenderSystem.disableBlend();
	      }

	      public String toString() {
	         return "SHIMMER_PARTICLE_TYPE";
	      }
	};
}
