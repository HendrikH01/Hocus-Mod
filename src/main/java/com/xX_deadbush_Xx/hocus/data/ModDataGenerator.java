package com.xX_deadbush_Xx.hocus.data;

import com.xX_deadbush_Xx.hocus.Hocus;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Hocus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new RecipesDataGen(generator));
            generator.addProvider(new LootTablesDataGen(generator));
            //generator.addProvider(new BlockTagsDataGen(generator));
            //generator.addProvider(new ItemTagsDataGen(generator));
        }

        if (event.includeClient()) {
            generator.addProvider(new BlockModelsDataGen(generator, Hocus.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(new BlockStatesDataGen(generator, Hocus.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(new ItemModelsDataGen(generator, Hocus.MOD_ID, event.getExistingFileHelper()));
            generator.addProvider(new LanguageDataGen(generator, Hocus.MOD_ID));
        }
    }
}