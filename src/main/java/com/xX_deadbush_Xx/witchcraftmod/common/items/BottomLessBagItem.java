package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.common.container.BottomLessBagContainer;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BottomLessBagItem extends Item {

    private final Inventory inventory;

    public BottomLessBagItem(Properties properties) {
        super(properties);
        this.inventory = new Inventory(this.getDefaultInstance());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, inventory, playerIn.getPosition());
            return ActionResult.resultSuccess(playerIn.getHeldItemMainhand());
        }
        return ActionResult.resultPass(playerIn.getHeldItemMainhand());
    }


    public static class Inventory implements INamedContainerProvider, IInventory {

        private final NonNullList<ItemStack> content;

        public Inventory(ItemStack itemStack) {
            this.content = NonNullList.withSize(1, ItemStack.EMPTY);
            CompoundNBT nbt = itemStack.getTag();
            if (nbt == null) {
                nbt = new CompoundNBT();
                ItemStackHelper.saveAllItems(nbt, this.content);
                itemStack.setTag(nbt);
            } else {
                ItemStackHelper.loadAllItems(nbt, this.content);
            }

        }

        @Override
        public int getSizeInventory() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return this.content.isEmpty();
        }

        @Override
        public ItemStack getStackInSlot(int index) {
            return this.content.get(index);
        }

        @Override
        public ItemStack decrStackSize(int index, int count) {
            return ItemStackHelper.getAndSplit(content, index, count);
        }

        @Override
        public ItemStack removeStackFromSlot(int index) {
            return ItemStackHelper.getAndRemove(content, index);
        }

        @Override
        public void setInventorySlotContents(int index, ItemStack stack) {
            this.content.set(index, stack);
        }

        @Override
        public void markDirty() {

        }

        @Override
        public boolean isUsableByPlayer(PlayerEntity player) {
            return player.getHeldItemMainhand().getItem().equals(ModItems.BOTTOMLESS_BAG.get());
        }

        @Override
        public void clear() {
            this.content.clear();

        }

        @Override
        public ITextComponent getDisplayName() {
            return new TranslationTextComponent("Bottomless Bag");
        }

        @Nullable
        @Override
        public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
            return new BottomLessBagContainer(p_createMenu_1_, p_createMenu_2_);
        }
    }

}
