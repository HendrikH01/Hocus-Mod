package com.xX_deadbush_Xx.hocus.api.spell;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ISpellRenderer<S extends SpellCast> {
	
	public void render(PlayerEntity caster, S spell, ActiveRenderInfo renderInfo, float partialTicks, MatrixStack matrixStackIn, Impl buffers, int packedLightIn, Vec3d offset, boolean firstperson);

	public RenderType getRenderType();
}
