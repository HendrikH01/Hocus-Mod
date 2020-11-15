package com.xX_deadbush_Xx.hocus.api.util.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.INBTSerializable;

public class LightningTree implements INBTSerializable<CompoundNBT> {
	
	private LightningSegment root;
	public int depth;
	public final int maxDepth;

	public LightningTree(Vec3d start, Vec3d direction, int maxDepth, float scale) {
		this.root = new LightningSegment(start, direction.normalize().scale(scale));
		this.depth = 1;
		this.maxDepth = maxDepth;
	}

	public boolean isMaxDepth() {
		return this.depth >= this.maxDepth;
	}
	
	public LightningSegment getRoot() {
		return root;
	}

	public void generateNext(Random rand, float branchChance, float spread) {
		List<LightningSegment> segments = findEndNodesRecursive(root);
		int branched = 0;
		if (depth == 1)
			segments.add(root);

		for (LightningSegment segment : segments) {
			int branchAmount = 1;
			if (rand.nextFloat() > 1 - Math.pow(2, this.depth / 1.5 - this.maxDepth / 3) + Math.pow(2, this.maxDepth))
				return;
			if (rand.nextFloat() < branchChance / Math.sqrt(branched))
				branchAmount++;

			for (int i = 0; i < branchAmount; i++) {
				branched++;
				segment.children.add(new LightningSegment(segment.end,
						segment.end.add(segment.getDifference().add(rand.nextDouble() * spread - spread / 2,
								rand.nextDouble() * spread - spread / 2, rand.nextDouble() * spread - spread / 2))));
			}
		}

		this.depth++;
	}

	private List<LightningSegment> findEndNodesRecursive(LightningSegment check) {
		List<LightningSegment> found = new ArrayList<>();
		check.children.forEach((node) -> {
			if (node.children.size() == 0)
				found.add(node);
			else
				found.addAll(findEndNodesRecursive(node));
		});
		return found;
	}

	private List<LightningSegment> getAllChildNodesRecursive(LightningSegment check) {
		List<LightningSegment> out = new ArrayList<>();
		check.children.forEach((node) -> {
			out.add(node);
			if (node.children.size() != 0)
				out.addAll(getAllChildNodesRecursive(node));
		});
		return out;
	}

	public List<LightningSegment> toList() {
		List<LightningSegment> out = getAllChildNodesRecursive(root);
		out.add(root);
		return out;
	}

	public static class LightningSegment {
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

	// TODO serialize LightningTree
	
	@Override
	public CompoundNBT serializeNBT() {
		return new CompoundNBT();
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
	}
}