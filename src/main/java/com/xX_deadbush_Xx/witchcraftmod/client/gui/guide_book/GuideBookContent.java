package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuideBookContent {
	private List<BookChapter> chapters = new ArrayList<>();
	private int maxPageCount;
	
	public GuideBookContent() {
		
	}
	
	public int getNumberOfPages() {
		return maxPageCount;
	}
	
	public void addChapter(BookChapter chapter) {
		chapters.add(chapter);
		maxPageCount += chapter.getNumberOfPages();
	}
	
	public static void loadBookContent() {
		GuideBookContent instance = new GuideBookContent();
		instance.addChapter(
				new BookChapter(new StringTextComponent("testchapter"))
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).addImage(new ResourceLocation("witchcraftmod:gui/testtexture"), 10, 10, 0, 0, 120, 130).build())
		);
		
		GuideBookScreen.INSTANCE.setContent(instance);
	}
}
