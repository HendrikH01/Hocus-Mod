package com.xX_deadbush_Xx.witchcraftmod.client.renderers.wands;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface SpellRenderer {
	
	public void render(PlayerEntity caster, ActiveRenderInfo renderInfo, int ticks, float partialTicks, MatrixStack matrixStackIn, Impl buffers, int packedLightIn, Vec3d offset, boolean firstperson, int[] args);
}
