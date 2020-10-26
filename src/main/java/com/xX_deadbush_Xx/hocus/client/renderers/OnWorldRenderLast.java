package com.xX_deadbush_Xx.hocus.client.renderers;

import java.util.Collections;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Hocus.MOD_ID, bus=Bus.FORGE)
public class OnWorldRenderLast {
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onHighlightBlock(RenderWorldLastEvent event) {
		PlayerEntity player = Minecraft.getInstance().player;
		
		if(player.getHeldItemMainhand().getItem().equals(ModItems.LINKING_WAND.get())) {
			CompoundNBT tag = player.getHeldItemMainhand().getOrCreateTag();
			if(!tag.contains("first")) return;
			BlockPos pos = NBTUtil.readBlockPos(tag.getCompound("first"));
			if(!player.getPosition().withinDistance(pos, 64)) return;
			
			MatrixStack stack = event.getMatrixStack();
			BlockState state = player.world.getBlockState(pos);
			Vec3d view = Minecraft.getInstance().getRenderManager().info.getProjectedView();
			IRenderTypeBuffer.Impl buffer = IRenderTypeBuffer.getImpl(Collections.singletonMap(RenderType.LINES, new BufferBuilder(RenderType.getLines().getBufferSize())), Tessellator.getInstance().getBuffer());
			WorldRenderer.drawVoxelShapeParts(stack, buffer.getBuffer(RenderType.LINES), state.getShape(player.world, pos, ISelectionContext.forEntity(player)),
					(double)pos.getX() - view.x, 
					(double)pos.getY() - view.y, 
					(double)pos.getZ() - view.z, (float)99/255, (float)214/255, 0.0f, 0.4F);
			buffer.finish();
		}
	}
}
