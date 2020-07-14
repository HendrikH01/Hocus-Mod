package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import net.minecraft.util.ResourceLocation;

public class BookPage {

	private String content;
	private int page;
	private ResourceLocation image;
	
	public BookPage(String content, int page, ResourceLocation image) {
		this.content = content;
		this.page = page;
		this.image = image;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public int getPage() {
		return this.page;
	}
	
	public ResourceLocation getImage() {
		return this.image;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public void setImage(ResourceLocation image) {
		this.image = image;
	}
	
}
