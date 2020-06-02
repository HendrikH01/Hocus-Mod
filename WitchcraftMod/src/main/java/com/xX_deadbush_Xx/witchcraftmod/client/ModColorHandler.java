package com.xX_deadbush_Xx.witchcraftmod.client;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModColorHandler {
	public static void registerBlocks() {
		BlockColors colors = Minecraft.getInstance().getBlockColors();
		colors.register((state, light, pos, color) -> ChalkBlock.getColor(state), ModBlocks.CHALK_BLOCK.get());
		//colors.register((state, light, pos, color) -> ChalkBlock.getColor(state), ModBlocks.RITUAL_STONE.get());
		colors.register((state, light, pos, color) ->  0x3C8455, ModBlocks.DREADWOOD_LEAVES.get());
	}
	
	public static void registerItems() {
		ItemColors colors = Minecraft.getInstance().getItemColors();
		colors.register((stack, color) -> 0x3C8455, ModBlocks.DREADWOOD_LEAVES.get().asItem());

	}
}
