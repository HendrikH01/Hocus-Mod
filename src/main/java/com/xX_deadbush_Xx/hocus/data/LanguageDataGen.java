package com.xX_deadbush_Xx.hocus.data;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.potion.ModPotions;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageDataGen extends LanguageProvider {

	public LanguageDataGen(DataGenerator generator, String modId) {
		super(generator, modId, "en_us");
	}

	@Override
	protected void addTranslations() {
		blocks();
		items();
		
		//POTION
		add(ModPotions.BELLADONNA_POISION, "Belladonna Poison");
		add(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModPotions.BELLADONNA_POISION_POTION.get()), "Belladonna Poison Potion");
		add(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), ModPotions.BELLADONNA_POISION_POTION.get()), "Splash Belladonna Poison Potion");
		add(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), ModPotions.BELLADONNA_POISION_POTION.get()), "Lingering Belladonna Poison Potion");
	
		add(ModPotions.FALLEN_ANGEL_CURSE, "Curse of the Fallen Angel");
		add(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModPotions.CURSE_OF_THE_FALLEN_ANGEL_POTION.get()), "Curse of the Fallen Angel Potion");
		add(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), ModPotions.CURSE_OF_THE_FALLEN_ANGEL_POTION.get()), "Splash Curse of the Fallen Angel Potion");
		add(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), ModPotions.CURSE_OF_THE_FALLEN_ANGEL_POTION.get()), "Lingering Curse of the Fallen Angel Potion");

		//OPTIONS
		addOption("manabar_mode", "Manabar Mode");
		addOption("shown", "Shown");
		addOption("hidden", "Hidden");
		addOption("no_number", "Numbers Hidden");

	}

	private void items() {
		addItem(ModItems.DREADWOOD_STICK::get, "Dreadwood Stick");
		addItem(ModItems.MAGIC_CHALK::get, "Magic Chalk");
		addItem(ModItems.WET_CHALK::get, "Wet Chalk");
		addItem(ModItems.PARTICLE_DEBUGGER::get, "Particle Debugger");
		addItem(ModItems.SACRIFICE_KNIFE::get, "Sacrifice Knife");
		addItem(ModItems.VIBRANT_CRYSTAL::get, "Vibrant Crystal");
		addItem(ModItems.VIBRANT_DUST::get, "Vibrant Dust");
		addItem(ModItems.PESTLE::get, "Pestle");
		addItem(ModItems.WOOD_SAW::get, "Wood Saw");
		addItem(ModItems.SLEDGE_HAMMER::get, "Sledge Hammer");
		addItem(ModItems.ADVANCED_ENERGY_CRYSTAL::get, "Advanced Energy Crystal");
		addItem(ModItems.WEAK_ENERGY_CRYSTAL::get, "Weak Energy Crystal");
		addItem(ModItems.LIGHTNING_ROD::get, "Lightning Rod");
		addItem(ModItems.ETERNAL_BAG_OF_BONEMEAL::get, "Eternal Bag of Bonemeal");
		addItem(ModItems.BOTTOMLESS_BAG::get, "Bottomless Bag");
		addItem(ModItems.LINKING_WAND::get, "Linking Wand");
		addItem(ModItems.GUIDE_BOOK::get, "Guide Book");
		addItem(ModItems.EMPTY_PHIAL::get, "Empty Phial");
		addItem(ModItems.BLOOD_PHIAL::get, "Blood Phial");
		addItem(ModItems.SILVER_BOOTS::get, "Silver Boots");
		addItem(ModItems.SILVER_CHESTPLATE::get, "Silver Chestplate");
		addItem(ModItems.SILVER_LEGGINS::get, "Silver Leggins");
		addItem(ModItems.SILVER_HELMET::get, "Silver Helmet");
		addItem(ModItems.SILVER_INGOT::get, "Silver Ingot");
		addItem(ModItems.RITUAL_ACTIVATOR::get, "Creative Ritual Activator");
		addItem(ModItems.WITCH_HAT::get, "Witch Hat");
		addItem(ModItems.THUNDER_STAFF::get, "Thunder Staff");
		addItem(ModItems.MANASPECS::get, "Manaspecs");
		addItem(ModItems.MAGNET_TALISMAN::get, "Magnet Talisman");
		addItem(ModItems.SUPREME_ENERGY_CRYSTAL::get, "Supreme Energy Crystal");
		addItem(ModItems.PLANT_OIL::get, "Plant Oil");
		addItem(ModItems.FIRE_STAFF::get, "Fire Wand");
		addItem(ModItems.WATER_WALKING_TALISMAN::get, "Water Walking Talisman");

	}

	private void blocks() {
		addBlock(ModBlocks.ADONIS::get, "Adonis Seed Pod");
		addBlock(ModBlocks.BELLADONNA::get, "Belladonna Berry");
		addBlock(ModBlocks.CANDLE::get, "Candle");
		addBlock(ModBlocks.DREADWOOD_LEAVES::get, "Dreadwood Leaves");
		addBlock(ModBlocks.DREADWOOD_LOG::get, "Dreadwood Log");
		addBlock(ModBlocks.DREADWOOD_PLANKS::get, "Dreadwood Planks");
		addBlock(ModBlocks.DREADWOOD_SAPLING::get, "Dreadwood Sapling");
		addBlock(ModBlocks.DRYING_RACK::get, "Drying Rack");
		addBlock(ModBlocks.FUNKY_MUSHROOM::get, "Hellshroom");
		addBlock(ModBlocks.FUNKY_MUSHROOM_BLOCK::get, "Hellshroom Block");
		addBlock(ModBlocks.FUNKY_MUSHROOM_STEM::get, "Hellshroom Stem");
		addBlock(ModBlocks.RITUAL_PEDESTAL::get, "Ritual Pedestal");
		addBlock(ModBlocks.RITUAL_STONE::get, "Ritual Stone");
		addBlock(ModBlocks.VIBRANT_BLOCK::get, "Vibrant Block");
		addBlock(ModBlocks.VIBRANT_CRYSTAL_ORE::get, "Vibrant Crystal Ore");
		addBlock(ModBlocks.STONE_MORTAR::get, "Vibrant Crystal Ore");
		addBlock(ModBlocks.TABLE::get, "Table");
		addBlock(ModBlocks.TOOL_TABLE::get, "Tool Table");
		addBlock(ModBlocks.CRYSTAL_RECHARGER::get, "Crystal Recharger");
		addBlock(ModBlocks.RED_TOTEM::get, "Red Totem");
		addBlock(ModBlocks.PURPLE_TOTEM::get, "Purple Totem");
		addBlock(ModBlocks.GREEN_TOTEM::get, "Green Totem");
		addBlock(ModBlocks.SHALE_BRICKS::get, "Shale Bricks");
		addBlock(ModBlocks.SHALE::get, "Shale");
		addBlock(ModBlocks.CHISELED_SHALE::get, "Chiseled Shale");
		addBlock(ModBlocks.POISON_IVY::get, "Poison Ivy");
		addBlock(ModBlocks.ONYX_ORE::get, "Onyx Ore");
		addBlock(ModBlocks.POLISHED_WOOD::get, "Polished Wood");
		addBlock(ModBlocks.FIRE_BOWL::get, "Fire Bowl");
		addBlock(ModBlocks.ENERGY_RELAY::get, "Energy Relay");
		addBlock(ModBlocks.SWIRLY_PLANT::get, "Swirly Plant");
		addBlock(ModBlocks.CAVE_FLOWER::get, "Cave Flower");
	}
	
	private void addOption(String key, String name) {
		add(Hocus.MOD_ID + ".options." + key, name);
	}

	@Override
	public String getName() {
		return "Hocus Languagegen";
	}
}
