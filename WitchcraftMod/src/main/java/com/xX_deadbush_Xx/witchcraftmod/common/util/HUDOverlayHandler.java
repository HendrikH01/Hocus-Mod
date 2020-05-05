package com.xX_deadbush_Xx.witchcraftmod.common.util;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HUDOverlayHandler {
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void drawPlayerStats(RenderGameOverlayEvent.Pre evt) {
		if(evt.getType() == ElementType.HEALTH) {
			evt.setCanceled(true);
			
			Minecraft mc = Minecraft.getInstance();
			PlayerEntity player =  mc.player;
			
			mc.getTextureManager().bindTexture(new ResourceLocation(WitchcraftMod.MOD_ID, "textures/mob_effect/belladonna_poison.png"));

			mc.ingameGUI.blit(100, 100, 0, 0, 9, 9);

			// rebind default icons
			mc.getTextureManager().bindTexture(IngameGui.GUI_ICONS_LOCATION);
		}
	}
	
	public static void enableAlpha(float alpha)
	{
		GlStateManager.enableBlend();

		if (alpha == 1f)
			return;

		GlStateManager.blendColor(1.0F, 1.0F, 1.0F, alpha);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public static void disableAlpha(float alpha)
	{
		GlStateManager.disableBlend();

		if (alpha == 1f)
			return;

		GlStateManager.blendColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
}