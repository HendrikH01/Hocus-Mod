package com.xX_deadbush_Xx.witchcraftmod.data;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
        	System.out.println("Starting common data gen");
            generator.addProvider(new RecipesDataGen(generator));
            generator.addProvider(new LootTablesDataGen(generator));
            //generator.addProvider(new BlockTagsDataGen(generator));
            //generator.addProvider(new ItemTagsDataGen(generator));
        }

        if (event.includeClient()) {
        	System.out.println("Starting client data gen");
            generator.addProvider(new BlockStatesDataGen(generator, WitchcraftMod.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(new BlockModelsDataGen(generator, WitchcraftMod.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(new ItemModelsDataGen(generator, WitchcraftMod.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(new LanguagesDataGen(generator, WitchcraftMod.MOD_ID));
        }
    }
}