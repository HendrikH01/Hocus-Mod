package com.xX_deadbush_Xx.witchcraftmod.common.items;

import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class WitchHatItem extends ArmorItem {

	public WitchHatItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);
	}
	
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent("+ Magic resistance"));
	}
	
	@Override
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack,
			EquipmentSlotType armorSlot, A _default) {
		// TODO Auto-generated method stub
		return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return WitchcraftMod.MOD_ID + ":textures/models/armor/witch_hat.png";
	}
 }
