package com.xX_deadbush_Xx.witchcraftmod.common.register;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.client.ModItemGroups;
import com.xX_deadbush_Xx.witchcraftmod.common.items.BloodPhial;
import com.xX_deadbush_Xx.witchcraftmod.common.items.BottomlessBagItem;
import com.xX_deadbush_Xx.witchcraftmod.common.items.EnergyCrystal;
import com.xX_deadbush_Xx.witchcraftmod.common.items.EternalBagOfBonemeal;
import com.xX_deadbush_Xx.witchcraftmod.common.items.GuideBook;
import com.xX_deadbush_Xx.witchcraftmod.common.items.MagicChalk;
import com.xX_deadbush_Xx.witchcraftmod.common.items.MagnetTalisman;
import com.xX_deadbush_Xx.witchcraftmod.common.items.ModArmorMaterials;
import com.xX_deadbush_Xx.witchcraftmod.common.items.SacrificeKnife;
import com.xX_deadbush_Xx.witchcraftmod.common.items.WaterWalkingTalisman;
import com.xX_deadbush_Xx.witchcraftmod.common.items.WitchHatItem;
import com.xX_deadbush_Xx.witchcraftmod.common.items.wands.FireWand;
import com.xX_deadbush_Xx.witchcraftmod.common.items.wands.LightningRod;
import com.xX_deadbush_Xx.witchcraftmod.common.items.wands.LinkingWand;
import com.xX_deadbush_Xx.witchcraftmod.common.items.wands.ParticleDebugger;
import com.xX_deadbush_Xx.witchcraftmod.common.items.wands.StaffOfDestruction;
import com.xX_deadbush_Xx.witchcraftmod.common.items.wands.ThunderStaff;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, WitchcraftMod.MOD_ID);
	
	//BOOK
	public static final RegistryObject<Item> GUIDE_BOOK = ITEMS.register("guide_book", () -> new GuideBook(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));

	//CRYSTAL
	public static final RegistryObject<Item> VIBRANT_CRYSTAL = ITEMS.register("vibrant_crystal", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> VIBRANT_DUST = ITEMS.register("vibrant_dust", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DUST_OF_NATURE = ITEMS.register("dust_of_nature", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DUST_OF_CORRUPTION = ITEMS.register("dust_of_corruption", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DUST_OF_SERENITY = ITEMS.register("dust_of_serenity", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DUST_OF_CHAOS = ITEMS.register("dust_of_chaos", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DUST_OF_CREATION = ITEMS.register("dust_of_creation", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DUST_OF_DESTRUCTION = ITEMS.register("dust_of_destruction", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	
	//PLANTS
	public static final RegistryObject<Item> ADONIS_SEED_POD = ITEMS.register("adonis_seed_pod", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> BELLADONNA_BERRY = ITEMS.register("belladonna_berry", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> DREADWOOD_STICK = ITEMS.register("dreadwood_stick", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> SLEDGE_HAMMER = ITEMS.register("sledge_hammer", () -> new Item(new Item.Properties().maxDamage(200).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> PLANT_OIL = ITEMS.register("plant_oil_bowl", () -> new Item(new Item.Properties().maxStackSize(16).group(ModItemGroups.ITEMS)));
	
	//RITUAL
	public static final RegistryObject<Item> WET_CHALK = ITEMS.register("wet_chalk", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> MAGIC_CHALK = ITEMS.register("magic_chalk", () -> new MagicChalk(new Item.Properties().group(ModItemGroups.ITEMS).maxDamage(50)));	
	public static final RegistryObject<Item> RITUAL_ACTIVATOR = ITEMS.register("ritual_activator", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));	
	public static final RegistryObject<Item> SACRIFICE_KNIFE = ITEMS.register("sacrifice_knife", () -> new SacrificeKnife());	
	
	//TALISMAN
	public static final RegistryObject<Item> WATER_WALKING_TALISMAN = ITEMS.register("water_walk_talisman", () -> new WaterWalkingTalisman(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> MAGNET_TALISMAN = ITEMS.register("magnet_talisman", () -> new MagnetTalisman(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));

	//WANDS/STAFFS
	public static final RegistryObject<Item> LINKING_WAND = ITEMS.register("linking_wand", () -> new LinkingWand(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> LIGHTNING_ROD = ITEMS.register("lightning_rod", () -> new LightningRod(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> FIRE_STAFF = ITEMS.register("fire_staff", () -> new FireWand(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> THUNDER_STAFF = ITEMS.register("thunder_staff", () -> new ThunderStaff(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> STAFF_OF_DESTRUCTION = ITEMS.register("staff_of_destruction", () -> new StaffOfDestruction(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	public static final RegistryObject<Item> STAFF_OF_CREATION = ITEMS.register("staff_of_creation", () -> new LinkingWand(new Item.Properties().maxStackSize(1).group(ModItemGroups.ITEMS)));
	
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
}
