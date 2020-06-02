package com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.MediumFusionSerializer;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.OneInOneOutSerializer;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.SmallFusionSerializer;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.DryingRackRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.MediumFusionRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.MortarRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.SmallFusionRecipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRecipeTypes {
	public static final IRecipeType<IDryingRackRecipe> DRYING_RACK_TYPE = new RecipeType<>();
	public static final IRecipeSerializer<DryingRackRecipe> DRYING_RACK_SERIALIZER = new OneInOneOutSerializer<>(DryingRackRecipe::new);
	public static final IRecipeType<ISmallFusionRecipe> SMALL_FUSION_TYPE = new RecipeType<>();
	public static final IRecipeSerializer<SmallFusionRecipe> SMALL_FUSION_SERIALIZER = new SmallFusionSerializer();
	public static final IRecipeType<IMediumFusionRecipe> MEDIUM_FUSION_TYPE = new RecipeType<>();
	public static final IRecipeSerializer<MediumFusionRecipe> MEDIUM_FUSION_SERIALIZER = new MediumFusionSerializer();
	public static final IRecipeType<IMortarRecipe> MORTAR_TYPE = new RecipeType<>();
	public static final IRecipeSerializer<MortarRecipe> MORTAR_SERIALIZER = new OneInOneOutSerializer<>(MortarRecipe::new);
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
		IForgeRegistry<IRecipeSerializer<?>> reg = evt.getRegistry();
		Registry.register(Registry.RECIPE_TYPE, IDryingRackRecipe.TYPE_ID, DRYING_RACK_TYPE);
		reg.register(DRYING_RACK_SERIALIZER.setRegistryName(IDryingRackRecipe.TYPE_ID));
		
		Registry.register(Registry.RECIPE_TYPE, ISmallFusionRecipe.TYPE_ID, SMALL_FUSION_TYPE);
		reg.register(SMALL_FUSION_SERIALIZER.setRegistryName(ISmallFusionRecipe.TYPE_ID));
		
		Registry.register(Registry.RECIPE_TYPE, IMediumFusionRecipe.TYPE_ID, MEDIUM_FUSION_TYPE);
		reg.register(MEDIUM_FUSION_SERIALIZER.setRegistryName(IMediumFusionRecipe.TYPE_ID));
	
		Registry.register(Registry.RECIPE_TYPE, IMortarRecipe.TYPE_ID, MORTAR_TYPE);
		reg.register(MORTAR_SERIALIZER.setRegistryName(IMediumFusionRecipe.TYPE_ID));
	}

	private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
		@Override
		public String toString() {
			return Registry.RECIPE_TYPE.getKey(this).toString();
		}
	}
}
