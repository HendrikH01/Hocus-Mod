package com.xX_deadbush_Xx.hocus.common.register;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.BigHellshroomFeature;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.CobwebDecorator;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.DreadwoodTreeFoliagePlacer;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.TreeIvyDecorator;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.config.DreadwoodTreeFeature;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.config.MultipleWithChanceBlockDependantConfig;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.config.MultipleWithChanceBlockDependantRandomSelector;
import com.xX_deadbush_Xx.hocus.common.world.gen.structures.WizardTowerPieces;
import com.xX_deadbush_Xx.hocus.common.world.gen.structures.WizardTowerStructure;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {
	
	public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, Hocus.MOD_ID);
	
	public static final RegistryObject<Feature<BigMushroomFeatureConfig>> HUGE_FUNKY_MUSHROOM = FEATURES.register("big_hellshroom", () -> 
		new BigHellshroomFeature(BigMushroomFeatureConfig::deserialize));

	public static final RegistryObject<Feature<TreeFeatureConfig>> DREADWOOD_TREE = FEATURES.register("dreadwood_tree", () -> 
		new DreadwoodTreeFeature(TreeFeatureConfig::deserializeFoliage));
	
	public static final RegistryObject<MultipleWithChanceBlockDependantRandomSelector> BLOCK_DEPENDANT_SELECTOR = FEATURES.register("block_dependant_selector", () -> 
		new MultipleWithChanceBlockDependantRandomSelector(MultipleWithChanceBlockDependantConfig::deserialize));

	
	//STRUCTURE
	public static final RegistryObject<Structure<NoFeatureConfig>> WIZARD_TOWER = FEATURES.register("wizard_tower", () -> new WizardTowerStructure(NoFeatureConfig::deserialize));

	//TYPES
	public static final IStructurePieceType WTPT = Registry.register(Registry.STRUCTURE_PIECE, "rdhp", WizardTowerPieces.Piece::new);

	public static final FoliagePlacerType<DreadwoodTreeFoliagePlacer> DREADWOOD_FOLIAGE_PLACER = Registry.register(Registry.FOLIAGE_PLACER_TYPE, Hocus.MOD_ID + ":dreadwood", new FoliagePlacerType<DreadwoodTreeFoliagePlacer>(DreadwoodTreeFoliagePlacer::new));

	public static final TreeDecoratorType<TreeIvyDecorator> IVY_DECORATOR = Registry.register(Registry.TREE_DECORATOR_TYPE, Hocus.MOD_ID + ":ivy_decorator", 
			new TreeDecoratorType<TreeIvyDecorator>((d) -> new TreeIvyDecorator()));

	public static final TreeDecoratorType<CobwebDecorator> COB_WEB_DECORATOR = Registry.register(Registry.TREE_DECORATOR_TYPE, Hocus.MOD_ID + ":cobweb_decorator", 
			new TreeDecoratorType<CobwebDecorator>((d) -> new CobwebDecorator()));

}
