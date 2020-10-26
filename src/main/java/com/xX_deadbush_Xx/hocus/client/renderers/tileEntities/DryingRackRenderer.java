package com.xX_deadbush_Xx.hocus.client.renderers.tileEntities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.xX_deadbush_Xx.hocus.common.blocks.DryingRackBlock;
import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;
import com.xX_deadbush_Xx.hocus.common.tile.DryingRackTile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class DryingRackRenderer extends TileEntityRenderer<DryingRackTile>{
	
	public DryingRackRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(DryingRackTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if(tileEntityIn.hasItem()) {
			Direction dir = DryingRackBlock.getDirection(tileEntityIn.getBlockState());
		    Quaternion rotation = new Quaternion(90, 0, dir == Direction.NORTH || dir == Direction.SOUTH ? 0 : 90, true);
		    
			matrixStackIn.push();
	        matrixStackIn.translate(0.5, 1.0, 0.5);
	        matrixStackIn.scale(0.6f, 0.6f, 0.6f);
	        matrixStackIn.rotate(rotation);
	        
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
	        ItemStack stack = tileEntityIn.getItem ();
	        IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null);
	        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);
			
	        matrixStackIn.pop();
	    }
	}
	
	public static void register() {
		ClientRegistry.bindTileEntityRenderer(ModTileEntities.DRYING_RACK.get(), DryingRackRenderer::new);
	}

}
