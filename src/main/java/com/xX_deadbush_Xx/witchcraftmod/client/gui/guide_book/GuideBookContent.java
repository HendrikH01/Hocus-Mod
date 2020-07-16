package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.ArrayList;
import java.util.List;

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
}
