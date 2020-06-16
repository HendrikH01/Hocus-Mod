package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.api.tile.BasicItemHolderTile;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.CraftingHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ItemStackHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.DryingRackRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
		ItemStack items = player.getHeldItemMainhand().copy();
		items.setCount(1);
		this.ticksUntilDry = 1000;
		
		if(!getItem().equals(items)) {
			Item heldItem = player.getHeldItemMainhand().getItem();
			if(!this.getItem().getItem().equals(heldItem)) {
				player.getHeldItemMainhand().shrink(1);
				if(this.hasItem()) {
					if(heldItem.equals(Items.AIR)) player.setHeldItem(Hand.MAIN_HAND, this.getItem());
					else if(!player.inventory.addItemStackToInventory(this.getItem())) { 
						ItemStackHelper.spawnItem(worldIn, this.getItem(), this.pos);
					}
				}
				setItem(items);
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
		
		List<IRecipe<?>> recipes = CraftingHelper.findRecipesByType(ModRecipeTypes.DRYING_RACK_TYPE);
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
