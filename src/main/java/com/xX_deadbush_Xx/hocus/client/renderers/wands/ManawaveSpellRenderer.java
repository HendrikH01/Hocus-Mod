package com.xX_deadbush_Xx.hocus.client.renderers.wands;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.xX_deadbush_Xx.hocus.client.ModRenderTypes;
import com.xX_deadbush_Xx.hocus.client.renderers.ManawaveRenderer;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ManawaveSpellRenderer implements SpellRenderer {
	
	public static final SpellRenderer INSTANCE = new ManawaveSpellRenderer();

	private ManawaveSpellRenderer() {}

	@Override
	public void render(PlayerEntity caster, ActiveRenderInfo renderInfo, int ticks, float partialTicks, MatrixStack matrixStackIn, Impl buffers, int packedLightIn, Vec3d offset, boolean firstperson, int[] args) {
		Vec3d target = new Vec3d(args[0] + 0.5, args[1] + 0.5, args[2] + 0.5);
		ManawaveRenderer.render(buffers.getBuffer(ModRenderTypes.MANAWAVE), renderInfo, partialTicks, offset, target, 0x80F0F0FF, firstperson ? 0.01 : 0.2, ticks);
	}
}
