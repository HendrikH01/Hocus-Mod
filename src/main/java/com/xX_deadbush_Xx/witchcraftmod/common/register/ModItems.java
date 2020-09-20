package com.xX_deadbush_Xx.witchcraftmod.common.register;

import java.util.function.Supplier;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.client.WitchcraftItemGroup;
import com.xX_deadbush_Xx.witchcraftmod.common.items.BloodPhial;
import com.xX_deadbush_Xx.witchcraftmod.common.items.BottomLessBagItem;
import com.xX_deadbush_Xx.witchcraftmod.common.items.EnergyCrystal;
import com.xX_deadbush_Xx.witchcraftmod.common.items.FireWand;
import com.xX_deadbush_Xx.witchcraftmod.common.items.GuideBook;
import com.xX_deadbush_Xx.witchcraftmod.common.items.LightningRod;
import com.xX_deadbush_Xx.witchcraftmod.common.items.LinkingWand;
import com.xX_deadbush_Xx.witchcraftmod.common.items.MagicChalk;
import com.xX_deadbush_Xx.witchcraftmod.common.items.MagnetTalisman;
import com.xX_deadbush_Xx.witchcraftmod.common.items.ModArmorMaterials;
import com.xX_deadbush_Xx.witchcraftmod.common.items.NatureWand;
import com.xX_deadbush_Xx.witchcraftmod.common.items.ParticleDebugger;
import com.xX_deadbush_Xx.witchcraftmod.common.items.SacrificeKnife;
import com.xX_deadbush_Xx.witchcraftmod.common.items.ThunderStaff;
import com.xX_deadbush_Xx.witchcraftmod.common.items.WaterWalkingTalisman;
import com.xX_deadbush_Xx.witchcraftmod.common.items.WitchHat;
import com.xX_deadbush_Xx.witchcraftmod.common.items.WitchHatItem;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	static {
		System.out.println("WHAT THE HECKKK");
	}
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, WitchcraftMod.MOD_ID);
	
	public static final RegistryObject<Item> VIBRANT_CRYSTAL = ITEMS.register("vibrant_crystal", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> VIBRANT_DUST = ITEMS.register("vibrant_dust", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> ADONIS_SEED_POD = ITEMS.register("adonis_seed_pod", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> BELLADONNA_BERRY = ITEMS.register("belladonna_berry", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> DREADWOOD_STICK = ITEMS.register("dreadwood_stick", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> WOOD_SAW = ITEMS.register("wood_saw", () -> new Item(new Item.Properties().maxDamage(200).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> SLEDGE_HAMMER = ITEMS.register("sledge_hammer", () -> new Item(new Item.Properties().maxDamage(200).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> EMPTY_PHIAL = ITEMS.register("empty_phial", () -> new Item(new Item.Properties().maxStackSize(16).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> PLANT_OIL = ITEMS.register("plant_oil_bowl", () -> new Item(new Item.Properties().maxStackSize(16).group(WitchcraftItemGroup.instance)));

	public static final RegistryObject<Item> MAGIC_CHALK = ITEMS.register("magic_chalk", () -> new MagicChalk(new Item.Properties().group(WitchcraftItemGroup.instance).maxDamage(50)));	
	public static final RegistryObject<Item> WET_CHALK = ITEMS.register("wet_chalk", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> RITUAL_ACTIVATOR = ITEMS.register("ritual_activator", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> PARTICLE_DEBUGGER = ITEMS.register("particle_debugger", () -> new ParticleDebugger(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> SACRIFICE_KNIFE = ITEMS.register("sacrifice_knife", () -> new SacrificeKnife());	
	public static final RegistryObject<Item> PESTLE = ITEMS.register("pestle", () -> new Item(new Item.Properties().group(WitchcraftItemGroup.instance)));	
	public static final RegistryObject<Item> BLOOD_PHIAL = ITEMS.register("blood_sample", () -> new BloodPhial(new Item.Properties().maxStackSize(1)));
	public static final RegistryObject<Item> LIGHTNING_ROD = ITEMS.register("lightning_rod", () -> new LightningRod(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> THUNDER_STAFF = ITEMS.register("thunder_staff", () -> new ThunderStaff(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> FIRE_WAND = ITEMS.register("fire_wand", () -> new FireWand(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> BOTTOMLESS_BAG = ITEMS.register("bottomless_bag", () -> new BottomLessBagItem(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> WEAK_ENERGY_CRYSTAL = ITEMS.register("weak_energy_crystal", () -> new EnergyCrystal(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance), 500));
	public static final RegistryObject<Item> ADVANCED_ENERGY_CRYSTAL = ITEMS.register("advanced_energy_crystal", () -> new EnergyCrystal(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance), 50000));
	public static final RegistryObject<Item> SUPREME_ENERGY_CRYSTAL = ITEMS.register("supreme_energy_crystal", () -> new EnergyCrystal(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance), 900000));
	public static final RegistryObject<Item> WATER_WALKING_TALISMAN = ITEMS.register("water_walk_talisman", () -> new WaterWalkingTalisman(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> MAGNET_TALISMAN = ITEMS.register("magnet_talisman", () -> new MagnetTalisman(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> GUIDE_BOOK = ITEMS.register("guide_book", () -> new GuideBook(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> LINKING_WAND = ITEMS.register("linking_wand", () -> new LinkingWand(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().maxStackSize(64).group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> WITCHES_HAT = ITEMS.register("witches_hat", () -> new WitchHatItem(ModArmorMaterials.MAGIC_LEATHER, EquipmentSlotType.HEAD, new Item.Properties().group(WitchcraftItemGroup.instance)));
	
	public static final RegistryObject<Item> SILVER_HELMET = ITEMS.register("silver_helmet", () -> new ArmorItem(ModArmorMaterials.SILVER, EquipmentSlotType.HEAD, new Item.Properties().group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> SILVER_CHESTPLATE = ITEMS.register("silver_chestplate", () -> new ArmorItem(ModArmorMaterials.SILVER, EquipmentSlotType.CHEST, new Item.Properties().group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> SILVER_LEGGINS = ITEMS.register("silver_leggins", () -> new ArmorItem(ModArmorMaterials.SILVER, EquipmentSlotType.LEGS, new Item.Properties().group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> SILVER_BOOTS = ITEMS.register("silver_boots", () -> new ArmorItem(ModArmorMaterials.SILVER, EquipmentSlotType.FEET, new Item.Properties().group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> MANASPECS = ITEMS.register("manaspecs", () -> new ArmorItem(ModArmorMaterials.MANA_GOGGLES, EquipmentSlotType.HEAD, new Item.Properties().group(WitchcraftItemGroup.instance)));
	public static final RegistryObject<Item> NATURE_WAND = ITEMS.register("nature_wand", () -> new NatureWand(new Item.Properties().maxStackSize(1).group(WitchcraftItemGroup.instance)));
	
	public enum ModArmorMaterial implements IArmorMaterial{
		WITCH_HAT_MATERIAL(WitchcraftMod.MOD_ID + ":leather", 50, new int[] {1, 3, 4, 2}, 420, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, () -> {
			return Ingredient.fromItems(Items.LEATHER);
		});
		
		private static final int[] MAX_DAMAGE_ARRAY = new int[] {16, 16, 16, 16};
		private final String name;
		private final int maxDamageFactor;
		private final int[] damageReductionAmountArray;
		private final int enchantability;
		private final SoundEvent soundEvent;
		private final float toughness;
		private final LazyValue<Ingredient> repairMaterial;
		
		private ModArmorMaterial(String nameIn, int maxDamageFactor, int[] damageReductionAmountIn, int enchantabilityIn, SoundEvent soundEventIn, float toughnessIn, Supplier<Ingredient> repairMaterialIn) {
			this.name = nameIn;
			this.maxDamageFactor = maxDamageFactor;
			this.damageReductionAmountArray = damageReductionAmountIn;
			this.enchantability = enchantabilityIn;
			this.soundEvent = soundEventIn;
			this.toughness = toughnessIn;
			this.repairMaterial = new LazyValue<>(repairMaterialIn);
		}

		@Override
		public int getDurability(EquipmentSlotType slotIn) {
			return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
		}

		@Override
		public int getDamageReductionAmount(EquipmentSlotType slotIn) {
			return this.damageReductionAmountArray[slotIn.getIndex()];
		}

		@Override
		public int getEnchantability() {
			return this.enchantability;
		}

		@Override
		public SoundEvent getSoundEvent() {
			return this.soundEvent;
		}

		@Override
		public Ingredient getRepairMaterial() {
			return this.repairMaterial.getValue();
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public float getToughness() {
			return this.toughness;
		}		
	}
}
