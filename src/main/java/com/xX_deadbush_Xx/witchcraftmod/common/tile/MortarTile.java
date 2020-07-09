package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.api.tile.BasicItemHolderTile;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.CraftingHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ItemStackHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.event.LoadComplete;
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
	public int v1 = 0;
	public int v2 = 0;
	public int v3 = 0;
	private Set<Item> possiblerecipeinputs;

	public MortarTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn, 1);
		this.possiblerecipeinputs = LoadComplete.mortarrecipeinputs.stream().map(i -> i.getItem()).collect(Collectors.toSet());
	}
	
	public MortarTile() {
		this(ModTileEntities.MORTAR_TILE.get());
	}
	
	public void swapItems(World worldIn, BlockPos pos, PlayerEntity player) {
		ItemStack heldItem = player.getHeldItemMainhand().copy();
		heldItem.setCount(1);

		if(!ItemStack.areItemsEqual(heldItem, this.getItem())) {
			if(this.hasItem()) {
				if(heldItem.getItem().equals(Items.AIR)) player.setHeldItem(Hand.MAIN_HAND, this.getItem());
				else if(!player.inventory.addItemStackToInventory(this.getItem())) { 
					ItemStackHelper.spawnItem(worldIn, this.getItem(), this.pos);
				}
			}
			if(possiblerecipeinputs.contains(heldItem.getItem())) {
				player.getHeldItemMainhand().shrink(1);
				setItem(heldItem);
				this.markDirty();
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
		
		List<IRecipe<?>> recipes = CraftingHelper.findRecipesByType(ModRecipeTypes.MORTAR_TYPE);
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

	public int get1() {
		return v1;
	}
	public int get2() {
		return v2;
	}	public int get3() {
		return v3;
	}
}
