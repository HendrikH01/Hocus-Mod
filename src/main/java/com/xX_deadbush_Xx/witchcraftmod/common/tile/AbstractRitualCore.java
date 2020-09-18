package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.AbstractRitual.Phase;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.RitualEffectHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.tile.BasicItemHolderTile;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ItemStackHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.RitualStone;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class AbstractRitualCore extends BasicItemHolderTile implements ITickableTileEntity {
	public IRecipe<RecipeWrapper> lastRecipe;
	public LazyOptional<IRitual> currentritual = LazyOptional.empty();
	public float glowpower = 0;
	private float diffglowpower = 1;
	
	public AbstractRitualCore(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn, 1);
	}
	
	@Override 
	public void tick() {
		this.currentritual.ifPresent(ritual -> {
			if(ritual.getPhase() != Phase.POWERDOWN) {
				if(ritual.conditionsMet()) {
					this.updateGlow(ritual.getGlowType(), ritual);
					if(!this.world.isRemote) ritual.tick();
				} else {
					ritual.stopRitual(false);
				}
			} else if (this.glowpower > 0) {
				updateGlow(this.getBlockState().get(RitualStone.GLOW_TYPE), ritual);
				if (!this.world.isRemote) ritual.tick();

			}
		});
	}

	private void updateGlow(GlowType glowtype, IRitual ritual) {
		this.glowpower += this.diffglowpower;
		this.diffglowpower = this.glowpower == 15 || ritual.getPhase() == Phase.POWERDOWN ?
				-0.5F : this.glowpower <= 7 ?
						0.5F : this.diffglowpower; //nested tenaries yay
		this.world.setBlockState(this.pos, this.getBlockState().with(RitualStone.GLOW_TYPE, glowtype).with(RitualStone.POWER, (int)this.glowpower));
		RitualHelper.colorChalk(glowtype, (int)glowpower, ritual.getChalkPositions(), this.world, this.pos);

	}

	public void swapItems(World worldIn, PlayerEntity player) {
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
