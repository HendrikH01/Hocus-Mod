package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.common.container.BottomLessBagContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BottomLessBagItem extends Item {


    public BottomLessBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        final int selectedSlot = handIn == Hand.MAIN_HAND ? playerIn.inventory.currentItem : -1;
        if (!worldIn.isRemote) {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, new SimpleNamedContainerProvider((id, playerInventory, openPlayer) ->
                    new BottomLessBagContainer(id, playerInventory, new BottomLessBagInventory(playerIn.getHeldItem(handIn), 1), selectedSlot),
                    new TranslationTextComponent("Bottomless  Bag")), packetBuffer -> packetBuffer.writeVarInt(selectedSlot));
            return ActionResult.resultSuccess(playerIn.getHeldItemMainhand());
        }
        return ActionResult.resultPass(playerIn.getHeldItemMainhand());
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        if (!stack.hasTag()) {
            return null;
        }
        final CompoundNBT compound = stack.getTag().copy();
        compound.remove("Items");
        if (compound.isEmpty()) {
            return null;
        }
        return compound;
    }

    public static class BottomLessBagInventory extends Inventory implements IItemHandlerModifiable {

        private final ItemStack stack;
        private int actuallyStack;
        private ItemStack currentStack;


        public BottomLessBagInventory(ItemStack stack, int count) {
            super(count);
            this.stack = stack;
            readItemStack();
        }

        @Override
        public int getInventoryStackLimit() {
            return 10000000;
        }

        public ItemStack getStack() {
            return stack;
        }

        @Override
        public ItemStack decrStackSize(int index, int count) {
            return super.decrStackSize(index, count);
        }

        public void readItemStack() {
            if (stack.getTag() != null) {
                readNBT(stack.getTag());
            }
        }

        public void writeItemStack() {
            if (isEmpty()) {
                stack.removeChildTag("Items");
            } else {
                writeNBT(stack.getOrCreateTag());
            }
        }

        private void readNBT(CompoundNBT compound) {
            final NonNullList<ItemStack> list = NonNullList.withSize(1, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(compound, list);
            setStackInSlot(0, list.get(0));
            this.actuallyStack = compound.getInt("stackAmount");
        }

        private void writeNBT(CompoundNBT compound) {
            final NonNullList<ItemStack> list = NonNullList.withSize(1, currentStack);
            ItemStackHelper.saveAllItems(compound, list, false);
            compound.putInt("stackAmount", actuallyStack);
        }

        @Override
        public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
            this.currentStack = stack;
            System.out.println("SET_STACK_IN_SLOT");
        }

        @Override
        public int getSlots() {
            return 1;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            this.actuallyStack += stack.getCount();
            System.out.println("INSERT_ITEM");
            return stack.copy();
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemStack stack = getStackInSlot(slot);
            if(actuallyStack < amount) {
                System.out.println("EXTRACT_ITEM_2");
                return ItemStack.EMPTY;
            }
            System.out.println("EXTRACT_ITEM: " + stack.toString());
            stack.setCount(amount);
            this.actuallyStack -= amount;
            return stack.copy();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1000000;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return true;
        }

        public int getActuallyStack() {
            return actuallyStack;
        }
    }
}
