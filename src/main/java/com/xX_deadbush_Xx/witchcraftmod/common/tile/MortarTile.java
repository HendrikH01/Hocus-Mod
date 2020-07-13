package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.api.tile.BasicItemHolderTile;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.CraftingHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ItemStackHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.MortarRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MortarTile extends BasicItemHolderTile {
	private static Set<Item> possibleRecipeInputs = new HashSet<>();
	
	public MortarTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn, 1);
	}
	
	public MortarTile() {
		this(ModTileEntities.MORTAR_TILE.get());
	}
	
	public void swapItems(World worldIn, BlockPos pos, PlayerEntity player) {
		ItemStack playerItems = player.getHeldItemMainhand().copy();
		ItemStack returnedItem = ItemStack.EMPTY;
		boolean empty = false;
		
		if(possibleRecipeInputs.isEmpty()) {
			possibleRecipeInputs = CraftingHelper.getAllRecipeInputs(ModRecipeTypes.MORTAR_TYPE, world).stream().map(i -> i.getItem()).collect(Collectors.toSet());
		}
		
		if (this.hasItem() || getItem().equals(playerItems)) {
			returnedItem = this.getItem().copy();
			empty = true;
		}
		
		if ((!getItem().equals(playerItems) && !empty || !this.hasItem()) && possibleRecipeInputs.contains(playerItems.getItem())) {
			player.getHeldItemMainhand().shrink(1);
			playerItems.setCount(1);
			setItem(playerItems); 
			this.markDirty();
		}
		
		if(empty) {
			setItem(ItemStack.EMPTY); 
			this.markDirty();
		}
		
		if(!returnedItem.isEmpty()) {
			if (playerItems.isEmpty())
				player.setHeldItem(Hand.MAIN_HAND, returnedItem);
			else if (!player.inventory.addItemStackToInventory(returnedItem)) {
				ItemStackHelper.spawnItem(worldIn, returnedItem, this.pos);
			}
		}
	}

	public boolean hasItem() {
		return !this.getItem().isEmpty() && !this.getItem().getItem().equals(Items.AIR);
	}
	
	public ItemStack getItem() {
		return this.inventory.getStackInSlot(0);
	}
	
	public void setItem(ItemStack stack) {
		this.inventory.setStackInSlot(0, stack);
	}
	
	public void attemptCrafting() {
		if(world.isRemote()) return;		
		if(Math.random() < 0.7) {
			return;
		}
		MortarRecipe recipe = getRecipe(getItem());
		if(recipe != null) craft(recipe);
	}
	
	private void craft(MortarRecipe recipe) {
		this.setItem(ItemStack.EMPTY);
		this.markDirty();
		ItemStackHelper.spawnItem(this.world, recipe.getRecipeOutput(), this.pos.getX() + 0.5f, this.pos.getY() + 0.8f, this.pos.getZ() + 0.5f);
	}

	private MortarRecipe getRecipe(ItemStack stack) {
		if(stack == null) return null;
		
		Set<IRecipe<?>> recipes = CraftingHelper.findRecipesByType(ModRecipeTypes.MORTAR_TYPE);
		for(IRecipe<?> r : recipes) {
			MortarRecipe recipe = (MortarRecipe) r;
			if(recipe.matches(new RecipeWrapper(this.inventory), this.world)) {
				return recipe;
			}
		}
		return null;
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("item", this.getItem().write(new CompoundNBT()));
		return super.write(compound);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		CompoundNBT item = compound.getCompound("item");

		if(item != null && !item.isEmpty()) {
			this.setItem(ItemStack.read(item));
		}
	}
}
