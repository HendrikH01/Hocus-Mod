package com.xX_deadbush_Xx.witchcraftmod.common.event;

import java.util.ArrayList;
import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.CraftingHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.world.gen.OreGeneration;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class LoadComplete {
    public static List<ItemStack> mortarrecipeinputs = new ArrayList<>();

	@SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
    	OreGeneration.generateOre();
    	
    }
    
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
    	mortarrecipeinputs = CraftingHelper.getAllRecipeInputs(ModRecipeTypes.MORTAR_TYPE, event.getWorld().getWorld());
    	System.out.println(mortarrecipeinputs);
    }
}
