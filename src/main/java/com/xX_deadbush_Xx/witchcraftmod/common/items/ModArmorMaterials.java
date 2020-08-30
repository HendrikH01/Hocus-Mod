package com.xX_deadbush_Xx.witchcraftmod.common.items;

import java.util.function.Supplier;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModItems;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ModArmorMaterials implements IArmorMaterial {
	
	SILVER(WitchcraftMod.MOD_ID + ":silver", 19, new int[]{2, 6, 6, 3}, 16, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.7F, ()-> {
		return Ingredient.fromItems(ModItems.SILVER_INGOT.get());
	}),
	
	MAGIC_LEATHER(WitchcraftMod.MOD_ID + ":magic_leather", 9, new int[]{2, 3, 4, 2}, 26, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, ()-> {
		return Ingredient.fromItems(Items.LEATHER);
	}),
	MANA_GOGGLES(WitchcraftMod.MOD_ID + ":mana_goggles", 5, new int[]{0, 0, 0, 2}, 6, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, null);

	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final LazyValue<Ingredient> repairMaterial;

	private ModArmorMaterials(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughnessIn, Supplier<Ingredient> repairMaterialSupplier) {
		this.name = nameIn;
		this.maxDamageFactor = maxDamageFactorIn;
		this.damageReductionAmountArray = damageReductionAmountsIn;
		this.enchantability = enchantabilityIn;
		this.soundEvent = equipSoundIn;
		this.toughness = toughnessIn;
		this.repairMaterial = new LazyValue<>(repairMaterialSupplier);
	}

	public int getDurability(EquipmentSlotType slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	public int getEnchantability() {
		return this.enchantability;
	}

	public SoundEvent getSoundEvent() {
		return this.soundEvent;
	}

	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}

	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return this.name;
	}

	public float getToughness() {
		return this.toughness;
	}
}
