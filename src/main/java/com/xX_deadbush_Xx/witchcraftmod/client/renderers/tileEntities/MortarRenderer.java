package com.xX_deadbush_Xx.witchcraftmod.client.renderers.tileEntities;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModItems;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.MortarTile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class MortarRenderer extends TileEntityRenderer<MortarTile>{
	private static Map<Item, ItemPosition> specialpositions = new HashMap<>();
	
	static {
		specialpositions.put(ModItems.VIBRANT_CRYSTAL.get(), ItemPosition.LARGE_ITEM);
	}
	
	public MortarRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(MortarTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if(tileEntityIn.hasItem()) {
  	        ItemStack stack = tileEntityIn.getItem();

			matrixStackIn.push();
			setPos(matrixStackIn, stack); 
				        
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
	        IBakedModel model = itemRenderer.getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null);
	        
	        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, model);
			
	        matrixStackIn.pop();
	    }
	}
	
	public static void register() {
		ClientRegistry.bindTileEntityRenderer(ModTileEntities.MORTAR_TILE.get(), MortarRenderer::new);
	}
	
	private void setPos(MatrixStack matrixstack, ItemStack itemstack) {
		specialpositions.getOrDefault(itemstack.getItem(), ItemPosition.DEFAULT).setPos(matrixstack);
	}
	
	private enum ItemPosition {
		DEFAULT(42, 18, 103, 0.55f, 0.28f, 0.57f, 0.4f),
		LARGE_ITEM(36, 30, -24, 0.53f, 0.28f, 0.55f, 0.4f),
		BLOCK(0, 0, 0, 0.5f, 0.22f, 0.5f, 0.4f);
		
		private int rx;
		private int rz;
		private int ry;
		private float tx;
		private float ty;
		private float scale;
		private float tz;

		ItemPosition(int rx, int ry, int rz, float tx, float ty, float tz, float scale) {
			this.rx = rx;
			this.ry = ry;
			this.rz = rz;
			this.tx = tx;
			this.ty = ty;
			this.tz = tz;
			this.scale = scale;
		}
		
		public void setPos(MatrixStack stack) {
			stack.translate(tx, ty, tz);
        	stack.scale(scale, scale, scale);
	        stack.rotate(new Quaternion(rx, ry, rz, true));
		}
	}
}
