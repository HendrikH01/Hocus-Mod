package com.xX_deadbush_Xx.hocus.client.renderers.spell_renderers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.xX_deadbush_Xx.hocus.api.spell.ISpellRenderer;
import com.xX_deadbush_Xx.hocus.client.ModRenderTypes;
import com.xX_deadbush_Xx.hocus.client.renderers.ManawaveRenderer;
import com.xX_deadbush_Xx.hocus.common.spell.DestructionSpell;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ManawaveSpellRenderer implements ISpellRenderer<DestructionSpell> {

	public static final ManawaveSpellRenderer INSTANCE = new ManawaveSpellRenderer();
	
	private ManawaveSpellRenderer() {}
	
	@Override
	public void render(PlayerEntity caster, DestructionSpell spell, ActiveRenderInfo renderInfo, float partialTicks, MatrixStack matrixStackIn, Impl buffers, int packedLightIn, Vec3d offset, boolean firstperson) {
		Vec3d target = new Vec3d(spell.args[0] + 0.5, spell.args[1] + 0.5, spell.args[2] + 0.5);
		ManawaveRenderer.render(buffers.getBuffer(ModRenderTypes.MANAWAVE), renderInfo, partialTicks, offset, target, 0x80F0F0FF, firstperson ? 0.01 : 0.2, spell.ticks);
	}

	@Override
	public RenderType getRenderType() {
		return ModRenderTypes.MANAWAVE;
	}
}
