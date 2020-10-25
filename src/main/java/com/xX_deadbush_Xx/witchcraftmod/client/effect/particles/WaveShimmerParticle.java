package com.xX_deadbush_Xx.witchcraftmod.client.effect.particles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.xX_deadbush_Xx.witchcraftmod.api.util.ModMathHelper;
import com.xX_deadbush_Xx.witchcraftmod.client.ModRenderTypes;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data.ScaledColoredParticleData;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WaveShimmerParticle extends SpriteTexturedParticle {
	
	private Vec3d[] points; //points used to generate bezier path 
	private int segments;
	private int bumpcount;
	private double distance;
	private Vec3d difference;
	private Vec3d startpos;

	public WaveShimmerParticle(World worldIn, double startX, double startY, double startZ, double endX, double endY, double endZ, int color) {
		super(worldIn, startX, startY, startZ, 0, 0, 0);
		this.maxAge = 100;
		this.particleScale = 0.2f;
		this.particleRed = (float) ((color & 0xFF0000) >> 16) / 255;
		this.particleGreen = (float) ((color & 0xFF00) >> 8) / 255;
		this.particleBlue = (float) (color & 0xFF) / 255;
		this.difference = new Vec3d(startX - endX, startY - endY, startZ - endZ);
		this.distance = this.difference.length();
		this.segments = (int) Math.sqrt(this.distance) * 15;
		this.bumpcount = (int) segments / 10 + 1;
		this.points = new Vec3d[bumpcount + 2];
		this.startpos = new Vec3d(startX, startY, startZ);
		
		//initialize points
	    points[this.bumpcount + 1] = new Vec3d(this.distance, 0, 0);
	    points[0] = new Vec3d(0, 0, 0);

	    double intensity = (bumpcount == 2) ? 0.2 : 0.6;
		for(int j = 0; j < this.bumpcount; j++) {
			Vec3d pos = this.startpos.add(this.difference.normalize().scale((double)(j+1)/(bumpcount+2)*distance));
			points[j + 1] = pos.add(0, Math.pow(-1, j)*Math.random()*intensity, 0);
		}
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
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.age++ >= this.maxAge) {
			this.setExpired();
		}
	}
   
	@Override
	public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
		System.out.println(this.posX + " " + this.posY + " " + this.posZ);
		Vec3d pos = ModMathHelper.bezierCurve((this.age + partialTicks) / 100, points);
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.posX = pos.x;
		this.posY = pos.y;
		this.posZ = pos.z;
		super.renderParticle(buffer, renderInfo, partialTicks);
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
         WaveShimmerParticle shimmer = new WaveShimmerParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, type.getColor());
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
			return ModParticles.WAVE_SHIMMER.get();
		}
	}
}