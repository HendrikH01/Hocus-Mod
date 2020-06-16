package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.RitualEffectHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.tile.BasicItemHolderTile;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ItemStackHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.RitualStone;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import com.xX_deadbush_Xx.witchcraftmod.common.register.RitualRegistry;

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
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class RitualStoneTile extends BasicItemHolderTile implements ITickableTileEntity {
	public IRecipe<RecipeWrapper> lastRecipe;
	public IRitual currentritual;
	public float glowpower = 0;
	private float diffglowpower = 1;
	
	public RitualStoneTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn, 1);
	}
	
	public RitualStoneTile() {
		super(ModTileEntities.RITUAL_STONE.get(), 1);
	}
	
	public void activateRitual(World worldIn, PlayerEntity player, IRitual ritual) {
		if(this.world.isRemote || ritual == null) return;
		System.out.println("Activating ritual: " + RitualRegistry.getName(ritual));
		this.currentritual = ritual;
		this.currentritual.activate();
	}
	
	@Override
	public void tick() {
		if(this.currentritual != null) {
			if(this.currentritual.conditionsMet()) {
				this.updateGlow(this.currentritual.getGlowType());
				if(!this.world.isRemote) {
					this.currentritual.tick();
				}
			} else {
				this.currentritual.stopRitual(false);
			}
		} else {
			if(this.glowpower > 0) {
				updateGlow(this.getBlockState().get(RitualStone.GLOW_TYPE));
			}
		}
	}
	
	private void updateGlow(GlowType glowtype) {
		this.glowpower += this.diffglowpower;
		this.diffglowpower = this.glowpower == 15 || this.currentritual == null || this.currentritual.isPoweringDown() ?
				-0.5F : this.glowpower <= 7 ?
						0.5F : this.diffglowpower; //nested tenaries yay
		this.world.setBlockState(this.pos, this.getBlockState().with(RitualStone.GLOW_TYPE, glowtype).with(RitualStone.POWER, (int)this.glowpower));
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
		return this.inventory.getStackInSlot(0);
	}
	
	public void setItem(ItemStack stack) {
		this.inventory.setStackInSlot(0, stack);
	}
	
	public boolean hasItem() {
		return !this.getItem().isEmpty() && !this.getItem().getItem().equals(Items.AIR);
	}
	
	public void useForCrafting() {
		this.inventory.decrStackSize(0, 1);
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
		private RitualEffectHandler currentAnimation;
		
		public void tick() {
			if(this.currentAnimation != null) this.currentAnimation.tick();
		}

		public void set(RitualEffectHandler ritualAnimation) {
			this.currentAnimation = ritualAnimation;
		}

		public void remove() {
			this.currentAnimation = null;
		}
	}
}
