package com.xX_deadbush_Xx.witchcraftmod.client.effect.particles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.xX_deadbush_Xx.witchcraftmod.client.ModRenderTypes;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data.ScaledColoredParticleData;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LightningParticle extends SpriteTexturedParticle {
	
	Random rand = new Random();
	LightningTree tree;
	
public LightningParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double offsetX, double offsetY, double offsetZ, int color) {
	super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0, 0, 0);
	this.maxAge = 4;
	this.particleRed = (float) ((color & 0xFF0000) >> 16) / 255;
	this.particleGreen = (float) ((color & 0xFF00) >> 8) / 255;
	this.particleBlue = (float) (color & 0xFF) / 255;
	
	Vec3d offset = new Vec3d(offsetX, offsetY, offsetZ).scale(0.8);
	this.tree = new LightningTree(new Vec3d(posX, posY, posZ), new Vec3d(posX, posY, posZ).add(offset), 7);
}

@Override
public void renderParticle(IVertexBuilder builder, ActiveRenderInfo renderInfo, float partialTicks) {
	Vec3d campos = renderInfo.getProjectedView();
	int brightness = this.getBrightnessForRender(partialTicks);
	
    this.tree.toList().forEach((segment) -> {
    	renderLightningSegment(builder, campos, brightness, segment.start, segment.end);
    });
}

private void renderLightningSegment(IVertexBuilder builder, Vec3d camerapos, int brightness, Vec3d start, Vec3d end) {
    Vec3d difference = start.subtract(end);
    Vec3d particleToCamPos = start.subtract(camerapos).add(difference);
    Vec3d cross = particleToCamPos.crossProduct(difference).normalize();

	Vector3f[] positions = new Vector3f[]{
			new Vector3f(cross.inverse()), 
			new Vector3f(cross), 
			new Vector3f(cross), 
			new Vector3f(cross.inverse())};
    
    for (int i = 0; i < 4; ++i) {
    	positions[i].mul(0.05f, 0.05f, 0.05f);
    	if(i > 1) {
			positions[i].add(new Vector3f(start));
		} else {
			positions[i].add(new Vector3f(end));
		}
    	
    	positions[i].sub(new Vector3f(camerapos));
	}

    builder.pos((double)positions[0].getX(), (double)positions[0].getY(), (double)positions[0].getZ()).tex(this.getMaxU(), this.getMaxV()).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(brightness).endVertex();
    builder.pos((double)positions[1].getX(), (double)positions[1].getY(), (double)positions[1].getZ()).tex(this.getMaxU(), this.getMinV()).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(brightness).endVertex();
    builder.pos((double)positions[2].getX(), (double)positions[2].getY(), (double)positions[2].getZ()).tex(this.getMinU(), this.getMinV()).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(brightness).endVertex();
    builder.pos((double)positions[3].getX(), (double)positions[3].getY(), (double)positions[3].getZ()).tex(this.getMinU(), this.getMaxV()).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(brightness).endVertex();
}

@Override
public int getBrightnessForRender(float partialTick) {
	return LightTexture.packLight(15, 15);
}

@Override
public IParticleRenderType getRenderType() {
	return ModRenderTypes.GLOWING_TRANSLUCENT_PARTICLE_TYPE;
}

@Override
public void tick() {
	if (this.age++ >= this.maxAge) {
		this.setExpired();
	} else if(!this.tree.isMaxDepth()) {
 		this.tree.generateNext(this.rand, 0.3f, 0.5f);
 		this.tree.generateNext(this.rand, 0.3f, 0.5f);
	}
}

public static ScaledColoredParticleData getData(boolean show, int color, float scale) {
	return new Data(show, color, scale);
}

private static class LightningTree {
	private LightningSegment root;
	public int depth;
	public int maxDepth;
	
	public LightningTree(Vec3d start, Vec3d end, int maxDepth) {
		this.root = new LightningSegment(start, end);
		this.depth = 1;
		this.maxDepth = maxDepth;
	}
	
	public boolean isMaxDepth() {
		return this.depth >= this.maxDepth;
	}

	public void generateNext(Random rand, float branchChance, float spread) {
		List<LightningSegment> segments = findEndNodesRecursive(root);
		int branched = 0;
		if(depth == 1) segments.add(root);
		
		for(LightningSegment segment : segments) {
			int branchAmount = 1;
			if(rand.nextFloat() > 1 - Math.pow(2, this.depth/1.5 - this.maxDepth/3) + Math.pow(2, this.maxDepth)) return;
			if(rand.nextFloat() < branchChance/Math.sqrt(branched)) branchAmount++;
			
			for(int i = 0; i < branchAmount; i++) {
				branched++;
				segment.children.add(new LightningSegment(segment.end, segment.end.add(segment.getDifference().add(rand.nextDouble()*spread - spread/2, rand.nextDouble()*spread - spread/2, rand.nextDouble()*spread - spread/2))));
			}
		}
		
		this.depth++;
	}
	
	private List<LightningSegment> findEndNodesRecursive(LightningSegment check) {
		List<LightningSegment> found = new ArrayList<>();
		check.children.forEach((node) -> {
			if(node.children.size() == 0) found.add(node);
			else found.addAll(findEndNodesRecursive(node));
			});
		return found;
	}
	
	private List<LightningSegment> getAllChildNodesRecursive(LightningSegment check) {
		List<LightningSegment> out = new ArrayList<>();
		check.children.forEach((node) -> {
				out.add(node);
				if(node.children.size() != 0) out.addAll(getAllChildNodesRecursive(node));
			});
		return out;
	}
	
	public List<LightningSegment> toList() {
		List<LightningSegment> out = getAllChildNodesRecursive(root);
		out.add(root);
		return out;
	}
	
	
		private static class LightningSegment {
			public List<LightningSegment> children = new ArrayList<>();
			public Vec3d start;
			public Vec3d end;
			
			public LightningSegment(Vec3d start, Vec3d end, LightningSegment... children) {
				this.children.addAll(Arrays.asList(children));
				this.start = start;
				this.end = end;
			}
			
			public Vec3d getDifference() {
				return end.subtract(start);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<ScaledColoredParticleData> {
		private final IAnimatedSprite spriteSet;
	
		public Factory(IAnimatedSprite p_i50823_1_) {
			this.spriteSet = p_i50823_1_;
		}
	
		public Particle makeParticle(ScaledColoredParticleData type, World worldIn, double x, double y, double z, double ox,
				double oy, double oz) {
			LightningParticle shimmer = new LightningParticle(worldIn, x, y, z, ox, oy, oz, type.getColor());
			shimmer.selectSpriteRandomly(this.spriteSet);
			return shimmer;
		}
	}
	
	public static class Data extends ScaledColoredParticleData {
		public Data(boolean alwaysShow, int color, float scale) {
			super(Data::new, alwaysShow, color, scale);
		}

		@Override
		public ParticleType<?> getType() {
			return ModParticles.LIGHTNING.get();
		}
	}
}