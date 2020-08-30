package com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.MediumFusionSerializer;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.MortarSerializer;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.OneInOneOutSerializer;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.SmallFusionSerializer;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.ToolTableShapedSerializer;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers.ToolTableShapelessSerializer;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.BloodPhialRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.DryingRackRecipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeTypes {
	public static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, WitchcraftMod.MOD_ID);
	
	public static final RegistryObject<MortarSerializer> MORTAR_SERIALIZER= SERIALIZERS
			.register(IMortarRecipe.TYPE_ID.getPath(), MortarSerializer::new);
	public static final RegistryObject<IRecipeSerializer<IDryingRackRecipe>> DRYING_RACK_SERIALIZER = SERIALIZERS
			.register(IDryingRackRecipe.TYPE_ID.getPath(), () -> new OneInOneOutSerializer<>(DryingRackRecipe::new));
	public static final RegistryObject<SmallFusionSerializer> SMALL_FUSION_SERIALIZER = SERIALIZERS
			.register(ISmallFusionRecipe.TYPE_ID.getPath(), SmallFusionSerializer::new);
	public static final RegistryObject<MediumFusionSerializer> MEDIUM_FUSION_SERIALIZER = SERIALIZERS
			.register(IMediumFusionRecipe.TYPE_ID.getPath(), MediumFusionSerializer::new);
	public static final RegistryObject<ToolTableShapedSerializer> TOOL_TABLE_SHAPED_SERIALIZER = SERIALIZERS
			.register("tool_table_shaped", ToolTableShapedSerializer::new);
	public static final RegistryObject<ToolTableShapelessSerializer> TOOL_TABLE_SHAPELESS_SERIALIZER = SERIALIZERS
			.register("tool_table_shapeless", ToolTableShapelessSerializer::new);
	public static final RegistryObject<SpecialRecipeSerializer<BloodPhialRecipe>> BLOOD_VIAL_SERIALIZER = SERIALIZERS
			.register("crafting_blood_vial", () -> new SpecialRecipeSerializer<>(BloodPhialRecipe::new));
	
	public static final IRecipeType<IMortarRecipe> MORTAR_TYPE = registerType(IMortarRecipe.TYPE_ID);
	public static final IRecipeType<IDryingRackRecipe> DRYING_RACK_TYPE = registerType(IDryingRackRecipe.TYPE_ID);
	public static final IRecipeType<ISmallFusionRecipe> SMALL_FUSION_TYPE = registerType(ISmallFusionRecipe.TYPE_ID);
	public static final IRecipeType<IMediumFusionRecipe> MEDIUM_FUSION_TYPE = registerType(IMediumFusionRecipe.TYPE_ID);
	public static final IRecipeType<IToolTableRecipe> TOOL_TABLE = registerType(IToolTableRecipe.TYPE_ID);

	private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
		@Override
		public String toString() {
			return Registry.RECIPE_TYPE.getKey(this).toString();
		}
	}
	
	private static <T extends IRecipeType<?>> T registerType(ResourceLocation id) {
		return (T) Registry.register(Registry.RECIPE_TYPE, id, new RecipeType<>());
	}
}
