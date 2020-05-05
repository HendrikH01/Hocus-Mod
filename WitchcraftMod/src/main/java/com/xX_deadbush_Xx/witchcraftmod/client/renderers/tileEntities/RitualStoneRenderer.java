package com.xX_deadbush_Xx.witchcraftmod.client.renderers.tileEntities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class RitualStoneRenderer extends TileEntityRenderer<RitualStoneTile>{
	
	public RitualStoneRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(RitualStoneTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {		
	    if(tileEntityIn.hasItem()) {
			long time = System.currentTimeMillis();
		    Quaternion rotation = Vector3f.YP.rotationDegrees((time/20)%360);
			
			matrixStackIn.push();
	        matrixStackIn.translate(0.5, 1.3, 0.5);
	        matrixStackIn.scale(0.6f, 0.6f, 0.6f);
	        matrixStackIn.rotate(rotation);
	        
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
	        ItemStack stack = tileEntityIn.getItem();
	        IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null);
	        
	        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);
			
	        matrixStackIn.pop();
	    }
	}
	
	public static void register() {
		ClientRegistry.bindTileEntityRenderer(ModTileEntities.RITUAL_STONE.get(), RitualStoneRenderer::new);
	}
}
