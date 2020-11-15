package com.xX_deadbush_Xx.hocus.client.effect.particles;

import com.xX_deadbush_Xx.hocus.client.ModRenderTypes;
import com.xX_deadbush_Xx.hocus.client.effect.particles.data.ScaledColoredParticleData;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.particles.ParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FadingShimmerParticle extends SpriteTexturedParticle {
	
	public FadingShimmerParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, float scale, int color) {
	      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
	      this.maxAge = (int)(Math.random()*80) + 40;
	      this.motionX = xSpeedIn + (Math.random() - 0.5)/1000;
	      this.motionY = ySpeedIn + (Math.random() - 0.5)/1000;
	      this.motionZ = zSpeedIn + (Math.random() - 0.5)/1000;
	      this.particleScale = scale/10;
	      this.particleRed = (float)((color & 0xFF0000) >> 16 ) / 255;
	      this.particleGreen = (float)((color & 0xFF00) >> 8 ) / 255;
	      this.particleBlue = (float)(color & 0xFF) / 255;
	      this.particleAlpha = 1.0f;
	   }
	   
	   @Override
	   public int getBrightnessForRender(float partialTick) {
		   return LightTexture.packLight(15, 15);
	   }
	   
	   @Override
	   public IParticleRenderType getRenderType() {
	      return ModRenderTypes.SHIMMER_PARTICLE_TYPE;
	   }
	   
	   @Override
	   public void tick() {
	      if(this.particleAlpha <= 0.0) {
	         this.setExpired();
	      } else {
	    	  super.tick();
	    	  this.particleAlpha *= 0.97;
	    	  this.particleScale *= 0.97;
	      }
	   }
	   
	   public static ScaledColoredParticleData getData(boolean show, int color, float scale) {
		   return new Data(show, color, scale);
	   }

	   @OnlyIn(Dist.CLIENT)
	   public static class Factory implements IParticleFactory<ScaledColoredParticleData> {
	      private final IAnimatedSprite spriteSet;

	      public Factory(IAnimatedSprite p_i50823_1_) {
	         this.spriteSet = p_i50823_1_;
	      }

	      public Particle makeParticle(ScaledColoredParticleData type, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
	    	 FadingShimmerParticle shimmer = new FadingShimmerParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, type.getScale(), type.getColor());
	         shimmer.selectSpriteRandomly(this.spriteSet);
	         return shimmer;
	      }
	   }

		private static class Data extends ScaledColoredParticleData {
			public Data(boolean alwaysShow, int color, float scale) {
				super(Data::new, alwaysShow, color, scale);
			}

			@Override
			public ParticleType<?> getType() {
				return ModParticles.FADING_SHIMMER.get();
			}
		}
}
