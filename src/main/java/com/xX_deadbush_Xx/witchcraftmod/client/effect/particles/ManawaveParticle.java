package com.xX_deadbush_Xx.witchcraftmod.client.effect.particles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ModMathHelper;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data.ManawaveParticleData;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ManawaveParticle extends SpriteTexturedParticle {
	
private double distance;
private Vec3d difference;
private Vec3d endpos;
private int segments;
private int bumpcount;
private int tick;
private double thickness;

public ManawaveParticle(World worldIn, double startX, double startY, double startZ, double endX, double endY, double endZ, int color, double thickness, int tick) {
	super(worldIn, startX, startY, startZ, 0, 0, 0);
	this.particleRed = (float) ((color & 0xFF0000) >> 16) / 255;
	this.particleGreen = (float) ((color & 0xFF00) >> 8) / 255;
	this.particleBlue = (float) (color & 0xFF) / 255;
	this.particleAlpha = 130;
	this.tick = tick;
	this.endpos = new Vec3d(endX, endY, endZ);
	this.distance = new Vec3d(startX - endX, startY - endY, startZ - endZ).length();
	this.thickness = thickness;
	this.segments = (int)Math.sqrt(this.distance)*15;
	this.bumpcount = (int)segments/10 + 1;
}

@Override
public void renderParticle(IVertexBuilder builder, ActiveRenderInfo renderInfo, float partialTicks) {
    float x = (float)(MathHelper.lerp((double)partialTicks, this.prevPosX, this.posX));
    float y = (float)(MathHelper.lerp((double)partialTicks, this.prevPosY, this.posY));
    float z = (float)(MathHelper.lerp((double)partialTicks, this.prevPosZ, this.posZ));
     
    Vec3d startpos = new Vec3d(x, y, z);

    Vec3d difference = endpos.subtract(startpos);
    Vec3d particleToCamPos = startpos.subtract(renderInfo.getProjectedView()).add(difference);
    Vec3d cross = particleToCamPos.crossProduct(difference).normalize();
    
    Vec3d[] points1 = new Vec3d[this.bumpcount + 2];
    points1[this.bumpcount+1] = new Vec3d(this.distance, 0, 0);
    Vec3d[] points2 = points1.clone();
    points1[0] = new Vec3d(0, thickness/3, 0);
    points2[0] = new Vec3d(0, -thickness/3, 0);

    Vec3d prev1 = startpos.add(cross.scale(thickness/3));
    Vec3d prev2 = startpos.add(cross.scale(-thickness/3));
    double intensity = (bumpcount == 2) ? 0.2 : 0.6;
	for(int j = 0; j < this.bumpcount; j++) {
		double pos = (double)(j+1)/(bumpcount+2)*distance;
		points1[j + 1] = new Vec3d(pos, Math.pow(-1, j)*Math.sin(((tick+partialTicks)*j/2)*Math.PI/40)*intensity + thickness, 0);
		points2[j + 1] = new Vec3d(pos, Math.pow(-1, j)*Math.sin(((tick+partialTicks)*j/2)*Math.PI/40)*intensity, 0);
	}
		
    for(int i = 0; i < segments ; i++) {
    	Vec3d pos = startpos.add(difference.normalize().scale((double)(i+1)/segments*distance));
    	double offset1 = ModMathHelper.bezierCurve((double)(i+1)/segments, points1).y;
    	double offset2 = ModMathHelper.bezierCurve((double)(i+1)/segments, points2).y;
    	Vec3d pos1 = pos.add(cross.scale(offset1));
    	Vec3d pos2 = pos.add(cross.scale(offset2));

    	renderSegment(builder, renderInfo, partialTicks, pos1, pos2, prev2, prev1);
    	prev1 = pos1.scale(1);
    	prev2 = pos2.scale(1);
    }
}

private void renderSegment(IVertexBuilder builder, ActiveRenderInfo renderInfo, float partialTicks, Vec3d pos1, Vec3d pos2, Vec3d pos3, Vec3d pos4) {
	int brightness = getBrightnessForRender(partialTicks);
	Vec3d camera = renderInfo.getProjectedView();
	double x = camera.x;
	double y = camera.y;
	double z = camera.z;
	
	builder.pos(pos1.x-x, pos1.y-y, pos1.z-z).tex(getMaxU(), getMaxV()).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(brightness).endVertex();
	builder.pos(pos2.x-x, pos2.y-y, pos2.z-z).tex(getMaxU(), getMinV()).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(brightness).endVertex();
	builder.pos(pos3.x-x, pos3.y-y, pos3.z-z).tex(getMinU(), getMinV()).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(brightness).endVertex();
	builder.pos(pos4.x-x, pos4.y-y, pos4.z-z).tex(getMinU(), getMaxV()).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(brightness).endVertex();

}

@Override
public int getBrightnessForRender(float partialTick) {
	return LightTexture.packLight(15, 15);
}

@Override
public IParticleRenderType getRenderType() {
	return ModParticleRenderTypes.MANAWAVE_PARTICLE_TYPE;
}

@Override
public void tick() {
	this.setExpired();
}

public static ManawaveParticleData getData(boolean show, int color, int tick, double thickness) {
	return new ManawaveParticleData(show, color, tick, thickness);
}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<ManawaveParticleData> {
		
		private IAnimatedSprite sprite;

		public Factory(IAnimatedSprite sprite) {
			this.sprite = sprite;
		}
		
		public Particle makeParticle(ManawaveParticleData type, World worldIn, double x1, double y1, double z1, double x2, double y2, double z2) {
			ManawaveParticle shimmer = new ManawaveParticle(worldIn, x1, y1, z1, x2, y2, z2, type.getColor(), type.getThickness(), type.getTick());
			shimmer.selectSpriteRandomly(this.sprite);
			return shimmer;
		}
	}
}