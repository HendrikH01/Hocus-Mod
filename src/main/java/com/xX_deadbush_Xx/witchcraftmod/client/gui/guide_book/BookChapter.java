package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.ResourceLocation;

public class BookChapter {
	
	private String title;
	private List<BookPage> pages = Lists.newArrayList();
	
	public BookChapter(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addPage(String content, int page, ResourceLocation image) {
		pages.add(new BookPage(content, page, image));
	}
	
}
