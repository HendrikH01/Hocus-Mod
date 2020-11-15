package com.xX_deadbush_Xx.hocus.client;

import com.xX_deadbush_Xx.hocus.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.RitualPedestal;
import com.xX_deadbush_Xx.hocus.common.blocks.RitualStone;
import com.xX_deadbush_Xx.hocus.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModColorHandler {
	public static void registerBlocks() {
		BlockColors colors = Minecraft.getInstance().getBlockColors();
		colors.register((state, light, pos, color) -> ChalkBlock.getColor(state), ModBlocks.CHALK_BLOCK.get());
		
		colors.register((state, light, pos, color) -> {
			int power = state.get(RitualPedestal.POWER);
			GlowType type = power <= 1 ? GlowType.WHITE : state.get(RitualPedestal.GLOW_TYPE);
			return type.getColor(power, 0x262629);
		}, ModBlocks.RITUAL_PEDESTAL.get());
		
		colors.register((state, light, pos, color) -> {
			int power = state.get(RitualStone.POWER);
			GlowType type = power <= 1 ? GlowType.WHITE : state.get(RitualStone.GLOW_TYPE);
			return type.getColor(power, 0x262629);
		}, ModBlocks.RITUAL_STONE.get());
		
		colors.register((state, light, pos, color) ->  {
	         return 0x22721D;
		}, ModBlocks.DREADWOOD_LEAVES.get());
	}
	
	public static void registerItems() {
		ItemColors colors = Minecraft.getInstance().getItemColors();
		colors.register((stack, color) -> 0x3C8455, ModBlocks.DREADWOOD_LEAVES.get().asItem());

	}
}
