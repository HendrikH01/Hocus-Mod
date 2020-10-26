package com.xX_deadbush_Xx.hocus.data;

import javax.annotation.Nonnull;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;

public class ItemTagProvider extends ItemTagsProvider {
	public ItemTagProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerTags() {

	}

	@Nonnull
	@Override
	public String getName() {
		return "Hocus item tags";
	}
}
