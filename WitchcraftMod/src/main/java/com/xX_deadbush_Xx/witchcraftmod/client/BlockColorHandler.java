package com.xX_deadbush_Xx.witchcraftmod.client;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockColorHandler {
	public static void register() {
		BlockColors colors = Minecraft.getInstance().getBlockColors();
		colors.register((state, light, pos, color) -> ChalkBlock.getColor(state), ModBlocks.CHALK_BLOCK.get());
		//colors.register((state, light, pos, color) -> ChalkBlock.getColor(state), ModBlocks.RITUAL_STONE.get());
	}
}
