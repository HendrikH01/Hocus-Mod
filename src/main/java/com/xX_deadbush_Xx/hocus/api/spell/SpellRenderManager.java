package com.xX_deadbush_Xx.hocus.api.spell;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.api.util.render.RenderHelper;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Matrix4f;
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
	
	@SuppressWarnings({ "resource", "unchecked" })
	@SubscribeEvent
	public static <S extends SpellCast> void renderFirstPerson(RenderWorldLastEvent event) {
		PlayerEntity player = Minecraft.getInstance().player;
		PlayerSpellCapability cap = PlayerSpellCapability.getSpellCap(player);
		
		cap.getActiveWand().ifPresent(wand -> {
			S spell = (S) cap.getSpell();
			ISpellRenderer<S> renderer = (ISpellRenderer<S>) spell.getRenderer();
			
			if(renderer == null || Minecraft.getInstance().gameSettings.thirdPersonView != 0) return;
			
			ActiveRenderInfo renderInfo = Minecraft.getInstance().getRenderManager().info;
			float partialTicks = Minecraft.getInstance().getRenderPartialTicks();
			
			Vec3d lookVector = player.getLook(partialTicks);
			Vec3d upVector = player.getUpVector(partialTicks);
			Vec3d sideVector = lookVector.crossProduct(upVector);

			Vec3d offset = sideVector
					.scale(0.4)
					.add(lookVector.scale(0.35))
					.add(player.getEyePosition(partialTicks));
			
			
			MatrixStack stack = event.getMatrixStack();
			stack.push();
			RenderSystem.pushMatrix();

			renderer.render(player, spell, renderInfo, partialTicks, stack, RenderHelper.spellbuffers, 0x00F000F0, offset, true);
			
			RenderSystem.multMatrix(stack.getLast().getMatrix());
			RenderHelper.spellbuffers.finish();
			RenderSystem.popMatrix();
			stack.pop();
			
			RenderSystem.disableDepthTest();
		});
	}

	//TODO fix positioning of spell in 3rd person

	@SuppressWarnings({ "resource", "unchecked" })
	@SubscribeEvent
	public static <S extends SpellCast> void renderThirdPerson(RenderPlayerEvent.Post event) {
		PlayerEntity player = Minecraft.getInstance().player;
		
		PlayerSpellCapability cap = PlayerSpellCapability.getSpellCap(player);
		cap.getActiveWand().ifPresent(wand -> {
			S spell = (S) cap.getSpell();
			ISpellRenderer<S> renderer = (ISpellRenderer<S>) spell.getRenderer();
			
			if(renderer == null) return;
			
			MatrixStack stack = event.getMatrixStack();
			stack.push();
			float partialTicks = Minecraft.getInstance().getRenderPartialTicks();
			ActiveRenderInfo renderInfo = Minecraft.getInstance().getRenderManager().info;

			RenderSystem.pushMatrix();
			stack.push();
			RenderHelper.positionMatrixStackToHand(player, stack, HandSide.RIGHT);
			stack.translate(0.6, 0.75, 0);
			Matrix4f matrix = stack.getLast().getMatrix();
			stack.pop();

			RenderSystem.multMatrix(stack.getLast().getMatrix());
			//renderer.render(renderInfo, partialTicks, stack, RenderHelper.spellbuffers, 0x00F000F0, translation, false);
			
			RenderHelper.spellbuffers.finish();
			stack.pop();
			
			RenderSystem.popMatrix();
			RenderSystem.disableDepthTest();
		});
	}
}