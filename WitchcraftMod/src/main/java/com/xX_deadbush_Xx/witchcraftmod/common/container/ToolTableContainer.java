package com.xX_deadbush_Xx.witchcraftmod.common.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Nullable;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.IToolTableRecipe;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.api.inventory.CraftingSlot;
import com.xX_deadbush_Xx.witchcraftmod.api.inventory.ToolSlot;
import com.xX_deadbush_Xx.witchcraftmod.api.inventory.ToolTableCraftingInventory;
import com.xX_deadbush_Xx.witchcraftmod.api.inventory.ToolTableResultSlot;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ContainerHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.InventoryHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModContainers;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.ToolTableTile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.hooks.BasicEventHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class ToolTableContainer extends Container {
	private final IItemHandlerModifiable craftResult = new ItemStackHandler(1);
	private final IItemHandlerModifiable inv;
	public final ToolTableTile tile;
	private final IWorldPosCallable canInteractWithCallable;
	private PlayerEntity player;
	private IRecipe<? extends IInventory> lastRecipe;
	
	private static final int[] TOOLSLOTS = {9, 10, 11};
	private static final int[] CRAFTINGSLOTS = {0, 1, 2, 3, 4, 5, 6, 7, 8};
	
	public ToolTableContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, ContainerHelper.getTileEntity(ToolTableTile.class, playerInventory, data));
	}

	public ToolTableContainer(int id, PlayerInventory playerinv, ToolTableTile tile) {
		super(ModContainers.TOOL_TABLE.get(), id);
		this.inv = tile.getItemHandler();
		this.tile = tile;
		this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());
		this.player = playerinv.player;
		
		updateRecipe();
		
		this.addSlot(new ToolTableResultSlot(craftResult, this.player, this, 0, 124, 35));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				this.addSlot(new CraftingSlot(this.inv, this, j + i * 3, 30 + j * 18, 17 + i * 18));
			}
		}
		
		this.addSlot(new ToolSlot(this.inv, this, TOOLSLOTS[0], 23, 84));
		this.addSlot(new ToolSlot(this.inv, this, TOOLSLOTS[1], 48, 87));
		this.addSlot(new ToolSlot(this.inv, this, TOOLSLOTS[2], 73, 84));

		for (int k = 0; k < 3; ++k) {
			for (int i1 = 0; i1 < 9; ++i1) {
				this.addSlot(new Slot(playerinv, i1 + k * 9 + 9, 8 + i1 * 18, 114 + k * 18));
			}
		}

		for (int l = 0; l < 9; ++l) {
			this.addSlot(new Slot(playerinv, l, 8 + l * 18, 172));
		}
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
		return super.slotClick(slotId, dragType, clickTypeIn == ClickType.PICKUP_ALL ? ClickType.PICKUP : clickTypeIn, player);
	}

	protected void findRecipe(int id, World worldIn, PlayerEntity playerIn, IItemHandlerModifiable inventoryIn, IItemHandlerModifiable inventoryResult) {
		if (!worldIn.isRemote) {			
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) playerIn;
			ItemStack itemstack = ItemStack.EMPTY;
			Optional<IToolTableRecipe> toolrecipe = worldIn.getServer().getRecipeManager().getRecipe(ModRecipeTypes.TOOL_TABLE, new RecipeWrapper(inventoryIn), worldIn);
			if (toolrecipe.isPresent()) {
				IRecipe<RecipeWrapper> recipe = toolrecipe.get();
				this.lastRecipe = recipe;
				itemstack = recipe.getCraftingResult(new RecipeWrapper(inventoryIn));
			} else {
				CraftingInventory craftingInv = getCraftingInv(inventoryIn);
				Optional<ICraftingRecipe> vanillarecipe = worldIn.getServer().getRecipeManager().getRecipe(IRecipeType.CRAFTING, craftingInv, worldIn);
				if (vanillarecipe.isPresent()) {
					IRecipe<CraftingInventory> recipe = vanillarecipe.get();
					this.lastRecipe = recipe;
					itemstack = recipe.getCraftingResult(craftingInv);
				}
			}

			inventoryResult.setStackInSlot(0, itemstack);
			
			serverplayerentity.connection.sendPacket(new SSetSlotPacket(id, 0, itemstack));
		}
	}

	public void onCraftMatrixChanged(@Nullable IInventory inventoryIn) {
		updateRecipe();
	}
	
	private void updateRecipe() {
		this.canInteractWithCallable.consume((world, pos) -> {
			findRecipe(this.windowId, world, this.player, this.inv, this.craftResult);
		});
	}

	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
	}

	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) { //vanilla copy pasta
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index == 0) {
				this.canInteractWithCallable.consume((world, pos) -> {
					itemstack1.getItem().onCreated(itemstack1, world, playerIn);
				});
				if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index >= 10 && index < 46) {
				if (!this.mergeItemStack(itemstack1, 1, 10, false)) {
					if (index < 37) {
						if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
			if (index == 0) {
				playerIn.dropItem(itemstack2, false);
			}
		}
		return itemstack;
	}

	public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
		return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
	}

	public int getOutputSlot() {
		return 0;
	}

	@OnlyIn(Dist.CLIENT)
	public int getSize() {
		return 13;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(canInteractWithCallable, playerIn, ModBlocks.TOOL_TABLE.get());
	}

	@SuppressWarnings("unchecked")
	public void craft() {
		if(this.lastRecipe == null || this.player.world.isRemote) return;
		ServerPlayerEntity serverplayer = (ServerPlayerEntity)this.player;
		List<Integer> slotstoupdate = new ArrayList<>();

		//REMOVE AND HANDLE REMAINING ITEMS
		NonNullList<ItemStack> itemsremaining;
		if(this.lastRecipe.getType() == ModRecipeTypes.TOOL_TABLE) { //damage tools
			itemsremaining = ((IRecipe<IInventory>)this.lastRecipe).getRemainingItems(new RecipeWrapper(this.inv));

		}
		else if(this.lastRecipe.getType() == IRecipeType.CRAFTING) {
			itemsremaining = ((IRecipe<IInventory>)this.lastRecipe).getRemainingItems(getCraftingInv(this.inv));
			BasicEventHooks.firePlayerCraftingEvent(serverplayer, this.lastRecipe.getRecipeOutput(), getCraftingInv(this.inv));
		}
		else return;
		
		for (int i = 0; i < itemsremaining.size(); ++i) {
			ItemStack itemstack = this.inv.getStackInSlot(i);
			ItemStack remaining = itemsremaining.get(i);
			if (!itemstack.isEmpty()) {
				this.inv.extractItem(i, 1, false);
				slotstoupdate.add(i);
				itemstack = this.inv.getStackInSlot(i);
			}

			if (!remaining.isEmpty()) {
				if (itemstack.isEmpty()) {
					this.inv.setStackInSlot(i, remaining);
					slotstoupdate.add(i);
				} else if (ItemStack.areItemsEqual(itemstack, remaining) && ItemStack.areItemStackTagsEqual(itemstack, remaining)) {
					remaining.grow(itemstack.getCount());
					this.inv.setStackInSlot(i, remaining);
					slotstoupdate.add(i);
				} else if (!player.inventory.addItemStackToInventory(remaining)) {
					player.dropItem(remaining, false);
				}
			}
		}
		updateRecipe();

		slotstoupdate.forEach(i -> serverplayer.connection.sendPacket(new SSetSlotPacket(this.windowId, i + 1, this.inv.getStackInSlot(i))));
	}
	
	private ToolTableCraftingInventory getCraftingInv(IItemHandler inv) {
		return new ToolTableCraftingInventory(this, 3, 3, inv);
	}
}
