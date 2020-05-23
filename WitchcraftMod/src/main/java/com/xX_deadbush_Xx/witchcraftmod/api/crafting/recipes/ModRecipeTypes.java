package com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes;

import static com.xX_deadbush_Xx.witchcraftmod.api.util.WitchcraftLib.*;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.DryingRackSerializer;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.SmallFusionSerializer;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.DryingRackRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.SmallFusionRecipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRecipeTypes {
	public static final IRecipeType<IDryingRackRecipe> DRYING_RACK_TYPE = new RecipeType<>();
	public static final IRecipeSerializer<DryingRackRecipe> DRYING_RACK_SERIALIZER = new DryingRackSerializer();
	public static final IRecipeType<ISmallFusionRecipe> SMALL_FUSION_TYPE = new RecipeType<>();
	public static final IRecipeSerializer<SmallFusionRecipe> SMALL_FUSION_SERIALIZER = new SmallFusionSerializer();
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
		Registry.register(Registry.RECIPE_TYPE, DRYING_RACK_ID, DRYING_RACK_TYPE);
		evt.getRegistry().register(DRYING_RACK_SERIALIZER.setRegistryName(DRYING_RACK_ID));
		
		Registry.register(Registry.RECIPE_TYPE, SMALL_FUSION_ID, SMALL_FUSION_TYPE);
		evt.getRegistry().register(SMALL_FUSION_SERIALIZER.setRegistryName(SMALL_FUSION_ID));
	}

	private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
		@Override
		public String toString() {
			return Registry.RECIPE_TYPE.getKey(this).toString();
		}
	}
}
