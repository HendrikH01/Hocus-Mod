package com.xX_deadbush_Xx.hocus.common.world.data.wsd;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.hocus.api.util.CraftingHelper;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

//Not used because I found a better way, leaving this as a skeleton if someone needs to use WSD
public class RecipeInputWSD extends WorldSavedData {
    private static final String NAME = Hocus.MOD_ID + "_recipe_inputs";
    private World world;
    private Set<Item> mortarinputs = new HashSet<>();

    public RecipeInputWSD(World world) {
    	super(NAME);
    	this.world = world;
    }

    @Override
    public void read(CompoundNBT nbt) {
    	ListNBT list = nbt.getList("items", 10);
    	
        for(int i = 0; i < list.size(); ++i) {
        	this.mortarinputs.add(Registry.ITEM.getOrDefault(new ResourceLocation(((CompoundNBT)list.get(i)).getString("id"))));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
    	ListNBT list = new ListNBT();
    	Set<Item> items = CraftingHelper.getAllRecipeInputs(ModRecipeTypes.MORTAR_TYPE, this.world).stream().map(i -> i.getItem()).collect(Collectors.toSet());
    	for(Item item : items) {
    	      ResourceLocation resourcelocation = Registry.ITEM.getKey(item);
    	      CompoundNBT compound = new CompoundNBT();
    	      compound.putString("id", resourcelocation.toString());
    	      list.add(compound);
    	}
    	nbt.put("items", list);
    	return nbt;
    }
    
    public static RecipeInputWSD read(ServerWorld world) {
        DimensionSavedDataManager storage = world.getSavedData();
        RecipeInputWSD instance = (RecipeInputWSD) storage.getOrCreate(() -> new RecipeInputWSD(world), Hocus.MOD_ID);
        
        if (instance == null) {
            instance = new RecipeInputWSD(world);
            storage.set(instance);
        }
        return instance;
    }
    
    public static RecipeInputWSD findRecipeInputs(World world) {
    	RecipeInputWSD instance = new RecipeInputWSD(world);
    	instance.mortarinputs = CraftingHelper.getAllRecipeInputs(ModRecipeTypes.MORTAR_TYPE, world).stream().map(i -> i.getItem()).collect(Collectors.toSet());
    	return instance;
    }
  

	public Set<Item> getMortarInputs() {
		return mortarinputs;
	}
}
