package com.xX_deadbush_Xx.witchcraftmod.client.gui;

import java.util.Random;

import com.mojang.blaze3d.systems.RenderSystem;
import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ModMathHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.potion.ModPotions;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.CrystalEnergyStorage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class IngameGuiOverlay extends AbstractGui {
	private static final IngameGuiOverlay INSTANCE = new IngameGuiOverlay();
	private int windowwidth;
	private int windowheight;
	private double guiscale;
	private long healthUpdateCounter;
	private int lastPlayerHealth;
	private long lastSystemTime;
	private int playerHealth;
	
	private final static ResourceLocation VANILLA_GUI_ICONS = new ResourceLocation("textures/gui/icons.png");
	private final static ResourceLocation GUI_ICONS = new ResourceLocation(WitchcraftMod.MOD_ID, "textures/gui/mod_gui_icons.png");
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void render(RenderGameOverlayEvent.Pre evt) {
		INSTANCE.guiscale = evt.getWindow().getGuiScaleFactor();
		INSTANCE.windowwidth = (int)(evt.getWindow().getWidth()/INSTANCE.guiscale);
		INSTANCE.windowheight = (int)(evt.getWindow().getHeight()/INSTANCE.guiscale);

		INSTANCE.renderOverlay(evt);
	}
	
	private void renderOverlay(RenderGameOverlayEvent.Pre evt) {
		Minecraft mc = Minecraft.getInstance();
		TextureManager texmanager = mc.getTextureManager();
		
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultBlendFunc();
		texmanager.bindTexture(GUI_ICONS);
		PlayerEntity player = getPlayer();

		if(evt.getType() == ElementType.HEALTH && player != null) {
			int hearts = MathHelper.ceil(player.getHealth());
	        long time = Util.milliTime();
			boolean flag = this.healthUpdateCounter > (long) mc.ingameGUI.getTicks() && (this.healthUpdateCounter - (long) mc.ingameGUI.getTicks()) / 3L % 2L == 1L;
	        if (hearts < this.playerHealth && player.hurtResistantTime > 0) {
	           this.lastSystemTime = time;
	           this.healthUpdateCounter = (long)(mc.ingameGUI.getTicks() + 20);
	        } else if (hearts > this.playerHealth && player.hurtResistantTime > 0) {
	           this.lastSystemTime = time;
	           this.healthUpdateCounter = (long)(mc.ingameGUI.getTicks() + 10);
	        }

	        if (time - this.lastSystemTime > 1000L) {
	           this.lastPlayerHealth = hearts;
	           this.playerHealth = hearts;
	           this.lastSystemTime = time;
				System.out.println(flag + " " + lastPlayerHealth + " " + hearts);
	        }
	        
	        this.playerHealth = hearts;
	        
			if(player.isPotionActive(ModPotions.BELLADONNA_POISION)) {
				evt.setCanceled(true);
				Random rand = new Random(mc.ingameGUI.getTicks() * 312871);
				float maxhealth = (float) mc.player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getValue();
				int absorption = MathHelper.ceil(player.getAbsorptionAmount());
				int lasthealth = this.lastPlayerHealth;
				int i = MathHelper.ceil(player.getHealth());
				int i1 = mc.getMainWindow().getScaledWidth() / 2 - 91;
				int k1 = mc.getMainWindow().getScaledHeight() - 39;
				int i2 = MathHelper.ceil((maxhealth + (float) absorption) / 2.0F / 10.0F);
				int j2 = Math.max(10 - (i2 - 2), 3);
				int i3 = absorption;
				int k3 = -1;
				for (int l5 = MathHelper.ceil((maxhealth + (float) absorption) / 2.0F) - 1; l5 >= 0; --l5) {					
		            int j4 =  flag ? 1 : 0;
		            
					int k4 = MathHelper.ceil((float) (l5 + 1) / 10.0F) - 1;
					int l4 = i1 + l5 % 10 * 8;
					int i5 = k1 - k4 * j2;
					if (i <= 4) {
						i5 += rand.nextInt(2);
					}
	
					if (i3 <= 0 && l5 == k3) {
						i5 -= 2;
					}
	
					int posyfactor = 0;
					if (player.world.getWorldInfo().isHardcore()) {
						posyfactor = 5;
					}
					texmanager.bindTexture(VANILLA_GUI_ICONS);
					this.blit(l4, i5, 16 + j4 * 9, 9 * posyfactor, 9, 9); //golden hearts
					if (i3 > 0) {
						if (i3 == absorption && absorption % 2 == 1) {
							this.blit(l4, i5, 16 + 153, 9 * posyfactor, 9, 9);
							--i3;
						} else {
							this.blit(l4, i5, 16 + 144, 9 * posyfactor, 9, 9);
							i3 -= 2;
						}
					}
					
					texmanager.bindTexture(GUI_ICONS);
					if (flag) {
						if (l5 * 2 + 1 < lasthealth) {
							this.blit(l4, i5, 49, 9 * posyfactor + 28, 9, 9);
						}
	
						if (l5 * 2 + 1 == lasthealth) {
							this.blit(l4, i5, 58, 9 * posyfactor + 28, 9, 9);
						}
					}
	
					if (i3 <= 0) {
						if (l5 * 2 + 1 < i) {
							this.blit(l4, i5, 31, 9 * posyfactor + 28, 9, 9);
						}
	
						if (l5 * 2 + 1 == i) {
							this.blit(l4, i5, 40, 9 * posyfactor + 28, 9, 9);
						}
					}
				}
			}
		}

		renderManaMeter();
		restoreRenderSettings();	
	}
	
	private PlayerEntity getPlayer() {
	      return !(Minecraft.getInstance().getRenderViewEntity() instanceof PlayerEntity) ? null : (PlayerEntity)Minecraft.getInstance().getRenderViewEntity();
	}
	
	private void renderManaMeter() {
		Minecraft mc = Minecraft.getInstance();
		
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);

		CrystalEnergyStorage energystorage = mc.player.getCapability(CrystalEnergyStorage.Capability.get(), null).orElse(null);
		int energy = energystorage == null ? 0 : energystorage.getEnergy();
		int maxenergy = energystorage == null ? 0 : energystorage.getMaxEnergy();
		
		if(energy == 0) return;
		
		double ratio = (double)energy/maxenergy;
		int boxwidth = ModMathHelper.getIntDigits(energy)*6+16;
		int boxY = windowheight - 20;
		for(int i = 0; i < 28*ratio; i++) blit((int)(boxwidth/2 - 9), boxY - 7 - i*3, 32, 0, 17, 3);

		
		renderTextBox(3, boxY, energy);
	
		blit((int)(boxwidth/2 - 12), boxY - 98, 23, 100);

		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
	}
	
	private void renderTextBox(int x, int y, int number) {
		Minecraft mc = Minecraft.getInstance();
		TextureManager texmanager = mc.getTextureManager();
		int digits = ModMathHelper.getIntDigits(number);
		blit(x, y, 32, 13, 9, 12);
		for(int i = 1; i < digits; i++) blit(x + i * 6, y, 41, 13, 6, 12);
		blit(x + digits*6, y, 47, 13, 9, 12);
		
		mc.fontRenderer.drawString(number + "", x + 5, y + 3, 0x542409);
		texmanager.bindTexture(GUI_ICONS);
	}
	
	private void blit(int x, int y, int texW, int texH) {
		blit(x, y, 0, 0, texW, texH);
	}
	
	private void restoreRenderSettings() {
		Minecraft mc = Minecraft.getInstance();
		TextureManager texmanager = mc.getTextureManager();
		texmanager.bindTexture(IngameGui.GUI_ICONS_LOCATION);
	}
}