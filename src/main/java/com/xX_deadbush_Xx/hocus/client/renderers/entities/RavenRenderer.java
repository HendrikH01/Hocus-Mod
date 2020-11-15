package com.xX_deadbush_Xx.hocus.client.renderers.entities;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.entity.RavenEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RavenRenderer extends MobRenderer<RavenEntity, RavenModel> {
   private static final ResourceLocation TEXTURE_RL = new ResourceLocation(Hocus.MOD_ID, "textures/entity/raven.png");

   public RavenRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new RavenModel(), 0.35F);
   }

   public ResourceLocation getEntityTexture(RavenEntity entity) {
      return TEXTURE_RL;
   }
}
