package com.xX_deadbush_Xx.witchcraftmod.common.compat.jei;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.common.compat.jei.categories.DryingRackCategory;
import com.xX_deadbush_Xx.witchcraftmod.common.compat.jei.categories.SmallFusionCategory;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@JeiPlugin
@OnlyIn(Dist.CLIENT)
public class WitchcraftJEIPlugin implements IModPlugin{

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation("witchcraftmod", "jei");
	}
	
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DRYING_RACK.get()), DryingRackCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.RITUAL_STONE.get()), SmallFusionCategory.UID);

    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
		registry.addRecipes(findRecipesByType(ModRecipeTypes.DRYING_RACK_TYPE), DryingRackCategory.UID);
		registry.addRecipes(findRecipesByType(ModRecipeTypes.SMALL_FUSION_TYPE), SmallFusionCategory.UID);

    }

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(
				new DryingRackCategory(registry.getJeiHelpers().getGuiHelper()),
				new SmallFusionCategory(registry.getJeiHelpers().getGuiHelper())
		);
	}
	
	public static List<IRecipe<?>> findRecipesByType(IRecipeType<?> type) {
		ClientWorld world = Minecraft.getInstance().world;
		return world != null ? world.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == type).collect(Collectors.toList()) : Collections.emptyList();
	}
}
