package com.xX_deadbush_Xx.hocus.common.register;

import java.util.function.Supplier;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.client.ModItemGroups;
import com.xX_deadbush_Xx.hocus.common.items.BloodPhial;
import com.xX_deadbush_Xx.hocus.common.items.BottomlessBagItem;
import com.xX_deadbush_Xx.hocus.common.items.EnergyCrystal;
import com.xX_deadbush_Xx.hocus.common.items.EternalBagOfBonemeal;
import com.xX_deadbush_Xx.hocus.common.items.GuideBook;
import com.xX_deadbush_Xx.hocus.common.items.MagicChalk;
import com.xX_deadbush_Xx.hocus.common.items.ModArmorMaterials;
import com.xX_deadbush_Xx.hocus.common.items.SacrificeKnife;
import com.xX_deadbush_Xx.hocus.common.items.WitchHatItem;
import com.xX_deadbush_Xx.hocus.common.items.talisman.CelestialTablet;
import com.xX_deadbush_Xx.hocus.common.items.talisman.MagnetTalisman;
import com.xX_deadbush_Xx.hocus.common.items.talisman.WaterWalkingTalisman;
import com.xX_deadbush_Xx.hocus.common.items.wands.FireWand;
import com.xX_deadbush_Xx.hocus.common.items.wands.LightningRod;
import com.xX_deadbush_Xx.hocus.common.items.wands.LinkingWand;
import com.xX_deadbush_Xx.hocus.common.items.wands.ParticleDebugger;
import com.xX_deadbush_Xx.hocus.common.items.wands.StaffOfDestruction;
import com.xX_deadbush_Xx.hocus.common.items.wands.StaffOfLight;
import com.xX_deadbush_Xx.hocus.common.items.wands.ThunderStaff;

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
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Hocus.MOD_ID);
	
	//BOOK
	public static final RegistryObject<Item> GUIDE_BOOK = ITEMS.register("guide_book", () -> new GuideBook(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));

	//CRYSTAL
	public static final RegistryObject<Item> VIBRANT_CRYSTAL = ITEMS.register("vibrant_crystal", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> VIBRANT_DUST = ITEMS.register("vibrant_dust", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> ONYX_CRYSTAL = ITEMS.register("onyx_crystal", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> ONYX_DUST = ITEMS.register("onyx_dust", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	

	public static final RegistryObject<Item> RUBIX_CRYSTAL = ITEMS.register("rubix_crystal", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> POLISHED_EMERALD = ITEMS.register("polished_emerald", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	

	public static final RegistryObject<Item> DUST_OF_NATURE = ITEMS.register("dust_of_nature", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DUST_OF_CORRUPTION = ITEMS.register("dust_of_corruption", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DUST_OF_SERENITY = ITEMS.register("dust_of_serenity", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DUST_OF_CHAOS = ITEMS.register("dust_of_chaos", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DUST_OF_CREATION = ITEMS.register("dust_of_creation", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DUST_OF_DESTRUCTION = ITEMS.register("dust_of_destruction", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	

	//PLANTS
	public static final RegistryObject<Item> DREADWOOD_STICK = ITEMS.register("dreadwood_stick", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> SLEDGE_HAMMER = ITEMS.register("sledge_hammer", () -> new Item(new Item.Properties().maxDamage(200).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> PLANT_OIL = ITEMS.register("plant_oil_bowl", () -> new Item(new Item.Properties().maxStackSize(16).group(ModItemGroups.ITEMS)));
	
	//ENTITY
	public static final RegistryObject<Item> RAVEN_FEATHER = ITEMS.register("raven_feather", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));
	
	//RITUAL
	public static final RegistryObject<Item> WET_CHALK = ITEMS.register("wet_chalk", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> MAGIC_CHALK = ITEMS.register("magic_chalk", () -> new MagicChalk(new Item.Properties().group(ModItemGroups.ITEMS).maxDamage(50)));	
	public static final RegistryObject<Item> RITUAL_ACTIVATOR = ITEMS.register("ritual_activator", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> SACRIFICE_KNIFE = ITEMS.register("sacrifice_knife", () -> new SacrificeKnife());	
	 
	//TALISMAN
	public static final RegistryObject<Item> WATER_WALKING_TALISMAN = ITEMS.register("water_walk_talisman", () -> new WaterWalkingTalisman(new Item.Properties().group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> MAGNET_TALISMAN = ITEMS.register("magnet_talisman", () -> new MagnetTalisman(new Item.Properties().group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> CELESTIAL_TABLET = ITEMS.register("celestial_tablet", () -> new CelestialTablet(new Item.Properties().group(ModItemGroups.ITEMS)));

	//WANDS/STAFFS
	public static final RegistryObject<Item> LINKING_WAND = ITEMS.register("linking_wand", () -> new LinkingWand(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> LIGHTNING_ROD = ITEMS.register("lightning_rod", () -> new LightningRod(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> FIRE_STAFF = ITEMS.register("fire_staff", () -> new FireWand(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> THUNDER_STAFF = ITEMS.register("thunder_staff", () -> new ThunderStaff(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> STAFF_OF_DESTRUCTION = ITEMS.register("staff_of_destruction", () -> new StaffOfDestruction(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> STAFF_OF_LIGHT = ITEMS.register("staff_of_light", () -> new StaffOfLight(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	
	//MISC MAGIC
	public static final RegistryObject<Item> WEAK_ENERGY_CRYSTAL = ITEMS.register("weak_energy_crystal", () -> new EnergyCrystal(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS), 500));
	public static final RegistryObject<Item> ADVANCED_ENERGY_CRYSTAL = ITEMS.register("advanced_energy_crystal", () -> new EnergyCrystal(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS), 50000));
	public static final RegistryObject<Item> SUPREME_ENERGY_CRYSTAL = ITEMS.register("supreme_energy_crystal", () -> new EnergyCrystal(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS), 900000));
	public static final RegistryObject<Item> ETERNAL_BAG_OF_BONEMEAL = ITEMS.register("eternal_bag_of_bonemeal", () -> new EternalBagOfBonemeal(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> BOTTOMLESS_BAG = ITEMS.register("bottomless_bag", () -> new BottomlessBagItem(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));

	//ARMOR / WEARABLES
	public static final RegistryObject<Item> MANASPECS = ITEMS.register("manaspecs", () -> new ArmorItem(ModArmorMaterials.MANA_GOGGLES, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> WITCH_HAT = ITEMS.register("witch_hat", () -> new WitchHatItem(ModArmorMaterials.MAGIC_LEATHER, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.ITEMS)));

	//SILVER
	public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().maxStackSize(64).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> SILVER_HELMET = ITEMS.register("silver_helmet", () -> new ArmorItem(ModArmorMaterials.SILVER, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> SILVER_CHESTPLATE = ITEMS.register("silver_chestplate", () -> new ArmorItem(ModArmorMaterials.SILVER, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> SILVER_LEGGINS = ITEMS.register("silver_leggins", () -> new ArmorItem(ModArmorMaterials.SILVER, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> SILVER_BOOTS = ITEMS.register("silver_boots", () -> new ArmorItem(ModArmorMaterials.SILVER, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.ITEMS)));
	
	//MISC
	public static final RegistryObject<Item> PARTICLE_DEBUGGER = ITEMS.register("particle_debugger", () -> new ParticleDebugger(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> PESTLE = ITEMS.register("pestle", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> EMPTY_PHIAL = ITEMS.register("empty_phial", () -> new Item(new Item.Properties().maxStackSize(16).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> WOOD_SAW = ITEMS.register("wood_saw", () -> new Item(new Item.Properties().maxDamage(200).group(ModItemGroups.ITEMS)));

	//NO GROUP
	public static final RegistryObject<Item> BLOOD_PHIAL = ITEMS.register("blood_sample", () -> new BloodPhial(new Item.Properties().maxStackSize(1)));
	
	//SPECIAL GROUP
	//public static final RegistryObject<Item> RAVEN_SPAWN_EGG = ITEMS.register("raven_spawn_egg", () -> new SpawnEggItem(ModEntities.RAVEN.get(), 0x23272B, 0xE6AE00, new Item.Properties().group(ModItemGroups.ITEMS)));

	public enum ModArmorMaterial implements IArmorMaterial{
		WITCH_HAT_MATERIAL(Hocus.MOD_ID + ":leather", 50, new int[] {1, 3, 4, 2}, 420, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, () -> {
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
