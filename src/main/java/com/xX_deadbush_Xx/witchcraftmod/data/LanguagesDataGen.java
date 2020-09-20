package com.xX_deadbush_Xx.witchcraftmod.data;

import com.xX_deadbush_Xx.witchcraftmod.common.potion.ModPotions;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguagesDataGen extends LanguageProvider {

	public LanguagesDataGen(DataGenerator generator, String modId) {
		super(generator, modId, "en_us");
	}

	@Override
	protected void addTranslations() {
		blocks();
		items();
		
		add(ModPotions.BELLADONNA_POISION, "Belladonna Poison");
		add(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModPotions.BELLADONNA_POISION_POTION.get()), "Belladonna Poison Potion");
		add(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), ModPotions.BELLADONNA_POISION_POTION.get()), "Splash Belladonna Poison Potion");
		add(PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), ModPotions.BELLADONNA_POISION_POTION.get()), "Lingering Belladonna Poison Potion");
	}

	private void items() {
		addItem(ModItems.ADONIS_SEED_POD::get, "Adonis Seed Pod");
		addItem(ModItems.BELLADONNA_BERRY::get, "Belladonna Berry");
		addItem(ModItems.DREADWOOD_STICK::get, "Dreadwood Stick");
		addItem(ModItems.MAGIC_CHALK::get, "Magic Chalk");
		addItem(ModItems.PARTICLE_DEBUGGER::get, "Particle Debugger");
		addItem(ModItems.SACRIFICE_KNIFE::get, "Sacrifice Knife");
		addItem(ModItems.VIBRANT_CRYSTAL::get, "Vibrant Crystal");
		addItem(ModItems.VIBRANT_DUST::get, "Vibrant Dust");
		addItem(ModItems.WET_CHALK::get, "Wet Chalk");
		addItem(ModItems.PESTLE::get, "Pestle");
		addItem(ModItems.WOOD_SAW::get, "Wood Saw");
		addItem(ModItems.SLEDGE_HAMMER::get, "Sledge Hammer");
		addItem(ModItems.ADVANCED_ENERGY_CRYSTAL::get, "Advanced Energy Crystal");
		addItem(ModItems.WEAK_ENERGY_CRYSTAL::get, "Weak Energy Crystal");
		addItem(ModItems.LIGHTNING_ROD::get, "Lightning Rod");
		addItem(ModItems.BOTTOMLESS_BAG::get, "Bottomless Bag");
		addItem(ModItems.NATURE_WAND::get, "Nature Wand");

	}

	private void blocks() {
		addBlock(ModBlocks.ADONIS::get, "Adonis");
		addBlock(ModBlocks.BELLADONNA::get, "Belladonna");
		addBlock(ModBlocks.CANDLE::get, "Candle");
		addBlock(ModBlocks.DREADWOOD_LEAVES::get, "Dreadwood Leaves");
		addBlock(ModBlocks.DREADWOOD_LOG::get, "Dreadwood Log");
		addBlock(ModBlocks.DREADWOOD_PLANKS::get, "Dreadwood Planks");
		addBlock(ModBlocks.DREADWOOD_SAPLING::get, "Dreadwood Sapling");
		addBlock(ModBlocks.DRYING_RACK::get, "Drying Rack");
		addBlock(ModBlocks.HARDENED_NETHERRACK::get, "Hardened Netherrack");
		addBlock(ModBlocks.HELLSHROOM::get, "Hellshroom");
		addBlock(ModBlocks.HELLSHROOM_BLOCK::get, "Hellshroom Block");
		addBlock(ModBlocks.HELLSHROOM_STEM::get, "Hellshroom Stem");
		addBlock(ModBlocks.RITUAL_PEDESTAL::get, "Ritual Pedestal");
		addBlock(ModBlocks.RITUAL_STONE::get, "Ritual Stone");
		addBlock(ModBlocks.VIBRANT_BLOCK::get, "Vibrant Block");
		addBlock(ModBlocks.VIBRANT_CRYSTAL_ORE::get, "Vibrant Crystal Ore");
		addBlock(ModBlocks.STONE_MORTAR::get, "Vibrant Crystal Ore");
		addBlock(ModBlocks.TABLE::get, "Table");
		addBlock(ModBlocks.TOOL_TABLE::get, "Tool Table");
		addBlock(ModBlocks.CRYSTAL_RECHARGER::get, "Crystal Recharger");
	}

	@Override
	public String getName() {
		return "Translation generator";
	}
}
