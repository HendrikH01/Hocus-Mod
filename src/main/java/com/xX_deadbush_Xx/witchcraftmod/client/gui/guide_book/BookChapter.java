package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.ResourceLocation;

public class BookChapter {
	
	private String title;
	private List<Type> contentStructure = Lists.newArrayList();
	private List<String> contentParagraphs = Lists.newArrayList();
	private List<ResourceLocation> images = Lists.newArrayList();
	private List<BookPage> pages = Lists.newArrayList();
	
	public BookChapter(String title) {
		this.title = title;
	}
	
	public BookChapter addImage(ResourceLocation image) {
		this.images.add(image);
		this.contentStructure.add(Type.IMAGE);
		return this;
	}
	
	public BookChapter addParagraph(String paragraph) {
		paragraph = paragraph + "/n";
		this.contentParagraphs.add(paragraph);
		this.contentStructure.add(Type.PARAGRAPH);
		return this;
	}
	
	public void generateChapter() {
		String content;
		ResourceLocation image;
		BookPage page = new BookPage(++GuideBookScreen.SCREEN.pages);
		page.addTitle(this.title);
		for(int i = 0 ; i < this.contentStructure.size(); i++) {
			if(contentStructure.get(i).equals(Type.PARAGRAPH)) {
				content = this.contentParagraphs.get(0);
				this.contentParagraphs.remove(0);
				if(!page.checkIfFullWithParagraph(content)) {
					page.addParagraph(content);
				} else {
					String leftovers = page.splitParagraphToFitAndGetLeftovers(content);
					this.addPage(page);
					page = new BookPage(++GuideBookScreen.SCREEN.pages);
					page.addParagraph(leftovers);
				}
			} else if(contentStructure.get(i).equals(Type.IMAGE)) {
				image = this.images.get(0);
				this.images.remove(0);
				page.addImage(image);
			}
			if(page.isFull()) {
				this.addPage(page);
				page = new BookPage(++GuideBookScreen.SCREEN.pages);
			}
		}
	}
	
	private void addPage(BookPage page) {
		pages.add(page);
	}
	
	enum Type{
		IMAGE,
		PARAGRAPH,
		RECIPE
	}
	
}
