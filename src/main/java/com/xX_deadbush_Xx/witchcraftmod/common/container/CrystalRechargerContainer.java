package com.xX_deadbush_Xx.witchcraftmod.common.container;

import com.xX_deadbush_Xx.witchcraftmod.api.inventory.ExtraItemSlot;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ContainerHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.items.EnergyCrystal;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModContainers;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.CrystalRechargerTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandlerModifiable;

public class CrystalRechargerContainer extends Container {

    private IItemHandlerModifiable inventory;
    private final IWorldPosCallable canInteractWithCallable;
    private CrystalRechargerTile tile;
    private int burnTime;

    public CrystalRechargerContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, ContainerHelper.getTileEntity(CrystalRechargerTile.class, playerInventory, data), 0);
    }

    public CrystalRechargerContainer(int id, PlayerInventory playerinv, CrystalRechargerTile tile, int burnTime) {
        super(ModContainers.CRYSTAL_RECHARGER.get(), id);
        this.inventory = tile.getItemHandler();
        this.tile = tile;
        this.burnTime = burnTime;
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());

        this.addSlot(new ExtraItemSlot(this.inventory, 0, 60, 26, 64, (stack) -> ForgeHooks.getBurnTime(stack) > 0 || stack.getItem() == Items.BUCKET));
        this.addSlot(new ExtraItemSlot(this.inventory, 1, 99, 35, 1, (stack) -> stack.getItem() instanceof EnergyCrystal));

        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerinv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerinv, l, 8 + l * 18, 142));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled() {
        return burnTime * 13 / 200;
    }

    public CrystalRechargerTile getTile() {
        return tile;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 1) {
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 0) {
                if (ForgeHooks.getBurnTime(itemstack1) > 0) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        this.inventory.setStackInSlot(0, itemstack);
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 2 && index < 39 && EnergyCrystal.isStackACrystal(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
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
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, ModBlocks.CRYSTAL_RECHARGER.get());
    }
}
