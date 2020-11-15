package com.xX_deadbush_Xx.hocus.client.renderers.spell_renderers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.xX_deadbush_Xx.hocus.api.spell.ISpellRenderer;
import com.xX_deadbush_Xx.hocus.client.ModRenderTypes;
import com.xX_deadbush_Xx.hocus.client.renderers.LightningRenderer;
import com.xX_deadbush_Xx.hocus.common.spell.LightningSpell;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class LightningSpellRenderer implements ISpellRenderer<LightningSpell> {

	public static final LightningSpellRenderer INSTANCE = new LightningSpellRenderer();
	
	private LightningSpellRenderer() {}
	
	@Override
	public void render(PlayerEntity caster, LightningSpell spell, ActiveRenderInfo renderInfo, float partialTicks, MatrixStack matrixstack, Impl buffers, int packedLightIn, Vec3d offset, boolean firstperson) {
		LightningRenderer.render(buffers.getBuffer(ModRenderTypes.LIGHTNING_SPELL), renderInfo, partialTicks, offset, spell.getTree(), 0x80F0F0FF, 0.05f);
	}

	@Override
	public RenderType getRenderType() {
		return ModRenderTypes.getLightning();
	}
}
