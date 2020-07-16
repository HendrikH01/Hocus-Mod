package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;

public class BookChapter {
	
	private ITextComponent title;
	private List<BookPage> pages = Lists.newArrayList();
	
	public BookChapter(ITextComponent title) {
		this.title = title;
	}
	
	public void drawTitle() {
		FontRenderer renderer = Minecraft.getInstance().fontRenderer;
		//TODO
	}
	
	public int getNumberOfPages() {
		return pages.size();
	}
	
	public void addPage(BookPage page) {
		pages.add(page);
	}
}
