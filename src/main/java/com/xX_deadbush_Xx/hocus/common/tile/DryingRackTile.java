package com.xX_deadbush_Xx.hocus.common.tile;

import java.util.Set;

import com.xX_deadbush_Xx.hocus.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.hocus.api.tile.BasicItemHolderTile;
import com.xX_deadbush_Xx.hocus.api.util.CraftingHelper;
import com.xX_deadbush_Xx.hocus.api.util.ItemStackHelper;
import com.xX_deadbush_Xx.hocus.common.recipes.DryingRackRecipe;
import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class DryingRackTile extends BasicItemHolderTile implements ITickableTileEntity {
	private int ticksUntilDry = 1000;
	
	public DryingRackTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn, 1);
	}
	
	public DryingRackTile() {
		this(ModTileEntities.DRYING_RACK.get());
	}
	
	public void swapItems(World worldIn, BlockPos pos, PlayerEntity player) {
		ItemStack playerItems = player.getHeldItemMainhand().copy();
		ItemStack returnedItem = ItemStack.EMPTY;
		boolean empty = false;
		
		if (this.hasItem() || getItem().equals(playerItems)) {
			returnedItem = this.getItem().copy();
			empty = true;
		}
		
		if ((!getItem().equals(playerItems) && !empty || !this.hasItem()) && !playerItems.isEmpty()) {
			player.getHeldItemMainhand().shrink(1);
			playerItems.setCount(1);
			setItem(playerItems);
			this.ticksUntilDry = 1000;
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
	
	@Override
	public void tick() {
		this.ticksUntilDry--;
		if (this.ticksUntilDry <= 0) {
			this.craft(this.getRecipe(this.getItem()));
			this.ticksUntilDry = 1000;
		}
	}
	
	private void craft(DryingRackRecipe recipe) {
		if(recipe != null) {
			this.setItem(recipe.getRecipeOutput());
			this.markDirty();
		}
	}

	private DryingRackRecipe getRecipe(ItemStack stack) {
		if(stack == null) return null;
		
		Set<IRecipe<?>> recipes = CraftingHelper.findRecipesByType(ModRecipeTypes.DRYING_RACK_TYPE);
		for(IRecipe<?> r : recipes) {
			DryingRackRecipe recipe = (DryingRackRecipe) r;
			if(recipe.matches(new RecipeWrapper(this.inventory), this.world)) {
				return recipe;
			}
		}
		return null;
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("item", this.getItem().write(new CompoundNBT()));
		compound.putInt("tickUntilDry", this.ticksUntilDry);

		return super.write(compound);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		CompoundNBT item = compound.getCompound("item");
		if(item != null && !item.isEmpty()) {
			this.setItem(ItemStack.read(item));
		}
		this.ticksUntilDry = compound.getInt("tickUntilDry");
	}
}
