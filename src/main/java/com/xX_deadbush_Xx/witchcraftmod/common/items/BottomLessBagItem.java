package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.common.container.BottomLessBagContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BottomLessBagItem extends Item implements INamedContainerProvider {


    public BottomLessBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, this, playerIn.getPosition());
            System.out.println("BOTTOM_LESS_BAG_ITEM");
            return ActionResult.resultSuccess(playerIn.getHeldItemMainhand());
        }
        return ActionResult.resultPass(playerIn.getHeldItemMainhand());
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new BottomLessBagContainer(p_createMenu_1_, p_createMenu_2_);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("Bottomless Bag");
    }
}
