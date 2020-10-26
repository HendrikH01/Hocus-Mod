package com.xX_deadbush_Xx.hocus.common.tile;

import com.xX_deadbush_Xx.hocus.api.tile.ContainerTile;
import com.xX_deadbush_Xx.hocus.common.container.ToolTableContainer;
import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ToolTableTile extends ContainerTile {

	public NonNullList<ItemStack> items = NonNullList.withSize(13, ItemStack.EMPTY);

	public ToolTableTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn, 12);
	}
	
	public ToolTableTile() {
		this(ModTileEntities.TOOL_TABLE.get());
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("Tool Table");
	}

	@Override
	public Container createMenu(int id, PlayerInventory player, PlayerEntity playerEntity) {
		return new ToolTableContainer(id, player, this);
	}
}
