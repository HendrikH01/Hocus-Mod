package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.common.container.BottomLessBagContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BottomLessBagItem extends Item {

    private final INamedContainerProvider iNamedContainerProvider = new INamedContainerProvider() {
        @Override
        public ITextComponent getDisplayName() {
            return new StringTextComponent("Bottomless Backpack");
        }

        @Nullable
        @Override
        public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
            return new BottomLessBagContainer(id, playerInventory, playerEntity.getHeldItemMainhand());
        }
    };

    public BottomLessBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn.isRemote) {
            playerIn.openContainer(iNamedContainerProvider);
            return ActionResult.resultSuccess(playerIn.getHeldItemMainhand());
        }
        return ActionResult.resultPass(playerIn.getHeldItemMainhand());
    }
}
