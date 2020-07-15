package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import net.minecraft.util.ResourceLocation;

public class BookPage {

	private int page;
	private boolean isFull = false;
	
	public BookPage(int page) {
		this.page = page;
	}
	
	public int getPage() {
		return this.page;
	}
	
	public boolean isFull() {
		return this.isFull;
	}
	
	public void addImage(ResourceLocation image) {
	}
	
	public void addParagraph(String paragraph) {
	}

	public boolean checkIfFullWithParagraph(String content) {
		return false;
	}

	public String splitParagraphToFitAndGetLeftovers(String content) {
		return null;
	}

	public void addTitle(String title) {
	}
	
}
