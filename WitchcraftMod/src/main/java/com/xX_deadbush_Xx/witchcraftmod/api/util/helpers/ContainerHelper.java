package com.xX_deadbush_Xx.witchcraftmod.api.util.helpers;

import java.util.Objects;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class ContainerHelper {
	public static <T extends TileEntity> T getTileEntity(Class<T> clazz, final PlayerInventory playerInventory, final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (clazz.isInstance(tileAtPos)) {
			return (T) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}
}
