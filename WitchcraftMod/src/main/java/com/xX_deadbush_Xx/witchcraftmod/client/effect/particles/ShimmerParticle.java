package com.xX_deadbush_Xx.witchcraftmod.client.effect.particles;

import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data.ScaledColoredParticleData;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShimmerParticle extends SpriteTexturedParticle {

public ShimmerParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, float scale, int color) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 1000;
      this.particleScale = scale/10;
      this.particleRed = (float)((color & 0xFF0000) >> 16 ) / 255;
      this.particleGreen = (float)((color & 0xFF00) >> 8 ) / 255;
      this.particleBlue = (float)(color & 0xFF) / 255;
      System.out.println(((color & 0xFF0000) >> 16) + " " + ((color & 0xFF00) >> 8) + " " + (color & 0xFF)/255);
      System.out.println(color + " " + particleRed + " " +particleGreen + " " + particleBlue);
   }
   
   @Override
   public int getBrightnessForRender(float partialTick) {
	   return LightTexture.packLight(15, 15);
   }
   
   @Override
   public IParticleRenderType getRenderType() {
      return ModParticleRenderTypes.SHIMMER_PARTICLE_TYPE;
   }
   
   @Override
   public void tick() {
      if (this.age++ >= this.maxAge) {
         this.setExpired();
      }
   }
   
   public static IParticleData getData(boolean show, int color, float scale) {
	   return new ScaledColoredParticleData(ModParticles.SHIMMER, show, color, scale);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<ScaledColoredParticleData> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i50823_1_) {
         this.spriteSet = p_i50823_1_;
      }

      public Particle makeParticle(ScaledColoredParticleData type, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         ShimmerParticle shimmer = new ShimmerParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, type.getScale(), type.getColor());
         shimmer.selectSpriteRandomly(this.spriteSet);
         return shimmer;
      }
   }
}