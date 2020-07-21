package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.ArrayList;
import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book.BookPage.Side;

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
	
	public List<BookChapter> getChapters() {
		return chapters;
	}
	
	public static void loadBookContent() {
		GuideBookContent instance = new GuideBookContent();
		instance.addChapter(
				new BookChapter(new StringTextComponent("testchapter"))
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).isFirstPage().setSide(Side.RIGHT).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).setSide(Side.LEFT).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).setSide(Side.RIGHT).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 4")).setSide(Side.LEFT).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 5")).setSide(Side.RIGHT).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 6")).setSide(Side.LEFT).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 7")).setSide(Side.RIGHT).build())
		);
		
		GuideBookScreen.INSTANCE.setContent(instance);
	}
}
