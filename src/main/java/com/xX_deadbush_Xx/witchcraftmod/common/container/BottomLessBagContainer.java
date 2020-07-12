package com.xX_deadbush_Xx.witchcraftmod.common.container;

import com.xX_deadbush_Xx.witchcraftmod.api.inventory.InfiniteSlot;
import com.xX_deadbush_Xx.witchcraftmod.common.items.BottomLessBagItem;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;

public class BottomLessBagContainer extends Container {

    private Inventory bagInventory;
    private int selectedSlot;
    private int itemCountAmount;
    private ItemStack stored;

    public BottomLessBagContainer(final int windowId, final PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(windowId, playerInventory, new BottomLessBagItem.BottomLessBagInventory(playerInventory.getCurrentItem(), 1), packetBuffer.readVarInt());
    }

    public BottomLessBagContainer(int windowId, PlayerInventory playerInventory, Inventory inventory, int selectedSlot) {
        super(ModContainers.BOTTOM_LESS_BAG.get(), windowId);
        this.bagInventory = inventory;
        this.selectedSlot = selectedSlot;
        this.stored = ItemStack.EMPTY;

        this.addSlot(new InfiniteSlot((IItemHandler) bagInventory, 0, 81, 35));

        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
    }


    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (bagInventory instanceof BottomLessBagItem.BottomLessBagInventory) {
            ((BottomLessBagItem.BottomLessBagInventory) bagInventory).writeItemStack();
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        final Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            stored = itemstack1.copy();
            if (index < 1) {
                System.out.println("BLBC_DEBUG_2");

                if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                System.out.println("BLBC_DEBUG_3");
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        System.out.println("BLBC_DEBUG_4");
        return itemstack;
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
        super.putStackInSlot(slotID, stack);
    }

    /*@Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, PlayerEntity player) {
        Slot tmpSlot;
        if (slotId >= 0 && slotId < inventorySlots.size()) {
            tmpSlot = inventorySlots.get(slotId);
        } else {
            tmpSlot = null;
        }
        if (tmpSlot != null) {
            if (tmpSlot.inventory == player.inventory && tmpSlot.getSlotIndex() == selectedSlot) {
                return tmpSlot.getStack();
            }
        }
        if (clickType == ClickType.SWAP) {
            final ItemStack stack = player.inventory.getStackInSlot(dragType);
            final ItemStack currentItem = PlayerInventory.isHotbar(selectedSlot) ? player.inventory.mainInventory.get(selectedSlot) : selectedSlot == -1 ? player.inventory.offHandInventory.get(0) : ItemStack.EMPTY;

            if (!currentItem.isEmpty() && stack == currentItem) {
                return ItemStack.EMPTY;
            }
        }
        ItemStack is = super.slotClick(slotId, dragType, clickType, player);
        return is;
    }*/


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public int getItemCountAmount() {
        return itemCountAmount;
    }

    public ItemStack getStored() {
        return stored;
    }

    public Inventory getBagInventory() {
        return bagInventory;
    }
}
