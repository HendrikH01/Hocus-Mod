package com.xX_deadbush_Xx.hocus.common.tile;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.hocus.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.hocus.api.tile.BasicItemHolderTile;
import com.xX_deadbush_Xx.hocus.api.util.CraftingHelper;
import com.xX_deadbush_Xx.hocus.api.util.ItemStackHelper;
import com.xX_deadbush_Xx.hocus.common.blocks.blockstate.ModBlockStateProperties;
import com.xX_deadbush_Xx.hocus.common.recipes.MortarRecipe;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;
import com.xX_deadbush_Xx.hocus.common.tags.ModTags;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MortarTile extends BasicItemHolderTile {
	private static Set<Item> possibleRecipeInputs = new HashSet<>();
	private static Random rand = new Random();
	
	public MortarTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn, 3);
	}
	
	public MortarTile() {
		this(ModTileEntities.MORTAR_TILE.get());
	}
	
	public void swapItems(World worldIn, BlockPos pos, PlayerEntity player) {
				
		ItemStack playerItems = player.getHeldItemMainhand().copy();
		playerItems.setCount(1);
		ItemStack returnedItem = ItemStack.EMPTY;
		
		if(possibleRecipeInputs.isEmpty()) {
			possibleRecipeInputs = CraftingHelper.getAllRecipeInputs(ModRecipeTypes.MORTAR_TYPE, world).stream().map(i -> i.getItem()).collect(Collectors.toSet());
			possibleRecipeInputs.addAll(ModTags.PLANT_OIL_INGREDIENT.getAllElements());
		}
		
		if(playerItems.isEmpty()) {
			for(int i = 0; i < 4; i++) {
				if(i == 3 || this.inventory.getStackInSlot(i).isEmpty() && i > 0) {
					returnedItem = this.inventory.getStackInSlot(i-1).copy();
					this.inventory.setStackInSlot(i-1, ItemStack.EMPTY);
					break;

				}
			}
			markDirty();
		}
		else if (!this.setFirstFreeSlot(playerItems)) {
			ItemStack stack = this.inventory.getStackInSlot(2);
			
			if(stack.getItem() == playerItems.getItem()) {
				returnedItem = stack.copy();
				this.inventory.setStackInSlot(2, ItemStack.EMPTY);
			} else {
				returnedItem = stack.copy();
				this.inventory.setStackInSlot(2, playerItems);
				player.getHeldItemMainhand().shrink(1);
			}
		} else {
			player.getHeldItemMainhand().shrink(1);
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
		return !this.inventory.isEmpty();
	}
	
	public ItemStack getFirstItem() {
		for(int i = 0; i < 3; i++) {
			if(!this.inventory.getStackInSlot(i).isEmpty()) {
				return this.inventory.getStackInSlot(i);
			}
		}
		
		return ItemStack.EMPTY;
	}
	
	public boolean setFirstFreeSlot(ItemStack stack) {
		if(!possibleRecipeInputs.contains(stack.getItem())) return false;
		for(int i = 0; i < 3; i++) {
			if(this.inventory.getStackInSlot(i).isEmpty()) {
				this.inventory.setStackInSlot(i, stack);
				return true;
			}
		}
		
		return false;
	}
	
	private boolean tryMakeOil() {
		int seedcount = 0;
		for(int i = 0; i < 3; i++) {
			ItemStack stack = this.inventory.getStackInSlot(i);
			if(stack.getItem().isIn(ModTags.PLANT_OIL_INGREDIENT)) {
				seedcount++;
			} else if (!stack.isEmpty()) return false;
		}
		
		if(seedcount == 0) return false;
		
		if(world.isRemote()) {
			for(int i = 0; i < rand.nextInt(seedcount + 1) + 2; i++) world.addParticle(new ItemParticleData(ParticleTypes.ITEM, inventory.getStackInSlot(rand.nextInt(seedcount))), this.pos.getX() + 0.5, this.pos.getY() + 0.4, this.pos.getZ() + 0.5, 0, 0.1, 0);
			return true;
		}
	
		if(Math.random() > 0.7) {
        	int level = world.getBlockState(pos).get(ModBlockStateProperties.OIL_FILLLEVEL);
        	if(level == 3) return true;
			world.setBlockState(pos, ModBlocks.STONE_MORTAR.get().getDefaultState().with(ModBlockStateProperties.OIL_FILLLEVEL, Math.min(3, level + seedcount)), 1);
			
			this.inventory.clear();
			
			this.markDirty();
		}
		
		return true;
	}
	
	public void attemptCrafting() {
		if(tryMakeOil()) return;
		if(world.getBlockState(pos).get(ModBlockStateProperties.OIL_FILLLEVEL) > 0) return;
		MortarRecipe recipe = getRecipe();

		if(world.isRemote() && recipe != null) {
			for(int i = 0; i < rand.nextInt(3) + 3; i++) world.addParticle(new ItemParticleData(ParticleTypes.ITEM, recipe.getRecipeOutput()), this.pos.getX() + 0.5, this.pos.getY() + 0.4, this.pos.getZ() + 0.5, 0, 0.1, 0);
			return;
		}
		
		if(Math.random() < 0.7) {
			return;
		}
		
		if(recipe != null) craft(recipe);
	}
	
	private void craft(MortarRecipe recipe) {
		this.inventory.clear();
		this.markDirty();
		ItemStackHelper.spawnItem(this.world, recipe.getRecipeOutput(), this.pos.getX() + 0.5f, this.pos.getY() + 0.8f, this.pos.getZ() + 0.5f);
	}

	private MortarRecipe getRecipe() {
		world.getRecipeManager().getRecipe(ModRecipeTypes.MORTAR_TYPE, new RecipeWrapper(this.inventory), world);
		Set<IRecipe<?>> recipes = CraftingHelper.findRecipesByType(ModRecipeTypes.MORTAR_TYPE);
		for(IRecipe<?> r : recipes) {
			MortarRecipe recipe = (MortarRecipe) r;
			if(recipe.matches(new RecipeWrapper(this.inventory), this.world)) {
				return recipe;
			}
		}
		
		return null;
	}
}
