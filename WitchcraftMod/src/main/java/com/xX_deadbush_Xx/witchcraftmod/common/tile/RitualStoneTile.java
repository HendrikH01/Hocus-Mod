package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.RitualAnimation;
import com.xX_deadbush_Xx.witchcraftmod.api.tile.BasicItemHolderTile;
import com.xX_deadbush_Xx.witchcraftmod.api.util.ItemStackHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import com.xX_deadbush_Xx.witchcraftmod.common.rituals.SmallFusionRitual;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class RitualStoneTile extends BasicItemHolderTile implements ITickableTileEntity {
	public RitualAnimationHandler animationHandler = new RitualAnimationHandler();
	public IRecipe<?> lastRecipe;
	
	public RitualStoneTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn, 1);
	}
	
	public RitualStoneTile() {
		super(ModTileEntities.RITUAL_STONE.get(), 1);
	}
	
	public void activateRitual(World worldIn, PlayerEntity player) {
		if(this.lastRecipe != null) System.out.println("ssss" + this.lastRecipe.getRecipeOutput());
		SmallFusionRitual.INSTANCE.activate(this.pos, worldIn, player);
	}
	
	@Override
	public void tick() {
		animationHandler.tick();
	}
	
	public void swapItems(World worldIn, PlayerEntity player) {
		ItemStack playerItems = player.getHeldItemMainhand().copy();
		playerItems.setCount(1);
		Item heldItem = playerItems.getItem();

		if(!getItem().getItem().equals(heldItem)) {
			player.getHeldItemMainhand().shrink(1);
			if(this.hasItem()) {
				if(heldItem.equals(Items.AIR)) player.setHeldItem(Hand.MAIN_HAND, this.getItem());
				else if(!player.inventory.addItemStackToInventory(this.getItem())) { 
					ItemStackHelper.spawnItem(worldIn, this.getItem(), this.pos);
				}
			}
			setItem(playerItems);
		}
		this.markDirty();
	}
	
	public ItemStack getItem() {
		return getStackInSlot(0);
	}
	
	public void setItem(ItemStack stack) {
		setInventorySlotContents(0, stack);
	}
	
	public boolean hasItem() {
		return !this.getItem().isEmpty() && !this.getItem().getItem().equals(Items.AIR);
	}
	
	public void useForCrafting() {
		this.decrStackSize(0, 1);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("item", getItem().write(new CompoundNBT()));
		return super.write(compound);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		CompoundNBT item = compound.getCompound("item");

		if(item != null && !item.isEmpty()) {
			setItem(ItemStack.read(item));
		}
	}
	
	public class RitualAnimationHandler {
		private RitualAnimation currentAnimation;
		
		public void tick() {
			if(this.currentAnimation != null) this.currentAnimation.tick();
		}

		public void set(RitualAnimation ritualAnimation) {
			this.currentAnimation = ritualAnimation;
		}

		public void remove() {
			this.currentAnimation = null;
		}
	}
}
