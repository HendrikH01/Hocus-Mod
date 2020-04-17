package com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.DryingRackSerializer;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.DryingRackRecipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRecipeTypes {
	public static final IRecipeType<IDryingRackRecipe> DRYING_RACK_TYPE = new RecipeType<>();
	public static final IRecipeSerializer<DryingRackRecipe> DRYING_RACK_SERIALIZER = new DryingRackSerializer<>(DryingRackRecipe::new);

	@SubscribeEvent
	public static void register(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
		System.out.println("type register");

		ResourceLocation id = new ResourceLocation(WitchcraftMod.MOD_ID, "drying_rack");

		Registry.register(Registry.RECIPE_TYPE, id, DRYING_RACK_TYPE);
		evt.getRegistry().register(DRYING_RACK_SERIALIZER.setRegistryName(id));
	}

	private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
		@Override
		public String toString() {
			return Registry.RECIPE_TYPE.getKey(this).toString();
		}
	}
}
