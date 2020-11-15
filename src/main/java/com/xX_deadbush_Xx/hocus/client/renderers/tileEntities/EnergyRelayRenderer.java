package com.xX_deadbush_Xx.hocus.client.renderers.tileEntities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.xX_deadbush_Xx.hocus.api.util.render.RenderHelper;
import com.xX_deadbush_Xx.hocus.client.ModRenderTypes;
import com.xX_deadbush_Xx.hocus.client.renderers.ManawaveRenderer;
import com.xX_deadbush_Xx.hocus.common.tile.EnergyRelayTile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EnergyRelayRenderer extends TileEntityRenderer<EnergyRelayTile> {

	public EnergyRelayRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@SuppressWarnings("resource")
	@Override
	public void render(EnergyRelayTile tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if(!tile.isLinked()) return;
		stack.push();
		BlockPos pos1 = tile.getPos();
		BlockPos pos2 = tile.getTargetTile().getPos();
		
		Vec3d start  = new Vec3d(pos1.getX(), pos1.getY(), pos1.getZ());
		Vec3d end  = new Vec3d(pos2.getX(), pos2.getY(), pos2.getZ());
		
		RenderSystem.pushMatrix();
		//Needs custom render type. sorry vanilla buffers :(
		ManawaveRenderer.render(RenderHelper.spellbuffers.getBuffer(ModRenderTypes.MANAWAVE), Minecraft.getInstance().getRenderManager().info, partialTicks, start, end, 0x00F000F0, 0.2, tile.ticks);
		
		RenderSystem.multMatrix(stack.getLast().getMatrix());
		RenderHelper.spellbuffers.finish();

		RenderSystem.popMatrix();
		stack.pop();
	}

}
