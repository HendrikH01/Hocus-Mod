package com.xX_deadbush_Xx.hocus.common.tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.xX_deadbush_Xx.hocus.api.tile.ContainerTile;
import com.xX_deadbush_Xx.hocus.common.container.AutoToolTableContainer;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;
import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class AutoToolTableTile extends ContainerTile implements ITickableTileEntity {

	public static final int IO_NUM_ROWS = 5;
	public static final int IO_NUM_COLS = 2;
	public static final int GRID_NUM_ROWS = 3;
	public static final int GRID_NUM_COLS = 3;
	public static final int IO_SIZE = IO_NUM_ROWS * IO_NUM_COLS;
	public static final int GRID_SIZE = GRID_NUM_ROWS * GRID_NUM_COLS;
	public static final int PREVIEW_SLOT = IO_SIZE * 2 + GRID_SIZE;
	public static final int OUTPUT_SLOT_START = IO_SIZE + GRID_SIZE;
	public static final int OUTPUT_SLOT_STOP = OUTPUT_SLOT_START + IO_SIZE - 1;
	public static final int GRID_SLOT_START = IO_SIZE;
	public static final int GRID_SLOT_STOP = GRID_SLOT_START + GRID_SIZE - 1;

	private ArrayList<ItemStack> lastRecipeGrid = null;

	public static final int TIMER_FULL = 40;
	public List<AutoToolTableContainer> containerList = Lists.newArrayList();

	private boolean printRecipe = false;

	public NonNullList<ItemStack> items = NonNullList.withSize(14, ItemStack.EMPTY);

	public AutoToolTableTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn, 13);
	}

	public AutoToolTableTile() {
		this(ModTileEntities.AUTO_TOOLTABLE.get());
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("Auto Tool Table");
	}

	@Override
	public Container createMenu(int id, PlayerInventory player, PlayerEntity playerEntity) {
		AutoToolTableContainer container = new AutoToolTableContainer(id, player, this);
		containerList.add(container);
		return container;
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		super.onDataPacket(net, pkt);
	}

	private IItemHandler getNearbyChestItemHandler() {
		IItemHandler itemHandler = null;
		BlockPos pos = this.getPos();
		World world = this.getWorld();
		world.getTileEntity(pos);
		if (world.getTileEntity(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())) instanceof IContainerProvider) {
			itemHandler = world.getTileEntity(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
		}
		if (world.getTileEntity(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())) instanceof IContainerProvider) {
			itemHandler = world.getTileEntity(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
		}
		if (world.getTileEntity(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ())) instanceof IContainerProvider) {
			itemHandler = world.getTileEntity(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ())).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
		}
		if (world.getTileEntity(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ())) instanceof IContainerProvider) {
			itemHandler = world.getTileEntity(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ())).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
		}
		if (world.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1)) instanceof IContainerProvider) {
			itemHandler = world.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
		}
		if (world.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1)) instanceof IContainerProvider) {
			itemHandler = world.getTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
		}
		return itemHandler;
	}

	@Override
	public void tick() {
		if (world == null || world.getServer() == null)
			return;
		if (world.isRemote)
			return;

		// AUTO CRAFTING
		
		if (this.getItemHandler().getStackInSlot(12).getItem().equals(ModItems.RECIPE.get())) {

			IItemHandler inputHandler = this.getItemHandler();
			IItemHandler outputHandler = this.getNearbyChestItemHandler();
			ArrayList<ItemStack> itemStacksInGrid = getItemsInCraftingGrid();
			if (lastRecipeGrid == null)
				lastRecipeGrid = itemStacksInGrid;
			if (itemStacksInGrid == null || countNonEmptyStacks(itemStacksInGrid) == 0) { // Nothing in Crafting grid, so don't do anything
				return;
			}

			// Searching for new recipes
			IRecipe<?> recipe = tryRecipes(itemStacksInGrid);

			// Checking if the recipe is valid
			if (recipe != null && outputHandler != null) {

				// Container doesn't have free space
				if (!hasFreeSpace(outputHandler, recipe.getRecipeOutput())) {
					return;
				} else if(this.getItemHandler().getStackInSlot(12).getOrCreateTag().getString("output").equals(recipe.getRecipeOutput().getDisplayName().getString())) {

					// Container has free space
					craft(inputHandler, recipe);
					ItemStack output = recipe.getRecipeOutput().copy();
					for (int slotId = 0; slotId < outputHandler.getSlots(); slotId++) {
						output = outputHandler.insertItem(slotId, output, false);
						if (output == ItemStack.EMPTY || output.getCount() == 0)
							break;
					}

					if (!containerList.isEmpty()) {
						for (AutoToolTableContainer container : containerList) {
							container.updateRecipe();
						}
					}
				}
			}
		}

		// PRINT RECIPE

		if (this.printRecipe && this.getItemHandler().getStackInSlot(12).getItem().equals(Items.OAK_PLANKS)) {
			this.getItemHandler().extractItem(12, 1, false);
			ItemStack stack = new ItemStack(ModItems.RECIPE.get());
			CompoundNBT nbt = stack.getOrCreateTag();
			nbt.putString("output", tryRecipes(getItemsInCraftingGrid()).getRecipeOutput().getDisplayName().getString());
			stack.write(nbt);
			this.getItemHandler().insertItem(12, stack, false);

		}

	}

	@Nullable
	private ArrayList<ItemStack> getItemsInCraftingGrid() {
		ArrayList<ItemStack> itemStacks = new ArrayList<>();
		IItemHandler gridHandler = this.getItemHandler();
		if (gridHandler == null)
			return null;
		for (int i = 0; i < GRID_SIZE; i++) {
			itemStacks.add(gridHandler.getStackInSlot(i));
		}
		return itemStacks;
	}

	private boolean hasFreeSpace(IItemHandler inv, ItemStack output) {
		for (int slotId = 0; slotId < IO_SIZE; slotId++) {
			if (inv.getStackInSlot(slotId) == ItemStack.EMPTY || (inv.getStackInSlot(slotId).isItemEqual(output) && inv.getStackInSlot(slotId).getCount() + output.getCount() <= output.getMaxStackSize()))
				return true;
			if (output == ItemStack.EMPTY || output.getCount() == 0)
				return true;
		}
		return false;
	}

	private void craft(IItemHandler input, IRecipe<?> recipe) {
		for(int i = 0; i < 9; i++) {
			if(input.getStackInSlot(i) != ItemStack.EMPTY) {
				if(input.getStackInSlot(i).isDamageable()) {
					input.getStackInSlot(i).setDamage(input.getStackInSlot(i).getDamage() - 1);
				} else {
					input.extractItem(i, 1, false);
				}
			}
		}
	}

	@Nullable
	private IRecipe<?> tryRecipes(ArrayList<ItemStack> itemStacksInGrid) {
		if (world == null || world.getServer() == null)
			return null;
		Collection<IRecipe<?>> recipes = world.getServer().getRecipeManager().getRecipes();
		for (IRecipe<?> recipe : recipes) {
			if (recipe instanceof ShapelessRecipe) {
				ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
				if (tryMatchShapelessRecipe(itemStacksInGrid, shapelessRecipe))
					return shapelessRecipe;
			} else if (recipe instanceof ShapedRecipe) {
				ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
				if (!doSizesMatch(shapedRecipe, itemStacksInGrid)) {
					continue;
				}
				if (tryMatchShapedRecipe(itemStacksInGrid, shapedRecipe))
					return shapedRecipe;
			}
		}
		return null;
	}

	private boolean tryMatchShapedRecipe(ArrayList<ItemStack> itemStacks, ShapedRecipe recipe) {
		for (int j = 0; j <= 3 - recipe.getHeight(); ++j) {
			for (int i = 0; i <= 3 - recipe.getWidth(); ++i) {
				if (this.tryMatchShapedRecipeRegion(itemStacks, recipe, i, j)) { return true; }
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean tryMatchShapelessRecipe(ArrayList<ItemStack> itemStacks, ShapelessRecipe recipe) {
		ArrayList<ItemStack> itemStacksCopy = (ArrayList<ItemStack>) itemStacks.clone();
		for (Ingredient ingredient : recipe.getIngredients()) {
			Iterator<ItemStack> iter = itemStacksCopy.iterator();
			boolean matched = false;
			while (iter.hasNext()) {
				ItemStack itemStack = iter.next();
				if (ingredient.test(itemStack)) {
					iter.remove();
					matched = true;
					break;
				}
			}
			if (!matched)
				return false;
		}
		return countNonEmptyStacks(itemStacksCopy) == 0;
	}

	private boolean tryMatchShapedRecipeRegion(ArrayList<ItemStack> itemStacks, ShapedRecipe recipe, int offsetX, int offsetY) {
		int recipeSize = recipe.getWidth() * recipe.getHeight();
		int itemStacksSize = itemStacks.size();
		Ingredient ingredient;
		if (itemStacksSize < recipeSize || offsetX + recipe.getWidth() > 3 || offsetY + recipe.getHeight() > 3)
			return false;
		int indexInRecipe = 0;
		for (int recipeYPos = 0; recipeYPos < recipe.getHeight(); recipeYPos++) {
			for (int recipeXPos = 0; recipeXPos < recipe.getWidth(); recipeXPos++) {
				int indexInArray = offsetX * 3 + offsetY + recipeXPos * 3 + recipeYPos;
				ItemStack itemStack = itemStacks.get(indexInArray);
				ingredient = recipe.getIngredients().get(indexInRecipe);
				if (!ingredient.test(itemStack)) { return false; }
				indexInRecipe++;
			}
		}
		return true;
	}

	private int countNonEmptyStacks(ArrayList<ItemStack> itemStacks) {
		int count = 0;
		for (ItemStack itemStack : itemStacks) {
			if (itemStack != ItemStack.EMPTY)
				count++;
		}
		return count;
	}

	private boolean doSizesMatch(ShapedRecipe recipe, ArrayList<ItemStack> itemStacks) {
		int ingredientCount = 0;
		int itemStackCount = 0;
		for (Ingredient ingredient : recipe.getIngredients()) {
			if (!ingredient.test(ItemStack.EMPTY))
				ingredientCount++;
		}
		for (ItemStack itemStack : itemStacks) {
			if (itemStack != ItemStack.EMPTY)
				itemStackCount++;
		}
		return ingredientCount == itemStackCount;
	}

	public void setPrintRecipe(boolean b) {
		this.printRecipe = b;
	}

}
