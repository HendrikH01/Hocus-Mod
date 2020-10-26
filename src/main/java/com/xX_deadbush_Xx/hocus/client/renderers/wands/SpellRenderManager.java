package com.xX_deadbush_Xx.hocus.client.renderers.wands;

import java.util.Collections;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.api.util.RenderHelper;
import com.xX_deadbush_Xx.hocus.client.ModRenderTypes;
import com.xX_deadbush_Xx.hocus.common.items.wands.WandItem;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//Parts of this class were taken from gigaherz's ElementsOfPower mod with permission
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Hocus.MOD_ID)
public class SpellRenderManager {
     
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void renderFirstPerson(RenderWorldLastEvent event) {
		PlayerEntity player = Minecraft.getInstance().player;
		
		PlayerSpellCapability cap = PlayerSpellCapability.getSpellCap(player);
		cap.getActiveWand().ifPresent(wand -> {
			SpellRenderer renderer = ((WandItem)wand.getItem()).getSpellRenderer();
			
			if(renderer == null || Minecraft.getInstance().gameSettings.thirdPersonView != 0) return;
			
			ActiveRenderInfo renderInfo = Minecraft.getInstance().getRenderManager().info;
			float partialTicks = Minecraft.getInstance().getRenderPartialTicks();
			
			Vec3d lookVector = player.getLook(partialTicks);
			Vec3d upVector = player.getUpVector(partialTicks);
			Vec3d sideVector = lookVector.crossProduct(upVector);

			//Perfect positioning for wands
			Vec3d offset = sideVector
					.scale(0.4)
					.add(lookVector.scale(0.35))
					.add(player.getEyePosition(partialTicks));
			
			
			IRenderTypeBuffer.Impl buffers = IRenderTypeBuffer.getImpl(Collections.singletonMap(ModRenderTypes.MANAWAVE, new BufferBuilder(ModRenderTypes.MANAWAVE.getBufferSize())), Tessellator.getInstance().getBuffer());
			MatrixStack stack = event.getMatrixStack();
	
			RenderSystem.pushMatrix();
			RenderSystem.multMatrix(stack.getLast().getMatrix());

			renderer.render(player, renderInfo, cap.ticks, partialTicks, stack, buffers, 0x00F000F0, offset, true, cap.args);
			
			buffers.finish();
			RenderSystem.popMatrix();
			RenderSystem.disableDepthTest();
		});
	}

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void renderThirdPerson(RenderPlayerEvent.Post event) {
		PlayerEntity player = Minecraft.getInstance().player;
		
		PlayerSpellCapability cap = PlayerSpellCapability.getSpellCap(player);
		cap.getActiveWand().ifPresent(wand -> {
			SpellRenderer renderer = ((WandItem)wand.getItem()).getSpellRenderer();
			if(renderer == null) return;
			MatrixStack stack = event.getMatrixStack();
			stack.push();
			float partialTicks = Minecraft.getInstance().getRenderPartialTicks();
			ActiveRenderInfo renderInfo = Minecraft.getInstance().getRenderManager().info;
			IRenderTypeBuffer.Impl buffers = IRenderTypeBuffer.getImpl(Collections.singletonMap(ModRenderTypes.MANAWAVE, new BufferBuilder(ModRenderTypes.MANAWAVE.getBufferSize())), Tessellator.getInstance().getBuffer());

			RenderSystem.pushMatrix();
			stack.push();
			RenderHelper.positionMatrixStackToHand(player, stack, HandSide.RIGHT);
			stack.translate(0.6, 0.75, 0);
			Matrix4f matrix = stack.getLast().getMatrix();
			stack.pop();


			Vec3d translation = new Vec3d(matrix.m03, matrix.m13, matrix.m23).add(0, 2, 4);
			stack.translate(translation.x, translation.y, translation.z);
			RenderSystem.multMatrix(stack.getLast().getMatrix());
			//TODO fix this crap
			//renderer.render(player, renderInfo, cap.ticks, partialTicks, stack, buffers, 0x00F000F0, translation, false);
			
			buffers.finish();
			stack.pop();
			RenderSystem.popMatrix();
			RenderSystem.disableDepthTest();
		});
	}
}